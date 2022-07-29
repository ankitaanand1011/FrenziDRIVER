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
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.StringTokenizer;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailsActivity extends AppCompatActivity {

    private static final String TAG = "JobDetailsActivity";
    TextView tv_ride_id,tv_cus_name,tv_cus_rating,tv_payment_mode,tv_amount,tv_distance,
            tv_pickup_add_1,tv_pickup_add_2,tv_drop_add_1,tv_drop_add_2,farecost_amount,
            discount_amount,paidamount_val;
    String pickup_lat,pickup_long,drop_lat,drop_long;
    Button go_to_pickup;
    ImageView btn_back;
    String ride_id;
    CircularImageView ci_cus_image;
    double current_lat, current_long;
    double  pick_lat,pick_long;
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.MidnightBlue));
        }


        initControls();
    }

    private void initControls() {

        btn_back=findViewById(R.id.btn_back);
        go_to_pickup=findViewById(R.id.go_to_pickup);
        ci_cus_image=findViewById(R.id.ci_cus_image);
        tv_ride_id=findViewById(R.id.tv_ride_id);
        tv_cus_name=findViewById(R.id.tv_cus_name);
        tv_cus_rating=findViewById(R.id.tv_cus_rating);
        tv_payment_mode=findViewById(R.id.tv_payment_mode);
        tv_amount=findViewById(R.id.tv_amount);
        tv_distance=findViewById(R.id.tv_distance);
        tv_pickup_add_1=findViewById(R.id.tv_pickup_add_1);
        tv_pickup_add_2=findViewById(R.id.tv_pickup_add_2);
        tv_drop_add_1=findViewById(R.id.tv_drop_add_1);
        tv_drop_add_2=findViewById(R.id.tv_drop_add_2);
        farecost_amount=findViewById(R.id.farecost_amount);
        discount_amount=findViewById(R.id.discount_amount);
        paidamount_val=findViewById(R.id.paidamount_val);



        functions();

    }

    private void functions() {

        ride_id = getIntent().getStringExtra("ride_id");
        current_lat = getIntent().getDoubleExtra("current_lat",0.0);
        current_long = getIntent().getDoubleExtra("current_long",0.0);

        Log.e(TAG, "onCreate: ride_id >> "+ride_id );
        Log.e(TAG, "onCreate: current_lat >> "+current_lat );
        Log.e(TAG, "onCreate: current_long >> "+current_long );

        tv_ride_id.setText("#"+ride_id);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        go_to_pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent pick=new Intent(JobDetailsActivity.this,PickupActivity.class);
                pick.putExtra("pickup_lat",pick_lat);
                pick.putExtra("pickup_long",pick_long);
                pick.putExtra("current_lat",current_lat);
                pick.putExtra("current_long",current_long);
                pick.putExtra("ride_id",ride_id);
                startActivity(pick);

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




        RequestBody RideID = RequestBody.create(MediaType.parse("text/plain"), ride_id);


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

                    tv_cus_name.setText(response.body().getResponse().getUserDetails().getName());
                    tv_cus_rating.setText(response.body().getResponse().getUserDetails().getReviews());
                    tv_payment_mode.setText("Stripe Payment");

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


                    //user_id = response.body().getResponse().getUserId();
                  //  driver_id = response.body().getResponse().getDriverId();
                  //  pickup_address = response.body().getResponse().getPickupAddress();
                   // drop_address = response.body().getResponse().getDropAddress();

                    String  pickup_latlng = response.body().getResponse().getPickupLocation();
                    String[] pickup_lat_lng = pickup_latlng.split(",");
                    pickup_lat = pickup_lat_lng [0];
                    pickup_long = pickup_lat_lng [1];

                    pick_lat = Double.parseDouble(pickup_lat);
                    pick_long = Double.parseDouble(pickup_long);


                    String  drop_latlng = response.body().getResponse().getDropLocation();
                    String[] drop_lat_lng = drop_latlng.split(",");
                    drop_lat = drop_lat_lng [0];
                    drop_long = drop_lat_lng [1];


                    tv_distance.setText(response.body().getResponse().getDistance());
                  //  total_time = response.body().getResponse().getTotalTime();
                    tv_amount.setText("£ "+response.body().getResponse().getAmount());
                    farecost_amount.setText("£ "+response.body().getResponse().getFare_cost());
                    discount_amount.setText("£ "+response.body().getResponse().getDiscount());
                    paidamount_val.setText("£ "+response.body().getResponse().getAmount());
                   /* start_date = response.body().getResponse().getStartDate();
                    start_time = response.body().getResponse().getStartTime();
                    end_date = response.body().getResponse().getEndDate();
                    end_time = response.body().getResponse().getEndTime();*/

                /*    Log.d(TAG, "onResponse:user_id  - "+user_id );
                    Log.d(TAG, "onResponse:driver_id  - "+driver_id );
                    Log.d(TAG, "onResponse:pickup_address - "+pickup_address );*/
                    Log.d(TAG, "onResponse:pickup_lat - "+pickup_lat );
                    Log.d(TAG, "onResponse:pickup_long - "+pickup_long );
                    Log.d(TAG, "onResponse:drop_lat - "+drop_lat );
                    Log.d(TAG, "onResponse:drop_long - "+drop_long );


                    RequestOptions options = new RequestOptions()
                            .centerInside()
                            .placeholder(R.drawable.profile)
                            .error(R.drawable.profile);
                    Glide.with(JobDetailsActivity.this).load(response.body()
                            .getResponse().userDetails.getImage())
                            .apply(options).into(ci_cus_image);



                /*    String otp = String.valueOf(response.body().getResponse().getRideOtp());
                    Log.d(TAG, "onResponse: otp"+otp);
                    char optChar[] = otp.toCharArray();
                    for (int i = 0;i < optChar.length; i++){
                        System.out.println(optChar[i]);

                    }
                    txt_pin1.setText(String.valueOf(optChar[0]));
                    txt_pin2.setText(String.valueOf(optChar[1]));
                    txt_pin3.setText(String.valueOf(optChar[2]));
                    txt_pin4.setText(String.valueOf(optChar[3]));

                    Log.d(TAG, "onResponse: "+otp);*/


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