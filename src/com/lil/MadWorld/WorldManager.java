package com.lil.MadWorld;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import com.lil.MadWorld.Models.MadWorld;
import com.lil.MadWorld.Models.Vampire;
import com.lil.MadWorld.Models.Werewolf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldManager extends Thread {
    private final SurfaceHolder surfaceHolder;

    private boolean running = false;
    static final long FPS = 25;

    private MadWorld madWorld;
    private Vampire vampire;
    private Werewolf werewolf;

    private Bitmap powerPicture;
    private int height;

    final Random myRandom = new Random();



    public WorldManager(SurfaceHolder surfaceHolder, Context context)
    {
        this.surfaceHolder = surfaceHolder;

        vampire = new Vampire(loadFrames(context, "vampire"), 0, 0);

        werewolf = new Werewolf(loadFrames(context, "werewolf"), -Vampire.DEFAULT_SPEED, -1);
        madWorld = new MadWorld(context.getResources().getDrawable(R.drawable.bckg), -Vampire.DEFAULT_SPEED);

        powerPicture = BitmapFactory.decodeResource(context.getResources(), R.drawable.power);
    }

    public void setRunning(boolean run)
    {
        running = run;
    }

    public void run()
    {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;


        while (running){
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    updateObjects();
                    refreshCanvas(c);
                }
            }
            catch (Exception e) {
            }
            finally {
                if(c != null){
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try{
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {}

        }
    }


    private List<Drawable> loadFrames(Context context, String type) {
        List<Drawable> characterImages = new ArrayList<Drawable>();
        if ("vampire".equals(type)) {
            characterImages.add(context.getResources().getDrawable(R.drawable.vampire1));
            characterImages.add(context.getResources().getDrawable(R.drawable.vampire2));
        } else if ("werewolf".equals(type)) {
            characterImages.add(context.getResources().getDrawable(R.drawable.werewolf1));
            characterImages.add(context.getResources().getDrawable(R.drawable.werewolf2));

        } else if ("hunter".equals(type)) {
            characterImages.add(context.getResources().getDrawable(R.drawable.man1));
            characterImages.add(context.getResources().getDrawable(R.drawable.man2));
        }

        return characterImages;
    }

    private void refreshCanvas(Canvas c) {
        madWorld.draw(c);
        vampire.draw(c);
        werewolf.draw(c);

        powerDraw(c);
        healthDraw(c);
    }


    private void powerDraw(Canvas c){
        c.drawBitmap(powerPicture, 0, this.height - powerPicture.getHeight(), null);
//

    }

    private  void healthDraw(Canvas c){
        int portionCount = vampire.getHealth()/10;
        float portionWith = 30;
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        for (int i =0; i < portionCount; i++){
            c.drawRect(i*portionWith, 0, (i+1)*portionWith-1, 30, paint);
        }
    }


    private void updateObjects() {
        if (isCollision()) {
            vampire.paused();
            madWorld.paused();
            werewolf.paused();
            if (myRandom.nextInt(100) % 30 ==0)
                werewolf.usePower(true);
            if (vampire.getPower() < werewolf.getPower())
                vampire.makeWeaken();
                //vampire.setRight(0);
            else {
                werewolf.makeWeaken();
//                werewolf.continued();
//                vampire.continued();
//                madWorld.continued();
            }

            if (werewolf.getHealth() <= 0 && vampire.getHealth() >= 0){
                werewolf.refresh();
                vampire.continued();
                madWorld.continued();
            } else if (vampire.getHealth() <= 0) {
                vampire.setRight(0);
            }
        } else {
            werewolf.usePower(false);
        }


        madWorld.update();
        vampire.update();
        werewolf.update();
    }

    public void initPositions(int height, int width) {
        this.height = height;

        vampire.setCenterX(width / 2);
        vampire.setCenterY(height/2);
        vampire.setMaxRight(width);
        vampire.setMaxBottom(height);



        werewolf.setRight(width);
        werewolf.setCenterY(height/2);
        werewolf.setMaxRight(width);
        werewolf.setMaxBottom(height);

        madWorld.setMHeight(height);
        madWorld.setMWidth(width);
    }

    private boolean isCollision(){
        if ((vampire.getRightX() >= werewolf.getLeftX()) && (vampire.getLeftX() <= werewolf.getCenterX()) && !vampire.isUsingPower()) {
            return true;
        }
        return false;
    }

    public void onPowerClick() {
        vampire.usePower();
    }

    public void onTouch(float x, float y) {
        if ((x <= powerPicture.getWidth())&&(x >= 0)&&(y >= height - powerPicture.getHeight())&&(y < height))
            onPowerClick();
    }
}
