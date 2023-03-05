package com.example.audionotesapp.di

import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.media.MediaRecorder
import com.example.audionotesapp.domain.usecase.AudioPlayerUseCase
import com.example.audionotesapp.domain.usecase.DirectoryAudioUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

	@Provides
	@Singleton
	fun provideAudioPlayer(mediaRecorder: MediaRecorder, mediaPlayer: MediaPlayer): AudioPlayerUseCase {
		return AudioPlayerUseCase(mediaRecorder, mediaPlayer)
	}

	@Provides
	@Singleton
	fun provideDirectory(@ApplicationContext context: Context, mmr: MediaMetadataRetriever): DirectoryAudioUseCase {
		return DirectoryAudioUseCase(context, mmr)
	}

	@Provides
	@Singleton
	fun provideMediaRecorder(): MediaRecorder{
		return MediaRecorder()
	}

	@Provides
	@Singleton
	fun provideMediaPlayer(): MediaPlayer{
		return MediaPlayer()
	}

	@Provides
	@Singleton
	fun provideMediaMetadataRetriever(): MediaMetadataRetriever{
		return MediaMetadataRetriever()
	}

}