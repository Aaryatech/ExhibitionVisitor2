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
import com.ats.exhibitionvisitor.adapter.LikedExhibitorAdapter;
import com.ats.exhibitionvisitor.adapter.LikedProductAdapter;
import com.ats.exhibitionvisitor.adapter.ProductsAdapter;
import com.ats.exhibitionvisitor.model.ExhibitorModel;
import com.ats.exhibitionvisitor.model.ProductModel;
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

public class LikedProductFragment extends Fragment {

    private RecyclerView recyclerView;
    private LikedProductAdapter adapter;
    int visitorId;
    private Gson gson = new Gson();

    private ArrayList<ProductModel> productList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liked_product, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        String jsonVisitor = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_VISITOR);
        final Visitor visitor = gson.fromJson(jsonVisitor, Visitor.class);
        if (visitor != null) {
            visitorId = visitor.getVisitorId();
        }
        Log.e("VisitorID", " ------------------------  " + visitorId);

        getLikedProducts(visitorId);

        return view;
    }


    public void getLikedProducts(int visitorId) {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ProductModel>> listCall = RetrofitClient.myInterface.getLikedProducts(visitorId);
            listCall.enqueue(new Callback<ArrayList<ProductModel>>() {
                @Override
                public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Products Data : ", "------------" + response.body());

                            ArrayList<ProductModel> data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                                Toast.makeText(getContext(), "No exhibitors found.", Toast.LENGTH_SHORT).show();
                            } else {
                                productList = data;
                                adapter = new LikedProductAdapter(productList, getContext());
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
                public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
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
