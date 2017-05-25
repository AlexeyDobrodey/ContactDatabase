package com.example.dobrodey.contactdatabase.models;

import java.util.regex.Pattern;

/**
 * Created by alexey on 5/25/17.
 */

public class ValidationEmail extends Validation {
    public ValidationEmail() {
        mPattern = Pattern.compile("[A-Za-z_\\-0-9\\.]+@[a-z]+\\.((com|net)(\\.(ua))?)$");
    }
}
