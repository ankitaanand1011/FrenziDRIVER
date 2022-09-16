package com.example.driverfrenzi;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.driverfrenzi.adapter.AdapterJobRequest;
import com.example.driverfrenzi.adapter.AdapterRideHistory;
import com.example.driverfrenzi.api.RestClient;
import com.example.driverfrenzi.responce.ResponseFetchRideHistory;
import com.example.driverfrenzi.responce.ResponseJobDetails;
import com.example.driverfrenzi.responce.ResponseJobList;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.firebase.firestore.GeoPoint;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobRequestActivity extends AppCompatActivity {

    private static final String TAG = "JobRequestActivity";
    TextView accept,newrequest;
    LabeledSwitch switch_old;
    DrawerLayout drawerLayout;
    ImageView iv_drawer;
    Activity activity;
    ActionBarDrawerToggle drawerToggle;
    AdapterJobRequest adapterJobRequest;
    RecyclerView rv_job_request;
    String driver_Name, driver_Image,driver_id;
    private final List<ResponseJobList.Response> JobList = new ArrayList<>();
    double current_lat, current_long;
    String rideId;
    String pickup_lat, pickup_long, drop_lat, drop_long;

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_request);
        activity = this;

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.gradient));
        }

        SharedPreferences spp = Objects.requireNonNull(getSharedPreferences(Constant.DRIVER_PREF, Context.MODE_PRIVATE));
        driver_Name = spp.getString(Constant.DRIVER_NAME, "");
        driver_Image = spp.getString(Constant.DRIVER_IMAGE, "");
        driver_id = spp.getString(Constant.DRIVER_ID, "");

        current_lat = getIntent().getDoubleExtra("current_lat",0.0);
        current_long = getIntent().getDoubleExtra("current_long",0.0);

        Log.e(TAG, "onCreate: current_lat > "+current_lat );
        Log.e(TAG, "onCreate: current_long > "+current_long );


        switch_old=findViewById(R.id.switch_old);
        newrequest=findViewById(R.id.newrequest);
        rv_job_request=findViewById(R.id.rv_job_request);


        adapterJobRequest =new AdapterJobRequest(JobRequestActivity.this,
                getApplicationContext(), JobList,
                new AdapterJobRequest.OnItemClickListener() {
            @Override
            public void onItemClick(ResponseJobList.Response item) {
                Log.e(TAG, "onItemClick:aDAPTER "+item );
                rideId = String.valueOf(item.getRide_id());
                Log.e(TAG, "onItemClick: rideId >> "+rideId );
                getLocationFromAddress1(item.getPickup_address());
                getLocationFromAddress2(item.getDrop_address());
                AcceptRide();


            }
        });


        RecyclerView.LayoutManager mmLayoutManager = new LinearLayoutManager(this);
        rv_job_request.setLayoutManager(mmLayoutManager);
        rv_job_request.setItemAnimator(new DefaultItemAnimator());
        rv_job_request.setAdapter(adapterJobRequest);


        switch_old.setOn(true);
        switch_old.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn==false){

                    Intent offline=new Intent(JobRequestActivity.this,OfflineActivity.class);
                    startActivity(offline);


                }
                Log.e(TAG, "onSwitched: Switch Status :"+isOn );
            }
        });

        iv_drawer=findViewById(R.id.iv_drawer);
        iv_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

       // accept=findViewById(R.id.accept);



        FetchJobList();
    }

    private void FetchJobList() {
        ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(R.color.ForestGreen)
                .fadeColor(Color.WHITE).build();
        dialog.setCancelable(false);
        dialog.show();



        RequestBody driverId = RequestBody.create(MediaType.parse("text/plain"), driver_id);

        RestClient.getClient().FetchJobList(driverId).enqueue(new Callback<ResponseJobList>() {
            @Override
            public void onResponse(Call<ResponseJobList> call, Response<ResponseJobList> response) {
                Log.e(TAG, "onResponse 2 : " + response.code());
                Log.e(TAG, "onResponse 2: " + response.isSuccessful());
                // ppDialog.dismiss();
                assert response.body() != null;
                if (response.body().getStatus().equals(200)) {
                    dialog.dismiss();

                    ResponseJobList listResponse = response.body();


                    assert listResponse != null;

                    for (ResponseJobList.Response list : listResponse.getResponse()) {
                        Log.e(TAG, "onResponse: block LIst " + list);
                        JobList.add(list);
                        Log.e(TAG, "onResponse: Block List :" + list);
                        Log.e(TAG, "onResponse: Block List :" + listResponse.getResponse().size());
                        newrequest.setText("You have "+listResponse.getResponse().size()+" new request!");

                    }


                    adapterJobRequest.notifyDataSetChanged();

                } else  {

                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseJobList> call, Throwable t) {
                Log.e(TAG, "onFailure 2: " + t.getMessage());
                dialog.dismiss();
            }
        });
    }

    private void AcceptRide() {
        ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(R.color.ForestGreen)
                .fadeColor(Color.WHITE).build();
        dialog.setCancelable(false);
        dialog.show();





        RequestBody RideID = RequestBody.create(MediaType.parse("text/plain"), rideId);
        RequestBody post_driver_id = RequestBody.create(MediaType.parse("text/plain"), driver_id);
        RequestBody post_latitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(current_lat));
        RequestBody post_longitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(current_long));

        RestClient.getClient().AcceptRide(RideID,post_driver_id,
                post_latitude,post_longitude)
                .enqueue(new Callback<ResponseJobDetails>() {
            @Override
            public void onResponse(Call<ResponseJobDetails> call, Response<ResponseJobDetails> response) {
                Log.e(TAG, "onResponse 2 : " + response.code());
                Log.e(TAG, "onResponse 2: " + response.isSuccessful());
                // ppDialog.dismiss();
                assert response.body() != null;
                if (response.body().getStatus().equals(200)) {
                    dialog.dismiss();

                    ResponseJobDetails listResponse = response.body();

                Intent acc=new Intent(JobRequestActivity.this, JobDetailsActivity.class);
                acc.putExtra("ride_id",rideId);
                   acc.putExtra("current_lat",current_lat);
                  acc.putExtra("current_long",current_long);
                activity.startActivity(acc);

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

    public GeoPoint getLocationFromAddress1(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            pickup_lat =  String.valueOf(location.getLatitude());
            pickup_long =  String.valueOf(location.getLongitude());

            Log.e(TAG, "onClick:pickup_lat>   "+pickup_lat );
            Log.e(TAG, "onClick:pickup_long>   "+pickup_long );

            // p1 = new GeoPoint((double) (location.getLatitude() * 1E6),
            //    (double) (location.getLongitude() * 1E6));


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public GeoPoint getLocationFromAddress2(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            drop_lat =  String.valueOf(location.getLatitude());
            drop_long =  String.valueOf(location.getLongitude());
            Log.e(TAG, "onClick:drop_lat>   "+drop_lat );
            Log.e(TAG, "onClick:drop_long>   "+drop_long );

            // p1 = new GeoPoint((double) (location.getLatitude() * 1E6),
            //    (double) (location.getLongitude() * 1E6));


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        int orientation = this.getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            getSupportActionBar().show();
            View windowDecorView = getWindow().getDecorView();
//            windowDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            windowDecorView.setSystemUiVisibility(View.STATUS_BAR_VISIBLE);


        }else{

            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            }
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }


            else {
                super.onBackPressed();
            }
        }

        // If drawer is already close -- Do not override original functionality


    }
    private void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //  toolbar=findViewById(R.id.toolbar);
        LinearLayout your_ride_layout=findViewById(R.id.your_ride_layout);
        LinearLayout  refer_layout=findViewById(R.id.refer_layout);
        CircularImageView img_profile=findViewById(R.id.img_profile);
        LinearLayout  btn_logout=findViewById(R.id.btn_logout);
        LinearLayout  btn_contact_us=findViewById(R.id.btn_contact_us);
        LinearLayout  btn_about=findViewById(R.id.btn_about);
        TextView txt = findViewById(R.id.txt);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.menu);

        txt.setText(driver_Name);
        RequestOptions options = new RequestOptions()
                .centerInside()
                //   .placeholder(R.mipmap.driver)
                .error(R.mipmap.driver);
        Glide.with(JobRequestActivity.this).load(driver_Image).apply(options).into(img_profile);


        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                Intent intent1=new Intent(JobRequestActivity.this,ProfileDetailActivity.class);
                startActivity(intent1);
            }
        });

        refer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                Intent intent1=new Intent(JobRequestActivity.this,ReferandEarn.class);
                startActivity(intent1);
            }
        });

        your_ride_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                Intent intent1=new Intent(JobRequestActivity.this,RideHistory.class);
                startActivity(intent1);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer();
                showDialog(activity);
            }
        });
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer();
                Intent intent222=new Intent(JobRequestActivity.this,Aboutus.class);
                startActivity(intent222);

            }
        });
        btn_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer();
                Intent intent1=new Intent(JobRequestActivity.this,ContactUs.class);
                startActivity(intent1);
            }
        });
//        if (drawerToggle == null) {
//            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
//                public void onDrawerClosed(View view) {
//
//                }
//
//                public void onDrawerOpened(View drawerView) {
//
//                }
//
//                public void onDrawerSlide (View drawerView, float slideOffset) {
////                    CategoryListVertical();
//                }
//
//                public void onDrawerStateChanged(int newState) {
//
//                }
//
//            };
//            drawerLayout.setDrawerListener(drawerToggle);
//        }
//
//        drawerToggle.syncState();

//        CategoryListVertical();
//        String [] numbers = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
//         itemArrayAdapter = new ItemArrayAdapter(this, R.layout.list_item, numbers);
//        recyclerview_vertical.setAdapter(adapterVerticalCatagoryList);
    }


    void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        //  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        final TextView mTxt_Cancel = dialog.findViewById(R.id.txt_cancel);
        final TextView mTxt_Delete = dialog.findViewById(R.id.txt_logout);

        mTxt_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mTxt_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Clear all checksheet data On Home Page
                SharedPreferences sp = getSharedPreferences(Constant.DRIVER_PREF, Context.MODE_PRIVATE);
                sp.edit().remove(Constant.DRIVER_ID)
                        .remove(Constant.DRIVER_NAME)
                        .remove(Constant.DRIVER_ADDRESS)
                        .remove(Constant.DRIVER_MAIL)
                        .remove(Constant.DRIVER_IMAGE)
                        .apply();
                dialog.dismiss();

                Intent intent=new Intent(JobRequestActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();


            }
        });
        dialog.show();
    }

}