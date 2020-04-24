package com.example.chatapp.view.ui.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chatapp.R
import com.example.chatapp.view.ui.form.ProfileFormActivity

import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragment_profile_image.setImageResource(R.drawable.men)

        fragment_profile_editBtn.setOnClickListener{
            toEditForm()
        }
    }
    private fun toEditForm(){
        val intent = Intent(this.requireContext(), ProfileFormActivity::class.java)
        startActivity(intent)
    }
}
