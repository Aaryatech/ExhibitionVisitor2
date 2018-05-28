package com.ats.exhibitionvisitor.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.model.CityModel;
import com.ats.exhibitionvisitor.model.CompanyTypeModel;
import com.ats.exhibitionvisitor.model.VisitorModel;
import com.ats.exhibitionvisitor.util.CommonDialog;
import com.ats.exhibitionvisitor.util.Constants;
import com.ats.exhibitionvisitor.util.CustomSharedPreference;
import com.ats.exhibitionvisitor.util.EmailValidation;
import com.ats.exhibitionvisitor.util.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edName, edEmail, edMobile, edRepresent;
    private Spinner spCity, spCompanyType;
    private TextView tvCityId, tvCompTypeId;
    private Button btnRegister, btnSignIn;

    String strName, strEmail, strMobile, strRepresent;
    int cityId, compTypeId;

    ArrayList<String> cityNameArray = new ArrayList<>();
    ArrayList<Integer> cityIdArray = new ArrayList<>();

    ArrayList<String> typeNameArray = new ArrayList<>();
    ArrayList<Integer> typeIdArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edName = findViewById(R.id.edName);
        edEmail = findViewById(R.id.edEmail);
        edMobile = findViewById(R.id.edMobile);
        edRepresent = findViewById(R.id.edRepresent);
        spCity = findViewById(R.id.spCity);
        spCompanyType = findViewById(R.id.spCompanyType);
        tvCityId = findViewById(R.id.tvCityId);
        tvCompTypeId = findViewById(R.id.tvCompTypeId);
        btnRegister = findViewById(R.id.btnRegister);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

        getAllCities();
        getAllCompanyType();

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvCityId.setText("" + cityIdArray.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spCompanyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvCompTypeId.setText("" + typeIdArray.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegister) {

            strName = edName.getText().toString();
            strEmail = edEmail.getText().toString();
            strMobile = edMobile.getText().toString();
            strRepresent = edRepresent.getText().toString();
            validate();
        } else if (v.getId() == R.id.btnSignIn) {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            finish();
        }
    }

    public void validate() {
        boolean isValidName = false, isValidEmail = false, isValidMobie = false, isValidRepresent = false, isValidCity = false, isValidCompantType = false;

        if (strName.isEmpty()) {
            isValidName = false;
            edName.setError("Required");
        } else {
            isValidName = true;
            edName.setError(null);
        }


        if (strEmail.isEmpty()) {
            isValidEmail = false;
            edEmail.setError("Required");
        } else if (!EmailValidation.isValidEmail(strEmail)) {
            isValidEmail = false;
            edEmail.setError("Invalid Email ID");
        } else {
            isValidEmail = true;
            edEmail.setError(null);
        }

        if (strMobile.isEmpty()) {
            isValidMobie = false;
            edMobile.setError("Required");
        } else if (strMobile.length() != 10) {
            isValidMobie = false;
            edMobile.setError("Required 10 digits");
        } else {
            isValidMobie = true;
            edMobile.setError(null);
        }

        if (strRepresent.isEmpty()) {
            isValidRepresent = false;
            edRepresent.setError("Required");
        } else {
            isValidRepresent = true;
            edRepresent.setError(null);
        }

        if (spCity.getSelectedItemPosition() == 0) {
            TextView view = (TextView) spCity.getSelectedView();
            view.setError("Required");
            isValidCity = false;
        } else {
            TextView view = (TextView) spCity.getSelectedView();
            view.setError(null);
            isValidCity = true;
            cityId = Integer.parseInt(tvCityId.getText().toString());
        }

        if (spCompanyType.getSelectedItemPosition() == 0) {
            TextView view = (TextView) spCompanyType.getSelectedView();
            view.setError("Required");
            isValidCompantType = false;
        } else {
            TextView view = (TextView) spCompanyType.getSelectedView();
            view.setError(null);
            isValidCompantType = true;
            compTypeId = Integer.parseInt(tvCompTypeId.getText().toString());
        }

        if (isValidName && isValidEmail && isValidMobie && isValidRepresent && isValidCity && isValidCompantType) {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

            VisitorModel model = new VisitorModel(0, 0, strName, strEmail, strMobile, strRepresent, 1, 1, cityId, compTypeId, "token");
            addNewVisitor(model);
        }

    }


    public void addNewVisitor(VisitorModel model) {
        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<VisitorModel> listCall = RetrofitClient.myInterface.saveVisitor(model);
            listCall.enqueue(new Callback<VisitorModel>() {
                @Override
                public void onResponse(Call<VisitorModel> call, Response<VisitorModel> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Visitor Data : ", "------------" + response.body());

                            VisitorModel data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                                Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(SelectCityActivity.this, "No Cities Found !", Toast.LENGTH_SHORT).show();
                            } else {

                                if (data.getVisitorId() != 0) {

                                    Toast.makeText(RegistrationActivity.this, "Success", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                    finish();


                                } else {
                                    Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();

                                }
                                commonDialog.dismiss();
                            }
                        } else {
                            commonDialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<VisitorModel> call, Throwable t) {
                    commonDialog.dismiss();
                    Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
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
                                cityNameArray.clear();
                                cityIdArray.clear();
                                cityNameArray.add("Select city");
                                cityIdArray.add(0);
                                for (int i = 0; i < data.size(); i++) {
                                    cityNameArray.add(data.get(i).getLocationName());
                                    cityIdArray.add(data.get(i).getLocationId());
                                }

                                ArrayAdapter cityAdapter = new ArrayAdapter(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, cityNameArray);
                                spCity.setAdapter(cityAdapter);

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

    public void getAllCompanyType() {
        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
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
                                typeNameArray.clear();
                                typeIdArray.clear();
                                typeNameArray.add("Select company type");
                                typeIdArray.add(0);
                                for (int i = 0; i < data.size(); i++) {
                                    typeNameArray.add(data.get(i).getCompanyTypeName());
                                    typeIdArray.add(data.get(i).getCompanyTypeId());
                                }

                                ArrayAdapter cityAdapter = new ArrayAdapter(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, typeNameArray);
                                spCompanyType.setAdapter(cityAdapter);

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
            Toast.makeText(this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

}
