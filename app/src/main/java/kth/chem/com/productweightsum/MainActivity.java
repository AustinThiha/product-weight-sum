package kth.chem.com.productweightsum;

//09.12.2019

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kth.chem.com.productweightsum.adapter.ProductRecAdapter;
import kth.chem.com.productweightsum.interfaces.ItemClickedListener;
import kth.chem.com.productweightsum.interfaces.SheetItemClickListener;
import kth.chem.com.productweightsum.model.Product;

public class MainActivity extends AppCompatActivity implements ItemClickedListener, SheetItemClickListener {
    private ProductRecAdapter adapter;
    private List<Product> usedProduct;
    private RecyclerView showUsedRec;
    private TextView showTotalWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showUsedRec = findViewById(R.id.showProductsFromUsed);
        showUsedRec.setLayoutManager(new LinearLayoutManager(this));
        showTotalWeight = findViewById(R.id.showUsedProductTotalWeight);

        findViewById(R.id.addProductToCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowProductBottomSheet sheet = ShowProductBottomSheet.getInstance();
                sheet.show(getSupportFragmentManager(), "show");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        usedProduct = new ArrayList<>();
        if (adapter == null) {
            adapter = new ProductRecAdapter(usedProduct, this, true);
            showUsedRec.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClickedListener(View view, int position) {
        Toast.makeText(this, usedProduct.get(position).getName(), Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void recItemClickedListener(int position, Product product) {
        checkSaleAdd(product);

        int total = 0;
        if (usedProduct.size() > 0) {
            for (Product p : usedProduct) {
                total += p.gettotalWeight();
            }
        }
        showTotalWeight.setText("Total Weight : " + total);
    }

    private void checkSaleAdd(Product pro) {
        Product tempSale = null;
        for (Product product : usedProduct) {
            if (product.getName().equals(pro.getName())) {
                tempSale = product;
            }
        }
        usedProduct.add(pro);
        usedProduct.remove(tempSale);
        adapter.setList(usedProduct);
    }
}
