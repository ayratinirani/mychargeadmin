package ir.gorganshaj.admin;
import android.content.*;

public class UsRequest
{
	
	SharedPreferences sh;
	Context  context;
	public UsRequest(Context c){
		this.context=c;
		sh=c.getSharedPreferences("gorgansharj",0);
	}
	
private	String mob="0";
	private String code="";

	public void setMob(String mob1)
	{
		//this.mob = mob;
		sh.edit().putString("CurrentMob",mob1).apply();
	}

	public String getMob()
	{
		return sh.getString("CurrentMob","");
	}

	public void setCode(String code1)
	{
		//this.code = code;
		sh.edit().putString("CurrentCode",code1).apply();
	}

	public String getCode()
	{
		return sh.getString("CurrentCode","");
	}
	
	public void resetall(){
		sh.edit().putString("CurrentMob","").apply();
		sh.edit().putString("CurrentCode","").apply();
	}
	
}
