package com.lil.MadWorld;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class GameStatusAlert extends GameAlert {
    private List<String> values;
    private Paint tablePaint;
    private int startXTable;
    private int stopXTable;

    private int stopXFields;

    public GameStatusAlert(String title, ArrayList<String> text, ArrayList<String> values, int titleColor, int textColor, int w) {
        super(title, text, titleColor, textColor, w*3/8);
//        valuesLength = width/9;
        startXTable = w/4;
        stopXFields = 5*w/8;
        stopXTable = 3*w/4;

        this.values = new ArrayList<String>();
        this.values.addAll(values);
        tablePaint = new Paint();
        tablePaint.setAlpha(200);
        tablePaint.setColor(Color.YELLOW);
        tablePaint.setStrokeWidth(2.0f);
        tablePaint.setStyle(Paint.Style.STROKE);
    }


    public void draw (Canvas c){
        final int size = alertText.size();
        final int textSize = height / size;

        c.drawRect(startXTable, 0, stopXTable, height * (size+1) / size, backgroundPaint);
        float lineLength = textPaint.measureText(alertTitle);
        c.drawText(alertTitle, (startXTable+stopXTable)/2-lineLength/2, textSize,titlePaint);


        for (int i = 0 ; i < size; i++) {
            final int lineLastY = textSize * (i+1);
            final int lineY = textSize * (i + 2);
            c.drawRect(startXTable, lineLastY, stopXFields, lineY, tablePaint);
            c.drawRect(stopXFields, lineLastY, stopXTable, lineY, tablePaint);

//            String line = alertText.get(i);
            c.drawText(alertText.get(i), startXTable, lineY, textPaint);

            String value = values.get(i);
            float valueLength = textPaint.measureText(value);
            c.drawText(value, stopXTable - valueLength, lineY, textPaint);

        }
    }
}
