package com.ats.exhibitionvisitor.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ats.exhibitionvisitor.R;
import com.ats.exhibitionvisitor.util.EmailValidation;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edFirstName, edLastName, edEmail, edMobile;
    private Button btnUpdate;

    String strFirstName, strLastName, strEmail, strMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edFirstName = findViewById(R.id.edFirstName);
        edLastName = findViewById(R.id.edLastName);
        edEmail = findViewById(R.id.edEmail);
        edMobile = findViewById(R.id.edMobile);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegister) {

            strFirstName = edFirstName.getText().toString();
            strLastName = edLastName.getText().toString();
            strEmail = edEmail.getText().toString();
            strMobile = edMobile.getText().toString();

            validate();
        }
    }

    public void validate() {
        boolean isValidFirstName = false, isValidLastName = false, isValidEmail = false, isValidMobie = false;

        if (strFirstName.isEmpty()) {
            isValidFirstName = false;
            edFirstName.setError("Required");
        } else {
            isValidFirstName = true;
            edFirstName.setError(null);
        }

        if (strLastName.isEmpty()) {
            isValidLastName = false;
            edLastName.setError("Required");
        } else {
            isValidLastName = true;
            edLastName.setError(null);
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

        if (isValidFirstName && isValidLastName && isValidEmail && isValidMobie) {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }

    }

}
