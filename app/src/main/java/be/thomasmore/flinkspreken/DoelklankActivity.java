package be.thomasmore.flinkspreken;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DoelklankActivity extends AppCompatActivity {

    private String frontstop;
    private String finaalinitiaal;
    private List<String> klanken = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doelklank);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        frontstop = bundle.getString("frontstop");
        finaalinitiaal = bundle.getString("finaalinitiaal");

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

        for (int i = 0; i < klanken.size(); i++) {
            Button button = new Button(this);

            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.topMargin = 16;
            layoutParams.leftMargin = 4;
            layoutParams.rightMargin = 4;
            layoutParams.bottomMargin = 4;

            button.setLayoutParams(layoutParams);
            button.setText(klanken.get(i));
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            button.setPadding(10, 10, 10, 10);
            button.setGravity(Gravity.CENTER);
            button.setBackgroundResource(R.drawable.button_green);
            button.setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics()));

//            TypedValue value = new TypedValue();
//            getApplicationContext().getTheme().resolveAttribute(R.style.Widget_AppCompat_Button_Borderless, value, true);
//            button.setBackgroundResource(value.resourceId);

            layout.addView(button);
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
