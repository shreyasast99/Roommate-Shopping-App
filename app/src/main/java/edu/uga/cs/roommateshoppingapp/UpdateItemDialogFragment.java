package edu.uga.cs.roommateshoppingapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class UpdateItemDialogFragment extends DialogFragment {
    private EditText price;
    private EditText buyer;

    // This interface will be used to obtain the new job lead from an AlertDialog.
    // A class implementing this interface will handle the new job lead, i.e. store it
    // in Firebase and add it to the RecyclerAdapter.
    public interface UpdateItemDialogListener {
        void onFinishNewItemDialog(Item jobLead);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create the AlertDialog view
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.update_item_dialog,
                (ViewGroup) getActivity().findViewById(R.id.root));

        // get the view objects in the AlertDialog
        price = layout.findViewById( R.id.price );
        buyer = layout.findViewById( R.id.buyer );

        // create a new AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set its view (inflated above).
        builder.setView(layout);

        // Set the title of the AlertDialog
        builder.setTitle( "New Job Lead" );
        // Provide the negative button listener
        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });
        // Provide the positive button listener
        builder.setPositiveButton( android.R.string.ok, new AddItemDialogFragment.ButtonClickListener() );

        // Create the AlertDialog and show it
        return builder.create();
    }

    private class ButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Double tempPrice = Double.valueOf(price.getText().toString());
            String tempBuyer = buyer.getText().toString();
            Item jobLead = new Item( tempName, tempPrice, tempBuyer);

            // get the Activity's listener to add the new job lead
            AddItemDialogFragment.AddItemDialogListener listener = (AddItemDialogFragment.AddItemDialogListener) getActivity();
            // add the new job lead
            listener.onFinishNewItemDialog( jobLead );
            // close the dialog
            dismiss();
        }
    }

}
