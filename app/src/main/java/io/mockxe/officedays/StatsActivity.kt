package io.mockxe.officedays

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        overridePendingTransition(R.anim.right_to_middle, R.anim.middle_to_left)

        // show arrow button in menu bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.menu_stats_title)
        actionBar?.setTitle(R.string.menu_stats_title)

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.left_to_middle, R.anim.middle_to_right)
    }

    // simulates back press when menu bar arrow is selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            android.R.id.home -> onBackPressed()

            else -> {}
        }

        return super.onOptionsItemSelected(item)
    }

}