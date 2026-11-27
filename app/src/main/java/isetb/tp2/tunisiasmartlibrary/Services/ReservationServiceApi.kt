package isetb.tp2.tunisiasmartlibrary.Services

import isetb.tp2.tunisiasmartlibrary.DTO.Category
import isetb.tp2.tunisiasmartlibrary.DTO.ReservationRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ReservationServiceApi {
    @GET("/api/reservations")
    fun getReservation (): Call<List<Category>>

    @POST("/api/reservations")
    fun createReservation (@Body reservationRequest: ReservationRequest): Call<Void>




}