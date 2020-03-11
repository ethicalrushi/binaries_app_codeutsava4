package binaries.app.codeutsava.restapi.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterProduce;
import binaries.app.codeutsava.restapi.model.farmer.FarmerProduceResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFarmerProduce extends DialogFragment {
    private RecyclerView recyclerView;
    private AdapterProduce mAdapter;
    private TextView farmProdEmptyText;
    private ProgressBar bar;

    public FragmentFarmerProduce() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();

        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_farmer_produce, container, false);
        recyclerView = view.findViewById(R.id.farmerProduceListRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        bar = view.findViewById(R.id.farm_prod_prog);

        view.findViewById(R.id.frag_far_prod_back).setOnClickListener(view1 -> dismiss());
        farmProdEmptyText = view.findViewById(R.id.farm_prod_empty_text);

        getFarmerProduceList();

        return view;
    }

    private void getFarmerProduceList() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<List<FarmerProduceResponse>> call = apiServices.getFarmerProduceList(
                PreferenceManager.getDefaultSharedPreferences(getContext()).getString("token", AppConstants.TEMP_FARM_TOKEN));

        call.enqueue(new Callback<List<FarmerProduceResponse>>() {
            @Override
            public void onResponse(Call<List<FarmerProduceResponse>> call, Response<List<FarmerProduceResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mAdapter = new AdapterProduce(response.body(), getActivity());
                    mAdapter.setFragManager(getFragmentManager());

                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }

                if(!response.isSuccessful() || response.body() == null || response.body().isEmpty())
                    farmProdEmptyText.setVisibility(View.VISIBLE);

                bar.setVisibility(GONE);
            }

            @Override
            public void onFailure(Call<List<FarmerProduceResponse>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                farmProdEmptyText.setVisibility(View.VISIBLE);
                bar.setVisibility(GONE);
            }
        });
    }

}
