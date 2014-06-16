package com.lil.MadWorld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.lil.MadWorld.GameAlert;

public class GameTaskAlert extends GameAlert {
    private Paint buttonPaint;
    private Paint buttonTextPaint;
    private final String CLOSE_TEXT;

    public boolean isVisible() {
        return isVisible;
    }

    private boolean isVisible = true;

    public GameTaskAlert(String closeString, String title, String text, int titleColor, int textColor, int width) {
        super(title, text, titleColor, textColor, width);

        buttonPaint = new Paint();
        buttonPaint.setColor(Color.BLUE);
        buttonPaint.setAlpha(150);

        buttonTextPaint = new Paint();
        buttonTextPaint.setTextSize(TEXT_SIZE);
        buttonTextPaint.setAlpha(255);
        buttonTextPaint.setColor(Color.argb(255, 32, 178, 170));
        buttonTextPaint.setFakeBoldText(true);
        CLOSE_TEXT = closeString;
    }

    public void draw (Canvas c){
        if (isVisible) {
            super.draw(c);

            c.drawRect(0, height, width, (float) (height + 1.5*TEXT_SIZE), buttonPaint);

            float lineLength = buttonTextPaint.measureText(CLOSE_TEXT);
            c.drawText(CLOSE_TEXT, (width - lineLength) / 2, height +TEXT_SIZE, buttonTextPaint);
        }
    }

    public void tryClose(float y){
        if (isVisible)
            if ((y >= height) && (y <= height+2*TEXT_SIZE))
                isVisible = false;
    }

    public void setVisible(){
        isVisible = true;
    }

}
