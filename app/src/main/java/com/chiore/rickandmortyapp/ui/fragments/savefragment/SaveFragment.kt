package com.chiore.rickandmortyapp.ui.fragments.savefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chiore.rickandmortyapp.R
import com.chiore.rickandmortyapp.adapters.SaveAdapter
import com.chiore.rickandmortyapp.databinding.DetailsFragmentBinding
import com.chiore.rickandmortyapp.databinding.SaveFragmentBinding
import com.chiore.rickandmortyapp.ui.fragments.detailsfragment.DetailsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveFragment : Fragment(R.layout.save_fragment) {

    private var _binding: SaveFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var saveAdapter: SaveAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SaveFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        getCharacters()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                val deletedCharacter = saveAdapter.currentList[position]
                val insertCharacter = saveAdapter.currentList[position]

                viewModel.deleteCharacter(deletedCharacter)

                Snackbar.make(requireView(), "Character deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.saveCharacter(insertCharacter)
                    }
                ).show()

            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.saveRv)

    }

    private fun setupRv() {
        saveAdapter = SaveAdapter()
        binding.saveRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.saveRv.apply {
            adapter = saveAdapter
            setHasFixedSize(true)
        }
    }

    private fun getCharacters() {
        viewModel.getSavedCharacters().observe(viewLifecycleOwner, Observer { characters ->
            saveAdapter.submitList(characters)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}