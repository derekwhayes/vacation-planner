package dev.derekhayes.vacationplanner.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dev.derekhayes.vacationplanner.model.Vacation;

@Dao
public interface VacationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addVacation(Vacation vacation);

    @Update
    void updateVacation(Vacation vacation);

    @Delete
    void deleteVacation(Vacation vacation);

    @Query("SELECT * FROM vacations ORDER BY id")
    List<Vacation> getVacations();

    @Query("SELECT * FROM vacations WHERE id = :vacationId")
    Vacation getVacation(long vacationId);

    @Query("SELECT * FROM vacations WHERE name LIKE :query ORDER BY name")
    List<Vacation> getQueriedVacations(String query);
}
