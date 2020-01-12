package com.test.study;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassNotesFragment extends Fragment {

    private RecyclerView rcv;
    private List<StudyModel> eBookList;
    private RcvAdapter mAdapter;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_class_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.notes_rcv);
        rcv.setHasFixedSize(true);
        progressBar = view.findViewById(R.id.progress_nBar);
        progressBar.setVisibility(View.VISIBLE);
        rcv.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        Map<String,String> headers = new HashMap<>();
        headers.put("User-ID","1");
        headers.put("Authorization","67nPxwLC/yyGc");

        Map<String,String > params = new HashMap<>();
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
                    }
                }else{
                    assert response.errorBody() != null;
                    Toast.makeText(getActivity(),response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NotNull Call<MaterialResponse> call, @NotNull Throwable t) {

            }
        });
    }
}
