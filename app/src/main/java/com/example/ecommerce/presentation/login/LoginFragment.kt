package com.example.ecommerce.presentation.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ecommerce.R
import com.example.ecommerce.common.utils.TokenGenerator
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.delay
import java.util.*
import kotlin.concurrent.schedule

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        get_token_button.setOnClickListener {
            login_progressBar.visibility = View.VISIBLE

            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            var token = sharedPref?.getString(getString(R.string.saved_token_key), null)

            if ( token == null) {
                TokenGenerator.generateAuthToken {
                    Log.i("i/LoginFragment", "Token NULO")
                    token = "Bearer $it"
                    sharedPref?.edit()?.putString(getString(R.string.saved_token_key), token)?.commit()
                    login_progressBar.visibility = View.INVISIBLE
                    this.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMarketListFragment())
                }
            } else {
                Log.i("i/LoginFragment", "Token NO es NULO, es: $token")
                login_progressBar.visibility = View.INVISIBLE
                this.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMarketListFragment())
            }
        }

//        TODO: LiveData loading en ViewModel, para observar y cambiar visibility de progress_bar
    }
}