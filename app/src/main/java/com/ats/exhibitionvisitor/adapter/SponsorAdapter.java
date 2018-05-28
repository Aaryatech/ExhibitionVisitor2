package com.ats.exhibitionvisitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.model.SponsorModel;

import java.util.ArrayList;

public class SponsorAdapter extends RecyclerView.Adapter<SponsorAdapter.MyViewHolder> {

    private ArrayList<SponsorModel> sponnsorList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvCompany, tvRemark, tvMobile, tvEmail, tvDesignation;
        public LinearLayout llSponsorBack;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvCompany = view.findViewById(R.id.tvCompany);
            tvRemark = view.findViewById(R.id.tvRemark);
            tvMobile = view.findViewById(R.id.tvMobile);
            tvEmail = view.findViewById(R.id.tvEmail);
            tvDesignation = view.findViewById(R.id.tvDesignation);
            llSponsorBack=view.findViewById(R.id.llSponsorBack);
        }
    }

    public SponsorAdapter(ArrayList<SponsorModel> sponnsorList, Context context) {
        this.sponnsorList = sponnsorList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_sponsor, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SponsorModel model = sponnsorList.get(position);
        Log.e("Adapter : ", " model : " + model);

        holder.tvName.setText(model.getName());
        holder.tvCompany.setText(model.getCompanyTypeName());
        holder.tvMobile.setText(model.getMobile());
        holder.tvEmail.setText(model.getEmail());
        holder.tvDesignation.setText(model.getDesignation());
        holder.tvRemark.setText(model.getRemark());

        if (position%2==0){
            holder.llSponsorBack.setBackgroundColor(context.getResources().getColor(R.color.light_blue));
        }else{
            holder.llSponsorBack.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }

    }

    @Override
    public int getItemCount() {
        return sponnsorList.size();
    }

}
