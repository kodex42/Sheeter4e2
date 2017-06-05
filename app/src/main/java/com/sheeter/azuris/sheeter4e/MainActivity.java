package com.sheeter.azuris.sheeter4e;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sheeter.azuris.sheeter4e.Modules.AbilityScores;
import com.sheeter.azuris.sheeter4e.Modules.D20Character;
import com.sheeter.azuris.sheeter4e.Modules.Details;
import com.sheeter.azuris.sheeter4e.Modules.Sheet;

import org.apache.commons.io.input.BOMInputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ProgressBar mProgressBar;
    private BottomNavigationView mNavigationView;
    private FragmentAdapter mFragmentAdapter;
    private ViewPager mViewPager;
    private D20Character mCharacter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgressBar = (ProgressBar) findViewById(R.id.Main_Progressbar);
        mViewPager = (ViewPager) findViewById(R.id.Main_Pager);
        mNavigationView = (BottomNavigationView) findViewById(R.id.Main_Navigation);
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_home)
                    mViewPager.setCurrentItem(0);
                else if (item.getItemId() == R.id.action_powers)
                    mViewPager.setCurrentItem(1);
                return true;
            }
        });

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mFragmentAdapter =
                new FragmentAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.Main_Pager);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        if (position == 0) {
                            mNavigationView.setSelectedItemId(R.id.action_home);
                        }
                        else if (position == 1) {
                            mNavigationView.setSelectedItemId(R.id.action_powers);
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            checkFiles();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                checkFilePerms();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkFilePerms() {
        mProgressBar.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 0);
            } else {
                checkFiles();
            }
        }
    }

    private void checkFiles() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents";
        File directory = new File(path);
        final List<File> files = Arrays.asList(directory.listFiles());

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.drawable.ddlogo);
        builderSingle.setTitle("Select A Character");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item);
        for (File file : files) {
            arrayAdapter.add(file.getName().replace(".dnd4e",""));
        }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                parseXMLFile(files.get(which));
            }
        });
        builderSingle.show();

        //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents/Grigle Groogle.dnd4e");
        //parseXMLFile(file);
    }

    private void parseXMLFile(File file) {
        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            BOMInputStream fin = new BOMInputStream(new FileInputStream(file));
            String contents = convertStreamToString(fin);
            fin.close();

            String textState = "";
            String tagState = "";

            xpp.setInput(new StringReader(contents));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_DOCUMENT) {
                    System.out.println("Start document");
                } else if(eventType == XmlPullParser.START_TAG) {
                    System.out.println("Start tag "+xpp.getName());

                    String tagName = xpp.getName();
                    int score;
                    int[] mods;
                    // Switch on tag name
                    switch (tagName) {
                        case "D20Character":
                            mCharacter = new D20Character(xpp.getAttributeValue(2));
                            break;
                        case "CharacterSheet":
                            mCharacter.setSheet(new Sheet());
                            break;
                        case "Details":
                            tagState = tagName;
                            mCharacter.sheet.setDetails(new Details());
                            break;
                        case "AbilityScores":
                            mCharacter.sheet.setAbilityScores(new AbilityScores());
                            break;
                        case "Strength":
                            score = Integer.parseInt(xpp.getAttributeValue(0).trim());
                            mods = getMods(score);
                            mCharacter.sheet.abilityScores.setStrength(score);
                            mCharacter.sheet.abilityScores.setStrengthMod(mods[0]);
                            mCharacter.sheet.abilityScores.setStrengthModHalfLevel(mods[1]);
                            break;
                        case "Constitution":
                            score = Integer.parseInt(xpp.getAttributeValue(0).trim());
                            mods = getMods(score);
                            mCharacter.sheet.abilityScores.setConstitution(score);
                            mCharacter.sheet.abilityScores.setConstitutionMod(mods[0]);
                            mCharacter.sheet.abilityScores.setConstitutionModHalfLevel(mods[1]);
                            break;
                        case "Dexterity":
                            score = Integer.parseInt(xpp.getAttributeValue(0).trim());
                            mods = getMods(score);
                            mCharacter.sheet.abilityScores.setDexterity(score);
                            mCharacter.sheet.abilityScores.setDexterityMod(mods[0]);
                            mCharacter.sheet.abilityScores.setDexterityModHalfLevel(mods[1]);
                            break;
                        case "Intelligence":
                            score = Integer.parseInt(xpp.getAttributeValue(0).trim());
                            mods = getMods(score);
                            mCharacter.sheet.abilityScores.setIntelligence(score);
                            mCharacter.sheet.abilityScores.setIntelligence(mods[0]);
                            mCharacter.sheet.abilityScores.setIntelligenceModHalfLevel(mods[1]);
                            break;
                        case "Wisdom":
                            score = Integer.parseInt(xpp.getAttributeValue(0).trim());
                            mods = getMods(score);
                            mCharacter.sheet.abilityScores.setWisdom(score);
                            mCharacter.sheet.abilityScores.setWisdomMod(mods[0]);
                            mCharacter.sheet.abilityScores.setWisdomModHalfLevel(mods[1]);
                            break;
                        case "Charisma":
                            score = Integer.parseInt(xpp.getAttributeValue(0).trim());
                            mods = getMods(score);
                            mCharacter.sheet.abilityScores.setCharisma(score);
                            mCharacter.sheet.abilityScores.setCharismaMod(mods[0]);
                            mCharacter.sheet.abilityScores.setCharismaModHalfLevel(mods[1]);
                            break;
                        case "StatBlock":
                            mCharacter.sheet.stats = new ArrayList<>();
                            break;
                        case "Stat":
                            StatParse(xpp, mCharacter);
                            break;
                        default:
                            textState = tagName;
                            break;
                    }
                } else if(eventType == XmlPullParser.END_TAG) {
                    System.out.println("End tag "+xpp.getName());

                    String tagName = xpp.getName();
                    // Switch on tag name
                    switch (tagName) {
                        case "Details":
                        case "AbilityScores":
                            tagState = "";
                            break;
                    }

                    textState = "";
                } else if(eventType == XmlPullParser.TEXT) {
                    System.out.println("Text "+xpp.getText());

                    String text = xpp.getText().trim();

                    if (tagState.equals("Details")) {
                        // Switch on tag text for details
                        switch (textState) {
                            case "name":
                                mCharacter.sheet.details.setName(text);
                                break;
                            case "Level":
                                mCharacter.sheet.details.setLevel(Integer.parseInt(text));
                                break;
                            case "Player":
                                mCharacter.sheet.details.setPlayer(text);
                                break;
                            case "Height":
                                mCharacter.sheet.details.setHeight(text);
                                break;
                            case "Weight":
                                mCharacter.sheet.details.setWeight(text);
                                break;
                            case "Gender":
                                mCharacter.sheet.details.setGender(text);
                                break;
                            case "Age":
                                mCharacter.sheet.details.setAge(!text.equals("") ? Integer.parseInt(text) : -1);
                                break;
                            case "Alignment":
                                mCharacter.sheet.details.setAlignment(text);
                                break;
                            case "Company":
                                mCharacter.sheet.details.setCompany(text);
                                break;
                            case "Portrait":
                                mCharacter.sheet.details.setPortrait(text);
                                break;
                            case "Experience":
                                mCharacter.sheet.details.setExperience(Long.parseLong(text));
                                break;
                            case "CarriedMoney":
                                mCharacter.sheet.details.setCarriedMoney(text);
                                break;
                            case "StoredMoney":
                                mCharacter.sheet.details.setStoredMoney(text);
                                break;
                        }
                    }
                }
                eventType = xpp.next();
            }
            System.out.println("End document");
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateView();
    }

    private int[] getMods(int i) {
        int k = (i - 10)/2;
        return new int[]{k, k + mCharacter.sheet.details.getHalfLevel()};
    }

    private void updateView() {
        View mainPage = mViewPager.getChildAt(0);

        // Character Name and Level
        ((TextView) mainPage.findViewById(R.id.Main_TextView_Character)).setText(String.format(Locale.CANADA,"Level %d %s", mCharacter.sheet.details.getLevel(), mCharacter.sheet.details.getName()));

        // Base Ability Scores
        ((TextView) mainPage.findViewById(R.id.Score_Strength)).setText(mCharacter.sheet.abilityScores.getStrength());
        ((TextView) mainPage.findViewById(R.id.Score_Constitution)).setText(mCharacter.sheet.abilityScores.getConstitution());
        ((TextView) mainPage.findViewById(R.id.Score_Dexterity)).setText(mCharacter.sheet.abilityScores.getDexterity());
        ((TextView) mainPage.findViewById(R.id.Score_Intelligence)).setText(mCharacter.sheet.abilityScores.getIntelligence());
        ((TextView) mainPage.findViewById(R.id.Score_Wisdom)).setText(mCharacter.sheet.abilityScores.getWisdom());
        ((TextView) mainPage.findViewById(R.id.Score_Charisma)).setText(mCharacter.sheet.abilityScores.getCharisma());

        // Ability Score Modifiers
        ((TextView) mainPage.findViewById(R.id.Modifier_Strength)).setText(mCharacter.sheet.abilityScores.getStrengthMod());
        ((TextView) mainPage.findViewById(R.id.Modifier_Constitution)).setText(mCharacter.sheet.abilityScores.getConstitutionMod());
        ((TextView) mainPage.findViewById(R.id.Modifier_Dexterity)).setText(mCharacter.sheet.abilityScores.getDexterityMod());
        ((TextView) mainPage.findViewById(R.id.Modifier_Intelligence)).setText(mCharacter.sheet.abilityScores.getIntelligenceMod());
        ((TextView) mainPage.findViewById(R.id.Modifier_Wisdom)).setText(mCharacter.sheet.abilityScores.getWisdomMod());
        ((TextView) mainPage.findViewById(R.id.Modifier_Charisma)).setText(mCharacter.sheet.abilityScores.getCharismaMod());

        // Ability Score Mods Plus Half Level
        ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Strength)).setText(mCharacter.sheet.abilityScores.getStrengthModHalfLevel());
        ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Constitution)).setText(mCharacter.sheet.abilityScores.getConstitutionModHalfLevel());
        ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Dexterity)).setText(mCharacter.sheet.abilityScores.getDexterityModHalfLevel());
        ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Intelligence)).setText(mCharacter.sheet.abilityScores.getIntelligenceModHalfLevel());
        ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Wisdom)).setText(mCharacter.sheet.abilityScores.getWisdomModHalfLevel());
        ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Charisma)).setText(mCharacter.sheet.abilityScores.getCharismaModHalfLevel());

        mProgressBar.setVisibility(View.GONE);
    }

    private void StatParse(XmlPullParser xpp, D20Character character) {
        // TODO: parse each stat
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    // Since this is an object collection, use a FragmentStatePagerAdapter,
    // and NOT a FragmentPagerAdapter.
    private class FragmentAdapter extends FragmentStatePagerAdapter {
        Fragment[] fragments;

        FragmentAdapter(FragmentManager fm) {
            super(fm);
            fragments = new Fragment[2];
            fragments[0] = new MainFragment();
            fragments[1] = new AddSheetsFragment();
        }

        @Override
        public Fragment getItem(int i) {
            return fragments[i];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
