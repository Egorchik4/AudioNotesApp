package com.example.audionotesapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audionotesapp.domain.model.AudioModel
import com.example.audionotesapp.domain.usecase.AudioPlayerUseCase
import com.example.audionotesapp.domain.usecase.DirectoryAudioUseCase
import com.example.audionotesapp.presentation.state.AudioItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	var audioPlayerUseCase: AudioPlayerUseCase,
	var directoryAudioUseCase: DirectoryAudioUseCase
) : ViewModel() {

	private val listAudioMut = MutableLiveData<MutableList<AudioItemState>>()
	val listAudioLive: LiveData<MutableList<AudioItemState>> = listAudioMut

	private val audioMut = MutableLiveData<AudioItemState>()
	val audioLive: LiveData<AudioItemState> = audioMut

	var fileAudio = ""
	fun startRecording() {
		fileAudio = directoryAudioUseCase.getNameToNewFile()
		audioPlayerUseCase.startRecording(fileAudio)
	}

	fun stopRecording() {
		audioPlayerUseCase.stopRecording()
		directoryAudioUseCase.refreshAudioList(fileAudio)
		listAudioMut.value = directoryAudioUseCase.getAudioModelListState()
	}

	fun playAudio(id: Int) {
		val fileName = directoryAudioUseCase.getAudioFromId(id)
		audioPlayerUseCase.startPlaying(fileName.directory)
		setStateProgressBar(fileName)
	}

	fun stopAudio(id: Int) {
		val fileName = directoryAudioUseCase.getAudioFromId(id)
		audioPlayerUseCase.stopPlaying(fileName.directory)
		setStateProgressBar(fileName)
	}

	private fun setStateProgressBar(model: AudioModel) {
		viewModelScope.launch(Dispatchers.IO) {
			while (audioPlayerUseCase.player.isPlaying) {
				val actual = audioPlayerUseCase.getCurrentPositionOfPlayer()
				val actualTime = directoryAudioUseCase.convertDurationToTime(actual)
				audioMut.postValue(
					AudioItemState.Play(
						actualInt = actual,
						actualTime = actualTime,
						model = model
					)
				)
			}
			audioPlayerUseCase.stopPlaying(model.directory)
			audioMut.postValue(AudioItemState.Stop(model))
		}
	}

	init {
		listAudioMut.value = directoryAudioUseCase.getAudioModelListState()
	}
}