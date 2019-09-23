package com.sumanmyon.projectextensions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sumanmyon.mylibrary.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.showToastLong(this, "ok");
    }
}
