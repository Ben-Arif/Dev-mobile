package isetb.tp2.tunisiasmartlibrary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
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
import kotlin.collections.emptyList

class DocumentsActivity : AppCompatActivity() {
    private val BASE_URL = "http://127.0.0.1:8081"
    val docService: DocService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DocService::class.java)
    }

    lateinit var categoriesAdapter : ArrayAdapter<Category>
    var categories = mutableListOf<Category>()
    lateinit var docsAdapter : DocsAdapter
    var documents = mutableListOf<Document>()


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
        val btn_search = findViewById<ImageButton>(R.id.btn_search)
        val spCategory = findViewById<Spinner>(R.id.sp_category)
        val rvDocs = findViewById<RecyclerView>(R.id.rv_docs)


        categoriesAdapter = ArrayAdapter<Category>(this@DocumentsActivity, android.R.layout.simple_spinner_item, categories)
        spCategory.adapter=categoriesAdapter

        docService.getCategories()
            .enqueue(object : retrofit2.Callback<List<Category>> {
                override fun onResponse(
                    call: Call<List<Category>?>,
                    response: Response<List<Category>?>
                ) {

                    categories.add(Category(0, "All"))
                    categories.addAll( response.body() ?: emptyList())
                    categoriesAdapter.notifyDataSetChanged()
                }

                override fun onFailure(
                    call: Call<List<Category>?>,
                    t: Throwable
                ) {
                    Toast.makeText(this@DocumentsActivity, t.message, Toast.LENGTH_SHORT).show()
                }


            })




        docsAdapter = DocsAdapter(documents){id ->
            val intent = Intent(this@DocumentsActivity, DocumentDetailsActivity::class.java)
            intent.putExtra("document_id", id)
            startActivity(intent)
        }

        rvDocs.adapter = docsAdapter

        rvDocs.layoutManager = GridLayoutManager(this@DocumentsActivity, 2)

        getAllDocs()

        btn_search.setOnClickListener {
            val pattern = etSearch.text.trim().toString()
            search(pattern)
        }


        spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                // Get the selected item
                val selectedCategory = parent?.getItemAtPosition(position) as? Category
                Log.d("selectedCategory ----- ", selectedCategory.toString())
                if(selectedCategory?.name!!.equals("All")){
                    Log.d("Enter to 1 ----- ", selectedCategory.toString())
                    getAllDocs()
                }else{
                    Log.d("Enter to 2 ----- ", selectedCategory.toString())
                    filterByCategory(selectedCategory!!.id)
                }


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }


    }

    fun getAllDocs(){
        docService.getDocs()
            .enqueue(object : retrofit2.Callback<List<Document>> {
                override fun onResponse(
                    call: Call<List<Document>?>,
                    response: Response<List<Document>?>
                ) {
                    documents.clear()
                    documents.addAll(response.body()?.toMutableList() ?: mutableListOf<Document>())
                    docsAdapter.notifyDataSetChanged()


                }

                override fun onFailure(
                    call: Call<List<Document>?>,
                    t: Throwable
                ) {
                    Toast.makeText(this@DocumentsActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })

    }

    fun search(pattern: String){

        docService.getDocs(pattern)
            .enqueue(object : retrofit2.Callback<List<Document>> {
                override fun onResponse(
                    call: Call<List<Document>?>,
                    response: Response<List<Document>?>
                ) {
                    documents.clear()
                    documents.addAll(response.body()?.toMutableList() ?: mutableListOf<Document>())
                    docsAdapter.notifyDataSetChanged()


                }

                override fun onFailure(
                    call: Call<List<Document>?>,
                    t: Throwable
                ) {
                    Toast.makeText(this@DocumentsActivity, "Faild to search", Toast.LENGTH_SHORT).show()
                }
            })


    }

    fun filterByCategory(categoryId: Long){

        docService.getDocs(
            categoryId = categoryId
        )
            .enqueue(object : retrofit2.Callback<List<Document>> {
                override fun onResponse(
                    call: Call<List<Document>?>,
                    response: Response<List<Document>?>
                ) {
                    documents.clear()
                    documents.addAll(response.body()?.toMutableList() ?: mutableListOf<Document>())
                    docsAdapter.notifyDataSetChanged()


                }

                override fun onFailure(
                    call: Call<List<Document>?>,
                    t: Throwable
                ) {
                    Toast.makeText(this@DocumentsActivity, "Faild to search", Toast.LENGTH_SHORT).show()
                }
            })


    }
}