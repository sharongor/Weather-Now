package il.example.weatherapp.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import il.example.weatherapp.data.local_db.AppDataBase
import il.example.weatherapp.data.local_db.FragmentsDao
import il.example.weatherapp.data.remote_db.weatherService
import il.example.weatherapp.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideRetrofit(gson:Gson) : Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    //needed for the Retrofit -> in which converts json to kotlin objects
    @Provides
    fun provideGson() : Gson = GsonBuilder().create()


    //making instance of the interface to make network requests to the specified URL
    @Provides
    fun provideWeatherService(retrofit: Retrofit) : weatherService =
        retrofit.create(weatherService::class.java)


    //since we are injecting Dao we need to create our DB first and then we can inject Dao using that db instance

    //getting DB
    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext appContext: Context) : AppDataBase =
        AppDataBase.getDataBase(appContext)



    @Singleton
    @Provides
    fun provideDao(dataBase: AppDataBase) : FragmentsDao =
        dataBase.favouriteDao()


    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context) }
}