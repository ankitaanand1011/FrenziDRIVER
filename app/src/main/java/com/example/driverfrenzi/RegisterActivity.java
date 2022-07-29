package com.example.driverfrenzi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.driverfrenzi.api.RestClient;
import com.example.driverfrenzi.responce.ResponseRegistration;
import com.google.android.material.snackbar.Snackbar;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;
    Button button_register;
    EditText edt_user_name, edt_user_phone, edt_user_email, edt_user_add, edt_user_postcode,
            edt_user_vehicle_type, edt_user_vehicle_no, edt_user_password, edt_user_license, edt_user_exp, edt_user_ins_number;


    private static final String TAG = "Reg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        button_register = findViewById(R.id.button_register);
        edt_user_name = findViewById(R.id.edt_user_name);

        edt_user_phone = findViewById(R.id.edt_user_phone);
        edt_user_email = findViewById(R.id.edt_user_email);
        edt_user_add = findViewById(R.id.edt_user_add);
        edt_user_postcode = findViewById(R.id.edt_user_postcode);
        edt_user_vehicle_type = findViewById(R.id.edt_user_vehicle_type);
        edt_user_vehicle_no = findViewById(R.id.edt_user_vehicle_no);
        edt_user_password = findViewById(R.id.edt_user_password);

        edt_user_license = findViewById(R.id.edt_user_license);
        edt_user_exp = findViewById(R.id.edt_user_exp);
        edt_user_ins_number = findViewById(R.id.edt_user_ins_number);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registration();
            }
        });
    }
    void showDialogRegistrationDone(Activity activity) {
        final Dialog popupView = new Dialog(activity);
        popupView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popupView.setContentView(R.layout.dialog_done_reg);
        popupView.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupView.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        popupView.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        //  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        TextView  btn_continue;

        btn_continue = popupView.findViewById(R.id.btn_continue);

        btn_continue.setOnClickListener(v -> {
            popupView.dismiss();
//            openBrowser("https://nu-by-js.com/know-more");
            Intent intent=new Intent(RegisterActivity.this,OfflineActivity.class);
            startActivity(intent);
            finish();
        });

        popupView.show();


    }

    private void Registration() {
        if (TextUtils.isEmpty(edt_user_name.getText().toString().trim())) {
            edt_user_name.setError("Please Enter Username");
            return;
        } else if (TextUtils.isEmpty(edt_user_email.getText().toString().trim())) {
            edt_user_email.setError("Please Enter Email");
            return;
        } else if (TextUtils.isEmpty(edt_user_password.getText().toString().trim())) {
            edt_user_password.setError("Please Enter PassWord");
            return;
        } else if (TextUtils.isEmpty(edt_user_phone.getText().toString().trim())) {
            edt_user_phone.setError("Please Enter Mobile Number");
            return;
        } else if (TextUtils.isEmpty(edt_user_vehicle_no.getText().toString().trim())) {
            edt_user_vehicle_no.setError("Please Enter Vehicle Number");
            return;
        } else if (TextUtils.isEmpty(edt_user_vehicle_type.getText().toString().trim())) {
            edt_user_vehicle_type.setError("Please Enter Vehicle Type");
            return;
        } else {
            ACProgressFlower dialog = new ACProgressFlower.Builder(RegisterActivity.this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.BLACK).build();
            dialog.show();


            RequestBody email_id = RequestBody.create(MediaType.parse("txt/plain"), edt_user_name.getText().toString().trim());
            RequestBody Email = RequestBody.create(MediaType.parse("txt/plain"), edt_user_email.getText().toString().trim());
            RequestBody Phone = RequestBody.create(MediaType.parse("txt/plain"), edt_user_phone.getText().toString().trim());
            RequestBody Password = RequestBody.create(MediaType.parse("txt/plain"), edt_user_password.getText().toString().trim());
            RequestBody Address = RequestBody.create(MediaType.parse("txt/plain"), edt_user_add.getText().toString().trim());
            RequestBody Postcode = RequestBody.create(MediaType.parse("txt/plain"), edt_user_postcode.getText().toString().trim());
            RequestBody Vtype = RequestBody.create(MediaType.parse("txt/plain"), edt_user_vehicle_type.getText().toString().trim());
            RequestBody Vnumber = RequestBody.create(MediaType.parse("txt/plain"), edt_user_vehicle_no.getText().toString().trim());
            RequestBody License = RequestBody.create(MediaType.parse("txt/plain"), edt_user_license.getText().toString().trim());
            RequestBody LIC_exp = RequestBody.create(MediaType.parse("txt/plain"), edt_user_exp.getText().toString().trim());
            RequestBody InsNo = RequestBody.create(MediaType.parse("txt/plain"), edt_user_ins_number.getText().toString().trim());


            RestClient.getClient().signUp(email_id, Email, Phone, Password, Address, Postcode, Vtype, Vnumber, License, LIC_exp, InsNo).enqueue(new Callback<ResponseRegistration>() {
                @Override
                public void onResponse(Call<ResponseRegistration> call, Response<ResponseRegistration> response) {
                    Log.e(TAG, "onResponse: Code :" + response.body());
                    Log.e(TAG, "onResponse: " + response.code());
                    Log.e(TAG, "onResponse: " + response.message());
                    Log.e(TAG, "onResponse: " + response.errorBody());
                    assert response.body() != null;
                    if (response.body().getStatus().equals(200)) {
                        dialog.dismiss();


                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, response.body().getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();


//                      SAVE  DATA TO LOCAL STORAGE
                        SharedPreferences sp = getSharedPreferences(Constant.DRIVER_PREF, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        assert response.body() != null;

                        editor.putString(Constant.DRIVER_NAME, response.body().getResponse().getDriverName());
                        editor.putString(Constant.DRIVER_ID, String.valueOf(response.body().getResponse().getDriverId()));
                        editor.putString(Constant.DRIVER_ADDRESS, response.body().getResponse().getAddress());
                        editor.putString(Constant.DRIVER_MAIL, response.body().getResponse().getEmail());
                        editor.apply();
                       showDialogRegistrationDone(RegisterActivity.this);




                    } else {
                        dialog.dismiss();
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, response.body().getMessage(), Snackbar.LENGTH_LONG);

                        snackbar.show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseRegistration> call, Throwable t) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, t.getMessage(), Snackbar.LENGTH_LONG);

                    snackbar.show();
                    dialog.dismiss();
                }
            });
        }
    }
}