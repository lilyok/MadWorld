package com.lil.MadWorld.Models;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

public abstract class SubjectOfTheWorld {

    protected Point mPoint;

    protected int mHeight;

    protected int mWidth;

    protected Drawable mImage;

    protected int mSpeed;

    protected int maxRight = 0;

    protected int maxBottom = 0;

    protected boolean isMoving = true;

    public SubjectOfTheWorld(Drawable image)
    {
        mImage = image;
        mPoint = new Point(0, 0);
        mWidth = image.getIntrinsicWidth();
        mHeight = image.getIntrinsicHeight();
    }

    protected abstract void updatePoint();

    public void update()
    {
        if (isMoving)
            updatePoint();
        mImage.setBounds(mPoint.x, mPoint.y, mPoint.x + mWidth, mPoint.y + mHeight);
    }

    public void draw(Canvas canvas)
    {
        mImage.draw(canvas);
    }

    /** Задает левую границу объекта */
    public void setLeft(int value)
    {
        mPoint.x = value;
    }

    public void setRight(int value)
    {
        mPoint.x = value - mWidth;
    }

    public void setTop(int value)
    {
        mPoint.y = value;
    }

    public void setBottom(int value)
    {
        mPoint.y = value - mHeight;
    }

    public void setCenterX(int value)
    {
        mPoint.x = value - mWidth / 2;
    }

    public void setCenterY(int value)
    {
        mPoint.y = value - mHeight / 2;
    }


    public void setMaxRight(int width) {
        this.maxRight = width;
    }

    public void setMaxBottom(int maxBottom) {
        this.maxBottom = maxBottom;
    }


    public void paused(){
        isMoving = false;
    }

    public void continued(){
        isMoving = true;
    }

    public int getCenterX() {
        return (mPoint.x + mWidth / 2);
    }

    public int getLeftX() {
        return mPoint.x;
    }

    public int getRightX() {
        return mPoint.x + mWidth;
    }
}
