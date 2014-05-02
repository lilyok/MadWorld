package com.lil.MadWorld.Models;

import android.graphics.drawable.Drawable;

import java.util.List;

public class Hunter extends Character {
        public Hunter(List < Drawable > defaultImages, int speedOfWorld, int speedFactor) {
            super(defaultImages, speedOfWorld, speedFactor);
        }

        @Override
        public int getPower() {
            return HUMAN_POWER;
        }
}
