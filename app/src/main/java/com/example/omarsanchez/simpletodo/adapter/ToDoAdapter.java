package com.example.omarsanchez.simpletodo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.omarsanchez.simpletodo.activities.DetailActivity;
import com.example.omarsanchez.simpletodo.activities.MainActivity;
import com.example.omarsanchez.simpletodo.R;
import com.example.omarsanchez.simpletodo.model.Task;
import com.example.omarsanchez.simpletodo.util.Priority;

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

    public void removeTask(int position) {
        tasks.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tasks.size());
    }

    public void updateTask(Task task,int position){
        tasks.set(position, task);
        notifyItemChanged(position);
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
        TextView taskName, priority;
        Context context;

        public ToDoHolder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    adapter.removeTask(getAdapterPosition());
                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(context.getString(R.string.detail), tasks.get(getAdapterPosition()));
                    bundle.putInt(context.getString(R.string.position),getAdapterPosition());
                    intent.putExtras(bundle);
                    ((MainActivity)context).startActivityForResult(intent, MainActivity.DETAIL_REQUEST);
                }
            });
            taskName = (TextView) itemView.findViewById(R.id.task_list_name);
            priority = (TextView) itemView.findViewById(R.id.task_list_priority);
            context = itemView.getContext();
        }

        public void bind(Task task) {
            taskName.setText(task.getTaskName());
            priority.setText(task.getPriority().name());
            priority.setTextColor(getTextColor(task.getPriority()));
        }

        private int getTextColor(Priority priority) {
            int textColor;
            switch (priority) {
                case High:
                    textColor = ContextCompat.getColor(context, R.color.high);
                    break;
                case Medium:
                    textColor = ContextCompat.getColor(context, R.color.medium);
                    break;
                case Low:
                    textColor = ContextCompat.getColor(context, R.color.low);
                    break;
                default:
                    textColor = ContextCompat.getColor(context, R.color.low);
                    break;
            }
            return textColor;
        }

    }
}
