package karshe.app.factpp_v1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {
	private static int SPLASH_TIME_OUT = 3000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new Handler().postDelayed(new Runnable() {
 
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, FactAppStart.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
	}
}