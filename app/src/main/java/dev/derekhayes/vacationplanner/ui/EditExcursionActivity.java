package dev.derekhayes.vacationplanner.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dev.derekhayes.vacationplanner.R;
import dev.derekhayes.vacationplanner.database.VacationRepository;
import dev.derekhayes.vacationplanner.model.Excursion;

public class EditExcursionActivity extends AppCompatActivity {

    private String name;
    private String date;
    private String description;
    private VacationRepository repo;
    private EditText nameTV;
    private EditText descriptionTV;
    private Button dateBtn;
    private boolean isAddNewExcursion;
    private Excursion excursion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_excursion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repo = new VacationRepository(getApplication());

        // set views
        nameTV = findViewById(R.id.edit_excursion_name);
        descriptionTV = findViewById(R.id.edit_excursion_description);
        dateBtn = findViewById(R.id.excursion_date_button);

        // check if editing existing excursion and populate view accordingly

    }
}