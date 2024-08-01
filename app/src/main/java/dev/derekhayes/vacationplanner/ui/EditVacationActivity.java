package dev.derekhayes.vacationplanner.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dev.derekhayes.vacationplanner.R;
import dev.derekhayes.vacationplanner.database.VacationRepository;
import dev.derekhayes.vacationplanner.model.Vacation;
import dev.derekhayes.vacationplanner.ui.fragment.DatePickerFragment;

public class EditVacationActivity extends AppCompatActivity implements DatePickerFragment.IDateSetListener {

    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private String accommodations;
    private VacationRepository repo;
    private EditText editText;
    private boolean isStartDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_vacation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repo = new VacationRepository(getApplication());

        findViewById(R.id.vacation_start_date_button).setOnClickListener(view -> {
            isStartDate = true;
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setIDateSetListener(this);
            fragment.show(getSupportFragmentManager(), "datePicker");
        });

        findViewById(R.id.vacation_end_date_button).setOnClickListener(view -> {
            isStartDate = false;
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setIDateSetListener(this);
            fragment.show(getSupportFragmentManager(), "datePicker");
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            // Collect info from edit fields
            editText = findViewById(R.id.edit_vacation_name);
            name = editText.getText().toString();
            editText = findViewById(R.id.edit_vacation_accommodations);
            accommodations = editText.getText().toString();
            editText = findViewById(R.id.edit_vacation_description);
            description = editText.getText().toString();

            Vacation vacation = new Vacation(name, accommodations, startDate, endDate, description);

            try {
                repo.addVacation(vacation);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            finish();

            return true;
        }
        else if (item.getItemId() == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure?").setPositiveButton("Ok", (dialogInterface, i) -> finish()).setNegativeButton("Cancel", null);

            AlertDialog mDialog = builder.create();
            mDialog.show();

            return true;
        }
        return false;
    }

    // from DatePicker
    @Override
    public void processDatePickerResult(int year, int month, int day) {

        // stringify ints
        String month_str = Integer.toString(month + 1);
        String day_str = Integer.toString(day);
        String year_str = Integer.toString(year);
        String date = (month_str + "/" + day_str + "/" + year_str);

        if (isStartDate) {
            startDate = date;
            Button button = findViewById(R.id.vacation_start_date_button);
            button.setText(startDate);
        }
        else {
            endDate = date;
            Button button = findViewById(R.id.vacation_end_date_button);
            button.setText(endDate);
        }
    }
}