package com.eslammongy.quizeapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.eslammongy.quizeapp.databinding.FragmentSplashBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController:NavController
    private lateinit var firebaseAuth:FirebaseAuth
    private val START_TAG = "START_LOG"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseApp.initializeApp(requireContext())
        navController = Navigation.findNavController(view)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.startFeedback.text = ("Checking User Account... ")
    }

    override fun onStart() {
        super.onStart()

        val currentUser = firebaseAuth.currentUser
        if (currentUser == null){
            binding.startFeedback.text = ("Creating User Account... ")
            firebaseAuth.signInAnonymously().addOnCompleteListener {
                if (it.isSuccessful){
                    binding.startFeedback.text = ("Account Created.. ")
                    navController.navigate(R.id.action_startFragment_to_listFragment)
                }else {
                    Log.d(START_TAG, "Start Log : " + it.exception!!.message)
                }
            }
        }else{

            binding.startFeedback.text = ("Login Successfully... ")
            navController.navigate(R.id.action_startFragment_to_listFragment)
        }

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}