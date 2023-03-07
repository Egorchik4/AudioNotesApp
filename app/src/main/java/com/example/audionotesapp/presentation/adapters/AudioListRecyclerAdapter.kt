package com.example.audionotesapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.audionotesapp.R
import com.example.audionotesapp.databinding.AudioFileItemBinding
import com.example.audionotesapp.presentation.state.AudioItemState

class AudioListRecyclerAdapter(private val onClickListener: RecyclerItemOnClickListener) : RecyclerView.Adapter<AudioListRecyclerAdapter.AudioListHolder>() {

	private var audioList: MutableList<AudioItemState> = mutableListOf()
	private var isChecked = false

	class AudioListHolder(item: View) : RecyclerView.ViewHolder(item) {

		val binding = AudioFileItemBinding.bind(item)
		fun bind(audioState: AudioItemState) {
			when (audioState) {
				is AudioItemState.Stop -> {
					binding.progressBar.visibility = View.INVISIBLE
					binding.textSlash.visibility = View.INVISIBLE
					binding.textTimeActual.text =
						binding.textTimeActual.context.getString(R.string.textEmpty)
					binding.buttonPlayPause.background =
						AppCompatResources.getDrawable(binding.buttonPlayPause.context, R.drawable.ic_play_)
					binding.progressBar.progress = 0
					binding.textNameAudio.text = audioState.model.name
					binding.textDataAudio.text = audioState.model.data
					binding.textTime.text = audioState.model.timeOfDuration
					binding.progressBar.max = audioState.model.maxOfDuration
				}

				is AudioItemState.Play -> {
					binding.progressBar.visibility = View.VISIBLE
					binding.textSlash.visibility = View.VISIBLE
					binding.buttonPlayPause.background =
						AppCompatResources.getDrawable(binding.buttonPlayPause.context, R.drawable.ic_pause_)
					binding.progressBar.progress = audioState.actualInt
					binding.textTimeActual.text = audioState.actualTime
					binding.textNameAudio.text = audioState.model.name
					binding.textDataAudio.text = audioState.model.data
					binding.textTime.text = audioState.model.timeOfDuration
					binding.progressBar.max = audioState.model.maxOfDuration
				}
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioListHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.audio_file_item, parent, false)
		return AudioListHolder(view)
	}

	override fun onBindViewHolder(holder: AudioListHolder, position: Int) {
		holder.bind(audioList[position])
		holder.binding.buttonPlayPause.setOnClickListener {
			if (isChecked) {
				onClickListener.stopAudio(position)
			} else {
				onClickListener.startAudio(position)
			}
		}
	}

	override fun getItemCount(): Int = audioList.size

	fun actualListAudio(list: MutableList<AudioItemState>) {
		list.forEach {
			if (!audioList.contains(it)) {
				addItem(it)
			}
		}
	}

	fun refreshItem(state: AudioItemState) {
		when (state) {
			is AudioItemState.Play -> {
				audioList[state.model.id] = state
				isChecked = true
				notifyItemChanged(state.model.id)
			}

			is AudioItemState.Stop -> {
				audioList[state.model.id] = state
				isChecked = false
				notifyItemChanged(state.model.id)
			}
		}
	}

	private fun addItem(item: AudioItemState) {
		audioList.add(item)
		notifyItemInserted(itemCount)
	}
}