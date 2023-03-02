package com.example.audionotesapp.presentation.adapters

interface RecyclerItemOnClickListener {

	fun startAudio(id: Int)

	fun stopAudio()
}