package com.lil.MadWorld;

import android.app.Activity;
import android.app.Dialog;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import com.lil.MadWorld.Models.*;
import com.lil.MadWorld.Models.Character;

import java.util.ArrayList;
import java.util.List;

public class Main extends Activity implements View.OnClickListener {
    private Dialog menu;
    private Dialog help;
    private Dialog about;

//    private Dialog alert;
    private WorldView worldView;
    private SharedPreferences sPref;
//    private boolean isExit = false;
    private boolean isRestart = false;

    private List<String> tasks = new ArrayList();
//    private boolean mIsBound = false;
//    private MyService mServ;
//
//
//    private ServiceConnection Scon =new ServiceConnection(){
//
//        public void onServiceConnected(ComponentName name, IBinder
//                binder) {
//            mServ = ((MyService.ServiceBinder) binder).getService();
//        }
//
//        public void onServiceDisconnected(ComponentName name) {
//            mServ = null;
//        }
//    };
//
//    void doBindService(){
//        bindService(new Intent(this,MyService.class),
//                Scon,Context.BIND_AUTO_CREATE);
//        mIsBound = true;
//    }
//
//    void doUnbindService()
//    {
//        if(mIsBound)
//        {
//            unbindService(Scon);
//            mIsBound = false;
//        }
//    }
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


//        alert = null;

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

        worldView.savePreferences(ed);

        ed.commit();
    }

    void restorePref(boolean isRestart){
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

        worldView.restorePreferences(sPref, isRestart);

        isRestart = sPref.getBoolean("isRestart", false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePref();
        Intent objIntent = new Intent(this, MyService.class);
        stopService(objIntent);
        Log.d("status activity", "ActivityA: onPause()");

    }
    @Override
    protected void onStop() {
        worldView.setStarted(false);

        super.onStop();
        if (menu != null){
            menu.dismiss();
            menu = null;
        }

        if(help != null){
            help.dismiss();
            help = null;
        }

        if(about != null){
            about.dismiss();
            about = null;
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

//        Intent objIntent = new Intent(this, MyService.class);
//        startService(objIntent);

        Log.d("status activity", "ActivityA: onResume()");
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();


        Log.d("status activity", "ActivityA: onDestroy()");
    }

    private void clearPref(boolean onlyCurrentLevel) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        int taskIndex = 0;
        if (onlyCurrentLevel){
            taskIndex = worldView.getTaskIndex();
        }
        ed.clear();
        ed.putInt("taskIndex", taskIndex);
        ed.commit();
    }

    @Override
    protected void onStart() {

        super.onStart();
        Log.d("status activity", "ActivityA: onStart()");
        restorePref(false);

        createMenu();

        startMenu();
    }

    private void startMenu() {
        worldView.setStarted(false);
        if(menu!=null)
            menu.show();
        Intent objIntent = new Intent(this, MyService.class);
        stopService(objIntent);

    }

    private void startHelp() {
        if(help!=null)
            help.show();
    }

    private void createHelp(){
        if (help == null){
            help = new Dialog(this, android.R.style.Theme_Translucent);
            help.requestWindowFeature(Window.FEATURE_NO_TITLE);
            help.setContentView(R.layout.help);
            help.setCancelable(false);
            Button toGameBtn = (Button) help.findViewById(R.id.toGameButton);
            toGameBtn.setOnClickListener(this);
        }
    }

    private void startAbout() {
        if(about!=null)
            about.show();
    }

    private void createAbout(){
        if (about == null){
            about = new Dialog(this, android.R.style.Theme_Translucent);
            about.requestWindowFeature(Window.FEATURE_NO_TITLE);
            about.setContentView(R.layout.about);
            about.setCancelable(false);
            Button toMenuBtn = (Button) about.findViewById(R.id.toMenuButton);
            toMenuBtn.setOnClickListener(this);
        }
    }

    private void createMenu() {
        if (menu == null) {
            menu = new Dialog(this, android.R.style.Theme_Translucent);
            menu.requestWindowFeature(Window.FEATURE_NO_TITLE);
            menu.setContentView(R.layout.start);
            menu.setCancelable(false);

            createHelp();
            createAbout();

            Button helpButton = (Button) menu.findViewById(R.id.helpBtn);
            helpButton.setOnClickListener(this);

            Button aboutButton = (Button) menu.findViewById(R.id.aboutBtn);
            aboutButton.setOnClickListener(this);


            Button newGameButton = (Button) menu.findViewById(R.id.newGameBtn);
            newGameButton.setOnClickListener(this);

            Button startButton = (Button) menu.findViewById(R.id.startBtn);
            startButton.setOnClickListener(this);

            Button exitButton = (Button) menu.findViewById(R.id.exitBtn);
            exitButton.setOnClickListener(this);

            Button restartButton = (Button) menu.findViewById(R.id.restartBtn);
            restartButton.setOnClickListener(this);
            if (!isRestart) {
                restartButton.setVisibility(View.INVISIBLE);
                startButton.setText(getString(R.string.startString));
            } else {
                restartButton.setVisibility(View.VISIBLE);
                startButton.setText(getString(R.string.continueString));
            }
        } else {
            Button restartButton = (Button) menu.findViewById(R.id.restartBtn);
            restartButton.setVisibility(View.VISIBLE);

            Button startButton = (Button) menu.findViewById(R.id.startBtn);
            startButton.setText(getString(R.string.continueString));
        }
    }


    /** Обработка нажатия кнопок */
    public void onClick(View v) {
        switch (v.getId()) {
            //переход на сюрфейс
            case R.id.startBtn: {
                worldView.setStarted(true);
                Intent objIntent = new Intent(this, MyService.class);
                startService(objIntent);

                menu.dismiss();
            }break;
            case R.id.restartBtn: {
                clearPref(true);
                restorePref(true);

                worldView.initVampirePosition();
                worldView.cleanBaseIndexOfFrame();
                worldView.setStarted(true);

                Intent objIntent = new Intent(this, MyService.class);
                startService(objIntent);

                menu.dismiss();
            }break;
            //выход
            case R.id.exitBtn: {
                worldView.exitGame();

                menu.dismiss();
//                isExit = true;
                finish();
            }break;
            //очистить все
            case R.id.newGameBtn: {
                clearPref(false);
                restorePref(true);

                worldView.initVampirePosition();
                worldView.cleanBaseIndexOfFrame();
                worldView.setStarted(true);

                Intent objIntent = new Intent(this, MyService.class);
                startService(objIntent);

                menu.dismiss();
            }break;
            case R.id.helpBtn: {
                startHelp();
            }break;
            case R.id.toGameButton: {
                help.dismiss();
            }break;
            case R.id.aboutBtn: {
                startAbout();
            }break;
            case R.id.toMenuButton: {
                about.dismiss();
            }break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        isRestart = true;
        createMenu();
        startMenu();

    }


}
