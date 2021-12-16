package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity {

    EditText et_name;
    EditText et_email;
    EditText et_phone;
    Button btn_add;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone);
        btn_add = findViewById(R.id.button_add_contact);

        db = new DatabaseHelper(this);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_name.getText().length() == 0){
                    Toast.makeText(view.getContext(), "Name is required", Toast.LENGTH_SHORT).show();
                    return;
                }else if(et_phone.getText().length() == 0){
                    Toast.makeText(view.getContext(), "Phone Number is required", Toast.LENGTH_SHORT).show();
                    return;
                }else if(et_phone.getText().length() != 10){
                    Toast.makeText(view.getContext(), "Phone Number must be 10 digit", Toast.LENGTH_SHORT).show();
                    return;
                }

                long id = db.insertContact(et_name.getText().toString(),et_phone.getText().toString(),et_email.getText().toString());
                Toast.makeText(view.getContext(), "New contact added", Toast.LENGTH_SHORT).show();
                finishActivity(101);
                finish();

            }
        });

    }
}