package be.thomasmore.flinkspreken;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class HondjeWaf extends AppCompatActivity {
    private long accountId;
    private String paar;
    private String spel;

    private Timer timer;
    private TimerTask timerTask;
    private Handler handler = new Handler();
    private MediaPlayer mediaPlayer;
    private DatabaseHelper db;

    private boolean clicked = false;
    private String[] woorden;
    private String antwoord;
    private int totaalScore = 0;
    private int behaaldeScore = 0;
    private String[] goedzo_geluidjes = new String[]{"bravo", "bravo2", "dikke_duim", "dikke_duim2", "goed_gedaan", "goedzo", "super_gedaan"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hondje_waf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hondje waf!");

        Bundle bundle = getIntent().getExtras();
        paar = bundle.getString("paar");
        accountId = bundle.getLong("id");
        spel = bundle.getString("spel");

        woorden = paar.split("-");
        mediaPlayer = new MediaPlayer();
        db = new DatabaseHelper(this);

        startTimer();
        setImages();
    }

    public void onClickButtonBot(View v) {
        clicked = true;
        stopTimer();

        int index = (int) (Math.round(Math.random()));
        antwoord = woorden[index].toLowerCase();

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), getResources().getIdentifier(woorden[index].toLowerCase(), "raw", getPackageName()));
            mediaPlayer.start();
        }
    }

    public void controleerAntwoord(ImageView v) {
        String tag = v.getTag().toString();

        if (clicked) {
            totaalScore++;

            if (tag.equals(antwoord)) {
                behaaldeScore++;

                Random random = new Random();
                int randomIndex = random.nextInt(goedzo_geluidjes.length);

                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bark);
                mediaPlayer.start();

                antwoord = "";
                startTimer();
                clicked = false;
            } else {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bijna_goed_nog_eens);
                mediaPlayer.start();
            }
        }
    }

    public void onClickButtonOpslaan(View v) {
        opslaan();
        finish();
    }

    private void opslaan() {
        String score = behaaldeScore + "/" + totaalScore;
        Paar paar = db.getPaar(this.paar.toLowerCase());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String dateTime = LocalDateTime.now().format(formatter);

        db.insertScore(score, accountId, spel, dateTime, paar.getId());

        Toast.makeText(getBaseContext(), "Opslaan gelukt!", Toast.LENGTH_SHORT).show();
    }

    private void showAlert() {
        if (totaalScore > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Je score is niet opgeslagen, ben je zeker dat je terug wil gaan ?");
            builder.setPositiveButton("Opslaan en terug gaan", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    opslaan();
                    finish();
                }
            });
            builder.setNegativeButton("Annuleer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user select "No", just cancel this dialog and continue with app
                    dialog.cancel();
                }
            });
            builder.setNeutralButton("Niet opslaan", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            finish();
        }
    }

    private void setImages() {
        ImageView tekening1 = (ImageView) findViewById(R.id.tekening1);
        ImageView tekening2 = (ImageView) findViewById(R.id.tekening2);

        tekening1.setImageResource(getResources().getIdentifier(woorden[0].toLowerCase(), "drawable", getPackageName()));
        tekening2.setImageResource(getResources().getIdentifier(woorden[1].toLowerCase(), "drawable", getPackageName()));

        tekening1.setTag(woorden[0].toLowerCase());
        tekening2.setTag(woorden[1].toLowerCase());

        tekening1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controleerAntwoord((ImageView) v);
            }
        });

        tekening2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controleerAntwoord((ImageView) v);
            }
        });

    }

    private void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        schudBot();
                    }
                });
            }
        };
        timer.schedule(timerTask, 1000, 3000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    private void schudBot() {
        if (!clicked) {
            ImageView bot = (ImageView) findViewById(R.id.bot);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
            bot.startAnimation(vibrateAnimation);
        }
    }

    @Override
    public void onBackPressed() {
        showAlert();
    }

    //Back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showAlert();
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
