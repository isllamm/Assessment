package com.technical.assessment.featuremain.presentation.fragments.profile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.technical.assessment.R
import com.technical.assessment.core.Constants
import com.technical.assessment.core.Resource
import com.technical.assessment.core.ToastUtils
import com.technical.assessment.core.TransitionUtils
import com.technical.assessment.databinding.FragmentProfileBinding
import com.technical.assessment.featuremain.domain.model.Album
import com.technical.assessment.featuremain.domain.model.User
import com.technical.assessment.featuremain.presentation.MainActivity
import com.technical.assessment.featuremain.presentation.adapters.AlbumsAdapter
import com.technical.assessment.featuremain.presentation.adapters.interfaces.AlbumEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile), AlbumEvent {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var parent: MainActivity
    private val viewModel: ProfileViewModel by viewModels()

    private val albumsAdapter by lazy { AlbumsAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        viewModel.getUsers()
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.usersFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(requireContext(), it.message.toString())
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        val selectedUser = it.data?.random()
                        viewModel.getAlbums(selectedUser?.id!!)
                        initUserData(selectedUser)
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.albumsFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(requireContext(), it.message.toString())
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        binding.albumsRv.adapter = albumsAdapter
                        albumsAdapter.submitList(it.data)
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initUserData(selectedUser: User?) {
        binding.apply {
            usernameTv.text = selectedUser?.name
            addressTv.text =
                "${selectedUser?.address?.street}, ${selectedUser?.address?.suite}, ${selectedUser?.address?.city}, ${selectedUser?.address?.zipcode},"
        }
    }

    override fun onAlbumClickListener(album: Album) {
        parent.navController.navigate(R.id.albumFragment, bundleOf(Constants.ALBUM_ID to album.id,Constants.ALBUM_NAME to album.title) ,TransitionUtils.generalAnimationOptions)
    }

}