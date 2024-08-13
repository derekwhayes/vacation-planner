package dev.derekhayes.vacationplanner.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Locale;

import dev.derekhayes.vacationplanner.R;
import dev.derekhayes.vacationplanner.database.VacationRepository;
import dev.derekhayes.vacationplanner.model.Vacation;
import dev.derekhayes.vacationplanner.ui.adapter.ReportAdapter;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // set the timestamp
        String timestamp;
        TextView createDate = findViewById(R.id.timestamp);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);
        LocalDateTime now = LocalDateTime.now();
        timestamp = dtf.format(now);

        String dateCreatedText = String.format(getResources().getString(R.string.date_created), timestamp);
        createDate.setText(dateCreatedText);

        RecyclerView recyclerView = findViewById(R.id.report_recycler_view);
        VacationRepository repo = new VacationRepository(getApplication());
        List<Vacation> vacations;
        try {
            vacations = repo.getVacations();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ReportAdapter reportAdapter = new ReportAdapter(this);
        recyclerView.setAdapter(reportAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportAdapter.setVacations(vacations);
    }
}