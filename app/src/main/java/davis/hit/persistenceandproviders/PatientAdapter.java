package davis.hit.persistenceandproviders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Davis on 07/04/2018.
 */

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {
    List<AppointmentListItem> items;

    public PatientAdapter(List<AppointmentListItem> items) {
        this.items = items;
    }

    @Override
    public PatientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PatientAdapter.ViewHolder holder, int position) {
        holder.name.setText(items.get(position).getTitle());
        holder.time.setText(items.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
        }
    }
}
