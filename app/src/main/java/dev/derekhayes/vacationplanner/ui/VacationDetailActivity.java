package dev.derekhayes.vacationplanner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dev.derekhayes.vacationplanner.R;
import dev.derekhayes.vacationplanner.database.VacationRepository;
import dev.derekhayes.vacationplanner.model.Excursion;
import dev.derekhayes.vacationplanner.model.Vacation;
import dev.derekhayes.vacationplanner.ui.adapter.VacationExcursionsAdapter;

public class VacationDetailActivity extends AppCompatActivity {

    // TODO: Excursion list isn't showing onCreate

    String name;
    String description;
    String startDate;
    String endDate;
    String accommodations;
    List<String> excursionIds = new ArrayList<>();
    String excursionId;
    long excursionIdLong;
    long id;
    TextView nameTV;
    TextView descriptionTV;
    TextView datesTV;
    TextView accommodationsTV;
    Excursion excursion;
    List<Excursion> excursions;

    VacationRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vaction_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repo = new VacationRepository(getApplication());

        // find views
        findViewById(R.id.add_excursion_button).setOnClickListener(view -> addExcursion());

        nameTV = findViewById(R.id.vacation_name_text);
        descriptionTV = findViewById(R.id.vacation_description_text);
        datesTV = findViewById(R.id.vacation_dates_text);
        accommodationsTV = findViewById(R.id.vacation_accommodations_text);

        // get vacation info from vacation list
        id = getIntent().getLongExtra("id", -1);
        name = getIntent().getStringExtra("name");
        description = getIntent().getStringExtra("description");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        accommodations = getIntent().getStringExtra("accommodations");
        try {
            populateVacation();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // set info from edited vacation
        try {
            Vacation vacation = repo.getVacation(id);
            name = vacation.getName();
            description = vacation.getDescription();
            accommodations = vacation.getAccommodationName();
            startDate = vacation.getStartDate();
            endDate = vacation.getEndDate();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            populateVacation();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateVacation() throws InterruptedException {

        nameTV.setText(name);
        descriptionTV.setText(description);
        datesTV.setText(getResources().getString(R.string.vacation_dates, startDate, endDate));
        accommodationsTV.setText(accommodations);

        excursions = repo.getVacationExcursions(id);

        // setup recycle list
        RecyclerView recyclerView = findViewById(R.id.vacation_excursion_recycler);
        final VacationExcursionsAdapter vacationExcursionsAdapter = new VacationExcursionsAdapter(this);
        recyclerView.setAdapter(vacationExcursionsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationExcursionsAdapter.setExcursions(excursions);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_vacation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit) {
            Intent intent = new Intent(this, EditVacationActivity.class);
            intent.putExtra("vacationId", id);
            Log.d("TAG", "id: " + id);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure?").setPositiveButton("Ok", (dialogInterface, i) -> {
                try {
                    Log.d("MYTAG", "vacationExcursions: " + repo.getVacationExcursions(id));
                    if (repo.getVacationExcursions(id).isEmpty()) {
                        repo.deleteVacation(repo.getVacation(id));
                        finish();
                    }
                    else {
                        Toast.makeText(this, "Excursions must be deleted first", Toast.LENGTH_LONG).show();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).setNegativeButton("Cancel", null);

            AlertDialog mDialog = builder.create();
            mDialog.show();
            return true;

        }
        return false;
    }

    public void addExcursion() {
        Intent intent = new Intent(this, EditExcursionActivity.class);
        intent.putExtra("vacationId", id);
        Log.d("MYTAG", "vacationId in vacationDetail: " + id);
        startActivity(intent);
    }
}