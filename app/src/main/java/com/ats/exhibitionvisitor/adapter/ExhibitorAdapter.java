package com.ats.exhibitionvisitor.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.activity.HomeActivity;
import com.ats.exhibitionvisitor.fragment.ExhibitorsHomeFragment;
import com.ats.exhibitionvisitor.model.ExhibitorLikeStatusModel;
import com.ats.exhibitionvisitor.model.ExhibitorListModel;
import com.ats.exhibitionvisitor.util.CommonDialog;
import com.ats.exhibitionvisitor.util.Constants;
import com.ats.exhibitionvisitor.util.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExhibitorAdapter extends RecyclerView.Adapter<ExhibitorAdapter.MyViewHolder> {

    private ArrayList<ExhibitorListModel> exhibitorList;
    private Context context;
    int visitorId,eventId;



    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView tvName, tvCompany, tvEmail, tvMobile;
        public ImageView imageView, ivLike;
        public LinearLayout llExhibitor;


        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvCompany = view.findViewById(R.id.tvCompany);
            tvEmail = view.findViewById(R.id.tvEmail);
            tvMobile = view.findViewById(R.id.tvMobile);
            imageView = view.findViewById(R.id.imageView);
            ivLike = view.findViewById(R.id.ivLike);
            llExhibitor = view.findViewById(R.id.llExhibitor);

        }



    }

    public ExhibitorAdapter(ArrayList<ExhibitorListModel> exhibitorList, Context context) {
        this.exhibitorList = exhibitorList;
        this.context = context;
    }


    public ExhibitorAdapter(ArrayList<ExhibitorListModel> exhibitorList, Context context, int visitorId) {
        this.exhibitorList = exhibitorList;
        this.context = context;
        this.visitorId = visitorId;
    }

    public ExhibitorAdapter(ArrayList<ExhibitorListModel> exhibitorList, Context context, int visitorId, int eventId) {
        this.exhibitorList = exhibitorList;
        this.context = context;
        this.visitorId = visitorId;
        this.eventId = eventId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_exhibitor, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ExhibitorListModel model = exhibitorList.get(position);
        Log.e("Adapter : ", " model : " + model);

        holder.tvName.setText(model.getExhName());

        if (model.getLikeStatus() == 1) {
            holder.ivLike.setBackground(context.getResources().getDrawable(R.drawable.ic_subscribe));
        } else {
            holder.ivLike.setBackground(context.getResources().getDrawable(R.drawable.ic_like_gray));
        }

        holder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status = 0;
                if (model.getLikeStatus() == 0) {
                    status = 1;
                } else {
                    status = 0;
                }
                updateLikeStatus(visitorId, model.getExhId(),eventId, status);
            }
        });


        holder.llExhibitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity activity = (HomeActivity) context;


                Fragment adf = new ExhibitorsHomeFragment();
                Bundle args = new Bundle();
                args.putInt("exhibitorId", model.getExhId());
                args.putInt("eventId", eventId);
                adf.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "ExhibitorListFragment").commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        return exhibitorList.size();
    }


    public void updateLikeStatus(int visitorId, int exbId, int eventId, int status) {

        Log.e("PARAMETERS : ", "--------- visitorId : " + visitorId + "________ exbId : " + exbId + "________ status : " + status);

        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ExhibitorLikeStatusModel> listCall = RetrofitClient.myInterface.updateExhibitorLikeStatus(visitorId, exbId,eventId, status);
            listCall.enqueue(new Callback<ExhibitorLikeStatusModel>() {
                @Override
                public void onResponse(Call<ExhibitorLikeStatusModel> call, Response<ExhibitorLikeStatusModel> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Exibitor Data : ", "------------" + response.body());

                            ExhibitorLikeStatusModel data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                                //Toast.makeText(SelectCityActivity.this, "No Cities Found !", Toast.LENGTH_SHORT).show();
                            } else {
                                commonDialog.dismiss();

                                Intent pushNotificationIntent = new Intent();
                                pushNotificationIntent.setAction("RefreshExhibitorList");
                                LocalBroadcastManager.getInstance(context).sendBroadcast(pushNotificationIntent);


                            }
                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ExhibitorLikeStatusModel> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(context, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

}
