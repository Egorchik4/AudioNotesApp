package com.example.audionotesapp.domain.usecase

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.IllegalStateException
import javax.inject.Inject

class AudioPlayerUseCase @Inject constructor(var recorder: MediaRecorder, var player: MediaPlayer) {

	var directoryAudio = ""

	fun startRecording(fileName: String) {
		recorder.apply {
			setAudioSource(MediaRecorder.AudioSource.MIC)
			setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
			setOutputFile(fileName)
			setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
			try {
				prepare()
			} catch (e: IOException) {

			}
			start()
		}
	}

	fun stopRecording() {
		recorder.apply {
			stop()
			reset()
		}
	}

	fun startPlaying(directoryOfAudio: String) {
		if(directoryAudio == directoryOfAudio && player.duration != player.currentPosition) {
			player.seekTo(player.currentPosition)
			player.start()
		}else{
			resetPlaying()
			player.apply {
				try {
					setDataSource(directoryOfAudio)
					prepare()
					start()
				} catch (e: IllegalStateException) {

				}
			}
		}
		directoryAudio = directoryOfAudio
	}

	fun getCurrentPositionOfPlayer(): Int{
		return if(player.isPlaying){
			player.currentPosition
		}else{
			0
		}
	}

	fun stopPlaying(directoryOfAudio: String) {
		player.pause()
	}

	private fun resetPlaying(){
		player.reset()
	}
}