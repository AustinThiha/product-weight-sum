package kth.chem.com.productweightsum;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import kth.chem.com.productweightsum.adapter.ProductRecAdapter;
import kth.chem.com.productweightsum.interfaces.ItemClickedListener;
import kth.chem.com.productweightsum.interfaces.SheetItemClickListener;
import kth.chem.com.productweightsum.model.Product;
import kth.chem.com.productweightsum.utils.CSVFile;

public class ShowProductBottomSheet extends BottomSheetDialogFragment implements ItemClickedListener {
    private static ShowProductBottomSheet instance;
    private SheetItemClickListener listener;
    private List<Product> productList;
    private ProductRecAdapter adapter;
    private Dialog dialog;
    private EditText inputQuantity;
    private Button saveQuantity;

    private ShowProductBottomSheet() {
    }

    public static ShowProductBottomSheet getInstance() {
        return instance == null ? new ShowProductBottomSheet() : instance;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            InputStream inputStream = new BufferedInputStream(getActivity().getAssets().open("tests.csv"));
            CSVFile csvFile = new CSVFile(inputStream);
            productList = csvFile.read();
            adapter.setList(productList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_product_sheet, container, false);
        RecyclerView showProductListRec = view.findViewById(R.id.showProductsFromCSV);

        showProductListRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ProductRecAdapter(productList, this, false);
        showProductListRec.setAdapter(adapter);

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog sheetDialog = (BottomSheetDialog) dialog;
                FrameLayout bottomSheet = sheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                BottomSheetBehavior.from(bottomSheet).setSkipCollapsed(true);
                BottomSheetBehavior.from(bottomSheet).setHideable(true);
            }
        });
        return bottomSheetDialog;
    }

    @Override
    public void onItemClickedListener(View view, int position) {
        setUpDialog(position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            listener = (SheetItemClickListener) activity;
        }
    }

    private void setUpDialog(final int position) {
        if (dialog == null) {
            dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.weight_input_dialog);
            dialog.setCanceledOnTouchOutside(false);

            inputQuantity = dialog.findViewById(R.id.weightInputEdt);
            saveQuantity = dialog.findViewById(R.id.weightSaveBtn);

        }
        dialog.show();
        saveQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputQuantity.getText().length() > 0) {

                    dialog.cancel();
                    getDialog().cancel();
                    Product product = adapter.getProductItem(position);
                    product.setQuantity(Integer.valueOf(inputQuantity.getText().toString()));

                    listener.recItemClickedListener(position, product);
                } else {
                    Toast.makeText(getActivity(), "add quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}