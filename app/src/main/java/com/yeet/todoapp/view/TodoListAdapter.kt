package com.yeet.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.yeet.todoapp.R
import com.yeet.todoapp.model.Todo
import com.yeet.todoapp.viewmodel.ListTodoViewModel

class TodoListAdapter(val todoList:ArrayList<Todo>, val adapterOnClick : (Todo) -> Unit)
    :RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    class TodoViewHolder(var view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_todo_layout, parent, false)

        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        var checktask = holder.view.findViewById<CheckBox>(R.id.checkTask)
        checktask.text = todoList[position].title

        checktask.setOnCheckedChangeListener { compoundButton, b ->
            adapterOnClick(todoList[position])
        }

    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

}