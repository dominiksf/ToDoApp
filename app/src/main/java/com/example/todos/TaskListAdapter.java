package com.example.todos;

import android.media.SoundPool;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskListAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    TaskManager taskManager;

    public TaskListAdapter(TaskManager taskManager) {
        this.taskManager = taskManager;

    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskManager.getTaskList().get(position);
        holder.checkBox.setText(task.getName());
        holder.checkBox.setChecked(false);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> onCheckedChanged(isChecked, holder.getLayoutPosition()));
    }

    protected void onCheckedChanged(boolean isChecked, int position) {
        if(isChecked) {
            removeTask(position);
        }
    }

    public void removeTask(int position) {
        taskManager.removeTask(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return taskManager.getTaskCount();
    }

}
