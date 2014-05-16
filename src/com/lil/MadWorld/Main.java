package com.lil.MadWorld;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.lil.MadWorld.Models.*;
import com.lil.MadWorld.Models.Character;

public class Main extends Activity implements View.OnClickListener {
    private Dialog menu;
    private WorldView worldView;
    private SharedPreferences sPref;
    private boolean isExit = false;
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
//        createMenu();
//
//        startMenu();


        setContentView(worldView);
        Log.d("status activity", "ActivityA: onCreate");

    }


    void savePref() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean("isUsingPower", worldView.isUsingPower());
        ed.putInt("indexOfEnemy", worldView.getIndexOfEnemy());
        ed.putLong("isHungry", worldView.getIsHungry());
        ed.putInt("enemyLeft", worldView.getEnemyLeft());
        ed.putInt("madWorldLeft", worldView.getMadWorldLeft());
        ed.putInt("indexOfFirstImage", worldView.getIndexOfFirstImage());
        ed.putInt("health", worldView.getHealth());
        ed.putInt("enemyHealth",worldView.getEnemyHealth());
        ed.putInt("sunProtection", worldView.getSunProtection());
        ed.commit();
    }

    void restorePref(){
        sPref = getPreferences(MODE_PRIVATE);
        worldView.setUsingPower(sPref.getBoolean("isUsingPower", false));
        worldView.setIndexOfEnemy(sPref.getInt("indexOfEnemy", 0));
        worldView.setIsHungry(sPref.getLong("isHungry", WorldManager.DEFAULT_HUNGRY_SPEED));
        worldView.setEnemyLeft(sPref.getInt("enemyLeft", -1000));
        worldView.setMadWorldLeft(sPref.getInt("madWorldLeft", 0));
        worldView.setIndexOfFirstImage(sPref.getInt("indexOfFirstImage", 1));
        worldView.setHealth(sPref.getInt("health", Character.DEFAULT_HEALTH));
        worldView.setEnemyHealth(sPref.getInt("enemyHealth", Character.DEFAULT_HEALTH));
        worldView.setSunProtection(sPref.getInt("sunProtection", Character.DEFAULT_HEALTH));
        worldView.setMImageByFirst();
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePref();
        Log.d("status activity", "ActivityA: onPause()");

    }
    @Override
    protected void onStop() {
        worldView.setStarted(false);
//        menu.dismiss();

        super.onStop();
        if (menu != null){
            menu.dismiss();
            menu = null;
        }
        finish();

        Log.d("status activity", "ActivityA: onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d("status activity", "ActivityA: onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        restorePref();
        Log.d("status activity", "ActivityA: onResume()");
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (isExit) {
            sPref = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.clear();
            ed.commit();
        }

        Log.d("status activity", "ActivityA: onDestroy()");
    }
    @Override
    protected void onStart() {

        super.onStart();
        Log.d("status activity", "ActivityA: onStart()");

        createMenu();
        startMenu();
    }
//
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putSerializable("allWorld", worldView.returnState());
//        super.onSaveInstanceState(outState);
//        Log.d("status activity", "ActivityA: onSaveInstanceState");
//    }
//
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        worldView.establishState((WorldManager.State) savedInstanceState.getSerializable("allWorld"));
//        Log.d("status activity", "ActivityA: onRestoreInstanceState");
//
//    }

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

//                sPref = getPreferences(MODE_PRIVATE);
//                SharedPreferences.Editor ed = sPref.edit();
//                ed.clear();
//                ed.commit();

                menu.dismiss();
                isExit = true;
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
