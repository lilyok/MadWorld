package com.lil.MadWorld;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class GameAlert {
    private String alertTitle = "";
    private List<String> alertText = new ArrayList();
    private Paint backgroundPaint;
    private Paint titlePaint;
    private Paint textPaint;
    protected int width = 0;
    protected int height = 0;

    protected static final int TEXT_SIZE = 40;

    public GameAlert(String title, String text, int titleColor, int textColor, int width){
        this.width = width;
        alertTitle = title;

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.BLACK);
        backgroundPaint.setAlpha(150);


        titlePaint = new Paint();
        titlePaint.setTextSize(TEXT_SIZE);
        titlePaint.setAlpha(255);
        titlePaint.setColor(titleColor);
        titlePaint.setFakeBoldText(true);

        textPaint = new Paint();
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setAlpha(255);
        textPaint.setColor(textColor);
        textPaint.setFakeBoldText(false);


        String[] words = text.split(" ");
        String line = "";

        alertText = new ArrayList();

        for (int i = 0; i < words.length; i++) {
            if (textPaint.measureText(line + words[i]) > width - 2*TEXT_SIZE) {
                alertText.add(line);
                line = "";
            }

            line += words[i] + " ";
        }

        alertText.add(line);

        height = TEXT_SIZE * (alertText.size()+2);
    }


    public void draw (Canvas c){
        c.drawRect(0, 0, width, height, backgroundPaint);
        float lineLength = textPaint.measureText(alertTitle);
        c.drawText(alertTitle, (width-lineLength)/2, TEXT_SIZE,titlePaint);
        for (int i = 0 ; i < alertText.size(); i++) {
            String line = alertText.get(i);
            lineLength = textPaint.measureText(line);
            c.drawText(line, (width-lineLength)/2, (i+2)*TEXT_SIZE, textPaint);
        }
    }
}
