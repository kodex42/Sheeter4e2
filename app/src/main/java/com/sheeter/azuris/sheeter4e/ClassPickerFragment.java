package com.sheeter.azuris.sheeter4e;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sheeter.azuris.sheeter4e.Modules.D20Class;
import com.sheeter.azuris.sheeter4e.Modules.D20ClassName;

import java.util.ArrayList;
import java.util.Arrays;

public class ClassPickerFragment extends Fragment {
    private BuilderActivity activity;
    private ArrayAdapter<String> mSpinnerAdapter;
    private ArrayList<String> mClassList;
    private Spinner mClassSpinner;
    private LinearLayout mClassLinearLayout;
    private TextView mRole;
    private TextView mPowerSource;
    private TextView mKeyAbilities;
    private TextView mArmorProficiencies;
    private TextView mWeaponProficiencies;
    private LinearLayout mImplementLayout;
    private TextView mImplements;
    private TextView mDefenseBonuses;
    private TextView mBaseHitpoints;
    private TextView mHitpointGains;
    private TextView mDailySurges;
    private TextView mTrainedSkills;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.content_builder_class_picker, container, false);
        activity = (BuilderActivity) root.getContext();

        mClassList = new ArrayList<>();
        mClassList.addAll(Arrays.asList(D20ClassName.getArray()));
        mClassList.add(0, "Choose A Class");

        initViews(root);

        return root;
    }

    private void initViews(final View root) {
        mClassSpinner = root.findViewById(R.id.Builder_Class_Spinner);
        mClassLinearLayout = root.findViewById(R.id.Builder_Class_LinearLayout);

        mRole = root.findViewById(R.id.Builder_Class_Role);
        mPowerSource = root.findViewById(R.id.Builder_Class_Power_Source);
        mKeyAbilities = root.findViewById(R.id.Builder_Class_Abilities);
        mArmorProficiencies = root.findViewById(R.id.Builder_Class_Armor_Proficiencies);
        mWeaponProficiencies = root.findViewById(R.id.Builder_Class_Weapon_Proficiencies);
        mImplementLayout = root.findViewById(R.id.Builder_Class_Implement_Layout);
        mImplements = root.findViewById(R.id.Builder_Class_Implement);
        mDefenseBonuses = root.findViewById(R.id.Builder_Class_Defense_Bonuses);
        mBaseHitpoints = root.findViewById(R.id.Builder_Class_Base_Hitpoints);
        mHitpointGains = root.findViewById(R.id.Builder_Class_Hitpoints_Per_Level);
        mDailySurges = root.findViewById(R.id.Builder_Class_Surges_Per_Day);
        mTrainedSkills = root.findViewById(R.id.Builder_Class_Trained_Skills);

        mSpinnerAdapter = new ArrayAdapter<>(root.getContext(), R.layout.simple_spinner_item, mClassList);
        mSpinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        mClassSpinner.setAdapter(mSpinnerAdapter);
        mClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                setSummaryVisible(true);
                String s = (String) textView.getText();

                if (!s.equals("Choose A Class")) {
                    D20ClassName choice = D20ClassName.fromString(s);
                    D20Class currentClass = BuilderActivity.sAvailableClasses.get(choice.ordinal());

                    mRole.setText(currentClass.getRole());
                    mPowerSource.setText(currentClass.getPowerSource());
                    mKeyAbilities.setText(currentClass.getKeyAbilities());
                    mArmorProficiencies.setText(currentClass.getArmorProficiencies());
                    mWeaponProficiencies.setText(currentClass.getWeaponProficiencies());
                    if (currentClass.hasImplements()) {
                        mImplementLayout.setVisibility(View.VISIBLE);
                        mImplements.setText(currentClass.getImplements());
                    }
                    mDefenseBonuses.setText(currentClass.getDefenseBonuses());
                    mBaseHitpoints.setText(currentClass.getBaseHitpoints());
                    mHitpointGains.setText(currentClass.getHitpointGains());
                    mDailySurges.setText(currentClass.getDailySurges());
                    mTrainedSkills.setText(currentClass.getTrainedSkills());
                }
                else
                    setSummaryVisible(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSummaryVisible(boolean visible) {
        mClassLinearLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
