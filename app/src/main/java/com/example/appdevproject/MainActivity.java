package com.example.appdevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private Task currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            initTask(extras.getInt("taskid"));
        } else {
            currentTask = new Task();
        }
        initListButton();
        initSettingsButton();
        initPriorityButton();
        initTextChangedEvents();
        initPrioByClick();
        initSaveButton();
        initToggleButton();
    }

    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonMap);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        ImageButton ibList = findViewById(R.id.imageButtonSettings);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initPriorityButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setEnabled(false);
    }

    private void initSaveButton() {
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean wasSuccessful;
                TaskDataSource ds = new TaskDataSource(MainActivity.this);
                try {
                    ds.open();
                    if (currentTask.getTaskID() == -1) {
                        wasSuccessful = ds.insertTask(currentTask);
                    } else {
                        wasSuccessful = ds.updateTask(currentTask);
                    }
                    ds.close();

                    if (wasSuccessful) {
                        int newId = ds.getLastTaskID();
                        currentTask.setTaskID(newId);
                    }
                } catch (Exception e) {
                    wasSuccessful = false;
                }

                if (wasSuccessful) {
                    ToggleButton editToggle = findViewById(R.id.toggleButtonEdit);
                    editToggle.toggle();
                    setForEditing(false);
                }
            }
        });
    }

    private void initTextChangedEvents() {
        final EditText etTaskName = findViewById(R.id.editSubject);
        etTaskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Auto-generated method stud
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Auto-generated method stud
            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentTask.setTaskName(etTaskName.getText().toString());
            }
        });
        final EditText etTaskNotes = findViewById(R.id.editNotes);
        etTaskNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Auto-generated method stud
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Auto-generated method stud
            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentTask.setNotes(etTaskNotes.getText().toString());
            }
        });
    }

    private void initPrioByClick() {
        RadioGroup rgPriority = findViewById(R.id.radioGroupPriority);
        rgPriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rbHigh = findViewById(R.id.radioHigh);
                RadioButton rbMedium = findViewById(R.id.radioMedium);
                if (rbHigh.isChecked()) {
                    currentTask.setPriority("High");
                } else if (rbMedium.isChecked()) {
                    currentTask.setPriority("Medium");
                } else {
                    currentTask.setPriority("Low");
                }
            }
        });
    }

    private void setForEditing(boolean enabled) {
        EditText editSubject = findViewById(R.id.editSubject);
        EditText editNotes = findViewById(R.id.editNotes);
        RadioGroup groupPriority = findViewById(R.id.radioGroupPriority);

        editSubject.setEnabled(enabled);
        editNotes.setEnabled(enabled);
        groupPriority.setEnabled(enabled);

        if (enabled) {
            editSubject.requestFocus();
        }
    }

    private void initToggleButton() {
        final ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleButtonEdit);
        editToggle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                setForEditing(editToggle.isChecked());
            }
        });
    }

    private void initTask(int id) {
        TaskDataSource ds = new TaskDataSource(MainActivity.this);
        try {
            ds.open();
            currentTask = ds.getSpecificTask(id);
            ds.close();
        } catch (Exception e) {
            Toast.makeText(this, "Load Contact Failed", Toast.LENGTH_LONG).show();
        }

        EditText editSubject = findViewById(R.id.editSubject);
        EditText editNotes = findViewById(R.id.editNotes);
        RadioButton rbHigh = findViewById(R.id.radioHigh);
        RadioButton rbMedium = findViewById(R.id.radioMedium);
        RadioButton rbLow = findViewById(R.id.radioLow);

        editSubject.setText(currentTask.getTaskName());
        editNotes.setText(currentTask.getNotes());
        if (currentTask.getPriority() == "High") {
            rbHigh.setChecked(true);
        }
        else if (currentTask.getPriority() == "Medium") {
            rbMedium.setChecked(true);
        }
        else {
            rbLow.setChecked(true);
        }

    }
}