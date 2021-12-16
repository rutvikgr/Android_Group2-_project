package com.example.myapplication;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private Context context;
    private List<Contact> contactList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView phone;
        public TextView email;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_name);
            phone = view.findViewById(R.id.tv_phone);
            email = view.findViewById(R.id.tv_email);
        }
    }


    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.name.setText(contact.getName());
        holder.phone.setText(contact.getPhone());
        holder.email.setText(contact.getEmail());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

}
