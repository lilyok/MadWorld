package com.lil.MadWorld.Models;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Vampire extends SubjectOfTheWorld {
    public static final int DEFAULT_SPEED = 10;
    public static final int DEFAULT_COUNT_OF_FACE = 2;

    List<Drawable> defaultImages;

    int numOfFrame = 0;
    int indexOfFrame = 0;
    int baseIndexOfFrame = 0;


    int numOfPower = 80;
    int maxPower = 80;

    public Vampire(Drawable defaultImage) {
        super(defaultImage);

        mSpeed = DEFAULT_SPEED;
    }

    public Vampire(List<Drawable> defaultImages, int speedOfWorld, int speedFactor) {
        super(defaultImages.get(0));
        numOfFrame = defaultImages.size()/DEFAULT_COUNT_OF_FACE;
        this.defaultImages = new ArrayList<Drawable>(defaultImages);
        mSpeed = speedOfWorld + speedFactor*DEFAULT_SPEED;
    }


    @Override
    protected void updatePoint() {
        mPoint.x += mSpeed;

        if (mPoint.x < -mWidth){
//            mSpeed = 0;
            setRight(maxRight);
        }


        indexOfFrame = (indexOfFrame+1)%numOfFrame+baseIndexOfFrame;
        mImage = defaultImages.get(indexOfFrame);

        if (baseIndexOfFrame == numOfFrame){
            numOfPower--;
            if (numOfPower <= maxPower/2){
                baseIndexOfFrame = 0;
            }
        }
    }


    public void usePower() {
        if ((baseIndexOfFrame == 0) && (numOfPower > maxPower/2))
            baseIndexOfFrame = numOfFrame;
        else if (baseIndexOfFrame == numOfFrame)
            baseIndexOfFrame = 0;
    }

    public int getNumOfPower() {
        return numOfPower;
    }

    public int getMaxPower() {
        return maxPower;
    }


    public boolean isUsingPower() {
        if (baseIndexOfFrame == numOfFrame) return true;
        else return false;
    }
}