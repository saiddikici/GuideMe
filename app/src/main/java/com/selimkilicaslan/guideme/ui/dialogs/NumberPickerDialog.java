package com.selimkilicaslan.guideme.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class NumberPickerDialog extends DialogFragment {

    private NumberPicker.OnValueChangeListener onValueChangeListener;
    private int currentValue = 75;

    public NumberPickerDialog(NumberPicker.OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }

    public NumberPickerDialog(NumberPicker.OnValueChangeListener onValueChangeListener, int currentValue) {
        this.onValueChangeListener = onValueChangeListener;
        this.currentValue = currentValue;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final NumberPicker numberPicker = new NumberPicker(getActivity());

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(5000);
        numberPicker.setValue(currentValue);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Set your price per day");

        builder.setPositiveButton("SET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onValueChangeListener.onValueChange(numberPicker,
                        numberPicker.getValue(), numberPicker.getValue());
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onValueChangeListener.onValueChange(numberPicker,
                        numberPicker.getValue(), numberPicker.getValue());
            }
        });

        builder.setView(numberPicker);
        return builder.create();
    }

    public NumberPicker.OnValueChangeListener getOnValueChangeListener() {
        return onValueChangeListener;
    }

    public void setOnValueChangeListener(NumberPicker.OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }
}
