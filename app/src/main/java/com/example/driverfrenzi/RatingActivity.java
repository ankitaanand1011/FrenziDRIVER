package com.example.driverfrenzi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.driverfrenzi.api.RestClient;

import com.example.driverfrenzi.api.ServerGeneralResponse;
import com.mikhaellopez.circularimageview.CircularImageView;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingActivity extends AppCompatActivity {
    private static final String TAG = "RatingActivity";
    Button btn_rating_submit;
    ImageView btn_back;
    CircularImageView civ_driver;
    TextView tv_thank, tv_riding, tv_skip;
    RatingBar rating;
    EditText edt_reviews;
    String rideId ,userId,driverId;


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);


        rideId = getIntent().getStringExtra("ride_id");
        userId = getIntent().getStringExtra("user_id");
        driverId = getIntent().getStringExtra("driver_id");

        btn_back=findViewById(R.id.btn_back);
        tv_skip=findViewById(R.id.tv_skip);
        rating=findViewById(R.id.rating);
        edt_reviews=findViewById(R.id.edt_reviews);
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
            window.setStatusBarColor(getResources().getColor(R.color.MidnightBlue));
        }
        btn_rating_submit=findViewById(R.id.btn_rating_submit);
        btn_rating_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(TAG, "onClick: rating"+String.valueOf(rating.getRating()) );
                // call API driver rating
                rateCustomer();
            }
        });

        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent navi=new Intent(RatingActivity.this,TripDetailsActivity.class);
                navi.putExtra("ride_id",rideId);
                startActivity(navi);

                finish();
            }
        });


    }
    private void rateCustomer() {
       /* if(TextUtils.isEmpty(edt_email.getText().toString().trim())) {
            edt_email.setError("Please Enter Email");
            return;
        }else if(TextUtils.isEmpty(edt_otp_code.getText().toString().trim())) {
            edt_otp_code.setError("Please Enter OTP");
            return;
        }else if(TextUtils.isEmpty(edt_password.getText().toString().trim())) {
            edt_password.setError("Please Enter Password");
            return;
        }else {*/



        Log.e(TAG, " rideId"+rideId );
        Log.e(TAG, " userId"+userId );
        Log.e(TAG, " driverId"+driverId );


        ACProgressFlower dialog = new ACProgressFlower.Builder(RatingActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.BLACK).build();
        dialog.show();


        RequestBody user_id = RequestBody.create(MediaType.parse("txt/plain"),userId );
        RequestBody driver_id = RequestBody.create(MediaType.parse("txt/plain"), driverId);
        RequestBody ride_id = RequestBody.create(MediaType.parse("txt/plain"), rideId);
        RequestBody ratings = RequestBody.create(MediaType.parse("txt/plain"), String.valueOf(rating.getRating()));
        RequestBody review_comment = RequestBody.create(MediaType.parse("txt/plain"), edt_reviews.getText().toString());
        RequestBody tipamount = RequestBody.create(MediaType.parse("txt/plain"), "0");

        RestClient.getClient().DriverReviews(user_id, driver_id, ride_id, ratings,
                review_comment, tipamount).enqueue(new Callback<ServerGeneralResponse>() {
            @Override
            public void onResponse(Call<ServerGeneralResponse> call, Response<ServerGeneralResponse> response) {
                Log.e(TAG, "onResponse: Code :" + response.body());
                Log.e(TAG, "onResponse: " + response.code());
                Log.e(TAG, "onResponse: " + response.errorBody());
                if (!String.valueOf(response.code()).equals("500")) {
                    if (response.body().getStatus().equals(200)) {
                        dialog.dismiss();

                        //  Log.e(TAG, "onResponse code: "+response.body().getResponse() );


                        Toast.makeText(RatingActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent navi = new Intent(RatingActivity.this, TripDetailsActivity.class);
                        navi.putExtra("ride_id",rideId);
                        startActivity(navi);
                        finish();


                    } else {
                        dialog.dismiss();
//

                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(RatingActivity.this, "Something went wrong !!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerGeneralResponse> call, Throwable t) {

                dialog.dismiss();
            }
        });
    }

}