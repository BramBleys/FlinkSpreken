package be.thomasmore.flinkspreken;

import android.content.Context;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.time.Duration;

public class LuisterGoedActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luister_goed);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.audio_test);
    }

    public void playAudio(View v) {
        AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (manager.isWiredHeadsetOn()) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        } else {
            ImageView headsetImage = (ImageView) findViewById(R.id.headset_image);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
            headsetImage.startAnimation(vibrateAnimation);

            Toast.makeText(getBaseContext(), "Plug je headset in!", Toast.LENGTH_SHORT).show();
        }
    }

    //Back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mediaPlayer.stop();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
