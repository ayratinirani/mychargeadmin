package ir.gorganshaj.admin.Connection;

import android.os.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class mHttpconnection extends AsyncTask<WebRequest,Void,String>
{
    WebRequest request;
	String result=null;
	URL mUrl;
	HttpURLConnection con;
	@Override
	protected String doInBackground(WebRequest...p1)
	{
		this.request=(WebRequest)p1[0];
		try
		{
			this.mUrl = new URL(request.url);
			con=(HttpURLConnection) mUrl.openConnection();
			con.setRequestMethod(request.Method);
	       con.setDoInput(true);
		   if(request.Method.equals("POST")){
			   con.setDoOutput(true);
		   }
			//con.getRequestProperties().putAll(request.headers);
			if(request.headers !=null){
				Iterator it = request.headers.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry)it.next();

					//System.out.println(pair.getKey() + " = " + pair.getValue());
					con.setRequestProperty((String)pair.getKey(),(String)pair.getValue());
					it.remove(); // avoids a ConcurrentModificationException
				}
			}
			StringBuilder postData = new StringBuilder();
			
			if(request.getParams()!=null){
				
				for (Map.Entry<String,Object> param : request.getParams().entrySet()) {
					if (postData.length() != 0) postData.append('&');
					postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
					postData.append('=');
					postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
				}
				byte[] postDataBytes = postData.toString().getBytes("UTF-8");

				con.setDoOutput(true);
				con.getOutputStream().write(postDataBytes);

			}
			con.connect();
			InputStream in= con.getInputStream();
			
			//return readStream(in);
			//result = in.toString();
			result=readStream(in);
			return result;
		}
		catch (Exception e)
		{
			result=e.getMessage();
			e.printStackTrace();
		}

		// TODO: Implement this method
		return result;
	}
	private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

}
