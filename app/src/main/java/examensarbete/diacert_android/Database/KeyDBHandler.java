package examensarbete.diacert_android.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by backevik on 16-04-13.
 */
public class KeyDBHandler extends DBHandler{

        public static final String TAG ="DB.Key";
        public KeyDBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
            super(context, factory);
        }
        public void addData(String key){
            ContentValues values =  new ContentValues();
            SQLiteDatabase db = getWritableDatabase();
            values.put("_key",key);
            db.insertWithOnConflict("Key",null,values,SQLiteDatabase.CONFLICT_IGNORE);
            db.close();
            Log.i(TAG, "Added 1 row into Key");
        }

        public String getData(){
            String returnKey = "";
            SQLiteDatabase db = getReadableDatabase();
            String query = "SELECT * FROM Key";
            Cursor c = db.rawQuery(query, null);
            c.moveToFirst();
            if(c == null || c.getCount() == 0){
                return "";
            }
            returnKey = c.getString(0);
            c.close();
            db.close();
            Log.i(TAG, "Got 1 row from Key");
            return returnKey;
        }

        public void removeData(String key){
            SQLiteDatabase db = getWritableDatabase();
            int rows = db.delete("Key","_key = " +key, null);
            db.close();
            Log.i(TAG, "Removed "+rows+" rows from Key");
        }
}
