package dev.derekhayes.vacationplanner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import dev.derekhayes.vacationplanner.dao.ExcursionDao;
import dev.derekhayes.vacationplanner.dao.VacationDao;
import dev.derekhayes.vacationplanner.model.Excursion;
import dev.derekhayes.vacationplanner.model.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 4, exportSchema = false)
public abstract class VacationDatabase extends RoomDatabase {
    public abstract VacationDao vacationDao();
    public abstract ExcursionDao excursionDao();

    // Support async
    private static volatile VacationDatabase INSTANCE;

    static VacationDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VacationDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), VacationDatabase.class, "vacation.db")
                        .fallbackToDestructiveMigration()
                        .build();
                }
            }
        }
        return INSTANCE;
    }
}
