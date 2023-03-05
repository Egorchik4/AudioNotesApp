package com.example.audionotesapp.presentation.state

import com.example.audionotesapp.domain.model.AudioModel
import java.text.FieldPosition

sealed class AudioItemState{

	data class Stop(var model: AudioModel): AudioItemState()

	data class Play(
		var actualInt: Int,
		var actualTime: String,
		var model: AudioModel
		): AudioItemState()

}
