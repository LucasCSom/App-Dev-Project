package com.example.appdevproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    TaskAdapter taskAdapter;

    RecyclerView taskList;
    ArrayList<Task> tasks;
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            int taskId = tasks.get(position).getTaskID();
            Intent intent = new Intent(ListActivity.this, MainActivity.class);
            intent.putExtra("taskId", taskId);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_list);
        initListButton();
        initPriorityButton();
        initSettingsButton();
        initDeleteSwitch();
    }

    @Override
    public void onResume() {
        super.onResume();

        String sortBy = getSharedPreferences("MyTaskListPreferences", Context.MODE_PRIVATE).getString("sortfield","taskname");
        String sortOrder = getSharedPreferences("MyTaskListPreferences", Context.MODE_PRIVATE).getString("sortorder","ASC");
        TaskDataSource ds = new TaskDataSource(this);

        try {
            ds.open();
            tasks = ds.getTasks(sortBy,sortOrder);
            ds.close();
            taskList = findViewById(R.id.rvTasks);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            taskList.setLayoutManager(layoutManager);
            TaskAdapter taskAdapter = new TaskAdapter(tasks, this);
            taskAdapter.setOnItemClickListener(onItemClickListener);
            taskList.setAdapter(taskAdapter);
        }
        catch (Exception e) {
            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();
        }

    }

    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonMap);
        ibList.setEnabled(false);

    }

    private void initSettingsButton() {
        ImageButton ibList = findViewById(R.id.imageButtonSettings);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initPriorityButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initDeleteSwitch() {
        Switch s = findViewById(R.id.switchDelete);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean status = compoundButton.isChecked();
                taskAdapter.setDelete(status);
                taskAdapter.notifyDataSetChanged();
            }
        });
    }
}