package be.thomasmore.flinkspreken;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.Serializable;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Account gebruiker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        long gebruikerId = bundle.getLong("gebruiker");
        gebruiker = db.getAccount(gebruikerId);
    }

    public void onClickButtonStart(View v) {
        Intent intent = new Intent(getApplicationContext(), FrontStopActivity.class);
        startActivity(intent);
    }

    public void onClickButtonResultaten(View v) {
        List<Score> scores = db.getScores(gebruiker.getId());

        Bundle bundle = new Bundle();
        bundle.putSerializable("scores", (Serializable) scores);

        Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
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
