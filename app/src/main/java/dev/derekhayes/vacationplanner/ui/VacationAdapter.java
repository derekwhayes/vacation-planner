package dev.derekhayes.vacationplanner.ui;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import dev.derekhayes.vacationplanner.R;
import dev.derekhayes.vacationplanner.model.Vacation;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {

    private List<Vacation> vacations;
    private final Context context;
    private final LayoutInflater inflater;

    public VacationAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder {

        private final TextView vacationItemView;

        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);

            vacationItemView = itemView.findViewById(R.id.vacation_list_item_text);
            itemView.setOnClickListener(view -> {
                int position = getBindingAdapterPosition();
                final Vacation current = vacations.get(position);
                Intent intent = new Intent(context, VacationDetailActivity.class)
                        .putExtra("id", current.getId())
                        .putExtra("name", current.getName())
                        .putExtra("accommodationName", current.getAccommodationName())
                        .putExtra("startDate", current.getStartDate())
                        .putExtra("endDate", current.getEndDate())
                        .putExtra("description", current.getDescription());
                        context.startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.vacation_list_item, parent, false);

        return new VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if (vacations != null) {
            Vacation current = vacations.get(position);
            String name = current.getName();
            holder.vacationItemView.setText(name);
        }
        else {
            holder.vacationItemView.setText("No vacations yet!");
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
