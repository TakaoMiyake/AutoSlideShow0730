package jp.techacademy.takao.miyake.autoslideshow0730

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.os.Build
import android.util.Log
import android.provider.MediaStore
import android.content.ContentUris
import kotlinx.android.synthetic.main.activity_main.*
import android.content.pm.PackageManager
import android.view.View

import android.os.Handler
import java.util.*

class MainActivity : AppCompatActivity(),View.OnClickListener {

    private var mTimer: Timer? = null
    private var mTimerSec = 0.0
    private var mHandler = Handler()

    val resolver = contentResolver

    private val PERMISSIONS_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        permission_button.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    getContentsInfo()
                } else {
                    requestPermissions(
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        PERMISSIONS_REQUEST_CODE
                    )
                }
            } else {
                getContentsInfo()
            }
        }


        go_button.setOnClickListener(this)
        back_button.setOnClickListener(this)
       // playandstop_button.setOnClickListener (this)


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
                when (requestCode) {
                    PERMISSIONS_REQUEST_CODE ->
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            getContentsInfo()
                        }
                }
    }

    fun getContentsInfo() {
        val cursor= resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )

        /*val resolver = contentResolver
        val cursor = resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )*/

                if (cursor!!.moveToFirst()) {
                    val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                    val id = cursor.getLong(fieldIndex)
                    val imageUri =
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    Log.d("ANDROID", "URI : " + imageUri.toString())
                }
    }

    override fun onClick(v:View?) {

        val cursor= resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )

        /*if (cursor!!.moveToFirst()) {
            val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val id = cursor.getLong(fieldIndex)
            val imageUri =
                ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
            Log.d("ANDROID", "URI : " + imageUri.toString())
        }*/

        if(v!= null){
            if(v.id == R.id.go_button) {
                    val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                    val id = cursor.getLong(fieldIndex)
                    val imageUri =
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                    if (!cursor.moveToNext()) {
                        cursor.moveToFirst()
                    }
                    imageView.setImageURI(imageUri)

                    Log.d("ANDROID", "URI : " + imageUri.toString())

            }
            else if(v.id == R.id.back_button) {
                    val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                    val id = cursor.getLong(fieldIndex)
                    val imageUri =
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                    if(!cursor.moveToPrevious()){
                        cursor.moveToLast()
                    }
                    imageView.setImageURI(imageUri)
                    Log.d("ANDROID", "URI : " + imageUri.toString())
            }
            else if(v.id == R.id.playandstop_button) {
                //I have no ideas to indicate around this area so far
            }

            cursor.close()

        }

    }
}

