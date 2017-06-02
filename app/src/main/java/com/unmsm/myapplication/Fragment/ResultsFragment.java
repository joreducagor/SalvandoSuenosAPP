package com.unmsm.myapplication.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unmsm.myapplication.Adapter.ResultadosAdapter;
import com.unmsm.myapplication.Network.Models.ResultListResponse;
import com.unmsm.myapplication.R;
import com.unmsm.myapplication.SalvandoSuenosApplication;
import com.unmsm.myapplication.Utils.SharedPreferencesHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rubymobile on 31/05/17.
 */

public class ResultsFragment extends Fragment {

    RecyclerView rv_resultados;

    ResultadosAdapter adapter;

    SharedPreferencesHelper manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_results,container,false);

        manager = SharedPreferencesHelper.getInstance(getActivity());

        rv_resultados = (RecyclerView) v.findViewById(R.id.rv_resultados);

        getResults();

        return v;
    }

    private void getResults() {
        Call<ResultListResponse> call = SalvandoSuenosApplication.getInstance().getServices().getResults(String.valueOf(manager.getUserIdDb()));

        call.enqueue(new Callback<ResultListResponse>() {
            @Override
            public void onResponse(Call<ResultListResponse> call, Response<ResultListResponse> response) {
                if(response.isSuccessful()){
                    adapter = new ResultadosAdapter(getActivity(),response.body().getResults());
                    rv_resultados.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv_resultados.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResultListResponse> call, Throwable t) {

            }
        });
    }
}
