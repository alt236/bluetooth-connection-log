package uk.co.alt236.bluetoothconnectionlog.ui.main.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogDevice

class DeviceRecyclerAdapter(
    private val parentActivity: FragmentActivity,
    private val twoPane: Boolean
) :
    RecyclerView.Adapter<ViewHolder>() {

    private val binder = ViewHolderBinder(parentActivity, twoPane)
    private var data: List<LogDevice> = ArrayList()


    fun setData(data: List<LogDevice>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(ViewHolder.LAYOUT_ID, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binder.onBindViewHolder(holder, data[position])
    }

    override fun getItemCount() = data.size
}