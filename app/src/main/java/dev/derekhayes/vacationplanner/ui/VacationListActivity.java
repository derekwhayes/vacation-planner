package dev.derekhayes.vacationplanner.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.derekhayes.vacationplanner.R;
import dev.derekhayes.vacationplanner.database.VacationRepository;
import dev.derekhayes.vacationplanner.model.Vacation;
import dev.derekhayes.vacationplanner.ui.adapter.VacationAdapter;

public class VacationListActivity extends AppCompatActivity {

    VacationRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // setup recycler list
        setRecyclerView();

        // add button listener
        findViewById(R.id.add_vacation_button).setOnClickListener(view -> {
            try {
                addVacation();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void addVacation() throws InterruptedException {
        Intent intent = new Intent(this, EditVacationActivity.class);
        startActivity(intent);
    }

    // update the vacation list when using up button
    @Override
    public void onResume() {
        super.onResume();
        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.vacation_recycler);
        repo  = new VacationRepository(getApplication());
        List<Vacation> vacations;
        try {
            vacations = repo.getVacations();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(vacations);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vacation_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        } else if (item.getItemId() == R.id.search) {
            onSearchRequested();
            return true;
        }
        else if (item.getItemId() == R.id.report) {
            Intent intent = new Intent(this, ReportActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.sample_data) {
            try {
                createSampleDate();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return false;
    }

    private void createSampleDate() throws InterruptedException {
        repo = new VacationRepository(getApplication());

        Vacation vacation = new Vacation("Las Vegas", "Hilton", "9/10/2024", "9/17/2024", "Gambling fun times");
        repo.addVacation(vacation);
        vacation = new Vacation("Disney Land", "Magic Castle", "9/19/2024", "9/22/2024", "Family fun times");
        repo.addVacation(vacation);
        vacation = new Vacation("Grand Canyon", "Colorado Castle", "10/10/2024", "10/27/2024", "Big hole fun times");
        repo.addVacation(vacation);
        vacation = new Vacation("Paris", "Louvre", "11/05/2024", "11/16/2024", "Weird food fun times");
        repo.addVacation(vacation);
        vacation = new Vacation("Bora Bora", "Treehouse", "12/19/2024", "12/22/2024", "Volcanic fun times");
        repo.addVacation(vacation);
        vacation = new Vacation("Glacier Park", "Ice Berg Motel", "1/1/2025", "1/9/2025", "Cold fun times");
        repo.addVacation(vacation);
        vacation = new Vacation("Rome", "St. Peter's Basilica", "2/22/2025", "2/28/2025", "Ancient fun times");
        repo.addVacation(vacation);
        vacation = new Vacation("Swiss Alps", "St Moritz", "3/5/2025", "3/18/2025", "Skiing fun times");
        repo.addVacation(vacation);
        vacation = new Vacation("Maui", "Haleakala Hotel", "4/8/2025", "4/11/2025", "Luau fun times");
        repo.addVacation(vacation);
        vacation = new Vacation("London", "Tower of London", "5/1/2025", "11/2/2048", "Prison fun times");
        repo.addVacation(vacation);

        setRecyclerView();
    }
}