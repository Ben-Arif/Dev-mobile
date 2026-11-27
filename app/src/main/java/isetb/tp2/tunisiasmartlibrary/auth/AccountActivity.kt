package isetb.tp2.tunisiasmartlibrary.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import isetb.tp2.tunisiasmartlibrary.Activities.AcceuilActivity
import isetb.tp2.tunisiasmartlibrary.Activities.InterceptorApi
import isetb.tp2.tunisiasmartlibrary.DTO.Document
import isetb.tp2.tunisiasmartlibrary.DTO.User
import isetb.tp2.tunisiasmartlibrary.R
import isetb.tp2.tunisiasmartlibrary.Services.AuthServiceApi
import isetb.tp2.tunisiasmartlibrary.Services.DocServiceApi
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AccountActivity : AppCompatActivity() {

    private val BASE_URL = "http://127.0.0.1:8081"





    private val client: OkHttpClient by lazy {
        InterceptorApi(applicationContext).client
    }

    val authServiceApi: AuthServiceApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthServiceApi::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val etfirstName=findViewById<EditText>(R.id.first_name)
        val etlastName=findViewById<EditText>(R.id.last_name)
        val etemail=findViewById<EditText>(R.id.email)
        val btnsave=findViewById<Button>(R.id.save)
        val btnBack=findViewById<Button>(R.id.back)
        val btnLogOut=findViewById<Button>(R.id.logout)


        authServiceApi.getUser()
            .enqueue(object : Callback<User>{
                override fun onResponse(
                    call: Call<User?>,
                    response: Response<User?>
                ) {
                    val user=response.body()
                    Log.d("user -------------", user?.firstName.toString())
                    etfirstName.setText(user?.firstName)
                    etlastName.setText(user?.lastName)
                    etemail.setText(user?.email)

                }

                override fun onFailure(
                    call: Call<User?>,
                    t: Throwable
                ) {
                    TODO("Not yet implemented")
                }

            })


        btnsave.setOnClickListener {
            val firstName=etfirstName.text.toString().trim()
            val lastName=etlastName.text.toString().trim()
            val email=etemail.text.toString().trim()

            authServiceApi.change(User(firstName,lastName,email))
                .enqueue(object : Callback<Void>{
                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        Log.d(".............response",response.code().toString())
                        Toast.makeText(this@AccountActivity, "User saved Successfuly", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(
                        call: Call<Void>,
                        t: Throwable
                    ) {
                        Log.d(".............error",t.message.toString())
                        Toast.makeText(this@AccountActivity, t.message, Toast.LENGTH_SHORT).show()
                    }

                })
        }


        btnBack.setOnClickListener {
            finish()
        }

        btnLogOut.setOnClickListener {

            val prefs=getSharedPreferences("prefs",MODE_PRIVATE)
            prefs.edit().clear().apply()
            startActivity(Intent(this@AccountActivity, AcceuilActivity::class.java))
        }










    }
}