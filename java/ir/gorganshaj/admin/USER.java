package ir.gorganshaj.admin;
import android.content.*;


public class USER
{
	SharedPreferences sh;
	Context context;
	public USER(Context c){
		this.context=c;
		sh=context.getSharedPreferences("gorgansharj",0);
	}
	private String Token="";
	private String Baseurl="";
	private String Email="";
	
	private String Password;

	public void setBaseurl(String baseurl1)
	{
		///Baseurl = baseurl1;
		sh.edit().putString("baseurl",baseurl1).apply();
	}

	public String getBaseurl()
	{
		return sh.getString("baseurl","");
	}

	public void setToken(String token1)
	{
		//this.Token = token1;
		sh.edit().putString("token",token1).apply();
	}

	public String getToken()
	{
		return sh.getString("token","n");
	}

	public void setEmail(String email1)
	{
		//this.Email = email1;
		sh.edit().putString("email",email1).apply();
	}

	public String getEmail()
	{
		return sh.getString("email","");
	}

	public void setPassword(String password1)
	{
		//this.Password = password1;
		sh.edit().putString("password",password1).apply();
	}

	public String getPassword()
	{
		return sh.getString("password","");
	}
	
	public void logout(){
		sh.edit().clear().apply();
	}
	public String readabletext(){
		return Email+Token+Baseurl;
	}
}
