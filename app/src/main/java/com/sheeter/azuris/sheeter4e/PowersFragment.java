package com.sheeter.azuris.sheeter4e;

import android.content.Context;
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
        refreshFragment(root, getActivity());
        return root;
    }

    public static void refreshFragment(View root, Context context) {
        if (MainActivity.sCharacter != null) {
            ListView listView = (ListView) root.findViewById(R.id.Powers_List);
            PowersListViewAdapter adapter = new PowersListViewAdapter(context);

            listView.setAdapter(adapter);

            adapter.addAll(MainActivity.sCharacter.sheet.getPowers());
            adapter.notifyDataSetChanged();
        }
        MainActivity.sProgressBar.setVisibility(View.GONE);
    }
}