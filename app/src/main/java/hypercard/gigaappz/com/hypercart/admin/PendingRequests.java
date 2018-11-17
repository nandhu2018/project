package hypercard.gigaappz.com.hypercart.admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hypercard.gigaappz.com.hypercart.R;
import hypercard.gigaappz.com.hypercart.model_class.Shop;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingRequests extends Fragment {
    RecyclerView recyclerView;
    List<Shop>pendinglist= new ArrayList<>();;
    Userpendingadapter userpendingadapter;

    public PendingRequests() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_pending_requests, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        userpendingadapter=new Userpendingadapter(pendinglist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userpendingadapter);

        getpendinglist();
        return view;
    }

    private void getpendinglist() {
        //  TODO pending shop request

    }


}
