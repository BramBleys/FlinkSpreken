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


        toonScores();
    }

    private void toonScores() {
        Bundle bundle = getIntent().getExtras();
        Bundle bundleScores = bundle.getBundle("bundle");
        scores = (List<Score>) bundleScores.getSerializable("scores");

        List<Long> scoreStrings = new ArrayList<Long>();
        for (int i = 0; i < scores.size(); i++) {
            scoreStrings.add(scores.get(i).getScore());
        }

        ArrayAdapter<Long> adapter = new ArrayAdapter<Long>(this, android.R.layout.simple_list_item_1, scoreStrings);

        ListView listViewScores = (ListView) findViewById(R.id.listview_accounts);
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
