package pl.atk.oeskandroidbenchmark.screens.devices;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pl.atk.oeskandroidbenchmark.R;
import pl.atk.oeskandroidbenchmark.model.TestResult;
import pl.atk.oeskandroidbenchmark.screens.devices.utils.RankingsAdapter;
import pl.atk.oeskandroidbenchmark.screens.main.MainActivity;

/**
 * Created by Tomasz on 13.12.2017.
 */

public class DevicesFragment extends Fragment implements DevicesContract.View {

    private DevicesContract.Presenter presenter = new DevicesPresenter(this);

    Unbinder unbinder;

    @BindView(R.id.devices_recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.search_view)
    SearchView searchView;

    private RankingsAdapter rankingsAdapter;

    public static DevicesFragment newInstance() {
        DevicesFragment devicesFragment = new DevicesFragment();

        return devicesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_devices, container, false);
        unbinder = ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                rankingsAdapter.filter(newText);
                return true;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.refreshDevicesList();
    }

    @Override
    public void stopRefreshAnimation() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void startRefreshAnimation() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void refreshData() {
        searchView.setQuery("",false);
        presenter.refreshDevicesList();
    }

    @Override
    public void adapterRefresh() {
        rankingsAdapter = new RankingsAdapter((presenter.getDevicesList()));
        recycler.setAdapter(rankingsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showErrorSnackBar() {
        showSnackBar("Error occurred while fetching data");
    }

    @Override
    public boolean checkInternetConnection() {
        return ((MainActivity) getActivity()).checkInternetConnection();
    }

    @Override
    public void showNoConnectionSnackBar() {
        showSnackBar("No Internet connection. Data can't be retrieved.");
    }

    private void showSnackBar(String text) {
        Snackbar.make(getView(), text, Snackbar.LENGTH_SHORT).show();
    }
}
