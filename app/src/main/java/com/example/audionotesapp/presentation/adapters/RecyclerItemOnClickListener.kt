package com.example.audionotesapp.presentation.adapters

import java.text.FieldPosition

interface RecyclerItemOnClickListener {

	fun startAudio(id: Int)

	fun stopAudio(id: Int)
}