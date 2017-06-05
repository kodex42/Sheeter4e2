package com.sheeter.azuris.sheeter4e;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sheeter.azuris.sheeter4e.Modules.Frequency;
import com.sheeter.azuris.sheeter4e.Modules.Power;

/**
 * Created by Azuris on 2017-06-03.
 */

public class PowersFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.content_powers, container, false);

        ListView listView = (ListView) root.findViewById(R.id.Powers_List);
        PowersListViewAdapter adapter = new PowersListViewAdapter(getActivity());

        listView.setAdapter(adapter);
        adapter.add(new Power("Magic Missle", Frequency.AT_WILL));
        adapter.notifyDataSetChanged();


        return root;
    }
}