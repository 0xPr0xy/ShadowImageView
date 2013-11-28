package com.example.app.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ShadowImageView extends ImageView {

    private static Paint paintShadow;
    private boolean disableRandomRotation;

    public ShadowImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintShadow = new Paint();

        setLayerType(LAYER_TYPE_HARDWARE, paintShadow);

        paintShadow.setColor(Color.GRAY);
        paintShadow.setStyle(Paint.Style.STROKE);
        paintShadow.setAntiAlias(true);
        paintShadow.setDither(true);
        paintShadow.setStrokeWidth(3);

        // TODO: android does not support LAYER_TYPE_HARDWARE and setShadowLayer()
        // TODO: http://developer.android.com/guide/topics/graphics/hardware-accel.html#unsupported

        // paintShadow.setShadowLayer(4.0f, 0.0f, 0.0f, Color.BLACK);

    }

    public void setRandomRotationDisabled()
    {
        disableRandomRotation = true;
    }

    private int getRandomRotation()
    {
        return -2 + (int) (Math.random() * ((3 - (-2)) + 1));
    }

    private Rect getRectForSize(int x, int y, int width, int height)
    {
        return new Rect(x,y,width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        if(disableRandomRotation){
            setRotation(2);
        } else {
            setRotation(getRandomRotation());
        }
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        canvas.drawLine(0, getHeight()-1, getWidth(), getHeight()-1, paintShadow);
        canvas.drawLine(getWidth()-1, 0, getWidth()-1, getHeight(), paintShadow);
        Rect dst = getRectForSize(8, 8, getWidth()-8, getHeight()-8);
        canvas.drawBitmap(bitmap, null, dst, null);
    }
}