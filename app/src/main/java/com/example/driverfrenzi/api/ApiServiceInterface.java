package com.example.driverfrenzi.api;



import com.example.driverfrenzi.responce.FPAssResponse;
import com.example.driverfrenzi.responce.LoginResponse;
import com.example.driverfrenzi.responce.ProfileResponse;
import com.example.driverfrenzi.responce.ResponseAboutUs;
import com.example.driverfrenzi.responce.ResponseFetchCarList;
import com.example.driverfrenzi.responce.ResponseFetchRideHistory;
import com.example.driverfrenzi.responce.ResponseJobDetails;
import com.example.driverfrenzi.responce.ResponseJobList;
import com.example.driverfrenzi.responce.ResponseReferCode;
import com.example.driverfrenzi.responce.ResponseRegistration;
import com.example.driverfrenzi.responce.ResponseStartRide;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServiceInterface {

    String BASE_URL = "https://mobileappsgamesstudio.com/works/frenzi_new/api/";


    @Multipart
    @POST("driver_reg")
    Call<ResponseRegistration> signUp(
      /*      @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("password") RequestBody passowrd,
            @Part("address") RequestBody address,
            @Part("post_code") RequestBody post_code,
            @Part("vehicle_type") RequestBody vehicle_type,
            @Part("vehicle_no") RequestBody vic_no,
            @Part("license") RequestBody lic,
            @Part("license_expiry") RequestBody lic_exp,
            @Part("address_lat") RequestBody address_lat,
            @Part("address_long") RequestBody address_long,
            @Part("vehicle_make") RequestBody vehicle_make,
            @Part("license_points") RequestBody convition_points,
            @Part("insurance_no") RequestBody ins_no,
            @Part("insurance_no") RequestBody ins_no,*/

            @Part("name") RequestBody name,
            @Part("phone") RequestBody phone,
            @Part("email") RequestBody email,
            @Part("address") RequestBody address,
            @Part("address_lat") RequestBody address_lat,
            @Part("address_long") RequestBody address_long,
            @Part("post_code") RequestBody post_code,
            @Part("vehicle_type") RequestBody vehicle_type,
            @Part("vehicle_no") RequestBody vehicle_no,
            @Part("vehicle_make") RequestBody vehicle_make,
            @Part("license") RequestBody license,
            @Part("license_expiry") RequestBody license_expiry,
            @Part("insurance_no") RequestBody insurance_no,
            @Part("license_points") RequestBody conviction_points,
            @Part("license_points_reason") RequestBody conviction_points_reason,
            @Part("convictions") RequestBody conviction,
            @Part("password") RequestBody password,
            @Part("gender") RequestBody gender,
            @Part("customer_preference") RequestBody customer_preference,
            @Part MultipartBody.Part license_image,
            @Part MultipartBody.Part insurance_certificate


    );

    @Multipart
    @POST("driver_login")
    Call<LoginResponse> Login(
            @Part("email") RequestBody referal,
            @Part("password") RequestBody pass

    );

    @Multipart
    @POST("driver_forget_password")
    Call<FPAssResponse> ForgetPassword(
            @Part("email") RequestBody email


    );

    @Multipart
    @POST("driver_reset_password")
    Call<ServerGeneralResponse> ResetPassword(
            @Part("email") RequestBody email,
            @Part("otp_code") RequestBody otp_code,
            @Part("new_password") RequestBody new_password


    );

    @Multipart
    @POST("roaming_radius")
    Call<ServerGeneralResponse> RoamingRadius(
            @Part("driver_id") RequestBody driver_id,
            @Part("roaming_radius") RequestBody roaming_radius



    );

    @Multipart
    @POST("driver_availibilty")
    Call<ServerGeneralResponse> DriverAvailability(
            @Part("driver_id") RequestBody driver_id,
            @Part("avaiable_status") RequestBody available_status

    );

    @Multipart
    @POST("generate_driver_refer_key")
    Call<ResponseReferCode> GenerateCode(
            @Part("driver_id") RequestBody driver_id

    );


    @Multipart
    @POST("contact_submit")
    Call<ServerGeneralResponse> ContactUs(
            @Part("fullname") RequestBody fullname,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("message") RequestBody message


    );

    @Multipart
    @POST("about_us")
    Call<ResponseAboutUs> AboutUs(
            @Part("body") RequestBody body


    );

    @Multipart
    @POST("job_request_list")
    Call<ResponseJobList> FetchJobList(
            @Part("driver_id") RequestBody driver_id


    );

    @Multipart
    @POST("driver_ride_history")
    Call<ResponseFetchRideHistory> FetchRideHistory(
            @Part("driver_id") RequestBody driver_id,
            @Part("from_date") RequestBody fromDate,
            @Part("to_date") RequestBody toDate

    );

    @Multipart
    @POST("vehicle_type_list")
    Call<ResponseFetchCarList> fetchCarlist(
            @Part("list") RequestBody list


    );

    @Multipart
    @POST("driver_profile")
    Call<ProfileResponse> DriverProfile(
            @Part("driver_id") RequestBody driver_id
    );

    @Multipart
    @POST("ride_details")
    Call<ResponseJobDetails> FetchRideDetails(

            @Part("ride_id") RequestBody ride_id
    );

    @Multipart
    @POST("accept_ride")
    Call<ResponseJobDetails> AcceptRide(
            @Part("ride_id") RequestBody ride_id,
            @Part("driver_id") RequestBody driver_id,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude
    );

    @Multipart
    @POST("driver_give_reviews")
    Call<ServerGeneralResponse>DriverReviews(
            @Part("ride_id") RequestBody ride_id,
            @Part("user_id") RequestBody user_id,
            @Part("driver_id") RequestBody driver_id,
            @Part("ratings") RequestBody ratings,
            @Part("review_comment") RequestBody review_comment,
            @Part("tip_amount") RequestBody tip_amount
    );

    @Multipart
    @POST("ride_otp_verification")
    Call<ServerGeneralResponse> OtpVerify(
            @Part("ride_id") RequestBody ride_id,
            @Part("otp") RequestBody otp
    );

    @Multipart
    @POST("start_ride")
    Call<ResponseStartRide> StartRide(
            @Part("ride_id") RequestBody ride_id
    );

    @Multipart
    @POST("arrived_ride")
    Call<ResponseStartRide> RideArrived(
            @Part("ride_id") RequestBody ride_id
    );

    @Multipart
    @POST("finish_ride")
    Call<ResponseStartRide> FinishRide(
            @Part("ride_id") RequestBody ride_id,
            @Part("payment_status") RequestBody payment_status
    );

    @Multipart
    @POST("extra_pay")
    Call<ServerGeneralResponse> ExtraPay(
            @Part("ride_id") RequestBody ride_id,
            @Part("drop_off_charges") RequestBody drop_off_charges,
            @Part("toll_charges") RequestBody toll_charges,
            @Part("luggage_charges") RequestBody luggage_charges
    );



    @Multipart
    @POST("driver_profile_update")
    Call<ProfileResponse> UpdateDriverProfile(
            @Part("driver_id") RequestBody driver_id,
            @Part("name") RequestBody name,
            @Part("phone") RequestBody phone,
            @Part("email") RequestBody email,
            @Part("address") RequestBody address,
            @Part("address_lat") RequestBody address_lat,
            @Part("address_long") RequestBody address_long,
            @Part("post_code") RequestBody post_code,
            @Part("vehicle_type") RequestBody vehicle_type,
            @Part("vehicle_no") RequestBody vehicle_no,
            @Part("vehicle_make") RequestBody vehicle_make,
            @Part("license") RequestBody license,
            @Part("license_expiry") RequestBody license_expiry,
            @Part("insurance_no") RequestBody insurance_no,
            @Part("conviction_points") RequestBody conviction_points,
            @Part("license_points_reason") RequestBody conviction_points_reason,
            @Part("gender") RequestBody gender,
            @Part("customer_preference") RequestBody customer_preference,




            @Part MultipartBody.Part license_image,
            @Part MultipartBody.Part insurance_certificate,
            @Part MultipartBody.Part image_icon



    );







}
