package campalans.m08.retrofit2

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import campalans.m08.retrofit2.databinding.ActivitySuperHeroViewHolderBinding
import com.squareup.picasso.Picasso

class SuperHeroViewHolder (view: View): RecyclerView.ViewHolder(view) {

    private val binding = ActivitySuperHeroViewHolderBinding.bind(view)

    fun bind(superHeroItemResponse: SuperHeroItemResponse, onItemSelected:(String) -> Unit)
    {
        binding.tvSuperHeroName.text = superHeroItemResponse.name
        Picasso.get().load(superHeroItemResponse.superHeroImage.url).into(binding.imageSuperHero)
        binding.root.setOnClickListener { onItemSelected(superHeroItemResponse.superHeroId) }
    }
}