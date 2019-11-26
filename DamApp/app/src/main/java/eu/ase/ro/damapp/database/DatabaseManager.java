package eu.ase.ro.damapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import eu.ase.ro.damapp.database.dao.PlayerDao;
import eu.ase.ro.damapp.database.model.Player;
import eu.ase.ro.damapp.util.DateConverter;

@Database(entities = {Player.class}, exportSchema = false,
        version = 1)
@TypeConverters({DateConverter.class})
public abstract class DatabaseManager extends RoomDatabase {
    private static final String DB_NAME = "dam_db";
    private static DatabaseManager databaseManager;

    //singleton with double-if
    public static DatabaseManager getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room
                            .databaseBuilder(context,
                                    DatabaseManager.class,
                                    DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
            return databaseManager;
        }
        return databaseManager;
    }

    //plasarea conexiunii catre dao
    public abstract PlayerDao getPlayerDao();
}
