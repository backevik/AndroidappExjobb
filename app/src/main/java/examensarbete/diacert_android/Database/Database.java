package examensarbete.diacert_android.Database;

import android.provider.BaseColumns;

/**
 * Created by backevik on 16-04-13.
 */
public class Database {

    public static final int VERSION = 3;
    public static final String DB_NAME = "ptj";

    public class KEY extends Database implements BaseColumns {
        public static final String TABLE = "Key";
        public static final String COLUMN_ID = "_key";
    }
}
