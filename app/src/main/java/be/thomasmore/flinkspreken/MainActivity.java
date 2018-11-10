package be.thomasmore.flinkspreken;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private List<Account> accounts;
    private AdapterInterface listener = new AdapterInterface() {
        @Override
        public void deleteAccount(long id) {
            db.deleteAccount(id);
            getAccounts();
        }
    };

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
        accounts = db.getAccounts();

        AccountAdapter accountAdapter = new AccountAdapter(getApplicationContext(), accounts, listener);
        final ListView listViewAccounts = (ListView) findViewById(R.id.accounts);

        listViewAccounts.setAdapter(accountAdapter);

        listViewAccounts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parentView, View childView, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                intent.putExtra("gebruiker", accounts.get(position).getId());
                startActivity(intent);
            }
        });

    }

    public void onResume() {
        super.onResume();
        getAccounts();
    }
}
