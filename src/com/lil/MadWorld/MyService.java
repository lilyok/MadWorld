package com.lil.MadWorld;//package com.lil.MadWorld;
//
//import android.app.Service;
//import android.content.Intent;
//import android.media.MediaPlayer;
//import android.media.MediaPlayer.OnErrorListener;
//import android.os.Binder;
//import android.os.IBinder;
//import android.widget.Toast;
//
//public class MyService extends Service  implements MediaPlayer.OnErrorListener {
//
//    private final IBinder mBinder = new ServiceBinder();
//    MediaPlayer mPlayer;
//    private int length = 0;
//
//    public MyService() {
////        this.onCreate();
//    }
//
//    public class ServiceBinder extends Binder {
//        MyService getService() {
//            return MyService.this;
//        }
//    }
//
//    @Override
//    public IBinder onBind(Intent arg0) {
//        return mBinder;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        try {
//            mPlayer = MediaPlayer.create(this, R.raw.paganini);
//            mPlayer.setOnErrorListener(this);
//
//            if (mPlayer != null) {
//                mPlayer.setLooping(true);
//                mPlayer.setVolume(100, 100);
//            }
//
//
//            mPlayer.setOnErrorListener(new OnErrorListener() {
//
//                public boolean onError(MediaPlayer mp, int what, int
//                        extra) {
//
//                    onError(mPlayer, what, extra);
//                    return true;
//                }
//            });
//        }catch (Exception e){
//                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        mPlayer.start();
//        return START_STICKY;
//    }
//
//    public void pauseMusic() {
//        if (mPlayer.isPlaying()) {
//            mPlayer.pause();
//            length = mPlayer.getCurrentPosition();
//
//        }
//    }
//
//    public void resumeMusic() {
//        if (mPlayer == null) {
//            this.onCreate();
//        }
//        if (mPlayer.isPlaying() == false) {
//            mPlayer.seekTo(length);
//            mPlayer.start();
//        }
//
//    }
//
//    public void stopMusic() {
//        mPlayer.stop();
//        mPlayer.release();
//        mPlayer = null;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mPlayer != null) {
//            try {
//                mPlayer.stop();
//                mPlayer.release();
//            } finally {
//                mPlayer = null;
//            }
//        }
//    }
//
//    public boolean onError(MediaPlayer mp, int what, int extra) {
//
//        Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
//        if (mPlayer != null) {
//            try {
//                mPlayer.stop();
//                mPlayer.release();
//            } finally {
//                mPlayer = null;
//            }
//        }
//        return false;
//    }
//}


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service{
    private static final String LOGCAT = null;
    MediaPlayer objPlayer;

    public void onCreate(){
        super.onCreate();
        Log.d(LOGCAT, "Service Started!");
        objPlayer = MediaPlayer.create(this, R.raw.paganini);
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        objPlayer.start();
        Log.d(LOGCAT, "Media Player started!");
        if(objPlayer.isLooping() != true){
            Log.d(LOGCAT, "Problem in Playing Audio");
        }
        return 1;
    }

    public void onStop(){
        objPlayer.stop();
        objPlayer.release();
    }

    public void onPause(){
        objPlayer.stop();
        objPlayer.release();
    }
    public void onDestroy(){
        objPlayer.stop();
        objPlayer.release();
    }
    @Override
    public IBinder onBind(Intent objIndent) {
        return null;
    }
}
