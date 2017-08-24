package com.donygeorge.todolist.ui;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.donygeorge.todolist.models.Item;
import com.donygeorge.todolist.models.Priority;
import com.donygeorge.todolist.R;

import java.util.Date;

import static com.donygeorge.todolist.R.id.prioritySpinner;

public class EditItemDialogFragment extends DialogFragment {
    private static final String ITEM_STRING = "item";
    private static final String POSITION_STRING = "position";
    private Item mItem;
    private int mPosition;
    private EditText mItemEditText;
    private DatePicker mCompletionDatePicker;
    private ToggleButton mCompletedToggleButton;
    private Spinner mPrioritySpinner;

    public interface EditItemDialogListener {
        public void onFinishEditDialog(Item item, int position);
    }


    public EditItemDialogFragment() {
    }

    public static EditItemDialogFragment newInstance(Item item, int position) {
        EditItemDialogFragment frag = new EditItemDialogFragment();
        frag.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        Bundle args = new Bundle();
        if (item != null) {
            args.putSerializable(ITEM_STRING, item);
        }
        args.putInt(POSITION_STRING, position);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mItem = (Item) getArguments().getSerializable(ITEM_STRING);
        mPosition = getArguments().getInt(POSITION_STRING);
        return getActivity().getLayoutInflater().inflate(R.layout.fragment_edit_item, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle("Edit Item");

        // Set text
        mItemEditText = (EditText) view.findViewById(R.id.itemEditText);
        mItemEditText.setText((mItem != null) ? mItem.text : "");

        // Set date
        mCompletionDatePicker = (DatePicker) view.findViewById(R.id.completionDataPicker);
        Date date = (mItem != null) ? mItem.completionDate : new Date();
        mCompletionDatePicker.updateDate(date.getYear() + 1900, date.getMonth() + 1, date.getDate());

        // Set completion status
        mCompletedToggleButton = (ToggleButton) view.findViewById(R.id.completedToggleButton);
        mCompletedToggleButton.setChecked((mItem != null) ? mItem.completed : false);

        // Set priority
        mPrioritySpinner = (Spinner) view.findViewById(prioritySpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrioritySpinner.setAdapter(adapter);
        mPrioritySpinner.setSelection((mItem != null) ? mItem.priority.ordinal() : Priority.MEDIUM.ordinal());

        final Button saveButton = (Button) view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(getSaveOnClickListener());
    }

    private View.OnClickListener getSaveOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemEditText.getText().length() == 0) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    alertDialog.setTitle("Invalid data");
                    alertDialog.setMessage("Cannot save an empty task");
                    alertDialog.show();
                    return;
                }

                EditItemDialogListener listener = (EditItemDialogListener) getActivity();
                Date date = new Date(mCompletionDatePicker.getYear() - 1900, mCompletionDatePicker.getMonth() - 1, mCompletionDatePicker.getDayOfMonth());
                Priority priority = Priority.values()[mPrioritySpinner.getSelectedItemPosition()];
                Item item = new Item(mItemEditText.getText().toString(), date, priority , mCompletedToggleButton.isChecked());
                listener.onFinishEditDialog(item, mPosition);
                dismiss();
            }
        };
    }
}
