package ir.gorganshaj.admin;
import android.app.*;
import android.os.*;
import android.widget.*;
import ir.gorganshaj.admin.Connection.*;
import android.content.*;

public class SmsRes extends Activity
{  String token;
String mobile;
String message;
SharedPreferences appsettings;
    Bundle bundle;
	TextView t;
	String Number;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		//setContentView(R.layout.ly_sms_res);
		appsettings=getSharedPreferences("gorgansharj",0);
		//t=(TextView) findViewById(R.id.ly_sms_resTextView);
		bundle=getIntent().getExtras();
		mobile=bundle.getString("mobile");
		message=bundle.getString("message");
		Number=appsettings.getString("CurrentMob","");
		// t.setText(message);
		// updaterequestes();
		
		if(mobile.contains(".IRANCELL.")){
			if(message.contains(getResources().getString(R.string.tayid))){
				updaterequestes();
			}
			
		}
		
		//System.exit(0);
	
		
	}
	
	private void updaterequestes(){
         if(Number.equals("")){
			 return;
		 }
		//ارسال شماره تلفنی که کد برای ان اجرا شده
		appsettings=getSharedPreferences("gorgansharj",0);
		token=appsettings.getString("token","n");
		String url=appsettings.getString("baseurl","")+"update.php";
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
			if(res.equals("ok")){
				
				Toast.makeText(getApplicationContext(),"اپدیت دیتالیس انجام شد",Toast.LENGTH_LONG).show();
				startActivity(new Intent(this,MainActivity.class));
				appsettings.edit().putString("CurrentMob","").apply();
				finish();

			}
		}
		catch (Exception e)
		{
			Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
			appsettings.edit().putString("CurrentMob","").apply();

		}

	}
	
}
