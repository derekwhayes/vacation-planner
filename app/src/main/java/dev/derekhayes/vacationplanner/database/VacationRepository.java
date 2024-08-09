package dev.derekhayes.vacationplanner.database;

import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.derekhayes.vacationplanner.dao.ExcursionDao;
import dev.derekhayes.vacationplanner.dao.VacationDao;
import dev.derekhayes.vacationplanner.model.Excursion;
import dev.derekhayes.vacationplanner.model.Vacation;

public class VacationRepository {
    private final VacationDao vacationDao;
    private final ExcursionDao excursionDao;

    private Vacation vacation;
    private Excursion excursion;
    private List<Vacation> vacations;
    private List<Excursion> excursions;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public VacationRepository(Application application) {
        VacationDatabase db = VacationDatabase.getDatabase(application);
        vacationDao = db.vacationDao();
        excursionDao = db.excursionDao();
    }

    public void addVacation(Vacation vacation) throws InterruptedException {
        databaseExecutor.execute(() -> {
            vacationDao.addVacation(vacation);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateVacation(Vacation vacation) throws InterruptedException {
        databaseExecutor.execute(() -> {
            vacationDao.updateVacation(vacation);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteVacation(Vacation vacation) throws InterruptedException {
        databaseExecutor.execute(() -> {
            vacationDao.deleteVacation(vacation);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vacation> getVacations() throws InterruptedException {
        databaseExecutor.execute(() -> {
            vacations = vacationDao.getVacations();
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return vacations;
    }

    public Vacation getVacation(long id) throws InterruptedException {
        databaseExecutor.execute(() -> {
            vacation = vacationDao.getVacation(id);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return vacation;
    }

    public List<Vacation> getQueriedVacations(String query) {
        databaseExecutor.execute(() -> {
            // wildcard returns entries with query anywhere in string
            vacations = vacationDao.getQueriedVacations("%" + query + "%");
        });

        try {
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return vacations;
    }

    public void addExcursion(Excursion excursion) throws InterruptedException {
        databaseExecutor.execute(() -> {
            excursionDao.addExcursion(excursion);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateExcursion(Excursion excursion) throws InterruptedException {
        databaseExecutor.execute(() -> {
            excursionDao.updateExcursion(excursion);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteExcursion(Excursion excursion) throws InterruptedException {
        databaseExecutor.execute(() -> {
            excursionDao.deleteExcursion(excursion);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Excursion> getExcursions() throws InterruptedException {
        databaseExecutor.execute(() -> {
            excursions = excursionDao.getExcursions();
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return excursions;
    }

    public Excursion getExcursion(long id) throws InterruptedException {
        databaseExecutor.execute(() -> {
            excursion = excursionDao.getExcursion(id);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return excursion;
    }

    public List<Excursion> getVacationExcursions(long vacationId) throws InterruptedException {
        databaseExecutor.execute(() -> {
            excursions = excursionDao.getVacationExcursions(vacationId);
        });

        try {
            Thread.sleep(100);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return excursions;
    }


}
