package ir.gorganshaj.admin;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import ir.gorganshaj.admin.Connection.*;
import java.util.concurrent.*;
import android.content.*;
import org.json.*;

public class Login extends Activity
{  
	SharedPreferences appsettings;
	USER user;
	EditText txtmail,txtpass,txturl;
	String token="";
	String email="";
	String pass="";
	String burl="";
	String loginUrl="";
     public void sabtenam(View v){
		 if(txtmail.getText().toString().equals("") || txtpass.getText().toString().equals("")){
			 return;
		 }
		 setTitle("ورود به سایت");
		 burl=txturl.getText().toString();
		 loginUrl=burl+"login.php";
		 WebRequest req= new WebRequest(loginUrl,"POST");
		 email=txtmail.getText().toString();
		 req.setParameter("email",email);
		 pass=txtpass.getText().toString();
		 req.setParameter("password",pass);
		 
		 
			 try
			 {
				 
				 
				 String res=new  mHttpconnection().execute(req).get();
				 Toast.makeText(this,res,Toast.LENGTH_LONG).show();
				 JSONObject obj=new JSONObject(res);
				// String token=obj.getString("userid");
				 String token=obj.getString("token");
				 
				appsettings=getSharedPreferences("gorgansharj",0);
				 appsettings.edit().putString("token",token).apply();
				 appsettings.edit().putString("email",email).apply();
				 appsettings.edit().putString("password",pass).apply();
				 appsettings.edit().putString("baseurl",burl).apply();
				//user.setEmail(email);
				//user.setPassword(pass);
				//user.setToken(token);
				//user.setBaseurl(burl);
				 
				 Intent tin=new Intent(Login.this,Tarif.class);
				 startActivity(tin);
				 finish();
				// Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();
				 
			 }
			 catch(Exception e){
				// Toast.makeText(getApplicationContext(),e.toString()
				 
				//,Toast.LENGTH_LONG).show();
				 
			 }

	 }
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ly_login);
		user=new USER(this);
		if(!user.getEmail().equals("")){
			
			startService(new Intent(this,MService.class));
		}
		txtmail=(EditText)findViewById(R.id._login_email);
		txtpass=(EditText)findViewById(R.id._login_pass);
		txturl=(EditText)findViewById(R.id._login_url);
		appsettings=getSharedPreferences("gilorgansharj",0);
		token=appsettings.getString("token","n");
		
		if(!token.equals("n")){
			Intent intent=new Intent(this,MainActivity.class);
			//Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();
			startActivity(intent);
		}
		
	}

	@Override
	protected void onResume()
	{
		
		appsettings=getSharedPreferences("gilorgansharj",0);
		token=appsettings.getString("token","n");
		if(!token.equals("n")){
			Intent intent=new Intent(this,MainActivity.class);
			startActivity(intent);
		}
		
		// TODO: Implement this method
		super.onResume();
	}
	
	
}
