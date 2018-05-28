package com.ats.exhibitionvisitor.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.model.LoginModel;
import com.ats.exhibitionvisitor.model.Visitor;
import com.ats.exhibitionvisitor.model.VisitorModel;
import com.ats.exhibitionvisitor.util.CommonDialog;
import com.ats.exhibitionvisitor.util.Constants;
import com.ats.exhibitionvisitor.util.CustomSharedPreference;
import com.ats.exhibitionvisitor.util.RetrofitClient;
import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llMobile, llOTP;
    private EditText edMobile, edOTP;
    private Button btnRequestOTP, btnSubmit, btnRegister;

    String strMobile, strOTP;
    private String randomNumber;

    private Visitor visitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        llMobile = findViewById(R.id.llMobile);
        llOTP = findViewById(R.id.llOTP);
        edMobile = findViewById(R.id.edMobile);
        edOTP = findViewById(R.id.edOTP);
        btnRequestOTP = findViewById(R.id.btnRequestOTP);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnRegister = findViewById(R.id.btnRegister);

        btnRequestOTP.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRequestOTP) {
            strMobile = edMobile.getText().toString();
            if (strMobile.isEmpty()) {
                edMobile.setError("Required");
            } else if (strMobile.length() != 10) {
                edMobile.setError("Required 10 digits");
            } else {
                edMobile.setError(null);


                visitorLogin(edMobile.getText().toString().trim());


            }

        } else if (v.getId() == R.id.btnSubmit) {
            strOTP = edOTP.getText().toString();
            if (!randomNumber.equals(strOTP)) {
                edOTP.setError("Invalid OTP");
            } else {
                edOTP.setError(null);
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
                String jsonVisitor = gson.toJson(visitor);
                CustomSharedPreference.putString(LoginActivity.this, CustomSharedPreference.KEY_VISITOR, jsonVisitor);

                startActivity(new Intent(LoginActivity.this, SelectCityActivity.class));
                finish();

            }

        } else if (v.getId() == R.id.btnRegister) {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            finish();
        }
    }

    public static String random(int size) {

        StringBuilder generatedToken = new StringBuilder();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            for (int i = 0; i < size; i++) {
                generatedToken.append(number.nextInt(9));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedToken.toString();
    }


    public void visitorLogin(String mobile) {
        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<LoginModel> listCall = RetrofitClient.myInterface.doLogin(mobile);
            listCall.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Login Data : ", "------------" + response.body());

                            LoginModel data = response.body();
                            if (data == null) {
                                commonDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(SelectCityActivity.this, "No Cities Found !", Toast.LENGTH_SHORT).show();
                            } else {

                                if (!data.getError()) {

                                    //Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();

                                    visitor = data.getVisitor();

                                    llMobile.setVisibility(View.GONE);
                                    llOTP.setVisibility(View.VISIBLE);

                                    randomNumber = random(6);
                                    Log.e("OTP", "--------------" + randomNumber);

//                                    startActivity(new Intent(LoginActivity.this, SelectCityActivity.class));
//                                    finish();

                                } else {
                                    Toast.makeText(LoginActivity.this, "" + data.getMsg(), Toast.LENGTH_SHORT).show();

                                }
                                commonDialog.dismiss();
                            }
                        } else {
                            commonDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    commonDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


}
