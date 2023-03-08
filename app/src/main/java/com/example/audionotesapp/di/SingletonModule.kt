package com.example.audionotesapp.di

import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.media.MediaRecorder
import com.example.audionotesapp.data.datasource.DataSource
import com.example.audionotesapp.data.datasource.DataSourceImpl
import com.example.audionotesapp.data.repository.DirectoryRepositoryImpl
import com.example.audionotesapp.domain.repository.DirectoryRepository
import com.example.audionotesapp.domain.usecase.AudioPlayerUseCase
import com.example.audionotesapp.domain.usecase.DirectoryAudioUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Calendar
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

	@Provides
	@Singleton
	fun provideDataSource(@ApplicationContext context: Context, mmr: MediaMetadataRetriever, calendar: Calendar): DataSource {
		return DataSourceImpl(context, mmr, calendar)
	}

	@Provides
	@Singleton
	fun provideDirectoryRepository(dataSource: DataSource): DirectoryRepository {
		return DirectoryRepositoryImpl(dataSource)
	}

	@Provides
	@Singleton
	fun provideDirectoryAudioUseCase(directoryRepository: DirectoryRepository): DirectoryAudioUseCase {
		return DirectoryAudioUseCase(directoryRepository)
	}

	@Provides
	@Singleton
	fun provideAudioPlayer(mediaRecorder: MediaRecorder, mediaPlayer: MediaPlayer): AudioPlayerUseCase {
		return AudioPlayerUseCase(mediaRecorder, mediaPlayer)
	}

	@Provides
	@Singleton
	fun provideMediaRecorder(): MediaRecorder {
		return MediaRecorder()
	}

	@Provides
	@Singleton
	fun provideMediaPlayer(): MediaPlayer {
		return MediaPlayer()
	}

	@Provides
	@Singleton
	fun provideMediaMetadataRetriever(): MediaMetadataRetriever {
		return MediaMetadataRetriever()
	}

	@Provides
	@Singleton
	fun provideCalendar(): Calendar {
		return Calendar.getInstance()
	}

}