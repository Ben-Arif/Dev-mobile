package isetb.tp2.tunisiasmartlibrary.DTO

class Category(
    val id: Long,
    val name: String,
){
    override fun toString(): String {
        return name;
    }
}