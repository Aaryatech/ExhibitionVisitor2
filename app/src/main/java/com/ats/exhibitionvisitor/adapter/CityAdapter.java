package com.ats.exhibitionvisitor.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.activity.SelectCityActivity;
import com.ats.exhibitionvisitor.model.CityModel;
import com.ats.exhibitionvisitor.util.CustomSharedPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.ats.exhibitionvisitor.activity.SelectCityActivity.cityMap;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> {


    private ArrayList<CityModel> citytList;
    private Context context;
    boolean isTouched = false;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkBox);
        }
    }

    public CityAdapter(ArrayList<CityModel> citytList, Context context) {
        this.citytList = citytList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_city, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CityModel model = citytList.get(position);
        Log.e("Adapter : ", " model : " + model);

        holder.checkBox.setText(model.getLocationName());

        if (model.isCheckedStatus()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }


        isTouched = false;
        holder.checkBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isTouched = true;
                return false;
            }
        });


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isTouched) {
                    isTouched = false;
                    if (isChecked) {
                        model.setCheckedStatus(true);
                        cityMap.put(model.getLocationId(), true);

                    } else {
                        model.setCheckedStatus(false);
                        cityMap.put(model.getLocationId(), false);

                        Intent pushNotificationIntent = new Intent();
                        pushNotificationIntent.setAction("Checkbox");
                        LocalBroadcastManager.getInstance(context).sendBroadcast(pushNotificationIntent);

                    }

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return citytList.size();
    }


}
