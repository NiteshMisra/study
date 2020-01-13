package com.test.study;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class PrevPaperFragment extends Fragment {

    private RecyclerView rcv;
    private List<StudyModel> eBookList;
    private RcvAdapter mAdapter;
    private TextView noInternet;
    private TextView noData;
    private SwipeRefreshLayout eBookRefresh;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prev_paper, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.prev_rcv);
        rcv.setHasFixedSize(true);
        noInternet = view.findViewById(R.id.prevNoInternet);
        noData = view.findViewById(R.id.prevNoData);
        rcv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        eBookRefresh = view.findViewById(R.id.prevRefresh);
        loadLayout();
        eBookRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadLayout();
            }
        });
    }

    private void loadLayout(){
        if (isInternet()){
            eBookRefresh.setRefreshing(true);
            noData.setText("Please wait...");
            rcv.setVisibility(View.GONE);
            noInternet.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
            Map<String,String> headers = new HashMap<>();
            headers.put("User-ID","1");
            headers.put("Authorization","67nPxwLC/yyGc");

            Map<String,String> params = new HashMap<>();
            params.put("start","-1");
            params.put("type","2");

            RetrofitClient.getInstance().getApi().getStudyMaterials(headers,params).enqueue(new Callback<MaterialResponse>() {
                @Override
                public void onResponse(@NotNull Call<MaterialResponse> call, @NotNull Response<MaterialResponse> response) {
                    if(response.isSuccessful()){
                        assert response.body() != null;
                        if (response.body().getData() != null){
                            eBookList = response.body().getData();
                            mAdapter = new RcvAdapter(getActivity(), eBookList);
                            rcv.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                            noData.setVisibility(View.GONE);
                            noInternet.setVisibility(View.GONE);
                            rcv.setVisibility(View.VISIBLE);
                        }else {
                            noData.setText("No Data is available");
                            noData.setVisibility(View.VISIBLE);
                            noInternet.setVisibility(View.GONE);
                            rcv.setVisibility(View.GONE);
                        }
                    }else{
                        assert response.errorBody() != null;
                        noData.setText("No Response from Server.\nRefresh to try again");
                        noData.setVisibility(View.VISIBLE);
                        noInternet.setVisibility(View.GONE);
                        rcv.setVisibility(View.GONE);
                    }
                    eBookRefresh.setRefreshing(false);
                }

                @Override
                public void onFailure(@NotNull Call<MaterialResponse> call, @NotNull Throwable t) {
                    eBookRefresh.setRefreshing(false);
                    noData.setText("Server not Responding\nRefresh to try again");
                    noData.setVisibility(View.VISIBLE);
                    noInternet.setVisibility(View.GONE);
                    rcv.setVisibility(View.GONE);
                }
            });
        }else{
            eBookRefresh.setRefreshing(false);
            noInternet.setText("No Internet Connection\nRefresh to try again");
            noData.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);
            rcv.setVisibility(View.GONE);
        }
    }

    private Boolean isInternet(){
        ConnectivityManager cm = (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null;
    }
}
