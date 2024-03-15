package com.sbapps.scheduleplus.presentation.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sbapps.scheduleplus.MyApplication
import com.sbapps.scheduleplus.R
import com.sbapps.scheduleplus.databinding.FragmentOnBoardingBinding
import com.sbapps.scheduleplus.di.FragmentComponent
import com.sbapps.scheduleplus.di.ViewModelFactory
import javax.inject.Inject

class OnBoardingFragment : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentOnBoardingBinding is null")

    companion object {
        private const val IS_NIGHT_MODE = "is_night_mode"
        fun getBundle(isNightMode: Boolean): Bundle {
            val bundle = Bundle()
            bundle.putBoolean(IS_NIGHT_MODE, isNightMode)
            return bundle
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[OnBoardingViewModel::class.java]
    }

    private val component : FragmentComponent by lazy {
        (requireActivity().application as MyApplication).component
            .fragmentComponentFactory().create()
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.setList(it.getBoolean(IS_NIGHT_MODE))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onBoardingRoundList = listOf(
            binding.imageViewOnBoarding1,
            binding.imageViewOnBoarding2,
            binding.imageViewOnBoarding3,
            binding.imageViewOnBoarding4,
        )
        viewModel.currentOnBoarding.observe(viewLifecycleOwner) {
            binding.textViewTitle.text = ContextCompat.getString(requireContext(), it.titleResId)
            binding.textViewDescription.text =
                ContextCompat.getString(requireContext(), it.descriptionResId)
            binding.imageViewOnBoarding.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    it.imageResId
                )
            )
            for (onBoardingRound in onBoardingRoundList) {
                if (onBoardingRoundList.indexOf(onBoardingRound) == viewModel.getIndexOfOnBoarding()) {
                    onBoardingRound.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.on_boarding_round_active
                        )
                    )
                } else {
                    onBoardingRound.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.on_boarding_round_inactive
                        )
                    )
                }
            }
        }
        binding.buttonContinue.setOnClickListener {
            if (!viewModel.getNextOnBoarding()) {
                findNavController().popBackStack()
            }
        }
    }
}