package ir.gorganshaj.admin;
import android.content.*;
import android.telephony.*;
import android.widget.*;
import android.util.*;
import android.os.*;
import ir.gorganshaj.admin.Connection.*;

public class IncomingSms extends BroadcastReceiver
{   String shomareKhadamat=".IRANCELL.";
      SmsManager smsManager;
	  SharedPreferences appsettings;
	  Context Context;
	@Override
	public void onReceive(Context context, Intent intent)
	{
		this.Context=context;
		smsManager=SmsManager.getDefault();
		// Retrieves a map of extended data from the intent.
		final Bundle bundle = intent.getExtras();
          
		try {

			if (bundle != null) {

				final Object[] pdusObj = (Object[]) bundle.get("pdus");

				for (int i = 0; i < pdusObj.length; i++) {

					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage.getDisplayOriginatingAddress();

					String senderNum = phoneNumber;
					String message = currentMessage.getDisplayMessageBody();

					Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);
                      /*
						 */
					 if(senderNum.contains(shomareKhadamat)){
						 Intent intent2=new Intent(context,SmsRes.class);
						 intent2.putExtra("message",message);
						 intent2.putExtra("mobile",senderNum);
						 context.startActivity(intent2);
						 
					 }
					// Show alert
					int duration = Toast.LENGTH_LONG;
					Toast toast = Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, Toast.LENGTH_LONG);
					toast.show();

				} // end for loop
			} // bundle is null

		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" +e);

		}
		}
		
	
		
		// TODO: Implement this method
	
	
	
}
