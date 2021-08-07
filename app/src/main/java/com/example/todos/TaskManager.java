package com.example.todos;

import android.content.Context;
import android.media.SoundPool;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Comparator;

public class TaskManager {

    ArrayList<Task> taskList;
    SoundPool soundPool;
    int soundIdAdded;
    int soundIdRemoved;

    public TaskManager(Context context, SoundPool soundPool, int soundIdAdded, int soundIdRemoved) {
        Hawk.init(context).build();
        loadTaskList();
        this.soundPool = soundPool;
        this.soundIdAdded = soundIdAdded;
        this.soundIdRemoved = soundIdRemoved;
    }

    public void addTask(Task task) {
        taskList.add(task);
        saveTaskList();
        soundPool.play(soundIdAdded, 1, 1, 0, 0, 1);
    }

    public void removeTask(Task task) {
        taskList.remove(task);
        saveTaskList();
        soundPool.play(soundIdRemoved, 1, 1, 0, 0, 1);
    }

    public void removeTask(int position) {
        taskList.remove(position);
        saveTaskList();
        soundPool.play(soundIdRemoved, 1, 1, 0, 0, 1);
    }

    public void removeAll() {
        taskList.clear();
        saveTaskList();
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public int getTaskCount() {
        return taskList.size();
    }

    protected void saveTaskList() {
        Hawk.put("taskList", taskList);
    }

    protected void loadTaskList() {
        taskList = Hawk.get("taskList", new ArrayList<>());
    }

    public void moveTask(int oldPosition, int newPosition) {
        Task task = taskList.get(oldPosition);
        taskList.remove(task);
        taskList.add(newPosition, task);
        saveTaskList();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortTaskList() {
        Comparator<Task> compareNames = new Comparator<Task>() {

            @Override
            public int compare(Task o1, Task o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };

        taskList.sort(compareNames);

    }

    public String getAllTasks() {

        StringBuilder sb = new StringBuilder();

        for(Task task : taskList) {
            sb.append("- " + task.name + "\n");
        }
        return sb.toString();
    }

}
