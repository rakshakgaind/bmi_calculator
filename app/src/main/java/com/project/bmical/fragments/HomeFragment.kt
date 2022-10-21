package com.project.bmical.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.bmical.R
import com.project.bmical.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var binding: FragmentHomeBinding
    private var height: Float = 0f
    private var weight: Float = 0f
    private var countWeight = 50
    private var countAge = 19
    private var chosen: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        initComponents()
        setUpClickListener()
    }

    private fun setUpClickListener() {
        binding.weightPlus.setOnClickListener {
            ++countWeight
            if (countWeight <= 200) {
                binding.weightTxt.text = (countWeight).toString()
            } else {
                countWeight--
                Toast.makeText(requireContext(), "Weight can't be more than 200", Toast.LENGTH_SHORT).show()
            }
        }

        binding.weightMinus.setOnClickListener {
            --countWeight
            if (countWeight >= 1) {
                binding.weightTxt.text = (countWeight).toString()
            } else {
                countWeight++
                Toast.makeText(requireContext(), "Weight can't be less than zero", Toast.LENGTH_SHORT).show()
            }
        }

        binding.agePlus.setOnClickListener {
            countAge++
            if (countAge < 150) {
                binding.age.text = countAge.toString()
            } else {
                countAge--
                Toast.makeText(requireContext(), "Age cannot be more than 150", Toast.LENGTH_SHORT).show()
            }
        }

        binding.ageMinus.setOnClickListener {
            countAge--
            if (countAge > 0) {
                binding.age.text = countAge.toString()
            } else {
                countAge++
                Toast.makeText(requireContext(), "Age can't be 0", Toast.LENGTH_SHORT).show()
            }

        }


        binding.cardViewMale.setOnClickListener {
            chosen = getString(R.string.male)
            Log.d("male", "setUpClickListener: $chosen")
            binding.maleTxt.setTextColor(AppCompatResources.getColorStateList(requireContext(), R.color.md_theme_light_tertiary))
            binding.femaleTxt.setTextColor(AppCompatResources.getColorStateList(requireContext(), R.color.md_theme_dark_shadow))

            binding.cardViewMale.setCardBackgroundColor(AppCompatResources.getColorStateList(requireContext(), R.color.md_theme_dark_tertiary))
            binding.cardViewFemale.setCardBackgroundColor(AppCompatResources.getColorStateList(requireContext(), android.R.color.transparent))
        }

        binding.cardViewFemale.setOnClickListener {
            chosen = getString(R.string.female)
            Log.d("female", "setUpClickListener: $chosen")
            binding.femaleTxt.setTextColor(AppCompatResources.getColorStateList(requireContext(), R.color.md_theme_light_tertiary))
            binding.maleTxt.setTextColor(AppCompatResources.getColorStateList(requireContext(), R.color.md_theme_dark_shadow))
            binding.cardViewFemale.setCardBackgroundColor(AppCompatResources.getColorStateList(requireContext(), R.color.md_theme_dark_tertiary))
            binding.cardViewMale.setCardBackgroundColor(AppCompatResources.getColorStateList(requireContext(), android.R.color.transparent))
        }
        binding.calculate.setOnClickListener {
            Log.d("male", "setUpClickListener: $chosen")
            if (chosen != null) {
                if (height.equals(0f)) {
                    Toast.makeText(requireContext(), "Height cannot be Zero, Try again", Toast.LENGTH_SHORT).show()
                } else {
                    weight = binding.weightTxt.text.toString().toFloat()
                    calculateBMI(binding.age.text.toString())
                }
            } else {
                Toast.makeText(requireContext(), "Choose your gender", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initComponents() {

        binding.apply {

            Seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    val ht = progress.toString() + resources.getString(R.string.cm)
                    binding.heightTxt.text = ht
                    height = progress.toFloat() / 100
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })

        }
    }

    override fun onResume() {
        if (chosen != null) {
            if (chosen == getString(R.string.male)) {
                Log.d("male", "setUpClickListener: $chosen")
                binding.maleTxt.setTextColor(AppCompatResources.getColorStateList(requireContext(), R.color.md_theme_light_tertiary))
                binding.femaleTxt.setTextColor(AppCompatResources.getColorStateList(requireContext(), R.color.md_theme_dark_shadow))

                binding.cardViewMale.setCardBackgroundColor(AppCompatResources.getColorStateList(requireContext(), R.color.md_theme_dark_tertiary))
                binding.cardViewFemale.setCardBackgroundColor(AppCompatResources.getColorStateList(requireContext(), android.R.color.transparent))
            } else {
                Log.d("female", "setUpClickListener: $chosen")
                binding.femaleTxt.setTextColor(AppCompatResources.getColorStateList(requireContext(), R.color.md_theme_light_tertiary))
                binding.maleTxt.setTextColor(AppCompatResources.getColorStateList(requireContext(), R.color.md_theme_dark_shadow))
                binding.cardViewFemale.setCardBackgroundColor(AppCompatResources.getColorStateList(requireContext(), R.color.md_theme_dark_tertiary))
                binding.cardViewMale.setCardBackgroundColor(AppCompatResources.getColorStateList(requireContext(), android.R.color.transparent))
            }
        }
        binding.heightTxt.text = binding.Seekbar.progress.toString()+"cm"

        super.onResume()

    }

    private fun calculateBMI(age: String) {
        val bmi = weight / (height * height)
        val action = HomeFragmentDirections.actionHomeFragmentToResultFragment(bmi, age)
        findNavController().navigate(action)
    }

}