package com.example.appdevproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_settings);
        initListButton();
        initSettingsButton();
        initPriorityButton();
        initSettings();
       initSortByClick();
        initSortOrderClick();
    }

    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonMap);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, ListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        ImageButton ibList = findViewById(R.id.imageButtonSettings);
        ibList.setEnabled(false);
    }

    private void initPriorityButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettings() {
        String sortBy = getSharedPreferences("TaskPreferences",
                Context.MODE_PRIVATE).getString("sortfield","date");
        String sortOrder = getSharedPreferences("TaskPreferences",
                Context.MODE_PRIVATE).getString("sortorder","ASC");

        RadioButton rbDate = findViewById(R.id.radioDate);
        RadioButton rbPriority = findViewById(R.id.radioPriority);
        RadioButton rbSubject = findViewById(R.id.radioSubject);
        if (sortBy.equalsIgnoreCase("date")) {
            rbDate.setChecked(true);
        }
        else if (sortBy.equalsIgnoreCase("priority")) {
            rbPriority.setChecked(true);
        }
        else {
            rbSubject.setChecked(true);
        }

        RadioButton rbAscending = findViewById(R.id.radioAscending);
        RadioButton rbDescending = findViewById(R.id.radioDescending);
        if (sortOrder.equalsIgnoreCase("ASC")) {
            rbAscending.setChecked(true);
        }
        else {
            rbDescending.setChecked(true);
        }
    }

    private void initSortByClick() {
        RadioGroup rgSortBy = findViewById(R.id.radioGroupPriority);
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbDate = findViewById(R.id.radioDate);
                RadioButton rbPriority = findViewById(R.id.radioPriority);
                if (rbDate.isChecked()) {
                    getSharedPreferences("TaskPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "date").apply();
                }
                else if (rbPriority.isChecked()) {
                    getSharedPreferences("TaskPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "priority").apply();
                }
                else {
                    getSharedPreferences("TaskPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortfield", "birthday").apply();
                }
            }
        });
    }

    private void initSortOrderClick() {
        RadioGroup rgSortOrder = findViewById(R.id.radioGroupSortOrder);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbAscending = findViewById(R.id.radioAscending);
                if (rbAscending.isChecked()) {
                    getSharedPreferences("TaskPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortorder", "ASC").apply();
                }
                else {
                    getSharedPreferences("TaskPreferences", Context.MODE_PRIVATE).edit().putString("sortorder", "DESC").apply();
                }
            }
        });
    }
}