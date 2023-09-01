package com.example.project_cs426_runningapp

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso

class EventAdapter(private val context: Context, private val dataSource: ArrayList<EventData>) : BaseAdapter() {

    private var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.event_list_view, parent, false)

        val event_data = getItem(position) as EventData

        val event_title = rowView.findViewById(R.id.text_event_name) as TextView
        val thumbnail = rowView.findViewById(R.id.event_thumbnail) as ImageView
        val join_button = rowView.findViewById(R.id.join_challenge_button) as TextView

        event_title.text = event_data.event_name

        if (!event_data.event_name.isNullOrEmpty()) {
            Log.d("Bug", event_data.event_name)
            Picasso.with(rowView.context)
                .load(event_data.image_name)
                .fit()
                .centerCrop()
                .into(thumbnail)
        }

        join_button.setOnClickListener {
            val db = SQLiteDBHelper(rowView.context, null,
                "RUNNING_DATABASE", 1)

            val event_name = rowView.findViewById<TextView>(R.id.text_event_name).text.toString()

            db.addEvent(event_name, true)

            Toast.makeText(rowView.context,"Added to database", Toast.LENGTH_LONG).show()
        }
        return rowView
    }

}

public class EventData(var event_name: String, var joined: Boolean, var image_name: String?)