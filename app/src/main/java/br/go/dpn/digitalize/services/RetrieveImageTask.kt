package br.go.dpn.digitalize.services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.net.URL

internal class RetrieveImageTask : AsyncTask<String, Void, Bitmap>() {

    private var exception: Exception? = null

    override fun doInBackground(vararg urls: String?): Bitmap? {
        try {
            val url = URL(urls[0])
            return BitmapFactory.decodeStream(url.openConnection().getInputStream())
        } catch (e: Exception) {
            this.exception = e
            return null
        } finally {
        }
    }

    override fun onPostExecute(feed: Bitmap) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}