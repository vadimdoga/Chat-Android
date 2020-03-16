package com.example.chatapp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.adapters.ChatAdapter
import com.example.chatapp.adapters.ChatItem
import com.example.chatapp.R
import kotlinx.android.synthetic.main.fragment_messages.*


class Messages : Fragment() {

    private var chatItems: MutableList<ChatItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()

        rv_msg_list.layoutManager = LinearLayoutManager(activity)
        rv_msg_list.adapter =
            ChatAdapter(
                this.requireContext(),
                chatItems
            ) {
                val toast = Toast.makeText(activity?.applicationContext, it.name, Toast.LENGTH_LONG)
                toast.show()
            }
    }

    private fun initData(){
        val image = resources.obtainTypedArray(R.array.msg_image)
        val name = resources.getStringArray(R.array.msg_name)
        val shortMsg = resources.getStringArray(R.array.msg_shortMsg)
        val time = resources.getStringArray(R.array.msg_time)
        chatItems.clear()
        for (i in name.indices){
            chatItems.add(
                ChatItem(
                    name[i],
                    shortMsg[0],
                    image.getResourceId(0, 0),
                    time[i]
                )
            )
        }
    }

}
