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

class register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registre)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirm = findViewById<EditText>(R.id.etConfirm)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvGoLogin = findViewById<TextView>(R.id.tvGoLogin)
        val btn_retour= findViewById<Button>(R.id.btn_retour)

        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirm = etConfirm.text.toString()

            when {
                name.isEmpty() -> etName.error = "Nom obligatoire"
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> etEmail.error = "Email invalide"
                password.length < 6 -> etPassword.error = "6 caractères minimum"
                password != confirm -> etConfirm.error = "Les mots de passe ne correspondent pas"
                else -> {
                    Toast.makeText(this, "Compte créé avec succès", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, login::class.java))
                    finish()
                }
            }
        }
        btn_retour.setOnClickListener {
            startActivity(Intent(this, acceuil::class.java))
            finish()
        }
        tvGoLogin.setOnClickListener {
            startActivity(Intent(this, login::class.java))
            finish()
        }
    }
}