package com.lil.MadWorld.Models;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public abstract class Character extends SubjectOfTheWorld {
    public static final int INVISIBILITY = 0;
    public static final int HUMAN_POWER = 1;
    public static final int VAMPIRE_POWER = 2;
    public static final int WEREWOLF_POWER = 3;
    public static final int BULLET_POWER = 4;

    public static final int DEFAULT_SPEED = 3;
    public static final int DEFAULT_COUNT_OF_FACE = 3;
    public static final int DEFAULT_INDEX_NO_POWER_ATTACK_FACE = 2;

    public static final int DEFAULT_HEALTH = 80;

    protected static int speedOfWorld = 0;
    protected static int speedFactor = 0;

    protected List<Drawable> defaultImages;

    protected int numOfFrame = 0;
    protected int indexOfFrame = 0;
    protected int baseIndexOfFrame = 0;

    protected int maxHealth = DEFAULT_HEALTH;
    protected int health = maxHealth;

    protected int chance = 3;

    protected int magicPower = HUMAN_POWER;


    protected boolean isHavingSun = false;


    public Character(List<Drawable> defaultImages, int speedOfWorld, int speedFactor, int magicPower) {
        super(defaultImages.get(0));
        numOfFrame = defaultImages.size() / DEFAULT_COUNT_OF_FACE;
        this.defaultImages = new ArrayList<Drawable>(defaultImages);

        this.speedOfWorld = speedOfWorld;
        this.speedFactor = speedFactor;
        mSpeed = speedOfWorld + speedFactor * DEFAULT_SPEED;

        this.magicPower = magicPower;
    }

    @Override
    protected void updatePoint() {
        mPoint.x += mSpeed;

        if (mPoint.x < -mWidth) {
            setRight(maxRight);
            Log.w("mPoint.x < -mWidth = ", String.valueOf(mPoint.x));

        }

        updateAnimate();
    }


    @Override
    protected void updateAnimate() {
        if (!isMoving && baseIndexOfFrame == 0)
            baseIndexOfFrame = numOfFrame * DEFAULT_INDEX_NO_POWER_ATTACK_FACE;
        else if (baseIndexOfFrame == numOfFrame * DEFAULT_INDEX_NO_POWER_ATTACK_FACE)
            baseIndexOfFrame = 0;
        indexOfFrame = (indexOfFrame + 1) % numOfFrame + baseIndexOfFrame;
        mImage = defaultImages.get(indexOfFrame);

    }

    public void usePower() {
        if (baseIndexOfFrame != numOfFrame)
            baseIndexOfFrame = numOfFrame;
        else if (baseIndexOfFrame == numOfFrame)
            baseIndexOfFrame = 0;
    }

    public void usePower(boolean isUsePower) {
        if (isUsePower) {
            baseIndexOfFrame = numOfFrame;
        } else {
            baseIndexOfFrame = 0;
        }
    }

    public boolean isUsingPower() {
        if (baseIndexOfFrame == numOfFrame)
            return true;
        else
            return false;
    }

    public int makeWeaken() {
        health--;
        if (health <= 0) health = 0;
        return health;
    }

    public int makeWeaken(int enemyPower) {
        health -= enemyPower;
        if (health <= 0) health = 0;
        return health;
    }

    public int getHealth() {
        return health;
    }


    public void refresh() {
    //    Log.w("refresh", String.valueOf(mPoint.x)) ;

        setLeft(maxRight);
        health = DEFAULT_HEALTH;
        continued();
    }

    public  int getChance(){
        return chance;
    }

    public int getPower() {
        if (isUsingPower())
            return magicPower;
        else
            return HUMAN_POWER;
    }
    public boolean isBulletHit(Character character) {
        return false;
    }


    public boolean isHavingSun() {
        return isHavingSun;
    }

    public void setHavingSun(boolean isHavingSun) {
        this.isHavingSun = isHavingSun;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    protected void cleanBaseIndexOfFrame(){
        baseIndexOfFrame = 0;
    }
}
