package com.n0ize.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.n0ize.wait.WaitLayout;
import com.n0ize.wait.events.OnConnectedListener;
import com.n0ize.wait.models.State;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "wait.demo";
    private WaitLayout waitLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        waitLayout = (WaitLayout) findViewById(R.id.wait_layout);

        waitLayout.setOnConnectedListener(new OnConnectedListener() {
            @Override
            public void OnConnected() {
                Log.i(TAG, "MainActivity.OnConnected: ");
            }
        });
    }

    public void loading(View view) {
        waitLayout.setState(State.LOADING);
    }

    public void working(View view) {
        waitLayout.setState(State.WORKING);
    }

    public void retry(View view) {
        waitLayout.setState(State.RETRY);
    }

    public void none(View view) {
        waitLayout.setState(State.NONE);
    }

    public void test(View view) {
        Toast.makeText(this, "im here as always.", Toast.LENGTH_SHORT).show();
    }
}
