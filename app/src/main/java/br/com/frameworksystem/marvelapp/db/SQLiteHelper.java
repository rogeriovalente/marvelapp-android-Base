package br.com.frameworksystem.marvelapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.frameworksystem.marvelapp.contantsenums.Constants;

/**
 * Created by rogerio.valente on 26/09/2016.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "mavelapp.db";
  private static final int DATABASE_VERSION = 2;

  public SQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(Constants.DDL_CHARACTER);
    db.execSQL(Constants.DDL_EVENT);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + Constants.CHARACTER_TABLE);
    db.execSQL("DROP TABLE IF EXISTS " + Constants.EVENT_TABLE);
    onCreate(db);
  }

  public static SQLiteDatabase getDatabase(Context context) {
    SQLiteHelper dbHelper = new SQLiteHelper(context);
    return dbHelper.getWritableDatabase();
  }
}
