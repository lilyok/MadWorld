package com.lil.MadWorld.Models;

import android.graphics.drawable.Drawable;

import java.util.List;

public class Vampire extends Character {
    private int sunProtection = maxHealth;
    public Vampire(List<Drawable> defaultImages, int speedOfWorld, int speedFactor) {
        super(defaultImages, speedOfWorld, speedFactor, INVISIBILITY);
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
    }
    public void takeHalfLife() {
        health = Math.min((health+maxHealth/2), maxHealth);
    }

    public void takeSunProtection() {
        sunProtection = maxHealth;
    }

    public void takeHalfSunProtection() {
        sunProtection = Math.min((sunProtection+maxHealth/2), maxHealth);
    }

    public int getSunProtection() {
        return sunProtection;
    }

    public void makeBurning(int speed) {
        sunProtection-=speed;
        if (sunProtection <= 0)
            sunProtection = 0;
    }

    public void setSunProtection(int sunProtection) {
        this.sunProtection = sunProtection;
    }

    public void cleanBaseIndexOfFrame(){
        super.cleanBaseIndexOfFrame();
    }
}