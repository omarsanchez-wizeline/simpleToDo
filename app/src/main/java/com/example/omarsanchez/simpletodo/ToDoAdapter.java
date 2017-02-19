package com.example.omarsanchez.simpletodo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by omarsanchez on 2/19/17.
 */

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoHolder> {
    private ArrayList<Task> tasks;
    private static ToDoAdapter adapter;

    private ToDoAdapter() {
        tasks = new ArrayList<>();
    }

    public static ToDoAdapter getInstance() {
        if (ToDoAdapter.adapter == null) {
            ToDoAdapter.adapter = new ToDoAdapter();
        }
        return ToDoAdapter.adapter;

    }


    public void addTask(Task task) {
        tasks.add(task);
        notifyDataSetChanged();
    }

    public void removeTast(int position) {
        tasks.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tasks.size());
    }

    @Override
    public ToDoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task_layout, parent, false);
        return new ToDoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ToDoHolder holder, int position) {
        holder.bind(tasks.get(position));

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ToDoHolder extends RecyclerView.ViewHolder {
        TextView taskName;

        public ToDoHolder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    adapter.removeTast(getAdapterPosition());
                    return true;
                }
            });
            taskName = (TextView) itemView.findViewById(R.id.task_list_name);
        }

        public void bind(Task task) {
            taskName.setText(task.getTaskName());
        }
    }
}
