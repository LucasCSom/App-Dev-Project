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
    private ArrayList<Task> contactData;
    private View.OnClickListener mOnItemClickListener;
    private boolean isDeleting;
    private Context parentContext;

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewContact;
        public TextView textPhone;
        public Button deleteButton;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewContact = itemView.findViewById(R.id.textContactName);
            textPhone = itemView.findViewById(R.id.textPhoneNumber);
            deleteButton = itemView.findViewById(R.id.buttonDeleteContact);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public TextView getContactTextView() {
            return textViewContact;
        }
        public TextView getPhoneTextView() {
            return textPhone;
        }
        public Button getDeleteButton() {
            return deleteButton;
        }
    }

    public TaskAdapter(ArrayList<Task> arrayList, Context context) {
        contactData = arrayList;
        parentContext = context;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ContactViewHolder cvh = (ContactViewHolder) holder;
        cvh.getContactTextView().setText(contactData.get(position).getTaskName());
        cvh.getPhoneTextView().setText(contactData.get(position).getNotes());
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
        return contactData.size();
    }

    private void deleteItem(int position) {
        Task contact = contactData.get(position);
        TaskDataSource ds = new TaskDataSource(parentContext);
        try {
            ds.open();
            boolean didDelete = ds.deleteTask(contact.getTaskID());
            ds.close();
            if (didDelete) {
                contactData.remove(position);
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
        isDeleting = b;
    }
}
