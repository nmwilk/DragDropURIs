package com.nmwilkinson.dragndrop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ViewFlipper
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class MainActivity : AppCompatActivity(), DragDropView.Listener {
    private lateinit var listAdapter: UriListAdapter
    private lateinit var list: RecyclerView
    private lateinit var dragDropView: DragDropView
    private lateinit var flipper: ViewFlipper

    private val viewModel: DragDropViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        setContentView(R.layout.activity_main)

        list = findViewById(R.id.list)
        list.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        dragDropView = findViewById(R.id.dragDropView)
        flipper = findViewById(R.id.viewFlipper)

        dragDropView.listener = this

        listAdapter = UriListAdapter()
        list.adapter = listAdapter
        updateUI(viewModel.allData)
    }

    override fun onFilesDropped(result: List<DropDropItems>) {
        viewModel.addResults(result)

        updateUI(result)
    }

    private fun updateUI(result: List<DropDropItems>) {
        flipper.displayedChild = if (result.isEmpty()) 0 else 1
        listAdapter.addResults(result)
    }
}