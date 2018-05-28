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
import com.ats.exhibitionvisitor.adapter.SponsorAdapter;
import com.ats.exhibitionvisitor.model.FloorMapModel;
import com.ats.exhibitionvisitor.model.SponsorModel;
import com.ats.exhibitionvisitor.util.CommonDialog;
import com.ats.exhibitionvisitor.util.Constants;
import com.ats.exhibitionvisitor.util.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FloorMapFragment extends Fragment {

    int eventId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_floor_map, container, false);

        eventId = getArguments().getInt("eventId");
        Log.e("EVENT ID","----"+eventId);

        getEventFloorMap(eventId);

        return view;
    }


    public void getEventFloorMap(int eventId) {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<FloorMapModel>> listCall = RetrofitClient.myInterface.getEventFloorMaap(eventId);
            listCall.enqueue(new Callback<ArrayList<FloorMapModel>>() {
                @Override
                public void onResponse(Call<ArrayList<FloorMapModel>> call, Response<ArrayList<FloorMapModel>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Floor Map Data : ", "------------" + response.body());

                            ArrayList<FloorMapModel> data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                                //Toast.makeText(SelectCityActivity.this, "No Cities Found !", Toast.LENGTH_SHORT).show();
                            } else {


                                commonDialog.dismiss();
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
                public void onFailure(Call<ArrayList<FloorMapModel>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


}
