package com.example.dobrodey.contactdatabase.models;

import java.util.regex.Pattern;

/**
 * Created by alexey on 5/25/17.
 */

public class ValidationPhone extends Validation {

    public ValidationPhone() {
        mPattern = Pattern.compile("^(\\+380|0)\\d{2}-?\\d{3}-?\\d{2}-?\\d{2}$");
    }
}
