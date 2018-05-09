package com.vitali.kotlinanimationtest

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewCompat
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId)
        {
            R.id.action_settings -> true

            R.id.action_show_action_btn ->
            {
                toggleFab()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        // Forwarding all results to SignInFragment for further handling.
        findFragmentByTag("com.vitali.kotlinanimationtest.MainActivityFragment")?.onActivityResult(requestCode, resultCode, data)
    }

    private fun toggleFab()
    {
        val fabButton = fab

        if(fabButton.isShown)
        {
            //fab.hide()

            ViewCompat.animate(fabButton)
                    .scaleX(0f)
                    .scaleY(0f)
                    .setInterpolator(FastOutSlowInInterpolator())
                    .withEndAction {
                        Toast.makeText(this@MainActivity, "Do Hide Action () ", Toast.LENGTH_SHORT).show()
                        fabButton.visibility = View.GONE
                    }
                    .start()


        }
        else
        {
            //fabButton.show()

            fabButton.visibility = View.VISIBLE

            ViewCompat.animate(fabButton)
                    .scaleYBy(1.5f)
                    .scaleXBy(1.5f)
                    .setInterpolator(FastOutSlowInInterpolator())
                    .withEndAction{
                        ViewCompat.animate(fabButton)
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .setInterpolator(FastOutSlowInInterpolator())
                                .withEndAction{
                                    Toast.makeText(this@MainActivity, "Do Show Action () ", Toast.LENGTH_SHORT).show()
                                }
                                .start()
                    }
                    .start()
        }
    }
}
