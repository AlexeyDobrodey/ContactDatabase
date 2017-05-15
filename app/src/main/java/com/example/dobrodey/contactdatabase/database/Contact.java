package com.example.dobrodey.contactdatabase.database;

import java.io.Serializable;
import java.util.UUID;


public class Contact  implements Serializable{
    private String mFirstName;
    private String mSecondName;
    private String mPhone;
    private String mEmail;
    private String mUserId;
    private UUID mUUID;

    public Contact() {
        mUUID = UUID.randomUUID();
    }

    public Contact(String firstName, String secondName, String phone, String email,
                   String userId, UUID uuid) {
        mFirstName = firstName;
        mSecondName = secondName;
        mPhone = phone;
        mEmail = email;
        mUserId = userId;
        mUUID = uuid;
    }
    public Contact(String firstName, String secondName, String phone, String email, String userId) {
        this(firstName, secondName, phone, email, userId, UUID.randomUUID());
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getSecondName() {
        return mSecondName;
    }

    public void setSecondName(String secondName) {
        mSecondName = secondName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public UUID getID() {
        return mUUID;
    }

    public void setID(UUID UUID) {
        mUUID = UUID;
    }
}
