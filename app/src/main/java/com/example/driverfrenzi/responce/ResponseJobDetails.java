package com.example.driverfrenzi.responce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseJobDetails {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("response")
    @Expose
    public Response response;

    public ResponseJobDetails(Integer status, String message, Response response) {
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

        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("driver_id")
        @Expose
        public Integer driverId;
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
        public String totalTime;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("amount")
        @Expose
        public String amount;
        @SerializedName("start_date")
        @Expose
        public String startDate;
        @SerializedName("start_time")
        @Expose
        public String startTime;
        @SerializedName("end_date")
        @Expose
        public String endDate;
        @SerializedName("end_time")
        @Expose
        public String endTime;
        @SerializedName("ride_otp")
        @Expose
        public Object rideOtp;

        @SerializedName("fare_cost")
        @Expose
        public String fare_cost;

        @SerializedName("discount")
        @Expose
        public String discount;

        @SerializedName("user_details")
        @Expose
        public UserDetails userDetails;
        @SerializedName("driver_details")
        @Expose
        public DriverDetails driverDetails;

        public Response(Integer userId, Integer driverId, String pickupAddress,
                        String dropAddress, String pickupLocation,
                        String dropLocation, String distance,
                        String totalTime, String status, String amount,
                        String startDate, String startTime, String endDate,
                        String endTime, Object rideOtp, String fare_cost,
                        String discount,
                        UserDetails userDetails, DriverDetails driverDetails) {
            this.userId = userId;
            this.driverId = driverId;
            this.pickupAddress = pickupAddress;
            this.dropAddress = dropAddress;
            this.pickupLocation = pickupLocation;
            this.dropLocation = dropLocation;
            this.distance = distance;
            this.totalTime = totalTime;
            this.status = status;
            this.amount = amount;
            this.startDate = startDate;
            this.startTime = startTime;
            this.endDate = endDate;
            this.endTime = endTime;
            this.fare_cost = fare_cost;
            this.rideOtp = rideOtp;
            this.discount = discount;
            this.userDetails = userDetails;
            this.driverDetails = driverDetails;
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

        public String getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(String totalTime) {
            this.totalTime = totalTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Object getRideOtp() {
            return rideOtp;
        }

        public void setRideOtp(Object rideOtp) {
            this.rideOtp = rideOtp;
        }

        public String getFare_cost() {
            return fare_cost;
        }

        public void setFare_cost(String fare_cost) {
            this.fare_cost = fare_cost;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public UserDetails getUserDetails() {
            return userDetails;
        }

        public void setUserDetails(UserDetails userDetails) {
            this.userDetails = userDetails;
        }

        public DriverDetails getDriverDetails() {
            return driverDetails;
        }

        public void setDriverDetails(DriverDetails driverDetails) {
            this.driverDetails = driverDetails;
        }
        public class UserDetails {

            @SerializedName("name")
            @Expose
            public String name;
            @SerializedName("user_id")
            @Expose
            public Integer userId;
            @SerializedName("email")
            @Expose
            public String email;
            @SerializedName("phone")
            @Expose
            public Object phone;
            @SerializedName("reviews")
            @Expose
            public String reviews;
            @SerializedName("image")
            @Expose
            public String image;

            public UserDetails(String name, Integer userId, String email, Object phone, String reviews, String image) {
                this.name = name;
                this.userId = userId;
                this.email = email;
                this.phone = phone;
                this.reviews = reviews;
                this.image = image;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getUserId() {
                return userId;
            }

            public void setUserId(Integer userId) {
                this.userId = userId;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public Object getPhone() {
                return phone;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }

            public String getReviews() {
                return reviews;
            }

            public void setReviews(String reviews) {
                this.reviews = reviews;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
        public class DriverDetails {

            @SerializedName("name")
            @Expose
            public String name;
            @SerializedName("driver_id")
            @Expose
            public Integer driverId;
            @SerializedName("email")
            @Expose
            public String email;
            @SerializedName("phone")
            @Expose
            public String phone;
            @SerializedName("reviews")
            @Expose
            public String reviews;
            @SerializedName("vehicle_no")
            @Expose
            public String vehicleNo;
            @SerializedName("vehicle_type")
            @Expose
            public String vehicleType;
            @SerializedName("image")
            @Expose
            public String image;

            public DriverDetails(String name, Integer driverId, String email, String phone, String reviews, String vehicleNo, String vehicleType, String image) {
                this.name = name;
                this.driverId = driverId;
                this.email = email;
                this.phone = phone;
                this.reviews = reviews;
                this.vehicleNo = vehicleNo;
                this.vehicleType = vehicleType;
                this.image = image;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getDriverId() {
                return driverId;
            }

            public void setDriverId(Integer driverId) {
                this.driverId = driverId;
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

            public String getReviews() {
                return reviews;
            }

            public void setReviews(String reviews) {
                this.reviews = reviews;
            }

            public String getVehicleNo() {
                return vehicleNo;
            }

            public void setVehicleNo(String vehicleNo) {
                this.vehicleNo = vehicleNo;
            }

            public String getVehicleType() {
                return vehicleType;
            }

            public void setVehicleType(String vehicleType) {
                this.vehicleType = vehicleType;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }

    }
}
