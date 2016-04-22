package examensarbete.diacert_android.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by backevik on 16-04-19.
 */
public class LogDBHandler extends DBHandler {

    public static final String TAG ="DB.Form";

    public LogDBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, factory);
    }

    public void addData(long date, String type, String data){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        values.put("_date", date/1000);
        values.put("type", type);
        values.put("data", data);
        db.insertWithOnConflict("Log",null,values,SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
        Log.i(TAG, "Added 1 row into Log");
    }

    public HashMap getData(){
        HashMap<Long, ArrayList<String>> tempMap = new HashMap<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM Log";
        final Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        if(c == null || c.getCount() == 0){
            return new HashMap();
        }
        while(!c.isAfterLast()){
            tempMap.put(c.getInt(0)*1000L,new ArrayList<String>(){{
                add(c.getString(1));
                add(c.getString(2));
            }});
            c.moveToNext();
        }
        c.close();
        db.close();
        Log.i(TAG, "Got "+tempMap.size()+" row from Key");
        return tempMap;
    }

    public void removeData(int key){
        SQLiteDatabase db = getWritableDatabase();
        String[] args = new String[]{key+""};
        int rows = db.delete("Key","_key = ?", args);
        db.close();
        Log.i(TAG, "Removed "+rows+" rows from Key");
    }
}
