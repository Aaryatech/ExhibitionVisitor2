package com.ats.exhibitionvisitor.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ats.exhibitionvisitor.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerImageAdapter extends PagerAdapter {

    private ArrayList<String> imageArray;
    private Context context;

    public BannerImageAdapter(ArrayList<String> imageArray, Context context) {
        this.imageArray = imageArray;
        this.context = context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageArray.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = LayoutInflater.from(view.getContext()).inflate(R.layout.adapter_banner_image, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout.findViewById(R.id.imageView);

        try {
            Log.e("Image : ", " - " + imageArray.get(position));
            Picasso.get()
                    .load(imageArray.get(position))
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView);
        } catch (Exception e) {
            Log.e("Image : ", " Exception - " + e.getMessage());
        }

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
