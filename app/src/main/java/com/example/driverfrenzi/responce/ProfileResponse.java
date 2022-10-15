package com.example.driverfrenzi.responce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("response")
    @Expose
    public Response response;

    public ProfileResponse(Integer status, String message, Response response) {
        this.status = status;
        this.message = message;
        this.response = response;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
    public class Response {

        @SerializedName("driver_id")
        @Expose
        public Integer driver_id;

        @SerializedName("driver_name")
        @Expose
        public String driver_name;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("password")
        @Expose
        public String password;

        @SerializedName("address")
        @Expose
        public String address;

        @SerializedName("address_lat")
        @Expose
        public Object address_lat;

        @SerializedName("address_long")
        @Expose
        public Object address_long;

        @SerializedName("post_code")
        @Expose
        public String post_code;

        @SerializedName("phone")
        @Expose
        public String phone;

        @SerializedName("email")
        @Expose
        public String email;

        @SerializedName("vehicle_type")
        @Expose
        public Object vehicle_type;

        @SerializedName("vehicle_make")
        @Expose
        public String vehicle_make;

        @SerializedName("vehicle_no")
        @Expose
        public String vehicle_no;

        @SerializedName("license")
        @Expose
        public String license;

        @SerializedName("license_expiry")
        @Expose
        public String license_expiry;

        @SerializedName("license_image")
        @Expose
        public String license_image;

        @SerializedName("insurance_no")
        @Expose
        public String insurance_no;

        @SerializedName("insurance_certificate")
        @Expose
        public String insurance_certificate;

        @SerializedName("license_points")
        @Expose
        public Object license_points;

        @SerializedName("license_points_reason")
        @Expose
        public Object license_points_reason;

        @SerializedName("convictions")
        @Expose
        public String convictions;

        @SerializedName("image")
        @Expose
        public String image;

        @SerializedName("gender")
        @Expose
        public String gender;

        @SerializedName("customer_preference")
        @Expose
        public String customer_preference;






        public Response(Integer driver_id, String driver_name,String name,
                        String password,String address,
                        Object address_lat,
                        Object address_long, String post_code, String phone,
                        String email, Object vehicle_type,String vehicle_make,
                        String vehicle_no, String license, String license_expiry,
                        String license_image, String insurance_no,
                        String insurance_certificate, Object license_points,
                        Object license_points_reason,
                        String convictions, String image, String gender, String customer_preference) {
            this.driver_id = driver_id;
            this.driver_name = driver_name;
            this.name = name;
            this.password = password;
            this.address = address;
            this.address_lat = address_lat;
            this.address_long = address_long;
            this.post_code = post_code;
            this.phone = phone;
            this.email = email;
            this.vehicle_type = vehicle_type;
            this.vehicle_make = vehicle_make;
            this.vehicle_no = vehicle_no;
            this.license = license;
            this.license_expiry = license_expiry;
            this.license_image = license_image;
            this.insurance_no = insurance_no;
            this.insurance_certificate = insurance_certificate;
            this.license_points = license_points;
            this.license_points_reason = license_points_reason;
            this.convictions = convictions;
            this.image = image;
            this.gender = gender;
            this.customer_preference = customer_preference;
        }

        public Integer getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(Integer driver_id) {
            this.driver_id = driver_id;
        }

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getAddress_lat() {
            return address_lat;
        }

        public void setAddress_lat(Object address_lat) {
            this.address_lat = address_lat;
        }

        public Object getAddress_long() {
            return address_long;
        }

        public void setAddress_long(Object address_long) {
            this.address_long = address_long;
        }

        public String getPost_code() {
            return post_code;
        }

        public void setPost_code(String post_code) {
            this.post_code = post_code;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getVehicle_type() {
            return vehicle_type;
        }

        public void setVehicle_type(Object vehicle_type) {
            this.vehicle_type = vehicle_type;
        }

        public String getVehicle_make() {
            return vehicle_make;
        }

        public void setVehicle_make(String vehicle_make) {
            this.vehicle_make = vehicle_make;
        }

        public String getVehicle_no() {
            return vehicle_no;
        }

        public void setVehicle_no(String vehicle_no) {
            this.vehicle_no = vehicle_no;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLicense_expiry() {
            return license_expiry;
        }

        public void setLicense_expiry(String license_expiry) {
            this.license_expiry = license_expiry;
        }

        public String getLicense_image() {
            return license_image;
        }

        public void setLicense_image(String license_image) {
            this.license_image = license_image;
        }

        public String getInsurance_no() {
            return insurance_no;
        }

        public void setInsurance_no(String insurance_no) {
            this.insurance_no = insurance_no;
        }

        public String getInsurance_certificate() {
            return insurance_certificate;
        }

        public void setInsurance_certificate(String insurance_certificate) {
            this.insurance_certificate = insurance_certificate;
        }

        public Object getLicense_points() {
            return license_points;
        }

        public void setLicense_points(Object license_points) {
            this.license_points = license_points;
        }

        public Object getLicense_points_reason() {
            return license_points_reason;
        }

        public void setLicense_points_reason(Object license_points_reason) {
            this.license_points_reason = license_points_reason;
        }

        public String getConvictions() {
            return convictions;
        }

        public void setConvictions(String convictions) {
            this.convictions = convictions;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCustomer_preference() {
            return customer_preference;
        }

        public void setCustomer_preference(String customer_preference) {
            this.customer_preference = customer_preference;
        }
    }
}
