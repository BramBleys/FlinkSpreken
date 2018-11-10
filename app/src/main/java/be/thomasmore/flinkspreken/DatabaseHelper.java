package be.thomasmore.flinkspreken;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "flinkSpreken";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_ACCOUNTS = "CREATE TABLE accounts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "naam TEXT)";
        db.execSQL(CREATE_TABLE_ACCOUNTS);

        String CREATE_TABLE_PAREN = "CREATE TABLE paren (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "naam TEXT)";
        db.execSQL(CREATE_TABLE_PAREN);

        String CREATE_TABLE_SCORES = "CREATE TABLE scores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "score INTEGER," +
                "accountId INTEGER," +
                "paarId INTEGER," +
                "FOREIGN KEY (accountId) REFERENCES accounts(id)," +
                "FOREIGN KEY (paarId) REFERENCES paren(id))";
        db.execSQL(CREATE_TABLE_SCORES);

        insertParen(db);
        insertAccounts(db);
        insertScores(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS accounts");
        db.execSQL("DROP TABLE IF EXISTS paren");
        db.execSQL("DROP TABLE IF EXISTS scores");

        onCreate(db);
    }

    private void insertParen(SQLiteDatabase db) {
        db.execSQL("INSERT INTO paren (naam) VALUES ('bek-bed');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('nek-net');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('bak-bad');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('buig-buis');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('leeg-lees');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('dag-das');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('pang-pan');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('tong-ton');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('koe-toe');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('kou-touw');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('kam-tam');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('guus-suus');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('goed-voet');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('goud-fout');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('boos-boot');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('bos-bot');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('pech-pet');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('gat-kat');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('sok-tok');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('zak-tak');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('fee-thee');");
        db.execSQL("INSERT INTO paren (naam) VALUES ('fien-tien');");
    }

    //testen van scores ophalen
    private void insertAccounts(SQLiteDatabase db) {
        db.execSQL("INSERT INTO accounts (naam) VALUES ('Bram');");
        db.execSQL("INSERT INTO accounts (naam) VALUES ('Kilian');");
        db.execSQL("INSERT INTO accounts (naam) VALUES ('Joske');");
    }

    private void insertScores(SQLiteDatabase db) {
        db.execSQL("INSERT INTO scores (score, accountId, paarId) VALUES (5,1,2);");
        db.execSQL("INSERT INTO scores (score, accountId, paarId) VALUES (10,1,5);");
        db.execSQL("INSERT INTO scores (score, accountId, paarId) VALUES (6,1,3);");
        db.execSQL("INSERT INTO scores (score, accountId, paarId) VALUES (4,2,5);");
        db.execSQL("INSERT INTO scores (score, accountId, paarId) VALUES (8,2,2);");
        db.execSQL("INSERT INTO scores (score, accountId, paarId) VALUES (6,2,4);");
    }

    //-------------------------------------------------------------------------------------------------
    //  CRUD Operations
    //-------------------------------------------------------------------------------------------------

    public long insertScore(long score, long accountId, long paarId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("score", score);
        values.put("accountId", accountId);
        values.put("paarId", paarId);

        long id = db.insert("scores", null, values);

        db.close();
        return id;
    }

    public boolean deleteScore(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int numrows = db.delete(
                "scores",
                "id = ?",
                new String[]{String.valueOf(id)});

        db.close();
        return numrows > 0;
    }

    public List<Score> getScores(long accountId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Score> lijst = new ArrayList<Score>();

        Cursor cursor = db.query(
                "scores",
                new String[]{"id", "score", "accountId", "paarId"},
                "accountId = ?",
                new String[]{String.valueOf(accountId)},
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                Score score = new Score(Long.parseLong(cursor.getString(0)),
                        Long.parseLong(cursor.getString(1)), Long.parseLong(cursor.getString(2)), Long.parseLong(cursor.getString(3)));
                lijst.add(score);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    public List<Account> getAccounts() {
        List<Account> lijst = new ArrayList<Account>();

        String selectQuery = "SELECT  * FROM accounts ORDER BY id";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Account account = new Account(cursor.getLong(0), cursor.getString(1));
                lijst.add(account);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    public Account getAccount(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "accounts",
                new String[]{"id", "naam"},
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        Account account = new Account();

        if (cursor.moveToFirst()) {
            account = new Account(cursor.getLong(0), cursor.getString(1));
        }

        cursor.close();
        db.close();
        return account;
    }

    public long addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("naam", account.getNaam());

        long id = db.insert("accounts", null, values);

        db.close();
        return id;
    }

    public boolean deleteAccount(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int numrows = db.delete(
                "accounts",
                "id = ?",
                new String[]{String.valueOf(id)});

        db.close();
        return numrows > 0;
    }
}
