package isetb.tp2.tunisiasmartlibrary.Services

import isetb.tp2.tunisiasmartlibrary.DTO.LoginRequest
import isetb.tp2.tunisiasmartlibrary.DTO.LoginRespense
import isetb.tp2.tunisiasmartlibrary.DTO.RegisterRequest
import isetb.tp2.tunisiasmartlibrary.DTO.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthServiceApi {
    @POST("/api/auth/login")
    fun login (@Body loginRequest: LoginRequest): Call<LoginRespense>

    @POST("/api/auth/register")
    fun register (@Body RegisterRequest: RegisterRequest): Call<LoginRespense>


    @GET("/api/users/profile")
    fun getUser (): Call<User>

    @PUT("/api/users/profile")
    fun change(@Body user: User): Call<Void>


}