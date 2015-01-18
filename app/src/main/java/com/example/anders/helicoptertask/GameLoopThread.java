package com.example.anders.helicoptertask;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
/**
 * Created by Anders on 12.01.2015.
 */
public class GameLoopThread extends Thread{

    private boolean isrunning = false;
    private GameSurfaceView view;

    public GameLoopThread(GameSurfaceView view) {
        this.view = view;

    }


    @SuppressLint("WrongCall")
    @Override
    public void run(){
        Canvas c = null;
        while(isrunning){
            try{
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()){
                    view.onDraw(c);
                }

            } finally{
                if(c != null){
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
        }
    }


    public void setRunning(boolean isrunning){
        this.isrunning = isrunning;
    }
}
