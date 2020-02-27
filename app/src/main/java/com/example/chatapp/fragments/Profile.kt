package com.example.chatapp.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chatapp.forms.EditProfileForm
import com.example.chatapp.R

import kotlinx.android.synthetic.main.fragment_profile.*


class Profile : Fragment() {

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
        val intent = Intent(this.requireContext(), EditProfileForm::class.java)
        startActivity(intent)
    }
}