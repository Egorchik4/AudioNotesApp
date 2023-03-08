package com.example.audionotesapp.data.datasource

import android.content.Context
import android.media.MediaMetadataRetriever
import com.example.audionotesapp.domain.model.AudioModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class DataSourceImpl @Inject constructor(val context: Context, private val mmr: MediaMetadataRetriever, private val calendar: Calendar) : DataSource {

	private val audioDirectory: String = context.cacheDir.toString() + "/audio"
	private val audioList: MutableList<AudioModel> = mutableListOf()

	private fun getNameOfFile(): String {
		val folderToSave: String = audioDirectory
		val photoDirectory = File(folderToSave)
		if (!photoDirectory.isDirectory) {
			photoDirectory.mkdir()
		}
		val data = SimpleDateFormat("ss", Locale.getDefault()).format(calendar.time)
		return "$photoDirectory/AudioFile $data"
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
				audioList.removeAt(index)
				removeFromDirectory(newFile)
				return
			}
		}
		val file = File(newFile)
		audioList.add(
			AudioModel(
				id = audioList.size,
				directory = file.path,
				name = file.name,
				data = setData(file),
				timeOfDuration = convertDurationOfAudio(getDurationOfAudio(file.absolutePath)),
				maxOfDuration = getDurationOfAudio(file.absolutePath)
			)
		)
	}

	override fun convertDurationToTime(time: Int): String {
		return convertDurationOfAudio(time)
	}

	override fun destroy() {
		mmr.release()
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
						data = setData(file),
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

	private fun setData(file: File): String {
		calendar.timeInMillis = file.lastModified()
		return SimpleDateFormat("dd/M/yyyy hh:mm", Locale.getDefault()).format(calendar.time)
	}

	private fun removeFromDirectory(pathFile: String) {
		val file = File(pathFile)
		file.delete()
	}
}