package examensarbete.diacert_android.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by backevik on 16-04-19.
 */
public class FormDBHandler extends DBHandler {

    public static final String TAG ="DB.Form";

    public FormDBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, factory);
    }

    public void addData(String date, String type, String data){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        values.put("_date", date);
        values.put("type", type);
        values.put("data", data);
        db.insertWithOnConflict("Form",null,values,SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
        Log.i(TAG, "Added 1 row into Form");
    }
}
