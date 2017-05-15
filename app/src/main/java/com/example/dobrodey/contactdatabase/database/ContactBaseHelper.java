package com.example.dobrodey.contactdatabase.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class ContactBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "contacts.db";

    //QUERY for create Tables

    private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE " +
            ContactsDBSchema.ContactsTable.NAME + "( _id integer primary key autoincrement,"+
            ContactsDBSchema.ContactsTable.Cols.UUID + " TEXT,"+
            ContactsDBSchema.ContactsTable.Cols.GOOGLE_ID + " TEXT,"+
            ContactsDBSchema.ContactsTable.Cols.FIRST_NAME + " TEXT,"+
            ContactsDBSchema.ContactsTable.Cols.LAST_NAME + " TEXT,"+
            ContactsDBSchema.ContactsTable.Cols.EMAIL + " TEXT,"+
            ContactsDBSchema.ContactsTable.Cols.PHONE + " TEXT);";




    public ContactBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ContactsDBSchema.ContactsTable.NAME);
        // create new tables
        onCreate(db);
    }
}
