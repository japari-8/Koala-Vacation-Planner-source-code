package com.example.d308vacationplanner.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308vacationplanner.Entities.Excursion;
import com.example.d308vacationplanner.Entities.Vacation;

import java.util.List;
@Dao
public interface VacationDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);

    @Query("SELECT * FROM Vacations ORDER BY vacationId ASC")
    List<Vacation> getAllVacations();

    @Query("SELECT * FROM Vacations WHERE vacationId = :vacationID")
    Vacation getSelectedVacation(long vacationID);
}
