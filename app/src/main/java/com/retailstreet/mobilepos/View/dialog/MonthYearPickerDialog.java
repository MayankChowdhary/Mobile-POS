package com.retailstreet.mobilepos.View.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.retailstreet.mobilepos.R;

import java.util.Calendar;
import java.util.Objects;

/**
 * Created by ankititjunkies on 20/03/18.
 */
/**
 * Modified by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class MonthYearPickerDialog extends DialogFragment {

    private static final int MAX_YEAR = 2099;
    private DatePickerDialog.OnDateSetListener listener;

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.month_year_picker_dialog, null);
        final NumberPicker monthPicker = dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = dialog.findViewById(R.id.picker_year);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(2000);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);

        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton("OK", (dialog12, id) -> listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), 0))
                .setNegativeButton("CANCEL", (dialog1, id) -> Objects.requireNonNull(MonthYearPickerDialog.this.getDialog()).cancel());
        return builder.create();
    }
}
