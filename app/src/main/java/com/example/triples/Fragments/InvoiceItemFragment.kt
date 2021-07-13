package com.example.triples.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.triples.Adapters.InvoiceProductAdapter
import com.example.triples.DataContainers.InvoiceItems
import com.example.triples.R
import com.example.triples.databinding.FragmentInvoiceItemBinding

class InvoiceItemFragment (invoiceItems : List<InvoiceItems>) : Fragment() {
    val invoiceItems = invoiceItems
    lateinit var binding : FragmentInvoiceItemBinding
    lateinit var adapter : InvoiceProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInvoiceItemBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init(){
        adapter = InvoiceProductAdapter(layoutInflater ,invoiceItems)
        binding.invoiceItemRecyclerView.adapter =adapter
        binding.invoiceItemRecyclerView.layoutManager = LinearLayoutManager(context
            , RecyclerView.VERTICAL , false)
    }


}