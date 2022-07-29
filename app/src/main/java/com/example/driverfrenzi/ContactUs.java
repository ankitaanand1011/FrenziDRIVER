package com.example.driverfrenzi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.driverfrenzi.api.RestClient;
import com.example.driverfrenzi.api.ServerGeneralResponse;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUs extends AppCompatActivity {
    private static final String TAG = "ContactUsActivity";

    ImageView img_close;
    EditText edt_full_name,edt_email,edt_contact,edt_message;
    TextView tv_submit;

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.gradient));
        }

        edt_full_name=findViewById(R.id.edt_full_name);
        edt_email=findViewById(R.id.edt_email);
        edt_contact=findViewById(R.id.edt_contact);
        edt_message=findViewById(R.id.edt_message);
        tv_submit=findViewById(R.id.tv_submit);

        img_close=findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact_us();
            }
        });


    }

    private void contact_us() {
        if(TextUtils.isEmpty(edt_full_name.getText().toString().trim())) {
            edt_full_name.setError("Please Enter Full name");
            return;
        }else if(TextUtils.isEmpty(edt_email.getText().toString().trim())) {
            edt_email.setError("Please Enter Email");
            return;
        }else if(TextUtils.isEmpty(edt_contact.getText().toString().trim())) {
            edt_contact.setError("Please Enter Contact Number");
            return;
        }else if(TextUtils.isEmpty(edt_message.getText().toString().trim())) {
            edt_message.setError("Please Enter Message");
            return;
        }else {

            ACProgressFlower dialog = new ACProgressFlower.Builder(ContactUs.this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.BLACK).build();
            dialog.show();


            RequestBody full_name = RequestBody.create(MediaType.parse("txt/plain"), edt_full_name.getText().toString().trim());
            RequestBody email = RequestBody.create(MediaType.parse("txt/plain"), edt_email.getText().toString().trim());
            RequestBody contact = RequestBody.create(MediaType.parse("txt/plain"), edt_contact.getText().toString().trim());
            RequestBody message = RequestBody.create(MediaType.parse("txt/plain"), edt_message.getText().toString().trim());


            RestClient.getClient().ContactUs(full_name,email,contact,message).enqueue(new Callback<ServerGeneralResponse>() {
                @Override
                public void onResponse(Call<ServerGeneralResponse> call, Response<ServerGeneralResponse> response) {
                    Log.e(TAG, "onResponse: Code :" + response.body());
                    Log.e(TAG, "onResponse: " + response.code());
                    Log.e(TAG, "onResponse: " + response.message());
                    Log.e(TAG, "onResponse: " + response.errorBody());

                    assert response.body() != null;
                    if (response.body().getStatus().equals(200)) {
                        dialog.dismiss();
                        edt_full_name.setText("");
                        edt_email.setText("");
                        edt_contact.setText("");
                        edt_message.setText("");
                        Toast.makeText(ContactUs.this,response.body().getMessage(),Toast.LENGTH_LONG).show();


                    } else {
                        dialog.dismiss();
//

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