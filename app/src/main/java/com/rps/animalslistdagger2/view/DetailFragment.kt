package com.rps.animalslistdagger2.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

import com.rps.animalslistdagger2.R
import com.rps.animalslistdagger2.databinding.FragmentDetailBinding
import com.rps.animalslistdagger2.model.Animal
import com.rps.animalslistdagger2.model.AnimalPalette
import com.rps.animalslistdagger2.util.getProgressDrawable
import com.rps.animalslistdagger2.util.loadImage
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    var animal: Animal? = null
    private lateinit var dataBinding: FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }
        animal?.imageUrl?.let {
            setUpBackgroundColor(it)
        }
        dataBinding.animal = animal
    }

    private fun setUpBackgroundColor(imageUrl: String) {
        Glide.with(this).asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate() { palette ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                            dataBinding.palette = AnimalPalette(intColor)
                        }
                }

            })
    }


}
