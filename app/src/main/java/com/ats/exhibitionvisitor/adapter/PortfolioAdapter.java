package com.ats.exhibitionvisitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.model.MaterialModel;

import java.util.ArrayList;

public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.MyViewHolder> {


    private ArrayList<MaterialModel> portfolioList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvLink;
        public LinearLayout llPortfolio;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvLink = view.findViewById(R.id.tvLink);
            llPortfolio = view.findViewById(R.id.llPortfolio);
        }
    }

    public PortfolioAdapter(ArrayList<MaterialModel> portfolioList, Context context) {
        this.portfolioList = portfolioList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_portfolio, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MaterialModel model = portfolioList.get(position);
        Log.e("Adapter : ", " model : " + model);

        holder.tvName.setText(model.getMatName());
        holder.tvLink.setText(model.getMatLink());

    }

    @Override
    public int getItemCount() {
        return portfolioList.size();
    }


}
