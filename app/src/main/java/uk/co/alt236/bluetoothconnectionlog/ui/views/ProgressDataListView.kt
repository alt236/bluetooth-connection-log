package uk.co.alt236.bluetoothconnectionlog.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import uk.co.alt236.bluetoothconnectionlog.R


class ProgressDataListView : FrameLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private lateinit var empty: TextView
    private lateinit var progress: ProgressBar
    private lateinit var recycler: RecyclerView

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.view_datalist_view, this, true)
        empty = findViewById(R.id.multi_state_empty)
        progress = findViewById(R.id.multi_state_progress)
        recycler = findViewById(R.id.multi_state_recycler)
    }

    fun setEmptyText(@StringRes resId: Int) {
        setEmptyText(context.getText(resId))
    }

    fun setEmptyText(text: CharSequence) {
        empty.text = text
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        recycler.adapter = adapter
    }

    fun showEmpty() {
        recycler.visibility = View.GONE
        progress.visibility = View.GONE

        empty.visibility = View.VISIBLE
    }

    fun showProgress() {
        recycler.visibility = View.GONE
        empty.visibility = View.GONE

        progress.visibility = View.VISIBLE
    }

    fun showData() {
        empty.visibility = View.GONE
        progress.visibility = View.GONE

        recycler.visibility = View.VISIBLE
    }
}