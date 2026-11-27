package isetb.tp2.tunisiasmartlibrary.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import isetb.tp2.tunisiasmartlibrary.DTO.Document
import isetb.tp2.tunisiasmartlibrary.DTO.ReservationRequest
import isetb.tp2.tunisiasmartlibrary.R
import isetb.tp2.tunisiasmartlibrary.Services.DocServiceApi
import isetb.tp2.tunisiasmartlibrary.Services.ReservationServiceApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DocumentDetailsActivity : AppCompatActivity() {
    var documentId:Int = 0
    private val BASE_URL = "http://127.0.0.1:8081"

    private val client: OkHttpClient by lazy {
        InterceptorApi(applicationContext).client
    }

    val docService: DocServiceApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DocServiceApi::class.java)
    }
    val reservationService: ReservationServiceApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReservationServiceApi::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_document_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }


        val img = findViewById<ImageView>(R.id.img_detail)
        val tvTitle = findViewById<TextView>(R.id.tv_detail_title)
        val tvAuthor = findViewById<TextView>(R.id.auther)
        val tvAvailablity = findViewById<TextView>(R.id.availablity)
        val tvBookType = findViewById<TextView>(R.id.book_type)
        val tvIsbn = findViewById<TextView>(R.id.isbn)
        val tvYear = findViewById<TextView>(R.id.publish_year)
        val tvDescription = findViewById<TextView>(R.id.tv_detail_description)
        val btnOrder = findViewById<Button>(R.id.btn_order)
        val tvCat  = findViewById<TextView>(R.id.category)
        val tvPublisher=findViewById<TextView>(R.id.publisher)


        documentId = intent.getIntExtra("document_id", 0)

        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)

        val token = prefs.getString("token", "")

        if(token.equals("")){
            btnOrder.visibility = View.GONE

        }else{

            btnOrder.visibility = View.VISIBLE

        }



        docService.getDocById(documentId)
            .enqueue(object : Callback<Document> {
                override fun onResponse(
                    call: Call<Document?>,
                    response: Response<Document?>
                ) {
                    Log.d("header -----------", call.request().header("Authorization").toString())
                    val doc = response.body()
                    documentId=doc!!.id

                    if (doc != null) {
                        tvTitle.text = doc.title
                        tvAuthor.text = doc.auther
                        tvPublisher.text = doc.publisher
                        tvIsbn.text = doc.isbn
                        tvBookType.text=doc.book_Type
                        if (doc.stock>0){
                            tvAvailablity.text="Available"
                            tvAvailablity.setTextColor(getResources().getColor(R.color.green))
                        }
                        else{
                            tvAvailablity.text="not Available !"
                            tvAvailablity.setTextColor(getResources().getColor(R.color.red))
                        }

                        tvYear.text = doc.publish_Year.toString()
                        tvDescription.text = doc.description
                        tvCat.text = doc.category.name




                        Glide.with(this@DocumentDetailsActivity)
                            .load("http://127.0.0.1:8081/api/documents/files/" + doc.image)
                            .into(img)
                    } else {
                        Toast.makeText(this@DocumentDetailsActivity, "Document introuvable", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(
                    call: Call<Document?>,
                    t: Throwable
                ) {
                    Toast.makeText(this@DocumentDetailsActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })

        btnOrder.setOnClickListener {
            reservationService.createReservation(
                ReservationRequest(documentId)
            )
                .enqueue(object : Callback<Void>{
                    override fun onResponse(
                        call: Call<Void?>,
                        response: Response<Void?>
                    ) {
                        if (response.code().equals(403)){
                            Toast.makeText(this@DocumentDetailsActivity, "you are not authenticated!!", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this@DocumentDetailsActivity, "success reservation", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(
                        call: Call<Void?>,
                        t: Throwable
                    ) {
                        Toast.makeText(this@DocumentDetailsActivity, t.message, Toast.LENGTH_SHORT).show()

                    }

                })

        }



    }
}