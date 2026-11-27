package isetb.tp2.tunisiasmartlibrary.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import isetb.tp2.tunisiasmartlibrary.R
import isetb.tp2.tunisiasmartlibrary.auth.AccountActivity
import isetb.tp2.tunisiasmartlibrary.auth.register

class AcceuilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_acceuil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        val tv_register = findViewById<TextView>(R.id.tvregister)
        val tv_account = findViewById<TextView>(R.id.tvaccount)
        val tv_document= findViewById<View>(R.id.navDocument)

        findViewById<TextView>(R.id.tvViewAll).setOnClickListener { toast("View All") }

        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)

        val token = prefs.getString("token", "")

        if(token.equals("")){
            tv_account.visibility = View.GONE
            tv_account.visibility = View.GONE
            tv_register.visibility = View.VISIBLE

        }else{
            tv_account.visibility = View.VISIBLE
            tv_account.visibility = View.VISIBLE
            tv_register.visibility = View.GONE

        }

        tv_register.setOnClickListener {
            startActivity(Intent(this, register::class.java))
            finish()
        }
       tv_document.setOnClickListener {
            startActivity(Intent(this, DocumentsActivity::class.java))
        }

        tv_account.setOnClickListener{
            startActivity( Intent(this, AccountActivity::class.java))
        }
    }
    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}