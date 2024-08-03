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
import dev.derekhayes.vacationplanner.ui.adapter.ExcursionAdapter;

public class ExcursionListActivity extends AppCompatActivity {

    VacationRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.add_excursion_button).setOnClickListener(view -> addExcursion());

        // setup recycle list
        RecyclerView recyclerView = findViewById(R.id.excursion_recycler);
        repo = new VacationRepository(getApplication());
        List<Excursion> excursions;
        try {
            excursions = repo.getExcursions();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setExcursions(excursions);

    }

    @Override
    protected void onResume() {

        super.onResume();

        RecyclerView recyclerView = findViewById(R.id.excursion_recycler);
        repo = new VacationRepository(getApplication());
        List<Excursion> excursions;
        try {
            excursions = repo.getExcursions();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setExcursions(excursions);
    }


    private void addExcursion() {
        startActivity(new Intent(this, EditExcursionActivity.class));
    }
}