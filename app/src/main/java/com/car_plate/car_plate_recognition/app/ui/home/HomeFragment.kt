package com.car_plate.car_plate_recognition.app.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import com.car_plate.car_plate_recognition.R
import com.car_plate.car_plate_recognition.app.arch.BaseFragment
import com.car_plate.car_plate_recognition.app.ui.main.MainActivity
import com.car_plate.car_plate_recognition.app.util.Constants.AUTHORITY
import com.car_plate.car_plate_recognition.app.util.Constants.REQUEST_IMAGE
import com.car_plate.car_plate_recognition.app.util.ImageLoader
import com.car_plate.car_plate_recognition.app.util.extensions.toast
import com.car_plate.car_plate_recognition.databinding.HomeScreenFragmentBinding
import com.car_plate.domain.entity.Car
import org.koin.android.ext.android.inject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class HomeFragment : BaseFragment<HomeScreenFragmentBinding>() {
    override fun getLayoutId() = R.layout.home_screen_fragment

    override fun initViewModel() = this

    override fun getCurrentDestinationId(): Int = R.id.homeFragment

    private var destination: File? = null

    private val viewModel: HomeViewModel by inject()
    private val imageLoader: ImageLoader by inject()

    private lateinit var saveContext: Context
    private lateinit var saveActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (context != null) saveContext = context as Context
        if (activity != null) saveActivity = activity as MainActivity
    }

    override fun onResume() {
        super.onResume()
        binding.presenter = this

//        getHtmlFromWeb()
    }

    override fun onPause() {
        super.onPause()
        binding.presenter = null
    }

    override fun setSubscribers() {
        viewModel.recognitionResult.observe(
            viewLifecycleOwner,
            Observer { setRecognitionResult(it) })
        viewModel.errorEvent.observe(
            viewLifecycleOwner,
            Observer { showError(it.message.toString()) })
        viewModel.carData.observe(viewLifecycleOwner, Observer { showCarResult(it) })
    }

    @SuppressLint("SetTextI18n")
    private fun showCarResult(car: Car) {
        binding.tvCarModel.text = getString(R.string.model) + car.vendor + " " + car.model
        binding.tvCarYear.text = getString(R.string.year) + car.year
        binding.tvCarRegion.text = getString(R.string.region) + car.region
        binding.tvCarIsStolen.text = ifIsStolen(car.isStolen)
        car.photoUrl?.let { imageLoader.loadImage(binding.imageView, it) }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        imageLoader.loadImage(binding.imageView, destination)
        binding.numberInput.setText("")

        Handler().postDelayed({
            val photoURI: Uri = destination?.let {
                FileProvider.getUriForFile(
                        saveContext, AUTHORITY, it)
            }!!
            photoURI.let { viewModel.processImage(saveContext, it) }
        }, 1500)
    }

    fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(saveContext.packageManager)?.also {
                // Create the File where the photo should go
                destination = try {
                    viewModel.createImageFile(saveContext)
                } catch (ex: IOException) {
                    showError(ex.toString())
                    null
                }
                // Continue only if the File was successfully created
                destination?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        saveContext, AUTHORITY, it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE)
                }
            }
        }
    }

    private fun setRecognitionResult(result: String) {
        if (result.length == 8) {
            binding.numberInput.append(result + "\n")
        }
    }

    private fun showError(message: String) {
        message.apply {
            saveContext.toast(this)
        }
    }

    fun searchByText() {
        viewModel.getCarInfoByPlateNumber(binding.numberInput.text.toString())
    }

    private fun ifIsStolen(isStolen: Boolean): String {
        return if (isStolen) {
            "Угон: Числится в угоне"
        } else {
            "Угон: Не числится в угоне"
        }
    }

    private fun getHtmlFromWeb() {
        Thread(Runnable {
            val stringBuilder = StringBuilder()
            try {
                val doc: Document =
                    Jsoup.connect("https://policy-web.mtsbu.ua/Search/ByRegNo?md=8FA7EADE99CE3A087D705E42954E4AC26F94B62D6F53B19EA9C5751A6F5FB24DBD928BF82E42744FDE4D0028C0735B992408672402DC5457DBD45310F1E41C26")
                        .get()
                val title: String = doc.title()
                val links: Elements = doc.select("a[href]")
                stringBuilder.append(title).append("\n")
                for (link in links) {
                    stringBuilder.append("\n").append(link.text())
                }
            } catch (e: IOException) {
                stringBuilder.append("Error : ").append(e.message).append("\n")
            }
            activity?.runOnUiThread {
                Toast.makeText(context, stringBuilder.toString(), Toast.LENGTH_LONG).show()
            }
        }).start()
    }
}