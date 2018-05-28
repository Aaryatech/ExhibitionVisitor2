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
import com.ats.exhibitionvisitor.adapter.PortfolioAdapter;
import com.ats.exhibitionvisitor.adapter.ProductsAdapter;
import com.ats.exhibitionvisitor.model.MaterialModel;
import com.ats.exhibitionvisitor.model.ProductModel;
import com.ats.exhibitionvisitor.util.CommonDialog;
import com.ats.exhibitionvisitor.util.Constants;
import com.ats.exhibitionvisitor.util.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PortfolioFragment extends Fragment {

    private RecyclerView recyclerView;

    int exhibitorId;

    PortfolioAdapter adapter;

    private ArrayList<MaterialModel> portfolioList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);

        try {
            exhibitorId = getArguments().getInt("exhibitorId");
        } catch (Exception e) {
        }

        getExhibitorPortfolio(exhibitorId);

        return view;
    }


    public void getExhibitorPortfolio(int exhibitorId) {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<MaterialModel>> listCall = RetrofitClient.myInterface.getExhibitorPortfolio(exhibitorId);
            listCall.enqueue(new Callback<ArrayList<MaterialModel>>() {
                @Override
                public void onResponse(Call<ArrayList<MaterialModel>> call, Response<ArrayList<MaterialModel>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Portfolio Data : ", "------------" + response.body());

                            ArrayList<MaterialModel> data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                            } else {

                                portfolioList = data;

                                adapter = new PortfolioAdapter(portfolioList, getContext());
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
                public void onFailure(Call<ArrayList<MaterialModel>> call, Throwable t) {
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
