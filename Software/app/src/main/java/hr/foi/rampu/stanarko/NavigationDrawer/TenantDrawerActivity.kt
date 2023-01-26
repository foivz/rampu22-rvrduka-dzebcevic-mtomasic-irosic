package hr.foi.rampu.stanarko.NavigationDrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import hr.foi.rampu.stanarko.R
import androidx.appcompat.widget.Toolbar
import com.google.android.material.navigation.NavigationView

class TenantDrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    override fun setContentView(view: View?) {
        drawerLayout = layoutInflater.inflate(R.layout.activity_tenant_drawer, null) as DrawerLayout
        var container: FrameLayout = drawerLayout.findViewById(R.id.activity_container)
        container.addView(view)
        super.setContentView(drawerLayout)

        var toolbar: Toolbar = drawerLayout.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        var navView: NavigationView = drawerLayout.findViewById(R.id.nav_view_tenant)
        navView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

    protected fun allocatedActivityTitle(title: String){
        supportActionBar?.let { it.title = title }
    }
}