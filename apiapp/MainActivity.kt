package com.example.apiapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    private val norrisFactRetriever = FactRetriever()
    lateinit var btnNewFact: Button
    lateinit var txtNorrisFact: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (isNetworkConnected()) {
            getNorrisFact()
        }else{
            AlertDialog.Builder(this).setTitle("No Interwebs Connection")
                    .setMessage("Please check your interweb connection and try again")
                    .setPositiveButton(android.R.string.ok) {_, _ ->}
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
        setContentView(R.layout.activity_main)

        btnNewFact = findViewById(R.id.btnNewFact)
        txtNorrisFact = findViewById(R.id.txtNorrisFact)

        btnNewFact.setOnClickListener {
            if (isNetworkConnected()) {
                getNorrisFact()
            }else{
                AlertDialog.Builder(this).setTitle("No Interwebs Connection")
                    .setMessage("Please check your interweb connection and try again")
                    .setPositiveButton(android.R.string.ok) {_, _ ->}
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
            }
        }
    }

    private fun isNetworkConnected(): Boolean {

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities  = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun getNorrisFact() {
        val mainActivityJob = Job()

        val errorHandler = CoroutineExceptionHandler {_, exception ->
            AlertDialog.Builder(this).setTitle("Error")
                    .setMessage(exception.message)
                    .setPositiveButton(android.R.string.ok) {_, _ ->}
                    .setIcon(android.R.drawable.ic_dialog_alert).show()

        }

        val coroutineScope = CoroutineScope(mainActivityJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) { val result = norrisFactRetriever.getNorrisFact()
            //println("hello" + result.value)
            txtNorrisFact.text = result.value
        }
    }
}