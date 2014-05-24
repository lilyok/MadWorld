package com.lil.MadWorld;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import com.lil.MadWorld.Models.*;
import com.lil.MadWorld.Models.Character;

import java.util.ArrayList;
import java.util.List;

public class Main extends Activity implements View.OnClickListener {
    private Dialog menu;
    private Dialog alert;
    private WorldView worldView;
    private SharedPreferences sPref;
    private boolean isExit = false;
    private boolean isRestart = false;

    private List<String> tasks = new ArrayList();
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


        alert = null;

        worldView = new WorldView(this);


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
        ed.putBoolean("isMoving", worldView.getIsMoving());
        ed.putBoolean("enemyIsMoving", worldView.getEnemyIsMoving());
        ed.putBoolean("worldIsMoving", worldView.getWorldIsMoving());
        ed.putBoolean("isRestart", true);
        ed.putInt("indexOfFire", worldView.getBaseOfFire());
        ed.putInt("indexOfBlood", worldView.getBaseOfBlood());

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

        worldView.setIsMoving(sPref.getBoolean("isMoving", true));
        worldView.setEnemyIsMoving(sPref.getBoolean("enemyIsMoving", true));
        worldView.setWorldIsMoving(sPref.getBoolean("worldIsMoving", true));
        worldView.setGiftsStatus();

        worldView.setMImageByFirst();

        worldView.setIsBlooded(false);
        worldView.setIsFired(false);
        worldView.setBaseOfFire(sPref.getInt("indexOfFire", 0));
        worldView.setBaseOfBlood(sPref.getInt("indexOfBlood", 0));

        isRestart = sPref.getBoolean("isRestart", false);
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
//        restorePref();
        Log.d("status activity", "ActivityA: onResume()");
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (isExit) {
            clearPref();
        }

        Log.d("status activity", "ActivityA: onDestroy()");
    }

    private void clearPref() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.clear();
        ed.commit();
    }

    @Override
    protected void onStart() {

        super.onStart();
        Log.d("status activity", "ActivityA: onStart()");
        restorePref();

        createMenu();

        startMenu();
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

            Button startButton = (Button) menu.findViewById(R.id.startBtn);
            startButton.setOnClickListener(this);

            Button exitButton = (Button) menu.findViewById(R.id.exitBtn);
            exitButton.setOnClickListener(this);

            Button restartButton = (Button) menu.findViewById(R.id.restartBtn);
            restartButton.setOnClickListener(this);
            if (!isRestart) {
                restartButton.setVisibility(View.INVISIBLE);
                startButton.setText("Start");
            }
            else {
                restartButton.setVisibility(View.VISIBLE);
                startButton.setText("Continue");
            }
        } else {
            Button restartButton = (Button) menu.findViewById(R.id.restartBtn);
            restartButton.setVisibility(View.VISIBLE);

            Button startButton = (Button) menu.findViewById(R.id.startBtn);
            startButton.setText("Continue");
        }
    }


    /** Обработка нажатия кнопок */
    public void onClick(View v) {
        switch (v.getId()) {
            //переход на сюрфейс
            case R.id.startBtn: {
                worldView.setStarted(true);

                menu.dismiss();
            }break;
            case R.id.restartBtn: {
                clearPref();
                restorePref();

                worldView.initVampirePosition();
                worldView.cleanBaseIndexOfFrame();
                worldView.setStarted(true);

                menu.dismiss();
            }break;
            //выход
            case R.id.exitBtn: {
                worldView.exitGame();

                menu.dismiss();
                isExit = true;
                finish();
            }break;

            default:
                break;
        }
    }

    public void onBackPressed() {
        isRestart = true;
        createMenu();
        startMenu();

    }


}
