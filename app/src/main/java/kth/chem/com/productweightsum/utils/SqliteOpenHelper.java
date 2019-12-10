package kth.chem.com.productweightsum.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliteOpenHelper extends SQLiteOpenHelper {
    private static final String DB = "WeightSum.db";
    private static final String TABLE = "products";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String COUNT = "count";
    private static final String UNIT = "unit";
    private static final String WEIGHT_ONE = "weight_one";

    private static final String createTableQuery = "CREATE TABLE " + TABLE +
            " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + NAME + " TEXT," + COUNT + " INTEGER," + UNIT + "TEXT," + WEIGHT_ONE + " DOUBLE )";

    public SqliteOpenHelper(@Nullable Context context) {
        super(context, DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
    }
}
