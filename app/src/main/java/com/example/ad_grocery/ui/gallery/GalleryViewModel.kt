package com.example.ad_grocery.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ad_grocery.objects.Produce
import java.util.Date

class GalleryViewModel : ViewModel() {
    private val _products = MutableLiveData<List<Produce>>().apply {
        value = arrayListOf(
            Produce("1", 1, Date(), 5.0f, 2, 0, 0.0f, "image1.png"),
            Produce("2", 1, Date(), 7.5f, 1, 0, 0.0f, "image2.png"),
            Produce("3", 2, Date(), 3.0f, 5, 1, 0.0f, "image3.png"),
            Produce("4", 2, Date(), 4.0f, 3, 1, 0.0f, "image4.png"),
            Produce("5", 3, Date(), 10.0f, 1, 2, 0.0f, "image5.png"),
            Produce("6", 3, Date(), 6.5f, 4, 2, 0.0f, "image6.png"),
            Produce("7", 4, Date(), 2.0f, 8, 3, 0.0f, "image7.png"),
            Produce("8", 4, Date(), 12.0f, 1, 3, 0.0f, "image8.png"),
            Produce("9", 5, Date(), 15.0f, 2, 4, 0.0f, "image9.png"),
            Produce("10", 5, Date(), 7.0f, 6, 4, 0.0f, "image10.png"),
            Produce("11", 6, Date(), 9.5f, 1, 5, 0.0f, "image11.png"),
            Produce("12", 6, Date(), 3.5f, 3, 5, 0.0f, "image12.png")
        )
    }
    val products: LiveData<List<Produce>> = _products
}
