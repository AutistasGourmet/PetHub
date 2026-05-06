package com.autistasgourmet.pethub.di

import android.content.Context
import com.autistasgourmet.pethub.data.repository.AdopterProfileRepositoryImpl
import com.autistasgourmet.pethub.data.repository.AuthRepositoryImpl
import com.autistasgourmet.pethub.data.repository.PetRepositoryImpl
import com.autistasgourmet.pethub.data.repository.StorageRepositoryImpl
import com.autistasgourmet.pethub.domain.repository.AdopterProfileRepository
import com.autistasgourmet.pethub.domain.repository.AuthRepository
import com.autistasgourmet.pethub.domain.repository.PetRepository
import com.autistasgourmet.pethub.domain.repository.StorageRepository
import com.autistasgourmet.pethub.domain.usecase.CompleteAdopterProfileUseCase
import com.autistasgourmet.pethub.domain.usecase.ExpressInterestUseCase
import com.autistasgourmet.pethub.domain.usecase.GetAdoptablePetsUseCase
import com.autistasgourmet.pethub.domain.usecase.GetPetDetailUseCase
import com.autistasgourmet.pethub.domain.usecase.LoginUseCase
import com.autistasgourmet.pethub.domain.usecase.RegisterUseCase
import com.autistasgourmet.pethub.domain.usecase.SavePetUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Firebase --------------------------------------------------------

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    // Repositories --------------------------------------------------------

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun providePetRepository(firestore: FirebaseFirestore): PetRepository {
        return PetRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideAdopterProfileRepository(firestore: FirebaseFirestore): AdopterProfileRepository {
        return AdopterProfileRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideStorageRepository(storage: FirebaseStorage): StorageRepository {
        return StorageRepositoryImpl(storage)
    }

    // Use Cases --------------------------------------------------------

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: AuthRepository): RegisterUseCase {
        return RegisterUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSavePetUseCase(
        repository: PetRepository
    ): SavePetUseCase {
        return SavePetUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCompleteAdopterProfileUseCase(
        repository: AdopterProfileRepository
    ): CompleteAdopterProfileUseCase {
        return CompleteAdopterProfileUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAdoptablePetsUseCase(
        repository: PetRepository,
        authRepository: AuthRepository,
        profileRepository: AdopterProfileRepository
    ): GetAdoptablePetsUseCase {
        return GetAdoptablePetsUseCase(repository, authRepository, profileRepository)
    }

    @Provides
    @Singleton
    fun provideExpressInterestUseCase(repository: PetRepository): ExpressInterestUseCase {
        return ExpressInterestUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPetDetailUseCase(repository: PetRepository): GetPetDetailUseCase {
        return GetPetDetailUseCase(repository)
    }
}
