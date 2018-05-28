package com.ats.exhibitionvisitor.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.activity.SelectCityActivity;
import com.ats.exhibitionvisitor.adapter.EventsAdapter;
import com.ats.exhibitionvisitor.adapter.ExhibitorAdapter;
import com.ats.exhibitionvisitor.model.EventListModel;
import com.ats.exhibitionvisitor.model.ExhibitorListModel;
import com.ats.exhibitionvisitor.model.ExhibitorModel;
import com.ats.exhibitionvisitor.model.Visitor;
import com.ats.exhibitionvisitor.util.CommonDialog;
import com.ats.exhibitionvisitor.util.Constants;
import com.ats.exhibitionvisitor.util.CustomSharedPreference;
import com.ats.exhibitionvisitor.util.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExhibitorsListFragment extends Fragment {

    private RecyclerView recyclerView;
    ExhibitorAdapter adapter;

    private ArrayList<ExhibitorListModel> exhibitorList = new ArrayList<>();

    int visitorId;
    static int eventId;

    Gson gson = new Gson();

    ArrayList<Integer> companyIds = new ArrayList<>();

    private BroadcastReceiver mBroadcastReceiver;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exhibitors_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        String jsonVisitor = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_VISITOR);
        final Visitor visitor = gson.fromJson(jsonVisitor, Visitor.class);
        if (visitor != null) {
            visitorId = visitor.getVisitorId();
        }

        try {
            eventId = getArguments().getInt("eventId");
        } catch (Exception e) {
        }

      /*  ArrayList<ExhibitorModel> exhibitorList = new ArrayList<>();
        exhibitorList.add(new ExhibitorModel(1, "Mr. Mukesh Ambani", "Reliance JIO", "ambani@gmail.com", "7897897897", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Reliance_Jio_Logo_%28October_2015%29.svg/1200px-Reliance_Jio_Logo_%28October_2015%29.svg.png"));
        exhibitorList.add(new ExhibitorModel(2, "Mr. Mukesh Ambani", "Reliance JIO", "ambani@gmail.com", "7897897897", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Reliance_Jio_Logo_%28October_2015%29.svg/1200px-Reliance_Jio_Logo_%28October_2015%29.svg.png"));
        exhibitorList.add(new ExhibitorModel(3, "Mr. Mukesh Ambani", "Reliance JIO", "ambani@gmail.com", "7897897897", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Reliance_Jio_Logo_%28October_2015%29.svg/1200px-Reliance_Jio_Logo_%28October_2015%29.svg.png"));
        exhibitorList.add(new ExhibitorModel(4, "Mr. Mukesh Ambani", "Reliance JIO", "ambani@gmail.com", "7897897897", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Reliance_Jio_Logo_%28October_2015%29.svg/1200px-Reliance_Jio_Logo_%28October_2015%29.svg.png"));
        exhibitorList.add(new ExhibitorModel(5, "Mr. Mukesh Ambani", "Reliance JIO", "ambani@gmail.com", "7897897897", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Reliance_Jio_Logo_%28October_2015%29.svg/1200px-Reliance_Jio_Logo_%28October_2015%29.svg.png"));
        exhibitorList.add(new ExhibitorModel(6, "Mr. Mukesh Ambani", "Reliance JIO", "ambani@gmail.com", "7897897897", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Reliance_Jio_Logo_%28October_2015%29.svg/1200px-Reliance_Jio_Logo_%28October_2015%29.svg.png"));
        exhibitorList.add(new ExhibitorModel(7, "Mr. Mukesh Ambani", "Reliance JIO", "ambani@gmail.com", "7897897897", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Reliance_Jio_Logo_%28October_2015%29.svg/1200px-Reliance_Jio_Logo_%28October_2015%29.svg.png"));
        exhibitorList.add(new ExhibitorModel(8, "Mr. Mukesh Ambani", "Reliance JIO", "ambani@gmail.com", "7897897897", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Reliance_Jio_Logo_%28October_2015%29.svg/1200px-Reliance_Jio_Logo_%28October_2015%29.svg.png"));
        exhibitorList.add(new ExhibitorModel(9, "Mr. Mukesh Ambani", "Reliance JIO", "ambani@gmail.com", "7897897897", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Reliance_Jio_Logo_%28October_2015%29.svg/1200px-Reliance_Jio_Logo_%28October_2015%29.svg.png"));
        exhibitorList.add(new ExhibitorModel(10, "Mr. Mukesh Ambani", "Reliance JIO", "ambani@gmail.com", "7897897897", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Reliance_Jio_Logo_%28October_2015%29.svg/1200px-Reliance_Jio_Logo_%28October_2015%29.svg.png"));


        adapter = new ExhibitorAdapter(exhibitorList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);*/

        companyIds.add(1);


        getExhibitorList(visitorId, 3, companyIds, 0);

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("RefreshExhibitorList")) {
                    handlePushNotification(intent);
                }
            }
        };


        return view;

    }


    @Override
    public void onPause() {
        Log.e("Select city", "  ON PAUSE");

        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mBroadcastReceiver,
                new IntentFilter("RefreshExhibitorList"));

    }

    private void handlePushNotification(Intent intent) {

        Log.e("handlePushNotification", "------------------------------------**********");

        getExhibitorList(visitorId, 3, companyIds, 0);
    }

    public void getExhibitorList(final int visitorId, final int eventId, ArrayList<Integer> companyIds, int status) {

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ExhibitorListModel>> listCall = RetrofitClient.myInterface.getExhibitorList(visitorId, eventId, companyIds, status);
            listCall.enqueue(new Callback<ArrayList<ExhibitorListModel>>() {
                @Override
                public void onResponse(Call<ArrayList<ExhibitorListModel>> call, Response<ArrayList<ExhibitorListModel>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Exhibitors Data : ", "------------" + response.body());

                            ArrayList<ExhibitorListModel> data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                                Toast.makeText(getContext(), "No exhibitors found.", Toast.LENGTH_SHORT).show();
                            } else {

                                exhibitorList.clear();
                                exhibitorList = data;

                                adapter = new ExhibitorAdapter(exhibitorList, getContext(), visitorId, eventId);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(adapter);

                                commonDialog.dismiss();
                            }
                        } else {
                            commonDialog.dismiss();
                            Toast.makeText(getContext(), "No exhibitors found.", Toast.LENGTH_SHORT).show();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Toast.makeText(getContext(), "No exhibitors found.", Toast.LENGTH_SHORT).show();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ExhibitorListModel>> call, Throwable t) {
                    commonDialog.dismiss();
                    Toast.makeText(getContext(), "No exhibitors found.", Toast.LENGTH_SHORT).show();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


}
