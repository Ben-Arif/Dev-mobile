package isetb.tp2.tunisiasmartlibrary.DTO

data class LoginRespense(
    val token: String,
    val nomComplet: String,
    val role: String,
)