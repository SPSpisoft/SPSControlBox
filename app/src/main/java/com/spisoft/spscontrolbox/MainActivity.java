package com.spisoft.spscontrolbox;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.snackbar.Snackbar;
import com.spisoft.spcontrolbox.SpCtrlBox;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton TBTN = findViewById(R.id.toggleBtn);

        SpCtrlBox spLateBoxAdd = findViewById(R.id.splatboxAdd);
        spLateBoxAdd.SetHeadSrc(R.drawable.ic_baseline_add_24, 0, 0);

//        spLateBoxAdd.SetSrc(
//                R.drawable.ic_baseline_note_add_24,
//                R.drawable.ic_baseline_post_add_24,
//                R.drawable.ic_baseline_note_add_24,
//                R.drawable.ic_baseline_note_add_24);

        LayoutInflater inflater0 = LayoutInflater.from(MainActivity.this);
        spLateBoxAdd.AddView(inflater0.inflate(R.layout.test_view, null));

        TestListAdapter testListAdapter0 = new TestListAdapter();
        List<ClsTest> mList0 = new ArrayList<>();
        mList0.add(new ClsTest("1", "ADD 1", true));
        mList0.add(new ClsTest("2", "add 2 ", false));
        mList0.add(new ClsTest("3", "add 3", true));
        mList0.add(new ClsTest("4", "add 4", true));
        testListAdapter0.updateList(mList0);
        spLateBoxAdd.SetList(testListAdapter0);

        spLateBoxAdd.setOnAddClickTaskListener(new SpCtrlBox.OnAddClickTaskListener() {
            @Override
            public void onEvent(boolean editMode) {
                Toast.makeText(MainActivity.this, "SAVE ", Toast.LENGTH_SHORT).show();
            }
        });

        SpCtrlBox spLateBoxTel = findViewById(R.id.splatboxTel);
        spLateBoxTel.SetHeadSrc(R.drawable.ic_baseline_check_24, Color.BLUE, Color.WHITE);

        TBTN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                spLateBoxTel.SetEditable(isChecked);
            }
        });

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View inflatedLayout = inflater.inflate(R.layout.test_view, null);
        spLateBoxTel.AddView(inflatedLayout);
        EditText editText = inflatedLayout.findViewById(R.id.editTextNumber);
        Button button = inflatedLayout.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, editText.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        TestListAdapter testListAdapter = new TestListAdapter();
        List<ClsTest> mList = new ArrayList<>();
        mList.add(new ClsTest("1", "Title1", true));
        mList.add(new ClsTest("2", "Title2", false));
        mList.add(new ClsTest("3", "Title3", true));
        testListAdapter.updateList(mList);
        spLateBoxTel.SetList(testListAdapter);

        spLateBoxTel.setOnAddClickTaskListener(new SpCtrlBox.OnAddClickTaskListener() {
            @Override
            public void onEvent(boolean editMode) {
                if(editMode) {
                    Snackbar.make(spLateBoxTel, "CANCEL", Snackbar.LENGTH_INDEFINITE)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    spLateBoxTel.setMode(false, -1);
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }else {
                    inflatedLayout.setTag(null);
                    editText.setText("");
                }
            }
        });

        spLateBoxTel.setOnEditClickTaskListener(new SpCtrlBox.OnEditClickTaskListener() {
            @Override
            public void onEvent(boolean editMode, int currentPosition) {
                if(!editMode){
                    inflatedLayout.setTag(currentPosition);
                    editText.setText(mList.get(currentPosition).getOptTitle());
                }else {
                    Snackbar.make(spLateBoxTel, "SAVE", Snackbar.LENGTH_INDEFINITE)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int skipTo;
                                    if(inflatedLayout.getTag() == null){
                                        mList.add(new ClsTest("4", editText.getText().toString(), true));
                                        skipTo = mList.size()-1;
                                    }else {
                                        mList.get(currentPosition).setOptTitle(editText.getText().toString());
                                        skipTo = currentPosition;
                                    }
                                    testListAdapter.updateList(mList);
                                    spLateBoxTel.setMode(false, skipTo);
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }
            }
        });
    }
}