package com.artiuil.lab.cleverdictionary.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.core.graphics.scale
import androidx.exifinterface.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun createImageFile(context: Context): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val imageFileName = "IMG_$timeStamp.jpg"
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    if (storageDir != null && !storageDir.exists()) {
        storageDir.mkdirs()
    }
    return File(storageDir, imageFileName)
}


fun getUriForFile(context: Context, file: File): Uri {
    val authority = "${context.packageName}.provider"
    return FileProvider.getUriForFile(context, authority, file)
}

fun createImageFileUri(context: Context): Uri? {
    val file = createImageFile(context)
    return try {
        getUriForFile(context, file)
    } catch (_: Exception) {
        null
    }
}

///Exif metadata is used to properly rotate the image
fun compressImage(context: Context, uri: Uri, targetWidth: Int = 1024, quality: Int = 85): ByteArray? {
    val inputStream = context.contentResolver.openInputStream(uri) ?: return null
    val originalBitmap = BitmapFactory.decodeStream(inputStream)
    inputStream.close()
    if (originalBitmap == null) return null
    val exifStream = context.contentResolver.openInputStream(uri) ?: return null
    val exif = ExifInterface(exifStream)
    exifStream.close()
    val rotationAngle = when (
        exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
    ) {
        ExifInterface.ORIENTATION_ROTATE_90 -> 90
        ExifInterface.ORIENTATION_ROTATE_180 -> 180
        ExifInterface.ORIENTATION_ROTATE_270 -> 270
        else -> 0
    }
    val rotatedBitmap = if (rotationAngle != 0) {
        val matrix = Matrix().apply { postRotate(rotationAngle.toFloat()) }
        Bitmap.createBitmap(
            originalBitmap, 0, 0,
            originalBitmap.width, originalBitmap.height,
            matrix, true
        )
    } else {
        originalBitmap
    }


    val scaledBitmap = if (rotatedBitmap.width > targetWidth) {
        val ratio = targetWidth.toFloat() / rotatedBitmap.width.toFloat()
        val targetHeight = (rotatedBitmap.height * ratio).toInt()
        rotatedBitmap.scale(targetWidth, targetHeight)
    } else {
        rotatedBitmap
    }

    val outputStream = ByteArrayOutputStream()
    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
    return outputStream.toByteArray()
}
