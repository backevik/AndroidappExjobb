package examensarbete.diacert_android.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by backevik on 16-04-13.
 */
public class DBHandler  extends SQLiteOpenHelper {

    public static final String TAG ="DB";
    /*BEGIN_SENSORS*/
    String queryKey = "CREATE TABLE "+ Database.KEY.TABLE + "(" +
            Database.KEY.COLUMN_ID + " BIGINT PRIMARY KEY " + ");";

    String queryForm = "CREATE TABLE "+ Database.LOG.TABLE + "(" +
            Database.LOG.COLUMN_DATE + " DATETIME PRIMARY KEY, "+
            Database.LOG.COLUMN_TYPE + " TEXT, " +
            Database.LOG.COLUMN_DATA + " TEXT " + ");";

    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, Database.DB_NAME, factory, Database.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*BEGIN TABLES*/
        db.execSQL(queryKey);
        db.execSQL(queryForm);
        Log.d(TAG, "Tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*BEGIN_SENSORS*/
        db.execSQL("DROP TABLE IF EXISTS " + Database.KEY.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Database.LOG.TABLE);

        onCreate(db);
    }
}
