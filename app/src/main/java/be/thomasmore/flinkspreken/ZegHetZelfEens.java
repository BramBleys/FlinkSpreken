package be.thomasmore.flinkspreken;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class ZegHetZelfEens extends AppCompatActivity {

    private Random r = new Random();
    private int imageSwitcher = 0;
    private int image0 = 0;
    private int image1 = 0;
    private int totaalScore = 0;
    private int behaaldeScore = 0;
    private boolean clicked = false;
    EasyFlipView flipViews[] = new EasyFlipView[9];
    EasyFlipView clickedFlipView;
    private String[] woorden;

    private long accountId;
    private String paar;
    private String spel;
    private DatabaseHelper db;

    private String[] goedzo_geluidjes = new String[]{"bravo", "bravo2", "dikke_duim", "dikke_duim2", "goed_gedaan", "goedzo", "super_gedaan"};
    private MediaPlayer mediaPlayer;
    private MediaPlayer instructie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zeg_het_zelf_eens);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Zeg het zelf eens");

        Bundle bundle = getIntent().getExtras();
        accountId = bundle.getLong("id");
        spel = bundle.getString("spel");
        paar = bundle.getString("paar");
        woorden = paar.split("-");

        db = new DatabaseHelper(this);
        mediaPlayer = new MediaPlayer();
        instructie = new MediaPlayer();

        instructie = MediaPlayer.create(getApplicationContext(), R.raw.spel3);
        instructie.start();

        setImages();
    }

    public void setImages() {

        ImageView tekening1 = (ImageView) findViewById(R.id.tekening1);
        ImageView tekening2 = (ImageView) findViewById(R.id.tekening2);

        tekening1.setImageBitmap(decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(woorden[0].toLowerCase(), "drawable", getPackageName()), 200, 200));
        tekening2.setImageBitmap(decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(woorden[1].toLowerCase(), "drawable", getPackageName()), 200, 200));

        tekening1.setTag(woorden[0].toLowerCase());
        tekening2.setTag(woorden[1].toLowerCase());

        tekening1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkJuist((ImageView) v);
            }
        });

        tekening2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkJuist((ImageView) v);
            }
        });


        int k = 0;
        LinearLayout mainlayout = (LinearLayout) findViewById(R.id.main_layout);

        for (int i = 0; i < mainlayout.getChildCount(); i++) {
            LinearLayout sublayout = (LinearLayout) findViewById(getResources().getIdentifier("rij_" + (i + 1), "id", getPackageName()));

            for (int j = 0; j < sublayout.getChildCount(); j++) {
                EasyFlipView flipview = findViewById(getResources().getIdentifier("rij_" + (i + 1) + "_flip" + (j + 1), "id", getPackageName()));

                ImageView imageView =
                        (ImageView) flipview.findViewById(getResources().getIdentifier("rij_" + (i + 1) + "_flip" + (j + 1) + "_back", "id", getPackageName()));

                imageSwitcher = r.nextInt(2);
                if ((imageSwitcher % 2) == 0 && image0 <= 4) {
                    imageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(woorden[0].toLowerCase(), "drawable", getPackageName()), 200, 200));
                    imageView.setTag(woorden[0].toLowerCase());
                    image0++;
                } else {
                    if ((imageSwitcher % 2) == 1 && image1 <= 4) {
                        imageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(woorden[1].toLowerCase(), "drawable", getPackageName()), 200, 200));
                        imageView.setTag(woorden[1].toLowerCase());
                        image1++;
                    } else {
                        imageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(woorden[0].toLowerCase(), "drawable", getPackageName()), 200, 200));
                        imageView.setTag(woorden[0].toLowerCase());
                        image0++;
                    }
                }

                flipview.flipTheView();

                flipview.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (!clicked) {
                            flipViewClicked((EasyFlipView) v);
                        }
                    }
                });

                flipViews[k] = flipview;
                k++;
            }
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public void checkJuist(ImageView v) {

        if (clicked) {
            totaalScore++;

            if (v.getTag().equals(clickedFlipView.getChildAt(1).getTag())) {
                behaaldeScore++;

                if (behaaldeScore == 9) {
                    opslaan();
                    finish();
                    Toast.makeText(getBaseContext(), "Het spel is afgelopen !", Toast.LENGTH_LONG).show();
                }

                clickedFlipView.setFlipEnabled(false);
                clickedFlipView.setOnClickListener(null);

                ImageView view = (ImageView) clickedFlipView.getChildAt(1);
                view.setImageResource(R.drawable.correct);

                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.correctanswerpling);
                mediaPlayer.start();

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        Random random = new Random();
                        int randomIndex = random.nextInt(goedzo_geluidjes.length);
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), getResources().getIdentifier(goedzo_geluidjes[randomIndex], "raw", getPackageName()));
                        mediaPlayer.start();
                    }
                });

            } else {
                clickedFlipView.flipTheView();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bijna_goed_nog_eens);
                mediaPlayer.start();
            }
            clicked = false;
        }
    }

    public void flipViewClicked(EasyFlipView easyFlipView) {
        clicked = true;
        clickedFlipView = easyFlipView;

        String woord = easyFlipView.getChildAt(1).getTag().toString();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), getResources().getIdentifier(woord, "raw", getPackageName()));
        mediaPlayer.start();

        easyFlipView.flipTheView();
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
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                instructie.start();
                return true;
            case android.R.id.home:
                mediaPlayer.stop();
                instructie.stop();
                showAlert();
                return true;
            default:
                return false;
        }

    }
}

