package com.lil.MadWorld;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Main extends Activity implements View.OnClickListener {
    private Dialog menu;
    private WorldView worldView;
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

        worldView = new WorldView(this);
        createMenu();

        startMenu();


        setContentView(worldView);

    }

    @Override
    protected void onStop() {
        worldView.setStarted(false);

        super.onStop();
//        startMenu();
//        finish();
    }

    @Override
    protected void onResume() {
        super.onStart();
        startMenu();
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("allWorld", worldView.returnState());
//        outState.putString("scoreString",score.getText().toString());
        //Log.d("cmd", "onSaveInstanceState");
        super.onSaveInstanceState(outState);

    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        worldView.establishState((WorldManager.State) savedInstanceState.getSerializable("allWorld"));
    }

    private void startMenu() {
        worldView.setStarted(false);
        if(menu!=null)
            menu.show();

    }

    private void createMenu() {
        if (menu == null) {
            menu = new Dialog(this, android.R.style.Theme_Translucent);
            menu.requestWindowFeature(Window.FEATURE_NO_TITLE);
            menu.setContentView(R.layout.start);
            menu.setCancelable(true);

            Button startButton = (Button) menu.findViewById(R.id.button1);
            startButton.setOnClickListener(this);

            Button exitButton = (Button) menu.findViewById(R.id.button2);
            exitButton.setOnClickListener(this);
        }
    }


    /** Обработка нажатия кнопок */
    public void onClick(View v) {
        switch (v.getId()) {
            //переход на сюрфейс
            case R.id.button1: {
                worldView.setStarted(true);

                menu.dismiss();
            }break;

            //выход
            case R.id.button2: {
                worldView.exitGame();
                menu.dismiss();

                finish();
            }break;

            default:
                break;
        }
    }

    public void onBackPressed() {
        startMenu();

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
