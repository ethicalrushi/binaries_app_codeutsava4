package binaries.app.codeutsava.restapi.activites;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterFarmerBids;
import binaries.app.codeutsava.restapi.adapters.AdapterFilter;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFarmerBids extends BaseActivity {
    RecyclerView recyclerView;
    AdapterFarmerBids adapterFarmerBids;
    TextView farmBulkOrdEmptyT;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_farmer_bids;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = findViewById(R.id.farmer_bids_recycler);
        farmBulkOrdEmptyT = findViewById(R.id.farm_bulk_ord_empty_text);

        findViewById(R.id.act_farm_bid_back).setOnClickListener(view -> {
            Intent intent = new Intent(ActivityFarmerBids.this, ActivityFarmer.class);
            startActivity(intent);
            finish();
        });

        getbids();
    }

    void getbids() {
        AppClient.getInstance().createService(APIServices.class)
                .getFarmerActiveBidList(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", AppConstants.TEMP_FARM_TOKEN))
                .enqueue(new Callback<List<FarmerActiveBidListResponse>>() {
                    @Override
                    public void onResponse(Call<List<FarmerActiveBidListResponse>> call, Response<List<FarmerActiveBidListResponse>> response) {
                        if (response.isSuccessful()) {
                            adapterFarmerBids = new AdapterFarmerBids(ActivityFarmerBids.this, response.body());
                            recyclerView.setAdapter(adapterFarmerBids);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ActivityFarmerBids.this));
                            adapterFarmerBids.notifyDataSetChanged();
                        }

                        if(!response.isSuccessful() || response.body() == null || response.body().isEmpty())
                            farmBulkOrdEmptyT.setVisibility(View.VISIBLE);

                        findViewById(R.id.farm_bids_prog).setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<List<FarmerActiveBidListResponse>> call, Throwable t) {
                        Toast.makeText(ActivityFarmerBids.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        farmBulkOrdEmptyT.setVisibility(View.VISIBLE);
                        findViewById(R.id.farm_bids_prog).setVisibility(View.GONE);
                    }
                });
    }
}
