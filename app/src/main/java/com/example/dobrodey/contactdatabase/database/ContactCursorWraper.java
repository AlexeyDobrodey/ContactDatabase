package com.example.dobrodey.contactdatabase.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;



public class ContactCursorWraper extends CursorWrapper{

    public ContactCursorWraper(Cursor cursor){
        super(cursor);
    }

    public Contact getContact() {
        String uuid = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Cols.UUID));
        String firstName = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Cols.FIRST_NAME));
        String lastName = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Cols.LAST_NAME));
        String phone = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Cols.PHONE));
        String email = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Cols.EMAIL));
        String userID = getString(getColumnIndex(ContactsDBSchema.ContactsTable.Cols.GOOGLE_ID));

        return new Contact(firstName, lastName, phone, email, userID, UUID.fromString(uuid));
    }
}
