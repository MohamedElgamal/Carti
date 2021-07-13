package com.example.triples.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triples.DataContainers.InvoiceItems;
import com.example.triples.DataContainers.InvoicesResponseItem;
import com.example.triples.R;
import com.example.triples.databinding.InvoiceItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InvoicesAdapter extends RecyclerView.Adapter<InvoicesAdapter.ViewHolder>{
    private LayoutInflater layoutInflater;
    private List<InvoicesResponseItem> invoiceItems;
    private OnInvoiceItemClicked onInvoiceItemClicked;

    public InvoicesAdapter(LayoutInflater layoutInflater, List<InvoicesResponseItem> invoiceItems ,OnInvoiceItemClicked onInvoiceItemClicked) {
        this.layoutInflater = layoutInflater;
        this.invoiceItems = invoiceItems;
        this.onInvoiceItemClicked = onInvoiceItemClicked;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        InvoiceItemBinding binding = InvoiceItemBinding.inflate(layoutInflater , parent , false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String transId = invoiceItems.get(position).getTransactionId();
        String paymentType = invoiceItems.get(position).getPaymentMethod();
        String date = invoiceItems.get(position).getDate();
        Double total = invoiceItems.get(position).getTotal();
        holder.binding.invoiceDateText.setText(date);
        holder.binding.invoicePaymentTypeText.setText(paymentType);
        holder.binding.invoiceTransIdText.setText("Trans id : " + transId);
        List<InvoiceItems> items = invoiceItems.get(position).getItems();
        if(paymentType.equals("stripe")){
            holder.binding.invoicePaymentMethodImage.setImageResource(R.drawable.credit);
        }
        else{
            holder.binding.invoicePaymentMethodImage.setImageResource(R.drawable.cash);
        }
        holder.binding.invoiceTotalText.setText(""+total +" EGP");
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onInvoiceItemClicked.onInvoiceItemClicked(items);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(invoiceItems == null) return 0;
        return invoiceItems.size();
    }

    public void update(List<InvoicesResponseItem> invoiceItems){
        this.invoiceItems = invoiceItems;
        notifyDataSetChanged();
    }

    public interface OnInvoiceItemClicked{
        void onInvoiceItemClicked(List<InvoiceItems> items);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        InvoiceItemBinding binding;
        public ViewHolder(@NonNull @NotNull InvoiceItemBinding binding ) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
