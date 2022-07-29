package com.example.driverfrenzi;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
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
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Objects;

public class OfflineActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener {

    Activity activity;
    ImageView txt_menu;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    //   Toolbar toolbar;


    private static final String TAG = "OfflinePage";
    RelativeLayout txt_offline_btn;
    LabeledSwitch switch_new;
    LatLng a = null;
    private Circle circle;
    private GoogleMap mMap;
    private final static int LOCATION_REQUEST_CODE = 23;
    boolean locationPermission = false;

    String driver_Name, driver_Image;
    double current_lat, current_long;


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


//        if (Build.VERSION.SDK_INT >= 21) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(getResources().getColor(R.color.Black));
//        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        SharedPreferences spp = Objects.requireNonNull(getSharedPreferences(Constant.DRIVER_PREF, Context.MODE_PRIVATE));
        driver_Name = spp.getString(Constant.DRIVER_NAME, "");
        driver_Image = spp.getString(Constant.DRIVER_IMAGE, "");



        Log.e(TAG, "onCreate: driver_Name >>> "+driver_Name );
        Log.e(TAG, "onCreate: driver_Image >>> "+driver_Image );


        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                //   makeUseOfNewLocation(location);

                Log.e(TAG, "onLocationChanged: curr_lat "+location.getLatitude() );
                Log.e(TAG, "onLocationChanged: curr_long "+location.getLongitude() );
                current_lat = location.getLatitude();
                current_long = location.getLongitude();

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

// Register the listener with the Location Manager to receive location updates
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
        locationManager.requestLocationUpdates
                (LocationManager.NETWORK_PROVIDER, 0, 0,  locationListener);






        a = new LatLng(53.801277, -1.548567);


        txt_menu=findViewById(R.id.txt_menu);
        txt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        switch_new=findViewById(R.id.switch_new);
        switch_new.setOn(false);
        switch_new.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn==true){

                    Intent offline=new Intent(OfflineActivity.this,JobRequestActivity.class);
                    offline.putExtra("current_lat", current_lat);
                    offline.putExtra("current_long",current_long);
                    startActivity(offline);


               }
                Log.e(TAG, "onSwitched: Switch Status :"+isOn );
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
    private void getMyLocation() {
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
        mMap.setMyLocationEnabled(false);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {
//                Findroutes(a,b,c);
////                myLocation=location;
//                LatLng ltlng=new LatLng(location.getLatitude(),location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        a, 16f);
                mMap.animateCamera(cameraUpdate);
            }
        });

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        LatLng pos = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 18.0f));
        if(locationPermission) {
            getMyLocation();
        }
        googleMap.addMarker(new MarkerOptions().position(a).title("Me").icon(BitmapDescriptorFactory.fromResource(R.drawable.map_j)));

// Uses a custom icon.

//        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
//
//            @Override
//            public void onCircleClick(Circle circle) {
//                // Flip the r, g and b components of the circle's
//                // stroke color.
//                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
//                circle.setStrokeColor(strokeColor);
//            }
//        });

        //Set Focus
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                a, 18f);
        mMap.animateCamera(cameraUpdate);
        MapsInitializer.initialize(this);
//        addCustomMarker();

    }
    private void requestPermision() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_CODE);
        } else {
            locationPermission = true;
        }
    }
    @Override
    public void onConnectionFailed(@NonNull  ConnectionResult connectionResult) {

    }
    private void addCustomMarker() {
        if (mMap == null) {
            return;
        }

        // adding a marker on map with image from  drawable
        mMap.addMarker(new MarkerOptions()
                .position(a)
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.map_j))));
    }
    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
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

    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (drawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
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
        Glide.with(OfflineActivity.this).load(driver_Image).apply(options).into(img_profile);


        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                Intent intent1=new Intent(OfflineActivity.this,ProfileDetailActivity.class);
                startActivity(intent1);
            }
        });

        refer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                Intent intent1=new Intent(OfflineActivity.this,ReferandEarn.class);
                startActivity(intent1);
            }
        });

        your_ride_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                Intent intent1=new Intent(OfflineActivity.this,RideHistory.class);
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
                Intent intent222=new Intent(OfflineActivity.this,Aboutus.class);
                startActivity(intent222);

            }
        });
        btn_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer();
                Intent intent1=new Intent(OfflineActivity.this,ContactUs.class);
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

                Intent intent=new Intent(OfflineActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();


            }
        });
        dialog.show();
    }

}