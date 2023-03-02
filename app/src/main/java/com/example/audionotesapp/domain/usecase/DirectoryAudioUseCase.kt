package com.example.audionotesapp.domain.usecase

import android.content.Context
import com.example.audionotesapp.domain.model.AudioModel
import java.io.File
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
						directory = file.path,
						name = file.name,
						data = "1.03.2023",
						time = "3:02"
					)
				)
			}
		}
		return audioList
	}
}