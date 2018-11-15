package br.go.dpn.digitalize.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import br.go.dpn.digitalize.R

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnGoodMorning, R.id.btnGoodAfternoon, R.id.btnGoodNight -> loadSearch(v!!.id)
        }
    }

    fun loadSearch(btnId: Int) {
        val button = findViewById<Button>(btnId)
        val searchTerm = button.text.toString()
        val i = Intent(this, SearchImageActivity::class.java)
        i.putExtra("searchTerm", searchTerm)
        startActivity(i)
    }

    fun setupView() {
        findViewById<Button>(R.id.btnGoodMorning).setOnClickListener(this)
        findViewById<Button>(R.id.btnGoodAfternoon).setOnClickListener(this)
        findViewById<Button>(R.id.btnGoodNight).setOnClickListener(this)
    }
}
