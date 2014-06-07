package com.lil.MadWorld.Models;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class MadWorld extends SubjectOfTheWorld {
    private List<Drawable> images;
    private int indexOfFirstImage = 1;

    private CollectOfGift gifts;

    private CollectOfGift flowers;

    public MadWorld(ArrayList<Drawable> images, ArrayList<Drawable> giftsImages, ArrayList<Drawable> flowersImages,
                    int speed) {
        super(images.get(1));
        this.images = new ArrayList<Drawable>(images);
        mSpeed = speed;

        gifts = new CollectOfGift(giftsImages, speed, false);
        flowers = new CollectOfGift(flowersImages, speed, true);
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

    public void setMImageByFirst(){
        mImage = images.get(indexOfFirstImage);
    }

    @Override
    protected void updateAnimate() {

    }

    @Override
    public void update(){
        super.update();
        gifts.update();
        flowers.update();
    }

    @Override
    public void draw(Canvas c) {
 //       super.draw(c);
        images.get(indexOfFirstImage).draw(c);
        Drawable nextImg = images.get((indexOfFirstImage+1)%images.size());

        nextImg.setBounds(mPoint.x + mWidth, 0, mPoint.x + 2 * mWidth, mHeight);
        nextImg.draw(c);


        gifts.draw(c);
        flowers.draw(c);
    }

    public void setMHeight(int value) {
        mHeight = value;
        gifts.setMaxBottom(value);
        flowers.setMaxBottom(value);
    }

    public void setMWidth(int value) {
        mWidth = value;
        gifts.setMaxRight(value);
        flowers.setMaxRight(value);
    }

    @Override
    public void paused() {
        isMoving = false;
        gifts.paused();
        flowers.paused();
    }

    public void continued() {
        isMoving = true;
        gifts.continued();
        flowers.continued();
    }

    public int takeGift(Vampire vampire) {
        return gifts.takeGift(vampire);
    }
    public int takeFlower(Vampire vampire) {return flowers.takeGift(vampire);}

    public boolean isNoon(int time) {
        if ((indexOfFirstImage == 0)&&(mPoint.x >= -time/2)){
                return true;
        }
        else if ((indexOfFirstImage == 1)&&(getRight() <= time/2)) {
            return true;
        }
        return false;
    }

    public int getIndexOfFirstImage() {
        return indexOfFirstImage;
    }

    public void setIndexOfFirstImage(int indexOfFirstImage) {
        this.indexOfFirstImage = indexOfFirstImage;
    }

    public void setGiftsStatus(){
        if (isMoving) {
            gifts.continued();
            flowers.continued();
        } else {
            gifts.paused();
            flowers.paused();
        }
    }
}
