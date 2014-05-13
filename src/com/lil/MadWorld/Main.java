package com.lil.MadWorld;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Main extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // если хотим, чтобы приложение постоянно имело портретную ориентацию
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // если хотим, чтобы приложение было полноэкранным
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // и без заголовка
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(new WorldView(this));
    }
}


//public class Main extends Activity {
//    private MyGLSurfaceView glView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        glView = new MyGLSurfaceView(this);
//        setContentView(glView);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        glView.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        glView.onResume();
//    }
//
//
//}
