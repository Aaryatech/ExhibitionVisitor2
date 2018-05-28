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
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.model.CompanyTypeModel;

import java.util.ArrayList;

import static com.ats.exhibitionvisitor.fragment.HomeFragment.industryMap;

public class IndustryTypeAdapter extends RecyclerView.Adapter<IndustryTypeAdapter.MyViewHolder> {

    private ArrayList<CompanyTypeModel> industryList;
    private Context context;
    boolean isTouched = false;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkBox);
        }
    }

    public IndustryTypeAdapter(ArrayList<CompanyTypeModel> industryList, Context context) {
        this.industryList = industryList;
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
        final CompanyTypeModel model = industryList.get(position);
        Log.e("Adapter : ", " model : " + model);

        holder.checkBox.setText(model.getCompanyTypeName());

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
                        industryMap.put(model.getCompanyTypeId(), true);

                    } else {
                        model.setCheckedStatus(false);
                        industryMap.put(model.getCompanyTypeId(), false);

                        Intent pushNotificationIntent = new Intent();
                        pushNotificationIntent.setAction("CheckboxEventIndustry");
                        LocalBroadcastManager.getInstance(context).sendBroadcast(pushNotificationIntent);

                    }

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return industryList.size();
    }


}
