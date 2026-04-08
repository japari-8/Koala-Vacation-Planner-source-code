package com.example.d308vacationplanner.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.d308vacationplanner.Converter.Converters;
import com.example.d308vacationplanner.DAO.ExcursionDAO;
import com.example.d308vacationplanner.DAO.VacationDAO;
import com.example.d308vacationplanner.Entities.Excursion;
import com.example.d308vacationplanner.Entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class VacationPlannerDatabaseBuilder extends RoomDatabase {
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();
    public static volatile VacationPlannerDatabaseBuilder INSTANCE;

    static VacationPlannerDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (VacationPlannerDatabaseBuilder.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), VacationPlannerDatabaseBuilder.class, "MyVacationPlannerDatabase.db").build();
                }
            }
        }
        return INSTANCE;
    }

}
