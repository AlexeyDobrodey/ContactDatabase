package com.example.dobrodey.contactdatabase.database;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.List;


public class Contact  extends SugarRecord implements Serializable{
    String firstName;
    String lastName;
    String userID; // google id user which login

    public Contact() {

    }

    public Contact(String firstName, String lastName, String userID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserId() {
        return userID;
    }

    public List<ContactData> getPhones() {
        return ContactData.find(ContactData.class, "type = ? and contact = ?",
                new String[]{ContactData.TYPE_PHONE.toString(),getId().toString()});
    }

    public List<ContactData> getEmails() {
        return ContactData.find(ContactData.class, "type = ? and contact = ?",
                new String[]{ContactData.TYPE_EMAIL.toString(),getId().toString()});
    }

}
