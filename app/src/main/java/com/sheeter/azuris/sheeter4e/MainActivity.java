package com.sheeter.azuris.sheeter4e;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.sheeter.azuris.sheeter4e.Modules.AbilityScores;
import com.sheeter.azuris.sheeter4e.Modules.D20Character;
import com.sheeter.azuris.sheeter4e.Modules.Details;
import com.sheeter.azuris.sheeter4e.Modules.Sheet;
import com.sheeter.azuris.sheeter4e.Modules.Stat;

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

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.Main_ProgressBar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.Main_Fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED
                            || checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 0);
                    } else {
                        parseXMLFile();
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            parseXMLFile();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void parseXMLFile() {
        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents/Grigle Groogle.dnd4e");
            BOMInputStream fin = new BOMInputStream(new FileInputStream(file));
            String contents = convertStreamToString(fin);
            fin.close();
            
            D20Character character = null;
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
                    // Switch on tag name
                    switch (tagName) {
                        case "D20Character":
                            character = new D20Character(xpp.getAttributeValue(2));
                            break;
                        case "CharacterSheet":
                            character.setSheet(new Sheet());
                            break;
                        case "Details":
                            tagState = tagName;
                            character.sheet.setDetails(new Details());
                            break;
                        case "AbilityScores":
                            character.sheet.setAbilityScores(new AbilityScores());
                            break;
                        case "Strength":
                            character.sheet.abilityScores.setStrength(Integer.parseInt(xpp.getAttributeValue(0).trim()));
                            break;
                        case "Constitution":
                            character.sheet.abilityScores.setConstitution(Integer.parseInt(xpp.getAttributeValue(0).trim()));
                            break;
                        case "Dexterity":
                            character.sheet.abilityScores.setDexterity(Integer.parseInt(xpp.getAttributeValue(0).trim()));
                            break;
                        case "Intelligence":
                            character.sheet.abilityScores.setIntelligence(Integer.parseInt(xpp.getAttributeValue(0).trim()));
                            break;
                        case "Wisdom":
                            character.sheet.abilityScores.setWisdom(Integer.parseInt(xpp.getAttributeValue(0).trim()));
                            break;
                        case "Charisma":
                            character.sheet.abilityScores.setCharisma(Integer.parseInt(xpp.getAttributeValue(0).trim()));
                            break;
                        case "StatBlock":
                            character.sheet.stats = new ArrayList<>();
                            break;
                        case "Stat":
                            StatParse(xpp, character);
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
                                character.sheet.details.setName(text);
                                break;
                            case "Level":
                                character.sheet.details.setLevel(Integer.parseInt(text));
                                break;
                            case "Player":
                                character.sheet.details.setPlayer(text);
                                break;
                            case "Height":
                                character.sheet.details.setHeight(text);
                                break;
                            case "Weight":
                                character.sheet.details.setWeight(text);
                                break;
                            case "Gender":
                                character.sheet.details.setGender(text);
                                break;
                            case "Age":
                                character.sheet.details.setAge(Integer.parseInt(text));
                                break;
                            case "Alignment":
                                character.sheet.details.setAlignment(text);
                                break;
                            case "Company":
                                character.sheet.details.setCompany(text);
                                break;
                            case "Portrait":
                                character.sheet.details.setPortrait(text);
                                break;
                            case "Experience":
                                character.sheet.details.setExperience(Long.parseLong(text));
                                break;
                            case "CarriedMoney":
                                character.sheet.details.setCarriedMoney(text);
                                break;
                            case "StoredMoney":
                                character.sheet.details.setStoredMoney(text);
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

        progressBar.setVisibility(View.GONE);
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
}
