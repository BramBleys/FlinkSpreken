package be.thomasmore.flinkspreken;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        getAccounts();
    }

    public void onClickNewAccount(View v) {
        Intent intent = new Intent(this, NewAccount.class);
        startActivity(intent);
    }

    private void getAccounts() {
        final List<Account> accounts = db.getAccounts();

        ArrayAdapter<Account> adapter = new ArrayAdapter<Account>(this, android.R.layout.simple_list_item_1, accounts);
        final ListView listViewAccounts = (ListView) findViewById(R.id.accounts);
        listViewAccounts.setAdapter(adapter);
    }

    public void onResume() {
        super.onResume();
        getAccounts();
    }
}
