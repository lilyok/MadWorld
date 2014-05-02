package com.lil.MadWorld.Models;

import android.graphics.drawable.Drawable;

import java.util.List;

public class Vampire extends Character {


    public Vampire(List<Drawable> defaultImages, int speedOfWorld, int speedFactor) {
        super(defaultImages, speedOfWorld, speedFactor);
    }

    @Override
    public int getPower() {
        if (isUsingPower())
            return INVISIBILITY;
        else
            return VAMPIRE_POWER;
    }


    public int getLastFourth(){
        return (2*getRightX() + getLeftX())/3;
    }
    public void takeLife() {
        health = DEFAULT_HEALTH;
    }
}