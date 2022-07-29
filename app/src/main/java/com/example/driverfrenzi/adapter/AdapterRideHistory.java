package com.example.driverfrenzi.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driverfrenzi.R;
import com.example.driverfrenzi.responce.ResponseFetchRideHistory;

import java.util.ArrayList;
import java.util.List;

public class AdapterRideHistory extends RecyclerView.Adapter<AdapterRideHistory.MyViewHolder> {

    private static final String TAG = "AdapterResidentsFamilyL";
    private Context mContext;
    private final OnItemClickListener listener;
    private List<ResponseFetchRideHistory.Response> rideHistoryList;
    Activity activity;
    private static final String USER_PREF = "USER_PREF";
    private static final String SHOP_NAME = "SHOP_NAME";
    private static final String SHOP_ADDRESS = "SHOP_ADDRESS";
    private static final String ORDER_STATUS = "ORDER_STATUS";
    private static final String ORDER_TOTAL_PRIZE = "ORDER_TOTAL_PRIZE";
    private static final String ORDER_ID = "ORDER_ID";
    private static final String ORDER_PLACED_ID = "ORDER_PLACED_ID";
    //FAMILY MEMEBER DETAIL DB
    private static final String SHOP_BANNER = "SHOP_BANNER";
    AlertDialog alertDialog;
    String Status;


    private Fragment callingFragment;
    int index = -1;


    public interface OnItemClickListener {
        void onItemClick(ResponseFetchRideHistory.Response item);

    }

    public AdapterRideHistory(Activity activity, Context mContext, List<ResponseFetchRideHistory.Response> rideHistoryList, OnItemClickListener listener) {
        this.activity = activity;
        this.rideHistoryList = rideHistoryList;
        this.listener = listener;
        this.mContext = mContext;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                      int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ride_history, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.txt_pickup_add.setText(rideHistoryList.get(position).getPickupAddress());
        holder.txt_drop_add.setText(rideHistoryList.get(position).getDropAddress());
        holder.txt_pickup_time.setText(String.valueOf(rideHistoryList.get(position).getStartTime()));
        holder.txt_drop_time.setText(String.valueOf(rideHistoryList.get(position).getEndTime()));
        holder.txt_amount.setText("£"+ String.valueOf(rideHistoryList.get(position).getAmount()));
        holder.tv_status.setText(rideHistoryList.get(position).getStatus());

//
//        holder.btn_select.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                popUppayment();
//
//            }
//        });

//        holder.full_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                index = position;
//                notifyDataSetChanged();
//            }
//        });

//        if (index == position) {
//            holder.full_layout.setBackgroundColor(Color.parseColor("#2CD3D3D3"));
////            holder.txt_property_name.setTextColor(Color.BLACK);
//        } else {
//            holder.full_layout.setBackgroundColor(Color.parseColor("#ffffff"));
////            holder.txt_property_name.setTextColor(Color.RED);
//        }

    }


    public void filterList(ArrayList<ResponseFetchRideHistory.Response> filterdNames) {
//        Log.e(TAG, "ADAPTER -- filterList: " + filterdNames );
        this.rideHistoryList = filterdNames;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return rideHistoryList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView txt_pickup_add, txt_drop_add, txt_pickup_time,
                txt_drop_time,btn,txt_amount, tv_status;

        public MyViewHolder(View view) {
            super(view);

            txt_pickup_add = view.findViewById(R.id.txt_pickup_add);
            txt_drop_add = view.findViewById(R.id.txt_drop_add);
            txt_pickup_time = view.findViewById(R.id.txt_pickup_time);
            txt_drop_time = view.findViewById(R.id.txt_drop_time);
         //   btn=view.findViewById(R.id.btn);
            txt_amount=view.findViewById(R.id.tv_amount);
            tv_status=view.findViewById(R.id.tv_status);

        }


    }
}