package com.ats.exhibitionvisitor.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.model.CompanyTypeModel;
import com.ats.exhibitionvisitor.model.ExhibitorModel;
import com.ats.exhibitionvisitor.util.CommonDialog;
import com.ats.exhibitionvisitor.util.Constants;
import com.ats.exhibitionvisitor.util.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExhibitorsHomeFragment extends Fragment {

    static int exhibitorId,eventId;

    private TextView tvComp, tvExbName, tvCompany, tvName, tvEmail, tvMobile;
    private ImageView ivLogo;
    private LinearLayout llProduct, llPortfolio;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exhibitors_home, container, false);

        try {
            exhibitorId = getArguments().getInt("exhibitorId");
            eventId=getArguments().getInt("eventId");
        } catch (Exception e) {
        }

        tvComp = view.findViewById(R.id.tvComp);
        tvExbName = view.findViewById(R.id.tvExbName);
        tvCompany = view.findViewById(R.id.tvCompany);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvMobile = view.findViewById(R.id.tvMobile);

        llProduct = view.findViewById(R.id.llProduct);
        llPortfolio = view.findViewById(R.id.llPortfolio);

        ivLogo = view.findViewById(R.id.ivLogo);

        getExhibitorInfo(exhibitorId);

        llProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment adf = new ProductsFragment();
                Bundle args = new Bundle();
                args.putInt("exhibitorId", exhibitorId);
                args.putInt("eventId", eventId);
                adf.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "ExhibitorHomeFragment").commit();
            }
        });

        llPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment adf = new PortfolioFragment();
                Bundle args = new Bundle();
                args.putInt("exhibitorId", exhibitorId);
                adf.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "ExhibitorHomeFragment").commit();
            }
        });


        return view;
    }


    public void getExhibitorInfo(int exhibitorId) {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ExhibitorModel> listCall = RetrofitClient.myInterface.getExhibitorInfo(exhibitorId);
            listCall.enqueue(new Callback<ExhibitorModel>() {
                @Override
                public void onResponse(Call<ExhibitorModel> call, Response<ExhibitorModel> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Company Data : ", "------------" + response.body());

                            ExhibitorModel data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                            } else {

                                tvComp.setText("" + data.getExhCompany());
                                tvExbName.setText("" + data.getExhName());

                                tvCompany.setText("" + data.getExhCompany());
                                tvName.setText("" + data.getExhName());
                                tvEmail.setText("" + data.getPersonEmail1() + ", " + data.getPersonEmail2());
                                tvMobile.setText("" + data.getPersonMob1() + ", " + data.getPersonMob2());

                                try {
                                    Picasso.get().load(data.getLogo())
                                            .placeholder(R.drawable.ic_launcher_background)
                                            .error(R.drawable.ic_launcher_background)
                                            .into(ivLogo);
                                } catch (Exception e) {
                                }

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
                public void onFailure(Call<ExhibitorModel> call, Throwable t) {
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
