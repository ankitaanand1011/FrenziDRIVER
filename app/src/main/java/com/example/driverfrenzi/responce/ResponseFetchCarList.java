package com.example.driverfrenzi.responce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFetchCarList {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("response")
    @Expose
    public List<Response> response = null;

    public ResponseFetchCarList(Integer status, String message, List<Response> response) {
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

        @SerializedName("vehicle_id")
        @Expose
        public Integer vehicleId;
        @SerializedName("vehicle_type")
        @Expose
        public String vehicleType;
        @SerializedName("ride_fare")
        @Expose
        public Integer rideFare;
        @SerializedName("image")
        @Expose
        public String image;


        public Response(Integer vehicleId, String vehicleType, Integer rideFare, String image) {
            this.vehicleId = vehicleId;
            this.vehicleType = vehicleType;
            this.rideFare = rideFare;
            this.image = image;

        }

        public Integer getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(Integer vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        public Integer getRideFare() {
            return rideFare;
        }

        public void setRideFare(Integer rideFare) {
            this.rideFare = rideFare;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }


    }
}
