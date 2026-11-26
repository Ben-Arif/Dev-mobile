package isetb.tp2.tunisiasmartlibrary

import java.util.Date

data class Document(
    val id: Int,
    val auther: String,
    val description: String,
    val image: String,
    val stock: Int,
    val title: String,
    val dateCreation: Date

)
