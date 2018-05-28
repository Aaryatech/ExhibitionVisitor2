package com.ats.exhibitionvisitor.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.adapter.EventsAdapter;
import com.ats.exhibitionvisitor.adapter.LikedExhibitorAdapter;
import com.ats.exhibitionvisitor.model.EventListModel;
import com.ats.exhibitionvisitor.model.ExhibitorModel;
import com.ats.exhibitionvisitor.model.Visitor;
import com.ats.exhibitionvisitor.util.CommonDialog;
import com.ats.exhibitionvisitor.util.Constants;
import com.ats.exhibitionvisitor.util.CustomSharedPreference;
import com.ats.exhibitionvisitor.util.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikedExhibitorFragment extends Fragment {

    private RecyclerView recyclerView;

    private ArrayList<ExhibitorModel> exhibitorList = new ArrayList<>();

    int visitorId;

    private LikedExhibitorAdapter adapter;

    Gson gson = new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liked_exhibitor, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        String jsonVisitor = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_VISITOR);
        final Visitor visitor = gson.fromJson(jsonVisitor, Visitor.class);
        if (visitor != null) {
            visitorId = visitor.getVisitorId();
        }
        Log.e("VisitorID", " ------------------------  " + visitorId);


        getLikedExhibitor(visitorId);

        return view;
    }


    public void getLikedExhibitor(int visitorId) {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ExhibitorModel>> listCall = RetrofitClient.myInterface.getLikedExhibitor(visitorId);
            listCall.enqueue(new Callback<ArrayList<ExhibitorModel>>() {
                @Override
                public void onResponse(Call<ArrayList<ExhibitorModel>> call, Response<ArrayList<ExhibitorModel>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Exhibitor Data : ", "------------" + response.body());

                            ArrayList<ExhibitorModel> data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                                Toast.makeText(getContext(), "No exhibitors found.", Toast.LENGTH_SHORT).show();
                            } else {
                                exhibitorList = data;
                                adapter = new LikedExhibitorAdapter(exhibitorList, getContext());
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
                public void onFailure(Call<ArrayList<ExhibitorModel>> call, Throwable t) {
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
