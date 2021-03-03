package com.retailstreet.mobilepos.Utils;

import androidx.annotation.NonNull;

public class StringWithTag {

    public String string;
    public String tag;

    public StringWithTag(String stringPart, String tagPart) {
        string = stringPart;
        tag = tagPart;
    }

    @NonNull
    @Override
    public String toString() {
        return string;
    }
}
