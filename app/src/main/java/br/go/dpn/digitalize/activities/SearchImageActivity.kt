package br.go.dpn.digitalize.activities

import BASE_URL
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import br.go.dpn.digitalize.R
import br.go.dpn.digitalize.services.GoogleRestAPI
import br.go.dpn.digitalize.adapters.ImageResultsAdapter
import br.go.dpn.digitalize.listeners.EndlessRecyclerViewScrollListener
import br.go.dpn.digitalize.model.ImageResult
import br.go.dpn.digitalize.model.ImageSearchResult
import br.go.dpn.digitalize.services.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v7.widget.LinearLayoutManager

class SearchImageActivity : AppCompatActivity() {

    private lateinit var searchTerm: String
    private lateinit var googleService: GoogleRestAPI
    private lateinit var rvImageResults: RecyclerView
    private var imageResults: ArrayList<ImageResult> = arrayListOf()
    private lateinit var imageResultsAdapter: ImageResultsAdapter
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private var imageNumber: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_image)
        setupView()
        startSearch()
    }

    private fun setupView() {
        searchTerm = intent.getStringExtra("searchTerm")

        googleService = ServiceGenerator(BASE_URL).createService(GoogleRestAPI::class.java)

        rvImageResults = findViewById(R.id.rvImageResults)
        imageResultsAdapter = ImageResultsAdapter(this, imageResults)

        val mLayoutManager = LinearLayoutManager(applicationContext)
        rvImageResults.layoutManager = mLayoutManager
        rvImageResults.itemAnimator = DefaultItemAnimator()
        rvImageResults.adapter = imageResultsAdapter

        imageResultsAdapter.notifyDataSetChanged()

        val linearLayoutManager = LinearLayoutManager(this)
        rvImageResults.layoutManager = linearLayoutManager
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                imageNumber += 10
                populateData()
            }
        }

        rvImageResults.addOnScrollListener(scrollListener)
    }

    private fun startSearch() {
        imageResults.clear()
        populateData()
    }

    private fun populateData() {
        googleService.getImages(searchTerm, imageNumber).enqueue(object : Callback<ImageSearchResult> {
            override fun onResponse(call: Call<ImageSearchResult>, response: Response<ImageSearchResult>) {
                val statusCode = response.code()
                if (statusCode == 200 && response.body() != null && !response.body()!!.items.isEmpty()) {
                    imageResults.addAll(response.body()!!.items)
                    imageResultsAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(applicationContext, getString(R.string.get_images_failed), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ImageSearchResult>, t: Throwable) {
                Toast.makeText(applicationContext, getString(R.string.get_images_failed), Toast.LENGTH_SHORT).show()
            }
        })
    }
}
