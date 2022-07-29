package com.example.driverfrenzi.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.driverfrenzi.JobDetailsActivity;
import com.example.driverfrenzi.JobRequestActivity;
import com.example.driverfrenzi.ProfileDetailActivity;
import com.example.driverfrenzi.R;
import com.example.driverfrenzi.responce.ResponseFetchRideHistory;
import com.example.driverfrenzi.responce.ResponseJobList;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AdapterJobRequest extends RecyclerView.Adapter<AdapterJobRequest.MyViewHolder> {

    private static final String TAG = "AdapterJobRequest";
    private Context mContext;
    private final OnItemClickListener listener;
    private List<ResponseJobList.Response> rideHistoryList;
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

    double current_lat, current_long;
    private Fragment callingFragment;
    int index = -1;


    public interface OnItemClickListener {
        void onItemClick(ResponseJobList.Response item);

    }

    public AdapterJobRequest(Activity activity, Context mContext,
                             List<ResponseJobList.Response> rideHistoryList,
                             double current_lat,double current_long,
                             OnItemClickListener listener ) {
        this.activity = activity;
        this.rideHistoryList = rideHistoryList;
        this.listener = listener;
        this.mContext = mContext;
        this.current_lat = current_lat;
        this.current_long = current_long;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                      int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_job_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        String  pickup = rideHistoryList.get(position).getPickup_address();
        String  drop = rideHistoryList.get(position).getDrop_address();

        holder.tv_distance.setText(rideHistoryList.get(position).getDistance());
        holder.tv_amount.setText("£"+ String.valueOf(rideHistoryList.get(position).getAmount()));
        holder.tv_customer_name.setText(rideHistoryList.get(position).getName());
        holder.tv_cus_rating.setText(rideHistoryList.get(position).getUser_rating());

        RequestOptions options = new RequestOptions()
                .centerInside()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile);
        Glide.with(mContext).load(rideHistoryList.get(position).getImage_icon())
                .apply(options).into(holder.iv_cus_image);

        Log.e(TAG, "onBindViewHolder: current_lat> "+current_lat );
        Log.e(TAG, "onBindViewHolder: current_long> "+current_long );


        holder.tv_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ride_id = String.valueOf(rideHistoryList.get(position).getRide_id());
                Intent acc=new Intent(mContext, JobDetailsActivity.class);
                acc.putExtra("ride_id",ride_id);
                acc.putExtra("current_lat",current_lat);
                acc.putExtra("current_long",current_long);
                activity.startActivity(acc);

            }
        });

        StringTokenizer pickup_tokens = new StringTokenizer(pickup, ",");
        String pickup_first = pickup_tokens.nextToken();// this will contain "12345"
        String pickup_second = pickup_tokens.nextToken();


        holder.txt_pickup_add.setText(pickup_first);
        holder.txt_pickup_add2.setText(pickup_second);

        StringTokenizer drop_tokens = new StringTokenizer(drop, ",");
        String drop_first = drop_tokens.nextToken();
        String drop_second = drop_tokens.nextToken();
        holder.txt_drop_add.setText(drop_first);
        holder.txt_drop_add2.setText(drop_second);

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


    public void filterList(ArrayList<ResponseJobList.Response> filterdNames) {
//        Log.e(TAG, "ADAPTER -- filterList: " + filterdNames );
        this.rideHistoryList = filterdNames;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return rideHistoryList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView txt_pickup_add,txt_pickup_add2, txt_drop_add,txt_drop_add2,
                tv_distance, tv_amount,tv_customer_name,
                tv_cus_rating, tv_accept;
        CircularImageView iv_cus_image;

        public MyViewHolder(View view) {
            super(view);

            txt_pickup_add = view.findViewById(R.id.txt_pickup_add);
            txt_pickup_add2 = view.findViewById(R.id.txt_pickup_add2);
            txt_drop_add = view.findViewById(R.id.txt_drop_add);
            txt_drop_add2 = view.findViewById(R.id.txt_drop_add2);
            tv_distance = view.findViewById(R.id.tv_distance);
            tv_cus_rating = view.findViewById(R.id.tv_cus_rating);
            tv_customer_name=view.findViewById(R.id.tv_customer_name);
            tv_amount=view.findViewById(R.id.tv_amount);
            tv_accept=view.findViewById(R.id.tv_accept);
            iv_cus_image=view.findViewById(R.id.iv_cus_image);

        }


    }
}