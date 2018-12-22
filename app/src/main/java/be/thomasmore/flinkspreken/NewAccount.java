package be.thomasmore.flinkspreken;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class NewAccount extends AppCompatActivity {

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Account toevoegen");

        db = new DatabaseHelper(this);
    }

    public void onClickButtonAdd(View v) {
        EditText editTextNaam = (EditText) findViewById(R.id.editName);
        String naam = editTextNaam.getText().toString();

        if (!naam.equals("")) {
            Account account = new Account();
            account.setNaam(naam);
            db.addAccount(account);
        }

        Intent intent = new Intent(this, MainActivity.class);
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
