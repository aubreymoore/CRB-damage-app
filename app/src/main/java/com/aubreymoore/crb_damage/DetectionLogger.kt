package com.aubreymoore.crb_damage

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class DetectionLogger(private val context: Context) {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    // Get or create the CRB-detections folder in Documents
    private fun getOutputDir(): File {
        val dir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "CRB-detections"
        )
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    // Daily CSV file name
    private fun getCsvFile(): File {
        val today = dateFormat.format(Date())
        return File(getOutputDir(), "crb_detections_$today.csv")
    }

    fun logDetections(
        boundingBoxes: List<BoundingBox>,
        bitmap: Bitmap,
        latitude: Double?,
        longitude: Double?
    ) {
        if (boundingBoxes.isEmpty()) return

        val timestamp = timeFormat.format(Date())
        val csvFile = getCsvFile()
        val isNew = !csvFile.exists()

        // Save photo
        val photoFile = savePhoto(bitmap, timestamp)

        // Write CSV row
        try {
            val writer = FileWriter(csvFile, true)
            if (isNew) {
                writer.append("timestamp,latitude,longitude,label,confidence,photo_path\n")
            }
            for (box in boundingBoxes) {
                writer.append("$timestamp,")
                writer.append("${latitude ?: ""},")
                writer.append("${longitude ?: ""},")
                writer.append("${box.clsName},")
                writer.append("${"%.3f".format(box.cnf)},")
                writer.append("${photoFile?.absolutePath ?: ""}\n")
            }
            writer.flush()
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun savePhoto(bitmap: Bitmap, timestamp: String): File? {
        return try {
            val safeTimestamp = timestamp.replace(":", "-").replace(" ", "_")
            val photoFile = File(getOutputDir(), "photo_$safeTimestamp.jpg")
            val out = FileOutputStream(photoFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
            photoFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}