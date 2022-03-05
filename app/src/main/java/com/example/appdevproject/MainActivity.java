package com.example.appdevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    private Task currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            initTask(extras.getInt("contactid"));
        }
        else {
            currentTask = new Task();
        }
        initListButton();
        initSettingsButton();
        initPriorityButton();
        initTextChangedEvents();
        initPrioByClick();
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
                }
                else if (rbMedium.isChecked()) {
                    currentTask.setPriority("Medium");
                }
                else {
                    currentTask.setPriority("Low");
                }
            }
        });
    }
}