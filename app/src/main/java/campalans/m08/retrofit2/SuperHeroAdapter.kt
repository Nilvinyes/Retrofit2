package campalans.m08.retrofit2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class SuperHeroAdapter (

    var SuperHeroList: List<SuperHeroItemResponse> = emptyList(),
    private val onItemSelected:(String) -> Unit):
    RecyclerView.Adapter<SuperHeroViewHolder>() {

        fun updateList(superHeroList: List<SuperHeroItemResponse>)
        {
            this.SuperHeroList = superHeroList
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder
        { return SuperHeroViewHolder(

            LayoutInflater.from(parent.context).inflate(R.layout.activity_super_hero_view_holder, parent, false))
        }

        override fun onBindViewHolder(viewHolder: SuperHeroViewHolder, position: Int)
        {
            viewHolder.bind(SuperHeroList[position], onItemSelected)
        }

        override fun getItemCount() = SuperHeroList.size
}