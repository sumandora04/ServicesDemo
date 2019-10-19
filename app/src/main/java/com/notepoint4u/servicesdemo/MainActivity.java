package com.notepoint4u.servicesdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent serviceIntent;
    private static final String TAG = "Thread";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate:Thread id: "+Thread.currentThread().getId());

    }

    public void startServiceClick(View view) {
        serviceIntent = new Intent(MainActivity.this, MyServices.class);
        startService(serviceIntent);
    }

    public void stopServiceClick(View view) {
        stopService(serviceIntent);
    }
}
