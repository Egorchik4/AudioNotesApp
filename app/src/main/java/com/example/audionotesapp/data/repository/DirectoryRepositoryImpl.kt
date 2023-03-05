package com.example.audionotesapp.data.repository

import com.example.audionotesapp.data.datasource.DataSource
import com.example.audionotesapp.domain.model.AudioModel
import com.example.audionotesapp.domain.repository.DirectoryRepository
import com.example.audionotesapp.presentation.state.AudioItemState
import javax.inject.Inject

class DirectoryRepositoryImpl @Inject constructor(private val dataSource: DataSource) : DirectoryRepository {
//	private val audioItemStateList = mutableListOf<AudioItemState>()

	override fun getNameToNewFile(): String {
		return dataSource.getNameToNewFile()
	}

	override fun getAudioFromId(id: Int): AudioModel {
		return dataSource.getAudioFromId(id)
	}

	override fun getAudioModelList(): MutableList<AudioItemState> {
		val audioItemStateList = mutableListOf<AudioItemState>()
		val list = dataSource.getAudioModelList()
		list.forEach {
			audioItemStateList.add(AudioItemState.Stop(it))
		}
		return audioItemStateList
	}

	override fun refreshAudioList(filePath: String) {
		dataSource.refreshAudioList(filePath)
	}

	override fun convertDurationToTime(time: Int): String {
		return dataSource.convertDurationToTime(time)
	}
}