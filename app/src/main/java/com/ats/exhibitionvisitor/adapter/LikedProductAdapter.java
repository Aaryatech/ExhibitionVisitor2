package com.ats.exhibitionvisitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.model.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LikedProductAdapter extends RecyclerView.Adapter<LikedProductAdapter.MyViewHolder>{

    private ArrayList<ProductModel> productList;
    private Context context;

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

    public LikedProductAdapter(ArrayList<ProductModel> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_product, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ProductModel model = productList.get(position);
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


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }



}
