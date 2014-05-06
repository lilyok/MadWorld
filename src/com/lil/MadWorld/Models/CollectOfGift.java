package com.lil.MadWorld.Models;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollectOfGift{
    private ArrayList<Gift> gifts;

    public void draw(Canvas c) {
        for(Gift gift : gifts){
            gift.draw(c);
        }
    }

    public void update() {
        for(Gift gift : gifts){
            gift.update();
        }
    }

    private class Gift extends SubjectOfTheWorld{
        public static final int MEET_BLOOD = 0;
        public static final int FAY_BLOOD = 1;
        final Random myRandom = new Random();

        private int type = 0;
        public Gift(Drawable image, int speed, int type) {
            super(image);
            mSpeed = speed;
            mPoint.y = -mWidth; 
            mPoint.x = 0;
            this.type = type;
        }

        @Override
        protected void updatePoint() {
            mPoint.x += mSpeed;
            if (mPoint.x + mWidth + maxRight <= 0) {
                randomX();
            }
        }

        @Override
        protected void updateAnimate() {


        }

        public void randomX() {
            mPoint.x = (myRandom.nextInt(maxRight*2-mWidth));//*mWidth%maxRight;
        }
    } 
    

    // images = [meetBloodImg, FayBloodImg, ...]
    public CollectOfGift(List<Drawable> images, List<Integer> numOfGift, int worldSpeed) {
        gifts = new ArrayList<Gift>();
        for (int i = 0; i < numOfGift.size(); i++){
            for (int j = 0; j < numOfGift.get(i); j++){
                gifts.add(new Gift(images.get(i), worldSpeed, j));

            }
        }
    }

    public void setMaxRight(int width) {
        for (Gift gift : gifts) {
            gift.setMaxRight(width);
            gift.randomX();
        }
    }

    public void setMaxBottom(int bottom) {
        for (Gift gift : gifts) {
            gift.setMaxBottom(bottom);
            gift.mPoint.y = bottom/3;
        }               
    }

}
