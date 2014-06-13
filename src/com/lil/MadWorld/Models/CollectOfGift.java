package com.lil.MadWorld.Models;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollectOfGift{
    private ArrayList<Gift> gifts;
    final Random myRandom = new Random();

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

    public void paused() {
        for(Gift gift : gifts){
            gift.paused();
        }
    }

    public void continued() {
        for(Gift gift : gifts){
            gift.continued();
        }
    }

    public int takeGift(Vampire vampire) {
        int res = -1;
        for(Gift gift : gifts){
            res = gift.takeGift(vampire);
            if (res >= 0) return res;
        }
        return res;
    }

    private class Gift extends SubjectOfTheWorld{
        public static final int MEET_BLOOD = 0;
        public static final int FAY_BLOOD = 1;
//        final Random myRandom = new Random();

        protected boolean isTaken = false;
        private int type = 0;
        protected int leftCharacter = 0;
        private int rightCharacter = 0;
        protected List<Drawable> images;

        public Gift(ArrayList<Drawable> images, int speed, int type) {
            super(images.get(0));
            this.images = new ArrayList<Drawable>(images);
            mSpeed = speed;
            mPoint.y = -mWidth; 
            mPoint.x = 0;
            this.type = type;
        }

        @Override
        protected void updatePoint() {
            mPoint.x += mSpeed;
            if (getRight() + mWidth < 0) {
                mImage =images.get(0);
                isTaken = false;
                randomX();
            }
            updateAnimate();
        }

        @Override
        protected void updateAnimate() {
            if (isTaken){
                mImage = images.get(1);
                if (leftCharacter > getCenterX()) {
                    mImage = images.get(2);
                    isTaken = false;
                }
            }
        }

        public void randomX() {
            mPoint.x = (myRandom.nextInt(maxRight/2)) + maxRight;//*mWidth%maxRight;
        }

        public int takeGift(Vampire vampire) {
            if (!isTaken){
                leftCharacter = vampire.getLeft();
                rightCharacter = vampire.getRight();
                if ((rightCharacter > getLeft())&&(leftCharacter < getCenterX())) {
                    isTaken = true;
                    if (type == 0){
                        vampire.takeHalfLife();
                    } else if (type == 1){
                        vampire.takeHalfSunProtection();
                    }
                    return type;
                }
            }
            return -1;
        }

        public boolean isAnimated() {
            return false;
        }
    }


    private class AnimatedGift extends Gift{
        private int indexOfFrame = 0;
        private int numOfFrame = 0;

        public AnimatedGift(ArrayList<Drawable> images, int speed, int type) {
            super(images, speed, type);
            numOfFrame = images.size() - 1;
        }

        @Override
        protected void updatePoint() {
            mPoint.x += mSpeed;
            if (getRight() + mWidth < 0) {
                mImage =images.get(0);
                isTaken = false;
                randomX();
            }
            updateAnimate();
        }

        @Override
        protected void updateAnimate() {
            indexOfFrame = (indexOfFrame + 1) % numOfFrame;
            mImage = images.get(indexOfFrame);

            if (isTaken){
                mImage = images.get(numOfFrame);
                resize();
                if (leftCharacter > getLeft()) {
                    mImage = images.get(0);
                    resize();
                    mPoint.x = -2*mWidth;
                  //  randomX();
                    isTaken = false;
                }
            }
        }


        public boolean isAnimated() {
            return true;
        }

    }


    private ArrayList<Drawable> cloneDrawables(ArrayList<Drawable> images){
        ArrayList<Drawable> newImages = new ArrayList<Drawable>();
        for (Drawable img:images)
            newImages.add(img.getConstantState().newDrawable());
        return newImages;
    }

    // images = [meetBloodImg, FayBloodImg, ...]
    public CollectOfGift(ArrayList<Drawable> images,int worldSpeed, boolean isAnimated) {
        gifts = new ArrayList<Gift>();
        if (isAnimated){
            for (int i = 0; i <2; i++) {
                gifts.add(new AnimatedGift(cloneDrawables(images), worldSpeed, 2));
            }
        }  else {
            for (int i = 0; i < images.size(); i += 3) {
                gifts.add(new Gift(new ArrayList<Drawable>(images.subList(i, i + 3)), worldSpeed, (int) (i / 3)));
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
            if (gift.isAnimated())
                gift.mPoint.y = bottom*3/5;
            else
                gift.mPoint.y = bottom/5;
        }               
    }

}
