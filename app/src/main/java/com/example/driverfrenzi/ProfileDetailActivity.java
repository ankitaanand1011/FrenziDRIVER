package com.example.driverfrenzi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.driverfrenzi.adapter.AdapterCarList;
import com.example.driverfrenzi.api.RestClient;
import com.example.driverfrenzi.responce.LoginResponse;
import com.example.driverfrenzi.responce.ProfileResponse;
import com.example.driverfrenzi.responce.ResponseFetchCarList;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

import dev.ronnie.github.imagepicker.ImagePicker;
import dev.ronnie.github.imagepicker.ImageResult;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileDetailActivity extends AppCompatActivity {

    String TAG = "ProfileDetailActivity";
    TextView tv_save,tv_vehicle_type,et_license_expiry;
    EditText et_dri_name,et_dri_mobile,et_email,et_vehicle_no,
            et_full_address,et_post_code,et_vehicle_make,et_license,
            et_insurance,et_conviction_points,et_conviction_p_reason;
    ImageView iv_back,iv_profile,iv_add_image,iv_license,iv_insurance;
    String dri_name, dri_phone, dri_address, dri_address2, postal_code,dri_image,contact_person,dri_email;
    RequestOptions options;
    AlertDialog alertDialog1;
    RelativeLayout rl_vehicle_type,rl_license_image,rl_insurance_image;
    AdapterCarList adapterCarList;
    private List<ResponseFetchCarList.Response> CarList = new ArrayList<>();
    public String v_id;
    ImagePicker imagePicker;
    String clicked_on ;
    File profile_image, license_image, insurance_image;
    Double add_lat ,add_lng;
    String profile_image_str;
    String driver_Name, driver_Image,driver_ID;
    DatePickerDialog.OnDateSetListener mDateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.gradient));
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(ProfileDetailActivity.this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(ProfileDetailActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            }
            else{
                if(checkForPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}, 124)){

                }

            }
        }

        SharedPreferences spp = Objects.requireNonNull(getSharedPreferences(Constant.DRIVER_PREF, Context.MODE_PRIVATE));
        driver_Name = spp.getString(Constant.DRIVER_NAME, "");
        driver_Image = spp.getString(Constant.DRIVER_IMAGE, "");
        driver_ID = spp.getString(Constant.DRIVER_ID, "");


        initControls();


    }

    private void initControls() {

        imagePicker = new ImagePicker(ProfileDetailActivity.this);
        iv_back = findViewById(R.id.iv_back);
        iv_profile = findViewById(R.id.iv_profile);
        iv_add_image = findViewById(R.id.iv_add_image);
        et_dri_name = findViewById(R.id.et_dir_name);
        et_dri_mobile = findViewById(R.id.et_dir_mobile);
        et_email = findViewById(R.id.et_email);
        et_vehicle_no = findViewById(R.id.et_vehicle_no);
        et_full_address = findViewById(R.id.et_full_address);
        et_post_code = findViewById(R.id.et_post_code);
        et_vehicle_make = findViewById(R.id.et_vehicle_make);
        et_license = findViewById(R.id.et_license);
        et_license_expiry = findViewById(R.id.et_license_expiry);
        et_insurance = findViewById(R.id.et_insurance);
        et_conviction_points = findViewById(R.id.et_conviction_points);
        et_conviction_p_reason = findViewById(R.id.et_conviction_p_reason);

        tv_save = findViewById(R.id.tv_save);
        rl_vehicle_type = findViewById(R.id.rl_vehicle_type);
        tv_vehicle_type = findViewById(R.id.tv_vehicle_type);
        rl_license_image = findViewById(R.id.rl_license_image);
        iv_license = findViewById(R.id.iv_license);
        iv_insurance = findViewById(R.id.iv_insurance);
        rl_insurance_image = findViewById(R.id.rl_insurance_image);

        functions();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void functions() {

       /* options = new RequestOptions()
                .centerInside()
                .placeholder(R.mipmap.driver)
                .error(R.mipmap.driver);
        Glide.with(ProfileDetailActivity.this).load(dri_image).apply(options).into(iv_profile);
*/

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        iv_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked_on = "profile";
                selectImage();
            }
        });

        rl_license_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked_on = "license";
                selectImage();
            }
        });

        rl_insurance_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked_on = "insurance";
                selectImage();
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call profile update API
                UpdateProfile();
            }
        });


        rl_vehicle_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicle_listview();
            }
        });

        et_license_expiry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =  new DatePickerDialog(
                        ProfileDetailActivity.this,
                        //    android.R.style.Theme_DeviceDefault_Light,
                        mDateListener,
                        year,month,day
                );
                dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.BLACK ) );
                dialog.show();
                return false;
            }


        });

        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker , int year , int month , int day) {
                month = month + 1;

                Log.d( "onDateSet" , month + "/" + day + "/" + year );
                et_license_expiry.setText( new StringBuilder().append( day ).append( "/" )
                        .append( month ).append( "/" ).append( year ) );

              /*  String selected_date= selectedDay + " / " + (selectedMonth + 1) + " / "
                        + selectedYear;
                edt_user_exp.setText(selected_date);*/
            }
        };

        FetchProfile();
    }


    public void vehicle_listview() {


        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_list_dialog, null);
        builderSingle.setView(dialogView);


        final RecyclerView rvList =  dialogView.findViewById(R.id.rvList);



        adapterCarList = new AdapterCarList(getApplicationContext(),
                CarList, new AdapterCarList.OnItemClickListener() {
            @Override
            public void onItemClick(ResponseFetchCarList.Response item) {

                String strName = String.valueOf(item.getVehicleType());
                v_id = String.valueOf(item.getVehicleId());
                Log.e("upcoming_dates", "id: " + v_id);
                tv_vehicle_type.setText(strName);


                alertDialog1.dismiss();

            }
        });

        RecyclerView.LayoutManager mmLayoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(mmLayoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());

        rvList.setAdapter(adapterCarList);
        adapterCarList.notifyDataSetChanged();

        alertDialog1 = builderSingle.create();
        alertDialog1.show();



    }

    private void selectImage() {
        try {
            PackageManager pm = ProfileDetailActivity.this.getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, ProfileDetailActivity.this.getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo","Choose From Gallery","Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileDetailActivity.this);
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();

                            imagePicker.takeFromCamera(imageCallBack());

                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();

                            imagePicker.pickFromStorage(imageCallBack());
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(ProfileDetailActivity.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(ProfileDetailActivity.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private Function1<ImageResult<? extends Uri>, Unit> imageCallBack() {
        return imageResult -> {
            if (imageResult instanceof ImageResult.Success) {
                Uri uri = ((ImageResult.Success<Uri>) imageResult).getValue();
                Log.e(TAG, "imageCallBack: uri>>> "+uri );
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                            uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                saveToInternalStorage(bitmap);
                switch (clicked_on) {
                    case "profile":
                        iv_profile.setImageURI(uri);
                 //       profile_image_str = uri.get();

                      //  profile_image = new File(uri.getPath());
                      //  profile_image.getAbsolutePath();


                      //  profile_image = new File(getRealPathFromURI(ProfileDetailActivity.this,uri));

                        Log.e(TAG, "imageCallBack:profile_image_str >> "+profile_image_str);
                        break;
                    case "license":
                        iv_license.setImageURI(uri);
//                        license_image = new File(uri.getPath());
//                        license_image.getAbsolutePath();
//                        //license_image = new File(getRealPathFromURI(ProfileDetailActivity.this, uri));
                        Log.e(TAG, "imageCallBack:license_image "+license_image);
                        break;
                    case "insurance":
                        iv_insurance.setImageURI(uri);
//                        insurance_image = new File(uri.getPath());
//                        insurance_image.getAbsolutePath();
                        //insurance_image = new File(getRealPathFromURI(ProfileDetailActivity.this,uri));
                        Log.e(TAG, "imageCallBack:license_image "+insurance_image);
                        break;
                }
            } else {
                String errorString = ((ImageResult.Failure) imageResult).getErrorString();
                Toast.makeText(ProfileDetailActivity.this, errorString, Toast.LENGTH_LONG).show();
            }
            return null;
        };
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());

        long tsLong = System.currentTimeMillis()/1000;
        String ts = Long.toString(tsLong);

        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("DriverFrenzi", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"image"+ts+".jpg");
        Log.e(TAG, "saveToInternalStorage: mypath>> "+mypath );

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        switch (clicked_on) {
            case "profile":
                profile_image = mypath;
                //Log.e(TAG, "imageCallBack:profile_image_str >> "+profile_image_str);
                break;
            case "license":
                license_image = mypath;
                Log.e(TAG, "imageCallBack:license_image "+license_image);
                break;
            case "insurance":
                insurance_image = mypath;
                 Log.e(TAG, "imageCallBack:license_image "+license_image);
                break;
        }



        return directory.getAbsolutePath();
    }

    private void loadImageFromStorage(String path) {

        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
         //   ImageView img=(ImageView)findViewById(R.id.imgPicker);
          //  img.setImageBitmap(b);


        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    public void successAlert(String msg){


       Dialog dialog = new Dialog(ProfileDetailActivity.this, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dlg_dismiss);
        dialog.setCanceledOnTouchOutside(true);


        TextView tv_alert_msg =  dialog.findViewById(R.id.tv_alert_msg);
        TextView tv_dismiss =  dialog.findViewById(R.id.tv_dismiss);

        tv_alert_msg.setText(msg);


        tv_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();

            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // camera-related task you need to do.
                    Toast.makeText(ProfileDetailActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(ProfileDetailActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }

    }

    public boolean checkForPermission(final String[] permissions, final int permRequestCode) {

        final List<String> permissionsNeeded = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            final String perm = permissions[i];
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(ProfileDetailActivity.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {

                    Log.d("permisssion","not granted");


                    if (shouldShowRequestPermissionRationale(permissions[i])) {

                        Log.d("if","if");
                        permissionsNeeded.add(perm);

                    } else {
                        // add the request.
                        Log.d("else","else");
                        permissionsNeeded.add(perm);
                    }

                }
            }
        }

        if (permissionsNeeded.size() > 0) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // go ahead and request permissions
                requestPermissions(permissionsNeeded.toArray(new String[permissionsNeeded.size()]), permRequestCode);
            }
            return false;
        } else {
            // no permission need to be asked so all good...we have them all.
            return true;
        }

    }



    private void FetchCarList(ACProgressFlower dialog) {

      /*  ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(R.color.ForestGreen)
                .fadeColor(Color.WHITE).build();
        dialog.setCancelable(false);
        dialog.show();*/



        RequestBody list = RequestBody.create(MediaType.parse("text/plain"),"list" );


        RestClient.getClient().fetchCarlist(list).enqueue(new Callback<ResponseFetchCarList>() {
            @Override
            public void onResponse(Call<ResponseFetchCarList> call, Response<ResponseFetchCarList> response) {
                Log.e(TAG, "onResponse 2 : " + response.code());
                Log.e(TAG, "onResponse 2: " + response.isSuccessful());
                // ppDialog.dismiss();
                assert response.body() != null;
                if (response.body().getStatus().equals(200)) {
                    dialog.dismiss();

                    ResponseFetchCarList listResponse = response.body();


                    assert listResponse != null;

                    for (ResponseFetchCarList.Response list : listResponse.getResponse()) {
                        Log.e(TAG, "onResponse: block LIst " + list);
                        CarList.add(list);
                        Log.e(TAG, "onResponse: Blick List :" + list);


                    }



                    Log.e(TAG, "onResponse: Size Of Array ::" + CarList.size());

                } else  {

                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseFetchCarList> call, Throwable t) {
                Log.e(TAG, "onFailure 2: " + t.getMessage());
                dialog.dismiss();
            }
        });
    }

    private void UpdateProfile() {


   

        ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(R.color.ForestGreen)
                .fadeColor(Color.WHITE).build();
        dialog.setCancelable(false);
        dialog.show();

        String addresslatlng= et_full_address.getText().toString();
        getLocationFromAddress(ProfileDetailActivity.this,addresslatlng);


        RequestBody driver_id = RequestBody.create(MediaType.parse("text/plain"),driver_ID );
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"),et_dri_name.getText().toString().trim() );
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"),et_dri_mobile.getText().toString().trim() );
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"),et_email.getText().toString().trim() );
        //  RequestBody password = RequestBody.create(MediaType.parse("text/plain"),"123456" );
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"),et_full_address.getText().toString().trim() );
        RequestBody address_lat = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(add_lat) );
        RequestBody address_long = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(add_lng) );
        RequestBody post_code = RequestBody.create(MediaType.parse("text/plain"), et_post_code.getText().toString().trim());
        RequestBody vehicle_type = RequestBody.create(MediaType.parse("text/plain"),v_id );
        RequestBody vehicle_no = RequestBody.create(MediaType.parse("text/plain"),et_vehicle_no.getText().toString().trim() );
        RequestBody vehicle_make = RequestBody.create(MediaType.parse("text/plain"),et_vehicle_make.getText().toString().trim() );
        RequestBody license = RequestBody.create(MediaType.parse("text/plain"),et_license.getText().toString().trim() );
        RequestBody license_expiry = RequestBody.create(MediaType.parse("text/plain"),et_license_expiry.getText().toString().trim() );
        RequestBody insurance_no = RequestBody.create(MediaType.parse("text/plain"),et_insurance.getText().toString().trim());
        RequestBody conviction_points = RequestBody.create(MediaType.parse("text/plain"),et_conviction_points.getText().toString().trim() );
        RequestBody license_points_reason = RequestBody.create(MediaType.parse("text/plain"),et_conviction_p_reason.getText().toString().trim() );
        RequestBody convictions = RequestBody.create(MediaType.parse("text/plain"),"convictions" );



        MultipartBody.Part lic_image= MultipartBody.Part.createFormData(
                "license_image",
                "",
                RequestBody.create(MediaType.parse("file"), "license_image"));

        MultipartBody.Part ins_image= MultipartBody.Part.createFormData(
                "insurance_certificate",
                "",
                RequestBody.create(MediaType.parse("file"), "insurance_image"));

        MultipartBody.Part pro_image= MultipartBody.Part.createFormData(
                "image_icon",
                profile_image.getName(),
                RequestBody.create(MediaType.parse("file"), profile_image));

        Log.e(TAG, "UpdateProfile: driver_id>>> "+driver_id.toString() );
        Log.e(TAG, "UpdateProfile: profile_image>>> "+profile_image );


        RestClient.getClient().UpdateDriverProfile(driver_id,name,phone,email,
                address,address_lat,address_long,post_code,vehicle_type,
                vehicle_no,vehicle_make, license,license_expiry,insurance_no,
                conviction_points,license_points_reason,
                lic_image,ins_image,pro_image
                // license_image,insurance_certificate,image_icon
        ).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                Log.e(TAG, "onResponse 2 : " + response.code());
                Log.e(TAG, "onResponse 2: " + response.isSuccessful());
                // ppDialog.dismiss();
                assert response.body() != null;
                if (response.body().getStatus().equals(200)) {
                    dialog.dismiss();


                    SharedPreferences sp = getSharedPreferences(Constant.DRIVER_PREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    assert response.body() != null;

                    editor.putString(Constant.DRIVER_NAME, response.body().getResponse().getDriver_name());
                    editor.putString(Constant.DRIVER_ID, String.valueOf(response.body().getResponse().getDriver_id()));
                    editor.putString(Constant.DRIVER_ADDRESS, response.body().getResponse().getAddress());
                    editor.putString(Constant.DRIVER_MAIL, response.body().getResponse().getEmail());
                    editor.putString(Constant.DRIVER_IMAGE, response.body().getResponse().getImage());
                    editor.apply();


                    successAlert( response.body().getMessage());
                    // Toast.makeText(ProfileDetailActivity.this, response.body().getMessage(),Toast.LENGTH_LONG).show();


                    Log.e(TAG, "onResponse: Size Of Array ::" + CarList.size());

                } else  {

                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.e(TAG, "onFailure 2: " + t.getMessage());
                dialog.dismiss();
            }
        });
    }

    private void FetchProfile() {

        ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(R.color.ForestGreen)
                .fadeColor(Color.WHITE).build();
        dialog.setCancelable(false);
        dialog.show();

      /*  String addresslatlng= et_full_address.getText().toString();
        getLocationFromAddress(ProfileDetailActivity.this,addresslatlng);
*/

        RequestBody driver_id = RequestBody.create(MediaType.parse("text/plain"),driver_ID );

        Log.e(TAG, "UpdateProfile: driver_id>>> "+driver_id.toString() );
        // Log.e(TAG, "UpdateProfile: profile_image>>> "+profile_image );


        RestClient.getClient().DriverProfile(driver_id).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                Log.e(TAG, "onResponse 2 : " + response.code());
                Log.e(TAG, "onResponse 2: " + response.isSuccessful());
                // ppDialog.dismiss();
                assert response.body() != null;
                if (response.body().getStatus().equals(200)) {
                    // dialog.dismiss();


                    SharedPreferences sp = getSharedPreferences(Constant.DRIVER_PREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    assert response.body() != null;

                    editor.putString(Constant.DRIVER_NAME, response.body().getResponse().getDriver_name());
                    editor.putString(Constant.DRIVER_ID, String.valueOf(response.body().getResponse().getDriver_id()));
                    editor.putString(Constant.DRIVER_ADDRESS, response.body().getResponse().getAddress());
                    editor.putString(Constant.DRIVER_MAIL, response.body().getResponse().getEmail());
                    editor.putString(Constant.DRIVER_MAIL, response.body().getResponse().getEmail());
                    editor.apply();

                    et_dri_name.setText(response.body().getResponse().getDriver_name());
                    et_dri_mobile.setText(response.body().getResponse().getPhone());
                    et_email.setText(response.body().getResponse().getEmail());
                    et_full_address.setText(response.body().getResponse().getAddress());
                    et_post_code.setText(response.body().getResponse().getPost_code());
                    et_vehicle_no.setText(response.body().getResponse().getVehicle_no());
                    et_vehicle_make.setText(response.body().getResponse().getVehicle_make());
                    et_license.setText(response.body().getResponse().getLicense());
                    et_license_expiry.setText(response.body().getResponse().getLicense_expiry());
                    et_insurance.setText(response.body().getResponse().getInsurance_no());
                    // et_conviction_points.setText((Integer) response.body().getResponse().getLicense_points());
                    // et_vehicle_make.setText(response.body().getResponse().getVehicle_make());


                    options = new RequestOptions()
                            .centerInside()
                            //   .placeholder(R.mipmap.driver)
                            .error(R.mipmap.driver);
                    Glide.with(ProfileDetailActivity.this).load(response.body().getResponse().getImage()).apply(options).into(iv_profile);


                    String vehicle_type = String.valueOf(response.body().getResponse().getVehicle_type());
                    switch (vehicle_type) {
                        case "5":
                            tv_vehicle_type.setText("Mini");
                            break;
                        case "6":
                            tv_vehicle_type.setText("Medium");
                            break;
                        case "7":
                            tv_vehicle_type.setText("Heavy");
                            break;
                        case "8":
                            tv_vehicle_type.setText("Hatchback");
                            break;
                    }






                    FetchCarList(dialog);

                } else  {

                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.e(TAG, "onFailure 2: " + t.getMessage());
                dialog.dismiss();
            }
        });
    }

    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );
            Log.e(TAG, "getLocationFromAddress: p1>"+ p1 );
            add_lat = location.getLatitude();
            add_lng = location.getLongitude();

            Log.e(TAG, "getLocationFromAddress: p1 add_lat >"+ add_lat );
            Log.e(TAG, "getLocationFromAddress: p1 add_lng >"+ add_lng );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

}