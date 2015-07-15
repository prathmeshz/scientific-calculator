package nyc.c4q.sufeiiz.scientificcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sufeizhao on 7/15/15.
 */
public class SQLOpenHelper extends SQLiteOpenHelper{

    private static final String DB = "answersDB";
    private static final int VERSION = 1;

    public SQLOpenHelper(Context context) {
        super(context, DB, null, VERSION);
    }

    private static SQLOpenHelper INSTANCE;

    public static synchronized SQLOpenHelper getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SQLOpenHelper(context.getApplicationContext());

        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public static abstract class DataEntry implements BaseColumns {
        public static final String TABLE_NAME = "calcAnswers";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_LAST_ANSWER= "lastAsnwer";
        public static final String COLUMN_CURRENT_INPUT = "currNumInput";
        public static final String COLUMN_LAST_INPUT = "lastNumInput";
    }

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
            DataEntry.TABLE_NAME + " (" +
            DataEntry._ID + " INTEGER PRIMARY KEY," +
            DataEntry.COLUMN_TIME + " INTEGER," +
            DataEntry.COLUMN_LAST_ANSWER + " INTEGER," +
            DataEntry.COLUMN_CURRENT_INPUT+ " INTEGER," +
            DataEntry.COLUMN_LAST_INPUT + " INTEGER" + ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DataEntry.TABLE_NAME;

    public void insertData(List<String[]> insert) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DataEntry.TABLE_NAME, null, null);

        //TODO insertROW
    }

    public void insertRow(double[] data) {
        SQLiteDatabase db = getWritableDatabase();
        long time = System.currentTimeMillis();

        ContentValues values = new ContentValues();
        values.put(DataEntry.COLUMN_TIME, time);
        values.put(DataEntry.COLUMN_LAST_ANSWER, 1);
        values.put(DataEntry.COLUMN_CURRENT_INPUT, 1);
        values.put(DataEntry.COLUMN_LAST_INPUT, 1 );

        db.insertOrThrow(DataEntry.TABLE_NAME, null, values);
    }

    public List<Double> loadLastAnswer() {
        SQLiteDatabase db = getWritableDatabase();
        String[] projection = {
                DataEntry.COLUMN_TIME,
                DataEntry.COLUMN_LAST_ANSWER,
                DataEntry.COLUMN_CURRENT_INPUT,
                DataEntry.COLUMN_LAST_INPUT
        };

        List<Double> data = new ArrayList<>();

        Cursor cursor = db.query(
                DataEntry.TABLE_NAME, projection, null, null, null, null, DataEntry.COLUMN_TIME);

        while (cursor.moveToNext()) {
            data.add((double) cursor.getInt(cursor.getColumnIndex(DataEntry.COLUMN_LAST_ANSWER)));
        }

        cursor.close();
        return data;
    }

    public double loadCurrentNum() {
     return 0;
    }
}
