/**
 * Created by Abdul on 18/06/19.
 * Splash screen class
 */
package com.abdul.sqliteassingment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent intent=new Intent(SplashScreen.this,MainActivity.class);
               // animation= AnimationUtils.loadAnimation(SplashScreen.this,R.anim.splashrotation);
                startActivity(intent);
              //  overridePendingTransition(R.anim.splashrotation,R.anim.splashrotation);

            }
        },3000);


        animation= AnimationUtils.loadAnimation(SplashScreen.this,R.anim.splashrotation);
        findViewById(R.id.imageView).setAnimation(animation);
    }
}
