package com.example.driverfrenzi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.example.driverfrenzi.adapter.AdapterCarList;
import com.example.driverfrenzi.api.RestClient;
import com.example.driverfrenzi.responce.ResponseFetchCarList;
import com.example.driverfrenzi.responce.ResponseRegistration;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

public class RegisterActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;
    Button button_register;
    EditText edt_user_name, edt_user_phone, edt_user_email, edt_user_add,
            edt_user_postcode,edt_conviction_points, edt_con_points_reason,
             edt_user_vehicle_no, edt_user_password, edt_user_license,
             edt_user_ins_number,edt_user_vehicle_make;

    TextView tv_vehicle_type,edt_user_exp;
    int year, month, day;

    RequestOptions options;
    AlertDialog alertDialog1;
    RelativeLayout rl_vehicle_type,rl_license_image,rl_insurance_image;
    AdapterCarList adapterCarList;
    private List<ResponseFetchCarList.Response> CarList = new ArrayList<>();
    public String v_id = "0";
    ImagePicker imagePicker;
    String clicked_on = "nothing";
    File profile_image, license_image, insurance_image;
    Double add_lat ,add_lng;
    String profile_image_str;
    String license_image_selected = "no", insurance_image_selected = "no";
    ImageView iv_back,iv_add_image,iv_license,iv_insurance;
    String gender = "male";
    String customer_prefer = "male_female";
    RadioButton rb_male, rb_female, rb_male_female, rb_female_only,rb_male_only;
    private static final String TAG = "Reg";
    DatePickerDialog.OnDateSetListener mDateListener;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(RegisterActivity.this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(RegisterActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
            }
            else{
                if(checkForPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}, 124)){

                }

            }
        }

        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        button_register = findViewById(R.id.button_register);
        edt_user_name = findViewById(R.id.edt_user_name);

        edt_user_phone = findViewById(R.id.edt_user_phone);
        edt_user_email = findViewById(R.id.edt_user_email);
        edt_user_add = findViewById(R.id.edt_user_add);
        edt_user_postcode = findViewById(R.id.edt_user_postcode);
        edt_user_vehicle_make = findViewById(R.id.edt_user_vehicle_make);
        tv_vehicle_type = findViewById(R.id.tv_vehicle_type);
        edt_user_vehicle_no = findViewById(R.id.edt_user_vehicle_no);
        edt_user_password = findViewById(R.id.edt_user_password);
        edt_conviction_points = findViewById(R.id.edt_conviction_points);
        edt_con_points_reason = findViewById(R.id.edt_con_points_reason);

        rl_vehicle_type = findViewById(R.id.rl_vehicle_type);
        tv_vehicle_type = findViewById(R.id.tv_vehicle_type);
        rl_license_image = findViewById(R.id.rl_license_image);
        iv_license = findViewById(R.id.iv_license);
        iv_insurance = findViewById(R.id.iv_insurance);
        rl_insurance_image = findViewById(R.id.rl_insurance_image);

        edt_user_license = findViewById(R.id.edt_user_license);
        edt_user_exp = findViewById(R.id.edt_user_exp);
        edt_user_ins_number = findViewById(R.id.edt_user_ins_number);

        rb_male=findViewById(R.id.rb_male);
        rb_female=findViewById(R.id.rb_female);
        rb_male_female=findViewById(R.id.rb_male_female);
        rb_female_only=findViewById(R.id.rb_female_only);
        rb_male_only=findViewById(R.id.rb_male_only);

        imagePicker = new ImagePicker(RegisterActivity.this);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rb_male.isChecked()) {
                    gender = "male";
                } else if (rb_female.isChecked()) {
                    gender = "female";
                }

                if (rb_male_female.isChecked()) {
                    customer_prefer = "any";
                } else if (rb_female_only.isChecked()) {
                    customer_prefer = "female_only";
                } else if (rb_male_only.isChecked()) {
                    customer_prefer = "male_only";
                }
                Registration();
            }
        });

        edt_user_exp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =  new DatePickerDialog(
                        RegisterActivity.this,
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
            public void onDateSet(DatePicker datePicker ,int year ,int month ,int day) {
                month = month + 1;

                Log.d( "onDateSet" , month + "/" + day + "/" + year );
                edt_user_exp.setText( new StringBuilder().append( day ).append( "/" )
                        .append( month ).append( "/" ).append( year ) );

              /*  String selected_date= selectedDay + " / " + (selectedMonth + 1) + " / "
                        + selectedYear;
                edt_user_exp.setText(selected_date);*/
            }
        };

        rl_vehicle_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicle_listview();
            }
        });

        rl_license_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked_on = "license";
                license_image_selected = "yes";

                selectImage();
            }
        });

        rl_insurance_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked_on = "insurance";
                insurance_image_selected = "yes";
                selectImage();
            }
        });

        iv_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iv_license.isSelected()){
                    iv_license.setSelected(false);
                }else{ iv_license.setSelected(true);

                    }
                }
        });


        FetchCarList();

    }

/*

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;
            da


         */
/*   DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this, datePickerListener, year, month, day);
            Field mDatePickerField;
            try {
                mDatePickerField = dialog.getClass().getDeclaredField("mDatePicker");
                mDatePickerField.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return dialog;*//*

            String selected_date= selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear;
            edt_user_exp.setText(selected_date);
        }
    };
*/


    void showDialogRegistrationDone(Activity activity) {
        final Dialog popupView = new Dialog(activity);
        popupView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popupView.setContentView(R.layout.dialog_done_reg);
        popupView.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupView.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        popupView.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        //  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        TextView  btn_continue;

        btn_continue = popupView.findViewById(R.id.btn_continue);

        btn_continue.setOnClickListener(v -> {
            popupView.dismiss();
//            openBrowser("https://nu-by-js.com/know-more");
            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        });

        popupView.show();


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
            PackageManager pm = RegisterActivity.this.getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, RegisterActivity.this.getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo","Choose From Gallery","Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
                Toast.makeText(RegisterActivity.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(RegisterActivity.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
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

                    case "license":
                        iv_license.setImageURI(uri);
                    //    license_image = new File(uri.getPath());
                     //   license_image.getAbsolutePath();
                        //license_image = new File(getRealPathFromURI(RegisterActivity.this, uri));
                        Log.e(TAG, "imageCallBack:license_image "+license_image);
                        break;
                    case "insurance":
                        iv_insurance.setImageURI(uri);
                      //  insurance_image = new File(uri.getPath());
                      //  insurance_image.getAbsolutePath();
                        //insurance_image = new File(getRealPathFromURI(RegisterActivity.this,uri));
                        Log.e(TAG, "imageCallBack:license_image "+license_image);
                        break;
                }
            } else {
                String errorString = ((ImageResult.Failure) imageResult).getErrorString();
                Toast.makeText(RegisterActivity.this, errorString, Toast.LENGTH_LONG).show();
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
    

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            // permission was granted, yay! Do the
            // camera-related task you need to do.
            Toast.makeText(RegisterActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {

            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            Toast.makeText(RegisterActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean checkForPermission(final String[] permissions, final int permRequestCode) {

        final List<String> permissionsNeeded = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            final String perm = permissions[i];
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(RegisterActivity.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {

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



    private void FetchCarList() {

        ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(R.color.ForestGreen)
                .fadeColor(Color.WHITE).build();
        dialog.setCancelable(false);
        dialog.show();



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

    private void Registration() {
        if (TextUtils.isEmpty(edt_user_name.getText().toString().trim())) {
            edt_user_name.setError("Please Enter Username");
            return;
        } else if (TextUtils.isEmpty(edt_user_email.getText().toString().trim())) {
            edt_user_email.setError("Please Enter Email");
            return;
        } else if (TextUtils.isEmpty(edt_user_password.getText().toString().trim())) {
            edt_user_password.setError("Please Enter PassWord");
            return;
        } else if (TextUtils.isEmpty(edt_user_phone.getText().toString().trim())) {
            edt_user_phone.setError("Please Enter Mobile Number");
            return;
        } else if (TextUtils.isEmpty(edt_user_add.getText().toString().trim())) {
            edt_user_add.setError("Please Enter Address");
            return;

        } else if (TextUtils.isEmpty(edt_user_postcode.getText().toString().trim())) {
            edt_user_postcode.setError("Please Enter Post Code");
            return;

        } else if (TextUtils.isEmpty(edt_user_vehicle_make.getText().toString().trim())) {
            edt_user_vehicle_make.setError("Please Enter Vehicle Make");
            return;

        } else if (TextUtils.isEmpty(edt_user_vehicle_no.getText().toString().trim())) {
            edt_user_vehicle_no.setError("Please Enter Vehicle Registration No.");
            return;

        } else if (TextUtils.isEmpty(edt_user_license.getText().toString().trim())) {
            edt_user_license.setError("Please Enter License No.");
            return;

        } else if (TextUtils.isEmpty(edt_user_exp.getText().toString().trim())) {
            edt_user_exp.setError("Enter License Expiry Date");
            return;

        } else if (TextUtils.isEmpty(edt_user_ins_number.getText().toString().trim())) {
            edt_user_ins_number.setError("Please Enter Insurance No.");
            return;

        } else if (TextUtils.isEmpty(edt_user_password.getText().toString().trim())) {
            edt_user_password.setError("Please Enter Password");
            return;

        } else if (TextUtils.isEmpty(edt_conviction_points.getText().toString().trim())) {
            edt_conviction_points.setError("Enter Conviction Points");
            return;

        } else if (TextUtils.isEmpty(edt_con_points_reason.getText().toString().trim())) {
            edt_con_points_reason.setError("Enter Reason");
            return;

        }else if (v_id.equals("0")) {
            tv_vehicle_type.setError("Select Vehicle");
            return;

        }else if (license_image_selected.equals("no")) {

            Toast.makeText(RegisterActivity.this,"Please add license image",Toast.LENGTH_LONG).show();
            return;

        }else if (insurance_image_selected.equals("no")) {
            Toast.makeText(RegisterActivity.this,"Please add insurance image",Toast.LENGTH_LONG).show();
            return;

        }else {
            ACProgressFlower dialog = new ACProgressFlower.Builder(RegisterActivity.this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.BLACK).build();
            dialog.show();
            String addresslatlng= edt_user_add.getText().toString();
            getLocationFromAddress(RegisterActivity.this,addresslatlng);

            RequestBody post_name = RequestBody.create(MediaType.parse("txt/plain"), edt_user_name.getText().toString().trim());
            RequestBody post_Phone = RequestBody.create(MediaType.parse("txt/plain"), edt_user_phone.getText().toString().trim());
            RequestBody post_Email = RequestBody.create(MediaType.parse("txt/plain"), edt_user_email.getText().toString().trim());
            RequestBody post_Address = RequestBody.create(MediaType.parse("txt/plain"), edt_user_add.getText().toString().trim());
            RequestBody post_address_lat = RequestBody.create(MediaType.parse("txt/plain"), String.valueOf(add_lat));
            RequestBody post_address_long = RequestBody.create(MediaType.parse("txt/plain"), String.valueOf(add_lng));
            RequestBody post_Postcode = RequestBody.create(MediaType.parse("txt/plain"), edt_user_postcode.getText().toString().trim());
            RequestBody Vtype = RequestBody.create(MediaType.parse("txt/plain"), v_id);
            RequestBody post_Vnumber = RequestBody.create(MediaType.parse("txt/plain"), edt_user_vehicle_no.getText().toString().trim());
            RequestBody post_vehicle_make = RequestBody.create(MediaType.parse("txt/plain"), edt_user_vehicle_make.getText().toString().trim());
            RequestBody post_License = RequestBody.create(MediaType.parse("txt/plain"), edt_user_license.getText().toString().trim());
            RequestBody post_LIC_exp = RequestBody.create(MediaType.parse("txt/plain"), edt_user_exp.getText().toString().trim());
            RequestBody post_InsNo = RequestBody.create(MediaType.parse("txt/plain"), edt_user_ins_number.getText().toString().trim());
            RequestBody post_conviction_points = RequestBody.create(MediaType.parse("txt/plain"), edt_conviction_points.getText().toString().trim());
            RequestBody post_conviction_points_reason = RequestBody.create(MediaType.parse("txt/plain"), edt_con_points_reason.getText().toString().trim());
            RequestBody post_conviction = RequestBody.create(MediaType.parse("txt/plain"), "test");
            RequestBody post_Password = RequestBody.create(MediaType.parse("txt/plain"), edt_user_password.getText().toString().trim());
            RequestBody post_Gender = RequestBody.create(MediaType.parse("txt/plain"), gender);
            RequestBody post_customer_preference = RequestBody.create(MediaType.parse("txt/plain"), customer_prefer);


            MultipartBody.Part lic_image= MultipartBody.Part.createFormData(
                    "license_image",
                    license_image.getName(),
                    RequestBody.create(MediaType.parse("file"), "license_image"));

            MultipartBody.Part ins_image= MultipartBody.Part.createFormData(
                    "insurance_certificate",
                    insurance_image.getName(),
                    RequestBody.create(MediaType.parse("file"), "insurance_image"));



          //  Log.e(TAG, "UpdateProfile: driver_id>>> "+driver_id.toString() );
            Log.e(TAG, "UpdateProfile: license_image>>> "+license_image );
            Log.e(TAG, "UpdateProfile: insurance_image>>> "+insurance_image );

            RestClient.getClient().signUp(post_name, post_Phone, post_Email, post_Address,
                    post_address_lat,post_address_long,post_Postcode,
                     Vtype, post_Vnumber, post_vehicle_make, post_License,
                    post_LIC_exp,post_InsNo,post_conviction_points,post_conviction_points_reason,
                    post_conviction, post_Password,post_Gender,post_customer_preference,
                   lic_image,ins_image

                    ).enqueue(new Callback<ResponseRegistration>() {
                @Override
                public void onResponse(Call<ResponseRegistration> call, Response<ResponseRegistration> response) {
                    Log.e(TAG, "onResponse: Code :" + response.body());
                    Log.e(TAG, "onResponse: " + response.code());
                    Log.e(TAG, "onResponse: " + response.message());
                    Log.e(TAG, "onResponse: " + response.errorBody());
                    assert response.body() != null;
                    if (response.body().getStatus().equals(200)) {
                        dialog.dismiss();


                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, response.body().getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();


//                      SAVE  DATA TO LOCAL STORAGE
                       /* SharedPreferences sp = getSharedPreferences(Constant.DRIVER_PREF, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        assert response.body() != null;

                        editor.putString(Constant.DRIVER_NAME, response.body().getResponse().getDriverName());
                        editor.putString(Constant.DRIVER_ID, String.valueOf(response.body().getResponse().getDriverId()));
                        editor.putString(Constant.DRIVER_ADDRESS, response.body().getResponse().getAddress());
                        editor.putString(Constant.DRIVER_MAIL, response.body().getResponse().getEmail());
                        editor.apply();*/
                       showDialogRegistrationDone(RegisterActivity.this);




                    } else {
                        dialog.dismiss();
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, response.body().getMessage(), Snackbar.LENGTH_LONG);

                        snackbar.show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseRegistration> call, Throwable t) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, t.getMessage(), Snackbar.LENGTH_LONG);

                    snackbar.show();
                    dialog.dismiss();
                }
            });

        }
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

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