package isetb.tp2.tunisiasmartlibrary

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface DocService {
    @GET("/api/categories")
    fun getCategories (): Call<List<Category>>


    @GET("/api/documents")
    fun getDocs(
        @Query("searchPattern") searchPattern: String? = null,
        @Query("categoryId") categoryId: Long? = null
    ): Call<List<Document>>


    @GET("/api/documents/{id}")
    fun getDocById(@Path("id") id: Int): Call<Document>


}