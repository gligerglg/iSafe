package apps.gliger.isafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Date;

public class SplashScreen extends AppCompatActivity {

    private boolean isFirsttime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        final SharedPreferences sharedPref = getSharedPreferences("iSafe_settings", 0);
        final SharedPreferences.Editor editor = sharedPref.edit();
        isFirsttime = sharedPref.getBoolean("intro", true);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent;
                    if(isFirsttime) {
                        intent = new Intent(SplashScreen.this, Intro.class);
                        editor.putBoolean("intro",false);
                        editor.commit();
                    }
                    else{
                        intent = new Intent(SplashScreen.this,LoginActivity.class);
                    }

                    startActivity(intent);
                }
            }
        };

        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
