package com.ats.exhibitionvisitor.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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
import com.ats.exhibitionvisitor.activity.EventInfoActivity;
import com.ats.exhibitionvisitor.activity.HomeActivity;
import com.ats.exhibitionvisitor.fragment.EventInfoFragment;
import com.ats.exhibitionvisitor.model.EventListModel;
import com.ats.exhibitionvisitor.model.EventModel;
import com.ats.exhibitionvisitor.model.EventSubscribeModel;
import com.ats.exhibitionvisitor.model.Visitor;
import com.ats.exhibitionvisitor.util.CommonDialog;
import com.ats.exhibitionvisitor.util.Constants;
import com.ats.exhibitionvisitor.util.CustomSharedPreference;
import com.ats.exhibitionvisitor.util.RetrofitClient;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    private ArrayList<EventListModel> eventList;
    private Context context;
    private int visitorId;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvDateTime, tvPlace;
        public ImageView imageView;
        public LinearLayout llEvent;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvDateTime = view.findViewById(R.id.tvDateTime);
            tvPlace = view.findViewById(R.id.tvPlace);
            imageView = view.findViewById(R.id.imageView);
            llEvent = view.findViewById(R.id.llEvent);
        }
    }

    public EventsAdapter(ArrayList<EventListModel> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    public EventsAdapter(ArrayList<EventListModel> eventList, Context context, int visitorId) {
        this.eventList = eventList;
        this.context = context;
        this.visitorId = visitorId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_events, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final EventListModel model = eventList.get(position);
        Log.e("Adapter : ", " model : " + model);

        holder.tvName.setText(model.getEventName());
        holder.tvPlace.setText(model.getEventLocation());
        holder.tvDateTime.setText(model.getEventFromDate() + " to " + model.getEventToDate() + " " + model.getToTime());

        try {
            Picasso.get().load(model.getEventLogo())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imageView);
        } catch (Exception e) {
        }


        holder.llEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (model.getSubscribeStatus() == 0) {
                    showSubscribeDialog(visitorId, model.getEventId());
                } else {
                    HomeActivity activity = (HomeActivity) context;

                    Fragment adf = new EventInfoFragment();
                    Bundle args = new Bundle();
                    args.putInt("eventId", model.getEventId());
                    adf.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


    private void showSubscribeDialog(final int visitorId, final int eventId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setMessage("Subscribe to the event");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                updateSubscribeStatus(visitorId, eventId, 1);

                // HomeActivity activity = (HomeActivity) context;
//
//                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.content_frame, new EventInfoFragment(), "HomeFragment");
//                ft.commit();

//                Fragment adf = new EventInfoFragment();
//                Bundle args = new Bundle();
//                args.putInt("eventId", eventId);
//                adf.setArguments(args);
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void updateSubscribeStatus(int visitorId, final int eventId, int status) {
        Log.e("PARAMETERS","--------------- VisitorId : "+visitorId+"   eventId : "+eventId+"   status : "+status );
        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<EventSubscribeModel> listCall = RetrofitClient.myInterface.updateSubscribeStatus(visitorId, eventId, status);
            listCall.enqueue(new Callback<EventSubscribeModel>() {
                @Override
                public void onResponse(Call<EventSubscribeModel> call, Response<EventSubscribeModel> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Event Data : ", "------------" + response.body());

                            EventSubscribeModel data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                                Toast.makeText(context, "Failed to subscribe", Toast.LENGTH_SHORT).show();
                            } else {

                                commonDialog.dismiss();

                                HomeActivity activity = (HomeActivity) context;

                                Fragment adf = new EventInfoFragment();
                                Bundle args = new Bundle();
                                args.putInt("eventId", eventId);
                                adf.setArguments(args);
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

                            }
                        } else {
                            commonDialog.dismiss();
                            Toast.makeText(context, "Failed to subscribe", Toast.LENGTH_SHORT).show();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Toast.makeText(context, "Failed to subscribe", Toast.LENGTH_SHORT).show();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<EventSubscribeModel> call, Throwable t) {
                    commonDialog.dismiss();
                    Toast.makeText(context, "Failed to subscribe", Toast.LENGTH_SHORT).show();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(context, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

}
