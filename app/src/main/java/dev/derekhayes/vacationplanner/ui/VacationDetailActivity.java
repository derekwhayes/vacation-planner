package dev.derekhayes.vacationplanner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
import dev.derekhayes.vacationplanner.ui.adapter.VacationExcursionsAdapter;

public class VacationDetailActivity extends AppCompatActivity {

    String name;
    String description;
    String startDate;
    String endDate;
    String accommodations;
    long id;
    TextView nameTV;
    TextView descriptionTV;
    TextView datesTV;
    TextView accommodationsTV;

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

        findViewById(R.id.add_excursion_button).setOnClickListener(view -> addExcursion());

        nameTV = findViewById(R.id.vacation_name_text);
        descriptionTV = findViewById(R.id.vacation_description_text);
        datesTV = findViewById(R.id.vacation_dates_text);
        accommodationsTV = findViewById(R.id.vacation_accommodations_text);

        id = getIntent().getLongExtra("id", -1);
        name = getIntent().getStringExtra("name");
        description = getIntent().getStringExtra("description");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        accommodations = getIntent().getStringExtra("accommodations");

        nameTV.setText(name);
        descriptionTV.setText(description);
        datesTV.setText(getResources().getString(R.string.vacation_dates, startDate, endDate));
        accommodationsTV.setText(accommodations);

        // setup recycle list
        RecyclerView recyclerView = findViewById(R.id.vacation_excursion_recycler);
        repo = new VacationRepository(getApplication());
        List<Excursion> excursions;
        try {
            excursions = repo.getAssociatedExcursions(id);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
            try {
                repo.deleteVacation(repo.getVacation(id));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure?").setPositiveButton("Ok", (dialogInterface, i) -> finish()).setNegativeButton("Cancel", null);

            AlertDialog mDialog = builder.create();
            mDialog.show();
            return true;

        }
        return false;
    }

    public void addExcursion() {
        startActivity(new Intent(this, ExcursionListActivity.class));
    }
}