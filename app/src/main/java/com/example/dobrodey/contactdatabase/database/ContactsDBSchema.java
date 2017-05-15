package com.example.dobrodey.contactdatabase.database;



public class ContactsDBSchema {
    public static final class ContactsTable{
        public static final String NAME = "contacts";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String GOOGLE_ID = "google_id";
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String EMAIL = "email";
            public static final String PHONE = "phone";

        }
    }
}
