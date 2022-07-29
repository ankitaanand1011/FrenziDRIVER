package com.example.driverfrenzi.responce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFetchRideHistory {
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("response")
    @Expose
    public List<Response> response = null;

    public ResponseFetchRideHistory(Integer status, String message, List<Response> response) {
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

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public class Response {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("driver_id")
        @Expose
        public Integer driverId;
        @SerializedName("pickup_short_address")
        @Expose
        public Object pickupShortAddress;
        @SerializedName("drop_short_address")
        @Expose
        public Object dropShortAddress;
        @SerializedName("pickup_address")
        @Expose
        public String pickupAddress;
        @SerializedName("drop_address")
        @Expose
        public String dropAddress;
        @SerializedName("pickup_location")
        @Expose
        public String pickupLocation;
        @SerializedName("drop_location")
        @Expose
        public String dropLocation;
        @SerializedName("distance")
        @Expose
        public String distance;
        @SerializedName("total_time")
        @Expose
        public Object totalTime;
        @SerializedName("coupon_code")
        @Expose
        public String couponCode;
        @SerializedName("coupon_status")
        @Expose
        public String couponStatus;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("cancel_reason")
        @Expose
        public String cancelReason;
        @SerializedName("payment_status")
        @Expose
        public String paymentStatus;
        @SerializedName("amount")
        @Expose
        public String amount;
        @SerializedName("time")
        @Expose
        public String time;
        @SerializedName("start_status")
        @Expose
        public Integer startStatus;
        @SerializedName("start_date")
        @Expose
        public Object startDate;
        @SerializedName("start_time")
        @Expose
        public Object startTime;
        @SerializedName("end_date")
        @Expose
        public Object endDate;
        @SerializedName("end_time")
        @Expose
        public Object endTime;
        @SerializedName("tip_amount")
        @Expose
        public Object tipAmount;
        @SerializedName("ride_otp")
        @Expose
        public Object rideOtp;

        public Response(Integer id, Integer userId, Integer driverId, Object pickupShortAddress, Object dropShortAddress, String pickupAddress, String dropAddress, String pickupLocation, String dropLocation, String distance, Object totalTime, String couponCode, String couponStatus, String status, String cancelReason, String paymentStatus, String amount, String time, Integer startStatus, Object startDate, Object startTime, Object endDate, Object endTime, Object tipAmount, Object rideOtp) {
            this.id = id;
            this.userId = userId;
            this.driverId = driverId;
            this.pickupShortAddress = pickupShortAddress;
            this.dropShortAddress = dropShortAddress;
            this.pickupAddress = pickupAddress;
            this.dropAddress = dropAddress;
            this.pickupLocation = pickupLocation;
            this.dropLocation = dropLocation;
            this.distance = distance;
            this.totalTime = totalTime;
            this.couponCode = couponCode;
            this.couponStatus = couponStatus;
            this.status = status;
            this.cancelReason = cancelReason;
            this.paymentStatus = paymentStatus;
            this.amount = amount;
            this.time = time;
            this.startStatus = startStatus;
            this.startDate = startDate;
            this.startTime = startTime;
            this.endDate = endDate;
            this.endTime = endTime;
            this.tipAmount = tipAmount;
            this.rideOtp = rideOtp;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getDriverId() {
            return driverId;
        }

        public void setDriverId(Integer driverId) {
            this.driverId = driverId;
        }

        public Object getPickupShortAddress() {
            return pickupShortAddress;
        }

        public void setPickupShortAddress(Object pickupShortAddress) {
            this.pickupShortAddress = pickupShortAddress;
        }

        public Object getDropShortAddress() {
            return dropShortAddress;
        }

        public void setDropShortAddress(Object dropShortAddress) {
            this.dropShortAddress = dropShortAddress;
        }

        public String getPickupAddress() {
            return pickupAddress;
        }

        public void setPickupAddress(String pickupAddress) {
            this.pickupAddress = pickupAddress;
        }

        public String getDropAddress() {
            return dropAddress;
        }

        public void setDropAddress(String dropAddress) {
            this.dropAddress = dropAddress;
        }

        public String getPickupLocation() {
            return pickupLocation;
        }

        public void setPickupLocation(String pickupLocation) {
            this.pickupLocation = pickupLocation;
        }

        public String getDropLocation() {
            return dropLocation;
        }

        public void setDropLocation(String dropLocation) {
            this.dropLocation = dropLocation;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public Object getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(Object totalTime) {
            this.totalTime = totalTime;
        }

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public String getCouponStatus() {
            return couponStatus;
        }

        public void setCouponStatus(String couponStatus) {
            this.couponStatus = couponStatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCancelReason() {
            return cancelReason;
        }

        public void setCancelReason(String cancelReason) {
            this.cancelReason = cancelReason;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Integer getStartStatus() {
            return startStatus;
        }

        public void setStartStatus(Integer startStatus) {
            this.startStatus = startStatus;
        }

        public Object getStartDate() {
            return startDate;
        }

        public void setStartDate(Object startDate) {
            this.startDate = startDate;
        }

        public Object getStartTime() {
            return startTime;
        }

        public void setStartTime(Object startTime) {
            this.startTime = startTime;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
            this.endDate = endDate;
        }

        public Object getEndTime() {
            return endTime;
        }

        public void setEndTime(Object endTime) {
            this.endTime = endTime;
        }

        public Object getTipAmount() {
            return tipAmount;
        }

        public void setTipAmount(Object tipAmount) {
            this.tipAmount = tipAmount;
        }

        public Object getRideOtp() {
            return rideOtp;
        }

        public void setRideOtp(Object rideOtp) {
            this.rideOtp = rideOtp;
        }
    }
}
