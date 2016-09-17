package com.example.jarvis;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

public class LinedEditText extends EditText {
    private Rect mRect;
    private Paint mPaint;

    int initialCount = 0;

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        //initialCount = this.getMinLines();
        
        //setLines(initialCount);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        // Gets the number of lines of text in the View.
        int count = getBaseline();
        // Gets the global Rectangle and Paint objects
        Rect r = mRect;
        
        Paint paint = mPaint;
        Paint paint1 = new Paint();
        paint1.setColor(Color.RED);
        paint1.setStyle(Paint.Style.STROKE);
        // Gets the baseline coordinates for the current line of text
        int baseline = getLineBounds(0, r);
        
        //canvas.drawLine(r.left-60, 22, r.right+60, 22, paint1);
        //canvas.drawLine(r.left-60, 25, r.right+60, 25, paint1);
     
        // Draws one line in the rectangle for every line of text in the EditTex
        for (int i = 0; i < count; i++) {
            /*
             * Draws a line in the background from the left of the rectangle to the
             * right, at a vertical position one dip below the baseline, using the
             * "paint" object for details.
             */
        	
            canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
            baseline += getLineHeight();//next line
        }

        // Finishes up by calling the parent method
        /*int x=15;
        for(int j=30;j<430;j=j+50)
        {   canvas.drawCircle(j, 10, 12, paint);
        }*/
        super.onDraw(canvas);
    }
}