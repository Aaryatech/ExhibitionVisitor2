package com.ats.exhibitionvisitor.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.activity.SelectCityActivity;
import com.ats.exhibitionvisitor.adapter.BannerImageAdapter;
import com.ats.exhibitionvisitor.adapter.EventsAdapter;
import com.ats.exhibitionvisitor.adapter.SponsorAdapter;
import com.ats.exhibitionvisitor.model.CityModel;
import com.ats.exhibitionvisitor.model.EventGalleryModel;
import com.ats.exhibitionvisitor.model.EventModel;
import com.ats.exhibitionvisitor.model.SponsorModel;
import com.ats.exhibitionvisitor.util.CommonDialog;
import com.ats.exhibitionvisitor.util.Constants;
import com.ats.exhibitionvisitor.util.CustomSharedPreference;
import com.ats.exhibitionvisitor.util.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventInfoFragment extends Fragment {

    private ViewPager bannerViewPager;
    private ArrayList<String> imageArray = new ArrayList<>();
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private LinearLayout llExhibitorsList, llFloorMap, llScanQR;

    static int eventId;

    private TextView tvEventName, tvEventDate, tvEventPlace, tvEventDesc, tvEventContactPerson, tvEventMobile, tvEventEmail, tvExhibitorCount;
    private ImageView ivEventImage;
    private RecyclerView rvSponsors;

    SponsorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_info, container, false);

        tvEventName = view.findViewById(R.id.tvEventName);
        tvEventDate = view.findViewById(R.id.tvEventDate);
        tvEventPlace = view.findViewById(R.id.tvEventPlace);
        tvEventDesc = view.findViewById(R.id.tvEventDesc);
        tvEventContactPerson = view.findViewById(R.id.tvEventContactPerson);
        tvEventMobile = view.findViewById(R.id.tvEventMobile);
        tvEventEmail = view.findViewById(R.id.tvEventEmail);
        tvExhibitorCount = view.findViewById(R.id.tvExhibitorCount);

        ivEventImage = view.findViewById(R.id.ivEventImage);

        rvSponsors = view.findViewById(R.id.rvSponsors);

        bannerViewPager = view.findViewById(R.id.bannerViewPager);
        llExhibitorsList = view.findViewById(R.id.llExhibitorList);
        llFloorMap = view.findViewById(R.id.llFloorMap);
        llScanQR = view.findViewById(R.id.llScanQR);

        try {
            eventId = getArguments().getInt("eventId");
        } catch (Exception e) {
        }

        llExhibitorsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment adf = new ExhibitorsListFragment();
                Bundle args = new Bundle();
                args.putInt("eventId", eventId);
                adf.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "EventInfoFragment").commit();

            }
        });

        llFloorMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment adf = new FloorMapFragment();
                Bundle args = new Bundle();
                args.putInt("eventId", eventId);
                adf.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "EventInfoFragment").commit();
            }
        });

        llScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new ExhibitorsListFragment(), "EventInfoFragment");
                ft.commit();
            }
        });

      //  setBannerImages();

        getEventInfo(eventId);
        getEventExhibitorCount(eventId);
        getEventSponsorList(eventId);
        getEventGallery(eventId);

        return view;
    }


    public void setBannerImages(ArrayList<String> imageArray) {
//        imageArray.add("https://i.ndtvimg.com/i/2018-02/motorsport-exhibits-auto-expo-2018-collage_827x510_81519558592.jpg");
//        imageArray.add("https://gaadiwaadi.com/wp-content/uploads/2016/02/Renault-RS01-Race-Car-Concept.jpg");
//        imageArray.add("https://static.digit.in/default/d4cc8b150b61aa9f5f09a2aa20fbf9af8cdb10f8.jpeg");
//        imageArray.add("https://storage.googleapis.com/gtspirit/uploads/2016/02/Mercedes-Benz-S-Class-Cabriolet-Delhi-Auto-Expo-2016-2.jpg");
//        imageArray.add("https://www.livemint.com/rf/Image-621x414/LiveMint/Period1/2016/02/04/Photos/raudi-kz0C--621x414@LiveMint.jpg");

        bannerViewPager.setAdapter(new BannerImageAdapter(imageArray, getContext()));

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


    public void getEventInfo(int eventId) {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<EventModel> listCall = RetrofitClient.myInterface.getEventInfo(eventId);
            listCall.enqueue(new Callback<EventModel>() {
                @Override
                public void onResponse(Call<EventModel> call, Response<EventModel> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Event Data : ", "------------" + response.body());

                            EventModel data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                                //Toast.makeText(SelectCityActivity.this, "No Cities Found !", Toast.LENGTH_SHORT).show();
                            } else {

                                tvEventName.setText(data.getEventName());
                                tvEventDate.setText(data.getEventFromDate() + " to " + data.getEventToDate() + " " + data.getFromTime() + " to " + data.getToTime());
                                tvEventPlace.setText(data.getEventLocation() + ", " + data.getLocationName());
                                tvEventDesc.setText(data.getAboutEvent());
                                tvEventContactPerson.setText(data.getContactPersonName1() + ", " + data.getContactPersonName2());
                                tvEventMobile.setText(data.getPerson1Mob() + ", " + data.getContactPersonName2());
                                tvEventEmail.setText(data.getPerson1EmailId() + ", " + data.getPerson2EmailId());

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
                public void onFailure(Call<EventModel> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void getEventExhibitorCount(int eventId) {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Integer> listCall = RetrofitClient.myInterface.getEventExhibitorCount(eventId);
            listCall.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Exhibitor Count : ", "------------" + response.body());

                            Integer data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                                //Toast.makeText(SelectCityActivity.this, "No Cities Found !", Toast.LENGTH_SHORT).show();
                            } else {
                                tvExhibitorCount.setText("" + data);

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
                public void onFailure(Call<Integer> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void getEventSponsorList(int eventId) {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<SponsorModel>> listCall = RetrofitClient.myInterface.getEventSponsorList(eventId);
            listCall.enqueue(new Callback<ArrayList<SponsorModel>>() {
                @Override
                public void onResponse(Call<ArrayList<SponsorModel>> call, Response<ArrayList<SponsorModel>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Sponsor Data : ", "------------" + response.body());

                            ArrayList<SponsorModel> data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                                //Toast.makeText(SelectCityActivity.this, "No Cities Found !", Toast.LENGTH_SHORT).show();
                            } else {

                                adapter = new SponsorAdapter(data, getContext());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                rvSponsors.setLayoutManager(mLayoutManager);
                                rvSponsors.setItemAnimator(new DefaultItemAnimator());
                                rvSponsors.setAdapter(adapter);

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
                public void onFailure(Call<ArrayList<SponsorModel>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void getEventGallery(int eventId) {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<EventGalleryModel>> listCall = RetrofitClient.myInterface.getEventGallery(eventId);
            listCall.enqueue(new Callback<ArrayList<EventGalleryModel>>() {
                @Override
                public void onResponse(Call<ArrayList<EventGalleryModel>> call, Response<ArrayList<EventGalleryModel>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Gallery Data : ", "------------" + response.body());

                            ArrayList<EventGalleryModel> data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                                //Toast.makeText(SelectCityActivity.this, "No Cities Found !", Toast.LENGTH_SHORT).show();
                            } else {
                                imageArray.clear();
                                for (int i = 0; i < data.size(); i++) {
                                    imageArray.add("" + data.get(i).getPhotoLink());
                                }
                                setBannerImages(imageArray);

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
                public void onFailure(Call<ArrayList<EventGalleryModel>> call, Throwable t) {
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
