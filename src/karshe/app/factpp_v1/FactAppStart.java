package karshe.app.factpp_v1;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

public class FactAppStart extends Activity {
	
	final Context context = this;
	ProgressDialog loadingFactDialog;
	String[] fact = new String[3];
	Button nextFactBtn;
	TextView factBody;
	TextView factAuthor;
	TextView factID;
	boolean activeMenu = false;
	
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;
	
	/** IS NETWORK AVAILABLE?? **/
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fact_screen);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
		
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F55347")));
		actionBar.setDisplayShowHomeEnabled(false);
		
		/* IN CASE INTERNET NOT AVAIL */
		
		if(!isNetworkAvailable()){
			Intent i = new Intent(FactAppStart.this, NetworkError.class);
            startActivity(i);
            finish();
		}else{
			nextFactBtn = (Button) findViewById(R.id.nextFactBtn);
			nextFactBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					RandomLoading rl = new RandomLoading();
					loadingFactDialog = ProgressDialog.show(FactAppStart.this, "Packets now traveling, be patient.",rl.giveMessage(), true);
					
					new Handler().postDelayed(new Runnable() {
			            @Override
			            public void run() {
			                loadFacts();
			            }
			        }, 1000);
					
				}
			});
			
			/* SHAKER */
			 mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
			    mSensorListener = new ShakeEventListener();   

			    mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

			      public void onShake() {
			    	  loadFacts();
			      }
			    });
			/* ends */
			
		}		
	}	
	
	@Override
	  protected void onResume() {
	    super.onResume();
	    mSensorManager.registerListener(mSensorListener,
	        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	        SensorManager.SENSOR_DELAY_UI);
	  }

	  @Override
	  protected void onPause() {
	    mSensorManager.unregisterListener(mSensorListener);
	    super.onPause();
	  }
	  
	  
	/* MENU INFLATOR */
	private ShareActionProvider mShareActionProvider;
	Intent sharingIntent;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.fact_screen, menu);
		MenuItem item = menu.findItem(R.id.menu_item_share);
	    // Fetch and store ShareActionProvider
		mShareActionProvider = (ShareActionProvider) item.getActionProvider();		
		
		/* INFLATE SHARE BUTTON */
		String shareBody = "Awesome facts delivered by Factpp!";				
		sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
    	sharingIntent.setType("text/plain");
    	sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Shared via Factpp");
    	sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
    	mShareActionProvider.setShareIntent(sharingIntent);
		return true;
	}
	
	/*** LOADING FACT **/
	public void loadFacts(){
		
		FetchFacts fetcher = new FetchFacts();
		
		factBody = (TextView) findViewById(R.id.factContent);
		factID = (TextView) findViewById(R.id.factID);
		factAuthor = (TextView) findViewById(R.id.factAuthor);
		
		factID.setText("Fact #"+fetcher.fact_id);
		factBody.setText(Html.fromHtml(fetcher.fact));
		factAuthor.setText(fetcher.fact_author);
		
		activeMenu = true;
		loadingFactDialog.dismiss(); 
	}

	
	/*** STARRING FACT **/
	public void starFact(int id){
		new AlertDialog.Builder(context)
	    .setTitle("Delete entry")
	    .setMessage("Are you sure you want to delete this entry?")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();		
	}
	
	/** MENU DESIGN **/
	 public boolean onOptionsItemSelected(MenuItem item)
	    {
		 	Intent i;
	        switch (item.getItemId())
	        {
	        case R.id.star_it:
	            
	        	if(activeMenu==true){

		            Toast.makeText(FactAppStart.this, "Okay I will star "+factID.getText(), Toast.LENGTH_SHORT).show();
		            
	        	}else{

		            Toast.makeText(FactAppStart.this, "Uuh! Is any fact there? Click the button!", Toast.LENGTH_SHORT).show();
	        	}
	        	
	            return true;
	            
	        case R.id.share_whatsapp:
	        	
	        	if(activeMenu==true){

	        		String fact_share = "I just checked an awesome fact at Factpp -  \n"+factBody.getText()+"\n\nDownload app from http://utkarshkumarraut.in/factpp/ now!";
		        	Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
		        	whatsappIntent.setType("text/plain");
		        	whatsappIntent.setPackage("com.whatsapp");
		        	whatsappIntent.putExtra(Intent.EXTRA_TEXT, fact_share);
		        	
		        	try {
		        	    startActivity(whatsappIntent);
		        	} catch (android.content.ActivityNotFoundException ex) {
		        		Toast.makeText(FactAppStart.this, "WhatsApp is not Installed!", Toast.LENGTH_SHORT).show();
		        	}
		            
	        	}else{

		            Toast.makeText(FactAppStart.this, "Uuh! Is any fact there? Click the button!", Toast.LENGTH_SHORT).show();
	        	}
	        	
	        	return true;
	        	
	        case R.id.app_settings:
	        	i = new Intent(FactAppStart.this, AppSettings.class);
                startActivity(i);
	        	return true;
	        	
	        case R.id.about_app:
	        	i = new Intent(FactAppStart.this, AboutApp.class);
                startActivity(i);
	        	return true;
	        
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }
}