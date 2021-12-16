package com.example.myapplication;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contacts_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contact.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contact.TABLE_NAME);
        onCreate(db);
    }


    public long insertContact(String name,String phone, String email) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Contact.COLUMN_NAME, name);
        values.put(Contact.COLUMN_PHONE, phone);
        values.put(Contact.COLUMN_EMAIL, email);

        long id = db.insert(Contact.TABLE_NAME, null, values);
        db.close();

        return id;
    }

    @SuppressLint("Range")
    public List<Contact> getAllContacts() {
        List<Contact> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Contact.TABLE_NAME + " ORDER BY " +
                Contact.COLUMN_NAME + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(cursor.getColumnIndex(Contact.COLUMN_ID)));
                contact.setName(cursor.getString(cursor.getColumnIndex(Contact.COLUMN_NAME)));
                contact.setPhone(cursor.getString(cursor.getColumnIndex(Contact.COLUMN_PHONE)));
                contact.setEmail(cursor.getString(cursor.getColumnIndex(Contact.COLUMN_EMAIL)));

                notes.add(contact);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

}
