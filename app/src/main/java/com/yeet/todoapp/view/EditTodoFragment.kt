package com.yeet.todoapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.yeet.todoapp.R
import com.yeet.todoapp.viewmodel.DetailTodoViewModel

class EditTodoFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtJudul: TextView = view.findViewById(R.id.txtTitleTodo)
        val txtNotes: TextView = view.findViewById(R.id.txtNotes)
        val btn: Button = view.findViewById(R.id.btnAdd)
        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid


        txtJudul.text = "Edit Todo"
        btn.text = "Save Changes"

        btn.setOnClickListener {
            val rgPriority = view.findViewById<RadioGroup>(R.id.rgPriority)
            val radio = view.findViewById<RadioButton>(rgPriority.checkedRadioButtonId)
            viewModel.update(txtJudul.text.toString(), txtNotes.text.toString(),
                radio.tag.toString().toInt(), uuid)
            Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }


        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        viewModel.fetch(uuid)
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            val txtTitle = requireView().findViewById<EditText>(R.id.txtTitle)
            val txtNotes = requireView().findViewById<EditText>(R.id.txtNotes)

            txtTitle.setText(it.title)
            txtNotes.setText(it.notes)

            val radioLow = requireView().findViewById<RadioButton>(R.id.radioLow)
            val radioMedium = requireView().findViewById<RadioButton>(R.id.radioMedium)
            val radioHigh = requireView().findViewById<RadioButton>(R.id.radioHigh)

            when (it.priority) {
                1 -> radioLow.isChecked = true
                2 -> radioMedium.isChecked = true
                else -> radioHigh.isChecked = true
            }

        })
    }

}