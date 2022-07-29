package com.example.driverfrenzi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    final private static int SPLASH_TIME_OUT = 3000;
    String TAG = "MainActivity";

    Button start_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.gradient));
        }

        SharedPreferences spp = Objects.requireNonNull(getSharedPreferences(Constant.DRIVER_PREF, Context.MODE_PRIVATE));
        String User_ID = spp.getString(Constant.DRIVER_ID, "");
        Log.e(TAG, "onCreate: "+User_ID);

        start_btn=findViewById(R.id.start_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!User_ID.equalsIgnoreCase("")){
                    Intent intent2 = new Intent(MainActivity.this,OfflineActivity.class);
                    startActivity(intent2);
                    finish();
                }else {
                    Intent intent2 = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent2);
                    finish();
                }


            }
        });

//        ImageView img_loader=findViewById(R.id.img_loader);
//        Glide.with(this)
//                .asGif()
//                .load(R.drawable.caranim) //or url
//                .into(new SimpleTarget<GifDrawable>() {
//                    @Override
//                    public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
//
//                        resource.start();
//                        //resource.setLoopCount(1);
//                        img_loader.setImageDrawable(resource);
//                    }
//                });
//        new Handler().postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//            @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//
////                SharedPreferences prefs = getSharedPreferences(USER_PREF_DATA, MODE_PRIVATE);
////                Log.d(TAG, "onCreate: " + prefs.getBoolean(USER_PREF_PHONE_USER_IS_LOGGED_IN, false));
////                boolean isLoggedIn = prefs.getBoolean(USER_PREF_PHONE_USER_IS_LOGGED_IN, false);
////                if (isLoggedIn) {
//
//                Intent intent2 = new Intent(SplashActivity.this.getApplicationContext(), MainActivity.class);
////                    Log.d(TAG, "Launching Chat Fragment: " + prefs.getBoolean(USER_PREF_PHONE_USER_IS_LOGGED_IN, false));
//                startActivity(intent2);
//                finish();
////                    return;
////                } else {
////                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
////                    startActivity(i);
////                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
////                    // close this activity
////                    finish();
//
//
//
//
//            }
//        }, SPLASH_TIME_OUT);
    }
}