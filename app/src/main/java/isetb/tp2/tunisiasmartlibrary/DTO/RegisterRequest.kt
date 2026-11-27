package isetb.tp2.tunisiasmartlibrary.DTO

data class RegisterRequest(
    val nom: String,
    val prenom: String,
    val email: String,
    val password: String
)