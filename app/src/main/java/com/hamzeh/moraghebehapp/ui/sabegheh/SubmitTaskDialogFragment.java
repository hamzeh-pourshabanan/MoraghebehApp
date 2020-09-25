package com.hamzeh.moraghebehapp.ui.sabegheh;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class SubmitTaskDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

//                NavController navController = NavHostFragment.findNavController(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        // We use a String here, but any type that can be put in a Bundle is supported
//        MutableLiveData<String> liveData = navController.getCurrentBackStackEntry()
//                .getSavedStateHandle()
//                .getLiveData("dialogData");
//        liveData.setValue("عمل با موفقیت ثبت شد");

        builder.setMessage("لطفا نتایج را بنویسید")
                .setPositiveButton("باشه", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
