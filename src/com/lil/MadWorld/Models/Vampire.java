package com.lil.MadWorld.Models;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Vampire extends Character {
    private List<Drawable> firedImages;
    private List<Drawable> bloodedImages;

    private boolean isFired = false;
    private boolean isBlooded = false;
    private int indexOfFire = 0;
    private int indexOfBlood = 0;

    private int numOfFire = 0;
    private int numOfBlood = 0;
    private int baseOfFire = 0;
    private int baseOfBlood = 0;

    private int sunProtection = maxHealth;
    private int firedHeight;


    public Vampire(List<Drawable> defaultImages, List<Drawable> firedImages, List<Drawable> bloodedImages,
                   int speedOfWorld, int speedFactor) {
        super(defaultImages, speedOfWorld, speedFactor, INVISIBILITY);
        this.firedImages = new ArrayList<Drawable>(firedImages);
        this.bloodedImages = new ArrayList<Drawable>(bloodedImages);
        numOfFire = firedImages.size()/2;
        numOfBlood = bloodedImages.size()/2;
        firedHeight = firedImages.get(baseOfFire).getIntrinsicHeight();
    }


    public void update() {
        super.update();

        if (isFired) {
            indexOfFire = baseOfFire + (indexOfFire+1) % numOfFire;
        }

        if (isBlooded) {
            indexOfBlood = baseOfBlood + (indexOfBlood+1) % numOfBlood;
        }

    }

    public void draw(Canvas canvas) {
        if (baseOfBlood == 0 && baseOfFire == 0)
            super.draw(canvas);

        setBurningDeathPosition();
        setBloodedDeathPosition();


        if (isFired)
            firedImages.get(indexOfFire).draw(canvas);

        if (isBlooded)
            bloodedImages.get(indexOfBlood).draw(canvas);

    }


    @Override
    public int getPower() {
        if (isUsingPower())
            return INVISIBILITY;
        else
            return VAMPIRE_POWER;
    }


    public void takeLife() {
        health = maxHealth;
        cleanBlood();
    }
    public void takeHalfLife() {
        health = Math.min((health+maxHealth/2), maxHealth);
        cleanBlood();
    }

    public void takeSunProtection() {
        sunProtection = maxHealth;
        cleanFire();
    }

    public void takeHalfSunProtection() {
        sunProtection = Math.min((sunProtection+maxHealth/2), maxHealth);
        if (sunProtection > maxHealth/2) {
            cleanFire();
        }
    }

    private void cleanBlood() {
        baseOfBlood = 0;
        isBlooded = false;
        bloodedImages.get(indexOfBlood).setBounds(0, 0, 0, 0);
    }

    private void cleanFire() {
        baseOfFire = 0;
        isFired = false;
        firedImages.get(indexOfFire).setBounds(0, 0, 0, 0);
    }

    public int makeWeaken() {
        super.makeWeaken();
        setHurt();

        return health;
    }

    public int makeWeaken(int enemyPower) {
        super.makeWeaken(enemyPower);
        setHurt();
        return health;
    }

    public void setHurt() {
        setBloodedDeathPosition();
        if (health <= 0){
            baseOfBlood = numOfBlood;
        }

    }

    private void setBloodedDeathPosition() {
        if ((getLeft() < maxRight) && (health <= maxHealth/2)) {
            if (!isBlooded)
                for (Drawable img : bloodedImages)
                    img.setBounds(mPoint.x, mPoint.y, mPoint.x + mWidth, mPoint.y + mHeight);
            isBlooded = true;
        }
    }

    public void makeBurning(int speed) {
        sunProtection-=speed;
        setBurningDeathPosition();
        if (sunProtection <= 0) {
            sunProtection = 0;
            baseOfFire = numOfFire;
        }

    }

    private void setBurningDeathPosition() {
        if ((getLeft() < maxRight) && (sunProtection <= maxHealth/2)){
            if (!isFired)
                for (Drawable img : firedImages)
                    img.setBounds(mPoint.x, mPoint.y + mHeight - firedHeight,
                            mPoint.x + mWidth, mPoint.y + mHeight);
            isFired = true;
        }
    }

    public int getSunProtection() {
        return sunProtection;
    }

    public void setSunProtection(int sunProtection) {
        this.sunProtection = sunProtection;
    }

    public void cleanBaseIndexOfFrame(){
        super.cleanBaseIndexOfFrame();
    }


    public int getBaseOfFire() {
        return baseOfFire;
    }

    public int getBaseOfBlood() {
        return baseOfBlood;
    }

    public void setIsBlooded(boolean isBlooded) {
        this.isBlooded = isBlooded;
    }

    public void setIsFired(boolean isFired) {
        this.isFired = isFired;
    }

    public void setBaseOfFire(int baseOfFire) {
        this.baseOfFire = baseOfFire;
    }

    public void setBaseOfBlood(int baseOfBlood) {
        this.baseOfBlood = baseOfBlood;
    }

    public int getWidth() {
        return mWidth;
    }
}