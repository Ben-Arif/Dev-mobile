package isetb.tp2.tunisiasmartlibrary

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/auth/login")
    fun login (@Body loginRequest: LoginRequest): Call<LoginRespense>

    @POST("/api/auth/register")
    fun register (@Body RegisterRequest: RegisterRequest): Call<LoginRespense>
}
