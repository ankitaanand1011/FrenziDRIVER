package com.example.driverfrenzi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.driverfrenzi.api.RestClient;
import com.example.driverfrenzi.responce.ResponseStartRide;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DropOffActivity extends AppCompatActivity {

    private static final String TAG = "DropOffActivity";
    TextView drop_button;
    ImageView btn_back;
    String paymentStatus,ride_id,amount;
    AlertDialog alertDialog;

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_off);

       // paymentStatus = getIntent().getStringExtra("paymentStatus");
        amount = getIntent().getStringExtra("amount");
        ride_id = getIntent().getStringExtra("ride_id");
        Log.e(TAG, "onCreate: paymentStatus>>> "+paymentStatus );

        btn_back=findViewById(R.id.btn_back);
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
        drop_button=findViewById(R.id.drop_button);
        drop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    popUp();

            }
        });


    }
    private void popUp() {

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.earning_popup, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this
        );

       // Button btn_cancel =promptsView.findViewById(R.id.btn_cancel);
        Button btn_done =promptsView.findViewById(R.id.btn_done);
        TextView tv_price =promptsView.findViewById(R.id.tv_price);
        tv_price.setText("£"+amount);


//        TextView txt_cancel = (TextView) promptsView.findViewById(R.id.txt_cancel);


        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

//        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                View radioButton = radio_group.findViewById(i);
//                int index = radio_group.indexOfChild(radioButton);
//
//                // Add logic here
//
//                switch (index) {
//                    case 0: // first button
//
////                        Toast.makeText(getApplicationContext(), "Selected button number " +index ,Toast.LENGTH_LONG).show();
//                        break;
//                    case 1: // secondbutton
//
////                        Toast.makeText(getApplicationContext(), "Selected button numbers "+index,Toast.LENGTH_LONG).show();
//                        break;
//                }
//            }
//        });


      /*  btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(DropOffActivity.this,RatingActivity.class);
                startActivity(in);
                finish();
            }
        });
*/


        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ride_arrived();
/*
                Intent in=new Intent(ReachDestinationActivity.this,
                        RatingActivity.class);
                in.putExtra("ride_id",ride_id);
                in.putExtra("user_id",user_id);
                in.putExtra("driver_id",driver_id);

                startActivity(in);
                finish();*/


            }
        });


        // create alert dialog
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        // show it
        alertDialog.show();
    }

    private void ride_arrived( ) {

        ACProgressFlower dialog = new ACProgressFlower.Builder(DropOffActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.BLACK).build();
        dialog.show();

        RequestBody rideId = RequestBody.create(MediaType.parse("txt/plain"), ride_id);


        RestClient.getClient().RideArrived(rideId).enqueue(new Callback<ResponseStartRide>() {
            @Override
            public void onResponse(Call<ResponseStartRide> call, Response<ResponseStartRide> response) {
                Log.e(TAG, "onResponse: Code :" + response.body());
                Log.e(TAG, "onResponse: " + response.code());
                Log.e(TAG, "onResponse: " + response.message());
                Log.e(TAG, "onResponse: " + response.errorBody());
                if (response.body().getStatus().equals(401)) {
                 //   dialog.dismiss();
                    paymentStatus = String.valueOf(response.body().getResponse().getPaymentStatus());
                    if (!paymentStatus.equals("done") || !paymentStatus.equals("PAID")|| paymentStatus.equals("CASH")){
                        Intent navi = new Intent(DropOffActivity.this, ReachDestinationActivity.class);
                        navi.putExtra("ride_id",ride_id);
                        startActivity(navi);
                    }else{
                        String amount = String.valueOf(response.body().getResponse().getAmount());

                        ride_finish( dialog,amount);
                    }


                /*    String paymentStatus = response.body().getResponse().getPaymentStatus();
                    Log.e(TAG, "onResponse:paymentStatus>>>  "+paymentStatus);
                    Toast.makeText(DropOffActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent start=new Intent(DropOffActivity.this,DropOffActivity.class);
                    start.putExtra("paymentStatus",paymentStatus);
                    startActivity(start);*/


                }else{
                    dialog.dismiss();
//                        Toast.makeText(MainActivity.this,"Wrong username or password !!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(DropOffActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseStartRide> call, Throwable t) {
                Toast.makeText(DropOffActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });




    }

    private void ride_finish( ACProgressFlower dialog, String amount ) {


        RequestBody rideId = RequestBody.create(MediaType.parse("txt/plain"), ride_id);
        RequestBody payment_status = RequestBody.create(MediaType.parse("txt/plain"), "done");


        RestClient.getClient().FinishRide(rideId,payment_status).enqueue(new Callback<ResponseStartRide>() {
            @Override
            public void onResponse(Call<ResponseStartRide> call, Response<ResponseStartRide> response) {
                Log.e(TAG, "onResponse: Code :" + response.body());
                Log.e(TAG, "onResponse: " + response.code());
                Log.e(TAG, "onResponse: " + response.message());
                Log.e(TAG, "onResponse: " + response.errorBody());
                if (response.body().getStatus().equals(401)) {
                    dialog.dismiss();

                   /* String paymentStatus = response.body().getResponse().getPaymentStatus();
                    Log.e(TAG, "onResponse:paymentStatus>>>  "+paymentStatus);

                    Intent start=new Intent(DropOffActivity.this,DropOffActivity.class);
                    start.putExtra("paymentStatus",paymentStatus);
                    startActivity(start);*/
                    Toast.makeText(DropOffActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent in=new Intent(DropOffActivity.this,RatingActivity.class);
                    in.putExtra("ride_id",ride_id);
                    in.putExtra("user_id",response.body().getResponse().getUserId());
                    in.putExtra("driver_id",response.body().getResponse().getDriverId());

                    startActivity(in);
                    finish();



                }else{
                    dialog.dismiss();
//                        Toast.makeText(MainActivity.this,"Wrong username or password !!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(DropOffActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseStartRide> call, Throwable t) {
                Toast.makeText(DropOffActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });




    }
}