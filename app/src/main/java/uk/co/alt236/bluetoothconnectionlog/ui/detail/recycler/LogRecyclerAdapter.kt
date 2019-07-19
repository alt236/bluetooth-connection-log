package uk.co.alt236.bluetoothconnectionlog.ui.detail.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry


class LogRecyclerAdapter(parentActivity: FragmentActivity) :
    RecyclerView.Adapter<ViewHolder>() {
    private val binder = ViewHolderBinder(parentActivity)
    private var data: List<LogEntry> = ArrayList()


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
        binder.onBindViewHolder(holder, item)
    }

    override fun getItemCount() = data.size

    companion object {
        private const val LAYOUT_ID: Int = R.layout.list_item_log
    }

}