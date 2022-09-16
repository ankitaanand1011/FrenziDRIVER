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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.driverfrenzi.api.RestClient;
import com.example.driverfrenzi.responce.ResponseJobDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripDetailsActivity extends AppCompatActivity {

    private static final String TAG = "TripDetailsActivity";
    ImageView btn_back;
    Button go_to_home;
    TextView tv_date_time,tv_distance_duration,
            tv_pickup_add_1,tv_pickup_add_2,
            tv_drop_add_1, tv_drop_add_2;

    String rideId;

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        rideId = getIntent().getStringExtra("ride_id");

        btn_back=findViewById(R.id.btn_back);
        tv_date_time=findViewById(R.id.tv_date_time);
        tv_distance_duration=findViewById(R.id.tv_distance_duration);
        tv_pickup_add_1=findViewById(R.id.tv_pickup_add_1);
        tv_pickup_add_2=findViewById(R.id.tv_pickup_add_2);
        tv_drop_add_1=findViewById(R.id.tv_drop_add_1);
        tv_drop_add_2=findViewById(R.id.tv_drop_add_2);



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

        go_to_home=findViewById(R.id.go_to_home);
        go_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gohome=new Intent(TripDetailsActivity.this,JobRequestActivity.class);
                startActivity(gohome);
            }
        });

        FetchRideDetails();
    }

    private void FetchRideDetails() {
        ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(R.color.ForestGreen)
                .fadeColor(Color.WHITE).build();
        dialog.setCancelable(false);
        dialog.show();




        RequestBody RideID = RequestBody.create(MediaType.parse("text/plain"),  rideId);


        RestClient.getClient().FetchRideDetails(RideID).enqueue(new Callback<ResponseJobDetails>() {
            @Override
            public void onResponse(Call<ResponseJobDetails> call, Response<ResponseJobDetails> response) {
                Log.e(TAG, "onResponse 2 : " + response.code());
                Log.e(TAG, "onResponse 2: " + response.isSuccessful());
                // ppDialog.dismiss();
                assert response.body() != null;
                if (response.body().getStatus().equals(200)) {
                    dialog.dismiss();

                    ResponseJobDetails listResponse = response.body();

                   /* tv_cus_name.setText(response.body().getResponse().getUserDetails().getName());
                    tv_cus_rating.setText(response.body().getResponse().getUserDetails().getReviews());
                    tv_payment_mode.setText("Stripe Payment");*/

                    StringTokenizer pickup_tokens = new StringTokenizer(response.body().
                            getResponse().getPickupAddress(), ",");
                    String pickup_first = pickup_tokens.nextToken();// this will contain "12345"
                    String pickup_second = pickup_tokens.nextToken();
                    tv_pickup_add_1.setText(pickup_first);
                    tv_pickup_add_2.setText(pickup_second);

                    StringTokenizer drop_tokens = new StringTokenizer(response.body()
                            .getResponse().getDropAddress(), ",");
                    String drop_first = drop_tokens.nextToken();
                    String drop_second = drop_tokens.nextToken();
                    tv_drop_add_1.setText(drop_first);
                    tv_drop_add_2.setText(drop_second);

                    String date=response.body().getResponse().getStartDate();
                    SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd");
                    Date newDate= null;
                    try {
                        newDate = spf.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    spf= new SimpleDateFormat("MMM dd, yyyy");
                    date = spf.format(newDate);
                    System.out.println(date);

                    tv_date_time.setText(String.valueOf(date)+"  "+ response.body().getResponse().getStartTime());




                    tv_distance_duration.setText(response.body().getResponse().getDistance());






                } else  {

                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseJobDetails> call, Throwable t) {
                Log.e(TAG, "onFailure 2: " + t.getMessage());
                dialog.dismiss();
            }
        });
    }
}