package com.example.driverfrenzi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.driverfrenzi.api.RestClient;
import com.example.driverfrenzi.api.ServerGeneralResponse;
import com.example.driverfrenzi.responce.FPAssResponse;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText edt_email,edt_otp_code,edt_password;
    Button btn_cancel,btn_done;
    private static final String TAG = "LoginActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        initControls();
    }

    private void initControls() {


        edt_email = findViewById(R.id.edt_email);
        edt_otp_code = findViewById(R.id.edt_otp_code);
        edt_password = findViewById(R.id.edt_password);
        btn_done = findViewById(R.id.btn_done);
        btn_cancel = findViewById(R.id.btn_cancel);


        functions();

    }

    private void functions() {
        String email = getIntent().getStringExtra("email_id");
        edt_email.setText(email);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void resetPassword( ) {
        if(TextUtils.isEmpty(edt_email.getText().toString().trim())) {
            edt_email.setError("Please Enter Email");
            return;
        }else if(TextUtils.isEmpty(edt_otp_code.getText().toString().trim())) {
            edt_otp_code.setError("Please Enter OTP");
            return;
        }else if(TextUtils.isEmpty(edt_password.getText().toString().trim())) {
            edt_password.setError("Please Enter Password");
            return;
        }else {
            ACProgressFlower dialog = new ACProgressFlower.Builder(ResetPasswordActivity.this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.BLACK).build();
            dialog.show();


            RequestBody email = RequestBody.create(MediaType.parse("txt/plain"), edt_email.getText().toString().trim());
            RequestBody otp_code = RequestBody.create(MediaType.parse("txt/plain"), edt_otp_code.getText().toString().trim());
            RequestBody new_password = RequestBody.create(MediaType.parse("txt/plain"), edt_password.getText().toString().trim());

            RestClient.getClient().ResetPassword(email,otp_code,new_password).enqueue(new Callback<ServerGeneralResponse>() {
                @Override
                public void onResponse(Call<ServerGeneralResponse> call, Response<ServerGeneralResponse> response) {
                    Log.e(TAG, "onResponse: Code :" + response.body());
                    Log.e(TAG, "onResponse: " + response.code());
                    Log.e(TAG, "onResponse: " + response.errorBody());
                    if (!String.valueOf(response.code()).equals("500")) {
                        if (response.body().getStatus().equals(200)) {
                            dialog.dismiss();

                          //  Log.e(TAG, "onResponse code: "+response.body().getResponse() );


                            Toast.makeText(ResetPasswordActivity.this,response.body().getMessage(), Toast.LENGTH_LONG).show();
                            finish();



                        } else {
                            dialog.dismiss();
//

                        }
                    }else{
                        dialog.dismiss();
                        Toast.makeText(ResetPasswordActivity.this,"Something went wrong !!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ServerGeneralResponse> call, Throwable t) {

                    dialog.dismiss();
                }
            });
        }

    }
}
