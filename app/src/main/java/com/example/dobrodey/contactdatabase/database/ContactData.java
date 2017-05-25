package com.example.dobrodey.contactdatabase.database;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.List;


public class ContactData extends SugarRecord {

    @Ignore
    public static final Integer TYPE_EMAIL = 0;
    @Ignore
    public static final Integer TYPE_PHONE = 1;


    Contact contact;
    Integer type;
    String data;

    public ContactData() {

    }

    public ContactData(Integer type, String data, Contact contact) {
        this.type = type;
        this.data = data;
        this.contact = contact;
    }

    public String getData() {
        return data;
    }
}
