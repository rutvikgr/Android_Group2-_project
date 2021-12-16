package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private ContactAdapter mAdapter;
    private List<Contact> contactList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noContactView;

    private DatabaseHelper db;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

        //check login
        if(!sharedPreferences.getBoolean("is_login", false)){
            startActivity(new Intent(ContactActivity.this,MainActivity.class));
            finish();
        }

        recyclerView = findViewById(R.id.recycler_view);
        noContactView = findViewById(R.id.empty_contact_view);

        db = new DatabaseHelper(this);

        contactList.addAll(db.getAllContacts());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContactActivity.this, AddContactActivity.class));
            }
        });

        mAdapter = new ContactAdapter(this, contactList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAdapter != null){
            contactList.clear();
            contactList.addAll(db.getAllContacts());
            mAdapter.notifyDataSetChanged();
            toggleEmptyContact();
        }
    }

    private void toggleEmptyContact() {
        if (db.getAllContacts().size() > 0) {
            noContactView.setVisibility(View.GONE);
        } else {
            noContactView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){
            if(mAdapter != null){
                contactList.clear();
                contactList.addAll(db.getAllContacts());
                mAdapter.notifyDataSetChanged();
                toggleEmptyContact();
            }
        }
    }


    void logout(){
        sharedPreferences.edit().clear();
        startActivity(new Intent(ContactActivity.this,MainActivity.class));
        finish();
    }

}