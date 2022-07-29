package com.example.driverfrenzi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.driverfrenzi.adapter.AdapterRideHistory;
import com.example.driverfrenzi.api.RestClient;
import com.example.driverfrenzi.responce.ResponseFetchRideHistory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RideHistory extends AppCompatActivity {


    ImageView img_close;
    RecyclerView recycler_view_ride_history;
    private final List<ResponseFetchRideHistory.Response> RideHistory = new ArrayList<>();
    AdapterRideHistory adapterRideHistory;
    private static final String TAG = "RideHistory";
    LinearLayout ll_date_layout;
    TextView tv_date;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String driver_ID;

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_history);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.gradient));
        }


        initControls();
        functions();

    }

    private void initControls() {

        SharedPreferences spp = Objects.requireNonNull(getSharedPreferences(Constant.DRIVER_PREF, Context.MODE_PRIVATE));
        driver_ID = spp.getString(Constant.DRIVER_ID, "");
        Log.e(TAG, "onCreate: driverId >>> "+driver_ID );


        recycler_view_ride_history=findViewById(R.id.recycler_view_ride_history);
        ll_date_layout=findViewById(R.id.ll_date_layout);
        tv_date=findViewById(R.id.tv_date);

        img_close=findViewById(R.id.img_close);




    }

    private void functions() {

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




        adapterRideHistory = new AdapterRideHistory(this,getApplicationContext(), RideHistory, new AdapterRideHistory.OnItemClickListener() {
            @Override
            public void onItemClick(ResponseFetchRideHistory.Response item) {

            }
        });

        RecyclerView.LayoutManager mmLayoutManager = new LinearLayoutManager(this);
        recycler_view_ride_history.setLayoutManager(mmLayoutManager);
        recycler_view_ride_history.setItemAnimator(new DefaultItemAnimator());
        recycler_view_ride_history.setAdapter(adapterRideHistory);

        ll_date_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();

            }
        });

          Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);
        Date newDate= null;
        try {
            newDate = df.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        df= new SimpleDateFormat("MMM dd, yyyy");
        String date = df.format(newDate);
       // System.out.println(date);


        tv_date.setText(date);

      // String date_to_send = formattedDate;


        FetchRideHistory(formattedDate);

    }


    public void datePicker() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(RideHistory.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        String date_val = dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year;

                        SimpleDateFormat spf=new SimpleDateFormat("dd-MM-yyyy");
                        Date newDate= null;
                        try {
                            newDate = spf.parse(date_val);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        spf= new SimpleDateFormat("MMM dd, yyyy");
                        String date = spf.format(newDate);
                        System.out.println(date);


                        tv_date.setText(date);
                       String date_to_send = year + "-" +(monthOfYear + 1) + "-" + dayOfMonth;
                        FetchRideHistory(date_to_send);
                    }
                }, mYear, mMonth, mDay);
        dpd.show();

    }


    private void FetchRideHistory(String date_to_send) {
        ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(R.color.ForestGreen)
                .fadeColor(Color.WHITE).build();
        dialog.setCancelable(false);
        dialog.show();



        RequestBody DriverId = RequestBody.create(MediaType.parse("text/plain"), driver_ID);
        RequestBody rideDate = RequestBody.create(MediaType.parse("text/plain"), date_to_send);


        RestClient.getClient().FetchRideHistory(DriverId,rideDate).enqueue(new Callback<ResponseFetchRideHistory>() {
            @Override
            public void onResponse(Call<ResponseFetchRideHistory> call, Response<ResponseFetchRideHistory> response) {
                Log.e(TAG, "onResponse 2 : " + response.code());
                Log.e(TAG, "onResponse 2: " + response.isSuccessful());
                // ppDialog.dismiss();
                assert response.body() != null;
                if (response.body().getStatus().equals(200)) {
                    dialog.dismiss();

                    ResponseFetchRideHistory listResponse = response.body();


                    assert listResponse != null;

                    for (ResponseFetchRideHistory.Response list : listResponse.getResponse()) {
                        Log.e(TAG, "onResponse: block LIst " + list);
                        RideHistory.add(list);
                        Log.e(TAG, "onResponse: Block List :" + list);


                    }


                    adapterRideHistory.notifyDataSetChanged();

                } else  {

                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseFetchRideHistory> call, Throwable t) {
                Log.e(TAG, "onFailure 2: " + t.getMessage());
                dialog.dismiss();
            }
        });
    }
}