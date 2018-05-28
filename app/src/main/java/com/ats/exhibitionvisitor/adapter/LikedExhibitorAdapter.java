package com.ats.exhibitionvisitor.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.activity.HomeActivity;
import com.ats.exhibitionvisitor.fragment.ExhibitorsHomeFragment;
import com.ats.exhibitionvisitor.model.ExhibitorListModel;
import com.ats.exhibitionvisitor.model.ExhibitorModel;

import java.util.ArrayList;

public class LikedExhibitorAdapter extends RecyclerView.Adapter<LikedExhibitorAdapter.MyViewHolder> {

    private ArrayList<ExhibitorModel> exhibitorList;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvCompany, tvEmail, tvMobile;
        public ImageView imageView;
        public LinearLayout llExhibitor;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvCompany = view.findViewById(R.id.tvCompany);
            tvEmail = view.findViewById(R.id.tvEmail);
            tvMobile = view.findViewById(R.id.tvMobile);
            imageView = view.findViewById(R.id.imageView);
            llExhibitor = view.findViewById(R.id.llExhibitor);

        }

    }

    public LikedExhibitorAdapter(ArrayList<ExhibitorModel> exhibitorList, Context context) {
        this.exhibitorList = exhibitorList;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_liked_exhibitor, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ExhibitorModel model = exhibitorList.get(position);
        Log.e("Adapter : ", " model : " + model);


        holder.tvName.setText(model.getExhName());
        holder.tvCompany.setText(model.getExhCompany());
        holder.tvEmail.setText(model.getPersonEmail1() + ", " + model.getPersonEmail2());
        holder.tvMobile.setText(model.getPersonMob1() + ", " + model.getPersonMob2());

        holder.llExhibitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity activity = (HomeActivity) context;

                Fragment adf = new ExhibitorsHomeFragment();
                Bundle args = new Bundle();
                args.putInt("exhibitorId", model.getExhId());
                adf.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "ExhibitorListFragment").commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        return exhibitorList.size();
    }


}
