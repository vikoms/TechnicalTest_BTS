package com.example.technicaltest_bts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.technicaltest_bts.adapter.CheckListAdapter
import com.example.technicaltest_bts.viewmodel.ChecklistViewModel
import kotlinx.android.synthetic.main.activity_checklist.*

class ChecklistActivity : AppCompatActivity() {

    private val token = LoginActivity.token
    lateinit var viewModel: ChecklistViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checklist)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ChecklistViewModel::class.java]
        viewModel.setData(token)
        setUpRecyclerView()

        btn_add_checklist.setOnClickListener {
            val intent = Intent(this@ChecklistActivity, FormChecklistActivity::class.java)
            startActivity(intent)
        }

        btn_refresh.setOnClickListener {
            viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ChecklistViewModel::class.java]
            viewModel.setData(token)
            setUpRecyclerView()
        }
    }

    private fun setUpRecyclerView() {

        val checkListAdapter = CheckListAdapter()
        viewModel.getData().observe(this, Observer { checkList ->
            checkListAdapter.setData(checkList)
        })

        with(rv_checklist) {
            layoutManager = LinearLayoutManager(this@ChecklistActivity)
            adapter = checkListAdapter
        }
    }
}