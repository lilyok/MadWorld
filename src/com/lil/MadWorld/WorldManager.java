package com.lil.MadWorld;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.SurfaceHolder;
import com.lil.MadWorld.Models.Character;
import com.lil.MadWorld.Models.*;

import java.util.ArrayList;
import java.util.Random;


public class WorldManager extends Thread {
    private final Context context;
    private SoundPool sounds;
    private int sAngryEagle;
    private int sAngryWolf;
    private int sAngryBullet;
    private int sAngryLaser;
    private int sAngrySounder;
    private int sBite;
    private int sBottle;
    private int sFlower;
    private int sFail;
//    private int sWing;

//    private final Context context;
    private final SurfaceHolder surfaceHolder;

    private boolean running = false;
    static final long FPS = 25;

    private MadWorld madWorld;
    private Vampire vampire;
    private CloseHandedCharacter werewolf;
    private FireArmedCharacter hunter;
    private FireArmedCharacter fay;
    private FireArmedCharacter wizard;
    private DivingKite angryEagle;



    private ArrayList<Character> enemies;
    private int indexOfEnemy = 3;

    private Bitmap powerPicture;
    private Bitmap takePicture;

    private int height = 1000000000;
    private int width = 1000000000;
    private boolean starting = true;

    final Random myRandom = new Random();

    public static final int DEFAULT_HUNGRY_SPEED = 20;
    private long isHungry = DEFAULT_HUNGRY_SPEED;

    private GameAlert bloodedAlert = null;
    private GameAlert firedAlert = null;
    private GameTaskAlert taskAlert = null;
    private GameStatusAlert statusAlert = null;

    private TaskManager taskManager;
    private int batSpeedCoefficient = 1;

//    private int taskIndex;
//    private List<String> taskText = new ArrayList<String>();
//    int taskIndex = 0;

    public boolean isUsingPower() {
        return vampire.isUsingPower();
    }

    public void setUsingPower(boolean usingPower) {
        vampire.usePower(usingPower);
    }

    public int getIndexOfEnemy() {
        return indexOfEnemy;
    }

    public void setIndexOfEnemy(int ind) {
        this.indexOfEnemy = ind;
    }

    public long getIsHungry() {
        return isHungry;
    }

    public void setIsHungry(long isHungry) {
        this.isHungry = isHungry;
    }

    public int getEnemyLeft() {
        return enemies.get(indexOfEnemy).getLeft();
    }

    public void setEnemyLeft(int enemyLeft) {
        enemies.get(indexOfEnemy).setLeft(enemyLeft);
    }

    public int getMadWorldLeft() {
        return madWorld.getLeft();
    }

    public void setMadWorldLeft(int madWorldLeft) {
        madWorld.setLeft(madWorldLeft);
    }

    public int getIndexOfFirstImage() {
        return madWorld.getIndexOfFirstImage();
    }

    public void setIndexOfFirstImage(int indexOfFirstImage) {
        madWorld.setIndexOfFirstImage(indexOfFirstImage);
    }

    public int getHealth() {
        return vampire.getHealth();
    }

    public void setHealth(int health) {
        vampire.setHealth(health);
    }

    public int getEnemyHealth() {
        return enemies.get(indexOfEnemy).getHealth();
    }

    public void setEnemyHealth(int enemyHealth) {
        enemies.get(indexOfEnemy).setHealth(enemyHealth);
    }

    public int getSunProtection() {
        return vampire.getSunProtection();
    }

    public void setSunProtection(int sunProtection) {
        vampire.setSunProtection(sunProtection);
    }

    public void setMImageByFirst() {
        madWorld.setMImageByFirst();
    }

    public boolean getIsMoving(){
        return vampire.isMoving();
    }

    public void setIsMoving(boolean f){
        vampire.setMoving(f);
    }

    public boolean getWorldIsMoving(){
        return madWorld.isMoving();
    }

    public void setWorldIsMoving(boolean f){
        madWorld.setMoving(f);
    }

    public boolean getEnemyIsMoving(){
        return enemies.get(indexOfEnemy).isMoving();
    }

    public void setEnemyIsMoving(boolean f){
        enemies.get(indexOfEnemy).setMoving(f);
    }

    public void cleanBaseIndexOfFrame() {
        vampire.cleanBaseIndexOfFrame();
    }

    public void setGiftsStatus() {
        madWorld.setGiftsStatus();
    }

    public WorldManager(SurfaceHolder surfaceHolder, Context context) {
//        this.context = context;
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        sAngryEagle = sounds.load(context, R.raw.kite, 1);
        sAngryWolf = sounds.load(context, R.raw.rrr, 1);
        sAngryBullet = sounds.load(context, R.raw.gun, 1);
        sAngryLaser = sounds.load(context, R.raw.laser, 1);
        sAngrySounder = sounds.load(context, R.raw.grom, 1);
        sBite = sounds.load(context, R.raw.bunny, 1);
        sBottle = sounds.load(context, R.raw.clochette, 1);
        sFlower = sounds.load(context, R.raw.ring, 1);
        sFail =  sounds.load(context, R.raw.no, 1);
//        sWing = sounds.load(context, R.raw.wing,1);

        taskManager = new TaskManager(context);

        angryEagle = new DivingKite(loadFrames(context, "kite"), Vampire.DEFAULT_SPEED*2, Character.BULLET_POWER);
        vampire = new Vampire(loadFrames(context, "vampire"), loadFrames(context, "firedVampire"), loadFrames(context, "bloodedVampire"), 0, 0);

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


        madWorld = new MadWorld(loadFrames(context, "world"), loadFrames(context, "gifts"),
                loadFrames(context, "flowers"),
                -Vampire.DEFAULT_SPEED);


        powerPicture = BitmapFactory.decodeResource(context.getResources(), R.drawable.power);
        takePicture = BitmapFactory.decodeResource(context.getResources(), R.drawable.take);
    }


    private ArrayList<Drawable> loadFrames(Context context, String type) {
        ArrayList<Drawable> characterImages = new ArrayList<Drawable>();
        if ("world".equals(type)) {
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
        } else if ("bloodedVampire".equals(type)) {
            characterImages.add(context.getResources().getDrawable(R.drawable.bloodedvamp01));
            characterImages.add(context.getResources().getDrawable(R.drawable.bloodedvamp02));
            characterImages.add(context.getResources().getDrawable(R.drawable.bloodedvamp03));
            characterImages.add(context.getResources().getDrawable(R.drawable.bloodedvamp04));
        } else if ("firedVampire".equals(type)) {
            characterImages.add(context.getResources().getDrawable(R.drawable.firedvamp01));
            characterImages.add(context.getResources().getDrawable(R.drawable.firedvamp02));
            characterImages.add(context.getResources().getDrawable(R.drawable.firedvamp03));
            characterImages.add(context.getResources().getDrawable(R.drawable.firedvamp04));
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

        } else if ("gifts".equals(type)) {
            characterImages.add(context.getResources().getDrawable(R.drawable.meetblood01));
            characterImages.add(context.getResources().getDrawable(R.drawable.meetblood03));
            characterImages.add(context.getResources().getDrawable(R.drawable.meetblood02));

            characterImages.add(context.getResources().getDrawable(R.drawable.fayblood01));
            characterImages.add(context.getResources().getDrawable(R.drawable.fayblood03));
            characterImages.add(context.getResources().getDrawable(R.drawable.fayblood02));
        } else if ("kite".equals(type)) {
            characterImages.add(context.getResources().getDrawable(R.drawable.kite01));
            characterImages.add(context.getResources().getDrawable(R.drawable.kite02));
        } else if ("flowers".equals(type)){
            characterImages.add(context.getResources().getDrawable(R.drawable.flower01));
            characterImages.add(context.getResources().getDrawable(R.drawable.flower02));
            characterImages.add(context.getResources().getDrawable(R.drawable.flower03));
        }

        return characterImages;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    public void setStarted(boolean f) {
        starting = f;
        if (taskAlert != null)
            taskAlert.setVisible();

//        Log.w("isStarted=", String.valueOf(starting));
    }

    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;

        while (running) {
//            Log.w(" running isStarted=", String.valueOf(starting));

            Canvas c = null;
            startTime = System.currentTimeMillis();

            try {
                c = surfaceHolder.lockCanvas();

                if (c != null) {
                    synchronized (surfaceHolder) {
                        try {
                            boolean isVampCover = updateObjects();
                            refreshCanvas(c, isVampCover);
                        } finally {
                            surfaceHolder.unlockCanvasAndPost(c);
                        }
                    }
                }


            } catch (Exception e) {
                Log.e("pzdc", e.toString());
            }

            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
//                Log.w("sleepTime = ", String.valueOf(sleepTime));

                if (sleepTime > 0)
                    sleep(sleepTime);
            } catch (Exception e) {
//                Log.e("sleep", e.toString());
            }

        }
    }

    private void refreshCanvas(Canvas c, boolean isVampCover) {
        madWorld.draw(c);
        if ((!taskAlert.isVisible())&&(vampire.getHealth() > 0)&&(vampire.getSunProtection() > 0))
            statusAlert.draw(c);
        if (isVampCover) {
            enemies.get(indexOfEnemy).draw(c);
            vampire.draw(c);
        } else {
            vampire.draw(c);
            enemies.get(indexOfEnemy).draw(c);
        }

        angryEagle.draw(c);
        madWorld.drawCovers(c);

        buttonsDraw(c);
        healthDraw(c);


        if (vampire.getHealth() <= 0) {
            bloodedAlert.draw(c);//drawAlert(c, BLOODED_DEATH_TEXT);
        }
        else if (vampire.getSunProtection() <= 0){
            firedAlert.draw(c);//drawAlert(c, FIRED_DEATH_TEXT);
        }
        else{
            if (taskAlert.isVisible()){
                starting = false;
                //pausedWorld(enemies.get(indexOfEnemy));
                taskAlert.draw(c);
            }
        }


    }


    private void buttonsDraw(Canvas c) {
        c.drawBitmap(powerPicture, 0, this.height - powerPicture.getHeight(), null);
        c.drawBitmap(takePicture, this.width - takePicture.getWidth(), this.height - takePicture.getHeight(), null);
    }

    private void healthDraw(Canvas c) {
        float blockWith = 30;

        Paint paint = new Paint();

        //my health
        int vHealthCount = vampire.getHealth() / 10;
        paint.setColor(Color.BLUE);
        for (int i = 0; i < vHealthCount; i++) {
            c.drawRect(i * blockWith, 0, (i + 1) * blockWith - 1, blockWith, paint);
        }

        //sun protection
        int vSunCount = vampire.getSunProtection() / 10;
        paint.setColor(Color.YELLOW);
        for (int i = 0; i < vSunCount; i++) {
            c.drawRect(i * blockWith, blockWith, (i + 1) * blockWith - 1, 2 * blockWith, paint);
        }

        //enemy health
        int wHealthCount = enemies.get(indexOfEnemy).getHealth() / 10;
        paint.setColor(Color.RED);
        for (int i = 0; i < wHealthCount; i++) {
            c.drawRect(width - i * blockWith, 0, width - (i + 1) * blockWith + 1, 30, paint);
        }


    }


    private boolean updateObjects() {
        boolean isVampCover = true;
//        Log.w(" update isStarted=", String.valueOf(this.isStarted));

        if (starting) {

            final Character enemy = enemies.get(indexOfEnemy);

            if ((vampire.getHealth() <= 0)||vampire.getSunProtection() <= 0) {

                pausedWorld(enemy);

            } else {
                final int destination = getDestination();
                final int vampireWidth = vampire.getWidth();
                if ((vampire.isMoving())&&(!enemy.isMoving()))
                    enemy.continued();
                if((Math.abs(destination) > vampireWidth) && (vampire.isUsingPower())) {//&&!enemy.isHaveBullet())
                    angryEagle.setHidden(false);
                    if (angryEagle.getTop() < 0)
                        sounds.play(sAngryEagle, 0.2f, 0.2f, 0, 0, 1.5f);
                }
                else {
                    angryEagle.setHidden(true);
                }

                if (taskManager.calculate(madWorld.getIndexOfFirstImage()))
                    fillTaskAlert();
                else
                    fillStatusAlert();

                isHungry--;
                if (isHungry <= 0) {
                    vampire.makeWeaken();
                    isHungry = DEFAULT_HUNGRY_SPEED;
                }
                if (madWorld.isNoon(vampire.DEFAULT_HEALTH / 2)) {
                    vampire.makeBurning(-madWorld.getSpeed());
                    if (vampire.getSunProtection() <= 0)
                        sounds.play(sFail, 1.0f, 1.0f, 0, 0, 1.0f);

                }

                if (enemy.getCenterX() <= 0)  {
                    taskManager.sparingEnemy(indexOfEnemy);
                    refreshEnemy(enemy);
                }

                if (!vampire.isUsingPower() && myRandom.nextInt(100) <= enemy.getChance() && vampire.getLeft() <= enemy.getCenterX()) {
                    if (enemy.isShootNew())
                        sPlay();
                    enemy.usePower(true);
//                    int bulletRight = enemy.getBulletRight();
//                    int tmpX = bulletRight - enemy.getLeft();

                }

                if (vampire.isUsingPower()) {
//                    sounds.play(sWing, 0.1f, 0.1f, 0, 0, 0.5f);

                    enemy.usePower(false);
                    if(angryEagle.isAttack()){
                        vampire.makeWeaken(angryEagle.getPower());
                    }
                } else {
                    if (enemy.isUsingPower() && enemy.isShootNew())
                        sPlay();
                }


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
                        if (vampire.getHealth() <= 0)
                            sounds.play(sFail, 1.0f, 1.0f, 0, 0, 1.0f);
                    }
                    if (isCollision == 1) {
                        enemy.makeWeaken(vampirePower);
                        if (vampire.isShootNew())
                            sounds.play(sBite, 1.0f, 1.0f, 0, 0, 0.5f);
                    }


                    if (enemy.getHealth() <= 0 && vampire.getHealth() >= 0) {
                        refreshEnemy(enemy);

                        vampire.takeLife();
                        if (enemy.isHavingSun())
                            vampire.takeSunProtection();

                        vampire.continued();

                        madWorld.continued();
                    } /*else if (vampire.getHealth() <= 0) {
                    vampire.setRight(0);
                }  */
                }
            }

            madWorld.update();
            enemy.update();
            vampire.update();
            angryEagle.update();
        }

        return isVampCover;
    }

    private void pausedWorld(Character enemy) {
        madWorld.paused();
        vampire.usePower(false);
        angryEagle.setHidden(true);
        if (angryEagle.getBottom() < 0)
            angryEagle.paused();
        if (enemy.getCenterX() <= 0) {
            enemy.setRight(0);
            enemy.paused();
        }
        else {
            enemy.usePower(false);
            enemy.continued();
        }
    }


    private void refreshEnemy(Character enemy) {
      //  enemy.refresh();
        indexOfEnemy = myRandom.nextInt(6);
        if (indexOfEnemy >= enemies.size())
            indexOfEnemy = 2;
        enemies.get(indexOfEnemy).refresh();
    }

    public void initPositions(int height, int width) {
        this.height = height;
        this.width = width;

        initVampirePosition();

        angryEagle.setMaxRight(width);
        angryEagle.setMaxBottom(vampire.getTop() + angryEagle.getHeight() / 4);
        for (Character enemy : enemies) {
            //       enemy.setRight(width);
            enemy.setCenterY(height / 2);
            enemy.setMaxRight(width);
            enemy.setMaxBottom(height);
        }

        madWorld.setMHeight(height);
        madWorld.setMWidth(width);
        madWorld.setVampireYs(vampire.getTop(), vampire.getBottom());

        if (bloodedAlert == null)
            bloodedAlert = new GameAlert(context.getString(R.string.diedVampireString),
                    context.getString(R.string.dietString)
                    , Color.argb(255, 238 ,130, 238), Color.argb(255,120, 255, 255), width);

        if (firedAlert == null)
            firedAlert = new GameAlert(context.getString(R.string.firedVampireString)
                    , context.getString(R.string.meetString),
                    Color.argb(255, 255, 99, 71), Color.argb(255, 255, 255, 120), width);

        if ((taskAlert == null) || (taskAlert.getWidth() != width))
            fillTaskAlert();

        if (statusAlert == null)
            fillStatusAlert();
   //     taskAlert.setVisible();
    }

    public void initVampirePosition() {
        vampire.setCenterX(width / 2);
        vampire.setCenterY(height / 2);
        vampire.setMaxRight(width);
        vampire.setMaxBottom(height);
    }

    private int getDestination(){
        Character enemy = enemies.get(indexOfEnemy);
        int destination  = (enemy.getLeft() - vampire.getRight());
        int bulletDestination = enemy.getBulletLeft() - vampire.getRight();

        if ((destination > bulletDestination)&&(enemy.getBulletRight() > vampire.getLeft() ))
            destination = bulletDestination;

  //          return bulletDestination;

        return destination;
    }
    private int isCollision() {
        final Character enemy = enemies.get(indexOfEnemy);


        if ((vampire.getRight() >= enemy.getLeft()) &&
                (vampire.getLeft() <= enemy.getCenterX()) && !vampire.isUsingPower()) {


            enemy.setLeft(vampire.getLastThirdX());
            taskManager.fighting(indexOfEnemy);
            return 1;
        } else if (enemy.isBulletHit(vampire)) {
            return 2;
        }
        return 0;
    }

    private void sPlay() {
        switch (indexOfEnemy) {
            case 0:
                sounds.play(sAngryWolf, 1.0f, 1.0f, 0, 0, 1.0f);
                break;
            case 1:
                sounds.play(sAngryBullet, 1.0f, 1.0f, 0, 0, 1.0f);
                break;
            case 2:
                sounds.play(sAngryLaser, 1.0f, 1.0f, 0, 0, 1.0f);
                break;
            case 3:
                sounds.play(sAngrySounder, 1.0f, 1.0f, 0, 0, 1.0f);
                break;
        }
    }

    public void onPowerClick() {
//        taskManager.setFlying(vampire.usePower());
        if (vampire.usePower()){
            madWorld.setSpeedCoefficient(batSpeedCoefficient);
            for(Character enemy :enemies)
                enemy.setSpeedCoefficient(batSpeedCoefficient);

        } else {
            madWorld.unsetSpeedCoefficient(batSpeedCoefficient);
            for(Character enemy :enemies)
                enemy.setSpeedCoefficient(batSpeedCoefficient);
        }


    }

    private void onTakeClick() {
        if (vampire.isUsingPower()) {
            int res = madWorld.takeGift(vampire);
            if (res >= 0)
                sounds.play(sBottle, 1.0f, 1.0f, 0, 0, 1.0f);
            if (res == 0){
                taskManager.incCountOfTrueBlood();
            } else if (res == 1){
                taskManager.incCountOfFayBlood();
            }
        } else {
            int res = madWorld.takeFlower(vampire);
            if (res == 2) {
                sounds.play(sFlower, 1.0f, 1.0f, 0, 0, 1.0f);
                taskManager.addFlower();
            }
        }

    }

    public void onTouch(float x, float y) {
        if ((x <= powerPicture.getWidth()) && (x >= 0) && (y >= height - powerPicture.getHeight()) && (y < height))
            onPowerClick();
        else if ((x <= this.width) && (x >= this.width - takePicture.getWidth()) && (y >= this.height - powerPicture.getHeight()) && (y < height)) {
            onTakeClick();
        } else {
            taskAlert.tryClose(y);
            if (taskManager.tryNextTask()){
                fillTaskAlert();
//                taskManager.rewarding(vampire);
                if (taskManager.rewarding(vampire) == 4)
                    batSpeedCoefficient = 2;

            } else {
                starting = true;
    //            continuedWorld(enemies.get(indexOfEnemy));
            }
        }
    }

    private void fillTaskAlert() {
            taskAlert = new GameTaskAlert(context.getString(R.string.closeString), context.getString(R.string.taskString), taskManager.getTaskText(),
                    Color.argb(255, 32, 178, 170), Color.WHITE, width);

    }

    private void fillStatusAlert() {
        if (taskManager.isHaveNewInformation())
            statusAlert = new GameStatusAlert(context.getString(R.string.status), taskManager.getFields(), taskManager.getValues(),
                Color.argb(255, 32, 178, 170), Color.WHITE, width);

    }
    public int getBaseOfFire() {
        return vampire.getBaseOfFire();
    }

    public int getBaseOfBlood() {
        return vampire.getBaseOfBlood();
    }

    public void setIsBlooded(boolean isBlooded) {
        vampire.setIsBlooded(isBlooded);
    }

    public void setIsFired(boolean isFired) {
        vampire.setIsFired(isFired);
    }

    public void setBaseOfFire(int baseOfFire) {
        vampire.setBaseOfFire(baseOfFire);
    }

    public void setBaseOfBlood(int baseOfBlood) {
        vampire.setBaseOfBlood(baseOfBlood);
    }



    public void savePreferences(SharedPreferences.Editor ed) {
        taskManager.savePreferences(ed);
    }

    public void restorePreferences(SharedPreferences sPref, boolean isRestart) {
        taskManager.restorePreferences(sPref);
        if (isRestart) {
            refreshEnemy(enemies.get(indexOfEnemy));
          //  enemies.get(indexOfEnemy).continued();
        }
        fillTaskAlert();
    }

    public int getTaskIndex() {
        return taskManager.getTaskIndex();
    }
}




