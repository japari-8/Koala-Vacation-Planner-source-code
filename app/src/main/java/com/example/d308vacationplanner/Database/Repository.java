package com.example.d308vacationplanner.Database;

import android.app.Application;

import com.example.d308vacationplanner.DAO.ExcursionDAO;
import com.example.d308vacationplanner.DAO.VacationDAO;
import com.example.d308vacationplanner.Entities.Excursion;
import com.example.d308vacationplanner.Entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private VacationDAO mVacationDAO;
    private ExcursionDAO mExcursionDAO;
    private List<Vacation> mListVacations;
    private List<Excursion> mListExcursions;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        VacationPlannerDatabaseBuilder db = VacationPlannerDatabaseBuilder.getDatabase(application);
        mVacationDAO = db.vacationDAO();
        mExcursionDAO = db.excursionDAO();

    }

    public List<Vacation> getmListVacations() throws InterruptedException {
        databaseExecutor.execute(()->{
            mListVacations = mVacationDAO.getAllVacations();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mListVacations;
    }

    public void addVacation(Vacation vacation) throws InterruptedException {
        databaseExecutor.execute(()->{
          mVacationDAO.add(vacation);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateVacation(Vacation vacation) throws InterruptedException {
        databaseExecutor.execute(()->{
            mVacationDAO.update(vacation);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteVacation(Vacation vacation) throws InterruptedException {
        databaseExecutor.execute(()->{
            mVacationDAO.delete(vacation);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Excursion> getmListExcursions() throws InterruptedException {
        databaseExecutor.execute(()->{
            mListExcursions = mExcursionDAO.getAllExcursions();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mListExcursions;
    }

    public List<Excursion> getmListAssociatedExcursions(long selectedVacationId) throws InterruptedException {
        databaseExecutor.execute(()->{
            mListExcursions = mExcursionDAO.getAssociatedExcursions(selectedVacationId);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mListExcursions;
    }

    public void addExcursion(Excursion excursion) throws InterruptedException {
        databaseExecutor.execute(()->{
            mExcursionDAO.add(excursion);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateExcursion(Excursion excursion) throws InterruptedException {
        databaseExecutor.execute(()->{
            mExcursionDAO.update(excursion);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteExcursion(Excursion excursion) throws InterruptedException {
        databaseExecutor.execute(()->{
            mExcursionDAO.delete(excursion);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}
