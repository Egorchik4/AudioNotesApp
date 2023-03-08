package com.example.audionotesapp.domain.repository

import com.example.audionotesapp.domain.model.AudioModel
import com.example.audionotesapp.presentation.state.AudioItemState

interface DirectoryRepository {

	fun getNameToNewFile(): String

	fun getAudioFromId(id: Int): AudioModel

	fun getAudioModelList(): MutableList<AudioItemState>

	fun refreshAudioList(filePath: String)

	fun convertDurationToTime(time: Int): String

	fun destroy()
}