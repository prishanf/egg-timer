package com.example.prishanf.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView countDownTextView;
    CountDownTimer countDownTimer;
    Boolean counterIsActive = false;
    Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        countDownTextView = findViewById(R.id.countdownTextView);
        goButton = findViewById(R.id.goButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(5);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void resetTimer() {
        countDownTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("GO!");
        counterIsActive = false;
    }

    public void updateTimer(int secLeft){

        int min = secLeft/60;
        int sec = secLeft - (min * 60);
        String secText = String.valueOf(sec);
        if(sec <= 9){
            secText = "0"+ String.valueOf(sec);
        }
        countDownTextView.setText(String.valueOf(min) +":"+ secText);

    }

    public void buttonClick(View view){
        if(counterIsActive){
            resetTimer();
        }else{

            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            goButton.setText("STOP!");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress()*1000 +100,1000){

                @Override
                public void onTick(long l) {
                    updateTimer((int)l/1000);
                }

                @Override
                public void onFinish() {
                    Log.i("Finished", "Timer All Done");
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                    mPlayer.start();
                    resetTimer();
                }
            }.start();
        }

    }
}
