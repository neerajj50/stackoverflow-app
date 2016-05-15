package com.stackoverflowapp.helper;

/**
 * Created by Neeraj on 15-05-2016.
 */
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.Example;
import com.example.Item;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ormlitedbmodel.ItemTableModel;
import com.ormlitedbmodel.OwnerTableModel;
import com.stackoverflowapp.R;

/**
 * Database helper which creates and upgrades the database and provides the DAOs for the app.
 *
 *
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    /************************************************
     * Suggested Copy/Paste code. Everything from here to the done block.
     ************************************************/

    private static final String DATABASE_NAME = "stackoverflow.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<ItemTableModel, Long> todoDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /************************************************
     * Suggested Copy/Paste Done
     ************************************************/

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {

            // Create tables. This onCreate() method will be invoked only once of the application life time i.e. the first time when the application starts.
            // TableUtils.createTable(connectionSource, Item.class);
            TableUtils.createTableIfNotExists(connectionSource, ItemTableModel.class);
            TableUtils.createTableIfNotExists(connectionSource, OwnerTableModel
                    .class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {

            // In case of change in database of next version of application, please increase the value of DATABASE_VERSION variable, then this method will be invoked
            //automatically. Developer needs to handle the upgrade logic here, i.e. create a new table or a new column to an existing table, take the backups of the
            // existing database etc.

            TableUtils.dropTable(connectionSource, ItemTableModel.class, true);
            TableUtils.dropTable(connectionSource, OwnerTableModel.class, true);
            onCreate(sqliteDatabase, connectionSource);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }
    /**
     * Returns an instance of the data access object
     * @return
     * @throws SQLException
     */
    public Dao<ItemTableModel, Long> getDao() throws SQLException {
        if(todoDao == null) {
            todoDao = getDao(ItemTableModel.class);
        }
        return todoDao;
    }
}