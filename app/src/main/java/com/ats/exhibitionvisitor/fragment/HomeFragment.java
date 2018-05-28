package com.ats.exhibitionvisitor.fragment;


import android.app.Dialog;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.activity.LoginActivity;
import com.ats.exhibitionvisitor.activity.RegistrationActivity;
import com.ats.exhibitionvisitor.activity.SelectCityActivity;
import com.ats.exhibitionvisitor.adapter.CityAdapter;
import com.ats.exhibitionvisitor.adapter.EventsAdapter;
import com.ats.exhibitionvisitor.adapter.IndustryTypeAdapter;
import com.ats.exhibitionvisitor.filterview.ChipView;
import com.ats.exhibitionvisitor.flowlayout.FlowLayout;
import com.ats.exhibitionvisitor.model.CityModel;
import com.ats.exhibitionvisitor.model.CompanyTypeModel;
import com.ats.exhibitionvisitor.model.EventListModel;
import com.ats.exhibitionvisitor.model.EventModel;
import com.ats.exhibitionvisitor.model.Visitor;
import com.ats.exhibitionvisitor.model.VisitorModel;
import com.ats.exhibitionvisitor.util.CommonDialog;
import com.ats.exhibitionvisitor.util.Constants;
import com.ats.exhibitionvisitor.util.CustomSharedPreference;
import com.ats.exhibitionvisitor.util.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rvEvents;
    private EventsAdapter adapter;
    private FlowLayout flFilter;
    private LinearLayout llFilter;

    public static Map<Integer, Boolean> industryMap = new HashMap<Integer, Boolean>();

    private ArrayList<EventListModel> eventList = new ArrayList<>();

    int visitorId = 0;
    Gson gson = new Gson();

    ArrayList<Integer> cityIds = new ArrayList<>();

    Dialog dialog;

    IndustryTypeAdapter industryAdapter;

    ArrayList<CompanyTypeModel> industryTypeList = new ArrayList<>();

    boolean isTouched = false;
    CheckBox checkBox;

    private BroadcastReceiver mBroadcastReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvEvents = view.findViewById(R.id.rvEvents);
        flFilter = view.findViewById(R.id.flFilter);
        llFilter = view.findViewById(R.id.llFilter);
        llFilter.setOnClickListener(this);

        /*ArrayList<EventModel> eventList = new ArrayList<>();
        eventList.add(new EventModel(1, "Auto Expo", "https://images.mapsofindia.com/my-india/2018/02/Auto-Expo-2018.jpg", "Ram-Leela Maidan, Delhi", "25th June 2018, 11:00 am onwards"));
        eventList.add(new EventModel(2, "Mobile World Congress 2018", "http://www.rever.eu/sites/default/files/MWC2018_1.png", "Golf-Club Maidan, Nashik", "25th June 2018, 11:00 am onwards"));
        eventList.add(new EventModel(3, "Krushi Pradarshan", "https://i.ytimg.com/vi/ha1CtQNXlnw/maxresdefault.jpg", "BKC Kurla, Mumbai", "25th June 2018, 11:00 am onwards"));
        eventList.add(new EventModel(4, "Property Expo", "http://www.eventstiger.com/files/ebanner/1380106486.jpg", "Pimpri chinchwad, Pune", "25th June 2018, 11:00 am onwards"));
*/

        String jsonVisitor = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_VISITOR);
        final Visitor visitor = gson.fromJson(jsonVisitor, Visitor.class);
        if (visitor != null) {
            visitorId = visitor.getVisitorId();
        }
        Log.e("VisitorID", " ------------------------  " + visitorId);

        String json = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_CITY);
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        Log.e("JSON", " ------------------------  " + json);

        if (json != null) {
            cityIds = gson.fromJson(json, type);
            cityIds.add(1);
        }

        Log.e("CITY IDS", "------------------" + cityIds);

        getAllCompanyType();

        ArrayList<Integer> companyIds = new ArrayList<>();
        companyIds.add(1);

        int allCity = CustomSharedPreference.getInt(getActivity(), CustomSharedPreference.KEY_ALL_CITY);
        int allIndustry = CustomSharedPreference.getInt(getActivity(), CustomSharedPreference.KEY_EVENT_ALL_INDUSTRY_TYPE);

        getEventList(companyIds, cityIds, visitorId, allIndustry, allCity);

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("CheckboxEventIndustry")) {
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
                new IntentFilter("CheckboxEventIndustry"));
    }

    private void handlePushNotification(Intent intent) {

        Log.e("handlePushNotification", "------------------------------------**********");

        checkBox.setChecked(false);
        CustomSharedPreference.putInt(getActivity(), CustomSharedPreference.KEY_EVENT_ALL_INDUSTRY_TYPE, 0);
        isTouched = false;

    }


    public void getEventList(ArrayList<Integer> compTypeArray, ArrayList<Integer> cityArray, final int visitorId, int allIndustry, int allCity) {

        Log.e("PARAMETERS : ", "industry : " + compTypeArray + "         city : " + cityArray + "       visitorId : " + visitorId + "           allIndustry : " + allIndustry + "               allCity : " + allCity);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<EventListModel>> listCall = RetrofitClient.myInterface.getEventList(compTypeArray, cityArray, visitorId, allIndustry, allCity);
            listCall.enqueue(new Callback<ArrayList<EventListModel>>() {
                @Override
                public void onResponse(Call<ArrayList<EventListModel>> call, Response<ArrayList<EventListModel>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Event Data : ", "------------" + response.body());

                            ArrayList<EventListModel> data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                                Toast.makeText(getContext(), "No events found.", Toast.LENGTH_SHORT).show();
                            } else {
                                eventList = data;
                                adapter = new EventsAdapter(eventList, getContext(), visitorId);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                rvEvents.setLayoutManager(mLayoutManager);
                                rvEvents.setItemAnimator(new DefaultItemAnimator());
                                rvEvents.setAdapter(adapter);
                                commonDialog.dismiss();
                            }
                        } else {
                            commonDialog.dismiss();
                            Toast.makeText(getContext(), "No events found.", Toast.LENGTH_SHORT).show();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Toast.makeText(getContext(), "No events found.", Toast.LENGTH_SHORT).show();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<EventListModel>> call, Throwable t) {
                    commonDialog.dismiss();
                    Toast.makeText(getContext(), "No events found.", Toast.LENGTH_SHORT).show();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    public void getAllCompanyType() {
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<CompanyTypeModel>> listCall = RetrofitClient.myInterface.getAllCompanyType();
            listCall.enqueue(new Callback<ArrayList<CompanyTypeModel>>() {
                @Override
                public void onResponse(Call<ArrayList<CompanyTypeModel>> call, Response<ArrayList<CompanyTypeModel>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Company Data : ", "------------" + response.body());

                            ArrayList<CompanyTypeModel> data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                                //Toast.makeText(SelectCityActivity.this, "No Cities Found !", Toast.LENGTH_SHORT).show();
                            } else {
                                industryTypeList.clear();

                                int allIndustries = CustomSharedPreference.getInt(getActivity(), CustomSharedPreference.KEY_EVENT_ALL_INDUSTRY_TYPE);

                                if (allIndustries == 1) {
                                    for (int i = 0; i < data.size(); i++) {
                                        data.get(i).setCheckedStatus(true);
                                        industryMap.put(data.get(i).getCompanyTypeId(), true);
                                    }
                                } else {

                                    Gson gson = new Gson();
                                    String json = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_EVENT_INDUSTRY_TYPE);
                                    Type type = new TypeToken<ArrayList<Integer>>() {
                                    }.getType();

                                    Log.e("JSON", "----------------------" + json);

                                    ArrayList<Integer> industryArrayList = gson.fromJson(json, type);

                                    Log.e("SHARED PREF", "-----------CITY IDS-------------" + industryArrayList);

                                    if (industryArrayList != null && industryArrayList.size() > 0) {
                                        for (int i = 0; i < data.size(); i++) {
                                            for (int j = 0; j < industryArrayList.size(); j++) {
                                                if (data.get(i).getCompanyTypeId() == industryArrayList.get(j)) {
                                                    data.get(i).setCheckedStatus(true);
                                                    industryMap.put(data.get(i).getCompanyTypeId(), true);
                                                } else {
                                                    data.get(i).setCheckedStatus(false);
                                                    industryMap.put(data.get(i).getCompanyTypeId(), false);
                                                }
                                            }
                                        }
                                    } else {
                                        for (int i = 0; i < data.size(); i++) {
                                            data.get(i).setCheckedStatus(false);
                                            industryMap.put(data.get(i).getCompanyTypeId(), false);
                                        }
                                    }

                                }

                                industryTypeList = data;

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
                public void onFailure(Call<ArrayList<CompanyTypeModel>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    public void showDialog(final ArrayList<CompanyTypeModel> industryList) {
        dialog = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar);
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.dialog_filter, null, false);
        dialog.setContentView(v);
        dialog.setCancelable(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        checkBox = dialog.findViewById(R.id.allCheckbox);
        final RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);

        industryAdapter = new IndustryTypeAdapter(industryList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(industryAdapter);

        final int allIndustries = CustomSharedPreference.getInt(getActivity(), CustomSharedPreference.KEY_EVENT_ALL_INDUSTRY_TYPE);
        if (allIndustries == 1) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        isTouched = false;
        checkBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isTouched = true;
                return false;
            }
        });


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isTouched = false;
                    CustomSharedPreference.putInt(getContext(), CustomSharedPreference.KEY_EVENT_ALL_INDUSTRY_TYPE, 1);

                    if (industryList.size() > 0) {
                        for (int i = 0; i < industryList.size(); i++) {
                            industryMap.put(industryList.get(i).getCompanyTypeId(), true);
                            industryList.get(i).setCheckedStatus(true);
                        }
                        industryAdapter.notifyDataSetChanged();
                    }
                } else {

                    if (isTouched) {
                        Log.e("CHECKBOX", "-----------ISTOUCHED");
                        isTouched = false;
                        if (industryList.size() > 0) {
                            for (int i = 0; i < industryList.size(); i++) {
                                industryMap.put(industryList.get(i).getCompanyTypeId(), false);
                                industryList.get(i).setCheckedStatus(false);
                            }
                            industryAdapter.notifyDataSetChanged();
                        }
                    }
                    CustomSharedPreference.putInt(getContext(), CustomSharedPreference.KEY_EVENT_ALL_INDUSTRY_TYPE, 0);

                }
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ArrayList<Integer> industryIds = new ArrayList<>();
                for (int i = 0; i < industryList.size(); i++) {
                    if (industryMap.get(industryList.get(i).getCompanyTypeId())) {
                        industryIds.add(industryList.get(i).getCompanyTypeId());
                    }
                }
                Log.e("Selected Ids", "--------------" + industryIds);

                Log.e("Dialog City List", "--------------" + industryList);

                String jsonCity = gson.toJson(industryIds);
                CustomSharedPreference.putString(getContext(), CustomSharedPreference.KEY_EVENT_INDUSTRY_TYPE, jsonCity);

                int allCity = CustomSharedPreference.getInt(getActivity(), CustomSharedPreference.KEY_ALL_CITY);

                int allIndustryType = 0;
                if (checkBox.isChecked()) {
                    allIndustryType = 1;
                }

                getEventList(industryIds, cityIds, visitorId, allIndustryType, allCity);

            }
        });

        dialog.show();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.llFilter) {
            showDialog(industryTypeList);
        }
    }
}
