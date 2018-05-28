package com.ats.exhibitionvisitor.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.adapter.CityAdapter;
import com.ats.exhibitionvisitor.adapter.EventsAdapter;
import com.ats.exhibitionvisitor.filterview.ChipView;
import com.ats.exhibitionvisitor.flowlayout.FlowLayout;
import com.ats.exhibitionvisitor.model.CityModel;
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

public class SelectCityActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llChooseCity;
    private TextView tvCity;
    private FlowLayout flowLayout;
    private Button btnView;

    Dialog dialog;

    CityAdapter adapter;

    ArrayList<CityModel> cityList = new ArrayList<>();

    Gson gson = new Gson();

    public static Map<Integer, Boolean> cityMap = new HashMap<Integer, Boolean>();

    private BroadcastReceiver mBroadcastReceiver;

    CheckBox checkBox;
    Boolean isTouched = false;

    ArrayList<Integer> cityIds = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        llChooseCity = findViewById(R.id.llChooseCity);
        tvCity = findViewById(R.id.tvCity);
        flowLayout = findViewById(R.id.flowLayout);
        btnView = findViewById(R.id.btnView);

        llChooseCity.setOnClickListener(this);
        btnView.setOnClickListener(this);


        getAllCities();

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("Checkbox")) {
                    handlePushNotification(intent);
                }
            }
        };


    }

    @Override
    public void onPause() {
        Log.e("Select city", "  ON PAUSE");

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,
                new IntentFilter("Checkbox"));
    }

    private void handlePushNotification(Intent intent) {

        Log.e("handlePushNotification", "------------------------------------**********");

        checkBox.setChecked(false);
        CustomSharedPreference.putInt(SelectCityActivity.this, CustomSharedPreference.KEY_ALL_CITY, 0);
        isTouched = false;

    }

    public void showDialog(final ArrayList<CityModel> cityList) {
        dialog = new Dialog(this, android.R.style.Theme_Light_NoTitleBar);
        LayoutInflater li = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.dialog_choose_city, null, false);
        dialog.setContentView(v);
        dialog.setCancelable(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        TextView tvClear = dialog.findViewById(R.id.tvClear);
        checkBox = dialog.findViewById(R.id.allCityCheckbox);
        final RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);

        adapter = new CityAdapter(cityList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        final int allCities = CustomSharedPreference.getInt(SelectCityActivity.this, CustomSharedPreference.KEY_ALL_CITY);
        if (allCities == 1) {
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
                    CustomSharedPreference.putInt(SelectCityActivity.this, CustomSharedPreference.KEY_ALL_CITY, 1);

                    if (cityList.size() > 0) {
                        for (int i = 0; i < cityList.size(); i++) {
                            cityMap.put(cityList.get(i).getLocationId(), true);
                            cityList.get(i).setCheckedStatus(true);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } else {

                    if (isTouched) {
                        Log.e("CHECKBOX", "-----------ISTOUCHED");
                        isTouched = false;
                        if (cityList.size() > 0) {
                            for (int i = 0; i < cityList.size(); i++) {
                                cityMap.put(cityList.get(i).getLocationId(), false);
                                cityList.get(i).setCheckedStatus(false);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                    CustomSharedPreference.putInt(SelectCityActivity.this, CustomSharedPreference.KEY_ALL_CITY, 0);


                }
            }
        });

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityList.size() > 0) {
                    for (int i = 0; i < cityList.size(); i++) {
                        cityMap.put(cityList.get(i).getLocationId(), false);
                        cityList.get(i).setCheckedStatus(false);
                    }
                    checkBox.setChecked(false);
                    adapter.notifyDataSetChanged();
                }
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                ArrayList<Integer> cityIds = new ArrayList<>();
                for (int i = 0; i < cityList.size(); i++) {
                    if (cityMap.get(cityList.get(i).getLocationId())) {
                        cityIds.add(cityList.get(i).getLocationId());
                    }
                }


                Log.e("Selected Ids", "--------------" + cityIds);

                flowLayout.removeAllViews();

                Log.e("Dialog City List", "--------------" + cityList);

                for (int i = 0; i < cityList.size(); i++) {
                    for (int j = 0; j < cityIds.size(); j++) {
                        if (cityList.get(i).getLocationId() == cityIds.get(j)) {
                            ChipView chipView1 = new ChipView(SelectCityActivity.this);
                            chipView1.setLabel(cityList.get(i).getLocationName());
                            chipView1.setHasAvatarIcon(false);
                            chipView1.setDeletable(false);
                            chipView1.setPadding(5, 2, 5, 2);

                            flowLayout.addView(chipView1);
                        }
                    }

                }

                String jsonCity = gson.toJson(cityIds);
                CustomSharedPreference.putString(SelectCityActivity.this, CustomSharedPreference.KEY_CITY, jsonCity);

                if (checkBox.isChecked()) {
                    tvCity.setText("All Cities");
                } else {
                    tvCity.setText("");
                }


            }
        });

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.llChooseCity) {

            int allCities = CustomSharedPreference.getInt(SelectCityActivity.this, CustomSharedPreference.KEY_ALL_CITY);

            showDialog(cityList);
        } else if (v.getId() == R.id.btnView) {
            startActivity(new Intent(SelectCityActivity.this, HomeActivity.class));
            finish();
        }
    }

    public void getAllCities() {
        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<CityModel>> listCall = RetrofitClient.myInterface.getAllCities();
            listCall.enqueue(new Callback<ArrayList<CityModel>>() {
                @Override
                public void onResponse(Call<ArrayList<CityModel>> call, Response<ArrayList<CityModel>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("City Data : ", "------------" + response.body());

                            ArrayList<CityModel> data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                                //Toast.makeText(SelectCityActivity.this, "No Cities Found !", Toast.LENGTH_SHORT).show();
                            } else {
                                cityList.clear();

                                int allCities = CustomSharedPreference.getInt(SelectCityActivity.this, CustomSharedPreference.KEY_ALL_CITY);
                                Log.e("All CITY", "--------------------" + allCities);
                                if (allCities == 1) {
                                    for (int i = 0; i < data.size(); i++) {
                                        data.get(i).setCheckedStatus(true);
                                        cityMap.put(data.get(i).getLocationId(), true);
                                    }
                                } else {

                                    Gson gson = new Gson();
                                    String json = CustomSharedPreference.getString(SelectCityActivity.this, CustomSharedPreference.KEY_CITY);
                                    Type type = new TypeToken<ArrayList<Integer>>() {
                                    }.getType();

                                    Log.e("JSON", "----------------------" + json);

                                    ArrayList<Integer> cityArrayList = gson.fromJson(json, type);

                                    Log.e("SHARED PREF", "-----------CITY IDS-------------" + cityArrayList);

                                    if (cityArrayList != null && cityArrayList.size() > 0) {
                                        for (int i = 0; i < data.size(); i++) {
                                            for (int j = 0; j < cityArrayList.size(); j++) {
                                                if (data.get(i).getLocationId() == cityArrayList.get(j)) {
                                                    data.get(i).setCheckedStatus(true);
                                                    cityMap.put(data.get(i).getLocationId(), true);
                                                } else {
                                                    data.get(i).setCheckedStatus(false);
                                                    cityMap.put(data.get(i).getLocationId(), false);
                                                }
                                            }
                                        }
                                    } else {
                                        for (int i = 0; i < data.size(); i++) {
                                            data.get(i).setCheckedStatus(false);
                                            cityMap.put(data.get(i).getLocationId(), false);
                                        }
                                    }

                                }
                                cityList = data;
                                Log.e("CITY MAP", "-----------------" + cityMap);

                                displayCity();

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
                public void onFailure(Call<ArrayList<CityModel>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayCity() {
        Gson gson = new Gson();
        String json = CustomSharedPreference.getString(SelectCityActivity.this, CustomSharedPreference.KEY_CITY);
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();

        Log.e("JSON", "----------------------" + json);

        if (json != null) {
            cityIds = gson.fromJson(json, type);
        }

        flowLayout.removeAllViews();

        for (int i = 0; i < cityList.size(); i++) {
            for (int j = 0; j < cityIds.size(); j++) {
                if (cityList.get(i).getLocationId() == cityIds.get(j)) {
                    ChipView chipView1 = new ChipView(SelectCityActivity.this);
                    chipView1.setLabel(cityList.get(i).getLocationName());
                    chipView1.setHasAvatarIcon(false);
                    chipView1.setDeletable(false);
                    chipView1.setPadding(5, 2, 5, 2);

                    flowLayout.addView(chipView1);
                }
            }
        }


        int allCities = CustomSharedPreference.getInt(this, CustomSharedPreference.KEY_ALL_CITY);
        if (allCities == 1) {
            tvCity.setText("All Cities");
        } else {
            tvCity.setText("");
        }


    }

}
