package com.example.appdevproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter{
    private static boolean isDeleting;
    private ArrayList<Task> taskData;
    private View.OnClickListener mOnItemClickListener;
    private Context parentContext;

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTask;
        public TextView textPriority;
        public Button deleteButton;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.textTaskSubject);
            textPriority = itemView.findViewById(R.id.textTaskPriority);
            deleteButton = itemView.findViewById(R.id.buttonDeleteTask);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public TextView getContactTextView() {
            return textViewTask;
        }
        public TextView getPhoneTextView() {
            return textPriority;
        }
        public Button getDeleteButton() {
            return deleteButton;
        }
    }

    public TaskAdapter(ArrayList<Task> arrayList, Context context) {
        taskData = arrayList;
        parentContext = context;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        TaskViewHolder cvh = (TaskViewHolder) holder;
        cvh.getContactTextView().setText(taskData.get(position).getTaskName());
        cvh.getPhoneTextView().setText(taskData.get(position).getPriority());
        if (isDeleting) {
            cvh.getDeleteButton().setVisibility(View.VISIBLE);
            cvh.getDeleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(position);
                }
            });
        }
        else {
            cvh.getDeleteButton().setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return taskData.size();
    }

    private void deleteItem(int position) {
        Task contact = taskData.get(position);
        TaskDataSource ds = new TaskDataSource(parentContext);
        try {
            ds.open();
            boolean didDelete = ds.deleteTask(contact.getTaskID());
            ds.close();
            if (didDelete) {
                taskData.remove(position);
                notifyDataSetChanged();
            }
            else {
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception e) {

        }
    }

    public static void setDelete(boolean b) {
        //weird error? might need rebuild by android studio or something
        isDeleting = b;
    }
}
