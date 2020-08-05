package com.example.ecommerce.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ecommerce.R
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel.mustSetToken.observe(viewLifecycleOwner, Observer {mustSetToken ->
            if (mustSetToken) {
                get_token_button.setOnClickListener {
                    login_progressBar.visibility = View.VISIBLE
                    loginViewModel.tokenSetup()
                }
            } else {
                login_progressBar.visibility = View.INVISIBLE
                this.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMarketListFragment())
            }
        })
    }
}