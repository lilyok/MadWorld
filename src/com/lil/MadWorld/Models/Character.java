package com.lil.MadWorld.Models;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public abstract class Character extends SubjectOfTheWorld {
    public static final int INVISIBILITY = 0;
    public static final int HUMAN_POWER = 1;
    public static final int VAMPIRE_POWER = 2;
    public static final int WEREWOLF_POWER = 3;


    public static final int DEFAULT_SPEED = 10;
    public static final int DEFAULT_COUNT_OF_FACE = 2;
    public static final int DEFAULT_HEALTH = 80;

    List<Drawable> defaultImages;

    protected int numOfFrame = 0;
    protected int indexOfFrame = 0;
    protected int baseIndexOfFrame = 0;

    protected int health = 80;


    public Character(List<Drawable> defaultImages, int speedOfWorld, int speedFactor) {
        super(defaultImages.get(0));
        numOfFrame = defaultImages.size()/DEFAULT_COUNT_OF_FACE;
        this.defaultImages = new ArrayList<Drawable>(defaultImages);
        mSpeed = speedOfWorld + speedFactor*DEFAULT_SPEED;
    }

    @Override
    protected void updatePoint() {
        mPoint.x += mSpeed;

        if (mPoint.x < -mWidth){
            setRight(maxRight);
        }

        updateAnimate();
    }


    @Override
    protected void updateAnimate() {
        indexOfFrame = (indexOfFrame+1)%numOfFrame+baseIndexOfFrame;
        mImage = defaultImages.get(indexOfFrame);

    }

    public void usePower() {
        if (baseIndexOfFrame == 0)
            baseIndexOfFrame = numOfFrame;
        else if (baseIndexOfFrame == numOfFrame)
            baseIndexOfFrame = 0;
    }

    public void usePower(boolean isUsePower){
        if (isUsePower){
            baseIndexOfFrame = numOfFrame;
        }  else {
            baseIndexOfFrame = 0;
        }
    }

    public boolean isUsingPower() {
        if (baseIndexOfFrame == numOfFrame)
            return true;
        else
            return false;
    }

    public int makeWeaken(){
        health--;
        return health;
    }

    public int getHealth(){
        return health;
    }
    protected abstract int getPower();

    public void refresh() {
        setLeft(maxRight);
        health = DEFAULT_HEALTH;
        continued();
    }
}
