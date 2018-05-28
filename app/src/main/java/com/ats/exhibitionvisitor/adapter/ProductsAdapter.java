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
import android.widget.TextView;
import android.widget.Toast;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.activity.HomeActivity;
import com.ats.exhibitionvisitor.fragment.EventInfoFragment;
import com.ats.exhibitionvisitor.model.EventSubscribeModel;
import com.ats.exhibitionvisitor.model.ProductByLikedStatus;
import com.ats.exhibitionvisitor.model.ProductModel;
import com.ats.exhibitionvisitor.model.VisitorProduct;
import com.ats.exhibitionvisitor.util.CommonDialog;
import com.ats.exhibitionvisitor.util.Constants;
import com.ats.exhibitionvisitor.util.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private ArrayList<ProductByLikedStatus> productList;
    private Context context;
    int visitorId, eventId,exhibitorId;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvSpecification, tvDesc;
        public ImageView ivImage1, ivImage2, ivImage3, ivLike;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvSpecification = view.findViewById(R.id.tvSpecification);
            tvDesc = view.findViewById(R.id.tvDesc);
            ivImage1 = view.findViewById(R.id.ivImage1);
            ivImage2 = view.findViewById(R.id.ivImage2);
            ivImage3 = view.findViewById(R.id.ivImage3);
            ivLike = view.findViewById(R.id.ivLike);
        }
    }

    public ProductsAdapter(ArrayList<ProductByLikedStatus> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    public ProductsAdapter(ArrayList<ProductByLikedStatus> productList, Context context, int visitorId, int eventId) {
        this.productList = productList;
        this.context = context;
        this.visitorId = visitorId;
        this.eventId = eventId;
    }

    public ProductsAdapter(ArrayList<ProductByLikedStatus> productList, Context context, int visitorId, int eventId, int exhibitorId) {
        this.productList = productList;
        this.context = context;
        this.visitorId = visitorId;
        this.eventId = eventId;
        this.exhibitorId = exhibitorId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_product, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ProductByLikedStatus model = productList.get(position);
        Log.e("Adapter : ", " model : " + model);

        holder.tvName.setText(model.getProdName());
        holder.tvSpecification.setText(model.getProdSpecification());
        holder.tvDesc.setText(model.getProdDesc());

        try {
            Picasso.get().load(model.getProdImage1())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.ivImage1);


            Picasso.get().load(model.getProdImage2())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.ivImage2);

            Picasso.get().load(model.getProdImage3())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.ivImage3);
        } catch (Exception e) {
        }

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

                updateProductLikeStatus(visitorId, exhibitorId, model.getProdId(), eventId, status);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public void updateProductLikeStatus(int visitorId, int exhibitorId, int productId, int eventId, int status) {

        Log.e("PARAMETERS : ", "-----VisitorId : " + visitorId + "      exhibitorId : " + exhibitorId + "     productId : " + productId + "      eventId : " + eventId + "      status : " + status);

        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<VisitorProduct> listCall = RetrofitClient.myInterface.updateProductLikeStatus(visitorId, exhibitorId, productId, eventId, status);
            listCall.enqueue(new Callback<VisitorProduct>() {
                @Override
                public void onResponse(Call<VisitorProduct> call, Response<VisitorProduct> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Visitor Product Data : ", "------------" + response.body());

                            VisitorProduct data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                            } else {

                                commonDialog.dismiss();

                                Intent pushNotificationIntent = new Intent();
                                pushNotificationIntent.setAction("RefreshProduct");
                                LocalBroadcastManager.getInstance(context).sendBroadcast(pushNotificationIntent);


                            }
                        } else {
                            commonDialog.dismiss();
                            Toast.makeText(context, "Failed to Like", Toast.LENGTH_SHORT).show();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Toast.makeText(context, "Failed to Like", Toast.LENGTH_SHORT).show();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<VisitorProduct> call, Throwable t) {
                    commonDialog.dismiss();
                    Toast.makeText(context, "Failed to Like", Toast.LENGTH_SHORT).show();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(context, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


}
