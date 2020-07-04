package com.rps.animalslistdagger2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavAction
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.rps.animalslistdagger2.R
import com.rps.animalslistdagger2.clicklisteners.AnimalItemListener
import com.rps.animalslistdagger2.databinding.AnimalItemBinding
import com.rps.animalslistdagger2.model.Animal
import com.rps.animalslistdagger2.util.getProgressDrawable
import com.rps.animalslistdagger2.util.loadImage
import com.rps.animalslistdagger2.view.ListFragmentDirections
import kotlinx.android.synthetic.main.animal_item.view.*

class AnimalListAdapter(private val animalList:ArrayList<Animal>):
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(),AnimalItemListener {


    fun updateAnimalList(newAnimalList:List<Animal>){
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {

        val inflater:LayoutInflater = LayoutInflater.from(parent.context)
        val  view = DataBindingUtil.inflate<AnimalItemBinding>(inflater,R.layout.animal_item,parent,false)
        return AnimalViewHolder(view)
    }

    override fun getItemCount() = animalList.size


    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animal = animalList[position]
        holder.view.animalListener = this

    }
    class AnimalViewHolder(var view: AnimalItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onAnimalClick(v: View) {
       for (animal in animalList){
           if(v.tag == animal.name){
               val action = ListFragmentDirections.goToAnimalDetail(animal)
               Navigation.findNavController(v).navigate(action)
           }
       }
    }
}