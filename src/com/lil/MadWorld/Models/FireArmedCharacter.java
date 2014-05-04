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
        private boolean isFalling = false;

        public Bullet(final List<Drawable> bulletImages, final int speedOfWorld, final int speedFactor, boolean isFalling){
            super(bulletImages, speedOfWorld, speedFactor, BULLET_POWER);
            this.isFalling = isFalling;
        }

        @Override
        protected void updatePoint() {
            if (isFalling) {
                if ((mPoint.x <= pointOfDestruction) || (mPoint.x >= FireArmedCharacter.this.maxRight))
                    setCenterX(FireArmedCharacter.this.maxRight / 2);


                if (mPoint.y <= FireArmedCharacter.this.getBottom()){
                    mPoint.y += Math.abs(mSpeed);
                } else {
                    setBottom(0);
                }

            } else {
                if ((mPoint.x <= pointOfDestruction) || (mPoint.x >= FireArmedCharacter.this.maxRight)) {
                    if (FireArmedCharacter.this.isUsingPower())
                        setRight(FireArmedCharacter.this.getLeft());
                    else
                        mPoint.x = FireArmedCharacter.this.maxRight;


                } else {
                    mPoint.x += mSpeed;

                }
            }

            updateAnimate();
        }

        @Override
        protected void updateAnimate() {
            if (isFalling){
                updateFallingBullet();
            }   else {
                updateFlyingBulletAnimate();
            }
        }

        private void updateFallingBullet() {
            if (getBottom() < FireArmedCharacter.this.maxBottom / 2) {
                mImage = defaultImages.get(0);
                isHit = false;
            }
            if (isHit) {
                if (mPoint.y >= FireArmedCharacter.this.maxRight / 2 + mHeight / 2) {
                    mImage = defaultImages.get(1);
                } else {
                    mImage = defaultImages.get(2);
                }
            }
        }

        private void updateFlyingBulletAnimate() {
            if (mPoint.x > FireArmedCharacter.this.maxRight / 2) {
                mImage = defaultImages.get(0);
                isHit = false;
            }
            if (isHit) {
                if (mPoint.x >= FireArmedCharacter.this.maxRight / 2 - mWidth / 2) {
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


        public void setHit() {
            isHit = true;
        }
    }


    public FireArmedCharacter(List<Drawable> defaultImages, List<Drawable> bulletImages, int speedOfWorld, int speedFactor, boolean isFalling, int magicPower) {
        super(defaultImages, speedOfWorld, speedFactor, magicPower);

        chance = 5;

        bullet = new Bullet(bulletImages, speedOfWorld, speedFactor, isFalling);
    }




    public boolean isBulletHit(Character character){
        if (bullet.isFalling){
            if ((character.getFirstThirdY() <= bullet.getBottom()) &&
                    (character.getCenterY() >= bullet.getCenterY())&&(!character.isUsingPower())) {
                setBulletHit();
                return true;            }

        } else {
            if ((character.getLastThirdX() >= bullet.getLeft()) &&
                    (character.getCenterX() <= bullet.getCenterX()) && !character.isUsingPower()) {
                setBulletHit();
                return true;
            }
        }

        return false;
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
        if (bullet.isFalling)
            bullet.setBottom(0);
        else
            bullet.setTop(value);
    }



    public void setBulletHit() {
        bullet.setHit();
    }
}
