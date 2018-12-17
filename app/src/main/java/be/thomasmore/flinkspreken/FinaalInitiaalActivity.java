package be.thomasmore.flinkspreken;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;


public class FinaalInitiaalActivity extends AppCompatActivity {
    private long accountId;
    private String frontstop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finaal_initiaal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Finaal of Initaal?");

        Bundle bundle = getIntent().getExtras();
        frontstop = bundle.getString("frontstop");
        accountId = bundle.getLong("id");
    }

    public void onClickButtonInfo(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        final View viewInflater = inflater.inflate(R.layout.info_popup, null);
        LinearLayout first_layout = (LinearLayout) viewInflater.findViewById(R.id.first_layout);
        LinearLayout second_layout = (LinearLayout) viewInflater.findViewById(R.id.second_layout);

        LayoutParams layoutParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 16;
        layoutParams.leftMargin = 4;
        layoutParams.rightMargin = 4;
        layoutParams.bottomMargin = 4;

        TextView first_title = new TextView(this);
        first_title.setText(R.string.finaal);
        first_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);

        TextView first_text = new TextView(this);
        first_text.setText(R.string.info_text_finaal);

        TextView second_title = new TextView(this);
        second_title.setText(R.string.initiaal);
        second_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);

        TextView second_text = new TextView(this);
        second_text.setText(R.string.info_text_initiaal);

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

    public void onClickButtonFinaal(View v) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", accountId);
        bundle.putString("frontstop", frontstop);
        bundle.putString("finaalinitiaal", "finaal");

        Intent intent = new Intent(getApplicationContext(), DoelklankActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickButtonInitiaal(View v) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", accountId);
        bundle.putString("frontstop", frontstop);
        bundle.putString("finaalinitiaal", "initiaal");

        Intent intent = new Intent(getApplicationContext(), DoelklankActivity.class);
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
