package edu.uga.cs.roommateshoppingapp;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all job leads.
 */
public class itemRecyclerAdapter extends RecyclerView.Adapter<itemRecyclerAdapter.JobLeadHolder> {

    public static final String DEBUG_TAG = "itemRecyclerAdapterr";

    private List<Item> jobLeadList;

    public itemRecyclerAdapter(List<Item> jobLeadList ) {
        this.jobLeadList = jobLeadList;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class JobLeadHolder extends RecyclerView.ViewHolder {

        TextView price;

        public JobLeadHolder(View itemView ) {
            super(itemView);

            price = (TextView) itemView.findViewById( R.id.price );
        }
    }

    @Override
    public JobLeadHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        // We need to make sure that all CardViews have the same, full width, allowed by the parent view.
        // This is a bit tricky, and we must provide the parent reference (the second param of inflate)
        // and false as the third parameter (don't attach to root).
        // Consequently, the parent view's (the RecyclerView) width will be used (match_parent).
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.item, parent, false );
        return new JobLeadHolder( view );
    }

    // This method fills in the values of a holder to show a JobLead.
    // The position parameter indicates the position on the list of jobs list.
    @Override
    public void onBindViewHolder( JobLeadHolder holder, int position ) {
        Item jobLead = jobLeadList.get( position );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + jobLead );

        holder.price.setText( Double.toString(jobLead.getPrice()));
    }

    @Override
    public int getItemCount() {
        return jobLeadList.size();
    }
}
