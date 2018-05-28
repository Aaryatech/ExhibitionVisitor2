package com.ats.exhibitionvisitor.activity;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.adapter.BannerImageAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class EventInfoActivity extends AppCompatActivity {

    private ViewPager bannerViewPager;
    private ArrayList<String> imageArray = new ArrayList<>();
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        bannerViewPager = findViewById(R.id.bannerViewPager);

        setBannerImages();

    }


    public void setBannerImages() {
        imageArray.add("https://i.ndtvimg.com/i/2018-02/motorsport-exhibits-auto-expo-2018-collage_827x510_81519558592.jpg");
        imageArray.add("https://gaadiwaadi.com/wp-content/uploads/2016/02/Renault-RS01-Race-Car-Concept.jpg");
        imageArray.add("https://static.digit.in/default/d4cc8b150b61aa9f5f09a2aa20fbf9af8cdb10f8.jpeg");
        imageArray.add("https://storage.googleapis.com/gtspirit/uploads/2016/02/Mercedes-Benz-S-Class-Cabriolet-Delhi-Auto-Expo-2016-2.jpg");
        imageArray.add("https://www.livemint.com/rf/Image-621x414/LiveMint/Period1/2016/02/04/Photos/raudi-kz0C--621x414@LiveMint.jpg");

        bannerViewPager.setAdapter(new BannerImageAdapter(imageArray, this));

        NUM_PAGES = imageArray.size();

        // Auto start viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                bannerViewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 8000);

        bannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

}