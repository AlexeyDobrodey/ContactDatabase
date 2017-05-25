package com.example.dobrodey.contactdatabase.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alexey on 5/22/17.
 */

public class Validation {
    protected Pattern mPattern;
    protected Matcher mMatcher;

    public boolean validate(String value){
        mMatcher = mPattern.matcher(value);
        return mMatcher.matches();
    }
}
