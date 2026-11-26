package isetb.tp2.tunisiasmartlibrary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DocsAdapter(
    private val documents: List<Document>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<DocsAdapter.DocsViewHolder>(

) {


    class DocsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val image = view.findViewById<ImageView>(R.id.img_doc)
        val title = view.findViewById<TextView>(R.id.tv_title_doc)
        val description = view.findViewById<TextView>(R.id.tv_discription_doc)
        val showMore = view.findViewById<TextView>(R.id.More)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            DocsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.document_item, parent, false)
        return DocsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DocsViewHolder,
        position: Int
    ) {
        val document = documents[position]

        Glide.with(holder.itemView.context)
            .load("http://127.0.0.1:8081/api/documents/files/" + document.image)
            .into(holder.image)

        holder.title.text = document.title
        if (document.description.length > 20)
            holder.description.text = "${document.description.subSequence(0, 20)}..."
        else
            holder.description.text = document.description

        holder.showMore.setOnClickListener {
            onItemClick(document.id)
        }


    }

    override fun getItemCount(): Int = documents.size

}
