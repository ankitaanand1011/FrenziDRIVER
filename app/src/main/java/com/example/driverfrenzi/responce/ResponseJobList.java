package com.example.driverfrenzi.responce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseJobList {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("response")
    @Expose
    public List<Response> response = null;

    public ResponseJobList(Integer status, String message, List<Response> response) {
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

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("image_icon")
        @Expose
        public String image_icon;

        @SerializedName("ride_id")
        @Expose
        public Integer ride_id;

        @SerializedName("user_id")
        @Expose
        public Integer user_id;

        @SerializedName("distance")
        @Expose
        public String distance;

        @SerializedName("pickup_address")
        @Expose
        public String pickup_address;

        @SerializedName("drop_address")
        @Expose
        public String drop_address;

        @SerializedName("amount")
        @Expose
        public String amount;

        @SerializedName("user_rating")
        @Expose
        public String user_rating;


        public Response(String name, String image_icon,Integer ride_id,
                        Integer user_id,  String distance,
                        String pickup_address, String drop_address,
                        String amount, String user_rating) {
            this.name = name;
            this.image_icon = image_icon;
            this.ride_id = ride_id;
            this.user_id = user_id;
            this.distance = distance;
            this.pickup_address = pickup_address;
            this.drop_address = drop_address;
            this.amount = amount;
            this.user_rating = user_rating;

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage_icon() {
            return image_icon;
        }

        public void setImage_icon(String image_icon) {
            this.image_icon = image_icon;
        }

        public Integer getRide_id() {
            return ride_id;
        }

        public void setRide_id(Integer ride_id) {
            this.ride_id = ride_id;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getPickup_address() {
            return pickup_address;
        }

        public void setPickup_address(String pickup_address) {
            this.pickup_address = pickup_address;
        }

        public String getDrop_address() {
            return drop_address;
        }

        public void setDrop_address(String drop_address) {
            this.drop_address = drop_address;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getUser_rating() {
            return user_rating;
        }

        public void setUser_rating(String user_rating) {
            this.user_rating = user_rating;
        }
    }
}
