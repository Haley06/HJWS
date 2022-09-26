package change.aaa.rtmpdemo.navi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import change.aaa.rtmpdemo.R

data class PoiSearchListItem(
    val uid: String,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)

class PoiSearchAdapter(private val fragment: PoiSearchFragment) : ListAdapter<PoiSearchListItem, PoiSearchViewHolder>(PoiSearchItemDiffer) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoiSearchViewHolder {
        val view =
            LayoutInflater.from(fragment.requireContext()).inflate(R.layout.item_poi_search, parent, false)
        return PoiSearchViewHolder(fragment, view)
    }

    override fun onBindViewHolder(holder: PoiSearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

class PoiSearchViewHolder(
    private val fragment: PoiSearchFragment,
    private val root: View
) : RecyclerView.ViewHolder(root) {
    private val title = root.findViewById<TextView>(R.id.poi_title)
    private val description = root.findViewById<TextView>(R.id.poi_description)
    fun bind(item: PoiSearchListItem) {
        root.setOnClickListener {
            fragment.onPoiItemClicked(item)
        }
        title.text = item.name
        description.text = item.description
    }
}

object PoiSearchItemDiffer : DiffUtil.ItemCallback<PoiSearchListItem>() {
    override fun areItemsTheSame(oldItem: PoiSearchListItem, newItem: PoiSearchListItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: PoiSearchListItem,
        newItem: PoiSearchListItem
    ): Boolean {
        return oldItem == newItem
    }

}