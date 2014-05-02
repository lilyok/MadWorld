package com.lil.MadWorld.Models;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.List;

public class Hunter extends Character {


    private final int speedBulletFactor = 3;
    private SubjectOfTheWorld bullet;


    public Hunter(List<Drawable> defaultImages, Drawable bulletImage, final int speedOfWorld, final int speedFactor) {
        super(defaultImages, speedOfWorld, speedFactor);

        chance = 10;

        bullet = new SubjectOfTheWorld(bulletImage) {
            private int mSpeed = speedOfWorld + speedBulletFactor * speedFactor * DEFAULT_SPEED;
            private int pointOfDestruction = 0;


            @Override
            protected void updatePoint() {

                if ((mPoint.x <= pointOfDestruction) || (mPoint.x >= Hunter.this.maxRight)) {
                    if (Hunter.this.isUsingPower())
                        setRight(Hunter.this.getLeft());
                    else
                        mPoint.x = Hunter.this.maxRight;
                } else {
                    mPoint.x += mSpeed;

                }
            }

            @Override
            protected void updateAnimate() {

            }
        };
    }

    public int getBulletCenterX() {
        return bullet.getCenterX();
    }

    public int getBulletLeftX() {
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
        bullet.setCenterY(value);
    }


    @Override
    public int getPower() {
        if (isUsingPower())
            return BULLET_POWER;
        else
            return HUMAN_POWER;
    }

}
