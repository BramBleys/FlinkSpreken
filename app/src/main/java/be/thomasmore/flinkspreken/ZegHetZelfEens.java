package be.thomasmore.flinkspreken;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wajahatkarim3.easyflipview.EasyFlipView;

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
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zeg_het_zelf_eens);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        paar = bundle.getString("paar");
        accountId = bundle.getLong("id");
        woorden = paar.split("-");

        db = new DatabaseHelper(this);

        setImages();
    }

    public void setImages() {

        ImageView tekening1 = (ImageView) findViewById(R.id.tekening1);
        ImageView tekening2 = (ImageView) findViewById(R.id.tekening2);

        tekening1.setImageResource(getResources().getIdentifier(woorden[0].toLowerCase(), "drawable", getPackageName()));
        tekening2.setImageResource(getResources().getIdentifier(woorden[1].toLowerCase(), "drawable", getPackageName()));

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
                EasyFlipView flipview = findViewById(getResources().getIdentifier("rij_" + (i+1) + "_flip" + (j + 1), "id", getPackageName()));

                ImageView imageView =
                        (ImageView) flipview.findViewById(getResources().getIdentifier("rij_" + (i+1) + "_flip" + (j + 1) + "_back", "id", getPackageName()));

                imageSwitcher = r.nextInt(2);
                if ((imageSwitcher % 2) == 0 && image0 <= 4){
                    imageView.setImageResource(getResources().getIdentifier(woorden[0].toLowerCase(), "drawable", getPackageName()));
                    imageView.setTag(woorden[0].toLowerCase());
                    image0++;
                }else{
                    if ((imageSwitcher % 2) == 1 && image1 <= 4){
                        imageView.setImageResource(getResources().getIdentifier(woorden[1].toLowerCase(), "drawable", getPackageName()));
                        imageView.setTag(woorden[1].toLowerCase());
                        image1++;
                    }else{
                        imageView.setImageResource(getResources().getIdentifier(woorden[0].toLowerCase(), "drawable", getPackageName()));
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

    public void checkJuist(ImageView v) {
        if (clicked) {
            Log.i("LOGTEST", "Flippedimagetag -> " + clickedFlipView.getChildAt(1).getTag());
            Log.i("LOGTEST", "Juist/Fout knop -> " + v.getTag());
            if (v.getTag().equals(clickedFlipView.getChildAt(1).getTag())) {
                totaalScore++;
                behaaldeScore++;
                clickedFlipView.setFlipEnabled(false);
                ImageView view = (ImageView) clickedFlipView.getChildAt(1);
                view.setImageResource(R.drawable.correct);
                clickedFlipView.setOnClickListener(null);
            } else {
                totaalScore++;
                clickedFlipView.flipTheView();
            }
            clicked = false;
        }
    }

    public void flipViewClicked(EasyFlipView easyFlipView) {
        clicked = true;
        clickedFlipView = easyFlipView;
        easyFlipView.flipTheView();
    }

    public void onClickButtonOpslaan(View v) {
        String score = behaaldeScore + "/" + totaalScore;
        Paar paar = db.getPaar(this.paar.toLowerCase());
        db.insertScore(score, accountId, paar.getId());

        Toast.makeText(getBaseContext(), "Opslaan gelukt!", Toast.LENGTH_SHORT).show();
    }

    //Back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                if (totaalScore > 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setCancelable(false);
                    builder.setMessage("Je score is niet opgeslagen, ben je zeker dat je terug wil gaan ?");
                    builder.setPositiveButton("Toch terug gaan", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //if user pressed "yes", then he is allowed to exit from application
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
                    AlertDialog alert = builder.create();
                    alert.show();
                }else{
                    finish();
                }

                //finish();
                //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}

