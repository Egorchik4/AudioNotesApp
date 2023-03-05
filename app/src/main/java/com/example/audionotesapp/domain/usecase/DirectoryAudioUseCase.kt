package com.example.audionotesapp.domain.usecase

import android.content.Context
import android.media.MediaMetadataRetriever
import android.util.Log
import com.example.audionotesapp.domain.model.AudioModel
import java.io.File
import java.util.Date
import javax.inject.Inject

class DirectoryAudioUseCase @Inject constructor(val context: Context, val mmr: MediaMetadataRetriever) {

	private val audioDirectory: String = context.cacheDir.toString() + "/audio"
	val audioList: MutableList<AudioModel> = mutableListOf()

	fun getNameAudioToSave(): String {
		val folderToSave: String = audioDirectory
		val photoDirectory = File(folderToSave)
		if (!photoDirectory.isDirectory) {
			photoDirectory.mkdir()
		}
		return photoDirectory.toString() + "/AudioFile ${audioList.size}"
	}

	fun getAudioModelListFromDirectory(): MutableList<AudioModel> {
		audioList.clear()
		val file = File(audioDirectory)
		if (!file.listFiles().isNullOrEmpty()) {
			file.listFiles().forEachIndexed { index, file ->
				audioList.add(
					index,
					AudioModel(
						id = index,
						directory = file.path,
						name = file.name,
						data = Date(file.lastModified()).toString(),
						timeOfDuration = convertDurationOfAudio(getDurationOfAudio(file.absolutePath)),
						maxOfDuration = getDurationOfAudio(file.absolutePath)
					)
				)
			}
		}
		return audioList
	}

	private fun getCreationDataOfAudio() {

	}

	private fun getDurationOfAudio(pathAudio: String): Int {
		var duration = ""
		mmr.apply {
			setDataSource(pathAudio)
			duration = extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toString()
		}
		return Integer.parseInt(duration)
	}

	fun convertDurationOfAudio(millSecond: Int): String{
		val minutes: Int = millSecond / 60000
		val second: Int = (millSecond % 60000) / 1000
		return "$minutes:$second"
	}
}