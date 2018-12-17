package be.thomasmore.flinkspreken;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private List<Score> scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Je resultaten");

        toonScores();
    }

    private void toonScores() {
        Bundle bundle = getIntent().getExtras();
        scores = (List<Score>) bundle.getSerializable("scores");

        ResultAdapter adapter = new ResultAdapter(getApplicationContext(), scores);
        final ListView listViewScores = (ListView) findViewById(R.id.listview_scores);

        listViewScores.setAdapter(adapter);
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
