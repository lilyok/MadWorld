package com.lil.MadWorld.Models;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;

public abstract class SubjectOfTheWorld {

    protected Point mPoint;

    protected int mHeight;

    protected int mWidth;

    protected Drawable mImage;

    protected int mSpeed;

    protected int maxRight = 1000000000;

    protected int maxBottom = 0;

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    protected boolean isMoving = true;

    public SubjectOfTheWorld(Drawable image) {
        mImage = image;
        mWidth = image.getIntrinsicWidth();
        mHeight = image.getIntrinsicHeight();
        mPoint = new Point(-mWidth, 0);
    }

    protected abstract void updatePoint();

    protected abstract void updateAnimate();

    public void update() {
        if (isMoving)
            updatePoint();
        else
            updateAnimate();
        mImage.setBounds(mPoint.x, mPoint.y, mPoint.x + mWidth, mPoint.y + mHeight);
    }

    public void draw(Canvas canvas) {
        mImage.draw(canvas);
    }

    /**
     * Задает левую границу объекта
     */
    public void setLeft(int value) {
        mPoint.x = value;
  //      Log.w("subjectLeft = ", String.valueOf(value));

    }

    public void setRight(int value) {
        mPoint.x = value - mWidth;
    }

    public void setTop(int value) {
        mPoint.y = value;
    }

    public void setBottom(int value) {
        mPoint.y = value - mHeight;
    }

    public void setCenterX(int value) {
        mPoint.x = value - mWidth / 2;
    }

    public void setCenterY(int value) {
        mPoint.y = value - mHeight / 2;
    }


    public void setMaxRight(int width) {
        this.maxRight = width;
    }

    public void setMaxBottom(int maxBottom) {
        this.maxBottom = maxBottom;
    }


    public void paused() {
        isMoving = false;
    }

    public void continued() {
        isMoving = true;
    }

    public int getCenterX() {
        return (mPoint.x + mWidth / 2);
    }

    public int getCenterY() {
        return (mPoint.y +mHeight)/2;
    }

    public int getLeft() {
        return mPoint.x;
    }

    public int getRight() {
        return mPoint.x + mWidth;
    }


    public int getBottom() {
        return mPoint.y + mHeight;
    }

    public int getTop() {
        return mPoint.y;
    }
    public int getLastThirdX() {
        return (2 * getRight() + getLeft()) / 3;
    }


    public int getFirstThirdY() {
        return (2 * getTop() + getBottom()) / 3;
    }
}
