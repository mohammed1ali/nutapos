package com.mdali.tes2.view.lists

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mdali.tes2.R
import com.mdali.tes2.model.room.Finance
import com.mdali.tes2.view.MainActivity

class WordListAdapter(val clickInterface:ClickInterface) : ListAdapter<Finance, WordListAdapter.WordViewHolder>(WordsComparator()) {

    interface ClickInterface {
        fun frag2btnClicked(finance: Finance)
        fun fragEditbtnClicked(finance: Finance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val currentFinance = getItem(position)
        holder.bind(currentFinance)

        holder.deleteButton.setOnClickListener {
            //it.context.
            clickInterface.frag2btnClicked(currentFinance)
        }
        holder.editButton.setOnClickListener {
            clickInterface.fragEditbtnClicked(currentFinance)

            /*val view: View = (it.context as MainActivity) .layoutInflater.inflate(R.layout.dialog_edit, null)
            val dateTv = view.findViewById<TextView>(R.id.tv_d_date)
            val timeTv = view.findViewById<TextView>(R.id.tv_d_time)
            //val msgBelowDescDialogMsgTv = view.findViewById<TextView>(R.id.tv_msg_below_author_desc)


            val dialog = MaterialAlertDialogBuilder(it.context)
                .setTitle("Edit")
                .setView(view)
                .setNeutralButton("Ok") { dialog, which ->
                    // Respond to neutral button press
                    dialog.dismiss()
                }.show()*/





        }
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTV: TextView = itemView.findViewById(R.id.tv_rv_date)
        private val timeTV: TextView = itemView.findViewById(R.id.tv_rv_time)
        private val masukkeTV: TextView = itemView.findViewById(R.id.tv_rv_masukke)
        private val terimaDariTV: TextView = itemView.findViewById(R.id.tv_rv_terimadari)
        private val nominalTV: TextView = itemView.findViewById(R.id.tv_rv_nominal)
        private val keteranganTV: TextView = itemView.findViewById(R.id.tv_rv_keterangan)
        public val deleteButton: Button = itemView.findViewById(R.id.button_delete)
        public val editButton: Button = itemView.findViewById(R.id.button_edit)

        fun bind(currentFinance: Finance?) {
            currentFinance?.let {
                dateTV.text = it.date
                timeTV.text = it.time
                masukkeTV.text = it.masukke
                terimaDariTV.text = it.terimadari
                nominalTV.text = it.nominal
                keteranganTV.text = it.keterangan
            }
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Finance>() {
        override fun areItemsTheSame(oldItem: Finance, newItem: Finance): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Finance, newItem: Finance): Boolean {
            return oldItem.uid == newItem.uid
        }
    }
}