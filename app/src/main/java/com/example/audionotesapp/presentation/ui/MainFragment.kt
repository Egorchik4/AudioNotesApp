package com.example.audionotesapp.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.audionotesapp.databinding.FragmentMainBinding
import com.example.audionotesapp.presentation.MainViewModel
import com.example.audionotesapp.presentation.adapters.AudioListRecyclerAdapter
import com.example.audionotesapp.presentation.adapters.RecyclerItemOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), RecyclerItemOnClickListener {

	lateinit var binding: FragmentMainBinding
	private val viewModel: MainViewModel by viewModels()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentMainBinding.inflate(inflater)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		checkPermissions()
		val recyclerAdapter = AudioListRecyclerAdapter(this)
		binding.recyclerAdapter.layoutManager = LinearLayoutManager(requireContext())
		binding.recyclerAdapter.adapter = recyclerAdapter


		viewModel.listAudioLive.observe(viewLifecycleOwner) {
			recyclerAdapter.actualListAudio(it)
		}

		viewModel.audioLive.observe(viewLifecycleOwner){
			recyclerAdapter.refreshItem(it)
		}

		binding.buttonMicro.setOnCheckedChangeListener { buttonView, isChecked ->
			if (isChecked) {
				viewModel.startRecording()
			} else {
				viewModel.stopRecording()
			}
		}
	}

	override fun startAudio(id: Int) {
		viewModel.playAudio(id)
	}

	override fun stopAudio(id: Int) {
		viewModel.stopAudio(id)
	}

	private fun checkPermissions() {
		if (
			ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) !=
			PackageManager.PERMISSION_GRANTED
		) {
			ActivityCompat.requestPermissions(
				requireActivity(),
				arrayOf(
					Manifest.permission.READ_EXTERNAL_STORAGE,
					Manifest.permission.WRITE_EXTERNAL_STORAGE,
					Manifest.permission.RECORD_AUDIO
				),
				0
			)
		}
	}
}