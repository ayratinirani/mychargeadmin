package ir.gorganshaj.admin;

import android.Manifest;
import android.app.*;
import android.content.pm.PackageManager;
import android.os.*;

import java.util.*;

import android.database.sqlite.*;

import ir.gorganshaj.admin.Connection.*;

import java.util.concurrent.*;

import android.support.v4.app.ActivityCompat;
import android.widget.*;

import org.json.*;

import android.view.View.*;
import android.view.*;
import android.net.*;
import android.content.*;
import android.support.v7.app.*;
import android.icu.text.*;
import android.util.*;

public class MainActivity extends AppCompatActivity {
	String code = "0";
	String tag = "MAIN_GORGAN_SJHARJ";
	String mobile = "";
	TextView tvCode;
	TextView tvMob;
	Button exe;
	String user;
	boolean isGetting = false;
	String pass;
	SQLiteDatabase DB;
	ProgressDialog progress;


	Timer timer;

	TimerTask timerTask;

	Enums enums;
	final Handler handler = new Handler();
	String Number = "";
	USER muser;
	public static SharedPreferences appsettings;
	String token = "";
	public static String BaseUrl = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		showMessage("oncrate", 0);
		tvCode = (TextView) findViewById(R.id.lbl_code);
		tvMob = (TextView) findViewById(R.id.lbl_mob);
		exe = (Button) findViewById(R.id.btn_ok);
		muser = new USER(this);

		if (!muser.getEmail().equals("")) {
		}
		//setTitle("مشاهده درخواست");

		appsettings = getSharedPreferences("gorgansharj", 0);
		token = appsettings.getString("token", "n");
		BaseUrl = appsettings.getString("baseurl", "");
		if (token.equals("n")) {
			Intent intent = new Intent(MainActivity.this, Login.class);
			//Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();

			startActivity(intent);
		}
		user = appsettings.getString("email", "");
		pass = appsettings.getString("password", "");
		get_news();
		//startTimer();

	}//oncreate ends


	public void runit(View v) {

	}


	//TODO GET new codes user pass send get json atrray mobile ussdname
	private boolean get_news() {
		//
		stoptimertask(exe);
		//در یافت آخرین در خواستها
		//makerequset
		String url = BaseUrl + "getnews.php";
		WebRequest req = new WebRequest(url, "POST");
		req.setParameter("email", user);
		req.setParameter("password", pass);
		req.setParameter("token", token);
		//progress.setProgress(10);
		//
		try {


			String res = new mHttpconnection().execute(req).get();


			JSONObject object = new JSONObject(res);
			String error = object.getString("error");

			if (error.equals("no")) {

				JSONObject object2 = object.getJSONObject("result");
				code = object2.getString("ussdcode");
				mobile = object2.getString("mobile");
				//progress.setProgress(90);


				tvCode.setText("کد درخواستی" + "   " + code);
				tvMob.setText("شماره تلفن " + "  " + mobile);
				appsettings.edit().putString("Currentcode", code).apply();
				appsettings.edit().putString("CurrentMob", mobile).apply();
				enums = new Enums(this);
				String ccccd = enums.get(code);
				if (ccccd.equals("")) {
					return false;
				} else {
					String ucode = enums.get(code) + mobile + "*1#";
					String ussdCode = Uri.encode(ucode);
					if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
						// TODO: Consider calling
						//    ActivityCompat#requestPermissions
						// here to request the missing permissions, and then overriding
						//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
						//                                          int[] grantResults)
						// to handle the case where the user grants the permission. See the documentation
						// for ActivityCompat#requestPermissions for more details.
						return true;
					}
					startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ussdCode)));

					waitfor(5);
					setTitle("دریافت شد");
					isGetting = false;
					return true;
				}
			} else if (error.equals("empty")) {
				showMessage("empty", 0);
				isGetting = true;
				setTitle("تایمر");
				showMessage("started timer",0);
				startTimer();
			}
			return false;
		} catch (Exception e) {

			setTitle("مشکل شبکه اینترنت");
			startTimer();
			showMessage("connect error", 0);
			get_news();
			return false;
		}

	}//get_news÷ends

//	private boolean doussd() {
//		isGetting = true;
//
//		enums=new Enums(this);
//		//انجام کد براساس کد دریافتی و موبایل
//		//stoptimertask(exe);
//		//setTitle("اجرای کد موبایلی");
//
//		if (!code.equals("0") && !mobile.equals("")) {
//			String ccccd = enums.get(code);
//			if (ccccd.equals("")) {
//				return false;
//			} else {
//				String ucode = enums.get(code) + mobile + "*1#";
//
//				//setTitle("یک دقیقه انتظار تا گرفتن نتیجه ");
//				String ussdCode = Uri.encode(ucode);
//				if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//					// TODO: Consider calling
//					//    ActivityCompat#requestPermissions
//					// here to request the missing permissions, and then overriding
//					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//					//                                          int[] grantResults)
//					// to handle the case where the user grants the permission. See the documentation
//					// for ActivityCompat#requestPermissions for more details.
//					return true;
//				}
//				startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ussdCode)));
//			return true;
//
//				}
//		}
//		return false;
//	}//doussd ends


	
	
	
	private boolean updaterequestes(){
	//	setTitle("در حال ارسال آپدیت");
		showMessage("updating",0);
		isGetting=true;
		//ارسال شماره تلفنی که کد برای ان اجرا شده
		appsettings=getSharedPreferences("gorgansharj",0);
		token=appsettings.getString("token","n");
		Number=appsettings.getString("CurrentMob","");
		if(Number.equals("")){
			return false;
		}
		String url=appsettings.getString("baseurl","")+"update1.php";
		WebRequest req=new WebRequest(url,"POST");
		String email=appsettings.getString("email","");
		String password=appsettings.getString("password","");
		req.setParameter("token",token);
		req.setParameter("email",email);
		req.setParameter("password",password);
		req.setParameter("mobile",Number);
		try
		{
			String res=new mHttpconnection().execute(req).get();
			//Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
			if(res.equals("ok")){
                
				appsettings.edit().putString("CurrentMob","").apply();
				isGetting=false;
				return true;

			}
		}
		catch (Exception e)
		{
			isGetting=false;
			
			appsettings.edit().putString("CurrentMob","").apply();
           return false;
		}
	return false;
	}//update requessts ends

	
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }//create menu ends
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.tarif:
				// EITHER CALL THE METHOD HERE OR DO THE FUNCTION DIRECTLY
				tarif();

				return true;
              case R.id.logout:
				  logout();
				  return true;
				  case R.id.exit:
					  System.exit(0);
					 // this.finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}//onmenuitem ends
	

	private void logout()
	{
		// TODO: Implement this method
		appsettings.edit().clear().apply();
		DB.execSQL("DROP TABLE IF EXISTS ussds");
		Intent intent= new Intent(this,Login.class);
		startActivity(intent);
	}

	@Override
	protected void onResume()
	{
		if(timer!=null){
			timer.cancel();
			timer=null;
		}
		get_news();
		showMessage("onresume",0);
		if(isGetting==false ){
			
		}
		// TODO: Implement this method

		super.onResume();
	}//logout ends
	
	
	
	

	public void tarif()
	{
		// TODO: Implement this method
		Intent tarifintent=new Intent(MainActivity.this,Tarif.class);
		startActivity(tarifintent);
	}//goto tariff ends 
	
	
	
	
	
	public void stoptimertask(View v) {
		//stop the timer, if it's not already null
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}//stoptimerends
	
	
	
	
	public void startTimer() {
		//set a new Timer
		timer = new Timer();

		//initialize the TimerTask's job
		initializeTimerTask();

		//schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
		timer.schedule(timerTask, 1100, 1100); //
	}//starttimerends
	
	
	public void initializeTimerTask() {

		timerTask = new TimerTask() {
			public void run() {

				//use a handler to run a toast that shows the current timestamp
				handler.post(new Runnable() {
						public void run() {
							setTitle("درخواستها");
							//get_news();
							//get the current timeStamp
							Calendar calendar = Calendar.getInstance();
							SimpleDateFormat simpleDateFormat = null;
							if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
								simpleDateFormat = new SimpleDateFormat("HH:mm:ss a");
							}
							final String strDate;
							if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
								strDate = simpleDateFormat.format(calendar.getTime());
								setTitle(strDate);
							}

							
						}
					});
			}
		};
	}//initialtimertask ends
	
	

	

	@Override
	protected void onPause()
	{  
	showMessage("onpause",0);
	    stoptimertask(exe);
		// TODO: Implement this method
		super.onPause();
	}//onpause ends

	
	
	
	
	
	
	@Override
	public void onBackPressed()
	{
		
		
		PopupMenu popup  = new PopupMenu(getApplicationContext(),tvMob);
		Menu menu = popup.getMenu();
		popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());  

		//registering popup with OnMenuItemClickListener  
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {  
				public boolean onMenuItemClick(MenuItem item) {  
					
					switch (item.getItemId()) {
						case R.id.tarif:
							// EITHER CALL THE METHOD HERE OR DO THE FUNCTION DIRECTLY
							tarif();

							return true;
						case R.id.logout:
							logout();
							return true;
						case R.id.exit:
							stoptimertask(exe);
							MainActivity.this.finish();
							return true;
						default:
							return true;
					}
		
				}  
            });  

		popup.show();
		//super.onBackPressed();
	}//onbackpressed ends
	
	private void showMessage(String m, int mode){
		if(mode==0){
			Log.i(tag,m);}
		if(mode==1){
			Log.e(tag,m);}
		//Toast.makeText(getApplicationContext(),m,Toast.LENGTH_LONG).show();
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
