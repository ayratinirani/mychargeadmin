package ir.gorganshaj.admin;
import android.support.v7.app.*;
import android.os.*;
import android.database.sqlite.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import java.util.*;
import android.database.*;
import android.widget.AdapterView.*;

public class Tarif extends AppCompatActivity
{  EditText number,codesd;
   ListView lv;
   ArrayList<String>list;
   ArrayAdapter Adapter;
   SQLiteDatabase DB;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ly_tarif);
		DB=openOrCreateDatabase("CodesDB", Context.MODE_PRIVATE, null);
         getSupportActionBar().setHomeButtonEnabled(true);
		 
		DB.execSQL("CREATE TABLE IF NOT EXISTS ussds(_id VARCHAR,code VARCHAR);");
		//	DB.execSQL("TRUNCATE TABLE users;");
		codesd=(EditText) findViewById(R.id._tarif_ussd);
		number=(EditText)findViewById(R.id._tarif_num);
		lv=(ListView) findViewById(R.id.ly_tarifListView);
		list=new ArrayList <String>();
		list.add("کدها");
		Adapter=new ArrayAdapter(this,
								 android.R.layout.simple_list_item_1, android.R.id.text1, list);
		
		lv.setAdapter(Adapter);
		lv.setOnItemLongClickListener(
			new OnItemLongClickListener(){

				@Override
				public boolean onItemLongClick(AdapterView<?> p1, View p2, int position, long p4)
				{
					// TODO: Implement this method
					String name=list.get(position);
					String id=name.split(" ")[0];
					Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
					
					//DB.rawQuery("DELETE FROM ussds WHERE _id=?",new String[]{id});
					DB.delete("ussds","_id=?",new String[]{id});
					
					getListOf();
					
					return true;
				}
				
			
		}
		);
		getListOf();
	}
	public void sabt(View v){
		ContentValues initialValues = new ContentValues();
		if (number.getText().toString().equals("")||codesd.getText().equals("")){
			return;
		}
		String numm=number.getText().toString();
		String coodd=codesd.getText().toString();
		initialValues.put("_id", numm); // the execution is different if _id is 2

		initialValues.put("code", coodd);

		
		DB.insertWithOnConflict("ussds", null, initialValues, SQLiteDatabase.CONFLICT_REPLACE);
	number.setText("");
	codesd.setText("");
		
		getListOf();
	
		
	}

	@Override
	public void onBackPressed()
	{
		Intent min= new Intent(this,MainActivity.class);
		startActivity(min);
		finish();
		// TODO: Implement this method
		super.onBackPressed();
	}
	
	public void getListOf(){
		list.clear();
		DB=openOrCreateDatabase("CodesDB", Context.MODE_PRIVATE, null);
		Cursor resultSet = DB.rawQuery("Select * from ussds",null);
		while(resultSet.moveToNext()){
			list.add(resultSet.getString(0)+" "+resultSet.getString(1));
		
	}
	Adapter.notifyDataSetChanged();
	
}

	
}
