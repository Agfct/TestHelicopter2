package com.example.anders.helicoptertask;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Created by Anders on 12.01.2015.
 */
public class Helicopter {

    private Bitmap sprite;
    private int x;
    private int y;
    private int speedX;
    private int speedY;
    private int rows = 2;
    private int col = 4;
    private int height;
    private int width;
    private int currentFrame = 0;
    private GameSurfaceView gameSurfaceView;
    private int curDir = 0;
    private int goToX = 0;
    private int goToY = 0;
    private Point[][] grid;
    private boolean controlled = false;
    private List<Point> line = new ArrayList<Point>();
    private int animationRow = 0;


    public Helicopter (int x, int y, GameSurfaceView view){
        this.gameSurfaceView = view;
        this.x = x;
        this.y = y;
        sprite = BitmapFactory.decodeResource(view.getResources(),R.drawable.helicopter);
        this.height = sprite.getHeight()/ rows;
        this.width = sprite.getWidth() / col;


        //TASK 1 Random start values
        Random rnd = new Random();
        speedX = rnd.nextInt(10)-5;
        speedY = rnd.nextInt(10) -5;
        if(speedX >0){
            animationRow = 1;
        }else {
            animationRow = 0;
        }
    }

    public void setGrid(){
        this.grid = new Point [gameSurfaceView.getWidth()][gameSurfaceView.getHeight()];
        for (int i = 0; i < gameSurfaceView.getWidth(); i++)
            for (int j = 0; j < gameSurfaceView.getHeight(); j++)
                grid[i][j] = new Point(i, j);
    }

    private void update() {

        if(!controlled) {
            //When you hit right or left
            if (x >= gameSurfaceView.getWidth() - width - speedX || x + speedX <= 0) {
                if(x >= gameSurfaceView.getWidth()-width - speedX){
                    Log.e("if", "anim row 0");
                    animationRow = 0;
                }else{
                    Log.e("else", "anim row 1");
                    animationRow = 1;
                }
                speedX = -speedX;

            }
            x = x + speedX;
            if (y >= gameSurfaceView.getHeight() - height - speedY || y + speedY <= 0) {
                speedY = -speedY;
            }
            y = y + speedY;
        }else{
            if(line.size() > 0){
                Point tempPoint = line.remove(0);
                if(line.size() > 0){
                Point tempPoint2 = line.remove(0);
                }

                if (!(x >= gameSurfaceView.getWidth() - width - speedX || x + speedX <= 0)){
                    speedX = tempPoint.x - getX();
                    if(speedX >0){
                        animationRow = 1;
                    }else {
                        animationRow = 0;
                    }
                    setX(tempPoint.x);
                }
                if (!(y >= gameSurfaceView.getHeight() - height - speedY || y + speedY <= 0)) {
                    speedY = tempPoint.y - getY();
                    setY(tempPoint.y);
                }
            }else {
                controlled = false;
            }
        }
        currentFrame = ++currentFrame % col;
    }


    //Draws the Helicopter to the screens canvas.
    public void onDraw(Canvas canvas){
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        //This is the spot in the bitmap that we are currently drawing
        Rect src = new Rect(srcX, srcY, srcX+width,srcY+height);
        //This is the spot in the canvas we are drawing it to
        Rect dst = new Rect(x, y, x+width, y+height);
        canvas.drawBitmap(sprite, src, dst, null);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(paint.getTextSize()*2);
        canvas.drawText("x: "+x + "y: "+y,10,20,paint);
    }


    private int getAnimationRow() {
        return animationRow;
    }

    public void moveHelicopter(MotionEvent event){
        controlled = true;
        goToX = Math.round(event.getX());
        goToY = Math.round(event.getY());

        line = findLine(grid, x,y,goToX,goToY);
    }

    public List<Point> findLine(Point[][] grid, int x0, int y0, int x1, int y1) {
        List<Point> line = new ArrayList<Point>();
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx-dy;
        int e2;

        while (true)
        {
            line.add(grid[x0][y0]);
            if (x0 == x1 && y0 == y1)
                break;
            e2 = 2 * err;
            if (e2 > -dy)
            {
                err = err - dy;
                x0 = x0 + sx;
            }
            if (e2 < dx)
            {
                err = err + dx;
                y0 = y0 + sy;
            }
        }
        return line;
    }

    public int getSpeedX() {
        return speedX;
    }
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }
    public int getSpeedY() {
        return speedY;
    }
    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
}
