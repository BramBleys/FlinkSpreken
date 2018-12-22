package be.thomasmore.flinkspreken;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SpelKiezenActivity extends AppCompatActivity {

    private long accountId;
    private String frontstop;
    private String finaalinitiaal;
    private String klank;
    private String paar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spel_kiezen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kies een spel");

        Bundle bundle = getIntent().getExtras();
        frontstop = bundle.getString("frontstop");
        finaalinitiaal = bundle.getString("finaalinitiaal");
        klank = bundle.getString("klank");
        paar = bundle.getString("paar");
        accountId = bundle.getLong("id");
    }

    public void onClickButtonInfo(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();


        final View viewInflater = inflater.inflate(R.layout.info_popup, null);
        LinearLayout first_layout = (LinearLayout) viewInflater.findViewById(R.id.first_layout);
        LinearLayout second_layout = (LinearLayout) viewInflater.findViewById(R.id.second_layout);
        LinearLayout third_layout = (LinearLayout) viewInflater.findViewById(R.id.third_layout);

        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "opensans_regular.ttf");

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 16;
        layoutParams.leftMargin = 4;
        layoutParams.rightMargin = 4;
        layoutParams.bottomMargin = 4;

        TextView first_title = new TextView(this);
        first_title.setText(R.string.luistergoed);
        first_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
        first_title.setTypeface(typeface);

        TextView first_text = new TextView(this);
        first_text.setText(R.string.info_text_luistergoed);
        first_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        first_text.setTypeface(typeface);

        TextView second_title = new TextView(this);
        second_title.setText(R.string.zeghetzelfeens);
        second_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
        second_title.setTypeface(typeface);

        TextView second_text = new TextView(this);
        second_text.setText(R.string.info_text_zeghetzelfeens);
        second_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        second_text.setTypeface(typeface);

        TextView third_title = new TextView(this);
        third_title.setText(R.string.hondjewaf);
        third_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
        third_title.setTypeface(typeface);

        TextView third_text = new TextView(this);
        third_text.setText(R.string.info_text_hondjewaf);
        third_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        third_text.setTypeface(typeface);

        first_title.setLayoutParams(layoutParams);
        first_text.setLayoutParams(layoutParams);
        second_title.setLayoutParams(layoutParams);
        second_text.setLayoutParams(layoutParams);
        third_title.setLayoutParams(layoutParams);
        third_text.setLayoutParams(layoutParams);

        first_layout.addView(first_title);
        first_layout.addView(first_text);
        second_layout.addView(second_title);
        second_layout.addView(second_text);
        third_layout.addView(third_title);
        third_layout.addView(third_text);

        builder.setTitle(R.string.info_popup_title)
                .setIcon(R.drawable.info_icon)
                .setView(viewInflater)
                .setPositiveButton(R.string.info_popup_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onClickButtonLuisterGoed(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("finaalinitiaal", finaalinitiaal);
        bundle.putString("klank", klank);
        bundle.putString("paar", paar);

        Intent intent = new Intent(this, LuisterGoedActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void onClickButtonZegHetZelfEens(View view) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", accountId);
        bundle.putString("paar", paar);
        bundle.putString("spel", "Zeg het zelf eens");

        Intent intent = new Intent(this, ZegHetZelfEens.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickButtonHondjeWaf(View v) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", accountId);
        bundle.putString("paar", paar);
        bundle.putString("spel", "Hondje waf");

        Intent intent = new Intent(this, HondjeWaf.class);
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
