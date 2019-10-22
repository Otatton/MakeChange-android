package e.otatt.cs3270a5_tatton;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;

public class TimesUpDialog extends DialogFragment {


    public TimesUpDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("You took too long.")
                .setMessage("You should try again.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Dialog", "onClick: times up");
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }

}
