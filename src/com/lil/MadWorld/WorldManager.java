package com.lil.MadWorld;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import com.lil.MadWorld.Models.*;
import com.lil.MadWorld.Models.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldManager extends Thread {
    private final SurfaceHolder surfaceHolder;

    private boolean running = false;
    static final long FPS = 25;

    private MadWorld madWorld;
    private Vampire vampire;
    private CloseHandedCharacter werewolf;
    private FireArmedCharacter hunter;
    private FireArmedCharacter fay;
    private FireArmedCharacter wizard;


    private ArrayList<Character> enemies;
    private int indexOfEnemy = 3;

    private Bitmap powerPicture;
    private Bitmap takePicture;

    private int height;
    private int width;

    final Random myRandom = new Random();

    private static final int DEFAULT_HUNGRY_SPEED = 20;
    private long isHungry = DEFAULT_HUNGRY_SPEED;


    public WorldManager(SurfaceHolder surfaceHolder, Context context)
    {
        this.surfaceHolder = surfaceHolder;

        vampire = new Vampire(loadFrames(context, "vampire"), 0, 0);

        enemies = new ArrayList<Character>();
        werewolf = new CloseHandedCharacter(loadFrames(context, "werewolf"), -Vampire.DEFAULT_SPEED,
                                            -1, Character.WEREWOLF_POWER);
        hunter = new FireArmedCharacter(loadFrames(context, "hunter"), loadFrames(context, "bullet"),
                                        -Vampire.DEFAULT_SPEED, -1, false, Character.BULLET_POWER);
        fay = new FireArmedCharacter(loadFrames(context, "fay"), loadFrames(context, "fireball"),
                                    -Vampire.DEFAULT_SPEED, -1, false, Character.BULLET_POWER);
        fay.setHavingSun(true);

        wizard = new FireArmedCharacter(loadFrames(context, "wizard"), loadFrames(context, "firerain"),
                                        -Vampire.DEFAULT_SPEED, -1, true, Character.BULLET_POWER);

        enemies.add(werewolf);
        enemies.add(hunter);
        enemies.add(fay);
        enemies.add(wizard);


        madWorld = new MadWorld(loadFrames(context, "world"), loadFrames(context, "gifts"), -Vampire.DEFAULT_SPEED);


        powerPicture = BitmapFactory.decodeResource(context.getResources(), R.drawable.power);
        takePicture = BitmapFactory.decodeResource(context.getResources(), R.drawable.take);
    }

    private List<Drawable> loadFrames(Context context, String type) {
        List<Drawable> characterImages = new ArrayList<Drawable>();
        if ("world".equals(type)){
            characterImages.add(context.getResources().getDrawable(R.drawable.background01));
            characterImages.add(context.getResources().getDrawable(R.drawable.background02));
        } else if ("vampire".equals(type)) {
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

            characterImages.add(context.getResources().getDrawable(R.drawable.hunt09));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt10));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt11));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt12));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt11));
            characterImages.add(context.getResources().getDrawable(R.drawable.hunt10));

        } else if ("bullet".equals(type)) {
            characterImages.add(context.getResources().getDrawable(R.drawable.bullet01));
            characterImages.add(context.getResources().getDrawable(R.drawable.bullet03));
            characterImages.add(context.getResources().getDrawable(R.drawable.bullet02));

        } else if ("fay".equals(type)) {
            characterImages.add(context.getResources().getDrawable(R.drawable.fay01));
            characterImages.add(context.getResources().getDrawable(R.drawable.fay02));
            characterImages.add(context.getResources().getDrawable(R.drawable.fay03));
            characterImages.add(context.getResources().getDrawable(R.drawable.fay04));
            characterImages.add(context.getResources().getDrawable(R.drawable.fay03));
            characterImages.add(context.getResources().getDrawable(R.drawable.fay02));

            characterImages.add(context.getResources().getDrawable(R.drawable.fay05));
            characterImages.add(context.getResources().getDrawable(R.drawable.fay06));
            characterImages.add(context.getResources().getDrawable(R.drawable.fay07));
            characterImages.add(context.getResources().getDrawable(R.drawable.fay08));
            characterImages.add(context.getResources().getDrawable(R.drawable.fay07));
            characterImages.add(context.getResources().getDrawable(R.drawable.fay06));

            characterImages.add(context.getResources().getDrawable(R.drawable.fay09));
            characterImages.add(context.getResources().getDrawable(R.drawable.fay10));
            characterImages.add(context.getResources().getDrawable(R.drawable.fay11));
            characterImages.add(context.getResources().getDrawable(R.drawable.fay12));
            characterImages.add(context.getResources().getDrawable(R.drawable.fay11));
            characterImages.add(context.getResources().getDrawable(R.drawable.fay10));

        } else if ("fireball".equals(type)) {
            characterImages.add(context.getResources().getDrawable(R.drawable.fireball01));
            characterImages.add(context.getResources().getDrawable(R.drawable.fireball03));
            characterImages.add(context.getResources().getDrawable(R.drawable.fireball02));

        } else if ("wizard".equals(type)) {
            characterImages.add(context.getResources().getDrawable(R.drawable.wizard01));
            characterImages.add(context.getResources().getDrawable(R.drawable.wizard02));
            characterImages.add(context.getResources().getDrawable(R.drawable.wizard03));
            characterImages.add(context.getResources().getDrawable(R.drawable.wizard04));
            characterImages.add(context.getResources().getDrawable(R.drawable.wizard03));
            characterImages.add(context.getResources().getDrawable(R.drawable.wizard02));

            characterImages.add(context.getResources().getDrawable(R.drawable.wizard05));
            characterImages.add(context.getResources().getDrawable(R.drawable.wizard06));
            characterImages.add(context.getResources().getDrawable(R.drawable.wizard07));
            characterImages.add(context.getResources().getDrawable(R.drawable.wizard08));
            characterImages.add(context.getResources().getDrawable(R.drawable.wizard07));
            characterImages.add(context.getResources().getDrawable(R.drawable.wizard06));

            characterImages.add(context.getResources().getDrawable(R.drawable.wizard05));
            characterImages.add(context.getResources().getDrawable(R.drawable.wizard06));
            characterImages.add(context.getResources().getDrawable(R.drawable.wizard07));
            characterImages.add(context.getResources().getDrawable(R.drawable.wizard08));
            characterImages.add(context.getResources().getDrawable(R.drawable.wizard07));
            characterImages.add(context.getResources().getDrawable(R.drawable.wizard06));

        } else if ("firerain".equals(type)) {
            characterImages.add(context.getResources().getDrawable(R.drawable.firerain01));
            characterImages.add(context.getResources().getDrawable(R.drawable.firerain02));
            characterImages.add(context.getResources().getDrawable(R.drawable.firerain03));

        } else if ("gifts".equals(type)){
            characterImages.add(context.getResources().getDrawable(R.drawable.meetblood01));
            characterImages.add(context.getResources().getDrawable(R.drawable.meetblood03));
            characterImages.add(context.getResources().getDrawable(R.drawable.meetblood02));

            characterImages.add(context.getResources().getDrawable(R.drawable.fayblood01));
            characterImages.add(context.getResources().getDrawable(R.drawable.fayblood03));
            characterImages.add(context.getResources().getDrawable(R.drawable.fayblood02));
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
                if (c != null)  {
                    synchronized (surfaceHolder) {
                        boolean isVampCover = updateObjects();
                        refreshCanvas(c, isVampCover);
                    }
                }
            }
            catch (Exception e) {
                Log.e("pzdc", e.toString());
            }
            finally {
                if(c != null){
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try{
                Log.w("sleepTime = ", String.valueOf(sleepTime));

                if (sleepTime > 0)
                    sleep(sleepTime);
            } catch (Exception e) {
                Log.e("sleep", e.toString());
            }

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


        buttonsDraw(c);
        healthDraw(c);
        boolean isCanvasAccelerated = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // This code must not be executed on a device with API
            // level less than 11 (Android 2.x, 1.x)
            isCanvasAccelerated = c.isHardwareAccelerated();
        }
        Log.w("isCanvasAccelerated=",String.valueOf(isCanvasAccelerated));

    }


    private void buttonsDraw(Canvas c){
        c.drawBitmap(powerPicture, 0, this.height - powerPicture.getHeight(), null);
        c.drawBitmap(takePicture,this.width - takePicture.getWidth(), this.height - takePicture.getHeight(), null);
    }

    private  void healthDraw(Canvas c){
        float blockWith = 30;

        Paint paint = new Paint();

        //my health
        int vHealthCount = vampire.getHealth()/10;
        paint.setColor(Color.BLUE);
        for (int i =0; i < vHealthCount; i++){
            c.drawRect(i*blockWith, 0, (i+1)*blockWith-1, blockWith, paint);
        }

        //sun protection
        int vSunCount = vampire.getSunProtection()/10;
        paint.setColor(Color.YELLOW);
        for (int i =0; i < vSunCount; i++){
            c.drawRect(i*blockWith, blockWith, (i+1)*blockWith-1, 2*blockWith, paint);
        }

        //enemy health
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
            isHungry = DEFAULT_HUNGRY_SPEED;
        }
        if (madWorld.isNoon(vampire.DEFAULT_HEALTH/2)){
            vampire.makeBurning(-madWorld.getSpeed());
        }


        final Character enemy = enemies.get(indexOfEnemy);

        if (enemy.getCenterX() <= 0)
            refreshEnemy(enemy);

        if (!vampire.isUsingPower() && myRandom.nextInt(100) <= enemy.getChance() && vampire.getLeft() <= enemy.getCenterX())
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

            int enemyPower = enemy.getPower();
            int vampirePower = vampire.getPower();
            if (vampirePower < enemyPower) {
                vampire.makeWeaken(enemyPower);
                isVampCover = false;
            }
            if (isCollision == 1)
                enemy.makeWeaken(vampirePower);


            if (enemy.getHealth() <= 0 && vampire.getHealth() >= 0) {
                refreshEnemy(enemy);

                vampire.takeLife();
                if (enemy.isHavingSun())
                    vampire.takeSunProtection();

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
        indexOfEnemy = myRandom.nextInt(4);
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
            enemy.setLeft(vampire.getLastThirdX());
            return 1;
        } else if (enemy.isBulletHit(vampire)) {
            return 2;
        }
        return 0;
    }

    public void onPowerClick() {
        vampire.usePower();
    }

    private void onTakeClick() {
        if (vampire.isUsingPower())
            madWorld.takeGift(vampire);
    }

    public void onTouch(float x, float y) {
        if ((x <= powerPicture.getWidth())&&(x >= 0)&&(y >= height - powerPicture.getHeight())&&(y < height))
            onPowerClick();
        else if ((x <= this.width)&&(x >= this.width - takePicture.getWidth())&&(y >= this.height - powerPicture.getHeight())&&(y < height)) {
            onTakeClick();
        }
    }


}
