package com.example.maciek.postviewer.Managers

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class NetworkingManager {

    companion object {
        fun makeGetRequest(urlString: String): String {
            var urlConnection: HttpURLConnection? = null
            val response: String

            try {
                val url = URL(urlString)

                urlConnection = url.openConnection() as HttpURLConnection
                response = streamToString(urlConnection.inputStream)
            } catch (ex: Exception) {
                println("Network error")
                throw ex
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect()
                }
            }
            return response
        }

        private fun streamToString(inputStream: InputStream): String {

            val bufferReader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            var result = ""

            try {
                do {
                    line = bufferReader.readLine()
                    if (line != null) {
                        result += line
                    }
                } while (line != null)
                inputStream.close()
            } catch (ex: Exception) {

            }

            return result
        }
    }
}