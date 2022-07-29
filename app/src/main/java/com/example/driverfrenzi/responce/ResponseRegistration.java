package com.example.driverfrenzi.responce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRegistration {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("response")
    @Expose
    public Response response;

    public ResponseRegistration(Integer status, String message, Response response) {
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

        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("phone")
        @Expose
        public String phone;
        @SerializedName("password")
        @Expose
        public String password;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("post_code")
        @Expose
        public String postCode;
        @SerializedName("vehicle_type")
        @Expose
        public String vehicleType;
        @SerializedName("vehicle_no")
        @Expose
        public String vehicleNo;
        @SerializedName("license")
        @Expose
        public String license;
        @SerializedName("license_expiry")
        @Expose
        public String licenseExpiry;
        @SerializedName("insurance_no")
        @Expose
        public String insuranceNo;
        @SerializedName("driver_id")
        @Expose
        public Integer driverId;
        @SerializedName("driver_name")
        @Expose
        public String driverName;
        @SerializedName("license_image")
        @Expose
        public String licenseImage;
        @SerializedName("insurance_certificate")
        @Expose
        public String insuranceCertificate;

        public Response(String name, String email, String phone, String password, String address, String postCode, String vehicleType, String vehicleNo, String license, String licenseExpiry, String insuranceNo, Integer driverId, String driverName, String licenseImage, String insuranceCertificate) {
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.password = password;
            this.address = address;
            this.postCode = postCode;
            this.vehicleType = vehicleType;
            this.vehicleNo = vehicleNo;
            this.license = license;
            this.licenseExpiry = licenseExpiry;
            this.insuranceNo = insuranceNo;
            this.driverId = driverId;
            this.driverName = driverName;
            this.licenseImage = licenseImage;
            this.insuranceCertificate = insuranceCertificate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        public String getVehicleNo() {
            return vehicleNo;
        }

        public void setVehicleNo(String vehicleNo) {
            this.vehicleNo = vehicleNo;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLicenseExpiry() {
            return licenseExpiry;
        }

        public void setLicenseExpiry(String licenseExpiry) {
            this.licenseExpiry = licenseExpiry;
        }

        public String getInsuranceNo() {
            return insuranceNo;
        }

        public void setInsuranceNo(String insuranceNo) {
            this.insuranceNo = insuranceNo;
        }

        public Integer getDriverId() {
            return driverId;
        }

        public void setDriverId(Integer driverId) {
            this.driverId = driverId;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getLicenseImage() {
            return licenseImage;
        }

        public void setLicenseImage(String licenseImage) {
            this.licenseImage = licenseImage;
        }

        public String getInsuranceCertificate() {
            return insuranceCertificate;
        }

        public void setInsuranceCertificate(String insuranceCertificate) {
            this.insuranceCertificate = insuranceCertificate;
        }
    }
}
