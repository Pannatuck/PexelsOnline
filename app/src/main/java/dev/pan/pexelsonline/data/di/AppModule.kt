package dev.pan.pexelsonline.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.pan.pexelsonline.BuildConfig
import dev.pan.pexelsonline.data.network.ApiService
import dev.pan.pexelsonline.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideRetrofit(): ApiService =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val request = original.newBuilder()
                            .header("accept", "application/json")
                            .header("Authorization", BuildConfig.API_KEY)
                            .method(original.method, original.body)
                            .build()
                        chain.proceed(request)
                    }
                    .build()
            )
            .build()
            .create(ApiService::class.java)

}