package com.example.todos;

import android.content.ClipData;
import android.media.SoundPool;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class TaskListTouchCallback extends ItemTouchHelper.SimpleCallback {

    TaskManager taskManager;
    RecyclerView recyclerView;

    public TaskListTouchCallback(TaskManager taskManager, RecyclerView recyclerView) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT);
        this.taskManager = taskManager;
        this.recyclerView = recyclerView;
    }

    // Element wird durch Drag & Drop bewegt
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int oldPosition = viewHolder.getAdapterPosition();
        int newPosition = target.getAdapterPosition();
        taskManager.moveTask(oldPosition, newPosition);
        recyclerView.getAdapter().notifyItemMoved(oldPosition, newPosition);
        return true;
    }

    // Element wird geswiped
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        taskManager.removeTask(position);
        recyclerView.getAdapter().notifyItemRemoved(position);

    }


}
