package com.etc.di
import com.etc.BuildConfig
import com.etc.api.ApiService
import com.etc.api.BaseApi
import com.etc.network.RequestHeader
import com.etc.network.appContext
import com.etc.network.converter.MyGsonConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 *
 *
 * @Author holo
 * @Date 2022/3/22
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    /**
     * OkHttp初始化并注入
     * @return OkHttpClient
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .cache(Cache(File(appContext.cacheDir, "wan_cache"), 6 * 1024 * 1024))//设置缓存配置 缓存最大6M
            .addInterceptor(RequestHeader())
            .addInterceptor(logInterceptor)
            .connectTimeout(BaseApi.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(BaseApi.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(BaseApi.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Retrofit初始化并注入
     * @param okHttpClient OkHttpClient
     * @return Retrofit
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BaseApi.API_BASE)
         //   .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MyGsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideChainService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}