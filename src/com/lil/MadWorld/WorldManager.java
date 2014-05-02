package com.lil.MadWorld;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import com.lil.MadWorld.Models.*;
import com.lil.MadWorld.Models.Character;

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
    private Hunter hunter;
    private ArrayList<Character> enemies;
    private int indexOfEnemy = 0;

    private Bitmap powerPicture;
    private int height;
    private int width;

    final Random myRandom = new Random();

    private long isHungry = FPS/2;

    public WorldManager(SurfaceHolder surfaceHolder, Context context)
    {
        this.surfaceHolder = surfaceHolder;

        vampire = new Vampire(loadFrames(context, "vampire"), 0, 0);

        enemies = new ArrayList<Character>();
        werewolf = new Werewolf(loadFrames(context, "werewolf"), -Vampire.DEFAULT_SPEED, -1);
        hunter = new Hunter(loadFrames(context, "hunter"), context.getResources().getDrawable(R.drawable.bullet01),
                                                                                        -Vampire.DEFAULT_SPEED, -1);

        enemies.add(werewolf);
        enemies.add(hunter);


        madWorld = new MadWorld(context.getResources().getDrawable(R.drawable.bckg), -Vampire.DEFAULT_SPEED);


        powerPicture = BitmapFactory.decodeResource(context.getResources(), R.drawable.power);
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
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt01));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt02));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt03));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt04));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt03));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt02));

            characterImages.add(context.getResources().getDrawable(R.drawable.hunt05));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt06));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt07));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt08));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt07));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt06));

            characterImages.add(context.getResources().getDrawable(R.drawable.hunt01));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt02));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt03));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt04));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt03));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt02));


        }

        return characterImages;
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

    private void refreshCanvas(Canvas c, boolean isVampCover) {
        madWorld.draw(c);

        if(isVampCover){
            enemies.get(indexOfEnemy).draw(c);
            vampire.draw(c);
        } else {
            vampire.draw(c);
            enemies.get(indexOfEnemy).draw(c);
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


        int wHealthCount = enemies.get(indexOfEnemy).getHealth()/10;
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

        final Character enemy = enemies.get(indexOfEnemy);
        if (!vampire.isUsingPower() && myRandom.nextInt(100) <= enemy.getChance())
            enemy.usePower(true);

        if (vampire.isUsingPower())
            enemy.usePower(false);

        int isCollision;
        if ((isCollision = isCollision()) > 0) {
           if (isCollision == 1) {
                vampire.paused();
                madWorld.paused();
                enemy.paused();
            }

            if (vampire.getPower() < enemy.getPower()) {
                vampire.makeWeaken();
                isVampCover = false;
            }
            if (isCollision == 1)
                enemy.makeWeaken();


            if (enemy.getHealth() <= 0 && vampire.getHealth() >= 0) {
                refreshEnemy(enemy);

                vampire.takeLife();
                vampire.continued();

                madWorld.continued();
            } else if (vampire.getHealth() <= 0) {
                vampire.setRight(0);
            }
        }


        madWorld.update();
        enemy.update();
        vampire.update();
        return isVampCover;
    }


    private void refreshEnemy(Character enemy){
        enemy.refresh();
        indexOfEnemy = myRandom.nextInt(2);
    }

    public void initPositions(int height, int width) {
        this.height = height;
        this.width = width;

        vampire.setCenterX(width / 2);
        vampire.setCenterY(height/2);
        vampire.setMaxRight(width);
        vampire.setMaxBottom(height);

        for (Character enemy:enemies){
            enemy.setRight(width);
            enemy.setCenterY(height / 2);
            enemy.setMaxRight(width);
            enemy.setMaxBottom(height);
        }

        madWorld.setMHeight(height);
        madWorld.setMWidth(width);
    }

    private int isCollision(){
        final Character enemy = enemies.get(indexOfEnemy);
        if ((vampire.getRight() >= enemy.getLeft()) &&
                (vampire.getLeft() <= enemy.getCenterX()) && !vampire.isUsingPower()) {
            enemy.setLeft(vampire.getLastThird());
            return 1;
        } else if ((vampire.getRight() >= enemy.getBulletLeftX())&&
                (vampire.getLeft() <= enemy.getBulletCenterX()) && !vampire.isUsingPower()) {
            return 2;
        }
        return 0;
    }

    public void onPowerClick() {
        vampire.usePower();
    }

    public void onTouch(float x, float y) {
        if ((x <= powerPicture.getWidth())&&(x >= 0)&&(y >= height - powerPicture.getHeight())&&(y < height))
            onPowerClick();
    }
}
