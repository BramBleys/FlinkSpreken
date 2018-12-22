package be.thomasmore.flinkspreken;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class LuisterGoedActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private String finaalinitiaal;
    private String klank;
    private String paar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luister_goed);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Luister goed");

        Bundle bundle = getIntent().getExtras();
        finaalinitiaal = bundle.getString("finaalinitiaal");
        klank = bundle.getString("klank");
        paar = bundle.getString("paar");

        mediaPlayer = new MediaPlayer();
        setMediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getBaseContext(), "Het spel is afgelopen !", Toast.LENGTH_LONG).show();
            }
        });
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

            Toast.makeText(getBaseContext(), "Plug je headset in!", Toast.LENGTH_LONG).show();
        }
    }

    public void setMediaPlayer() {
        switch (klank) {
            case "K-T":
                if (finaalinitiaal.equals("finaal")) {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.reeks1);
                } else {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.reeks2);
                }
                break;
            case "G-S":
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.reeks3);
                break;
            case "NG-N":
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.reeks5);
                break;
            case "G-S/V":
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.reeks4);
                break;
            case "S-T":
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.reeks6);
                break;
            case "CH-T":
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.reeks3);
                break;
            case "G-K":
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.reeks4);
                break;
            case "S/Z-T":
                if (paar.equals("Sok-tok")) {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.reeks7);
                } else {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.reeks8);
                }
                break;
            case "F-T":
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.reeks9);
                break;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }
}
