package com.theokhunov.imagedownloader

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var downloadImage : Button
    private lateinit var enterLink : EditText
    private var permission = 0
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
    permission = if (it){
        1
    }

    else{
        0
    }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        downloadImage = findViewById(R.id.btnDownloadImage)
        enterLink = findViewById(R.id.edtLink)

        downloadImage.setOnClickListener{
            requestPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (permission==1){
                download(enterLink.text.toString(),"Muhammadamin Developer")
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun download(url:String,fileName:String) {

        try {
            var downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val imageLink = Uri.parse(url)
            var request = DownloadManager.Request(imageLink)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                .setMimeType("image/jpeg")
                .setAllowedOverRoaming(false)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle(fileName)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,File.separator+fileName+".jpg")
                downloadManager.enqueue(request)
            Toast.makeText(this, "Image is Download", Toast.LENGTH_SHORT).show()

        }
        catch (e:Exception){
            Toast.makeText(this, "Image Download Failed", Toast.LENGTH_SHORT).show()
        }

    }
}