package com.lil.MadWorld.Models;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class MadWorld extends SubjectOfTheWorld {
    private List<Drawable> images;
    private int indexOfFirstImage = 1;
    public MadWorld(List<Drawable> images, int speed) {
        super(images.get(1));
        this.images = new ArrayList<Drawable>(images);
        mSpeed = speed;
    }

    @Override
    protected void updatePoint() {
        mPoint.x += mSpeed;
        if (mPoint.x + mWidth <= 0) {
            indexOfFirstImage = (indexOfFirstImage + 1)%images.size();
            mImage = images.get(indexOfFirstImage);
            mPoint.x = 0;//mWidth;
        }
    }

    @Override
    protected void updateAnimate() {

    }

    @Override
    public void draw(Canvas c) {
        images.get(indexOfFirstImage).draw(c);
        Drawable nextImg = images.get((indexOfFirstImage+1)%images.size());

        nextImg.setBounds(mPoint.x + mWidth, 0, mPoint.x + 2 * mWidth, mHeight);
        nextImg.draw(c);
//        mImage.draw(c);
//        mImage.setBounds(mPoint.x + mWidth, 0, mPoint.x + 2 * mWidth, mHeight);
//        mImage.draw(c);
    }

    public void setMHeight(int value) {
        mHeight = value;
    }

    public void setMWidth(int value) {
        mWidth = value;
    }


}
