package com.technical.assessment.featuremain.presentation.fragments.album

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.technical.assessment.R
import com.technical.assessment.core.Constants
import com.technical.assessment.core.Resource
import com.technical.assessment.core.ToastUtils
import com.technical.assessment.databinding.FragmentAlbumBinding
import com.technical.assessment.databinding.FragmentProfileBinding
import com.technical.assessment.featuremain.domain.model.Photo
import com.technical.assessment.featuremain.presentation.MainActivity
import com.technical.assessment.featuremain.presentation.adapters.AlbumsAdapter
import com.technical.assessment.featuremain.presentation.adapters.PhotosAdapter
import com.technical.assessment.featuremain.presentation.adapters.interfaces.PhotoEvent
import com.technical.assessment.featuremain.presentation.fragments.profile.ProfileViewModel
import com.technical.assessment.featuremain.presentation.sheets.ImageSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class AlbumFragment : Fragment(R.layout.fragment_album), PhotoEvent {

    private lateinit var binding: FragmentAlbumBinding
    private lateinit var parent: MainActivity
    private val viewModel: AlbumViewModel by viewModels()
    private lateinit var allPhotos:List<Photo>
    private val photosAdapter by lazy { PhotosAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlbumBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        searchInImages()
        observeData()
        listenToArgs()
    }
    private fun listenToArgs() {
        if (arguments!=null){
            viewModel.getPhotos(arguments?.getInt(Constants.ALBUM_ID)!!)
            binding.albumNameTv.text = arguments?.getString(Constants.ALBUM_NAME)
        }
    }
    private fun observeData() {
        lifecycleScope.launch {
            viewModel.photosFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(requireContext(), it.message.toString())
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        allPhotos = it.data!!
                        setupImages()
                    }
                }
            }
        }
    }
    private fun setupImages() {
        binding.imagesRv.adapter = photosAdapter
        photosAdapter.submitList(allPhotos)
    }

    private fun searchInImages() {
        binding.searchEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable?) {
                val filteredPhotos = mutableListOf<Photo>()
                for (photo in allPhotos) {
                    if (
                        photo.title?.lowercase(Locale.ROOT)!!
                            .contains(binding.searchEt.text.toString().lowercase(Locale.ROOT))
                        || photo.title?.lowercase(Locale.ROOT)!!
                            .contains(binding.searchEt.text.toString().lowercase(Locale.ROOT))
                    ) {
                        filteredPhotos.add(photo)
                    }
                }
                photosAdapter.submitList(filteredPhotos)
                if (photosAdapter.currentList.isEmpty()) {
                    binding.empty.visibility = View.VISIBLE
                }
            }
        })
    }
    override fun onPhotoClickListener(photo: Photo) {
        ImageSheet.newInstance(photo.url!!)
            .show(childFragmentManager, ImageSheet::class.java.canonicalName)
    }

}