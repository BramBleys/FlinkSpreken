package be.thomasmore.flinkspreken;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewAccount extends AppCompatActivity {

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(this);
    }

    public void onClickButtonAdd(View v) {
        EditText editTextNaam = (EditText) findViewById(R.id.editName);
        String naam = editTextNaam.getText().toString();

        Account account = new Account();
        account.setNaam(naam);

        db.addAccount(account);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
