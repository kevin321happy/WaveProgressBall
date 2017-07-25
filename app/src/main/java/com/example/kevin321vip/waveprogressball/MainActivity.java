package com.example.kevin321vip.waveprogressball;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kevin321vip.waveprogressball.view.WaveProgressBall;

public class MainActivity extends AppCompatActivity {
    private static final int one=100;
    private int progress=0;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress++;
            switch (msg.what){
                case one:
                    if (progress <= 75) {
                        mWave_progress.setCurrent(progress, progress + "%");
                        sendEmptyMessageDelayed(one, 50);
                    }
                    break;
            }
        }
    };
    private WaveProgressBall mWave_progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWave_progress = (WaveProgressBall) findViewById(R.id.wave_view);
//        new WaveProgressView();

        mWave_progress.setTexrSize(50);

    }

    //点击开始
    public void begin(View view) {
        Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();

        mHandler.sendEmptyMessageDelayed(one, 1000);
    }
}
