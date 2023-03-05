package com.example.audionotesapp.data.datasource

import android.content.Context
import android.media.MediaMetadataRetriever
import android.util.Log
import com.example.audionotesapp.domain.model.AudioModel
import java.io.File
import java.util.Date
import javax.inject.Inject

class DataSourceImpl @Inject constructor(val context: Context, val mmr: MediaMetadataRetriever) : DataSource {

	private val audioDirectory: String = context.cacheDir.toString() + "/audio"
	private val audioList: MutableList<AudioModel> = mutableListOf()

	private fun getNameOfFile(): String {
		val folderToSave: String = audioDirectory
		val photoDirectory = File(folderToSave)
		if (!photoDirectory.isDirectory) {
			photoDirectory.mkdir()
		}
		return photoDirectory.toString() + "/AudioFile ${audioList.size}"
	}

	override fun getNameToNewFile(): String {
		return getNameOfFile()
	}

	override fun getAudioFromId(id: Int): AudioModel {
		return audioList[id]
	}

	override fun getAudioModelList(): MutableList<AudioModel> {
		return if (audioList.isEmpty()) {
			getAudioModelListFromDirectory()
		} else {
			audioList
		}
	}

	override fun refreshAudioList(newFile: String) {
		audioList.forEachIndexed { index, audioModel ->
			if (audioModel.directory == newFile) {
				Log.e("eee", "delete")
				audioList.removeAt(index)
				return
			}
		}
		Log.e("eee", "rrrrr")
		val audio = File(newFile)
		audioList.add(
			AudioModel(
				id = audioList.size,
				directory = audio.path,
				name = audio.name,
				data = Date(audio.lastModified()).toString(),
				timeOfDuration = convertDurationOfAudio(getDurationOfAudio(audio.absolutePath)),
				maxOfDuration = getDurationOfAudio(audio.absolutePath)
			)
		)
	}

	override fun convertDurationToTime(time: Int): String {
		return convertDurationOfAudio(time)
	}

	private fun getAudioModelListFromDirectory(): MutableList<AudioModel> {
		val file = File(audioDirectory)
		if (!file.listFiles().isNullOrEmpty()) {
			val list = file.listFiles()
			list.sortBy { it.lastModified() }
			list.forEachIndexed { index, file ->
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

	private fun getDurationOfAudio(pathAudio: String): Int {
		var duration = ""
		mmr.apply {
			setDataSource(pathAudio)
			duration = extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toString()
		}
		return Integer.parseInt(duration)
	}

	private fun convertDurationOfAudio(millSecond: Int): String {
		val minutes: Int = millSecond / 60000
		val second: Int = (millSecond % 60000) / 1000
		return "$minutes:$second"
	}
}