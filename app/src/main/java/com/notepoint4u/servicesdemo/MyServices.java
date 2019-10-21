package com.notepoint4u.servicesdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

public class MyServices extends Service {
    private static final String TAG = "Thread";

    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;
    private final int MIN = 0;
    private final int MAX = 100;

//    Deprecated
//    @Override
//    public void onStart(Intent intent, int startId) {
//        super.onStart(intent, startId);
//    }

    //To get the IBinder instance:
    //1. create a class and extend the Binder
    //2. Create an instance of IBinder
    //3. Return the IBinder instance in onBind()
    public class MyServiceBinder extends Binder{
        public MyServices getBinder(){
            return MyServices.this;
        }
    }

    private IBinder mIBinder = new MyServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mIBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Service destroyed");
        stopRandomNumberGenerator();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Thread id:"+Thread.currentThread().getId());
        //To stop the service when it is done with the work.--- automatically stop
       // stopSelf();

        mIsRandomGeneratorOn = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                onStartRandomNumberGenerator();
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    private void onStartRandomNumberGenerator(){
        while (mIsRandomGeneratorOn){
            try {
                Thread.sleep(1000);
                if (mIsRandomGeneratorOn){
                    mRandomNumber = new Random().nextInt(MAX)+MIN;
                    Log.d(TAG, "Thread id: "+Thread.currentThread().getId()+", Random Number: "+ mRandomNumber);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopRandomNumberGenerator(){
        mIsRandomGeneratorOn =false;
    }

    public int getRandomNumber(){
        return mRandomNumber;
    }
}
