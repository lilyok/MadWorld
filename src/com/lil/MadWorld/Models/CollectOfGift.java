package com.lil.MadWorld.Models;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class CollectOfGift{
    private ArrayList<Gift> gifts;
    
    private class Gift extends SubjectOfTheWorld{
        public Gift(Drawable image, int speed) {
            super(image);
            mSpeed = speed;
            mPoint.y = -mWidth; 
            mPoint.x = 0;
        }

        @Override
        protected void updatePoint() {
            
        }

        @Override
        protected void updateAnimate() {

        }

        public void randomX() {
        }
    } 
    
    
    public CollectOfGift(List<Drawable> images, int maxWidth, int maxHeight, int worldSpeed) {
        gifts = new ArrayList<Gift>();
        for (Drawable img : images){
            gifts.add(new Gift(img, worldSpeed));
        }

    }

    public void setMaxRight(int width) {
        for (Gift gift : gifts) {
            gift.setMaxRight(width);
            gift.randomX();
        }
    }

    public void setMaxBottom(int bottom) {
        for (Gift gift : gifts) {
            gift.setMaxRight(bottom);
            gift.mPoint.y = bottom/3;
        }               
    }

}
