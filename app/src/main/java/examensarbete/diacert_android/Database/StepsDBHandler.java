package examensarbete.diacert_android.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;


public class StepsDBHandler extends DBHandler{

    public static final String TAG ="DB.Steps";
    public StepsDBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, factory);
    }
    public void addData(long timeStamp){
        ContentValues values =  new ContentValues();
        values.put("timestamps",timeStamp);
        SQLiteDatabase db = getWritableDatabase();
        db.insertWithOnConflict("Steps",null,values,SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
        Log.i(TAG, "Added 1 row into Key");
    }

    public long getData(){
        long returnTimeStamp;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT MAX(timestamps) AS lastInsert" +
                " FROM Steps";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        returnTimeStamp = c.getLong(0);
        c.close();
        db.close();
        if(returnTimeStamp >0){
            return returnTimeStamp;
        }
        return 0;
    }
}
