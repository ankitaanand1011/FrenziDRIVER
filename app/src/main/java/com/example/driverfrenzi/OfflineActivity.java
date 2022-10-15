package com.example.driverfrenzi;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.driverfrenzi.api.RestClient;
import com.example.driverfrenzi.api.ServerGeneralResponse;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Objects;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfflineActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener{

    Activity activity;
    ImageView txt_menu;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    //   Toolbar toolbar;

    EditText edt_roaming;
    TextView tv_save;
    private static final String TAG = "OfflinePage";
    RelativeLayout txt_offline_btn;
    LabeledSwitch switch_new;
    LatLng a = null;
    private Circle circle;
    private GoogleMap mMap;
    private final static int LOCATION_REQUEST_CODE = 23;
    boolean locationPermission = false;

    String driver_Name, driver_Image,driver_id;
     String driver_availability;
    double current_lat, current_long;
    GoogleApiClient googleApiClient;
     double longitude;
     double latitude;
    LatLng latLng;
    FloatingActionButton btn_current_location;

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        activity = this;


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

      /*  SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);*/

        SharedPreferences spp = Objects.requireNonNull(getSharedPreferences(Constant.DRIVER_PREF, Context.MODE_PRIVATE));
        driver_Name = spp.getString(Constant.DRIVER_NAME, "");
        driver_Image = spp.getString(Constant.DRIVER_IMAGE, "");
        driver_id = spp.getString(Constant.DRIVER_ID, "");
        driver_availability = spp.getString(Constant.DRIVER_AVAILABILITY, "");



        Log.e(TAG, "onCreate: driver_id >>> "+driver_id );
        Log.e(TAG, "onCreate: driver_Image >>> "+driver_Image );
        Log.e(TAG, "onCreate: DRIVER_AVAILABILITY >>> "+driver_availability);



        btn_current_location=findViewById(R.id.btn_current_location);
        switch_new=findViewById(R.id.switch_new);
        txt_menu=findViewById(R.id.txt_menu);
        edt_roaming=findViewById(R.id.edt_roaming);
        tv_save=findViewById(R.id.tv_save);

        txt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        btn_current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
                moveMap();
            }
        });

        if(driver_availability.equals("yes")){

            Intent offline=new Intent(OfflineActivity.this,JobRequestActivity.class);
            offline.putExtra("current_lat", latitude);
            offline.putExtra("current_long",longitude);
            startActivity(offline);
            finish();
        }else{
            switch_new.setOn(false);
        }
        switch_new.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn==true){
                    String available = "yes" ;
                    driverAvailability(available);
                /*
                    Intent offline=new Intent(OfflineActivity.this,JobRequestActivity.class);
                    offline.putExtra("current_lat", latitude);
                    offline.putExtra("current_long",longitude);
                    startActivity(offline); */


                }
                Log.e(TAG, "onSwitched: Switch Status :"+isOn );
            }


        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roamingRadius();
            }
        });




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //if permission granted.
                    locationPermission = true;
                    mMap.getMyLocation();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(null, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.img_profile);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }
    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    //Getting current location
    private void getCurrentLocation() {
//        mMap.clear();
        //Creating a location object
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.



            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            Log.d(TAG, "getCurrentLocation: latitude >> "+latitude);
            Log.d(TAG, "getCurrentLocation: longitude >> "+longitude);

            //moving the map to location
            moveMap();
        }
    }

    //Function to move the map
    private void moveMap() {
        //String to display current latitude and longitude
        String msg = latitude + ", "+longitude;
        Log.e(TAG, "moveMap: Location Point ::"+msg );

        //Creating a LatLng Object to store Coordinates
        latLng = new LatLng(latitude, longitude);

//        //Adding marker to map
//        mMap.addMarker(new MarkerOptions()
//                .position(latLng) //setting position
//                .draggable(true) //Making the marker draggable
//                .title("Me")); //Adding a title
        if (mMap == null) {
            return;
        }
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.map_j);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 84, 84, false);
        // adding a marker on map with image from  drawable
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 16));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String msg = latitude + ", "+longitude;
        Log.e(TAG, "moveMap: Location Point ::"+msg );

        //Creating a LatLng Object to store Coordinates
        latLng = new LatLng(latitude, longitude);


        if (mMap == null) {
            return;
        }

      /*  BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.map_j);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 84, 84, false);
        // adding a marker on map with image from  drawable
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));*/

        //Animating the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 16));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
//        googleMap.addMarker(new MarkerOptions().position(latLng).title("Me").icon(BitmapDescriptorFactory.fromResource(R.drawable.map_j)));

    }

    @Override
    public void onConnected(Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //Getting the coordinates
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        //Moving the map
        moveMap();
    }

    private void circularRevealTransition() {
        int X = 9 * drawerLayout.getWidth()/10; //The X coordinate for the initial position
        int Y = 9 * drawerLayout.getHeight()/10; //The Y coordinate for the initial position
        int Duration = 500;  //Duration for the animation

        float finalRadius = Math.max(drawerLayout.getWidth(), drawerLayout.getHeight()); //The final radius must be the end points of the current activity

        // create the animator for this view, with the start radius as zero
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(drawerLayout, X, Y, 0, finalRadius);
        circularReveal.setDuration(Duration);

        // set the view visible and start the animation
        drawerLayout.setVisibility(View.VISIBLE);
        // start the animation
        circularReveal.start();
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

            View windowDecorView = getWindow().getDecorView();
            windowDecorView.setSystemUiVisibility(View.STATUS_BAR_VISIBLE);


        }else{

            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            }


            else {
                super.onBackPressed();
            }
        }



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
        LinearLayout your_ride_layout = findViewById(R.id.your_ride_layout);
        LinearLayout refer_layout = findViewById(R.id.refer_layout);
        CircularImageView img_profile = findViewById(R.id.img_profile);
        LinearLayout btn_logout = findViewById(R.id.btn_logout);
        LinearLayout btn_contact_us = findViewById(R.id.btn_contact_us);
        LinearLayout btn_about = findViewById(R.id.btn_about);
        TextView txt = findViewById(R.id.txt);


        txt.setText(driver_Name);

        RequestOptions options = new RequestOptions()
                .centerInside()
                //   .placeholder(R.mipmap.driver)
                .error(R.mipmap.driver);
        Glide.with(OfflineActivity.this).load(driver_Image).apply(options).into(img_profile);


        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                Intent intent1 = new Intent(OfflineActivity.this, ProfileDetailActivity.class);
                startActivity(intent1);
            }
        });

        refer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                Intent intent1 = new Intent(OfflineActivity.this, ReferAndEarn.class);
                startActivity(intent1);
            }
        });

        your_ride_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                Intent intent1 = new Intent(OfflineActivity.this, RideHistory.class);
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
                Intent intent222 = new Intent(OfflineActivity.this, Aboutus.class);
                startActivity(intent222);

            }
        });
        btn_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer();
                Intent intent1 = new Intent(OfflineActivity.this, ContactUs.class);
                startActivity(intent1);
            }
        });
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

                Intent intent=new Intent(OfflineActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();


            }
        });
        dialog.show();
    }

    private void driverAvailability( String available ) {

        ACProgressFlower dialog = new ACProgressFlower.Builder(OfflineActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.BLACK).build();
        dialog.show();


        RequestBody driverId = RequestBody.create(MediaType.parse("txt/plain"), driver_id);
        RequestBody available_status = RequestBody.create(MediaType.parse("txt/plain"), available);
      //  RequestBody new_password = RequestBody.create(MediaType.parse("txt/plain"), edt_password.getText().toString().trim());


        Log.e(TAG, "driverAvailability:driver_id>> "+driver_id );
        Log.e(TAG, "driverAvailability:available>> "+available );

        RestClient.getClient().DriverAvailability(driverId,available_status).
                enqueue(new Callback<ServerGeneralResponse>() {
            @Override
            public void onResponse(Call<ServerGeneralResponse> call, Response<ServerGeneralResponse> response) {
                Log.e(TAG, "onResponse: Code :" + response.body());
                Log.e(TAG, "onResponse: " + response.code());
                Log.e(TAG, "onResponse: " + response.errorBody());
               // assert response.body() != null;
                if(!response.body().getStatus().equals(500)){

                    if (response.body().getStatus().equals(200)) {
                        dialog.dismiss();

                        //  Log.e(TAG, "onResponse code: "+response.body().getResponse() );

                        SharedPreferences sp = getSharedPreferences(Constant.DRIVER_PREF, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        assert response.body() != null;

                        editor.putString(Constant.DRIVER_AVAILABILITY, "yes");
                        editor.apply();

                        Intent offline=new Intent(OfflineActivity.this,JobRequestActivity.class);
                        offline.putExtra("current_lat", latitude);
                        offline.putExtra("current_long",longitude);
                        startActivity(offline);
                        finish();

                    } else {
                        dialog.dismiss();

                    }
                }else{
                    Toast.makeText(OfflineActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                }

        }

            @Override
            public void onFailure(Call<ServerGeneralResponse> call, Throwable t) {

                dialog.dismiss();
            }
        });

    }

    private void roamingRadius() {
        if(TextUtils.isEmpty(edt_roaming.getText().toString().trim())) {
            edt_roaming.setError("Please enter your roaming radius");
            return;

        }else {
            ACProgressFlower dialog = new ACProgressFlower.Builder(OfflineActivity.this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.BLACK).build();
            dialog.show();


            RequestBody roaming_radius = RequestBody.create(MediaType.parse("txt/plain"), edt_roaming.getText().toString().trim());
            RequestBody driverId = RequestBody.create(MediaType.parse("txt/plain"), driver_id);

            RestClient.getClient().RoamingRadius(driverId,roaming_radius).enqueue(new Callback<ServerGeneralResponse>() {
                @Override
                public void onResponse(Call<ServerGeneralResponse> call, Response<ServerGeneralResponse> response) {
                    Log.e(TAG, "onResponse: Code :" + response.body());
                    Log.e(TAG, "onResponse: " + response.code());
                    Log.e(TAG, "onResponse: " + response.errorBody());
                    if (!String.valueOf(response.code()).equals("500")) {
                        if (response.body().getStatus().equals(200)) {
                            dialog.dismiss();


                            Toast.makeText(OfflineActivity.this,response.body().getMessage(), Toast.LENGTH_LONG).show();

                        } else {
                            dialog.dismiss();

                        }
                    }else{
                        dialog.dismiss();
                        Toast.makeText(OfflineActivity.this,"Something went wrong !!", Toast.LENGTH_LONG).show();
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

