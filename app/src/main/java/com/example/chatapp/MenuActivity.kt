package com.example.chatapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.chatapp.fragments.Contacts
import com.example.chatapp.fragments.Messages
import com.example.chatapp.fragments.Profile
import kotlinx.android.synthetic.main.activity_menu.*


class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.title = "   " + "My Name"
        supportActionBar?.setIcon(R.drawable.ic_profile_actionbar)

        val adapter = viewPagerAdapter(
            supportFragmentManager
        )
        adapter.addFragment(Messages())
        adapter.addFragment(Contacts())
        adapter.addFragment(Profile())
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        tabs.getTabAt(0)?.setIcon(R.drawable.ic_chat_icon)
        tabs.getTabAt(1)?.setIcon(R.drawable.ic_contacts_icon)
        tabs.getTabAt(2)?.setIcon(R.drawable.ic_profile_icon)
    }
    class viewPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager){

        private val fragmentList : MutableList<Fragment> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment){
            fragmentList.add(fragment)
        }


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_bar, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId

        if(id == R.id.menu_bar_search){
            Toast.makeText(applicationContext,"search", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }
}
