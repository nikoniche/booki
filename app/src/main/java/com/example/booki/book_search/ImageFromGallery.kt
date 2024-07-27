package com.example.booki.book_search

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

fun getImageUri(context: Context, bitmap: Bitmap): Uri {
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
    return Uri.parse(path)
}

@Composable
fun rememberImagePickerLauncher(
    onImagePicked: (Uri) -> Unit,
    onCameraImageTaken: (Bitmap) -> Unit
): Pair<() -> Unit, () -> Unit> {
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            onImagePicked(it)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        bitmap?.let {
            onCameraImageTaken(it)
        }
    }

    return Pair({ galleryLauncher.launch("image/*") }, { cameraLauncher.launch(null) })
}
//
//@Composable
//fun ImagePickerScreen() {
//    val context = LocalContext.current
//    val database = AppDatabase.getDatabase(context)
//    val imageDao = database.imageDao()
//    val scope = rememberCoroutineScope()
//
//    var imageUri by remember { mutableStateOf<Uri?>(null) }
//    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
//
//    val (launchGalleryPicker, launchCameraPicker) = rememberImagePickerLauncher(
//        onImagePicked = { uri ->
//            imageUri = uri
//            scope.launch {
//                imageDao.insertImage(ImageEntity(imageUri = uri.toString()))
//            }
//        },
//        onCameraImageTaken = { bitmap ->
//            imageBitmap = bitmap
//            val uri = getImageUri(context, bitmap)
//            scope.launch {
//                imageDao.insertImage(ImageEntity(imageUri = uri.toString()))
//            }
//        }
//    )
//}