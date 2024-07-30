package dev.derekhayes.vacationplanner.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dev.derekhayes.vacationplanner.R;
import dev.derekhayes.vacationplanner.database.VacationRepository;
import dev.derekhayes.vacationplanner.model.Excursion;
import dev.derekhayes.vacationplanner.model.Vacation;

public class VacationListActivity extends AppCompatActivity {

    private VacationRepository vacationRepository;

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
        vacationRepository = new VacationRepository(getApplication());

        Vacation vacation = new Vacation("tropical", "tropicabana", "2024", "2025", "really fun time");
        try {
            vacationRepository.addVacation(vacation);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        vacation = new Vacation("midwestern", "midwestereiner", "2026", "2027", "really boring time");
        try {
            vacationRepository.addVacation(vacation);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Excursion excursion = new Excursion("shit shoveling", "2025", "get knee deep in the manure trade", 2);
        try {
            vacationRepository.addExcursion(excursion);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        findViewById(R.id.add_vacation_button).setOnClickListener(view -> {
            try {
                addVacation();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void addVacation() throws InterruptedException {
        Intent intent = new Intent(this, VacationDetailActivity.class);
        intent.putExtra("isNewVacation", true);
        startActivity(intent);
    }
}