package com.lil.MadWorld.Models;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class MadWorld extends SubjectOfTheWorld {

    public MadWorld(Drawable image, int speed)
    {
        super(image);

        mSpeed = speed;
    }
    @Override
    protected void updatePoint() {
         mPoint.x += mSpeed;
         if (mPoint.x + mWidth <= 0)
             mPoint.x = 0;
    }

    @Override
    protected void updateAnimate() {

    }

    @Override
    public void draw(Canvas c)
    {
        mImage.draw(c);
        mImage.setBounds(mPoint.x + mWidth, 0, mPoint.x + 2*mWidth, mHeight);
        mImage.draw(c);
    }

    public void setMHeight(int value)
    {
        mHeight = value;
    }

    public void setMWidth(int value)
    {
        mWidth = value;
    }





}
