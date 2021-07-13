package com.example.triples

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.*
import com.bumptech.glide.Glide
import com.example.triples.ApisConnectionClasses.AbstractConnectionHandler
import com.example.triples.ApisConnectionClasses.BarcodeSearchConnection
import com.example.triples.DataContainers.DataTunnel
import com.example.triples.DataContainers.ProductBarcodeResponse
import com.example.triples.DataContainers.ProductItemViewModel
import com.example.triples.DataContainers.ProductsItem
import com.example.triples.databinding.ActivityBarcodeBinding


class BarcodeActivity : AppCompatActivity() ,AbstractConnectionHandler<ProductBarcodeResponse>{
    lateinit var binding :ActivityBarcodeBinding
    lateinit var codeScanner :CodeScanner
    lateinit var barcodeSearchConnection : BarcodeSearchConnection
    lateinit var productItemViewModel: ProductItemViewModel
    lateinit var productItem : ProductsItem

    val CAMERA_REQUEST_PERMISSION_CODE = 1111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarcodeBinding.inflate(layoutInflater)
        binding.cameraAddProductItem.isEnabled = false
        init()
        initConnectionObject()
        binding.cameraCancelImage.setOnClickListener(){
            finish()
        }

        binding.cameraAddProductItem.setOnClickListener(){
            addItemToShoppingCart()
        }

        binding.cameraCancelProductItem.setOnClickListener(){
            viewsVisibility(View.INVISIBLE , View.VISIBLE , View.INVISIBLE)
        }

        setContentView(binding.root)
    }

    fun init(){
        codeScanner = CodeScanner(this ,binding.barcodeCameraView)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
         codeScanner.scanMode = ScanMode.PREVIEW// or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
                getProductByBarcode(it.text)
                viewsVisibility(View.INVISIBLE ,View.INVISIBLE , View.VISIBLE)
                binding.cameraAddProductItem.isEnabled =true
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

    private fun initConnectionObject(){
        barcodeSearchConnection = BarcodeSearchConnection(this)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == CAMERA_REQUEST_PERMISSION_CODE
            && grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                codeScanner.startPreview()
        }
        else{
            Toast
                .makeText(this , "can't scan until you give camera permission" , Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun getProductByBarcode(barcode : String){
        barcodeSearchConnection.connectForBarcodeSearch(barcode)
    }

    private fun onDataProductBind(productItem : ProductsItem){
        binding.cameraProductNameText.text = productItem.name
        binding.camerProductPriceText.text = "" + productItem.price + " EGP"
        binding.camerProductDescriptionText.text = productItem.description
        Glide.with(this)
            .load("http://carti.elkayal.me/${productItem.image}")
            .into(binding.cameraProductImage)
    }

    private fun addItemToShoppingCart(){
        DataTunnel.productsItem = productItem
        finish()
    }

    private fun viewsVisibility(errorViewState : Int
                                , blankViewState :Int
                                , dataViewState :Int){
        binding.cameraErrorSection.visibility = errorViewState
        binding.cameraBlankSection.visibility = blankViewState
        binding.cameraProductInfoSection.visibility = dataViewState
    }

    override fun onSuccessConnection(data: ProductBarcodeResponse?) {
        val product = data!!.product
        productItem = ProductsItem().productBarcodeCastFactory(product!!)
        onDataProductBind(productItem)
        Log.e("Barcode Object : ", "$productItem" )
    }

    override fun onFailedConnection() {
        Log.e("barcode " , "not found")
        viewsVisibility(View.VISIBLE ,View.INVISIBLE , View.INVISIBLE)
    }

    override fun onFailureConnection() {
        Log.e("Failed " , "check your internet connection or firewall")
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}