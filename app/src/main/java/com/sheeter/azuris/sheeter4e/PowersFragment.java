package com.sheeter.azuris.sheeter4e;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sheeter.azuris.sheeter4e.MainActivity;
import com.sheeter.azuris.sheeter4e.Modules.Frequency;
import com.sheeter.azuris.sheeter4e.Modules.Power;
import com.sheeter.azuris.sheeter4e.PowersListViewAdapter;
import com.sheeter.azuris.sheeter4e.R;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidbucket.utils.imageprocess.ABShape;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azuris on 2017-06-03.
 */

public class PowersFragment extends Fragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {
    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;
    Context context;
    public static PowersListViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        adapter = new PowersListViewAdapter(context);

        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.content_powers, container, false);
        refreshFragment(root, context);

        rfaLayout = (RapidFloatingActionLayout) root.findViewById(R.id.activity_main_rfal);
        rfaBtn = (RapidFloatingActionButton) root.findViewById(R.id.activity_main_rfab);

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(context);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Refresh At-Wills")
                .setResId(R.mipmap.ic_refresh)
                .setIconNormalColor(context.getResources().getColor(R.color.atWillPower))
                .setIconPressedColor(context.getResources().getColor(R.color.atWillPowerPressed))
                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Refresh Enounters")
                .setResId(R.mipmap.ic_refresh)
                .setIconNormalColor(context.getResources().getColor(R.color.encounterPower))
                .setIconPressedColor(context.getResources().getColor(R.color.encounterPowerPressed))
                .setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Refresh Dailies")
                .setResId(R.mipmap.ic_refresh)
                .setIconNormalColor(context.getResources().getColor(R.color.dailyPower))
                .setIconPressedColor(context.getResources().getColor(R.color.dailyPowerPressed))
                .setWrapper(2)
        );
        rfaContent
                .setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(context, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(context, 5))
        ;
        rfabHelper = new RapidFloatingActionHelper(
                context,
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();

        return root;
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        //Toast.makeText(getContext(), "clicked label: " + position, Toast.LENGTH_SHORT).show();
        adapter.refreshPowers(position);
        rfabHelper.toggleContent();
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        //Toast.makeText(getContext(), "clicked icon: " + position, Toast.LENGTH_SHORT).show();
        adapter.refreshPowers(position);
        rfabHelper.toggleContent();
    }

    public static void refreshFragment(View root, Context context) {
        if (MainActivity.sCharacter != null) {
            ListView listView = (ListView) root.findViewById(R.id.Powers_List);

            listView.setAdapter(adapter);

            adapter.addAll(MainActivity.sCharacter.sheet.getPowers());
            adapter.notifyDataSetChanged();
        }
        MainActivity.sProgressBar.setVisibility(View.GONE);
    }
}