package android.bignerdranch.com;

import android.bignerdranch.com.database.CheckinBaseHelper;
import android.bignerdranch.com.database.CheckinCursorWrapper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.bignerdranch.com.database.CheckinDbSchema.CheckinTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CheckinStore {
    private static CheckinStore sCheckinStore;
    //private List<Checkin> mCheckins; // deleted
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CheckinStore get(Context context){
        if (sCheckinStore == null) {
            sCheckinStore = new CheckinStore(context);
        }
        return sCheckinStore;
    }
    private CheckinStore(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CheckinBaseHelper(mContext)
                .getWritableDatabase();
        //mCheckins = new ArrayList<>();  //deleted

    }

    public void addCheckin(CheckinStore c) {
        //mCheckins.add(c); //deleted
        ContentValues values = getContentValues(c);
        mDatabase.insert(CheckinTable.NAME, null, values);

    }
    public List<CheckinStore> getCheckins() {
        //return mCheckins;//deleted
        List<Chickin> chickins = new ArrayList<>();
        CheckinCursorWrapper cursor = queryCheckins(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                chickins.add(cursor.getCheckin());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return checkins;

    }
    public CheckinStore getCheckin(UUID id){
       /* for (Checkin checkin : mCheckins){
            if (checkin.getId().equals(id)){
                return checkin;
            }
        }*/
        CheckinCursorWrapper cursor = queryCheckins(
                CheckinTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCheckin();
        } finally {
            cursor.close();
        }

    }

    public File getPhotoFile(CheckinStore checkin) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, crime.getPhotoFilename());
    }

    public void updateCheckin(CheckinStore checkin) {
        String uuidString = checkin.getId().toString();
        ContentValues values = getContentValues(checkin);
        mDatabase.update(CheckinTable.NAME, values,
                CheckinTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private CheckinCursorWrapper queryCheckins(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CheckinTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new CheckinCursorWrapper(cursor);
    }
    private static ContentValues getContentValues(CheckinStore checkin) {
        ContentValues values = new ContentValues();
        values.put(CheckinTable.Cols.UUID, checkin.getId().toString());
        values.put(CheckinTable.Cols.TITLE, checkin.getTitle());
        values.put(CheckinTable.Cols.PLACE, checkin.getPlace());
        values.put(CheckinTable.Cols.DETAILS, checkin.getDetails());
        values.put(CheckinTable.Cols.DATE, checkin.getDate().getTime());
        values.put(CheckinTable.Cols.LOCATION, checkin.getlocation());
        values.put(CheckinTable.Cols.SHARE, checkin.getShare());

        return values;
    }

}
