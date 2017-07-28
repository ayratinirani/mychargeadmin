package ir.gorganshaj.admin.Connection;

import java.util.*;
import java.lang.reflect.*;

public class WebRequest
{
	String url="";
	String Method="";
	HashMap <String,Object>params=new HashMap <String,Object>();
	HashMap <String,String> headers=new HashMap <String , String>();
	
	public WebRequest(String url1,String method){
		this.Method=method;
		this.url=url1;
	}

	public void setParams(HashMap<String, Object> params)
	{
		this.params = params;
	}

	public HashMap<String, Object> getParams()
	{
		return params;
	}

	public void setHeaders(HashMap<String, String> headers)
	{
		this.headers = headers;
	}

	public HashMap<String, String> getHeaders()
	{
		return headers;
	}
	
	public void setParameter(String name,Object val){
		params.put(name,val);
	}
	public void setHeader(String name,String val){
		headers.put(name,val);
	}
}
