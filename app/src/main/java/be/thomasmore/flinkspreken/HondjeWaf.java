package be.thomasmore.flinkspreken;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hondje_waf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hondje waf");

        Bundle bundle = getIntent().getExtras();
        paar = bundle.getString("paar");
        accountId = bundle.getLong("id");
        spel = bundle.getString("spel");

        woorden = paar.split("-");
        mediaPlayer = new MediaPlayer();
        db = new DatabaseHelper(this);

        startTimer();
        setImages();

        if (savedInstanceState != null) {
            clicked = savedInstanceState.getBoolean("clicked");
            totaalScore = savedInstanceState.getInt("totaalScore");
            behaaldeScore = savedInstanceState.getInt("behaaldeScore");
            antwoord = savedInstanceState.getString("antwoord");
        }
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

    private void opslaan() {
        if (totaalScore != 0) {
            String score = behaaldeScore + "/" + totaalScore;
            Paar paar = db.getPaar(this.paar.toLowerCase());

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String date = df.format(Calendar.getInstance().getTime());

            db.insertScore(score, accountId, spel, date, paar.getId());

            Toast.makeText(getBaseContext(), "Opslaan gelukt!", Toast.LENGTH_SHORT).show();
        }
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

        tekening1.setImageBitmap(decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(woorden[0].toLowerCase(), "drawable", getPackageName()), 200, 200));
        tekening2.setImageBitmap(decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(woorden[1].toLowerCase(), "drawable", getPackageName()), 200, 200));

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

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    @Override
    public void onBackPressed() {
        showAlert();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opslaan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opslaan_menu_item:
                this.opslaan();
                finish();
                return true;
            case R.id.instructie_menu_item:
                //audio afspelen
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("clicked", clicked);
        outState.putInt("totaalScore", totaalScore);
        outState.putInt("behaaldeScore", behaaldeScore);
        outState.putString("antwoord", antwoord);

        super.onSaveInstanceState(outState);
    }
}
