package com.example.audionotesapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.audionotesapp.R
import com.example.audionotesapp.databinding.AudioFileItemBinding
import com.example.audionotesapp.domain.model.AudioModel

class AudioListRecyclerAdapter(private val onClickListener: RecyclerItemOnClickListener) : RecyclerView.Adapter<AudioListRecyclerAdapter.AudioListHolder>() {

	private var audioList: MutableList<AudioModel> = mutableListOf()

	class AudioListHolder(item: View) : RecyclerView.ViewHolder(item) {

		val binding = AudioFileItemBinding.bind(item)
		fun bind(audioModel: AudioModel) {
			binding.textNameAudio.text = audioModel.name
			binding.textDataAudio.text = audioModel.data
			binding.textTime.text = audioModel.time
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioListHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.audio_file_item, parent, false)
		return AudioListHolder(view)
	}

	override fun onBindViewHolder(holder: AudioListHolder, position: Int) {
		holder.bind(audioList[position])
		holder.binding.imageViewPlayPause.setOnClickListener {
			if (holder.binding.imageViewPlayPause.isActivated) {
				onClickListener.start(position)
			} else {
				onClickListener.stop()
			}
		}
	}

	override fun getItemCount(): Int = audioList.size

	fun actualListAudio(list: MutableList<AudioModel>) {
		list.forEach {
			if (!audioList.contains(it)) {
				addItem(it)
			}
		}
	}

	private fun addItem(item: AudioModel) {
		val index = 0
		audioList.add(index, item)
		notifyItemInserted(index)
	}
}