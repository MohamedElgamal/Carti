package com.example.triples

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.example.triples.ApisConnectionClasses.AbstractConnectionHandler
import com.example.triples.ApisConnectionClasses.CheckoutCashConnection
import com.example.triples.DataContainers.CashResponse
import com.example.triples.DataContainers.Constants
import com.example.triples.databinding.ActivityCashBinding

class CashActivity : AppCompatActivity() , AbstractConnectionHandler<CashResponse>{
    lateinit var codeScanner: CodeScanner

    lateinit var binding: ActivityCashBinding
    lateinit var checkoutCashConnection : CheckoutCashConnection

    lateinit var sharePreferencesHelper: PreferencesHelper

    val CAMERA_REQUEST_PERMISSION_CODE = 1111
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCashBinding.inflate(layoutInflater)
        initCamera()
        init()
        setContentView(binding.root)
    }

    fun init(){
        sharePreferencesHelper = PreferencesHelper(this , Constants.CREDENTIALS_FILE_PATH)
        checkoutCashConnection = CheckoutCashConnection(this)
    }

    private fun initCamera() {
        codeScanner = CodeScanner(this, binding.barcodeCameraView)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.PREVIEW// or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                initButtonBehaviour(it.text)
                viewsVisibility(View.VISIBLE ,View.INVISIBLE)
                codeScanner.startPreview()
                codeScanner.scanMode = ScanMode.PREVIEW
            }
        }

        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }
        binding.barcodeCameraBtn.setOnClickListener()
        {
            codeScanner.scanMode = ScanMode.SINGLE
        }
        checkPermission()
    }

    private fun initButtonBehaviour(qrCode : String){

        binding.cashConfirmBtn.setOnClickListener(){
            checkoutCashConnection.connectToCashCheckout("Bearer "
                    + sharePreferencesHelper.getAuthToken() , qrCode)
        }
    }

    private fun viewsVisibility(confirmViewState : Int
                                , messageViewState :Int
                                ){
        binding.cashConfirmSection.visibility = confirmViewState
        binding.cashMessageSection.visibility = messageViewState

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == CAMERA_REQUEST_PERMISSION_CODE
            && grantResults.isNotEmpty()&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
            codeScanner.startPreview()
        }
        else{
            Toast
                .makeText(this , "can't scan until you give camera permission" , Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun checkPermission(){
        if(ContextCompat.checkSelfPermission(this , android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this ,
                arrayOf(android.Manifest.permission.CAMERA)
                ,CAMERA_REQUEST_PERMISSION_CODE)
        }
        else{
            codeScanner.startPreview()
        }
    }

    override fun onSuccessConnection(data: CashResponse?) {
        viewsVisibility(View.INVISIBLE , View.INVISIBLE)
        binding.cashProcessSection.visibility = View.VISIBLE
        Handler().postDelayed({
            val intent = Intent(this , MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
            finish()
        } ,2000 )
    }

    override fun onFailedConnection() {
        Toast.makeText(this , "not a QR code of cashier scanned the wrong one"
            , Toast.LENGTH_LONG).show()
        viewsVisibility(View.INVISIBLE , View.VISIBLE)
    }

    override fun onFailureConnection() {
        Toast.makeText(this , "Check the internet connection or firewall",Toast.LENGTH_LONG)
            .show()
    }
}