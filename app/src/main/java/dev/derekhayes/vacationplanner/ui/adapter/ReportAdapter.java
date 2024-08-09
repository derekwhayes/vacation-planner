package dev.derekhayes.vacationplanner.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.derekhayes.vacationplanner.R;
import dev.derekhayes.vacationplanner.model.Vacation;
import dev.derekhayes.vacationplanner.ui.VacationDetailActivity;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private List<Vacation> vacations;
    private final Context context;
    private final LayoutInflater inflater;

    public ReportAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameItemView;
        private final TextView startDateItemView;
        private final TextView endDateItemView;
        private final TextView accommodationsItemView;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);

            nameItemView = itemView.findViewById(R.id.report_name_text);
            startDateItemView = itemView.findViewById(R.id.report_start_date_text);
            endDateItemView = itemView.findViewById(R.id.report_end_date_text);
            accommodationsItemView = itemView.findViewById(R.id.report_accommodations_text);

//            itemView.setOnClickListener(view -> {
//                int position = getBindingAdapterPosition();
//                final Vacation current = vacations.get(position);
//                Intent intent = new Intent(context, VacationDetailActivity.class)
//                        .putExtra("id", current.getId())
//                        .putExtra("name", current.getName())
//                        .putExtra("accommodationName", current.getAccommodationName())
//                        .putExtra("startDate", current.getStartDate())
//                        .putExtra("endDate", current.getEndDate())
//                        .putExtra("description", current.getDescription());
//                context.startActivity(intent);
//            });
        }
    }

    @NonNull
    @Override
    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.report_list_item, parent, false);

        return new ReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ReportViewHolder holder, int position) {
        if (vacations != null) {
            Vacation current = vacations.get(position);
            String name = current.getName();
            String startDate = current.getStartDate();
            String endDate = current.getEndDate();
            String accommodations = current.getAccommodationName();

            holder.nameItemView.setText(name);
            holder.startDateItemView.setText(startDate);
            holder.endDateItemView.setText(endDate);
            holder.accommodationsItemView.setText(accommodations);
        }
    }

    @Override
    public int getItemCount() {
        if (vacations != null) {
            return vacations.size();
        }
        else {
            return 0;
        }
    }

    public void setVacations(List<Vacation> vacations) {
        this.vacations = vacations;
        notifyDataSetChanged();
    }
}
