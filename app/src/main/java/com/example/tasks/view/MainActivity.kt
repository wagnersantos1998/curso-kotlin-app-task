package com.example.tasks.view

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tasks.R
import com.example.tasks.R.id.text_name
import com.example.tasks.funcoes.VerificarConexao
import com.example.tasks.funcoes.mensagensSnack
import com.example.tasks.funcoes.pintarSnack
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.model.TarefaModel
import com.example.tasks.service.repository.TarefaRepository
import com.example.tasks.view.adapter.TaskAdapter
import com.example.tasks.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var aux: Int = 0

        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED")
        val conexaoInternet = VerificarConexao()
        registerReceiver(conexaoInternet, intentFilter)

        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            startActivity(Intent(this, TaskFormActivity::class.java))
        }

        // Navegação
        setupNavigation()

        carregarNomeUsuario()

        // Observadores
        observe()

        conexaoInternet.addOnMudarEstadoConexao(
            object : VerificarConexao.IOnMudarEstadoConexao {
                override fun onMudar(tipoConexao: VerificarConexao.TipoConexao?) {
                    if (tipoConexao === VerificarConexao.TipoConexao.TIPO_NAO_CONECTADO) {
                        pintarSnack(mensagensSnack("Sem Conexão!",
                            drawer_layout,
                            Snackbar.LENGTH_INDEFINITE), Color.WHITE, Color.RED)
                        aux = 1
                    } else if (aux === 1) {
                        pintarSnack(mensagensSnack("Conectado!",
                            drawer_layout,
                            Snackbar.LENGTH_SHORT), Color.WHITE, Color.GREEN)

                        aux = 0

                    }
                }
            })
    }

    private fun carregarNomeUsuario() {
        mViewModel.buscarNomeUsuario()
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupNavigation() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_all_tasks, R.id.nav_next_tasks, R.id.nav_expired, R.id.nav_logout),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun observe() {
        mViewModel.nome.observe(this, Observer {
            val nav = findViewById<NavigationView>(R.id.nav_view)
            val header = nav.getHeaderView(0)
            val nome = header.findViewById<TextView>(R.id.text_name)
            nome.text = it
        })
    }

}
