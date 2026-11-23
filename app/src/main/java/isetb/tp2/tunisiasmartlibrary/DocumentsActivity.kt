package isetb.tp2.tunisiasmartlibrary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DocumentsActivity : AppCompatActivity() {
    private val BASE_URL = "http://127.0.0.1:8081"
    val docService: DocService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DocService::class.java)
    }

    lateinit var categoriesAdapter : ArrayAdapter<String>
    lateinit var docsAdapter : DocsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_documents)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val link_acceuil = findViewById<TextView>(R.id.link_acceuil)
        link_acceuil.setOnClickListener {
            val intent = Intent(this, acceuil::class.java)
            startActivity(intent)

        }

        val etSearch = findViewById<EditText>(R.id.et_search)
        val spCategory = findViewById<Spinner>(R.id.sp_category)
        val rvDocs = findViewById<RecyclerView>(R.id.rv_docs)


        var categories = emptyList<String>()




        docService.getCategories()
            .enqueue(object : retrofit2.Callback<List<Category>> {
                override fun onResponse(
                    call: Call<List<Category>?>,
                    response: Response<List<Category>?>
                ) {
                    categories = response.body()?.map {
                        c -> c.name.toString()
                    } ?: emptyList()
                    categoriesAdapter = ArrayAdapter<String>(this@DocumentsActivity, android.R.layout.simple_spinner_item, categories)
                    spCategory.adapter=categoriesAdapter

                }

                override fun onFailure(
                    call: Call<List<Category>?>,
                    t: Throwable
                ) {
                    Toast.makeText(this@DocumentsActivity, "Faild to get the categories", Toast.LENGTH_SHORT).show()
                }


            })

        rvDocs.layoutManager = GridLayoutManager(this, 2)

        docService.getDocs()
            .enqueue(object : retrofit2.Callback<List<Document>> {
                override fun onResponse(
                    call: Call<List<Document>?>,
                    response: Response<List<Document>?>
                ) {
                    Log.d("DocumentsActivity", "Response: ${response.body()}")
                    val documents = response.body() ?: emptyList()
                    docsAdapter = DocsAdapter(documents){id ->
                        //val intent = Intent(this@DocumentsActivity, DocumentDetailsActivity::class.java)
                        //intent.putExtra("document_id", id)
                        //startActivity(intent)
                    }

                    rvDocs.adapter = docsAdapter

                }

                override fun onFailure(
                    call: Call<List<Document>?>,
                    t: Throwable
                ) {
                    Toast.makeText(this@DocumentsActivity, "Faild to get the documents", Toast.LENGTH_SHORT).show()
                }
            })


    }
}