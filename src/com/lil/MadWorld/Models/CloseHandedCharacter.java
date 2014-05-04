package com.lil.MadWorld.Models;

import android.graphics.drawable.Drawable;

import java.util.List;

public class CloseHandedCharacter extends Character {

    public CloseHandedCharacter(List<Drawable> defaultImages, int speedOfWorld, int speedFactor, int magicPower) {
        super(defaultImages, speedOfWorld, speedFactor, magicPower);

        chance = 10;

    }

}
