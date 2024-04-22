package com.demo.example.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

public class CustomViewFlipper extends ViewFlipper {
    Paint paint = new Paint();

    public CustomViewFlipper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        float width = ((float) (getWidth() / 2)) - ((((float) getChildCount()) * 24.0f) / 2.0f);
        float height = (float) (getHeight() - 15);
        canvas.save();
        for (int i = 0; i < getChildCount(); i++) {
            if (i == getDisplayedChild()) {
                this.paint.setColor(-1);
                canvas.drawCircle(width, height, 8.0f, this.paint);
            } else {
                this.paint.setColor(-7829368);
                canvas.drawCircle(width, height, 8.0f, this.paint);
            }
            width += 24.0f;
        }
        canvas.restore();
    }
}
