package com.example.roomdb_alifaa_04

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdb_alifaa_04.room.Constant
import com.example.roomdb_alifaa_04.room.Reminder
import com.example.roomdb_alifaa_04.room.ReminderDB
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { ReminderDB(this) }
    lateinit var reminderAdapter: ReminderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadReminder()
    }

    fun loadReminder(){
        CoroutineScope(Dispatchers.IO).launch {
            val reminder = db.ReminderDao().getReminders()
            Log.d("MainActivity",  "dbresponse $reminder")
            withContext(Dispatchers.Main){
                reminderAdapter.setData(reminder)
            }
        }
    }

    fun setupListener(){
        add_reminder.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(reminderId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, AddActivity::class.java)
                .putExtra("intent_id", reminderId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView() {
        reminderAdapter = ReminderAdapter(arrayListOf(), object : ReminderAdapter.OnAdapterListener {
            override fun onClick(reminder: Reminder) {
                // read detail reminder
                intentEdit(reminder.id, Constant.TYPE_READ)
            }

            override fun onUpdate(reminder: Reminder) {
                intentEdit(reminder.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(reminder: Reminder) {
                deleteDialog(reminder)
            }
        })
        rv_reminder.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = reminderAdapter
        }
    }

    private fun deleteDialog(reminder: Reminder) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Apakah anda ingin menghapus Reminder -${reminder.title}-?")
            setNegativeButton("Cancel") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Delete") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.ReminderDao().deleteReminder(reminder)
                    loadReminder()
                }
            }
        }
        alertDialog.show()
    }
}