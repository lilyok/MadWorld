package com.lil.MadWorld;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class WorldView extends SurfaceView implements SurfaceHolder.Callback{
    private SurfaceHolder holder;

    private WorldManager gameLoopThread;
    public WorldView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        gameLoopThread = new WorldManager(holder, context);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        gameLoopThread.setRunning(false);
        while (retry){
            try{
                gameLoopThread.join();
                retry = false;
            } catch (InterruptedException e)
            {

            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {

        gameLoopThread.setRunning(true);
        if (!gameLoopThread.isAlive())
            gameLoopThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        gameLoopThread.initPositions(height, width);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gameLoopThread.onTouch(event.getX(), event.getY());
        return super.onTouchEvent(event);
    }}
