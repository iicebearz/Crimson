package io.iicebear.crimson.fps;

import android.app.Activity;

final class Ui {

    private Ui() {}

    static int themeColor(Activity activity, int attrRes) {
        android.util.TypedValue tv = new android.util.TypedValue();
        activity.getTheme().resolveAttribute(attrRes, tv, true);
        return tv.data;
    }
}