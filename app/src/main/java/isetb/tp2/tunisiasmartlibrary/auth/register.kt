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
import isetb.tp2.tunisiasmartlibrary.DTO.LoginRespense
import isetb.tp2.tunisiasmartlibrary.DTO.RegisterRequest
import isetb.tp2.tunisiasmartlibrary.R
import isetb.tp2.tunisiasmartlibrary.Activities.AcceuilActivity
import isetb.tp2.tunisiasmartlibrary.Services.AuthServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class register : AppCompatActivity() {
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
        setContentView(R.layout.activity_registre)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        val etFirstName = findViewById<EditText>(R.id.etFName)
        val etLastName = findViewById<EditText>(R.id.etLName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirm = findViewById<EditText>(R.id.etConfirm)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvGoLogin = findViewById<TextView>(R.id.tvGoLogin)
        val btn_retour = findViewById<TextView>(R.id.btn_retour)

        btnRegister.setOnClickListener {
            val Fname = etFirstName.text.toString().trim()
            val Lname = etLastName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirm = etConfirm.text.toString()

            when {
                Fname.isEmpty() -> etFirstName.error = "First Name obligated"
                Lname.isEmpty() -> etLastName.error = "Last Name obligated"
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> etEmail.error = "Invalid Email"
                password.length < 6 -> etPassword.error = "Minimum 6 Caracters"
                password != confirm -> etConfirm.error = "Paswword is not defined"
                else -> {
                    authService.register(RegisterRequest(Fname, Lname, email, password))
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

                                val intent = Intent(this@register, AcceuilActivity::class.java)
                                startActivity(intent)

                            }

                            override fun onFailure(
                                call: Call<LoginRespense?>,
                                t: Throwable
                            ) {
                                Toast.makeText(
                                    this@register,
                                    "Invalide Credentials",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }


                        })

                }
            }
        }
        btn_retour.setOnClickListener {
            startActivity(Intent(this, AcceuilActivity::class.java))
            finish()
        }
        tvGoLogin.setOnClickListener {
            startActivity(Intent(this, login::class.java))
            finish()
        }
    }
}