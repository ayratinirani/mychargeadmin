package ir.gorganshaj.admin;

import android.Manifest;
import android.app.*;
import android.content.pm.PackageManager;
import android.os.*;
import android.content.*;

import ir.gorganshaj.admin.Connection.*;

import java.util.concurrent.*;

import android.support.v4.app.ActivityCompat;
import android.widget.*;

import org.json.*;

import android.net.*;
import android.util.*;

public class MService extends Service {
	String email = "";
	String pass = "";
	String baseurl = "";
	String token = "";
	Enums enums;
	SharedPreferences sh;
	UsRequest UsRequest;
	USER user;
	String tag = "MSERVICE_LOG";

	private Object mobilenum;
	//private String token="";

	//private String BaseUrl="";

	@Override
	public IBinder onBind(Intent p1) {
		// TODO: Implement this method
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
//		
		// TODO: Implement this method
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		UsRequest = new UsRequest(this);
		user = new USER(this);

		sh = this.getSharedPreferences("gorgansharj", 0);
		baseurl = sh.getString("baseurl", "");
		email = sh.getString("email", "");
		token = sh.getString("token", "");
		pass = sh.getString("password", "");
		showMessage("doing commands", 0);
		get_news();
		chekresults();

		return Service.START_STICKY;
		// TODO: Implement this method
		//return super.onStartCommand(intent, flags, startId);
	}

	private void chekresults() {
		UsRequest = new UsRequest(this);
		user = new USER(this);
		showMessage("cheking res", 0);
		if (!UsRequest.getCode().equals("") && !UsRequest.getMob().equals("")) {
			waitfor(5);

			doussdCommand();

			waitfor(5);

			get_news();
		} else {

			waitfor(5);

			get_news();
		}
	}

	private void doussdCommand() {
		showMessage("doing code", 0);
		UsRequest = new UsRequest(this);
		user = new USER(this);

		String ccccd = enums.get(UsRequest.getCode());
		if (ccccd.equals("")) {
			showMessage("این نوع کد تعریف نشده است", 1);
			//exe.setVisibility(View.GONE);
			return;
		} else {
			showMessage("doing ussd", 0);
			String ucode = enums.get(UsRequest.getCode()) + UsRequest.getMob() + "*1#";


			String ussdCode = Uri.encode(ucode);
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
			startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ussdCode)));
			showMessage("ussd done",0);
		// TODO: Implement this method
	}
	
	
	
	waitfor(60);
	
	
	if(UsRequest.getMob().equals("")){
		doUpdateofError();
	}else{
		return;
	}
	
	}

	private void doUpdateofError()
	{
		showMessage("doing updates",0);
		UsRequest=new UsRequest(this);
		user=new USER(this);
		String url=baseurl+"update1.php";
		WebRequest req=new WebRequest(url,"POST");
		req.setParameter("email",email);
		req.setParameter("password",pass);
		req.setParameter("token",token);
		req.setParameter("mobile",mobilenum);
		try
		{
			String res=new mHttpconnection().execute(req).get();

			showMessage(res,0);
			JSONObject data= new JSONObject(res);


			String error=data.getString("error");
			if(error.equals("no")){
				showMessage("خطا در سایت ذخیره شد ",1);
				UsRequest.setMob("");
				UsRequest.setCode("");
				
			}else{
				
				showMessage(UsRequest.getCode()+""+UsRequest.getMob()+"ذخیره نشد",1);
			}

		}
		catch (InterruptedException e)
		{
			showMessage(e.toString(),1);
		}
		catch (Exception e)
		{
			showMessage(e.toString(),1);
		}

		
		
		// TODO: Implement this method
	}
	
	
	
	
	
	
	public void get_news() {
		showMessage("start to get requests",0);
		UsRequest=new UsRequest(this);
		user=new USER(this);
		String url=baseurl+"getnews.php";
		
		WebRequest req=new WebRequest(url,"POST");
		req.setParameter("email",email);
		req.setParameter("password",pass);
		req.setParameter("token",token);
		showMessage(email+pass+token,0);
		try
		{
			String res=new mHttpconnection().execute(req).get();
			
			showMessage(res,0);
			JSONObject data= new JSONObject(res);
			
			
			String error=data.getString("error");
			if(error.equals("empty")){
				showMessage("no thing to show",1);
			}else{
				JSONObject result=data.getJSONObject("result");
				
				UsRequest.setCode(result.getString(""));
				UsRequest.setMob(result.getString("mobile"));
				showMessage(UsRequest.getCode()+""+UsRequest.getMob(),0);
			}
			
		}
		catch (InterruptedException e)
		{
			showMessage(e.toString(),1);
		}
		catch (Exception e)
		{
			showMessage(e.toString(),1);
		}

		
	}
	
	private void showMessage(String m, int mode){
         if(mode==0){
		Log.i(tag,m);}
		if(mode==1){
			Log.e(tag,m);}
		Toast.makeText(getApplicationContext(),m,Toast.LENGTH_LONG).show();
	}
	
	public void waitfor(int i){
		try
		{
			showMessage("waiting for"+i+"seconds",0);
			Thread.sleep(i * 1000);
		}
		catch (InterruptedException e)
		{
			showMessage("waiting in problem",1);
			
		}
	}
	
	
}
