package com.lil.MadWorld.Models;

import android.graphics.drawable.Drawable;

import java.util.List;

public class Werewolf extends Character {


    public Werewolf(List<Drawable> defaultImages, int speedOfWorld, int speedFactor) {
        super(defaultImages, speedOfWorld, speedFactor);
    }

    @Override
    public int getPower() {
        if (isUsingPower())
            return WEREWOLF_POWER;
        else
            return HUMAN_POWER;
    }

    @Override
    public int getBulletLeft() {
        return 0;
    }

    @Override
    public int getBulletCenterX() {
        return 0;
    }

}