package com.example.leprechaun;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Leprechaun extends LeprechaunObject {

    private final int[] pic = new int[]{R.drawable.lep1, R.drawable.lep2, R.drawable.lep3, R.drawable.lep2};
    private boolean isJumping, isRunning;
    private int count;
    private int currentFrame = 0;
    private final Resources resources;
    private Bitmap bitmap;

    public Leprechaun(LeprechaunView leprechaunView, Bitmap bitmapObject, int x, int y) {
        super(leprechaunView, bitmapObject, x, y);
        resources = leprechaunView.getResources();
        setObjectXSpeed(0);
        isJumping = false;
        isRunning = true;
        count = 15;
        bitmap = BitmapFactory.decodeResource(resources, pic[currentFrame]);
    }

    public void jump() {
        setObjectYSpeed(30);
        setObjectXSpeed(7);
        isJumping = true;
        isRunning = false;
    }

    @Override
    public void update() {
        if (isJumping) {
            if (count > 0) {
                setObjectY(getObjectY() - getObjectYSpeed());
                setObjectX(getObjectX() + getObjectXSpeed());
                count--;
                bitmap = BitmapFactory.decodeResource(resources, R.drawable.lep4);
            } else {
                isJumping = false;
                setObjectXSpeed(5);
            }
        }
        if ((count < 15) && !isJumping) {
            bitmap = BitmapFactory.decodeResource(resources, R.drawable.lep5);
            setObjectY(getObjectY() + getObjectYSpeed());
            setObjectX(getObjectX() + getObjectXSpeed() * 2);
            count++;
        } else if ((getObjectX() > 350) && !isJumping) {
            isRunning = true;
            setObjectXSpeed(15);
            setObjectX(getObjectX() - getObjectXSpeed());
            bitmap = BitmapFactory.decodeResource(resources, pic[currentFrame]);

        } else if (isRunning) {
            bitmap = BitmapFactory.decodeResource(resources, pic[currentFrame]);
        }
        if (currentFrame < 3) {
            currentFrame = ++currentFrame;
        } else {
            currentFrame = 0;
        }
    }

    @Override
    public void onDraw(Canvas c) {
        update();
        c.drawBitmap(bitmap, getObjectX(), getObjectY(), null);
    }

    public boolean isRunning() {
        return isRunning;
    }

}
