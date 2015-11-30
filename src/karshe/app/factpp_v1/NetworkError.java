package karshe.app.factpp_v1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class NetworkError extends Activity {	
	boolean activeMenu = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.network_error);
		
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F55347")));
		actionBar.setDisplayShowHomeEnabled(false);
			
	}
	
	/* MENU INFLATOR */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fact_screen, menu);
		return true;
	}
	
	/** MENU DESIGN **/
	 public boolean onOptionsItemSelected(MenuItem item)
	    {
		 	Intent i;
	        switch (item.getItemId())
	        {
	        case R.id.star_it:
	            
	        	if(activeMenu==true){

		            Toast.makeText(NetworkError.this, "You can never reach here.", Toast.LENGTH_SHORT).show();
		            
	        	}else{

		            Toast.makeText(NetworkError.this, "Uuh! Is any fact there? Retry when you got some packets to fly in air!", Toast.LENGTH_SHORT).show();
	        	}
	        	
	            return true;
	            
	        case R.id.share_whatsapp:
	        	
	        	if(activeMenu==true){

	        		String fact_share = "I just checked an awesome fact at Factpp -  \n"+"\n\nDownload app from http://utkarshkumarraut.in/factpp/ now!";
		        	Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
		        	whatsappIntent.setType("text/plain");
		        	whatsappIntent.setPackage("com.whatsapp");
		        	whatsappIntent.putExtra(Intent.EXTRA_TEXT, fact_share);
		        	
		        	try {
		        	    startActivity(whatsappIntent);
		        	} catch (android.content.ActivityNotFoundException ex) {
		        		Toast.makeText(NetworkError.this, "WhatsApp is not Installed!", Toast.LENGTH_SHORT).show();
		        	}
		            
	        	}else{

		            Toast.makeText(NetworkError.this, "Uuh! Is any fact there? Retry when you got some packets to fly in air!", Toast.LENGTH_SHORT).show();
	        	}
	        	
	        	return true;
	        	
	        case R.id.app_settings:
	        	i = new Intent(NetworkError.this, AppSettings.class);
               startActivity(i);
	        	return true;
	        	
	        case R.id.about_app:
	        	i = new Intent(NetworkError.this, AboutApp.class);
               startActivity(i);
	        	return true;
	        
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }
}