package campalans.m08.retrofit2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import campalans.m08.retrofit2.databinding.ActivityMainBinding
import androidx.core.view.isVisible
import campalans.m08.retrofit2.DetailSuperHeroActivity.Companion.EXTRA_ID

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit

    private lateinit var Adapter: SuperHeroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = getRetrofit()
        initUI()
    }

    private fun initUI() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean
            {
                searchByName(query.orEmpty())
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean
            {
                return false
            }
        })

        Adapter = SuperHeroAdapter {navigateToDetail(it)}
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = Adapter
    }

    private fun searchByName(query: String)
    {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val myRespone: Response<SuperHeroDataResponse> =
                retrofit.create(ApiService::class.java).getSuperHeroes(query)

            if (myRespone.isSuccessful)
            {
                val response: SuperHeroDataResponse? = myRespone.body()
                if (response != null)
                {
                    runOnUiThread {
                        Adapter.updateList(response.superheroes)
                        binding.progressBar.isVisible = false
                    }
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit
    {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun navigateToDetail(id: String) {
        val intent = Intent(this, DetailSuperHeroActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
    }
}