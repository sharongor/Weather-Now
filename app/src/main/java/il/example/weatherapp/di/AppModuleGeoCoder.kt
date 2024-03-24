package il.example.weatherapp.di

import android.content.Context
import android.location.Geocoder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Locale


@Module
@InstallIn(SingletonComponent::class)
class AppModuleGeoCoder {


    @Provides
    fun provideGeocoder(@ApplicationContext context: Context): Geocoder {
        return Geocoder(context, Locale.ENGLISH)
    }
}