package com.example.kevin321vip.waveprogressball;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kevin321vip.waveprogressball.view.WaveProgressBall;

public class MainActivity extends AppCompatActivity {
    private static final int one=1;
    private int progress;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress++;
            switch (msg.what){
                case one:
                    if (progress <= 50) {
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
        mWave_progress.setCurrent(progress, "当前排名 ："+progress);
        mWave_progress.setTexrSize(50);

//        waveProgressbar2.setCurrent(77, "788M/1024M");
//        waveProgressbar2.setWaveColor("#5b9ef4");
//        waveProgressbar2.setText("#FFFF00", 41);
//        mWave_progress.setWaveColor("#00C799");
    }

    //点击开始
    public void begin(View view) {
        Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();

        mHandler.sendEmptyMessageDelayed(one, 1000);
    }
}
