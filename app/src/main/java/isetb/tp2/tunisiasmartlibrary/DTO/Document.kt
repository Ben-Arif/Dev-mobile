package isetb.tp2.tunisiasmartlibrary.DTO

import java.util.Date

data class Document(
    val id: Int,
    val auther: String,
    val description: String,
    val image: String,
    val stock: Int,
    val title: String,
    val dateCreation: Date,
    val publish_Year : Int,
    val category: Category,
    val publisher: String,
    val book_Type: String,
    val isbn: String




)










