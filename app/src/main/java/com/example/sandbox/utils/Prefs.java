package com.example.sandbox.utils;

import android.content.Context;
import android.content.SharedPreferences;

/** Sesión fake en SharedPreferences para pasar email entre Login->Registro->Intro */
public final class Prefs {
    private static final String NAME = "sandbox_prefs";
    private static final String KEY_USER = "user_email";
    private Prefs(){}

    public static void saveUser(Context c, String email){
        c.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit().putString(KEY_USER, email).apply();
    }
    public static String getUser(Context c){
        return c.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(KEY_USER, "");
    }
}
