package com.example.anders.helicoptertask;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
public class MainActivity extends Activity {

    private GameSurfaceView gameSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        gameSurfaceView = new GameSurfaceView(this);
        setContentView(gameSurfaceView);
    }

    @Override
    public void onPause() {
        super.onPause();
        gameSurfaceView.pauseAll();

    }

    @Override
    public void onResume() {
        super.onResume();
        gameSurfaceView.resumeAll();
    }
}
