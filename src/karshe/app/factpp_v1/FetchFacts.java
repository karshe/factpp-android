package karshe.app.factpp_v1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;



public class FetchFacts{
	String fact_id;
	String fact;
	String fact_author;
	
	FetchFacts(){
		JSONObject json;
		try {
			json = new JSONObject(readUrl("http://utkarshkumarraut.in/factpp/android/fact_api.php"));
			fact_id = (String) json.get("id");
			fact = (String) json.get("fact");
			fact_author = (String) json.get("author");
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
 public String f_id(){
	 return fact_id;
 }
 
 public String fact(){
	 return fact;
 }
 
 public String fact_author(){
	 return fact_author;
 }

private static String readUrl(String urlString) throws Exception {
    BufferedReader reader = null;
    try {
        URL url = new URL(urlString);
        reader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuffer buffer = new StringBuffer();
        int read;
        char[] chars = new char[1024];
        while ((read = reader.read(chars)) != -1)
            buffer.append(chars, 0, read); 

        return buffer.toString();
    } finally {
        if (reader != null)
            reader.close();
    }
 }
}
