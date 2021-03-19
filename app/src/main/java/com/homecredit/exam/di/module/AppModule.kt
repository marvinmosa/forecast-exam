package com.homecredit.exam.di.module

import android.content.Context
import com.homecredit.exam.BuildConfig
import com.homecredit.exam.data.api.ApiHelper
import com.homecredit.exam.data.api.ApiHelperImpl
import com.homecredit.exam.data.api.ApiService
import com.homecredit.exam.data.db.AppDatabase
import com.homecredit.exam.utils.NetworkHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), BuildConfig.BASE_URL) }
    single { provideApiService(get()) }
    single { provideNetworkHelper(androidContext()) }
    single { provideDatabase(androidContext())}
    single { provideForecastDao(get())}

    single<ApiHelper> {
        return@single ApiHelperImpl(get())
    }
}

private fun provideNetworkHelper(context: Context) = NetworkHelper(context)

private fun provideOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
} else OkHttpClient
    .Builder()
    .build()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)

private fun provideDatabase(context: Context) = AppDatabase.getInstance(context)

private fun provideForecastDao(db: AppDatabase) = db.forecastDao()
