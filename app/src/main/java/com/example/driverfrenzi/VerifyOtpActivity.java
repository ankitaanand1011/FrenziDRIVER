package com.example.driverfrenzi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.driverfrenzi.api.RestClient;
import com.example.driverfrenzi.api.ServerGeneralResponse;
import com.example.driverfrenzi.responce.ResponseFetchRideHistory;
import com.example.driverfrenzi.responce.ResponseStartRide;

import java.util.Objects;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpActivity  extends AppCompatActivity {

    Button btn_verify;
    ImageView btn_back;
    EditText onecode,twocode,three_code,forth_code;
    private static final String TAG = "OtpPage";
    String ride_id;

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyotp);


        btn_back=findViewById(R.id.btn_back);
        onecode=findViewById(R.id.onecode);
        twocode=findViewById(R.id.twocode);
        three_code=findViewById(R.id.three_code);
        forth_code=findViewById(R.id.forth_code);


        ride_id = getIntent().getStringExtra("ride_id");


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.gradient));
        }

        btn_verify=findViewById(R.id.btn_verify);
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyOtp();

               /* Intent start=new Intent(VerifyOtpActivity.this, DropOffActivity.class);
                startActivity(start);*/
            }
        });
        onecode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                twocode.requestFocus();

            }
        });
        twocode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                three_code.requestFocus();

            }
        });

        three_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                forth_code.requestFocus();

            }
        });
        forth_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
               // verifyOtp();

            }
        });
        onecode.requestFocus();

    }

    private void verifyOtp() {
        SharedPreferences spp = Objects.requireNonNull(getSharedPreferences(Constant.DRIVER_PREF, Context.MODE_PRIVATE));
        String User_Email = spp.getString(Constant.DRIVER_MAIL, "");

        ACProgressFlower dialog = new ACProgressFlower.Builder(VerifyOtpActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.BLACK).build();
        dialog.show();

        String OTP=onecode.getText().toString()+twocode.getText().toString()+three_code.getText().toString()+forth_code.getText().toString();

        if(OTP.length()==4){
            RequestBody rideId = RequestBody.create(MediaType.parse("txt/plain"), ride_id);
            RequestBody Otp = RequestBody.create(MediaType.parse("txt/plain"), OTP);

            RestClient.getClient().OtpVerify(rideId, Otp).enqueue(new Callback<ServerGeneralResponse>() {
                @Override
                public void onResponse(Call<ServerGeneralResponse> call, Response<ServerGeneralResponse> response) {
                    Log.e(TAG, "onResponse: Code :" + response.body());
                    Log.e(TAG, "onResponse: " + response.code());
                    Log.e(TAG, "onResponse: " + response.message());
                    Log.e(TAG, "onResponse: " + response.errorBody());
                    if (response.body().getStatus().equals(200)) {
                        dialog.dismiss();


                      //  Toast.makeText(VerifyOtpActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();


                        startRide(dialog);

                    }else{
                        dialog.dismiss();

                        Toast.makeText(VerifyOtpActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<ServerGeneralResponse> call, Throwable t) {
                    Toast.makeText(VerifyOtpActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        }else {
            Toast.makeText(VerifyOtpActivity.this,"Enter Otp from start", Toast.LENGTH_SHORT).show();

        }



    }

    private void startRide( ACProgressFlower dialog) {

       // dialog.show();
        RequestBody rideId = RequestBody.create(MediaType.parse("txt/plain"), ride_id);


        RestClient.getClient().StartRide(rideId).enqueue(new Callback<ResponseStartRide>() {
            @Override
            public void onResponse(Call<ResponseStartRide> call, Response<ResponseStartRide> response) {
                Log.e(TAG, "onResponse: Code :" + response.body());
                Log.e(TAG, "onResponse: " + response.code());
                Log.e(TAG, "onResponse: " + response.message());
                Log.e(TAG, "onResponse: " + response.errorBody());
                Log.e(TAG, "response.body().getStatus(): " + response.body().getStatus());

                if (response.body().getStatus().equals(200)) {
                 //   dialog.dismiss();

                    String paymentStatus = response.body().getResponse().getPaymentStatus();
                    Log.e(TAG, "onResponse:paymentStatus>>>  "+paymentStatus);
               //     Toast.makeText(VerifyOtpActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent start=new Intent(VerifyOtpActivity.this,DropOffActivity.class);
                    start.putExtra("paymentStatus",paymentStatus);
                    start.putExtra("ride_id",ride_id);
                    start.putExtra("amount",response.body().getResponse().getAmount());
                    start.putExtra("pickup_address",response.body().getResponse().getPickupAddress());
                    startActivity(start);


                }else{
                    Log.e(TAG, "onResponse: else" );
                 //   dialog.dismiss();

                    Toast.makeText(VerifyOtpActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseStartRide> call, Throwable t) {
                Toast.makeText(VerifyOtpActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
              //  dialog.dismiss();
            }
        });




    }
}
