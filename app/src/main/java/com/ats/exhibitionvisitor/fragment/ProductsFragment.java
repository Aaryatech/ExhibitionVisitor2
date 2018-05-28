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
import com.ats.exhibitionvisitor.adapter.ProductsAdapter;
import com.ats.exhibitionvisitor.model.ExhibitorModel;
import com.ats.exhibitionvisitor.model.ProductByLikedStatus;
import com.ats.exhibitionvisitor.model.ProductModel;
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

public class ProductsFragment extends Fragment {

    int exhibitorId, visitorId, eventId;

    private ArrayList<ProductByLikedStatus> productList = new ArrayList<>();
    ProductsAdapter adapter;

    private RecyclerView recyclerView;

    Gson gson = new Gson();

    private BroadcastReceiver mBroadcastReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        String jsonVisitor = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_VISITOR);
        final Visitor visitor = gson.fromJson(jsonVisitor, Visitor.class);
        if (visitor != null) {
            visitorId = visitor.getVisitorId();
        }
        Log.e("VisitorID", " ------------------------  " + visitorId);

        try {
            exhibitorId = getArguments().getInt("exhibitorId");
            eventId = getArguments().getInt("eventId");
        } catch (Exception e) {
        }

        recyclerView = view.findViewById(R.id.recyclerView);

        getExhibitorProduct(exhibitorId, visitorId);

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("RefreshProduct")) {
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
                new IntentFilter("RefreshProduct"));
    }

    private void handlePushNotification(Intent intent) {

        Log.e("handlePushNotification", "------------------------------------**********");
        getExhibitorProduct(exhibitorId, visitorId);
    }

    public void getExhibitorProduct(final int exhibitorId, final int visitorId) {

        Log.e("Parameters : ", "--------exhibitorId : " + exhibitorId + "      visitorId : " + visitorId);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ProductByLikedStatus>> listCall = RetrofitClient.myInterface.getExhibitorProductsByLikeStatus(exhibitorId, visitorId);
            listCall.enqueue(new Callback<ArrayList<ProductByLikedStatus>>() {
                @Override
                public void onResponse(Call<ArrayList<ProductByLikedStatus>> call, Response<ArrayList<ProductByLikedStatus>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Products Data : ", "------------" + response.body());

                            ArrayList<ProductByLikedStatus> data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                            } else {

                                productList = data;

                                adapter = new ProductsAdapter(productList, getContext(), visitorId, eventId,exhibitorId);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(adapter);


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
                public void onFailure(Call<ArrayList<ProductByLikedStatus>> call, Throwable t) {
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
