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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.driverfrenzi.api.RestClient;
import com.example.driverfrenzi.api.ServerGeneralResponse;
import com.example.driverfrenzi.responce.ResponseStartRide;

import java.util.StringTokenizer;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import io.grpc.internal.LogExceptionRunnable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DropOffActivity extends AppCompatActivity {

    private static final String TAG = "DropOffActivity";
    TextView drop_button,tv_amount,tv_pick_add_1,tv_pick_add_2;
    ImageView btn_back;
    String paymentStatus,ride_id,amount,pickup_address;
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

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.MidnightBlue));
        }

        paymentStatus = getIntent().getStringExtra("paymentStatus");
        amount = getIntent().getStringExtra("amount");
        ride_id = getIntent().getStringExtra("ride_id");
        pickup_address = getIntent().getStringExtra("pickup_address");

        Log.e(TAG, "onCreate: paymentStatus>>> "+paymentStatus );
        Log.e(TAG, "onCreate: amount>>> "+amount );
        Log.e(TAG, "onCreate: ride_id>>> "+ride_id );
        Log.e(TAG, "onCreate: pickup_address>>> "+pickup_address );

        btn_back=findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        StringTokenizer tokens = new StringTokenizer(pickup_address, ",");
        String first = tokens.nextToken();// this will contain "12345"
        String second = tokens.nextToken();
        String third = tokens.nextToken();

        Log.e(TAG, "onCreate: first>>> "+first );
        Log.e(TAG, "onCreate: second>>> "+second );
        Log.e(TAG, "onCreate: third>>> "+third );


        drop_button=findViewById(R.id.drop_button);
        tv_amount=findViewById(R.id.tv_amount);
        tv_pick_add_1=findViewById(R.id.tv_pick_add_1);
        tv_pick_add_2=findViewById(R.id.tv_pick_add_2);

        tv_pick_add_1.setText(first);
        tv_pick_add_2.setText(second+", "+third);

        tv_amount.setText("£ "+amount);
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

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                ride_arrived();



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
                Log.e(TAG, "response.body().getStatus(): " + response.body().getStatus());

                if (response.body().getStatus().equals(401) ||response.body().getStatus().equals(200) ) {
                    //dialog.dismiss();
                    paymentStatus = String.valueOf(response.body().getResponse().getPaymentStatus());

               /*     Log.e(TAG, "onResponse: paymentStatus"+paymentStatus );
                    String price = String.valueOf(response.body().getResponse().getAmount());
                   if (paymentStatus.equals("done") || paymentStatus.equals("PAID")){
                     //if(!price.equals("0")) {
                       Log.e(TAG, "onResponse: inside if" );
                       String amount = String.valueOf(response.body().getResponse().getAmount());
                       ride_finish( dialog, amount);
                    }else{

                       Intent navi = new Intent(DropOffActivity.this, ReachDestinationActivity.class);
                       navi.putExtra("ride_id",ride_id);
                       startActivity(navi);
                    }*/
                    String amount = String.valueOf(response.body().getResponse().getAmount());
                    ride_finish( dialog, amount);

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

    private void ride_finish(ACProgressFlower dialog , String amount ) {
       /* ACProgressFlower dialog = new ACProgressFlower.Builder(DropOffActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.BLACK).build();
        dialog.show();*/

        RequestBody rideId = RequestBody.create(MediaType.parse("txt/plain"), ride_id);
        RequestBody payment_status = RequestBody.create(MediaType.parse("txt/plain"), "done");


        RestClient.getClient().FinishRide(rideId,payment_status).enqueue(new Callback<ResponseStartRide>() {
            @Override
            public void onResponse(Call<ResponseStartRide> call, Response<ResponseStartRide> response) {
                Log.e(TAG, "onResponse: Code :" + response.body());
                Log.e(TAG, "onResponse: " + response.code());
                Log.e(TAG, "onResponse: " + response.message());
                Log.e(TAG, "onResponse: " + response.errorBody());
                Log.e(TAG, "response.body().getStatus(): " + response.body().getStatus());
                if (response.body().getStatus().equals(401)||response.body().getStatus().equals(200)) {
                    dialog.dismiss();

                    String userID = String.valueOf(response.body().getResponse().getUserId());
                    String driverID = String.valueOf(response.body().getResponse().getDriverId());
                    Log.e(TAG, "onResponse: user_id"+userID );
                    Log.e(TAG, "onResponse: driverID"+driverID );


                    popUpExtraEarning( userID, driverID);




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

    private void popUpExtraEarning(String userID,String driverID) {

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.extra_earning_popup, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this
        );

        // Button btn_cancel =promptsView.findViewById(R.id.btn_cancel);
        Button btn_done =promptsView.findViewById(R.id.btn_done);
        Button btn_skip =promptsView.findViewById(R.id.btn_skip);
        EditText edt_drop_charges =promptsView.findViewById(R.id.edt_drop_charges);
        EditText edt_toll_charges =promptsView.findViewById(R.id.edt_toll_charges);
        EditText edt_luggage_charges =promptsView.findViewById(R.id.edt_luggage_charges);
      //  tv_price.setText("£"+amount);


//        TextView txt_cancel = (TextView) promptsView.findViewById(R.id.txt_cancel);


        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //ride_arrived();

                extra_pay( edt_drop_charges.getText().toString(),
                        edt_toll_charges.getText().toString(),
                        edt_luggage_charges.getText().toString(),
                        userID, driverID);

            }
        });

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //ride_arrived();

             //   Toast.makeText(DropOffActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                Intent in=new Intent(DropOffActivity.this,RatingActivity.class);
                in.putExtra("ride_id",ride_id);
                in.putExtra("user_id",userID);
                in.putExtra("driver_id",driverID);
                startActivity(in);
                finish();

            }
        });


        // create alert dialog
        alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        // show it
        alertDialog.show();

    }

    private void extra_pay(String drop_charge, String toll_charge, String luggage_charge,
                           String userID,String driverID) {
        ACProgressFlower dialog = new ACProgressFlower.Builder(DropOffActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.BLACK).build();
        dialog.show();

        RequestBody rideId = RequestBody.create(MediaType.parse("txt/plain"), ride_id);
        RequestBody drop_off_charges = RequestBody.create(MediaType.parse("txt/plain"), drop_charge);
        RequestBody toll_charges = RequestBody.create(MediaType.parse("txt/plain"), toll_charge);
        RequestBody luggage_charges = RequestBody.create(MediaType.parse("txt/plain"), luggage_charge);


        RestClient.getClient().ExtraPay(rideId,drop_off_charges,toll_charges,luggage_charges )
                .enqueue(new Callback<ServerGeneralResponse>() {
            @Override
            public void onResponse(Call<ServerGeneralResponse> call, Response<ServerGeneralResponse> response) {
                if (response.body().getStatus().equals(200)) {
                    dialog.dismiss();


                    //  Toast.makeText(VerifyOtpActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();


                  //  startRide(dialog);


                    Toast.makeText(DropOffActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent in=new Intent(DropOffActivity.this,RatingActivity.class);
                    in.putExtra("ride_id",ride_id);
                    in.putExtra("user_id",userID);
                    in.putExtra("driver_id",driverID);

                    startActivity(in);
                    finish();

                }else{
                    dialog.dismiss();

                    Toast.makeText(DropOffActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ServerGeneralResponse> call, Throwable t) {
                Toast.makeText(DropOffActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });




    }
}