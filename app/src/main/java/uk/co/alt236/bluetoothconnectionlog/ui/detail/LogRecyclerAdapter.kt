package uk.co.alt236.bluetoothconnectionlog.ui.detail

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry
import java.util.*
import kotlin.collections.ArrayList


class LogRecyclerAdapter(
    private val parentActivity: FragmentActivity
) :
    RecyclerView.Adapter<LogRecyclerAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener
    private var data: List<LogEntry> = ArrayList()

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as LogEntry

            if (item.location.valid) {
                val gmmIntentUri = Uri.parse("geo:${item.location.latitude},${item.location.longitude}?z=15")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(parentActivity.packageManager) != null) {
                    ContextCompat.startActivity(parentActivity, mapIntent, null)
                }
            }

        }
    }

    fun setData(data: List<LogEntry>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(LAYOUT_ID, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.idView.text = item.event.toString()
        holder.contentView.text = Date(item.timestamp).toString()

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = data.size

    companion object {
        private const val LAYOUT_ID: Int = R.layout.list_item_device
    }

    inner class ViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
        val idView: TextView = root.findViewById(R.id.id_text)
        val contentView: TextView = root.findViewById(R.id.content)
    }
}