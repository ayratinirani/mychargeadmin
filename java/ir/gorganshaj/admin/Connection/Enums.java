package ir.gorganshaj.admin.Connection;
import java.util.*;
import android.database.sqlite.*;
import android.content.*;
import android.database.*;

public  class Enums
{
	
	
	SQLiteDatabase DB;
	public Enums(Context context){
		codes.clear();
		DB=context.openOrCreateDatabase("CodesDB", Context.MODE_PRIVATE, null);
		Cursor resultSet = DB.rawQuery("Select * from ussds",null);
		while(resultSet.moveToNext()){
		codes.put(resultSet.getString(0),resultSet.getString(1));
		}
	}
	HashMap<String,String>codes= new HashMap();
	
	public  String get(String input){
		if(codes.get(input)==null){
			return "";
		}
		return codes.get(input);
	}
}
