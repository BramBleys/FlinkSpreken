package be.thomasmore.flinkspreken;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PaarActivity extends AppCompatActivity {

    private long accountId;
    private String frontstop;
    private String finaalinitiaal;
    private String klank;
    private List<String> paren = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kies je paar");

        Bundle bundle = getIntent().getExtras();
        frontstop = bundle.getString("frontstop");
        finaalinitiaal = bundle.getString("finaalinitiaal");
        klank = bundle.getString("klank");
        accountId = bundle.getLong("id");

        vulParenOp();
        maakLayout();
    }

    private void maakLayout() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearlayout);
        List<Integer> ids = Arrays.asList(R.drawable.button_blauw, R.drawable.button_bruin, R.drawable.button_green);
        List<Integer> previousNumbers = new ArrayList<Integer>();
        Random random = new Random();
        int index;
        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "comic_bold.ttf");

        for (int i = 0; i < paren.size(); i++) {
            Button button = new Button(this);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.topMargin = 16;
            layoutParams.leftMargin = 4;
            layoutParams.rightMargin = 4;
            layoutParams.bottomMargin = 4;

            while (true) {
                index = random.nextInt(ids.size());
                if (!previousNumbers.contains(index)) {
                    previousNumbers.add(index);
                    break;
                }
            }

            button.setLayoutParams(layoutParams);
            button.setText(paren.get(i));
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            button.setPadding(10, 10, 10, 10);
            button.setGravity(Gravity.CENTER);
            button.setBackgroundResource(ids.get(index));
            button.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics()));
            button.setTypeface(typeface);

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    buttonClick((Button) v);
                }
            });

            layout.addView(button);
        }
    }

    private void buttonClick(Button button){
        Bundle bundle = new Bundle();
        bundle.putLong("id", accountId);
        bundle.putString("frontstop", frontstop);
        bundle.putString("finaalinitiaal", finaalinitiaal);
        bundle.putString("klank", klank);
        bundle.putString("paar", button.getText().toString());

        Intent intent = new Intent(this, SpelKiezenActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void vulParenOp() {
        switch (klank) {
            case "K-T":
                if (finaalinitiaal.equals("finaal")) {
                    paren.add("Bek-bed");
                    paren.add("Nek-net");
                    paren.add("Bak-bad");

                } else {
                    paren.add("Koe-toe");
                    paren.add("Kou-touw");
                    paren.add("Kam-tam");

                }
                break;
            case "G-S":
                paren.add("Buig-buis");
                paren.add("Leeg-lees");
                paren.add("Dag-das");
                break;
            case "NG-N":
                paren.add("Pang-pan");
                paren.add("Tong-ton");
                break;
            case "G-S/V":
                paren.add("Guus-suus");
                paren.add("Goed-voet");
                paren.add("Goud-fout");
                break;
            case "S-T":
                paren.add("Boos-boot");
                paren.add("Bos-bot");
                break;
            case "CH-T":
                paren.add("Pech-pet");
                break;
            case "G-K":
                paren.add("Gat-kat");
                break;
            case "S/Z-T":
                paren.add("Sok-tok");
                paren.add("Zak-tak");
                break;
            case "F-T":
                paren.add("Fee-thee");
                paren.add("Fien-tien");
                break;
        }
    }

    //Back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
