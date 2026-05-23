package com.example.dayflow;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF = "dayflow_session";
    private static final String KEY_USER_ID = "user_id";

    public static void saveUserId(Context c, int userId) {
        SharedPreferences sp = c.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        sp.edit().putInt(KEY_USER_ID, userId).apply();
    }

    public static int getUserId(Context c) {
        SharedPreferences sp = c.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        return sp.getInt(KEY_USER_ID, -1);
    }

    public static void logout(Context c) {
        SharedPreferences sp = c.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        sp.edit().remove(KEY_USER_ID).apply();
    }
}

