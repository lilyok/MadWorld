package com.lil.MadWorld.Models;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.List;

public class DivingKite extends Character {
    private int maxBottom = 0;

    private boolean isHidden = true;

    public DivingKite(List<Drawable> defaultImages, int speedFactor, int magicPower) {
        super(defaultImages, 0, speedFactor, magicPower);
        numOfFrame = defaultImages.size();
        setBottom(0);
        setLeft(0);
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public void setMaxBottom(int maxBottom) {
        this.maxBottom = maxBottom;
    }

    public boolean isAttack(){
        if (getBottom() >= maxBottom)
            return true;
        else
            return false;
    }

    public int getHeight(){
        return mHeight;
    }


    @Override
    protected void updatePoint() {
        if (getLeft() == 0)
            setLeft(maxRight/2 - maxBottom);

        if (isHidden){
            mSpeed = - Math.abs(mSpeed);
        }
        if ((!isHidden)||(getBottom() > 0)) {
//            setCenterX(maxRight / 2);

            if ((getCenterX() < maxRight/2) || (mSpeed < 0))
                mPoint.x += mSpeed;
            mPoint.y += mSpeed;

            if (/*(getBottom() > maxBottom) ||*/ (getBottom() < 0)) {
                setLeft(maxRight/2 - maxBottom);
                setBottom(0);
                mSpeed = -mSpeed;
            }else if (getBottom() > maxBottom){
                setBottom(maxBottom);
            }

            updateAnimate();
        }
    }
//
//    public void update() {
//        if (isMoving)
//            updatePoint();
//        else
//            updateAnimate();
//        mImage.setBounds(mPoint.x, mPoint.y, mPoint.x + mWidth, mPoint.y + mHeight);
//    }

    @Override
    protected void updateAnimate() {
//        if (!isMoving && baseIndexOfFrame == 0)
//            baseIndexOfFrame = numOfFrame * DEFAULT_INDEX_NO_POWER_ATTACK_FACE;
//        else if (baseIndexOfFrame == numOfFrame * DEFAULT_INDEX_NO_POWER_ATTACK_FACE)
//            baseIndexOfFrame = 0;
        indexOfFrame = (indexOfFrame + 1) % numOfFrame;
        mImage = defaultImages.get(indexOfFrame);

    }
}
