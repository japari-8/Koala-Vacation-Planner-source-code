package com.example.d308vacationplanner.DAO;

import android.view.View;

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
public interface ExcursionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM Excursions ORDER BY excursionId ASC")
    List<Excursion> getAllExcursions();

    @Query("SELECT * FROM Excursions WHERE vacationId = :vacationID ORDER BY excursionId ASC")
    List<Excursion> getAssociatedExcursions(long vacationID);


}
