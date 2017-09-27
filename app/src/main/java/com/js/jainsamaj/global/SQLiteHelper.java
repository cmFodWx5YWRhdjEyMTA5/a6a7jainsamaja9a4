package com.js.jainsamaj.global;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.js.jainsamaj.global.Constants.DATABASE_NAME;
import static io.fabric.sdk.android.Fabric.TAG;


/**
 * Created by arbaz on 14/3/17.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteDatabase mSqLiteDatabase;
    private static String DB_PATH = "";
    private static String DB_NAME = DATABASE_NAME;// Database name
    private final Context mContext;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.mContext = context;
        //check database path
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }


        //If the database does not exist, copy it from the assets.
        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assests
                copyDataBase();
                openDataBase();
                Log.e(TAG + "createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        } else {
            //Create DataBase
            mSqLiteDatabase = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        }


    }

    public  SQLiteDatabase getmSqLiteDatabase() {
        if (mSqLiteDatabase != null && mSqLiteDatabase.isOpen()) {
            return mSqLiteDatabase;
        } else if (mSqLiteDatabase != null && !mSqLiteDatabase.isOpen()) {
            openDataBase();
            return mSqLiteDatabase;
        } else {
            return null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase DATABASE_NAME) {

        //Create TimeStamp Table
        DATABASE_NAME.execSQL("CREATE TABLE IF NOT EXISTS " + Constants.TABLE_TIME_STAMP + "("
                + Constants.TABLE_TIME_STAMP_DATE + " VARCHAR,"
                + Constants.TABLE_TIME_STAMP_IS_FIRST_TIME + " INTEGER);"
        );
        //Create AdvertisementMain Main Table
        DATABASE_NAME.execSQL("CREATE TABLE IF NOT EXISTS " + Constants.TABLE_ADVERTISEMENTS + "("
                + Constants.ADVERTISEMENTS_TABLE_KEY_ID + " INTEGER,"
                + Constants.ADVERTISEMENTS_TABLE_KEY_C_DATETIME + " VARCHAR,"
                + Constants.ADVERTISEMENTS_TABLE_KEY_C_USER + " VARCHAR,"
                + Constants.ADVERTISEMENTS_TABLE_KEY_U_DATETIME + " VARCHAR,"
                + Constants.ADVERTISEMENTS_TABLE_KEY_STATUS + " boolean,"
                + Constants.ADVERTISEMENTS_TABLE_KEY_TITLE + " VARCHAR,"
                + Constants.ADVERTISEMENTS_TABLE_KEY_DESCRIPTION + " VARCHAR,"
                + Constants.ADVERTISEMENTS_TABLE_KEY_COMPANY_NAME + " VARCHAR,"
                + Constants.ADVERTISEMENTS_TABLE_KEY_COMPANY_ADDRESS + " VARCHAR,"
                + Constants.ADVERTISEMENTS_TABLE_KEY_COMPANY_CONTACT + " VARCHAR,"
                + Constants.ADVERTISEMENTS_TABLE_KEY_COMPANY_EMAIL + " VARCHAR,"
                + Constants.ADVERTISEMENTS_TABLE_KEY_ACTIVE + " boolean);"
        );
        //Create Attachment Table
        DATABASE_NAME.execSQL("CREATE TABLE IF NOT EXISTS " + Constants.TABLE_ATTACHMENT_LINK + "("
                + Constants.ATTACHMENT_TABLE_ID + " INTEGER,"
                + Constants.ATTACHMENT_TABLE_COMPANY_URL + " VARCHAR,"
                + Constants.ATTACHMENT_TABLE_URL + " BLOB);"
        );
        //Create Medium Table
        DATABASE_NAME.execSQL("CREATE TABLE IF NOT EXISTS " + Constants.TABLE_MEDIUM_IMAGE_LINK + "("
                + Constants.MEDIUM_TABLE_ID + " INTEGER,"
                  + Constants.MEDIUM_TABLE_COMPANY_URL + " VARCHAR,"
                + Constants.MEDIUM_TABLE_URL + " BLOB);"
        );
        //Create Small Table
        DATABASE_NAME.execSQL("CREATE TABLE IF NOT EXISTS " + Constants.TABLE_SMALL_IMAGE_LINK + "("
                + Constants.SMALL_TABLE_ID + " INTEGER,"
                + Constants.SMALL_TABLE_COMPANY_URL + " VARCHAR,"
                + Constants.SMALL_TABLE_URL + " BLOB);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_TIME_STAMP);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_ADVERTISEMENTS);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_ATTACHMENT_LINK);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_MEDIUM_IMAGE_LINK);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_SMALL_IMAGE_LINK);
        onCreate(db);
    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    //Copy the database from assets
    private void copyDataBase() throws IOException {
        try {
            InputStream mInput = mContext.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            OutputStream mOutput = new FileOutputStream(outFileName);
            byte[] mBuffer = new byte[1024];
            int mLength;
            while ((mLength = mInput.read(mBuffer)) > 0) {
                mOutput.write(mBuffer, 0, mLength);
            }
                 mOutput.flush();
            mOutput.close();
            mInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Open the database, so we can query it
    public boolean openDataBase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        //Log.v("mPath", mPath);
        mSqLiteDatabase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mSqLiteDatabase != null;
    }
}
