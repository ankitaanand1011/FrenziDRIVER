package com.example.driverfrenzi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.driverfrenzi.api.RestClient;
import com.example.driverfrenzi.responce.ResponseAboutUs;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Aboutus extends AppCompatActivity {

    private static final String TAG = "AboutUsActivity";
    ImageView img_close;
    TextView tv_description, txt_about;

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.gradient));
        }

        tv_description=findViewById(R.id.tv_description);
        txt_about=findViewById(R.id.txt_about);

        img_close=findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        about_us();






    }


    private void about_us() {

        ACProgressFlower dialog = new ACProgressFlower.Builder(Aboutus.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.BLACK).build();
        dialog.show();


        RequestBody body = RequestBody.create(MediaType.parse("txt/plain"), "body");


        RestClient.getClient().AboutUs(body).enqueue(new Callback<ResponseAboutUs>() {
            @Override
            public void onResponse(Call<ResponseAboutUs> call, Response<ResponseAboutUs> response) {
                Log.e(TAG, "onResponse: Code :" + response.body());
                Log.e(TAG, "onResponse: " + response.code());
                Log.e(TAG, "onResponse: " + response.message());
                Log.e(TAG, "onResponse: " + response.errorBody());

                assert response.body() != null;
                if (response.body().getStatus().equals(200)) {
                    dialog.dismiss();

                    tv_description.setText(response.body().getResponse().getMain_description());
                    txt_about.setText(response.body().getResponse().getTitle());


                }else{
                    dialog.dismiss();
//

                }
            }

            @Override
            public void onFailure(Call<ResponseAboutUs> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }
}