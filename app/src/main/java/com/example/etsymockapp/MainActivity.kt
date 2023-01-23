package com.example.etsymockapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView

    private val Home = Home()
    private val Favorites = Favorites()
    private val Updates = Updates()
    private val You = You()
    private val Cart = Cart()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = Home()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



//      First method:
//      Hide the supportActionBar on home fragment and show in the rest, setting the supportActionBar title as the fragment title
//      Show appBarLayout that contains toolbar on the home fragment only
//      This has the same problem of drop shadow disappearing
//      Also the motion graphic is not the same with Etsy when switching around the fragments (appBarLayout being pushed off to the top)
//      supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFFFF")))

//        Second method:
//        Hiding supportActionBar all across the app and changing the visibility of constraint layout and textview inside the toolbar
/*        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFFFF")))
        supportActionBar?.hide()*/


/*        fun onCreateOptionsMenu(menu: Menu?): Boolean {
            return super.onCreateOptionsMenu(menu)
        }*/

        loadFragment(Home())


        bottomNavigationView = findViewById(R.id.bottomnavigationview) as BottomNavigationView

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(Home())
                    true
                }
                R.id.favorites -> {
                    loadFragment(Favorites())
                    true
                }
                R.id.updates -> {
                    loadFragment(Updates())
                    true
                }
                R.id.you -> {
                    loadFragment(You())
                    true
                }
                R.id.cart -> {
                    loadFragment(Cart())
                    true
                }
                else -> {false}
            }
        }

    }



    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

}

