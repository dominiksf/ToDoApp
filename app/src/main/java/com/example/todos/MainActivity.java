package com.example.todos;

import android.content.Intent;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText newTaskEditText;
    ImageView addButton;
    TaskManager taskManager;
    DarkModeManager darkModeManager;

    SoundPool soundPool;
    int soundIdAdded;
    int soundIdRemoved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        darkModeManager = new DarkModeManager(this);
        setContentView(R.layout.activity_main);
        initSounds();
        taskManager = new TaskManager(this, soundPool, soundIdAdded, soundIdRemoved);
        initViews();
        initClickListener();
    }

    protected void initViews() {
        newTaskEditText = findViewById(R.id.new_task);
        addButton = findViewById(R.id.add_button);
        recyclerView = findViewById(R.id.task_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TaskListAdapter(taskManager));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TaskListTouchCallback(taskManager, recyclerView));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    protected void initClickListener() {
        addButton.setOnClickListener(view -> onAddButtonClick());
    }

    protected void initSounds() {
        soundPool = new SoundPool.Builder().build();
        soundIdAdded = soundPool.load(this, R.raw.added, 1);
        soundIdRemoved = soundPool.load(this, R.raw.removed, 1);
    }

    protected void onAddButtonClick() {
        String newTaskName = newTaskEditText.getText().toString();
        if (newTaskName.length() > 0) {
            Task task = new Task();
            task.setName(newTaskName);
            taskManager.addTask(task);
            recyclerView.getAdapter().notifyItemInserted(taskManager.getTaskCount() - 1);
            newTaskEditText.getText().clear();
            Log.d("ToDos", "Neue Aufgabe: " + newTaskName);
        } else
            Toast.makeText(this, R.string.toast_name_empty, Toast.LENGTH_SHORT).show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.dark_mode)
            darkModeManager.toggle();

        if(item.getItemId() == R.id.clear_all) {
           taskManager.removeAll();
           recyclerView.getAdapter().notifyDataSetChanged();
        }

        if(item.getItemId() == R.id.abc) {
         taskManager.sortTaskList();
         recyclerView.getAdapter().notifyDataSetChanged();

        }

        if(item.getItemId() == R.id.share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, taskManager.getAllTasks());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Intent shareIntent = Intent.createChooser(intent, "Teilen");
            startActivity(shareIntent);
        }

        return true;
    }
}