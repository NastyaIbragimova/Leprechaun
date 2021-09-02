package com.example.leprechaun;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.leprechaun.LeprechaunView;

public abstract class LeprechaunObject {

    private LeprechaunView leprechaunView;
    private Bitmap leprechaunBitmap;
    private int objectX, objectY, objectWidth, objectHeight, objectXSpeed, objectYSpeed;
    private boolean isObjectAlive;

    public LeprechaunObject(LeprechaunView leprechaunView, Bitmap bitmapObject, int x, int y) {
        this.leprechaunView = leprechaunView;
        this.leprechaunBitmap = bitmapObject;
        objectWidth = bitmapObject.getWidth();
        objectHeight = bitmapObject.getHeight();
        objectX = x;
        objectY = y;
        objectXSpeed = 15;
        objectYSpeed = 0;
        isObjectAlive = true;
    }

    public void update() {
        objectX = objectX - objectXSpeed;
    }

    public void onDraw(Canvas c) {
        update();
        c.drawBitmap(leprechaunBitmap, objectX, objectY, null);
    }

    public boolean isCollision(float left1, float right1, float top1) {
        if (getRight() >= left1 && getRight() <= right1) {
            if (getBottom() < top1) {
                return false;
            }
            return true;
        }
        return false;
    }

    public int getObjectX() {
        return objectX;
    }

    public int getObjectY() {
        return objectY;
    }

    public void setObjectX(int objectX) {
        this.objectX = objectX;
    }

    public void setObjectY(int objectY) {
        this.objectY = objectY;
    }

    public int getObjectWidth() {
        return leprechaunBitmap.getWidth();
    }

    public int getLeft() {
        return objectX;
    }

    public int getRight() {
        return objectX + objectWidth;
    }

    public int getTop() {
        return objectY;
    }

    public int getBottom() {
        return objectY + objectHeight;
    }

    public void setObjectXSpeed(int objectXSpeed) {
        this.objectXSpeed = objectXSpeed;
    }

    public int getObjectXSpeed() {
        return objectXSpeed;
    }

    public void setObjectAlive(boolean isObjectAlive) {
        this.isObjectAlive = isObjectAlive;
    }

    public boolean isObjectAlive() {
        return isObjectAlive;
    }

    public int getObjectYSpeed() {
        return objectYSpeed;
    }

    public void setObjectYSpeed(int objectYSpeed) {
        this.objectYSpeed = objectYSpeed;
    }
}

