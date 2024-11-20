package ar.edu.unicen.seminarioentregable.network

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

class LanguageInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        // Obtener el idioma del dispositivo
        val language = Locale.getDefault().language

        // Agregar el parámetro language a la URL
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("language", language)
            .build()

        // Crear una nueva solicitud con la URL modificada
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        // Continuar con la solicitud
        return chain.proceed(newRequest)
    }
}