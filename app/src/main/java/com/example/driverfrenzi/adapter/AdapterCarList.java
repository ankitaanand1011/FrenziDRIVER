package com.example.driverfrenzi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.example.driverfrenzi.ProfileDetailActivity;
import com.example.driverfrenzi.R;
import com.example.driverfrenzi.responce.ResponseFetchCarList;

import java.util.ArrayList;
import java.util.List;

public class AdapterCarList extends RecyclerView.Adapter<AdapterCarList.MyViewHolder>{

    private static final String TAG = "AdapterResidentsFamilyL";
    private Context mContext;
    private final OnItemClickListener listener;
    private List<ResponseFetchCarList.Response> AssignmentList;
    private static final String USER_PREF = "USER_PREF";
    private static final String SHOP_NAME = "SHOP_NAME";
    private static final String SHOP_ADDRESS= "SHOP_ADDRESS";
    private static final String ORDER_STATUS= "ORDER_STATUS";
    private static final String ORDER_TOTAL_PRIZE= "ORDER_TOTAL_PRIZE";
    private static final String ORDER_ID= "ORDER_ID";
    private static final String ORDER_PLACED_ID= "ORDER_PLACED_ID";
    //FAMILY MEMEBER DETAIL DB
    private static final String SHOP_BANNER = "SHOP_BANNER";


    private Fragment callingFragment;
    int index=-1;



    public interface OnItemClickListener {
        void onItemClick(ResponseFetchCarList.Response item);


    }

    public AdapterCarList(Context mContext, List<ResponseFetchCarList.Response> AssignmentList, OnItemClickListener listener) {
        this.AssignmentList = AssignmentList;
        this.listener = listener;
        this.mContext=mContext;



    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                    int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_lv_single_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.txt_car_name.setText(AssignmentList.get(position).getVehicleType());
      //  holder.txt_price.setText("£" +AssignmentList.get(position).getRideFare());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(AssignmentList.get(position));
                index = position;
                Log.e(TAG, "onClick: adapter"+AssignmentList.get(position).getVehicleId() );
                notifyDataSetChanged();




               /* ProfileDetailActivity profileDetailActivity = new ProfileDetailActivity();
                profileDetailActivity.dismissAlert();*/
            }
        });

       /* if(index==position){
            holder.full_layout.setBackgroundColor(Color.parseColor("#2CD3D3D3"));
//            holder.txt_property_name.setTextColor(Color.BLACK);
        }
        else
        {
            holder.full_layout.setBackgroundColor(Color.parseColor("#ffffff"));
//            holder.txt_property_name.setTextColor(Color.RED);
        }*/

    }



    public void filterList(ArrayList<ResponseFetchCarList.Response> filterdNames) {
//        Log.e(TAG, "ADAPTER -- filterList: " + filterdNames );
        this.AssignmentList = filterdNames;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return AssignmentList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView txt_car_name,txt_price;
      ///  RelativeLayout full_layout;

        public MyViewHolder(View view) {
            super(view);

            txt_car_name = view.findViewById(R.id.txt_car_name);
        /*    txt_price=view.findViewById(R.id.txt_price);
            full_layout=view.findViewById(R.id.full_layout);*/



        }


    }
}