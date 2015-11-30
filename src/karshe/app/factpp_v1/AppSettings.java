package karshe.app.factpp_v1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class AppSettings extends Activity {	
	SharedPreferences sharedpreferences;
	Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_settings);
		
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F55347")));
		actionBar.setDisplayShowHomeEnabled(false);
		
		sharedpreferences = getSharedPreferences("FACTPP_SETTINGS", Context.MODE_PRIVATE);	
		editor = sharedpreferences.edit();
		editor.putString("ShakeAction", "true");		
		editor.commit();
		
	}
}