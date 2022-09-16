package com.example.driverfrenzi.api;



import com.example.driverfrenzi.responce.FPAssResponse;
import com.example.driverfrenzi.responce.LoginResponse;
import com.example.driverfrenzi.responce.ProfileResponse;
import com.example.driverfrenzi.responce.ResponseAboutUs;
import com.example.driverfrenzi.responce.ResponseFetchCarList;
import com.example.driverfrenzi.responce.ResponseFetchRideHistory;
import com.example.driverfrenzi.responce.ResponseJobDetails;
import com.example.driverfrenzi.responce.ResponseJobList;
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
            @Part("password") RequestBody passowrd,
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
            @Part("ride_date") RequestBody ride_date


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
     



            @Part MultipartBody.Part license_image,
            @Part MultipartBody.Part insurance_certificate,
            @Part MultipartBody.Part image_icon



    );






//    @Multipart
//    @POST("services")
//    Call<ResponceLogin> Login(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("email") RequestBody email,
//            @Part("password") RequestBody pass
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponceGeneralWithUserId> SubmitPersonalProfile(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id,
//            @Part("gender") RequestBody gender,
//            @Part("age") RequestBody age,
//            @Part("height") RequestBody height,
//            @Part("weight") RequestBody weight,
//            @Part("allergies") RequestBody aller,
//            @Part("foodddislike") RequestBody flike,
//            @Part("activity_lavel") RequestBody level
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponceSubmitPaymentData> SubmitPaymentData(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id,
//            @Part("payment_amt") RequestBody gender,
//            @Part("package_id") RequestBody age,
//            @Part("tran_id") RequestBody height,
//            @Part("country") RequestBody country,
//            @Part("package_amt") RequestBody weiamtght,
//            @Part("coupon_code") RequestBody code,
//            @Part("coupon_discount") RequestBody discount
//    );
//    @Multipart
//    @POST("services")
//    Call<com.example.nuapplication.responce.ServerGeneralResponse> SubmitCompitition_Image(
//
//            @Part MultipartBody.Part imageFile,
//
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id,
//            @Part("name") RequestBody gender,
//            @Part("type") RequestBody age
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponceFetchMeal6Week> FetchMeal6Week(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id,
//            @Part("week") RequestBody gender,
//            @Part("day") RequestBody age,
//            @Part("mealTypeId") RequestBody height
//    );
//
//    @Multipart
//    @POST("services")
//    Call<ResponceFetchMySelectedMeal6Week> FetchMySelectedMeal6Week(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id,
//            @Part("week") RequestBody gender,
//            @Part("day") RequestBody age,
//            @Part("mealTypeId") RequestBody height
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponceSelectMeal6week> SelectMeal6Week(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id,
//            @Part("week") RequestBody week,
//            @Part("day") RequestBody day,
//            @Part("mealTypeId") RequestBody type,
//            @Part("meal_id") RequestBody id,
//            @Part("mealCalorie") RequestBody cal
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponcefetchDayListOne2One> FetchDayListOne2One(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponcefetchDayListOne2One> FetchDayListOne2OneWorkout(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponceFetchMyMealOne2one> FetchMealOne2One(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id,
//            @Part("masterId") RequestBody gender,
//            @Part("mealTypeId") RequestBody age
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponceRecipeIngradients> FetchRecipeIngradients(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id,
//            @Part("meal_day_id") RequestBody gender,
//            @Part("user_meal_id") RequestBody age,
//            @Part("meal_id") RequestBody meal_id
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponceRecipeIngradients> FetchRecipeIngradients6Week(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id,
//            @Part("meal_id") RequestBody gender,
//            @Part("mealCalorie") RequestBody age
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponceWorkoutCategory6week> FetchWorkoutCategory6Week(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id,
//            @Part("week") RequestBody gender
////            @Part("day") RequestBody age
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponceSelectWorkout6Week> SubmitWorkoutSelection6Week(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id,
//            @Part("workout_day_id") RequestBody gender,
//            @Part("user_workout_id") RequestBody age
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponceFetchWorkoutList6Week> FetchWorkoutList6Week(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id,
//            @Part("workout_id") RequestBody w_id
//    );
//    @Multipart
//    @POST("services")
//    Call<ResonceFetchWorkoutDetails> FetchWorkoutDetails(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("excerciseId") RequestBody user_id
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponceFetchWorkoutlistCategoryOne2one> FetchWorkoutCategoryOne2One(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id,
//            @Part("masterId") RequestBody gender
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponceClassJordanAvailability> FetchAvailability(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("month") RequestBody gg,
//            @Part("year") RequestBody year
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponceFetchTimeSlotList> FetchAvailabilitySlot(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("available_date") RequestBody gg
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponceSetCalBack> SubmitJordanAppoinment(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id,
//            @Part("slot_id") RequestBody gender
//    );
//    @Multipart
//    @POST("services")
//    Call<Responcemessege> FetchMessegeList(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id
//    );
//
//    @Multipart
//    @POST("services")
//    Call<ResponceSetUserImage> UpdateProfileImage(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id,
//            @Part MultipartBody.Part wash_image
//
//            );
//
//    @Multipart
//    @POST("services")
//    Call<ResponceUpdateProfileData> UpdateProfileDetails(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id,
//            @Part("age") RequestBody age,
//            @Part("height") RequestBody height,
//            @Part("weight") RequestBody weight,
//            @Part("gender") RequestBody gender,
//            @Part("activity_lavel") RequestBody activity_level,
//            @Part("foodddislike") RequestBody foodDislike,
//            @Part("allergies") RequestBody allergies
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponcEsendSms> SendMessege(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id,
//            @Part("message") RequestBody age,
//            @Part("sender") RequestBody height
//    );
//
//    @Multipart
//    @POST("services")
//    Call<ResponceFetchProfileDetails> FetchProfileDetails(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody id
//    );
//
//    @Multipart
//    @POST("services")
//    Call<ResponceApplyCoumpon> CouponApply(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("coupon_code") RequestBody user_id,
//            @Part("package_id") RequestBody type
//
//    );
//
//    @Multipart
//    @POST("services")
//    Call<ResponceValidatePackage> validate(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key
//
//    );
//
//    @Multipart
//    @POST("services")
//    Call<ResponceFetchMainMessegeList> FetchMainMessegeListAdmin(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key
//    );
//    @Multipart
//    @POST("services")
//    Call<ResponceChangeMessageStatus> ChangeMessageStatus(
//            @Part("action") RequestBody action,
//            @Part("api_key") RequestBody key,
//            @Part("cust_id") RequestBody user_id
//
//    );
}
