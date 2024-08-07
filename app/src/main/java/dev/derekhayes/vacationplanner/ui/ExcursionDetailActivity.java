package dev.derekhayes.vacationplanner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dev.derekhayes.vacationplanner.R;
import dev.derekhayes.vacationplanner.database.VacationRepository;
import dev.derekhayes.vacationplanner.model.Excursion;

public class ExcursionDetailActivity extends AppCompatActivity {

    String name;
    String description;
    String date;
    long id;
    TextView nameTV;
    TextView descriptionTV;
    TextView dateTV;
    VacationRepository repo;
    long vacationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repo = new VacationRepository(getApplication());

        // find views
        nameTV = findViewById(R.id.excursion_name_text);
        descriptionTV = findViewById(R.id.excursion_description_text);
        dateTV = findViewById(R.id.excursion_date_text);

        // get excursion info from excursion list
        id = getIntent().getLongExtra("id", -1);
        name = getIntent().getStringExtra("name");
        description = getIntent().getStringExtra("description");
        date = getIntent().getStringExtra("date");
        vacationId = getIntent().getLongExtra("vacationId", -1);

        try {
            populateExcursion();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // set info from edited excursion
        try {
            Excursion excursion = repo.getExcursion(id);
            name = excursion.getName();
            description = excursion.getDescription();
            date = excursion.getDate();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            populateExcursion();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateExcursion() throws InterruptedException {

        nameTV.setText(name);
        descriptionTV.setText(description);
        dateTV.setText(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_excursion_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        else if (item.getItemId() == R.id.edit) {
            Intent intent = new Intent(this, EditExcursionActivity.class);
            intent.putExtra("excursionId", id);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure?").setPositiveButton("Ok", (dialogInterface, i) -> {
                try {
                    repo.deleteExcursion(repo.getExcursion(id));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                finish();
            }).setNegativeButton("Cancel", null);

            AlertDialog mDialog = builder.create();
            mDialog.show();
            return true;

        }
        return false;
    }
}





















