package isetb.tp2.tunisiasmartlibrary.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import isetb.tp2.tunisiasmartlibrary.DTO.LoginRequest
import isetb.tp2.tunisiasmartlibrary.DTO.LoginRespense
import isetb.tp2.tunisiasmartlibrary.R
import isetb.tp2.tunisiasmartlibrary.Activities.AcceuilActivity
import isetb.tp2.tunisiasmartlibrary.Services.AuthServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class login : AppCompatActivity() {
    private val BASE_URL = "http://127.0.0.1:8081"
    val authService: AuthServiceApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthServiceApi::class.java)
    }
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
            authService.login(LoginRequest(email, password))
                .enqueue(object : Callback<LoginRespense> {
                    override fun onResponse(
                        call: Call<LoginRespense?>,
                        response: Response<LoginRespense?>
                    )
                    {

                        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
                        prefs.edit()
                            .putString("token", response.body()?.token)
                            .apply()

                        val intent = Intent(this@login, AcceuilActivity::class.java)
                        startActivity(intent)

                    }

                    override fun onFailure(
                        call: Call<LoginRespense?>,
                        t: Throwable
                    ) {
                        Toast.makeText(
                            this@login,
                            "Invalide Credentials",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                })
            startActivity(Intent(this, AcceuilActivity::class.java))
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