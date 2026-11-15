package isetb.tp2.tunisiasmartlibrary

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvGoRegister = findViewById<TextView>(R.id.tvGoRegister)
        val tvForgot = findViewById<TextView>(R.id.tvForgot)
        val btn_retour = findViewById<TextView>(R.id.btn_retour)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Email invalide"
                return@setOnClickListener
            }
            if (password.length < 6) {
                etPassword.error = "at last 6 caractères minimum"
                return@setOnClickListener
            }
            startActivity(Intent(this, acceuil::class.java))
            finish()
        }

        tvGoRegister.setOnClickListener {
            startActivity(Intent(this, register::class.java))
        }
        btn_retour.setOnClickListener {
            startActivity(Intent(this, register::class.java))
            finish()
        }

        tvForgot.setOnClickListener {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
        }
    }
}