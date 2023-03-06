package com.example.audionotesapp.data.datasource

import com.example.audionotesapp.domain.model.AudioModel

interface DataSource {

	fun getNameToNewFile(): String

	fun getAudioFromId(id: Int): AudioModel

	fun getAudioModelList(): MutableList<AudioModel>

	fun refreshAudioList(newFile: String)

	fun convertDurationToTime(time: Int): String
}