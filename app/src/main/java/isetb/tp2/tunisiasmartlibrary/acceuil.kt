package isetb.tp2.tunisiasmartlibrary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class acceuil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_acceuil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
        mapOf(
            R.id.catEbooks to "E-Books",
            R.id.catCategories to "Categories",
            R.id.catAudio to "Audio Books",
            R.id.catNovels to "Novels",
            R.id.catMostRead to "Most Read"
        ).forEach { (id, label) ->
            findViewById<LinearLayout>(id).setOnClickListener { toast(label) }
        }

        findViewById<TextView>(R.id.tvViewAll).setOnClickListener { toast("View All") }

        findViewById<TextView>(R.id.tvregister).setOnClickListener {
            startActivity(Intent(this, register::class.java))
            finish()
        }
    }
    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}