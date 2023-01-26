package hr.foi.rampu.stanarko.NavigationDrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.drawerlayout.widget.DrawerLayout
import hr.foi.rampu.stanarko.R
import androidx.appcompat.widget.Toolbar
import com.google.android.material.navigation.NavigationView

class TenantDrawerActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    override fun setContentView(view: View?) {
        drawerLayout = layoutInflater.inflate(R.layout.activity_tenant_drawer, null) as DrawerLayout
        var container: FrameLayout = drawerLayout.findViewById(R.id.activity_container)
        container.addView(view)
        super.setContentView(drawerLayout)

    }
}