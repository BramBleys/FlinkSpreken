package be.thomasmore.flinkspreken;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Intro extends AppCompatActivity {
    private Timer timer;
    private TimerTask timerTask;
    private Handler handler = new Handler();

    private Animation vibrateAnimation;
    private ImageView varken;
    private ImageView oor;
    private ImageView nijlpaard;
    private ImageView hond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        vibrateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        hond = (ImageView) findViewById(R.id.hond);
        nijlpaard = (ImageView) findViewById(R.id.nijlpaard);
        oor = (ImageView) findViewById(R.id.oor);
        varken = (ImageView) findViewById(R.id.varken);

        startTimer();
    }

    public void start(View v){
        stopTimer();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void schud(){
        varken.startAnimation(vibrateAnimation);
        oor.startAnimation(vibrateAnimation);
        nijlpaard.startAnimation(vibrateAnimation);
        hond.startAnimation(vibrateAnimation);
    }

    private void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        schud();
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 2000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }
}
