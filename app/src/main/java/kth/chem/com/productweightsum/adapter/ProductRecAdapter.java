package kth.chem.com.productweightsum.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kth.chem.com.productweightsum.R;
import kth.chem.com.productweightsum.interfaces.ItemClickedListener;
import kth.chem.com.productweightsum.model.Product;

public class ProductRecAdapter extends RecyclerView.Adapter<ProductRecAdapter.CSV_ViewHolder> {
    private List<Product> productList;
    private ItemClickedListener listener;
    private boolean aBoolean;

    public ProductRecAdapter(List<Product> productsList, ItemClickedListener listener, boolean b) {
        this.productList = productsList;
        this.listener = listener;
        aBoolean = b;
    }

    @NonNull
    @Override
    public CSV_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new CSV_ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CSV_ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.info.setText(product.getName() + " " + product.getCount() + " " + product.getUnit() + " " + product.getWeightOfOne());

        if (aBoolean) {
            holder.quantity.setText(String.valueOf(product.getQuantity()));
            holder.total.setText(String.valueOf(product.gettotalWeight()));
        } else {
            holder.quantity.setVisibility(View.GONE);
            holder.total.setVisibility(View.GONE);
        }
    }

    public Product getProductItem(int position) {
        return productList.get(position);
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public void setList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    class CSV_ViewHolder extends RecyclerView.ViewHolder {
        private TextView info, quantity, total;

        CSV_ViewHolder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.productItemName);
            quantity = itemView.findViewById(R.id.productQuantity);
            total = itemView.findViewById(R.id.productItemTotalWeight);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickedListener(v, getAdapterPosition());
                }
            });
        }
    }
}
