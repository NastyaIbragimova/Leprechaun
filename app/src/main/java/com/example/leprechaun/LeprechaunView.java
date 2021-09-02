package com.example.leprechaun;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class LeprechaunView extends SurfaceView implements SurfaceHolder.Callback {

    private LeprechaunThread leprechaunThread;
    private Bitmap scaledBack;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private ArrayList<Coin> coins = new ArrayList<>();
    private Leprechaun leprechaun;
    private int countCoins;
    private final int[] oPic = new int[]{R.drawable.rock, R.drawable.wood, R.drawable.mushroom};
    private final Random random = new Random();
    private Thread threadObstacle, threadCoin;

    public LeprechaunView(Context context) {
        super(context);
        unit();
    }

    public LeprechaunView(Context context, AttributeSet attrs) {
        super(context, attrs);
        unit();
    }

    private void startGame() {
        countCoins = 0;
        Bitmap bitmapLeprechaun = BitmapFactory.decodeResource(getResources(), R.drawable.lep2);
        leprechaun = new Leprechaun(this, bitmapLeprechaun, 350, getScreenHeight() - bitmapLeprechaun.getHeight() - 50);

        threadObstacle = new Thread(new Runnable() {
            @Override
            public void run() {
                fillObstacleArray(random.nextInt(3));
                while (leprechaun.isObjectAlive()) {
                    try {
                        Thread.sleep(random.nextInt(5000) + 5000);
                        fillObstacleArray(random.nextInt(3));
                    } catch (InterruptedException e) {
                    }
                }
                stopGame();
            }
        });
        threadObstacle.start();
        threadCoin = new Thread(new Runnable() {
            @Override
            public void run() {
                fillCoinsArray(random.nextInt(3));
                while (leprechaun.isObjectAlive()) {
                    try {
                        Thread.sleep(random.nextInt(5000) + 3000);
                        fillCoinsArray(random.nextInt(3));
                    } catch (InterruptedException e) {
                    }
                }
                stopGame();
            }
        });
        threadCoin.start();
    }

    private void stopGame() {
        obstacles.clear();
        coins.clear();
        threadCoin.interrupt();
        threadObstacle.interrupt();
        Intent intent = new Intent(getContext(), EndGameActivity.class);
        intent.putExtra("CountCoins", countCoins);
        getContext().startActivity(intent);
    }

    private void fillCoinsArray(int r) {
        Bitmap bitmapCoin = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
        Coin coin;
        if (r != 2) {
            coin = new Coin(this, bitmapCoin, getScreenWidth(), (getScreenHeight() - bitmapCoin.getHeight() - 51 - 100 * r));
        } else {
            coin = new Coin(this, bitmapCoin, getScreenWidth(), (getScreenHeight() - bitmapCoin.getHeight() - 51 - 80 * r));
        }

        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle o = obstacles.get(i);
            if (coin.isCollision((o.getLeft() - 30), (o.getRight() + 30))) {
                Log.e("MyLog", "coin ");
                coin.setObjectY(630);
            }
        }
        coins.add(coin);
    }

    private void fillObstacleArray(int r) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), oPic[r]);
        obstacles.add(new Obstacle(this, bitmap, getScreenWidth(), (getScreenHeight() - bitmap.getHeight() - 10)));
    }


    private void unit() {
        leprechaunThread = new LeprechaunThread(this);
        getHolder().addCallback(this);
        startGame();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(scaledBack, 0, 0, null);
        testCollision();
        leprechaun.onDraw(canvas);
        Iterator<Coin> iteratorCoin = coins.iterator();
        while (iteratorCoin.hasNext()) {
            Coin coin = iteratorCoin.next();
            changeSpeed(coin);
            coin.onDraw(canvas);
            if (coin.getObjectX() + coin.getObjectWidth() < -10) {
                iteratorCoin.remove();
            }
        }

        Iterator<Obstacle> iteratorO = obstacles.iterator();
        while (iteratorO.hasNext()) {
            Obstacle obstacle = iteratorO.next();
            changeSpeed(obstacle);
            obstacle.onDraw(canvas);
            if (obstacle.getObjectX() + obstacle.getObjectWidth() < 150) {
                iteratorO.remove();
            }
        }
    }

    private void changeSpeed(LeprechaunObject leprechaunObject) {
        if (leprechaun.getObjectX() > 350 && leprechaun.isRunning()) {
            leprechaunObject.setObjectXSpeed(20);
        } else {
            leprechaunObject.setObjectXSpeed(15);
        }
    }

    private void testCollision() {
        Iterator<Obstacle> iteratorObstacle = obstacles.iterator();
        while (iteratorObstacle.hasNext()) {
            Obstacle o = iteratorObstacle.next();
            if (!leprechaun.isRunning()) {
                if (leprechaun.isCollision(o.getLeft() + 60, o.getRight(), o.getTop() + 15)) {
                    leprechaun.setObjectAlive(false);
                    iteratorObstacle.remove();
                    stopGame();
                    break;
                }
            } else {
                if (leprechaun.isCollision(o.getLeft(), o.getRight(), o.getTop())) {
                    leprechaun.setObjectAlive(false);
                    iteratorObstacle.remove();
                    stopGame();
                    break;
                }
            }
        }
        Iterator<Coin> iteratorCoin = coins.iterator();
        while (iteratorCoin.hasNext()) {
            Coin coin = iteratorCoin.next();
            if (leprechaun.isCollision(coin.getLeft(), coin.getRight(), coin.getTop())) {
                iteratorCoin.remove();
                countCoins++;
                break;
            }
        }
    }

    public Leprechaun getLeprechaun() {
        return leprechaun;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        leprechaunThread.setRunning(true);
        leprechaunThread.start();
        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.back);
        float scaleHeight = (float) background.getHeight() / (float) getScreenHeight();
        float scaleWidth = (float) background.getWidth() / (float) getScreenWidth();
        int newWidth;
        int newHeight;
        if (scaleHeight < scaleWidth) {
            newWidth = Math.round(background.getWidth() / scaleHeight);
            newHeight = Math.round(background.getHeight() / scaleHeight);
        } else {
            newWidth = Math.round(background.getWidth() / scaleWidth);
            newHeight = Math.round(background.getHeight() / scaleWidth);
        }
        scaledBack = Bitmap.createScaledBitmap(background, newWidth, newHeight, true);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        leprechaunThread.setRunning(false);
        while (retry) {
            try {
                leprechaunThread.join();
                retry = false;
            } catch (Exception e) {
            }
        }
    }

    public int getScreenWidth() {
        return this.getResources().getDisplayMetrics().widthPixels;
    }

    public int getScreenHeight() {
        return this.getResources().getDisplayMetrics().heightPixels;
    }
}