package com.centurylink.mail.action;

import java.net.MalformedURLException;
import java.net.URL;

public class ExtractURLFromString {

	public static String extractURL(String s) {
		StringBuffer stringBuffer = new StringBuffer();
		if(s != null) {
			//Separate input by spaces ( URLs don't have spaces )
	        String [] parts = s.split("\\s");
	        for(String part : parts){
	        	try{
	        		URL url = new URL(part);
	        		if(url != null) {
	        			stringBuffer.append(part+";");
	        		}
	        	}catch (MalformedURLException e) {}
	        }
		}
        return stringBuffer.toString();
	}

}
