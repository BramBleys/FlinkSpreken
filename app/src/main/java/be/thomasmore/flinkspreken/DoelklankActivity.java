package be.thomasmore.flinkspreken;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DoelklankActivity extends AppCompatActivity {

    private long accountId;
    private String frontstop;
    private String finaalinitiaal;
    private List<String> klanken = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doelklank);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kies je doelklank");

        Bundle bundle = getIntent().getExtras();
        frontstop = bundle.getString("frontstop");
        finaalinitiaal = bundle.getString("finaalinitiaal");
        accountId = bundle.getLong("id");

        if (frontstop.equals("fronting")) {
            if (finaalinitiaal.equals("finaal")) {
                klanken.add("K-T");
                klanken.add("G-S");
                klanken.add("NG-N");
            } else {
                klanken.add("K-T");
                klanken.add("G-S/V");
            }
        } else {
            if (finaalinitiaal.equals("finaal")) {
                klanken.add("S-T");
                klanken.add("CH-T");
            } else {
                klanken.add("G-K");
                klanken.add("S/Z-T");
                klanken.add("F-T");
            }
        }

        maakLayout();
    }

    private void maakLayout() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearlayout);
        List<Integer> ids = Arrays.asList(R.drawable.button_blauw, R.drawable.button_bruin, R.drawable.button_green);
        List<Integer> previousNumbers = new ArrayList<Integer>();
        Random random = new Random();
        int index;

        for (int i = 0; i < klanken.size(); i++) {
            Button button = new Button(this);

            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
            button.setText(klanken.get(i));
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            button.setPadding(10, 10, 10, 10);
            button.setGravity(Gravity.CENTER);
            button.setBackgroundResource(ids.get(index));
            button.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics()));

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    buttonClick((Button) v);
                }
            });

            layout.addView(button);
        }
    }

    private void buttonClick(Button button) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", accountId);
        bundle.putString("frontstop", frontstop);
        bundle.putString("finaalinitiaal", finaalinitiaal);
        bundle.putString("klank", button.getText().toString());

        Intent intent = new Intent(this, PaarActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
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
