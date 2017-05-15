package com.example.dobrodey.contactdatabase.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;



public class Contacts {

    private SQLiteDatabase mDatabase;

    public Contacts(Context context){
        mDatabase = new ContactBaseHelper(context).getWritableDatabase();
    }

    public void addContact(Contact contact){
        ContentValues contentValues = getContentValue(contact);
        mDatabase.insert(ContactsDBSchema.ContactsTable.NAME, null, contentValues);
    }

    public void updateContact(Contact contact){
        String uuidString = contact.getID().toString();
        ContentValues contentValues = getContentValue(contact);

        mDatabase.update(ContactsDBSchema.ContactsTable.NAME, contentValues,
                ContactsDBSchema.ContactsTable.Cols.UUID + " =?", new String[]{uuidString});
    }

    public void deleteContact(Contact contact) {
        String uuid = contact.getID().toString();
        mDatabase.delete(ContactsDBSchema.ContactsTable.NAME,
                ContactsDBSchema.ContactsTable.Cols.UUID + " =?", new String[]{uuid});
    }


    public List<Contact> getContactsByUserID(String userID) {
        List<Contact> contacts = new ArrayList<>();
        ContactCursorWraper cursor =
                queryCrimes(ContactsDBSchema.ContactsTable.Cols.GOOGLE_ID + " =?", new String[]{userID}, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                contacts.add(cursor.getContact());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return contacts;
    }
    public List<Contact> getContactsSortBy(String userID, String order) {
        List<Contact> contacts = new ArrayList<>();
        ContactCursorWraper cursor =
                queryCrimes(ContactsDBSchema.ContactsTable.Cols.GOOGLE_ID + " =?", new String[]{userID},
                        order);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                contacts.add(cursor.getContact());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return contacts;
    }




    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        ContactCursorWraper cursor = queryCrimes(null, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                contacts.add(cursor.getContact());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return contacts;
    }

    private ContactCursorWraper queryCrimes(String whereClause, String[] whereArgs, String orderBy) {
        Cursor cursor = mDatabase.query(
                ContactsDBSchema.ContactsTable.NAME,
                null, // Columns - null выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                orderBy // orderBy
        );
        return new ContactCursorWraper(cursor);
    }


    private ContentValues getContentValue(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(ContactsDBSchema.ContactsTable.Cols.FIRST_NAME, contact.getFirstName());
        values.put(ContactsDBSchema.ContactsTable.Cols.LAST_NAME, contact.getSecondName());
        values.put(ContactsDBSchema.ContactsTable.Cols.PHONE, contact.getPhone());
        values.put(ContactsDBSchema.ContactsTable.Cols.EMAIL, contact.getEmail());
        values.put(ContactsDBSchema.ContactsTable.Cols.GOOGLE_ID, contact.getUserId());
        values.put(ContactsDBSchema.ContactsTable.Cols.UUID, contact.getID().toString());

        return values;
    }
}
