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
    static final long FPS = 10;

    private MadWorld madWorld;
    private Vampire vampire;
    private Werewolf werewolf;


    private Bitmap powerPicture;
    private int height;
    private int width;

    final Random myRandom = new Random();

    private long isHungry = FPS/2;

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
                    boolean isVampCover  = updateObjects();
                    refreshCanvas(c, isVampCover);
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
            characterImages.add(context.getResources().getDrawable(R.drawable.vamp01));
            characterImages.add(context.getResources().getDrawable(R.drawable.vamp02));
            characterImages.add(context.getResources().getDrawable(R.drawable.vamp03));
            characterImages.add(context.getResources().getDrawable(R.drawable.vamp04));
            characterImages.add(context.getResources().getDrawable(R.drawable.vamp03));
            characterImages.add(context.getResources().getDrawable(R.drawable.vamp02));

            characterImages.add(context.getResources().getDrawable(R.drawable.vamp05));
            characterImages.add(context.getResources().getDrawable(R.drawable.vamp06));
            characterImages.add(context.getResources().getDrawable(R.drawable.vamp07));
            characterImages.add(context.getResources().getDrawable(R.drawable.vamp08));
            characterImages.add(context.getResources().getDrawable(R.drawable.vamp07));
            characterImages.add(context.getResources().getDrawable(R.drawable.vamp06));

            characterImages.add(context.getResources().getDrawable(R.drawable.vamp09));
            characterImages.add(context.getResources().getDrawable(R.drawable.vamp10));
            characterImages.add(context.getResources().getDrawable(R.drawable.vamp11));
            characterImages.add(context.getResources().getDrawable(R.drawable.vamp12));
            characterImages.add(context.getResources().getDrawable(R.drawable.vamp11));
            characterImages.add(context.getResources().getDrawable(R.drawable.vamp10));



        } else if ("werewolf".equals(type)) {
            characterImages.add(context.getResources().getDrawable(R.drawable.were01));
            characterImages.add(context.getResources().getDrawable(R.drawable.were02));
            characterImages.add(context.getResources().getDrawable(R.drawable.were03));
            characterImages.add(context.getResources().getDrawable(R.drawable.were04));
            characterImages.add(context.getResources().getDrawable(R.drawable.were03));
            characterImages.add(context.getResources().getDrawable(R.drawable.were02));

            characterImages.add(context.getResources().getDrawable(R.drawable.were05));
            characterImages.add(context.getResources().getDrawable(R.drawable.were06));
            characterImages.add(context.getResources().getDrawable(R.drawable.were06a));
            characterImages.add(context.getResources().getDrawable(R.drawable.were07));
            characterImages.add(context.getResources().getDrawable(R.drawable.were08));
            characterImages.add(context.getResources().getDrawable(R.drawable.were08a));

            characterImages.add(context.getResources().getDrawable(R.drawable.were09));
            characterImages.add(context.getResources().getDrawable(R.drawable.were10));
            characterImages.add(context.getResources().getDrawable(R.drawable.were11));
            characterImages.add(context.getResources().getDrawable(R.drawable.were12));
            characterImages.add(context.getResources().getDrawable(R.drawable.were11));
            characterImages.add(context.getResources().getDrawable(R.drawable.were10));

        } else if ("hunter".equals(type)) {
            characterImages.add(context.getResources().getDrawable(R.drawable.man1));
            characterImages.add(context.getResources().getDrawable(R.drawable.man2));
            characterImages.add(context.getResources().getDrawable(R.drawable.man2));
        }

        return characterImages;
    }

    private void refreshCanvas(Canvas c, boolean isVampCover) {
        madWorld.draw(c);

        if(isVampCover){
            werewolf.draw(c);
            vampire.draw(c);
        } else {
            vampire.draw(c);
            werewolf.draw(c);
        }


        powerDraw(c);
        healthDraw(c);
    }


    private void powerDraw(Canvas c){
        c.drawBitmap(powerPicture, 0, this.height - powerPicture.getHeight(), null);
    }

    private  void healthDraw(Canvas c){
        float blockWith = 30;

        int vHealthCount = vampire.getHealth()/10;
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        for (int i =0; i < vHealthCount; i++){
            c.drawRect(i*blockWith, 0, (i+1)*blockWith-1, 30, paint);
        }


        int wHealthCount = werewolf.getHealth()/10;
        paint.setColor(Color.RED);
        for (int i =0; i < wHealthCount; i++){
            c.drawRect(width - i*blockWith, 0, width - (i+1)*blockWith+1, 30, paint);
        }


    }


    private boolean updateObjects() {
        boolean isVampCover = true;

        isHungry--;
        if (isHungry <= 0) {
            vampire.makeWeaken();
            isHungry = FPS/2;
        }

        if (!vampire.isUsingPower() && myRandom.nextInt(100) % 30 ==0)
            werewolf.usePower(true);

        if (vampire.isUsingPower())
            werewolf.usePower(false);


        if (isCollision()) {
            vampire.paused();
            madWorld.paused();
            werewolf.paused();

            if (vampire.getPower() < werewolf.getPower()) {
                vampire.makeWeaken();
                isVampCover = false;
            } else
                werewolf.makeWeaken();


            if (werewolf.getHealth() <= 0 && vampire.getHealth() >= 0) {
                werewolf.refresh();

                vampire.takeLife();
                vampire.continued();

                madWorld.continued();
            } else if (vampire.getHealth() <= 0) {
                vampire.setRight(0);
            }
        }
//        } else {
//            werewolf.usePower(false);
//        }


        madWorld.update();
        werewolf.update();
        vampire.update();
        return isVampCover;
    }

    public void initPositions(int height, int width) {
        this.height = height;
        this.width = width;

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
            werewolf.setLeft(vampire.getLastFourth());
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
