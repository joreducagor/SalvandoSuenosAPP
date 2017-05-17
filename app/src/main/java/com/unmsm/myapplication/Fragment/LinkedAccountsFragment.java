package com.unmsm.myapplication.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.unmsm.myapplication.Adapter.LinkedAccountsAdapter;
import com.unmsm.myapplication.Network.Models.DeleteLinkedAccountResponse;
import com.unmsm.myapplication.Network.Models.DeleteLinkedAccountbody;
import com.unmsm.myapplication.Network.Models.LinkedAccountItem;
import com.unmsm.myapplication.Network.Models.LinkedAccountsResponse;
import com.unmsm.myapplication.Network.MyTwitterApiClient;
import com.unmsm.myapplication.R;
import com.unmsm.myapplication.SalvandoSuenosApplication;
import com.unmsm.myapplication.Utils.SharedPreferencesHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rubymobile on 10/05/17.
 */

public class LinkedAccountsFragment extends Fragment {

    RecyclerView rv_linked_accounts;

    TwitterSession activeSession;

    public Dialog dialog;

    LinkedAccountsAdapter adapter;

    SharedPreferencesHelper manager;

    public static LinkedAccountsFragment instance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_linked_accounts,container,false);

        instance = this;

        manager = SharedPreferencesHelper.getInstance(getActivity());

        rv_linked_accounts = (RecyclerView) v.findViewById(R.id.rv_linked_accounts);

        activeSession = TwitterCore.getInstance().getSessionManager().getActiveSession();

        getLinkedAccounts();

        return v;
    }

    public void getLinkedAccounts() {
        Call<LinkedAccountsResponse> call = SalvandoSuenosApplication.getInstance().getServices().linkedAccounts(String.valueOf(manager.getUserIdDb()));

        call.enqueue(new Callback<LinkedAccountsResponse>() {
            @Override
            public void onResponse(Call<LinkedAccountsResponse> call, Response<LinkedAccountsResponse> response) {
                if(response.isSuccessful()){
                    setDataToRv(response.body().getLinked_accounts());
                }
            }

            @Override
            public void onFailure(Call<LinkedAccountsResponse> call, Throwable t) {

            }
        });
    }

    private void setDataToRv(List<LinkedAccountItem> linked_accounts) {
        adapter = new LinkedAccountsAdapter(linked_accounts,getActivity(),this);
        rv_linked_accounts.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_linked_accounts.setAdapter(adapter);
    }

    public void showDialog(final String id, final int position){
        dialog = new Dialog(getActivity(), R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_borrar);

        TextView btn_accept = (TextView) dialog.findViewById(R.id.dialog_si);
        TextView btn_reject = (TextView) dialog.findViewById(R.id.dialog_no);

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                DeleteLinkedAccountbody deleteBody = new DeleteLinkedAccountbody();

                deleteBody.setUsername(activeSession.getUserName());
                deleteBody.setTwitter_user_id(id);

                Call<DeleteLinkedAccountResponse> call = SalvandoSuenosApplication.getInstance().getServices().deleteLinkedAccount(deleteBody);

                call.enqueue(new Callback<DeleteLinkedAccountResponse>() {
                    @Override
                    public void onResponse(Call<DeleteLinkedAccountResponse> call, Response<DeleteLinkedAccountResponse> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getActivity(),"Cuenta desvinculada.",Toast.LENGTH_SHORT).show();
                            adapter.removeItem(position);
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteLinkedAccountResponse> call, Throwable t) {
                        Toast.makeText(getActivity(),"Error al desvincular la cuenta.",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }
}
