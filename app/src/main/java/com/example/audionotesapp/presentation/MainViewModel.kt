package com.example.audionotesapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.audionotesapp.domain.model.AudioModel
import com.example.audionotesapp.domain.usecase.AudioPlayerUseCase
import com.example.audionotesapp.domain.usecase.DirectoryAudioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	var audioPlayerUseCase: AudioPlayerUseCase,
	var directoryAudioUseCase: DirectoryAudioUseCase
) : ViewModel() {

	private val listAudioMut = MutableLiveData<MutableList<AudioModel>>()
	val listAudio: LiveData<MutableList<AudioModel>> = listAudioMut

	fun startRecording() {
		val fileAudio = directoryAudioUseCase.getNameAudioToSave()
		audioPlayerUseCase.startRecording(fileAudio)
	}

	fun stopRecording() {
		audioPlayerUseCase.stopRecording()
		listAudioMut.value = directoryAudioUseCase.getAudioModelListFromDirectory()
	}

	fun playAudio(id: Int) {
		val fileName = directoryAudioUseCase.audioList[id].directory
		audioPlayerUseCase.startPlaying(fileName)
	}

	fun stopAudio() {
		audioPlayerUseCase.stopPlaying()
	}

	init {
		listAudioMut.value = directoryAudioUseCase.getAudioModelListFromDirectory()
	}
}