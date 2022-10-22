package com.edon.stutility

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.edon.stutility.databinding.ActivityBrowserBinding
import android.widget.Toast




class BrowserActivity : AppCompatActivity() {
    lateinit var binding: ActivityBrowserBinding
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrowserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //accept the incoming intent and its data coming in
        val myIntent = intent
        //create and initialize progress Dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        //get the address from the intent and pass it to the webviewloader fun
        webViewLoader(myIntent.getStringExtra("address").toString())

    }

    //function to process the url from the intents
    //@SuppressLint("SetJavaScriptEnabled")
    private fun webViewLoader(myIncomeAddress: String){
        //set up webviewclient so it wouldn't open other browsers when
        //link is clicked and also implement progress bar here
        binding.webViewMain.webChromeClient = object: WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                binding.prgBar1.visibility = View.VISIBLE
                binding.prgBar1.progress = newProgress
                title = "Loading..."
                progressDialog.show()
                if(newProgress == 100){
                    binding.prgBar1.visibility = View.GONE
                    title = view?.title
                    progressDialog.dismiss()
                }
                super.onProgressChanged(view, newProgress)
            }
        } // TODO: search for the use of this function

        binding.webViewMain.webViewClient = WebViewClient()
        /*binding.webViewMain.webViewClient = object: WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                binding.prgBar1.visibility = View.VISIBLE
                //title = "Loading..."
                super.onPageStarted(view, url, favicon)
                //view?.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                binding.prgBar1.visibility = View.GONE
                title = view?.title
                super.onPageFinished(view, url)
                //view?.visibility = View.VISIBLE
            }
        }*/

        //actual loading of the page
        binding.webViewMain.apply{
            loadUrl(myIncomeAddress) //received the incoming address
            //few settings ot the page
            settings.javaScriptEnabled = true
            settings.allowContentAccess = true
            settings.allowFileAccess = true
            //settings.displayZoomControls = true // show the zoom controls
            settings.builtInZoomControls = true // allow pinch to zoom
            settings.setSupportZoom(true)
            //for fitting to screen size
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
        }
    }

    fun backBtn(view: View) {
        if (binding.webViewMain.canGoBack())
            binding.webViewMain.goBack()
    }

    fun forwardBtn(view: View) {
        if (binding.webViewMain.canGoForward())
            binding.webViewMain.goForward()
    }

    fun reloadBtn(view: View) {
        binding.webViewMain.reload()
    }

    //making the up button go back to the previous activity that called it
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
                onBackPressed()
                return true
        }
        return false
        //return super.onOptionsItemSelected(item)
    }
}