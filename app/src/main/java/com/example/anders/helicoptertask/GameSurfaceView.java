package com.example.anders.helicoptertask;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Anders on 12.01.2015.
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private List<Helicopter> helicopterList = new ArrayList<>();

    public GameSurfaceView(MainActivity context) {
        super(context);
        this.holder = getHolder();
        this.gameLoopThread = new GameLoopThread(this);

        //Creating objects
        createObjects();

        //Using callback to make sure the surface is available
        // and that the canvas can be drawn on.
        holder.addCallback(this);


    }

    public void createObjects(){
        Helicopter helicopter = new Helicopter(100,100,this);
        helicopterList.add(helicopter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (getHolder()) {
            for(Helicopter helicopter: helicopterList){
                helicopter.moveHelicopter(event);
            }
        }
        return true;
    }


    @Override
    public void onDraw(Canvas canvas){
        canvas.drawColor(Color.argb(255, 254, 0, 254));

        for(Helicopter helicopter : helicopterList){
            helicopter.onDraw(canvas);
        }

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        for(Helicopter helicopter : helicopterList){
            helicopter.setGrid();
        }
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //When the surface is destroyed we stop the running in the GameLoopThread
        gameLoopThread.setRunning(false);
        try {
            gameLoopThread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void pauseAll(){
        gameLoopThread.setRunning(false); //Turns the loop off, when paused,
        while(true){ //Dont need this but nice to have.
            try {
                gameLoopThread.join();
                break;
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void resumeAll(){
        this.gameLoopThread = new GameLoopThread(this);
    }
}
