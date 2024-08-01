package dev.derekhayes.vacationplanner.ui;

import android.content.Intent;
import android.os.Bundle;

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
import dev.derekhayes.vacationplanner.model.Excursion;
import dev.derekhayes.vacationplanner.model.Vacation;

public class VacationListActivity extends AppCompatActivity {

    private VacationRepository repo;

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

        // create sample data
        repo = new VacationRepository(getApplication());

        Vacation vacation = new Vacation("tropical", "tropicabana", "2024", "2025", "really fun time");
        try {
            repo.addVacation(vacation);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        vacation = new Vacation("midwestern", "midwestereiner", "2026", "2027", "really boring time");
        try {
            repo.addVacation(vacation);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Excursion excursion = new Excursion("shit shoveling", "2025", "get knee deep in the manure trade", 2);
        try {
            repo.addExcursion(excursion);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // setup recycler list
        RecyclerView recyclerView = findViewById(R.id.vacation_recycler);
        repo = new VacationRepository(getApplication());
        List<Vacation> vacations;
        try {
            vacations = repo.getVacations();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(vacations);

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
}