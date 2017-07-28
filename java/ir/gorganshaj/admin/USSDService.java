
import android.view.accessibility.*;
import android.util.*;
import java.util.*;
import android.text.*;
import android.accessibilityservice.*;
import ir.gorganshaj.admin.*;
import android.content.*;
import android.widget.*;
public class USSDService extends AccessibilityService
 {

	public static String TAG = USSDService.class.getSimpleName();

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		Log.d(TAG, "onAccessibilityEvent");
		Toast.makeText(this,"",Toast.LENGTH_LONG).show();

		AccessibilityNodeInfo source = event.getSource();
		 if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && !event.getClassName().equals("android.app.AlertDialog")) { // android.app.AlertDialog is the standard but not for all phones
		return;
		 }
		if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && !String.valueOf(event.getClassName()).contains("AlertDialog")) {
			return;
		}
		if(event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED && (source == null || !source.getClassName().equals("android.widget.TextView"))) {
			return;
		}
		if(event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED && TextUtils.isEmpty(source.getText())) {
			return;
		}

		List<CharSequence> eventText;

		if(event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
			eventText = event.getText();
		} else {
			eventText = Collections.singletonList(source.getText());
		}

		String text = processUSSDText(eventText);

		if( TextUtils.isEmpty(text) ){
			Toast.makeText(this,"",Toast.LENGTH_LONG).show();
			
			return;}

		// Close dialog
		performGlobalAction(GLOBAL_ACTION_BACK); // This works on 4.1+ only
          Intent intent=new Intent(this,SmsRes.class);
		  intent.putExtra("message",text);
		 // intent.putExtra("mobile","0");
		 // this.startActivity(intent);
		Log.d(TAG, text);
		// Handle USSD response here
		Toast.makeText(this,text,Toast.LENGTH_LONG).show();

	}

	private String processUSSDText(List<CharSequence> eventText) {
		for (CharSequence s : eventText) {
			String text = String.valueOf(s);
			// Return text if text is the expected ussd response
			if( true ) {
				return text;
			}
		}
		return null;
	}

	@Override
	public void onInterrupt() {
	}

	@Override
	protected void onServiceConnected() {
		super.onServiceConnected();
		Log.d(TAG, "onServiceConnected");
		Toast.makeText(this,"service connected",Toast.LENGTH_LONG).show();
		AccessibilityServiceInfo info = new AccessibilityServiceInfo();
		info.flags = AccessibilityServiceInfo.DEFAULT;
		info.packageNames = new String[]{"com.android.phone"};
		info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
		info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
		setServiceInfo(info);
	}
}
