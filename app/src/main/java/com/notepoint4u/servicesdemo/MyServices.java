package com.notepoint4u.servicesdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyServices extends Service {
    private static final String TAG = "Thread";

    //Deprecated
//    @Override
//    public void onStart(Intent intent, int startId) {
//        super.onStart(intent, startId);
//    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Service destroyed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Thread id:"+Thread.currentThread().getId());
        //To stop the service when it is done with the work.--- automatically stop
       // stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }
}
