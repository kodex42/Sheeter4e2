package com.sheeter.azuris.sheeter4e;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sheeter.azuris.sheeter4e.Modules.AbilityScores;
import com.sheeter.azuris.sheeter4e.Modules.Background;
import com.sheeter.azuris.sheeter4e.Modules.D20Character;
import com.sheeter.azuris.sheeter4e.Modules.DamageType;
import com.sheeter.azuris.sheeter4e.Modules.Details;
import com.sheeter.azuris.sheeter4e.Modules.Feat;
import com.sheeter.azuris.sheeter4e.Modules.Item;
import com.sheeter.azuris.sheeter4e.Modules.Power;
import com.sheeter.azuris.sheeter4e.Modules.Sheet;
import com.sheeter.azuris.sheeter4e.Modules.WeaponBonus;
import com.sheeter.azuris.sheeter4e.SQLITE.FeedReaderDbHelper;

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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static D20Character sCharacter = null;
    public static ProgressBar sProgressBar;
    public static FeedReaderDbHelper sDbHelper;
    private BottomNavigationView mNavigationView;
    private FragmentAdapter mFragmentAdapter;
    private ViewPager mViewPager;
    private Item currItem = null;
    private Power currPower = null;
    private WeaponBonus currBonus = null;
    private Background currBackground = null;
    private Feat currFeat = null;
    private Boolean inLootTally = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sProgressBar = (ProgressBar) findViewById(R.id.Main_Progressbar);
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
                            PowersFragment.refreshFragment(mViewPager.getChildAt(position), MainActivity.this);
                        }
                    }
                });

        /** SQLITE BLOCK **/
        sProgressBar.setVisibility(View.VISIBLE);
        AsyncTask task = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                sDbHelper = new FeedReaderDbHelper(MainActivity.this);
                sDbHelper.initDb();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                //ArrayList items = sDbHelper.query("*");
                //items.size();
                sProgressBar.setVisibility(View.GONE);
            }
        };
        task.execute();
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
                resetFields();
                checkFilePerms();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void resetFields() {
        currItem = null;
        currPower = null;
        currBonus = null;
        currBackground = null;
        currFeat = null;
        inLootTally = false;
        sCharacter = null;
    }

    private void checkFilePerms() {
        sProgressBar.setVisibility(View.VISIBLE);

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

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.select_dialog_item);
        for (File file : files) {
            arrayAdapter.add(file.getName().replace(".dnd4e",""));
        }

        new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.ddlogo)
                .setTitle("Select A Character")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        parseXMLFile(files.get(which));
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        MainActivity.sProgressBar.setVisibility(View.GONE);
                    }
                })
                .show();
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
                            sCharacter = new D20Character(xpp.getAttributeValue(null,"legality"));
                            break;
                        case "CharacterSheet":
                            sCharacter.setSheet(new Sheet());
                            break;
                        case "Details":
                            tagState = tagName;
                            sCharacter.sheet.setDetails(new Details());
                            break;
                        case "AbilityScores":
                            sCharacter.sheet.setAbilityScores(new AbilityScores());
                            break;
                        case "Strength":
                            score = Integer.parseInt(xpp.getAttributeValue(null,"score").trim());
                            mods = getMods(score);
                            sCharacter.sheet.abilityScores.setStrength(score);
                            sCharacter.sheet.abilityScores.setStrengthMod(mods[0]);
                            sCharacter.sheet.abilityScores.setStrengthModHalfLevel(mods[1]);
                            break;
                        case "Constitution":
                            score = Integer.parseInt(xpp.getAttributeValue(null,"score").trim());
                            mods = getMods(score);
                            sCharacter.sheet.abilityScores.setConstitution(score);
                            sCharacter.sheet.abilityScores.setConstitutionMod(mods[0]);
                            sCharacter.sheet.abilityScores.setConstitutionModHalfLevel(mods[1]);
                            break;
                        case "Dexterity":
                            score = Integer.parseInt(xpp.getAttributeValue(null,"score").trim());
                            mods = getMods(score);
                            sCharacter.sheet.abilityScores.setDexterity(score);
                            sCharacter.sheet.abilityScores.setDexterityMod(mods[0]);
                            sCharacter.sheet.abilityScores.setDexterityModHalfLevel(mods[1]);
                            break;
                        case "Intelligence":
                            score = Integer.parseInt(xpp.getAttributeValue(null,"score").trim());
                            mods = getMods(score);
                            sCharacter.sheet.abilityScores.setIntelligence(score);
                            sCharacter.sheet.abilityScores.setIntelligence(mods[0]);
                            sCharacter.sheet.abilityScores.setIntelligenceModHalfLevel(mods[1]);
                            break;
                        case "Wisdom":
                            score = Integer.parseInt(xpp.getAttributeValue(null,"score").trim());
                            mods = getMods(score);
                            sCharacter.sheet.abilityScores.setWisdom(score);
                            sCharacter.sheet.abilityScores.setWisdomMod(mods[0]);
                            sCharacter.sheet.abilityScores.setWisdomModHalfLevel(mods[1]);
                            break;
                        case "Charisma":
                            score = Integer.parseInt(xpp.getAttributeValue(null,"score").trim());
                            mods = getMods(score);
                            sCharacter.sheet.abilityScores.setCharisma(score);
                            sCharacter.sheet.abilityScores.setCharismaMod(mods[0]);
                            sCharacter.sheet.abilityScores.setCharismaModHalfLevel(mods[1]);
                            break;
                        case "StatBlock":
                            sCharacter.sheet.stats = new HashMap<String, String>();
                            break;
                        case "Stat":
                            StatParse(xpp, sCharacter);
                            break;
                        case "RulesElement":
                            RuleParse(xpp, sCharacter);
                            break;
                        case "LootTally":
                            this.inLootTally = true;
                            break;
                        case "loot":
                            if(sCharacter.sheet.items == null){
                                sCharacter.sheet.items = new ArrayList<Item>();
                            }

                            this.currItem = new Item();
                            this.currItem.setQuantity(Integer.parseInt(xpp.getAttributeValue(null,"count").trim()));

                            if (currItem.getQuantity() != 0 ) {
                                int equip = Integer.parseInt(xpp.getAttributeValue(null,"equip-count").trim());
                                Boolean equipBool = equip == 0 ? Boolean.FALSE : Boolean.TRUE;
                                this.currItem.setEquipped(equipBool);
                            }
                            break;
                        case "Power":
                            if(sCharacter.sheet.powers == null){
                                sCharacter.sheet.powers = new ArrayList<Power>();
                            }

                            this.currPower = new Power();
                            this.currPower.setName(xpp.getAttributeValue(null,"name").trim());
                            break;
                        case "specific":
                            String name = xpp.getAttributeValue(null,"name").trim();

                            if (this.currPower != null) {
                                if (name.equals("Power Usage")) {
                                    textState = "Power Usage";
                                    tagState = "pDetail";
                                }
                                else if (name.equals("Action Type")){
                                    textState = "Action Type";
                                    tagState = "pDetail";
                                }
                            }
                            else if (this.currBackground != null) {
                                textState = "Background";
                                tagState = "bDetail";
                            }
                            else if (this.currFeat != null){
                                textState = "Feat";
                                tagState = "fDetail";
                            }

                            break;
                        case "Weapon":
                            this.currBonus = new WeaponBonus();
                            this.currBonus.setWeaponName(xpp.getAttributeValue(null,"name").trim());
                            break;
                        case "AttackBonus":
                            textState = "AttackBonus";
                            tagState = "wDetail";
                            break;
                        case "Damage":
                            textState = "Damage";
                            tagState = "wDetail";
                            break;
                        case "AttackStat":
                            textState = "AttackStat";
                            tagState = "wDetail";
                            break;
                        case "Defense":
                            textState = "Defense";
                            tagState = "wDetail";
                            break;
                        case "HitComponents":
                            textState = "HitComponents";
                            tagState = "wDetail";
                            break;
                        case "DamageComponents":
                            textState = "DamageComponents";
                            tagState = "wDetail";
                            break;
                        case "DamageType":
                            textState = "DamageType";
                            tagState = "wDetail";
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
                        case "AttackBonus":
                        case "specific":
                        case "Damage":
                        case "AttackStat":
                        case "Defense":
                        case "HitComponents":
                        case "DamageComponents":
                        case "RulesElement":
                        case "DamageType":
                            tagState = "";
                            break;
                        case "Weapon":
                            this.currPower.addBonus(this.currBonus);
                            this.currBonus = null;
                            break;
                        case "LootTally":
                            this.inLootTally = false;
                            break;
                        case "loot":
                            if (this.inLootTally) {
                                if (this.currItem.getQuantity() != 0)
                                    sCharacter.sheet.items.add(this.currItem);
                            }

                            this.currItem = null;
                            break;
                        case "Power":
                            if (this.currPower.getDamageType() == null)
                                this.currPower.setDamageType(DamageType.PHYSICAL);

                            sCharacter.sheet.powers.add(this.currPower);
                            this.currPower = null;
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
                                sCharacter.sheet.details.setName(text);
                                break;
                            case "Level":
                                sCharacter.sheet.details.setLevel(Integer.parseInt(text));
                                break;
                            case "Player":
                                sCharacter.sheet.details.setPlayer(text);
                                break;
                            case "Height":
                                sCharacter.sheet.details.setHeight(text);
                                break;
                            case "Weight":
                                sCharacter.sheet.details.setWeight(text);
                                break;
                            case "Gender":
                                sCharacter.sheet.details.setGender(text);
                                break;
                            case "Age":
                                sCharacter.sheet.details.setAge(!text.equals("") ? Integer.parseInt(text) : -1);
                                break;
                            case "Alignment":
                                sCharacter.sheet.details.setAlignment(text);
                                break;
                            case "Company":
                                sCharacter.sheet.details.setCompany(text);
                                break;
                            case "Portrait":
                                sCharacter.sheet.details.setPortrait(text);
                                break;
                            case "Experience":
                                if (text.equals(""))
                                    sCharacter.sheet.details.setExperience(0);
                                else
                                    sCharacter.sheet.details.setExperience(Long.parseLong(text));
                                break;
                            case "CarriedMoney":
                                sCharacter.sheet.details.setCarriedMoney(text);
                                break;
                            case "StoredMoney":
                                sCharacter.sheet.details.setStoredMoney(text);
                                break;
                        }
                    }
                    else if (tagState.equals("pDetail")){
                        switch (textState){
                            case "Power Usage":
                                this.currPower.setFrequency(currPower.stringToFrequency(text));
                                break;
                            case "Action Type":
                                this.currPower.setActionType(currPower.stringToActionType(text));
                                break;
                        }
                    }
                    else if (tagState.equals("wDetail")){
                        switch (textState) {
                            case "AttackBonus":
                                this.currBonus.setAttackBonus(Integer.parseInt(text));
                                break;
                            case "Damage":
                                this.currBonus.setDamage(text);
                                break;
                            case "AttackStat":
                                this.currBonus.setAttackStat(text);
                                break;
                            case "Defense":
                                this.currBonus.setDefense(text);
                                break;
                            case "HitComponents":
                                this.currBonus.setHitComponents(text);
                                break;
                            case "DamageComponents":
                                this.currBonus.setDamageComponents(text);
                                break;
                            case "DamageType":
                                this.currPower.setDamageType(this.currPower.stringToDamageType(text));
                                break;
                        }
                    }
                    else if (tagState.equals("bDetail")) {
                        sCharacter.sheet.background.setDescription(text);
                        this.currBackground = null;
                    }
                    else if (tagState.equals("fDetail")) {
                        this.currFeat.setDescription(text);
                        sCharacter.sheet.feats.add(this.currFeat);
                        this.currFeat = null;
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
        return new int[]{k, k + sCharacter.sheet.details.getHalfLevel()};
    }

    private void updateView() {
        View mainPage = mViewPager.getChildAt(0);

        if (sCharacter.sheet.abilityScores != null) {
            // Character Name and Level
            ((TextView) mainPage.findViewById(R.id.Main_TextView_Character)).setText(String.format(Locale.CANADA,"Level %d %s", sCharacter.sheet.details.getLevel(), sCharacter.sheet.details.getName()));

            // Base Ability Scores
            ((TextView) mainPage.findViewById(R.id.Score_Strength)).setText(sCharacter.sheet.abilityScores.getStrength());
            ((TextView) mainPage.findViewById(R.id.Score_Constitution)).setText(sCharacter.sheet.abilityScores.getConstitution());
            ((TextView) mainPage.findViewById(R.id.Score_Dexterity)).setText(sCharacter.sheet.abilityScores.getDexterity());
            ((TextView) mainPage.findViewById(R.id.Score_Intelligence)).setText(sCharacter.sheet.abilityScores.getIntelligence());
            ((TextView) mainPage.findViewById(R.id.Score_Wisdom)).setText(sCharacter.sheet.abilityScores.getWisdom());
            ((TextView) mainPage.findViewById(R.id.Score_Charisma)).setText(sCharacter.sheet.abilityScores.getCharisma());

            // Ability Score Modifiers
            ((TextView) mainPage.findViewById(R.id.Modifier_Strength)).setText(sCharacter.sheet.abilityScores.getStrengthMod());
            ((TextView) mainPage.findViewById(R.id.Modifier_Constitution)).setText(sCharacter.sheet.abilityScores.getConstitutionMod());
            ((TextView) mainPage.findViewById(R.id.Modifier_Dexterity)).setText(sCharacter.sheet.abilityScores.getDexterityMod());
            ((TextView) mainPage.findViewById(R.id.Modifier_Intelligence)).setText(sCharacter.sheet.abilityScores.getIntelligenceMod());
            ((TextView) mainPage.findViewById(R.id.Modifier_Wisdom)).setText(sCharacter.sheet.abilityScores.getWisdomMod());
            ((TextView) mainPage.findViewById(R.id.Modifier_Charisma)).setText(sCharacter.sheet.abilityScores.getCharismaMod());

            // Ability Score Mods Plus Half Level
            ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Strength)).setText(sCharacter.sheet.abilityScores.getStrengthModHalfLevel());
            ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Constitution)).setText(sCharacter.sheet.abilityScores.getConstitutionModHalfLevel());
            ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Dexterity)).setText(sCharacter.sheet.abilityScores.getDexterityModHalfLevel());
            ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Intelligence)).setText(sCharacter.sheet.abilityScores.getIntelligenceModHalfLevel());
            ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Wisdom)).setText(sCharacter.sheet.abilityScores.getWisdomModHalfLevel());
            ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Charisma)).setText(sCharacter.sheet.abilityScores.getCharismaModHalfLevel());

            //      Defenses        //

            // AC
            ((TextView) mainPage.findViewById(R.id.Score_AC)).setText(sCharacter.sheet.stats.get("AC"));

            // Fortitude
            ((TextView) mainPage.findViewById(R.id.Score_FORT)).setText(sCharacter.sheet.stats.get("Fortitude"));

            // Reflex
            ((TextView) mainPage.findViewById(R.id.Score_REF)).setText(sCharacter.sheet.stats.get("Reflex"));

            // Will
            ((TextView) mainPage.findViewById(R.id.Score_WILL)).setText(sCharacter.sheet.stats.get("Will"));


            ArrayList<Item> equipedWeapons = sCharacter.sheet.getEquipedWeapons();
            // Melee Basic Attack
            ArrayList<WeaponBonus> meleeBonuses = getMeleeWeapons(equipedWeapons);

            ((TextView) mainPage.findViewById(R.id.Main_Weapon_Name_Primary_Melee)).setText(meleeBonuses.get(0).getWeaponName());
            ((TextView) mainPage.findViewById(R.id.Main_Weapon_Attack_Primary_Melee)).setText(String.valueOf(meleeBonuses.get(0).getAttackBonus()));
            String[] damageStrings = (meleeBonuses.get(0).getDamage()).split("\\+");
            String finalString = meleeBonuses.get(0).getDamage();
            if (damageStrings.length == 2) {
                finalString = damageStrings[0] + "\n+" + damageStrings[1];
            }
            ((TextView) mainPage.findViewById(R.id.Main_Weapon_Damage_Primary_Melee)).setText(finalString);
            ((TextView) mainPage.findViewById(R.id.Main_Weapon_VS_Primary_Melee)).setText(meleeBonuses.get(0).getDefense());

            if (meleeBonuses.size() > 1) {
                ((TextView) mainPage.findViewById(R.id.Main_Weapon_Name_Secondary_Melee)).setText(meleeBonuses.get(1).getWeaponName());
                ((TextView) mainPage.findViewById(R.id.Main_Weapon_Attack_Secondary_Melee)).setText(String.valueOf(meleeBonuses.get(1).getAttackBonus()));
                damageStrings = (meleeBonuses.get(1).getDamage()).split("\\+");
                finalString = meleeBonuses.get(1).getDamage();
                if (damageStrings.length == 2) {
                    finalString = damageStrings[0] + "\n+" + damageStrings[1];
                }
                ((TextView) mainPage.findViewById(R.id.Main_Weapon_Damage_Secondary_Melee)).setText(finalString);
                ((TextView) mainPage.findViewById(R.id.Main_Weapon_VS_Secondary_Melee)).setText(meleeBonuses.get(1).getDefense());
            }

            // Ranged Basic Attack
            ArrayList<WeaponBonus> rangedBonuses = getRangedWeapons(equipedWeapons);

            ((TextView) mainPage.findViewById(R.id.Main_Weapon_Name_Primary_Ranged)).setText(rangedBonuses.get(0).getWeaponName());
            ((TextView) mainPage.findViewById(R.id.Main_Weapon_Attack_Primary_Ranged)).setText(String.valueOf(rangedBonuses.get(0).getAttackBonus()));
            damageStrings = (rangedBonuses.get(0).getDamage()).split("\\+");
            finalString = meleeBonuses.get(0).getDamage();
            if (damageStrings.length == 2) {
                finalString = damageStrings[0] + "\n+" + damageStrings[1];
            }
            ((TextView) mainPage.findViewById(R.id.Main_Weapon_Damage_Primary_Ranged)).setText(finalString);
            ((TextView) mainPage.findViewById(R.id.Main_Weapon_VS_Primary_Ranged)).setText(rangedBonuses.get(0).getDefense());

            if (rangedBonuses.size() > 1) {
                ((TextView) mainPage.findViewById(R.id.Main_Weapon_Name_Secondary_Ranged)).setText(rangedBonuses.get(1).getWeaponName());
                ((TextView) mainPage.findViewById(R.id.Main_Weapon_Attack_Secondary_Ranged)).setText(String.valueOf(rangedBonuses.get(1).getAttackBonus()));
                damageStrings = (rangedBonuses.get(1).getDamage()).split("\\+");
                finalString = rangedBonuses.get(1).getDamage();
                if (damageStrings.length == 2) {
                    finalString = damageStrings[0] + "\n+" + damageStrings[1];
                }
                ((TextView) mainPage.findViewById(R.id.Main_Weapon_Damage_Secondary_Ranged)).setText(finalString);
                ((TextView) mainPage.findViewById(R.id.Main_Weapon_VS_Secondary_Ranged)).setText(rangedBonuses.get(1).getDefense());
            }

            //TODO: pullPowersFromDatabase();
        }
        else {
            Toast.makeText(this, "Character Parsed as Null :/", Toast.LENGTH_SHORT).show();

            // Character Name and Level
            ((TextView) mainPage.findViewById(R.id.Main_TextView_Character)).setText("Character");

            // Base Ability Scores
            ((TextView) mainPage.findViewById(R.id.Score_Strength)).setText("0");
            ((TextView) mainPage.findViewById(R.id.Score_Constitution)).setText("0");
            ((TextView) mainPage.findViewById(R.id.Score_Dexterity)).setText("0");
            ((TextView) mainPage.findViewById(R.id.Score_Intelligence)).setText("0");
            ((TextView) mainPage.findViewById(R.id.Score_Wisdom)).setText("0");
            ((TextView) mainPage.findViewById(R.id.Score_Charisma)).setText("0");

            // Ability Score Modifiers
            ((TextView) mainPage.findViewById(R.id.Modifier_Strength)).setText("0");
            ((TextView) mainPage.findViewById(R.id.Modifier_Constitution)).setText("0");
            ((TextView) mainPage.findViewById(R.id.Modifier_Dexterity)).setText("0");
            ((TextView) mainPage.findViewById(R.id.Modifier_Intelligence)).setText("0");
            ((TextView) mainPage.findViewById(R.id.Modifier_Wisdom)).setText("0");
            ((TextView) mainPage.findViewById(R.id.Modifier_Charisma)).setText("0");

            // Ability Score Mods Plus Half Level
            ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Strength)).setText("0");
            ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Constitution)).setText("0");
            ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Dexterity)).setText("0");
            ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Intelligence)).setText("0");
            ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Wisdom)).setText("0");
            ((TextView) mainPage.findViewById(R.id.Modifier_PlusHalfLevel_Charisma)).setText("0");

            //      Defenses        //

            // AC
            ((TextView) mainPage.findViewById(R.id.Score_AC)).setText("0");

            // Fortitude
            ((TextView) mainPage.findViewById(R.id.Score_FORT)).setText("0");

            // Reflex
            ((TextView) mainPage.findViewById(R.id.Score_REF)).setText("0");

            // Will
            ((TextView) mainPage.findViewById(R.id.Score_WILL)).setText("0");

            sCharacter = null;
        }

        sProgressBar.setVisibility(View.GONE);
        mViewPager.setCurrentItem(0);
    }

    private void StatParse(XmlPullParser xpp, D20Character character) {
        // TODO: parse each stat
        String statName = xpp.getAttributeValue(null,"name");

        switch (statName){
            case "Strength":
                sCharacter.sheet.abilityScores.setStrength(Integer.parseInt(xpp.getAttributeValue(null,"value").trim()));
                sCharacter.sheet.abilityScores.setStrengthMod(getMods(Integer.parseInt(sCharacter.sheet.abilityScores.getStrength()))[0]);
                sCharacter.sheet.abilityScores.setStrengthModHalfLevel(Integer.parseInt(sCharacter.sheet.abilityScores.getStrengthMod()) + sCharacter.sheet.details.getHalfLevel());
                break;
            case "Constitution":
                sCharacter.sheet.abilityScores.setConstitution(Integer.parseInt(xpp.getAttributeValue(null,"value").trim()));
                sCharacter.sheet.abilityScores.setConstitutionMod(getMods(Integer.parseInt(sCharacter.sheet.abilityScores.getConstitution()))[0]);
                sCharacter.sheet.abilityScores.setConstitutionModHalfLevel(Integer.parseInt(sCharacter.sheet.abilityScores.getConstitutionMod()) + sCharacter.sheet.details.getHalfLevel());
                break;
            case "Dexterity":
                sCharacter.sheet.abilityScores.setDexterity(Integer.parseInt(xpp.getAttributeValue(null,"value").trim()));
                sCharacter.sheet.abilityScores.setDexterityMod(getMods(Integer.parseInt(sCharacter.sheet.abilityScores.getDexterity()))[0]);
                sCharacter.sheet.abilityScores.setDexterityModHalfLevel(Integer.parseInt(sCharacter.sheet.abilityScores.getDexterityMod()) + sCharacter.sheet.details.getHalfLevel());
                break;
            case "Intelligence":
                sCharacter.sheet.abilityScores.setIntelligence(Integer.parseInt(xpp.getAttributeValue(null,"value").trim()));
                sCharacter.sheet.abilityScores.setIntelligenceMod(getMods(Integer.parseInt(sCharacter.sheet.abilityScores.getIntelligence()))[0]);
                sCharacter.sheet.abilityScores.setIntelligenceModHalfLevel(Integer.parseInt(sCharacter.sheet.abilityScores.getIntelligenceMod()) + sCharacter.sheet.details.getHalfLevel());
                break;
            case "Wisdom":
                sCharacter.sheet.abilityScores.setWisdom(Integer.parseInt(xpp.getAttributeValue(null,"value").trim()));
                sCharacter.sheet.abilityScores.setWisdomMod(getMods(Integer.parseInt(sCharacter.sheet.abilityScores.getWisdom()))[0]);
                sCharacter.sheet.abilityScores.setWisdomModHalfLevel(Integer.parseInt(sCharacter.sheet.abilityScores.getWisdomMod()) + sCharacter.sheet.details.getHalfLevel());
                break;
            case "Charisma":
                sCharacter.sheet.abilityScores.setCharisma(Integer.parseInt(xpp.getAttributeValue(null,"value").trim()));
                sCharacter.sheet.abilityScores.setCharismaMod(getMods(Integer.parseInt(sCharacter.sheet.abilityScores.getCharisma()))[0]);
                sCharacter.sheet.abilityScores.setCharismaModHalfLevel(Integer.parseInt(sCharacter.sheet.abilityScores.getCharismaMod()) + sCharacter.sheet.details.getHalfLevel());
                break;
            default:
                // Remove defense suffix
                if (statName.equals("Fortitude Defense"))
                    statName = "Fortitude";
                if (statName.equals("Reflex Defense"))
                    statName = "Reflex";
                if (statName.equals("Will Defense"))
                    statName = "Will";
                sCharacter.sheet.stats.put(statName,xpp.getAttributeValue(null,"value").trim());
        }
    }

    private void RuleParse(XmlPullParser xpp, D20Character character) {
        String tagType = xpp.getAttributeValue(null,"type");

        switch (tagType) {
            case "Alignment":
                sCharacter.sheet.details.setAlignment(xpp.getAttributeValue(null,"name").trim());
                break;
            case "Gender":
                sCharacter.sheet.details.setGender(xpp.getAttributeValue(null,"name"));
                break;
            case "Class":
                sCharacter.sheet.details.setCharClass(xpp.getAttributeValue(null,"name").trim());
                break;
            case "Race":
                sCharacter.sheet.details.setRace(xpp.getAttributeValue(null,"name").trim());
                break;
            case "Language":
                sCharacter.sheet.details.addLanguage(xpp.getAttributeValue(null,"name").trim());
                break;
            case "Size":
                sCharacter.sheet.details.setSize(xpp.getAttributeValue(null,"name").trim());
                break;
            case "Vision":
                sCharacter.sheet.details.setVision(xpp.getAttributeValue(null,"name").trim());
                break;
            case "Role":
                sCharacter.sheet.details.setRole(xpp.getAttributeValue(null,"name").trim());
                break;
            case "Background":
                this.currBackground = new Background();
                sCharacter.sheet.background = new Background();
                sCharacter.sheet.background.setName(xpp.getAttributeValue(null,"name"));
                break;
            // Is item?
            case "Armor":
            case "Weapon":
            case "Gear":
            case "Magic Item":
                if (this.currItem != null ) {
                    this.currItem.setName(xpp.getAttributeValue(null,"name").trim());
                    this.currItem.setType(xpp.getAttributeValue(null,"type").trim());

                    if (tagType.equals("Magic Item"))
                        this.currItem.setMagic(true);
                }
                break;
            case "Feat":
                if (sCharacter.sheet.feats == null)
                    sCharacter.sheet.feats = new ArrayList<>();

                this.currFeat = new Feat();
                this.currFeat.setName(xpp.getAttributeValue(null,"name").trim());
                break;
        }
    }

    private ArrayList<WeaponBonus> getMeleeWeapons(ArrayList<Item> equipped){
        ArrayList<WeaponBonus> newBonuses = new ArrayList<>();
        ArrayList<WeaponBonus> bonuses = this.sCharacter.sheet.getMeleeBasicAttack().getWeaponBonuses();
        int equipIndex = 0;

        if (equipped.size() > 0) {
            for (WeaponBonus b : bonuses) {
                if (b.getWeaponName().equals(equipped.get(0).getName())) {
                    newBonuses.add(b);
                }
            }
        }

        newBonuses.add(sCharacter.sheet.getUnarmedMeleeBasicAttack());
        return newBonuses;
    }

    private ArrayList<WeaponBonus> getRangedWeapons(ArrayList<Item> equipped){
        ArrayList<WeaponBonus> newBonuses = new ArrayList<>();
        ArrayList<WeaponBonus> bonuses = this.sCharacter.sheet.getRangedBasicAttack().getWeaponBonuses();
        int equipIndex = 0;

        if (equipped.size() > 0) {
            for (WeaponBonus b : bonuses) {
                if (b.getWeaponName().equals(equipped.get(0).getName())) {
                    newBonuses.add(b);
                }
            }
        }

        newBonuses.add(sCharacter.sheet.getUnarmedRangedBasicAttack());
        return newBonuses;
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
            fragments[1] = new PowersFragment();
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
