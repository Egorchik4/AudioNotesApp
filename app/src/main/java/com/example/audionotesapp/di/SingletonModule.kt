package com.example.audionotesapp.di

import android.content.Context
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
	fun provideAudioPlayer(): AudioPlayerUseCase {
		return AudioPlayerUseCase(MediaRecorder(), MediaPlayer())
	}

	@Provides
	@Singleton
	fun provideDirectory(@ApplicationContext context: Context): DirectoryAudioUseCase {
		return DirectoryAudioUseCase(context)
	}

}