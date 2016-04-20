package examensarbete.diacert_android.Database;

import android.provider.BaseColumns;

/**
 * Created by backevik on 16-04-13.
 */
public class Database {

    public static final int VERSION = 5;
    public static final String DB_NAME = "ptj";

    public class KEY extends Database implements BaseColumns {
        public static final String TABLE = "Key";
        public static final String COLUMN_ID = "_key";
    }

    public class LOG extends Database implements BaseColumns {
        public static final String TABLE = "Log";
        public static final String COLUMN_DATE = "_date";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_DATA = "data";
    }
}
