package com.notepoint4u.servicesdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Intent serviceIntent;
    private static final String TAG = "Thread";
    TextView randomText;

    private MyServices myServices;
    private boolean isServiceBound;
    private ServiceConnection serviceConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate:Thread id: "+Thread.currentThread().getId());

        randomText = findViewById(R.id.random_number_textView);
    }

    public void startServiceClick(View view) {
        serviceIntent = new Intent(MainActivity.this, MyServices.class);
        startService(serviceIntent);
    }

    public void stopServiceClick(View view) {
        stopService(serviceIntent);
    }

    public void getRandomNumberClick(View view) {
        if (isServiceBound) {
            randomText.setText(String.valueOf(myServices.getRandomNumber()));
        }else {
            randomText.setText("Service is not bound.");
        }
    }

    public void unbindServiceClick(View view) {
        unBindServiceMethod();
    }

    public void bindServiceClick(View view) {
        bindServiceMethod();
    }


    private void bindServiceMethod(){
        if(serviceConnection==null){
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder iBinder) {

                    MyServices.MyServiceBinder myServiceBinder = (MyServices.MyServiceBinder)iBinder ;
                    myServices = myServiceBinder.getBinder();

                    isServiceBound = true;
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                    isServiceBound = false;
                }
            };
        }

        bindService(serviceIntent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unBindServiceMethod(){
        if (isServiceBound){
            unbindService(serviceConnection);
            isServiceBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindServiceMethod();
    }
}
