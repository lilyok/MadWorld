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
//
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
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


    public WorldManager.State returnState() {
        return gameLoopThread.returnState();
    }

    public void establishState(WorldManager.State allWorld) {
        gameLoopThread.establishState(allWorld);
    }
}

