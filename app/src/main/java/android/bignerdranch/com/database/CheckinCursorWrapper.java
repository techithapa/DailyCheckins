package android.bignerdranch.com.database;


import android.bignerdranch.com.Crime;
import android.bignerdranch.com.database.CheckinDbSchema.CrimeTable;
import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

public class CheckinCursorWrapper extends CursorWrapper {
    public CheckinCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Checkin getCheckin() {
        String uuidString = getString(getColumnIndex(CheckinTable.Cols.UUID));
        String title = getString(getColumnIndex(CheckinTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CheckinTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CheckinTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CheckinTable.Cols.SUSPECT));

        Checkin checkin = new Checkin(UUID.fromString(uuidString));
        checkin.setTitle(title);
        checkin.setDate(new Date(date));
        checkin.setSolved(isSolved != 0);
        checkin.setSuspect(suspect);

        return checkin;
    }

}
