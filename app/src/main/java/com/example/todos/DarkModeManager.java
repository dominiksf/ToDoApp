package com.example.todos;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatCheckBox;

public class DarkModeManager {

    final static int DAY = AppCompatDelegate.MODE_NIGHT_NO;
    final static int NIGHT = AppCompatDelegate.MODE_NIGHT_YES;

    SharedPreferences sharedPreferences;

    public DarkModeManager(Context context) {
        sharedPreferences = context.getSharedPreferences("DarkMode", Context.MODE_PRIVATE);
        setMode(getMode());
    }

    public void setMode(int mode) {
        AppCompatDelegate.setDefaultNightMode(mode);
        sharedPreferences.edit().putInt("mode", mode).apply();
    }

    public int getMode() {
        return sharedPreferences.getInt("mode", DAY);
    }

    public void toggle() {
        setMode(getMode() == DAY ? NIGHT : DAY);
    }
}
