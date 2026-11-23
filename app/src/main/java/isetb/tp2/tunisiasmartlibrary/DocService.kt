package isetb.tp2.tunisiasmartlibrary

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DocService {
    @GET("/api/categories")
    fun getCategories (): Call<List<Category>>


    @GET("/api/documents")
    fun getDocs (): Call<List<Document>>


}