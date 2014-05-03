package com.lil.MadWorld.Models;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.List;

public class FireArmedCharacter extends Character {


    private final int speedBulletFactor = 3;
    private Bullet bullet;


    private class Bullet extends Character {
        private int mSpeed = speedOfWorld + speedBulletFactor * speedFactor * DEFAULT_SPEED;
        private int pointOfDestruction = 0;
        private boolean isHit = false;


        public Bullet(final List<Drawable> bulletImages, final int speedOfWorld, final int speedFactor){
            super(bulletImages, speedOfWorld, speedFactor);
        }

        @Override
        protected void updatePoint() {

            if ((mPoint.x <= pointOfDestruction) || (mPoint.x >= FireArmedCharacter.this.maxRight)) {
                if (FireArmedCharacter.this.isUsingPower())
                    setRight(FireArmedCharacter.this.getLeft());
                else
                    mPoint.x = FireArmedCharacter.this.maxRight;
            } else {
                mPoint.x += mSpeed;

            }
            updateAnimate();
        }

        @Override
        protected void updateAnimate() {
            if (isHit) {
                if (mPoint.x > FireArmedCharacter.this.maxRight / 2) {
                    mImage = defaultImages.get(0);
                } else if (mPoint.x >= FireArmedCharacter.this.maxRight / 2 - mWidth / 4) {
                    mImage = defaultImages.get(1);
                } else {
                    mImage = defaultImages.get(2);
                }
            }
        }

        @Override
        public int getPower() {
            return BULLET_POWER;
        }

        @Override
        public int getBulletLeft() {
            return getLeft();
        }

        @Override
        public int getBulletCenterX() {
            return getCenterX();
        }

        public void setHit() {
            isHit = true;
        }
    }


    public FireArmedCharacter(List<Drawable> defaultImages, List<Drawable> bulletImages, int speedOfWorld, int speedFactor) {
        super(defaultImages, speedOfWorld, speedFactor);

        chance = 10;

        bullet = new Bullet(bulletImages, speedOfWorld, speedFactor);
    }

    public int getBulletCenterX() {
        return bullet.getCenterX();
    }

    public int getBulletLeft() {
        return bullet.getLeft();
    }

    public void update() {
        if (isMoving) {
            if (isUsingPower()) {
                mSpeed = 0;
            } else {
                mSpeed = speedOfWorld + speedFactor * DEFAULT_SPEED;
            }
            updatePoint();
        } else
            updateAnimate();
        mImage.setBounds(mPoint.x, mPoint.y, mPoint.x + mWidth, mPoint.y + mHeight);


        bullet.update();
    }

    public void draw(Canvas canvas) {
        mImage.draw(canvas);
        bullet.draw(canvas);
    }


    public void setCenterY(int value) {
        super.setCenterY(value);
        bullet.setTop(value);
    }


    @Override
    public int getPower() {
        if (isUsingPower())
            return BULLET_POWER;
        else
            return HUMAN_POWER;
    }

    public void setBulletHit() {
        bullet.setHit();
    }
}
