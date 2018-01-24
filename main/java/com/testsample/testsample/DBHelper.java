package com.testsample.testsample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dot on 1/18/2018.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    // Fields

    public static final String DB_NAME = "person_manager.db";
    private static final int DB_VERSION = 1;

    // Public methods

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource cs)
    {
        DatabaseConnection conn = connectionSource.getSpecialConnection();
        boolean clearSpecial = false;
        if (conn == null) {
            conn = new AndroidDatabaseConnection(db, true);
            try {
                connectionSource.saveSpecialConnection(conn);
                db.setLockingEnabled(false);


                clearSpecial = true;
            } catch (SQLException e) {
                throw new IllegalStateException(
                        "Could not save special connection", e);
            }
        }
        try
        {
            TableUtils.createTable(connectionSource, PersonDetails.class);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        finally {
            if (clearSpecial) {
                connectionSource.clearSpecialConnection(conn);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource cs, int oldVersion, int newVersion) {

    }

    public List getAll(Class clazz) throws SQLException {
        Dao<PersonDetails, ?> dao = getDao(clazz);
        return dao.queryForAll();
    }

    public  PersonDetails getById(Class clazz, Object aId) throws SQLException {
        Dao<PersonDetails, Object> dao = getDao(clazz);
        return dao.queryForId(aId);
    }

    public int create(PersonDetails obj) throws SQLException {
        Dao<PersonDetails, ?> dao = (Dao<PersonDetails, ?>) getDao(obj.getClass());
        return dao.create(obj);
    }

    public int update(PersonDetails obj) throws SQLException {
        Dao<PersonDetails, ?> dao = (Dao<PersonDetails, ?>) getDao(obj.getClass());
        return dao.update(obj);
    }

    public int deleteById(Class clazz, int aId) throws SQLException {
        Dao<PersonDetails, Object> dao = getDao(clazz);
        return dao.deleteById(aId);
    }
}
