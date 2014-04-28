package com.lil.MadWorld.Models;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Werewolf  extends SubjectOfTheWorld {
    public static final int DEFAULT_SPEED = 5;
    List<Drawable> defaultImages;
    int numOfFrame = 0;
    int indexOfFrame = 0;

    public Werewolf(Drawable image) {
        super(image);

        mSpeed = DEFAULT_SPEED;
    }

    public Werewolf(List<Drawable> defaultImages, int speedOfWorld, int speedFactor) {
        super(defaultImages.get(0));
        numOfFrame = defaultImages.size()/2;
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

        indexOfFrame = (indexOfFrame+1)%numOfFrame;
        mImage = defaultImages.get(indexOfFrame);
    }


}