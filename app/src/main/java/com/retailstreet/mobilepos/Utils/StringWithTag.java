package com.retailstreet.mobilepos.Utils;

import androidx.annotation.NonNull;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

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
