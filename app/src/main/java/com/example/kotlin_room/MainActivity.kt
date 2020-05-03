package com.example.kotlin_room

import android.app.Activity
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var cmdViewModel: CommandViewModel
    private val newCmdActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = CommandListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        cmdViewModel = ViewModelProvider(this).get(CommandViewModel::class.java)
        cmdViewModel.allCmd.observe(this, Observer { commands ->
            commands?.let{adapter.setCommands(it)}
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewCommandActivity::class.java)
            startActivityForResult(intent, newCmdActivityRequestCode)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newCmdActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewCommandActivity.EXTRA_REPLY)?.let {
                val cmd = Command("main",it,"description", UUID.randomUUID().toString()+"0000000")
                cmdViewModel.insert(cmd)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }
}
