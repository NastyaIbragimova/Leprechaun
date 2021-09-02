package com.example.leprechaun;

import android.graphics.Bitmap;

public class Coin extends LeprechaunObject {

    public Coin(LeprechaunView leprechaunView, Bitmap bitmapObject, int x, int y) {
        super(leprechaunView, bitmapObject, x, y);
    }

    public boolean isCollision(float left1, float right1) {
        if (getRight() >= left1 && getRight() <= right1) {
            return true;
        }
        if (getLeft() >= left1 && getLeft() <= right1) {
            return true;
        }
        return false;
    }
}
