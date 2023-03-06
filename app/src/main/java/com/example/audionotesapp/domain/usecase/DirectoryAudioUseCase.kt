package com.example.audionotesapp.domain.usecase

import com.example.audionotesapp.domain.model.AudioModel
import com.example.audionotesapp.domain.repository.DirectoryRepository
import com.example.audionotesapp.presentation.state.AudioItemState
import javax.inject.Inject

class DirectoryAudioUseCase @Inject constructor(private val directoryRepository: DirectoryRepository) {

	fun getNameToNewFile(): String {
		return directoryRepository.getNameToNewFile()
	}

	fun getAudioFromId(id: Int): AudioModel {
		return directoryRepository.getAudioFromId(id)
	}

	fun getAudioModelListState(): MutableList<AudioItemState> {
		return directoryRepository.getAudioModelList()
	}

	fun refreshAudioList(filePath: String) {
		directoryRepository.refreshAudioList(filePath)
	}

	fun convertDurationToTime(time: Int): String {
		return directoryRepository.convertDurationToTime(time)
	}
}