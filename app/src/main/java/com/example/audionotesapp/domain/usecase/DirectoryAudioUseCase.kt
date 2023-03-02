package com.example.audionotesapp.domain.usecase

import android.content.Context
import android.media.MediaMetadataRetriever
import com.example.audionotesapp.domain.model.AudioModel
import java.io.File
import java.util.Date
import javax.inject.Inject

class DirectoryAudioUseCase @Inject constructor(val context: Context) {

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
						time = getDurationOfAudio(file.absolutePath)
					)
				)
			}
		}
		return audioList
	}

	private fun getCreationDataOfAudio() {

	}

	private fun getDurationOfAudio(pathAudio: String): String {
		val mmr = MediaMetadataRetriever()
		mmr.setDataSource(pathAudio)
		val duration: String = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toString()
		mmr.release()
		val millSecond = Integer.parseInt(duration)
		val minutes: Int = millSecond / 60000
		val second: Int = (millSecond % 60000) / 1000
		return "$minutes:$second"
	}
}