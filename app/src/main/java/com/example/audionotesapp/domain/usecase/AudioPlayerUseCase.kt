package com.example.audionotesapp.domain.usecase

import android.media.MediaPlayer
import android.media.MediaRecorder
import java.io.IOException
import javax.inject.Inject

class AudioPlayerUseCase @Inject constructor(var recorder: MediaRecorder?, var player: MediaPlayer?) {

	fun startRecording(fileName: String) {
		recorder = MediaRecorder().apply {
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
		recorder?.apply {
			stop()
			release()
		}
		recorder = null
	}

	fun startPlaying(directoryOfAudio: String) {
		player = MediaPlayer().apply {
			try {
				setDataSource(directoryOfAudio)
				prepare()
				start()
			} catch (e: IOException) {

			}
		}
	}

	fun stopPlaying() {
		player?.release()
		player = null
	}
}