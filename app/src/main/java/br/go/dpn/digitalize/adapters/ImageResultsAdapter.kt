package br.go.dpn.digitalize.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.go.dpn.digitalize.R
import br.go.dpn.digitalize.model.ImageResult
import com.squareup.picasso.Picasso
import android.graphics.Bitmap
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.support.v4.content.ContextCompat.startActivity
import java.io.File
import java.io.FileOutputStream
import android.os.StrictMode
import android.support.v7.widget.RecyclerView
import android.widget.*
import br.go.dpn.digitalize.services.RetrieveImageTask

class ImageResultsAdapter(private var context: Context, private var images: List<ImageResult>) :
    RecyclerView.Adapter<ImageResultsAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivImage)
        val btnShare: Button = view.findViewById(R.id.btnShareImage)

        init {
            btnShare.setOnClickListener { v -> shareImage(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageResultsAdapter.MyViewHolder {
        val layout = R.layout.item_image_result

        val itemView = LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageResultsAdapter.MyViewHolder, position: Int) {
        val item = images[position]
        Picasso.get().load(item.link).fit().into(holder.ivImage)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    private fun shareImage(adapterPosition: Int) {
        try {
            val bitmap = RetrieveImageTask().execute(images[adapterPosition].link)

            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())

            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "share_image_" + System.currentTimeMillis() + ".png")
            val fOut = FileOutputStream(file)
            bitmap.get().compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
            file.setReadable(true, false)
            val intent = Intent(android.content.Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
            intent.type = "image/png"
            startActivity(context, Intent.createChooser(intent, "Share image via"), null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}