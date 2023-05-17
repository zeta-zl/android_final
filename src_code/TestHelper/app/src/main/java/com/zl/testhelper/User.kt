package com.zl.testhelper


import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.zl.testhelper.ImageStateManager.getImagePath
import com.zl.testhelper.ImageStateManager.saveImagePath
import com.zl.testhelper.PhotoStateManager.getPhotoBitmap
import com.zl.testhelper.PhotoStateManager.savePhotoBitmap
import java.io.ByteArrayOutputStream
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


@Composable
fun User(navHostController : NavHostController) {
    Scaffold(
        content = { padding ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    horizontalAlignment =
                    Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        PersonScreen(navHostController)
                    }

                }
            }
        }, bottomBar = {
            Box(
                modifier = Modifier
                    .paddingFromBaseline(bottom = 0.dp)
            ) {
                BottomNavigationBar(navHostController)
            }
        })
}

@Composable
fun SettingDetails(navHostController : NavHostController) {
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
    ) {
        Section(
            title = "Summarize",
            onClick = {
                navHostController.navigate("Summarize")
            }
        )
        Divider(
            color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Section(
            title = "Setting",
            onClick = {
                navHostController.navigate("Setting")
            }
        )
        Divider(
            color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Section(
            title = "About",
            onClick = {
                navHostController.navigate("About")
            }
        )
    }
}

@Composable
fun Section(title : String, onClick : () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp, vertical = 8.dp
            ),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun PersonScreen(navHostController : NavHostController) {
    val context = LocalContext.current
    val imageUri = remember {
        mutableStateOf(
            getImagePath(context)
        )
    }
    val contentResolver = context.contentResolver
    var bitmap by remember {
        mutableStateOf(
            getPhotoBitmap(context)?.asImageBitmap()
        )
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            // 保存选择的图片 URI
            imageUri.value = uri.toString()
            bitmap = imageUri.value?.let {
                contentResolver.openInputStream(Uri.parse(it))?.use { inputStream ->
                    BitmapFactory.decodeStream(inputStream)?.asImageBitmap()
                }
            }
            if (bitmap != null) {
                savePhotoBitmap(context, bitmap!!.asAndroidBitmap())
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 显示用户选择的图片
        if (bitmap != null) {
            Image(
                bitmap = bitmap!!,
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .clickable {
                        galleryLauncher.launch("image/*")
                    },
                contentScale = ContentScale.Crop
            )
        } else {
            // 显示默认图片
            Image(
                painter = painterResource(R.drawable.baseline_account_circle_24),
                contentDescription = "Default Image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .clickable {
                        galleryLauncher.launch("image/*")
                    },
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        SettingDetails(navHostController)

    }
}


object ImageStateManager {
    private const val PREF_NAME = "ImageStatePref"
    private const val IMAGE_PATH_KEY = "image_path"

    private fun getSharedPreferences(context : Context) : SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getImagePath(context : Context) : String? {
        return getSharedPreferences(context).getString(IMAGE_PATH_KEY, null)
    }

    fun saveImagePath(context : Context, uri : Uri) {
        val imagePath = uri.toString()
        getSharedPreferences(context).edit {
            putString(IMAGE_PATH_KEY, imagePath)
        }
    }
}


object PhotoStateManager {
    private const val PREF_NAME = "PhotoStatePref"
    private const val PHOTO_BITMAP_KEY = "photo_bitmap"

    private fun getSharedPreferences(context : Context) : SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getPhotoBitmap(context : Context) : Bitmap? {
        val base64Image = getSharedPreferences(context).getString(PHOTO_BITMAP_KEY, null)
        return base64Image?.let { base64 ->
            val decodedBytes = Base64.decode(
                base64,
                Base64.DEFAULT
            )
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun savePhotoBitmap(context : Context, bitmap : Bitmap) {

        GlobalScope.launch {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val encodedImage =
                Base64.encodeToString(
                    byteArrayOutputStream.toByteArray(),
                    Base64.DEFAULT
                )
            getSharedPreferences(context).edit {
                putString(PHOTO_BITMAP_KEY, encodedImage)
            }
        }
    }
}




