package com.example.triples.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.triples.Adapters.InvoicesAdapter
import com.example.triples.ApisConnectionClasses.AbstractConnectionHandler
import com.example.triples.ApisConnectionClasses.InvoicesConnection
import com.example.triples.DataContainers.Constants
import com.example.triples.DataContainers.InvoiceItems
import com.example.triples.DataContainers.InvoicesResponse
import com.example.triples.PreferencesHelper
import com.example.triples.R
import com.example.triples.databinding.FragmentInvoiceBinding


class InvoiceFragment (onItemInvoiceClickedFragmentReplace :OnItemInvoiceClickedFragmentReplace)
    : Fragment() , AbstractConnectionHandler<InvoicesResponse> ,InvoicesAdapter.OnInvoiceItemClicked{
    lateinit var binding : FragmentInvoiceBinding
    lateinit var adapter : InvoicesAdapter
    lateinit var invoiceConnection : InvoicesConnection
    lateinit var sharePreferencesHelper: PreferencesHelper
    var onItemInvoiceClickedFragmentReplace = onItemInvoiceClickedFragmentReplace


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentInvoiceBinding.inflate(layoutInflater )
        init()
        invoiceConnection.connectToGetInvoice("Bearer " + sharePreferencesHelper.getAuthToken())
        return binding.root
    }
    private fun init(){
        adapter = InvoicesAdapter(layoutInflater , null , this)
        binding.invoiceRecyclerView.adapter = adapter
        binding.invoiceRecyclerView.layoutManager = LinearLayoutManager(context
            ,RecyclerView.VERTICAL , false)
        invoiceConnection = InvoicesConnection(this)
        sharePreferencesHelper = PreferencesHelper(requireContext() , Constants.CREDENTIALS_FILE_PATH)

    }

    override fun onInvoiceItemClicked(items: MutableList<InvoiceItems>?) {
        onItemInvoiceClickedFragmentReplace.fragmentReplace(items!!)
    }

    override fun onSuccessConnection(data:InvoicesResponse?) {
        Log.e("Array of data " , "" + data)
        adapter.update(data?.history)
    }

    override fun onFailedConnection() {
        binding.invoiceNoItemsText.visibility = View.VISIBLE
    }

    override fun onFailureConnection() {
        binding.invoiceNoItemsText.visibility = View.VISIBLE
        binding.invoiceNoItemsText.text = "Check the internet connection or firewall "
    }

    public interface OnItemInvoiceClickedFragmentReplace{
        fun fragmentReplace(items: MutableList<InvoiceItems>)
    }
}