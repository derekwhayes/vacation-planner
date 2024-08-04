package dev.derekhayes.vacationplanner.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.derekhayes.vacationplanner.R;
import dev.derekhayes.vacationplanner.model.Excursion;
import dev.derekhayes.vacationplanner.ui.ExcursionDetailActivity;



public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {

    private List<Excursion> excursions;
    private final Context context;
    private final LayoutInflater inflater;
    private long vacationId;


    public ExcursionAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;

    }

    public class ExcursionViewHolder extends RecyclerView.ViewHolder {

        private final TextView excursionItemView;

        public ExcursionViewHolder(@NonNull View itemView) {
            super(itemView);

            excursionItemView = itemView.findViewById(R.id.excursion_list_item_text);
            itemView.setOnClickListener(view -> {
                int position = getBindingAdapterPosition();
                final Excursion current = excursions.get(position);
                Intent intent = new Intent(context, ExcursionDetailActivity.class)
                        .putExtra("id", current.getId())
                        .putExtra("name", current.getName())
                        .putExtra("date", current.getDate())
                        .putExtra("vacationId", vacationId)
                        .putExtra("description", current.getDescription());
                Log.d("MYTAG", "vacationId in excursionAdapter.ExcursionViewHolder: " + vacationId);
                context.startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public ExcursionAdapter.ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.excursion_list_item, parent, false);

        return new ExcursionAdapter.ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionAdapter.ExcursionViewHolder holder, int position) {
        if (excursions != null) {
            Excursion current = excursions.get(position);
            String name = current.getName();
            holder.excursionItemView.setText(name);
        }
        else {
            holder.excursionItemView.setText("No excursions yet!");
        }
    }

    @Override
    public int getItemCount() {
        if (excursions != null) {
            return excursions.size();
        }
        else {
            return 0;
        }
    }

    public void setExcursions(List<Excursion> excursions) {
        this.excursions = excursions;
        notifyDataSetChanged();
    }

    public void setVacationId(long id) {
        vacationId = id;
        Log.d("MYTAG", "vacationId in excursionAdapter.setVacationId: " + vacationId);
        notifyDataSetChanged();
    }
}

