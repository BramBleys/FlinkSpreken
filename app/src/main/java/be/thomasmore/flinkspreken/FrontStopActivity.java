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
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


public class FrontStopActivity extends AppCompatActivity {

    private long accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_stop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Fronting of stopping?");

        Bundle bundle = getIntent().getExtras();
        accountId = bundle.getLong("id");
    }

    public void onClickButtonInfo(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        final View viewInflater = inflater.inflate(R.layout.info_popup, null);
        LinearLayout first_layout = (LinearLayout) viewInflater.findViewById(R.id.first_layout);
        LinearLayout second_layout = (LinearLayout) viewInflater.findViewById(R.id.second_layout);

        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "opensans_regular.ttf");

        LayoutParams layoutParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 16;
        layoutParams.leftMargin = 4;
        layoutParams.rightMargin = 4;
        layoutParams.bottomMargin = 4;

        TextView first_title = new TextView(this);
        first_title.setText(R.string.fronting);
        first_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
        first_title.setTypeface(typeface);

        TextView first_text = new TextView(this);
        first_text.setText(R.string.info_text_fronting);
        first_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        first_text.setTypeface(typeface);

        TextView second_title = new TextView(this);
        second_title.setText(R.string.stopping);
        second_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
        second_title.setTypeface(typeface);

        TextView second_text = new TextView(this);
        second_text.setText(R.string.info_text_stopping);
        second_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        second_text.setTypeface(typeface);

        first_title.setLayoutParams(layoutParams);
        first_text.setLayoutParams(layoutParams);
        second_title.setLayoutParams(layoutParams);
        second_text.setLayoutParams(layoutParams);

        first_layout.addView(first_title);
        first_layout.addView(first_text);
        second_layout.addView(second_title);
        second_layout.addView(second_text);

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

    public void onClickButtonFronting(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("frontstop", "fronting");
        bundle.putLong("id", accountId);

        Intent intent = new Intent(getApplicationContext(), FinaalInitiaalActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickButtonStopping(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("frontstop", "stopping");
        bundle.putLong("id", accountId);

        Intent intent = new Intent(getApplicationContext(), FinaalInitiaalActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //Back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_menu_item:
                Intent intent = new Intent(getApplicationContext(), Intro.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
}
