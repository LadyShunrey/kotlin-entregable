package ar.edu.unicen.seminarioentregable.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ar.edu.unicen.seminarioentregable.R

class WishlistFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout XML del Fragment
        return inflater.inflate(R.layout.fragment_wishlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Aquí puedes acceder a las vistas del layout XML del Fragment
        // y agregar la lógica para obtener la lista de películas populares
        // y mostrarlas en el RecyclerView
    }
}