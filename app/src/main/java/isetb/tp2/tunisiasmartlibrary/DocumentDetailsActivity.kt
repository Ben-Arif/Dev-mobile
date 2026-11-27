package isetb.tp2.tunisiasmartlibrary

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DocumentDetailsActivity : AppCompatActivity() {

    private val BASE_URL = "http://127.0.0.1:8081"
    val docService: DocService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DocService::class.java)
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
        val tvAuthor = findViewById<TextView>(R.id.tv_detail_author)
        val tvStock = findViewById<TextView>(R.id.tv_detail_stock)
        val tvDate = findViewById<TextView>(R.id.tv_detail_date)
        val tvDescription = findViewById<TextView>(R.id.tv_detail_description)
        val btnBack = findViewById<Button>(R.id.btn_back)


        val documentId = intent.getIntExtra("document_id", 0)


        docService.getDocById(documentId)
            .enqueue(object : retrofit2.Callback<Document> {
                override fun onResponse(
                    call: Call<Document?>,
                    response: Response<Document?>
                ) {
                    val doc = response.body()
                    if (doc != null) {
                        tvTitle.text = doc.title
                        tvAuthor.text = "Auteur : ${doc.auther}"
                        tvStock.text = "Stock : ${doc.stock}"
                        tvDate.text = "Date : ${doc.dateCreation}"
                        tvDescription.text = doc.description

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


        btnBack.setOnClickListener {
            finish()
        }
    }
}