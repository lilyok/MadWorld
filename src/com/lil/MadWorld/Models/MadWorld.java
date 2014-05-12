package com.lil.MadWorld.Models;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class MadWorld extends SubjectOfTheWorld {
    private List<Drawable> images;
    private int indexOfFirstImage = 1;

    private CollectOfGift gifts;

    public MadWorld(List<Drawable> images, List<Drawable> giftsImages, int speed) {
        super(images.get(1));
        this.images = new ArrayList<Drawable>(images);
        mSpeed = speed;

//        List numOfGifts = new ArrayList();
//        numOfGifts.add(1);
       // numOfGifts.add(3);
        gifts = new CollectOfGift(giftsImages, speed);
    }

    public int getSpeed() {
        return mSpeed;
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
    public void update(){
        super.update();
        gifts.update();
    }

    @Override
    public void draw(Canvas c) {
 //       super.draw(c);
        images.get(indexOfFirstImage).draw(c);
        Drawable nextImg = images.get((indexOfFirstImage+1)%images.size());

        nextImg.setBounds(mPoint.x + mWidth, 0, mPoint.x + 2 * mWidth, mHeight);
        nextImg.draw(c);


        gifts.draw(c);
    }

    public void setMHeight(int value) {
        mHeight = value;
        gifts.setMaxBottom(value);
    }

    public void setMWidth(int value) {
        mWidth = value;
        gifts.setMaxRight(value);
    }

    @Override
    public void paused() {
        isMoving = false;
        gifts.paused();
    }

    public void continued() {
        isMoving = true;
        gifts.continued();
    }

    public void takeGift(Vampire vampire) {
        gifts.takeGift(vampire);
    }

    public boolean isNoon(int time) {
        if ((indexOfFirstImage == 0)&&(mPoint.x >= -time/2)){
                return true;
        }
        else if ((indexOfFirstImage == 1)&&(getRight() <= time/2)) {
            return true;
        }
        return false;
    }
}
