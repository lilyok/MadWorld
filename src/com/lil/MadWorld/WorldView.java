package com.lil.MadWorld;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class WorldView extends SurfaceView implements SurfaceHolder.Callback{
    private SurfaceHolder holder;

    private WorldManager gameLoopThread;
    public WorldView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        gameLoopThread = new WorldManager(holder, context);
        gameLoopThread.setPriority(8);
    }


    public void exitGame(){
        boolean retry = true;
        gameLoopThread.setRunning(false);
        while (retry){
            try{
                gameLoopThread.join();
                retry = false;
            } catch (InterruptedException e)
            {
                Log.e("exit = ", e.toString());
            }
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
       // exitGame();

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
//        Thread.State state = gameLoopThread.getState();
//        if (gameLoopThread.getState() == Thread.State.TERMINATED) {
//            gameLoopThread = new WorldManager(getHolder(), this.getContext());
//            gameLoopThread.setRunning(true);
//            gameLoopThread.start();
//        }
//        else
        if (gameLoopThread.getState() == Thread.State.NEW) {
            gameLoopThread.start();
            gameLoopThread.setRunning(true);
        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        if (width  > 0)
            gameLoopThread.initPositions(height, width);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gameLoopThread.onTouch(event.getX(), event.getY());
        return super.onTouchEvent(event);
    }

    public void setStarted(boolean f)   {
        gameLoopThread.setStarted(f);

    }


    public Boolean isUsingPower(){
        return gameLoopThread.isUsingPower();
    }

    public void setUsingPower(Boolean usingPower) {
        gameLoopThread.setUsingPower(usingPower);
    }

    public int getIndexOfEnemy(){
        return gameLoopThread.getIndexOfEnemy();
    }

    public void setIndexOfEnemy(int indexOdEnemy){
            gameLoopThread.setIndexOfEnemy(indexOdEnemy);
    }

    public long getIsHungry(){
        return gameLoopThread.getIsHungry();
    }

    public void setIsHungry(long isHungry){
            gameLoopThread.setIsHungry(isHungry);
    }

    public int getEnemyLeft(){
        return gameLoopThread.getEnemyLeft();
    }

    public void setEnemyLeft(int enemyLeft){
            gameLoopThread.setEnemyLeft(enemyLeft);
    }

    public int getMadWorldLeft(){
        return gameLoopThread.getMadWorldLeft();
    }

    public void setMadWorldLeft(int madWorldLeft) {
            gameLoopThread.setMadWorldLeft(madWorldLeft);
    }

    public int getIndexOfFirstImage(){
        return gameLoopThread.getIndexOfFirstImage();
    }

    public void setIndexOfFirstImage(int indexOfFirstImage){
            gameLoopThread.setIndexOfFirstImage(indexOfFirstImage);
    }

    public int getHealth(){
        return gameLoopThread.getHealth();
    }

    public void setHealth(int health){
            gameLoopThread.setHealth(health);
    }

    public int getEnemyHealth(){
        return gameLoopThread.getEnemyHealth();
    }

    public void setEnemyHealth(int enemyHealth){
            gameLoopThread.setEnemyHealth(enemyHealth);
    }

    public int getSunProtection(){
        return gameLoopThread.getSunProtection();
    }

    public void setSunProtection(int sunProtection){
            gameLoopThread.setSunProtection(sunProtection);
    }

    public void setMImageByFirst() {
        gameLoopThread.setMImageByFirst();
    }

    public boolean getIsMoving(){

        return gameLoopThread.getIsMoving();
    }

    public void setIsMoving(boolean f){
        gameLoopThread.setIsMoving(f);
    }

    public boolean getWorldIsMoving(){

        return gameLoopThread.getWorldIsMoving();
    }

    public void setWorldIsMoving(boolean f){
        gameLoopThread.setWorldIsMoving(f);
    }

    public boolean getEnemyIsMoving(){
        return gameLoopThread.getEnemyIsMoving();
    }

    public void setEnemyIsMoving(boolean f){
        gameLoopThread.setEnemyIsMoving(f);
    }

    public void initVampirePosition() {
        gameLoopThread.initVampirePosition();
    }

    public void cleanBaseIndexOfFrame() {
        gameLoopThread.cleanBaseIndexOfFrame();
    }

    public void setGiftsStatus(){
        gameLoopThread.setGiftsStatus();
    }
}

