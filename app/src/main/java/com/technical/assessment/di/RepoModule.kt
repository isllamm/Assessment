package com.technical.assessment.di


import com.technical.assessment.featuremain.data.remote.MainApiService
import com.technical.assessment.featuremain.data.repo.MainRepoImpl
import com.technical.assessment.featuremain.domain.repo.MainRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideMainRepo(api: MainApiService): MainRepo {
        return MainRepoImpl(api)
    }


}