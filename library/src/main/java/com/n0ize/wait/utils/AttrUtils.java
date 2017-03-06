package com.n0ize.wait.utils;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * n0ise on 3/4/2017.
 */
public class AttrUtils {
    public static int getColor(Context context, int attr) {
        //define colors in appTheme
        //<item name="sample">#930406</item>
        int[] attrs = {attr};
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs);
        try {
            return ta.getColor(0, -1);
        } finally {
            ta.recycle();
        }
    }
}
