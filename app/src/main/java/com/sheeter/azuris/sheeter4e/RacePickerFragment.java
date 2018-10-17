package com.sheeter.azuris.sheeter4e;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class RacePickerFragment extends Fragment {
    private Spinner mRaceSpinner;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> mRaceList;
    private TextView mRaceSummary;
    private LinearLayout mRaceLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.content_builder_race_picker, container, false);
        init();

        mRaceLinearLayout = root.findViewById(R.id.Builder_Race_LinearLayout);
        mRaceSummary = root.findViewById(R.id.Builder_Race_Summary);
        mRaceSpinner = root.findViewById(R.id.Builder_Race_Spinner);
        mAdapter = new ArrayAdapter<>(root.getContext(), R.layout.simple_spinner_item, mRaceList);
        mAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        mRaceSpinner.setAdapter(mAdapter);
        mRaceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                setVisible(true);
                String choice = (String) textView.getText();
                String summary;

                switch (choice) {
                    case "Dragonborn":
                        summary = getString(R.string.summary_race_dragonborn);
                        break;
                    case "Dwarf":
                        summary = getString(R.string.summary_race_dwarf);
                        break;
                    case "Eladrin":
                        summary = getString(R.string.summary_race_eladrin);
                        break;
                    case "Elf":
                        summary = getString(R.string.summary_race_elf);
                        break;
                    case "Half-Elf":
                        summary = getString(R.string.summary_race_halfelf);
                        break;
                    case "Halfling":
                        summary = getString(R.string.summary_race_halfling);
                        break;
                    case "Human":
                        summary = getString(R.string.summary_race_human);
                        break;
                    case "Tiefling":
                        summary = getString(R.string.summary_race_tiefling);
                        break;
                    default:
                        summary = "";
                        setVisible(false);
                }

                mRaceSummary.setText(Html.fromHtml(summary));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return root;
    }

    // Hides elements when empty
    private void setVisible(boolean visible) {
        mRaceLinearLayout.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    private void init() {
        mRaceList = new ArrayList<>();
        String[] items = {
                "Choose A Race",
                "Dragonborn",
                "Dwarf",
                "Eladrin",
                "Elf",
                "Half-Elf",
                "Halfling",
                "Human",
                "Tiefling"
        };
        mRaceList.addAll(Arrays.asList(items));
    }
}
