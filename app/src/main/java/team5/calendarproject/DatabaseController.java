package team5.calendarproject;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by edward on 4/2/16.
 */
public final class DatabaseController {

    //Database Name and Version Number. Change V# if you add new columns
    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final int DATABASE_VERSION = 1;
    //download and merge changes to update to current db before changing number
    //always save work! GitHub can be evil.

    //Separators for SQL Creation
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String LONG_TYPE = " LONG";
    private static final String DOUBLE_TYPE = " DOUBLE";
    private static final String COMMA_SEP = ",";



    //TODO: This is where we enter in information for database

    //LOGIN INFORMATION TABLE
    private static abstract class EventEntries implements BaseColumns {
        public static final String TABLE_NAME = "Passwords";
        public static final String _ID = "ID";
        public static final String NAME = "Name";
        public static final String EMAIL = "Email";
        public static final String PASSWORD = "Password";
        public static final String SECRET_QUESTION = "SecretQuestion";
        public static final String SECRET_ANSWER = "SecretAnswer";
        public static final String APP_ID = "AppID";
        public static final String[] ALL_COLUMNS =
                {_ID, NAME, EMAIL, PASSWORD, SECRET_QUESTION, SECRET_ANSWER, APP_ID};
    }

    private static final String SQL_CREATE_LOGIN_ENTRIES =
            "CREATE TABLE " + EventEntries.TABLE_NAME + " (" +
                    EventEntries._ID + " INTEGER PRIMARY KEY," +
                    EventEntries.NAME + TEXT_TYPE + COMMA_SEP +
                    EventEntries.EMAIL + TEXT_TYPE + COMMA_SEP +
                    EventEntries.PASSWORD + TEXT_TYPE + COMMA_SEP +
                    EventEntries.SECRET_QUESTION + INT_TYPE + COMMA_SEP +
                    EventEntries.SECRET_ANSWER + TEXT_TYPE + COMMA_SEP +
                    EventEntries.APP_ID + INT_TYPE

                    + ");";
    private static final String SQL_DELETE_LOGIN_ENTRIES =
            "DROP TABLE IF EXISTS " + EventEntries.TABLE_NAME;

	/*
	 * PUBLIC METHODS
	 */

    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    //public DatabaseContract() {}

    public DatabaseController(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DatabaseController open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }


    //EVENT METHODS
    /**
     * Add a new set of values to the database.
     * @param title
     * @param date
     * @param time
     * @param
     * @param
     * @param
     * @param
     * @param
     * @return The DB table _ID row number.
     */
    public long insertEventRow(String name, String email, String password,
                               int question, String answer)  {
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(EventEntries.NAME, name);
        initialValues.put(EventEntries.EMAIL, email);
        initialValues.put(EventEntries.PASSWORD, password);
        initialValues.put(EventEntries.SECRET_QUESTION, question);
        initialValues.put(EventEntries.SECRET_ANSWER, answer);

        int ID = 0, i = 0;
        for(char letter : email.toCharArray()) {
            ID += letter + i;
            i++;
        }

        initialValues.put(EventEntries.APP_ID, ID);

        // Insert it into the database.
        return db.insert(EventEntries.TABLE_NAME, null, initialValues);
    }

    // Change an existing row to be equal to new data.
    public boolean updateEventRow(long rowId, String name, String email,
                                  String password, int question, String answer) {
        String where = EventEntries._ID + "=" + rowId;
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(EventEntries.NAME, name);
        newValues.put(EventEntries.EMAIL, email);
        newValues.put(EventEntries.PASSWORD, password);
        newValues.put(EventEntries.SECRET_QUESTION, question);
        newValues.put(EventEntries.SECRET_ANSWER, answer);

        // Insert it into the database.
        return db.update(EventEntries.TABLE_NAME, newValues, where, null) != 0;
    }

    // Get a specific row (by rowId)
    public Cursor getEventRow(long rowId) {
        String where = EventEntries._ID + "=" + rowId;
        String[] ALL_KEYS = EventEntries.ALL_COLUMNS;
        Cursor c = 	db.query(true, EventEntries.TABLE_NAME, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public void deleteAllEventRows() {
        Cursor c = getAllEventRows();
        long rowId = c.getColumnIndexOrThrow(EventEntries._ID);
        if (c.moveToFirst()) {
            do {
                deleteEventRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteEventRow(long rowId) {
        String where = EventEntries._ID + "=" + rowId;
        return db.delete(EventEntries.TABLE_NAME, where, null) != 0;
    }

    // Return all data in the database.
    public Cursor getAllEventRows() {
        String where = null;
        Cursor c = 	db.query(EventEntries.TABLE_NAME, EventEntries.ALL_COLUMNS,
                where, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }







    //HELPER CLASS, Do not mess with this.
    // Change an existing row to be equal to new data.
    public class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_LOGIN_ENTRIES);
            //career
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            //LOGIN
            db.execSQL(SQL_DELETE_LOGIN_ENTRIES);
            //CAREER
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

}
