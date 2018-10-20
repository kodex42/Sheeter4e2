package com.sheeter.azuris.sheeter4e;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sheeter.azuris.sheeter4e.Modules.AbilityType;
import com.sheeter.azuris.sheeter4e.Modules.ArmorType;
import com.sheeter.azuris.sheeter4e.Modules.D20Class;
import com.sheeter.azuris.sheeter4e.Modules.D20ClassEnumerator;
import com.sheeter.azuris.sheeter4e.Modules.D20Race;
import com.sheeter.azuris.sheeter4e.Modules.DefenseType;
import com.sheeter.azuris.sheeter4e.Modules.Feat;
import com.sheeter.azuris.sheeter4e.Modules.ImplementType;
import com.sheeter.azuris.sheeter4e.Modules.PowerSource;
import com.sheeter.azuris.sheeter4e.Modules.RaceName;
import com.sheeter.azuris.sheeter4e.Modules.Role;
import com.sheeter.azuris.sheeter4e.Modules.ShieldType;
import com.sheeter.azuris.sheeter4e.Modules.SizeType;
import com.sheeter.azuris.sheeter4e.Modules.SkillType;
import com.sheeter.azuris.sheeter4e.Modules.Trait;
import com.sheeter.azuris.sheeter4e.Modules.VisionType;
import com.sheeter.azuris.sheeter4e.Modules.WeaponComplexity;
import com.sheeter.azuris.sheeter4e.Modules.WeaponGroupType;
import com.sheeter.azuris.sheeter4e.Modules.WeaponPropertyWithBonus;
import com.sheeter.azuris.sheeter4e.Modules.WeaponType;

import java.util.ArrayList;
import java.util.Arrays;

import static com.sheeter.azuris.sheeter4e.Modules.SizeType.getRaw;
import static com.sheeter.azuris.sheeter4e.Modules.VisionType.getRaw;

public class RacePickerFragment extends Fragment {
    private Spinner mRaceSpinner;
    private ArrayAdapter<String> mSpinnerAdapter;
    private ArrayAdapter<Trait> mTraitsAdapter;
    private ArrayList<String> mRaceList;
    private TextView mRaceSummary;
    private LinearLayout mRaceLinearLayout;
    private RelativeLayout mLoadingScreen;
    private ListView mTraitListView;
    private TextView mAvgHeightTv;
    private TextView mAvgWeightTv;
    private TextView mAbilityScoresTv;
    private TextView mSizeTv;
    private TextView mSpeedTv;
    private TextView mVisionTv;
    private TextView mLanguagesTv;
    private TextView mSkillBonusesTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.content_builder_race_picker, container, false);

        // Load all the arbitrary data in the background
        mRaceLinearLayout = root.findViewById(R.id.Builder_Race_LinearLayout);
        mLoadingScreen = root.findViewById(R.id.Builder_Loading_Page);
        new AsyncTask() {
            @Override
            protected void onPreExecute() {
                // Loading...
                setSummaryVisible(false);
                setLoadingScreenVisible(true);
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                init();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                // Initialize the Spinner
                initViews(root);
                setLoadingScreenVisible(false);
                super.onPostExecute(o);
            }
        }.execute();

        return root;
    }

    // Hides summary elements when empty
    private void setSummaryVisible(boolean visible) {
        mRaceLinearLayout.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    private void setLoadingScreenVisible(boolean visible) {
        mLoadingScreen.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void init() {
        mRaceList = new ArrayList<>();
        mRaceList.addAll(Arrays.asList(RaceName.getArray()));
        mRaceList.add(0, "Choose A Race");

        // Initialize Available Classes
        initClasses();

        // Initialize Available Races
        initRaces();
    }

    private void initViews(final View root) {
        mRaceSummary = root.findViewById(R.id.Builder_Race_Summary);
        mRaceSpinner = root.findViewById(R.id.Builder_Race_Spinner);
        mTraitListView = root.findViewById(R.id.Builder_Race_Triat_ListView);

        mAvgHeightTv = root.findViewById(R.id.Builder_Race_Average_Height);
        mAvgWeightTv = root.findViewById(R.id.Builder_Race_Average_Weight);
        mAbilityScoresTv = root.findViewById(R.id.Builder_Race_Ability_Scores);
        mSizeTv = root.findViewById(R.id.Builder_Race_Size);
        mSpeedTv = root.findViewById(R.id.Builder_Race_Speed);
        mVisionTv = root.findViewById(R.id.Builder_Race_Vision);
        mLanguagesTv = root.findViewById(R.id.Builder_Race_Languages);
        mSkillBonusesTv = root.findViewById(R.id.Builder_Race_Skill_Bonuses);

        mSpinnerAdapter = new ArrayAdapter<>(root.getContext(), R.layout.simple_spinner_item, mRaceList);
        mSpinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        mRaceSpinner.setAdapter(mSpinnerAdapter);
        mRaceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                setSummaryVisible(true);
                String s = (String) textView.getText();

                if (!s.equals("Choose A Race")) {
                    RaceName choice = RaceName.fromString(s);
                    D20Race currentRace = BuilderActivity.sAvailableRaces.get(choice.ordinal());

                    mAvgHeightTv.setText(currentRace.getAverageHeight());
                    mAvgWeightTv.setText(currentRace.getAverageWeight());
                    mAbilityScoresTv.setText(currentRace.getAbilityScoreBonusesString());
                    mSizeTv.setText(getRaw(currentRace.getSizeType()));
                    mSpeedTv.setText(String.valueOf(currentRace.getSpeed()));
                    mVisionTv.setText(getRaw(currentRace.getVisionType()));
                    mLanguagesTv.setText(currentRace.getLanguagesString());
                    mSkillBonusesTv.setText(currentRace.getSkillBonusesString());
                    mRaceSummary.setText(Html.fromHtml(currentRace.getDescription()));

                    mTraitsAdapter = new TraitListViewAdapter(root.getContext(), R.layout.trait_row, BuilderActivity.sAvailableRaces.get(choice.ordinal()).getRacialTraits());
                    mTraitListView.setAdapter(mTraitsAdapter);
                }
                else
                    setSummaryVisible(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initClasses() {
        BuilderActivity.sAvailableClasses = new ArrayList<>();
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Ardent",
                        new PowerSource[]{
                                PowerSource.PSIONIC
                        },
                        new Role[]{
                                Role.LEADER
                        },
                        new AbilityType[]{
                                AbilityType.CHARISMA,
                                AbilityType.CONSTITUTION,
                                AbilityType.WISDOM
                        },
                        new DefenseType[]{
                                DefenseType.FORTITUDE,
                                DefenseType.WILL
                        },
                        12,
                        5,
                        7,
                        ArmorType.CHAINMAIL,
                        null,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.MILITARY_MELEE),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED)
                        },
                        new ImplementType[]{},
                        new SkillType[]{},
                        4,
                        new SkillType[]{
                                SkillType.ARCANA,
                                SkillType.ATHLETICS,
                                SkillType.BLUFF,
                                SkillType.DIPLOMACY,
                                SkillType.ENDURANCE,
                                SkillType.HEAL,
                                SkillType.INSIGHT,
                                SkillType.INTIMIDATE,
                                SkillType.STREETWISE
                        },
                        new Feat[]{
                                new Feat(
                                        "Psionic Augmentation",
                                        "Through discipline and careful study, you have mastered a form of psionic magic that offers greater versatility than other characters command. " +
                                                "You know a broad array of at-will powers, each of which is a conduit through which you can pour as much or as little psionic energy as you choose. " +
                                                "You channel psionic energy into a reservoir of personal power (represented in the game as power points) that you can use to augment your at-will attack powers, replacing the encounter attack powers that other characters use." +
                                                "Because of this class feature, you acquire and use powers in a slightly different manner from how most other classes do." +
                                                "\n\tAt-Will Attack Powers: At 1st level, you choose two at-will attack powers and one daily attack power from your class, but you don't start with any encounter attack powers from your class. " +
                                                "You can instead augment your class at·will attack powers using power points. " +
                                                "These powers have the augmentable keyword." +
                                                "You gain new at-will attack powers from this class, instead of new encounter attack powers, as you increase in level. " +
                                                "At 3rd level, you choose a new at-will attack power from this class. " +
                                                "At 7th, 13th, 17th, 23rd, and 27th level, you can replace one ofy our at-will attack powers with another one of your level or lower. " +
                                                "Both powers must be augmentable and from this class." +
                                                "\n\tPower Points: At 1st, 3rd, 17th, 21st, 23rd, and 27th level, you gain +2 maximum power points. At 13th level you gain +1 maximum power points."
                                ),
                                new Feat(
                                        "Ardent Mantle",
                                        "Choose one of the following options. " +
                                                "Your choice gives a power to you as well as a benefit to you and your allies:" +
                                                "\n\tMantle of Clarity: You and each ally within 5 squares of you gain a bonus to all defenses against opportunity attacks. " +
                                                "The bonus equals your Wisdom modifier." +
                                                "In addition, each ally within 5 squares of you gains a +2 bonus to Insight checks and Perception checks." +
                                                "You also gain the Ardent Alacrity power." +
                                                "\n\tMantle ofElation: You and each ally within 5 squares of you gain a bonus to damage rolls for opportunity attacks. " +
                                                "The bonus equals your Constitution modifier. " +
                                                "In addition, each ally within 5 squares of you gains a +2 bonus to Diplomacy checks and Intimidate checks." +
                                                "You also gain the Ardent Outrage power."
                                ),
                                new Feat(
                                        "Ardent Surge",
                                        "You gain the Ardent Surge power. Your advanced emotional state is inspirational, motivating your allies and helping them to recover from injuries."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Artificer",
                        new PowerSource[]{
                                PowerSource.ARCANE
                        },
                        new Role[]{
                                Role.LEADER
                        },
                        new AbilityType[]{
                                AbilityType.INTELLIGENCE,
                                AbilityType.CONSTITUTION,
                                AbilityType.WISDOM
                        },
                        new DefenseType[]{
                                DefenseType.FORTITUDE,
                                DefenseType.WILL
                        },
                        12,
                        5,
                        6,
                        ArmorType.LEATHER,
                        null,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED)
                        },
                        new ImplementType[]{
                                ImplementType.ROD,
                                ImplementType.STAFF,
                                ImplementType.WAND
                        },
                        new SkillType[]{
                                SkillType.ARCANA
                        },
                        4,
                        new SkillType[]{
                                SkillType.DIPLOMACY,
                                SkillType.DUNGEONEERING,
                                SkillType.HEAL,
                                SkillType.HISTORY,
                                SkillType.PERCEPTION,
                                SkillType.THIEVERY
                        },
                        new Feat[]{
                                new Feat(
                                        "Arcane Empowerment",
                                        "The artificer's study of magic allows manipulating arcane energy found within items. " +
                                                "An artificer begins each day with the ability to empower one magic item, and gains an additional empowerment at each milestone. " +
                                                "You must spend a short rest with an item in order to empower it. An item may be empowered in two ways:" +
                                                "\n\tImpart Energy: You recharge the daily power of a magic item. An item can only be recharged only once per day in this way." +
                                                "\n\tAugment Energy: You infuse a weapon or implement with a reservoir of energy that lasts until the end of your next extended rest or until it is expended. " +
                                                "The wielder of the implement or the weapon can use a free action after making an attack roll to expend this to gain a +2 bonus to that attack roll. " +
                                                "An implement or weapon can be augmented only once per day in this way."
                                ),
                                new Feat(
                                        "Arcane Rejuvenation",
                                        "Before combat, an artificer spends time infusing his or her allies' magic items with curative energy. " +
                                                "\nWhenever an ally of an artificer uses a magic item's daily power, in addition to the power's normal effects, the ally gains temporary hit points equal to one-half the artificer's level plus the artificer's Intelligence modifier."
                                ),
                                new Feat(
                                        "Healing Infusion",
                                        "At the end of an extended rest, the artificer creates two healing infusions that last until the end of the next extended rest. " +
                                                "At 16th level, the artificer can create a third infusion." +
                                                "\nThe effect of a healing infusion is determined at the time of use, not at the time of creation. " +
                                                "When the artificer uses a healing infusion power, he expends one of the infusions created during the last extended rest. " +
                                                "During a short rest, you or an ally can expend a healing surge to replenish one of the infusions expended."
                                ),
                                new Feat(
                                        "Ritual Casting",
                                        "The artificer has Ritual Caster as a bonus feat, and owns a ritual book with the following rituals: " +
                                                "Brew Potion, Disenchant Magic Item, Enchant Magic Item, and Make Whole. " +
                                                "In addition, the artificer can use Disenchange magic item without expending components."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Avenger",
                        new PowerSource[]{
                                PowerSource.DIVINE
                        },
                        new Role[]{
                                Role.STRIKER
                        },
                        new AbilityType[]{
                                AbilityType.WISDOM,
                                AbilityType.DEXTERITY,
                                AbilityType.INTELLIGENCE
                        },
                        new DefenseType[]{
                                DefenseType.FORTITUDE,
                                DefenseType.REFLEX,
                                DefenseType.WILL
                        },
                        14,
                        6,
                        7,
                        ArmorType.CLOTH,
                        null,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.MILITARY_MELEE),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED)
                        },
                        new ImplementType[]{
                                ImplementType.HOLY_SYMBOL
                        },
                        new SkillType[]{
                                SkillType.RELIGION
                        },
                        3,
                        new SkillType[]{
                                SkillType.ACROBATICS,
                                SkillType.ATHLETICS,
                                SkillType.ENDURANCE,
                                SkillType.HEAL,
                                SkillType.INTIMIDATE,
                                SkillType.PERCEPTION,
                                SkillType.STEALTH,
                                SkillType.STREETWISE
                        },
                        new Feat[]{
                                new Feat(
                                        "Armor of Faith",
                                        "The favor of your deity wards you from harm. While you are neither wearing heavy armor or shield, you gain a +3 bonus to AC. "
                                ),
                                new Feat(
                                        "Avenger's Censure",
                                        "As an avenger, you train your mind, body, and soul toward one purpose: destroying the enemies of your faith. " +
                                                "To that end, you gain divine aid in pursuing a single target, though the way you eliminate that enemy varies. " +
                                                "Do you pin your foe down and keep other enemies away, or do you pursue your foe across the field of battle?" +
                                                "\nChoose one of these options. Your choice provides bonuses to certain avenger powers, as detailed in those powers:" +
                                                "\n\tCensure of Pursuit: If your oath of enmity target moves away from you willingly, you gain a bonus to damage rolls against the target equal to 2 + your Dexterity modifier until the end of your next turn. " +
                                                "The bonus increases to 4 + your Dexterity modifier at 11th level and 6 + your Dexterity modifier at 21st level." +
                                                "\n\tCensure of Retribution: When any enemy other than your oath of enmity target hits you, you gain a bonus to damage rolls against your oath of enmity target equal to your Intelligence modifier until the end of your next turn. " +
                                                "This bonus is cumulative."
                                ),
                                new Feat(
                                        "Channel Divinity",
                                        "Once per encounter you can use a Channel Divinity power. " +
                                                "You start with two Channel Divinity powers: Abjure Undead and Divine Guidance. " +
                                                "You can gain additional Channel Divinity powers by taking divinity feats." +
                                                "With DM permission, you may use Divine Identification instead of Divine Guidance."
                                ),
                                new Feat(
                                        "Oath of Enmity",
                                        "Your god gives you the power to strike down your chosen prey. " +
                                                "You gain the oath of enmity power."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Barbarian",
                        new PowerSource[]{
                                PowerSource.PRIMAL
                        },
                        new Role[]{
                                Role.STRIKER
                        },
                        new AbilityType[]{
                                AbilityType.STRENGTH,
                                AbilityType.CONSTITUTION,
                                AbilityType.CHARISMA
                        },
                        new DefenseType[]{
                                DefenseType.FORTITUDE,
                                DefenseType.FORTITUDE
                        },
                        15,
                        6,
                        8,
                        ArmorType.HIDE,
                        null,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.MILITARY_MELEE)
                        },
                        new ImplementType[]{},
                        new SkillType[]{},
                        3,
                        new SkillType[]{
                                SkillType.ACROBATICS,
                                SkillType.ATHLETICS,
                                SkillType.ENDURANCE,
                                SkillType.HEAL,
                                SkillType.INTIMIDATE,
                                SkillType.NATURE,
                                SkillType.PERCEPTION
                        },
                        new Feat[]{
                                new Feat(
                                        "Barbarian Agility",
                                        "While you are not wearing heavy armor, you gain a +1 bonus to AC and Reflex. " +
                                                "The bonus increases to +2 at 11th level and +3 at 21st level."
                                ),
                                new Feat(
                                        "Feral Might",
                                        "Barbarians connect with the natural world in a variety of ways. " +
                                                "Some barbarians grow so hardened to physical punishment that they find it easier to simply absorb, rather than avoid, attacks. " +
                                                "Others are living examples of the power of one’s will to shape one’s fate." +
                                                "\n" +
                                                "Choose one of the following options. " +
                                                "The choice you make gives you the benefit described below and also provides bonuses to certain barbarian powers, as detailed in those powers:" +
                                                "\n\tRageblood Vigor: You gain the swift charge power. " +
                                                "In addition, whenever your attack reduces an enemy to 0 hit points, you gain temporary hit points equal to your Constitution modifier. " +
                                                "The number of temporary hit points equals 5 + your Constitution modifier at 11th level and 10 + your Constitution modifier at 21st level." +
                                                "\n\tThaneborn Triumph: You gain the roar of triumph power. " +
                                                "In addition, whenever you bloody an enemy, the next attack by you or an ally against that enemy gains a bonus to the attack roll equal to your Charisma modifier."
                                ),
                                new Feat(
                                        "Rage Strike",
                                        "Barbarian daily attack powers have the rage keyword. " +
                                                "They allow you to unleash powerful bursts of emotion, willpower, and primal energy. " +
                                                "Each rage power starts with a mighty attack, and then you enter a rage, which grants an ongoing benefit. " +
                                                "At 5th level, you gain the Rage Strike power, which lets you channel an unused rage power into a devastating attack while you’re raging. " +
                                                "Using rage strike is an alternative to using a second rage power in a climactic battle; it gives you the damage output of a daily power without forcing you to enter a different rage."
                                ),
                                new Feat(
                                        "Rampage",
                                        "Once per round, when you score a critical hit with a barbarian attack power, you can immediately make a melee basic attack as a free action. " +
                                                "You do not have to attack the same target that you scored a critical hit against."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Bard",
                        new PowerSource[]{
                                PowerSource.ARCANE
                        },
                        new Role[]{
                                Role.LEADER
                        },
                        new AbilityType[]{
                                AbilityType.CHARISMA,
                                AbilityType.CONSTITUTION,
                                AbilityType.INTELLIGENCE
                        },
                        new DefenseType[]{
                                DefenseType.REFLEX,
                                DefenseType.WILL
                        },
                        12,
                        5,
                        7,
                        ArmorType.CHAINMAIL,
                        ShieldType.LIGHT,
                        new WeaponType[]{
                                new WeaponType(
                                        new WeaponGroupType[]{
                                                WeaponGroupType.LONGSWORD,
                                                WeaponGroupType.SCIMITAR,
                                                WeaponGroupType.SHORTSWORD
                                        },
                                        WeaponComplexity.SIMPLE_MELEE,
                                        new WeaponPropertyWithBonus[]{}
                                ),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED),
                                new WeaponType(WeaponComplexity.MILITARY_RANGED)
                        },
                        new ImplementType[]{
                                ImplementType.WAND
                        },
                        new SkillType[]{
                                SkillType.ARCANA
                        },
                        4,
                        new SkillType[]{
                                SkillType.ACROBATICS,
                                SkillType.ATHLETICS,
                                SkillType.BLUFF,
                                SkillType.DIPLOMACY,
                                SkillType.DUNGEONEERING,
                                SkillType.HEAL,
                                SkillType.HISTORY,
                                SkillType.INSIGHT,
                                SkillType.INTIMIDATE,
                                SkillType.NATURE,
                                SkillType.PERCEPTION,
                                SkillType.RELIGION,
                                SkillType.STREETWISE
                        },
                        new Feat[]{
                                new Feat(
                                        "Bardic Training",
                                        "You gain the Ritual Caster feat as a bonus feat, allowing you to use magical rituals. " +
                                                "You own a ritual book, and it contains two rituals of your choice that you have mastered: one 1st-level ritual that has bard as a prerequisite and another 1st-level ritual." +
                                                "In addition, you can perform one bard ritual per day of your level or lower without expending components, although you must pay any other costs and use any focus required by the ritual. " +
                                                "At 11th level, you can perform two bard rituals per day of your level or lower without expending components; at 21st level, you can perform three."
                                ),
                                new Feat(
                                        "Bardic Virtue",
                                        "Bards praise many virtues in their stories, telling tales of people whose particular qualities set them above common folk. " +
                                                "The valor of dauntless heroes and the cunning of great minds are among these virtues, and a bard can choose to emphasize either quality. " +
                                                "Choose one of the following options. " +
                                                "The choice you make gives you the benefit described below and also provides bonuses to certain bard powers, as detailed in those powers:" +
                                                "\n\tVirtue of Cunning: Once per round, when an enemy attack misses an ally within a number of squares of you equal to 5 + your Intelligence modifier, you can slide that ally 1 square as a free action." +
                                                "\n\tVirtue of Valor: Once per round, when any ally within 5 squares of you reduces an enemy to 0 hit points or bloodies an enemy, you can grant temporary hit points to that ally as a free action. " +
                                                "The number of temporary hit points equals 1 + your Constitution modifier at 1st level, 3 + your Constitution modifier at 11th level, and 5 + your Constitution modifier at 21st level."
                                ),
                                new Feat(
                                        "Majestic Word",
                                        "The arcane power of a bard’s voice can heal allies. " +
                                                "You gain the majestic word power."
                                ),
                                new Feat(
                                        "Multiclass Versatility",
                                        "You can choose class-specific multiclass feats from more than one class."
                                ),
                                new Feat(
                                        "Skill Versatility",
                                        "You gain a +1 bonus to untrained skill checks."
                                ),
                                new Feat(
                                        "Song of Rest",
                                        "When you play an instrument or sing during a short rest, you and each ally who can hear you are affected by your Song of Rest. " +
                                                "When an affected character spends healing surges at the end of the rest, that character regains additional hit points equal to your Charisma modifier with each healing surge. " +
                                                "A character can be affected by only one Song of Rest at a time."
                                ),
                                new Feat(
                                        "Words of Friendship",
                                        "Bards use magic to honey their words and turn the simplest argument into a compelling oration. You gain the words of friendship power."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Battlemind",
                        new PowerSource[]{
                                PowerSource.PSIONIC
                        },
                        new Role[]{
                                Role.DEFENDER
                        },
                        new AbilityType[]{
                                AbilityType.CHARISMA,
                                AbilityType.CONSTITUTION,
                                AbilityType.WISDOM
                        },
                        new DefenseType[]{
                                DefenseType.WILL,
                                DefenseType.WILL
                        },
                        15,
                        6,
                        9,
                        ArmorType.SCALE,
                        ShieldType.HEAVY,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.MILITARY_MELEE),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED)
                        },
                        new ImplementType[]{},
                        new SkillType[]{},
                        3,
                        new SkillType[]{
                                SkillType.ACROBATICS,
                                SkillType.ATHLETICS,
                                SkillType.BLUFF,
                                SkillType.DIPLOMACY,
                                SkillType.DUNGEONEERING,
                                SkillType.HEAL,
                                SkillType.HISTORY,
                                SkillType.INSIGHT,
                                SkillType.INTIMIDATE,
                                SkillType.NATURE,
                                SkillType.PERCEPTION,
                                SkillType.RELIGION,
                                SkillType.STREETWISE
                        },
                        new Feat[]{
                                new Feat(
                                        "Psionic Augmentation",
                                        "Through discipline and careful study, you have mastered a form of psionic magic that offers greater versatility than other characters command. " +
                                                "You know a broad array of at-will powers, each of which is a conduit through which you can pour as much or as little psionic energy as you choose. " +
                                                "You channel psionic energy into a reservoir of personal power (represented in the game as power points) that you can use to augment your at-will attack powers, replacing the encounter attack powers that other characters use. " +
                                                "Because of this class feature, you acquire and use powers in a slightly different manner from how most other classes do." +
                                                "\n\tAt-Will Attack Powers: At 1st level, you choose two at-will attack powers and one daily attack power from your class, but you don't start with any encounter attack powers from your class. " +
                                                "You can instead augment your class at·will attack powers using power points. " +
                                                "These powers have the augmentable keyword." +
                                                "You gain new at-will attack powers from this class, instead of new encounter attack powers, as you increase in level. " +
                                                "At 3rd level, you choose a new at-will attack power from this class. " +
                                                "At 7th, 13th, 17th, 23rd, and 27th level, you can replace one ofy our at-will attack powers with another one of your level or lower. " +
                                                "Both powers must be augmentable and from this class." +
                                                "\n\tPower Points: At 1st, 3rd, 17th, 21st, 23rd, and 27th level, you gain +2 maximum power points. At 13th level you gain +1 maximum power points."
                                ),
                                new Feat(
                                        "Psionic Defense",
                                        "Three powers (Battlemind's Demand, Blurred Step, and Mind Spike) help you maintain tactical superiority in combat. " +
                                                "You can use these psionic powers to demand your enemies' attention, follow them if they try to avoid you, and punish them if they attack your allies.\n" +
                                                "This combination of mental compulsion and psionic enhancement of your own capabilities makes you a force to be reckoned with in battle."
                                ),
                                new Feat(
                                        "Psionic Study",
                                        "Battleminds learn to fight using their bodies as weapons. " +
                                                "Some battleminds alter their own form to fit their needs. " +
                                                "Other battleminds use their psionic power to predict the best position for launching an attack. " +
                                                "Choose one of these options:" +
                                                "\n\tBattle Resilience: You gain the Battle Resilience power, which reflects your ability to use your psionic power to bend your own body to protect yourself." +
                                                "\n\tSpeed ofThought: You gain the Speed of Thought power, which allows you to be always ready for a fight."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Cleric",
                        new PowerSource[]{
                                PowerSource.DIVINE
                        },
                        new Role[]{
                                Role.LEADER
                        },
                        new AbilityType[]{
                                AbilityType.WISDOM,
                                AbilityType.STRENGTH,
                                AbilityType.CHARISMA
                        },
                        new DefenseType[]{
                                DefenseType.WILL,
                                DefenseType.WILL
                        },
                        12,
                        5,
                        7,
                        ArmorType.CHAINMAIL,
                        null,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED)
                        },
                        new ImplementType[]{
                                ImplementType.HOLY_SYMBOL
                        },
                        new SkillType[]{
                                SkillType.RELIGION
                        },
                        3,
                        new SkillType[]{
                                SkillType.ARCANA,
                                SkillType.DIPLOMACY,
                                SkillType.HEAL,
                                SkillType.HISTORY,
                                SkillType.INSIGHT
                        },
                        new Feat[]{
                                new Feat(
                                        "Channel Divinity",
                                        "Once per encounter you can invoke divine power, filling yourself with the might of your patron deity. " +
                                                "With the divine might you invoke you can wield special powers, such as Turn Undead and Divine Fortune. " +
                                                "Some clerics learn other uses for this feature, granting characters with access to the Channel Divinity class feature the ability to use additional special powers. " +
                                                "Regardless of how many different uses for Channel Divinity you know, you can use only one such ability per encounter. " +
                                                "The special ability or power you invoke works just like your other powers."
                                ),
                                new Feat(
                                        "Healer’s Lore",
                                        "Your study of healing allows you to make the most of your healing prayers. " +
                                                "When you grant healing with one of your cleric powers that has the healing keyword, add your Wisdom modifier to the hit points the recipient regains."
                                ),
                                new Feat(
                                        "Healing Word",
                                        "Using the Healing Word power, clerics can grant their comrades additional resilience with nothing more than a short prayer."
                                ),
                                new Feat(
                                        "Ritual Casting",
                                        "You gain the Ritual Caster feat as a bonus feat, allowing you to use magical rituals. " +
                                                "You possess a ritual book, and it contains two rituals you have mastered: the Gentle Repose ritual and one other 1st-level ritual of your choice."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Druid",
                        new PowerSource[]{
                                PowerSource.PRIMAL
                        },
                        new Role[]{
                                Role.CONTROLLER
                        },
                        new AbilityType[]{
                                AbilityType.WISDOM,
                                AbilityType.DEXTERITY,
                                AbilityType.CONSTITUTION
                        },
                        new DefenseType[]{
                                DefenseType.REFLEX,
                                DefenseType.WILL
                        },
                        12,
                        5,
                        7,
                        ArmorType.HIDE,
                        null,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED)
                        },
                        new ImplementType[]{
                                ImplementType.STAFF,
                                ImplementType.TOTEM
                        },
                        new SkillType[]{
                                SkillType.NATURE
                        },
                        3,
                        new SkillType[]{
                                SkillType.ARCANA,
                                SkillType.ATHLETICS,
                                SkillType.DIPLOMACY,
                                SkillType.ENDURANCE,
                                SkillType.HEAL,
                                SkillType.HISTORY,
                                SkillType.INSIGHT,
                                SkillType.PERCEPTION
                        },
                        new Feat[]{
                                new Feat(
                                        "Balance of Nature",
                                        "Some druids favor being in beast form, while others prefer being in humanoid form. " +
                                                "However, just as druids seek balance in the world between divine and primordial forces, druids pursue balance within their own minds and bodies. " +
                                                "You begin with three at-will attack powers. " +
                                                "Throughout your career, at least one of those powers, and no more than two, must have the beast form keyword. " +
                                                "By this means, you have access to useful attacks in either beast form or humanoid form."
                                ),
                                new Feat(
                                        "Primal Aspect",
                                        "Druidic lore speaks of the Primal Beast, the first spirit of the world’s noble predators. " +
                                                "A formless thing of shadows, fur, feathers, and claws, this creature appears in many druids’ visions, and they speak of channeling the Primal Beast when using their wild shape and beast form powers. " +
                                                "As a druid, you choose which aspect of the Primal Beast you most strongly manifest with your powers. " +
                                                "Choose one of these options. " +
                                                "Your choice provides bonuses to certain druid powers, as detailed in those powers:" +
                                                "\n\tPrimal Guardian: While you are not wearing heavy armor, you can use your Constitution modifier in place of your Dexterity or Intelligence modifier to determine your AC." +
                                                "\n\tPrimal Predator: While you are not wearing heavy armor, you gain a +1 bonus to your speed."
                                ),
                                new Feat(
                                        "Ritual Casting",
                                        "You gain the Ritual Caster feat as a bonus feat, allowing you to use magical rituals. " +
                                                "You own a ritual book, and it contains two rituals of your choice that you have mastered: Animal Messenger and another 1st-level ritual.\n" +
                                                "Once per day, you can use Animal Messenger without expending components."
                                ),
                                new Feat(
                                        "Wild Shape",
                                        "As a druid, you have the ability to channel the primal energy of beasts into your physical form and transform into a beast. " +
                                                "You have an at-will power, wild shape, that allows you to assume the form of a beast, and many druid powers have the beast form keyword and therefore can be used only while you are in beast form. " +
                                                "The wild shape power lets you assume a form of your size that resembles a natural or a fey beast, usually a four-legged mammalian predator such as a bear, a boar, a panther, a wolf, or a wolverine. " +
                                                "Your beast form might also be an indistinct shape of shadowy fur and claws, an incarnation of the Primal Beast of which all earthly beasts are fractured images. " +
                                                "You choose a specific form whenever you use wild shape, and that form has no effect on your game statistics or movement modes. " +
                                                "Your choice of Primal Aspect might suggest a specific form you prefer to assume, and certain beast form powers specify changes to your form when you use them. " +
                                                "You might also resemble a more exotic beast when you’re in beast form: a reptile such as a rage drake or a crocodile, or a fantastic beast such as an owlbear or a bulette."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Fighter",
                        new PowerSource[]{
                                PowerSource.MARTIAL
                        },
                        new Role[]{
                                Role.DEFENDER
                        },
                        new AbilityType[]{
                                AbilityType.STRENGTH,
                                AbilityType.DEXTERITY,
                                AbilityType.WISDOM,
                                AbilityType.CONSTITUTION
                        },
                        new DefenseType[]{
                                DefenseType.FORTITUDE,
                                DefenseType.FORTITUDE
                        },
                        15,
                        6,
                        9,
                        ArmorType.SCALE,
                        ShieldType.HEAVY,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.MILITARY_MELEE),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED),
                                new WeaponType(WeaponComplexity.MILITARY_RANGED)
                        },
                        new ImplementType[]{},
                        new SkillType[]{},
                        3,
                        new SkillType[]{
                                SkillType.ATHLETICS,
                                SkillType.ENDURANCE,
                                SkillType.HEAL,
                                SkillType.INTIMIDATE,
                                SkillType.STREETWISE
                        },
                        new Feat[]{
                                new Feat(
                                        "Combat Challenge",
                                        "In combat, it’s dangerous to ignore a fighter. " +
                                                "Every time you attack an enemy, whether the attack hits or misses, you can choose to mark that target. " +
                                                "The mark lasts until the end of your next turn. " +
                                                "While a target is marked, it takes a –2 penalty to attack rolls for any attack that doesn’t include you as a target. " +
                                                "A creature can be subject to only one mark at a time. " +
                                                "A new mark supersedes a mark that was already in place. " +
                                                "In addition, whenever a marked enemy that is adjacent to you shifts or makes an attack that does not include you, you can make a melee basic attack against that enemy as an immediate interrupt."
                                ),
                                new Feat(
                                        "Combat Superiority",
                                        "You gain a bonus to opportunity attacks equal to your Wisdom modifier. " +
                                                "An enemy struck by your opportunity attack stops moving, if a move provoked the attack. " +
                                                "If it still has actions remaining, it can use them to resume moving."
                                ),
                                new Feat(
                                        "Fighter Weapon Talent",
                                        "Choose either one-handed or two-handed weapons. " +
                                                "When using a weapon of your chosen style, you gain a +1 bonus to attack rolls."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Invoker",
                        new PowerSource[]{
                                PowerSource.DIVINE
                        },
                        new Role[]{
                                Role.CONTROLLER
                        },
                        new AbilityType[]{
                                AbilityType.WISDOM,
                                AbilityType.CONSTITUTION,
                                AbilityType.INTELLIGENCE
                        },
                        new DefenseType[]{
                                DefenseType.FORTITUDE,
                                DefenseType.REFLEX,
                                DefenseType.WILL
                        },
                        10,
                        4,
                        6,
                        ArmorType.CHAINMAIL,
                        null,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED)
                        },
                        new ImplementType[]{
                                ImplementType.ROD,
                                ImplementType.STAFF
                        },
                        new SkillType[]{
                                SkillType.RELIGION
                        },
                        3,
                        new SkillType[]{
                                SkillType.ARCANA,
                                SkillType.DIPLOMACY,
                                SkillType.ENDURANCE,
                                SkillType.HISTORY,
                                SkillType.INSIGHT,
                                SkillType.INTIMIDATE
                        },
                        new Feat[]{
                                new Feat(
                                        "Channel Divinity",
                                        "Once per encounter, you can use a Channel Divinity power. " +
                                                "You start with two Channel Divinity powers: rebuke undead and a power determined by your Divine Covenant. " +
                                                "You can gain additional Channel Divinity powers by taking divinity feats."
                                ),
                                new Feat(
                                        "Divine Covenant",
                                        "Invokers wield ancient divine power that is not accessible to most mortals—only to those who enter into a personal covenant with a god. " +
                                                "Invokers undergo long years of study and testing. " +
                                                "Only after that time are they allowed to enter into the final covenant that grants them access to this class’s powers. " +
                                                "Some say that, in the final swearing of the covenant, the invoker’s god briefly manifests, but the details of an invoker’s initiation are a closely held secret. " +
                                                "Choose one of the options described below. " +
                                                "The Divine Covenant you choose provides you with a Channel Divinity power and a covenant manifestation that takes effect whenever you use a divine encounter or daily attack power. " +
                                                "Your choice also provides bonuses to certain invoker powers, as detailed in those powers:" +
                                                "\n\tCovenant of Preservation: The gods have charged you to defend the faithful and to ally with those who seek to defeat the gods’ enemies." +
                                                "\nChannel Divinity: You gain the Channel Divinity power Preserver’s Rebuke." +
                                                "\nCovenant Manifestation: When you use a divine encounter or daily attack power on your turn, you can slide an ally within 10 squares of you 1 square." +
                                                "\n\tCovenant of Wrath: You have sworn to seek out and destroy those that oppose the gods. " +
                                                "Primordials, demons, and devils fall before your magic like wheat before a scythe." +
                                                "\nChannel Divinity: You gain the Channel Divinity power Armor of Wrath." +
                                                "\nCovenant Manifestation: When you use a divine encounter or daily attack power on your turn, you gain a bonus to the damage roll equal to 1 for each enemy you attack with the power."
                                ),
                                new Feat(
                                        "Ritual Casting",
                                        "You gain the Ritual Caster feat as a bonus feat, allowing you to use magical rituals. " +
                                                "You own a ritual book, and it contains two rituals of your choice that you have mastered: Hand of Fate and one 1st-level ritual. " +
                                                "Once per day, you can use Hand of Fate without expending components."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Monk",
                        new PowerSource[]{
                                PowerSource.PSIONIC
                        },
                        new Role[]{
                                Role.STRIKER
                        },
                        new AbilityType[]{
                                AbilityType.DEXTERITY,
                                AbilityType.STRENGTH,
                                AbilityType.WISDOM
                        },
                        new DefenseType[]{
                                DefenseType.FORTITUDE,
                                DefenseType.REFLEX,
                                DefenseType.WILL
                        },
                        12,
                        5,
                        7,
                        ArmorType.CLOTH,
                        null,
                        new WeaponType[]{
                                new WeaponType(
                                        new WeaponGroupType[]{
                                                WeaponGroupType.CLUB,
                                                WeaponGroupType.DAGGER,
                                                WeaponGroupType.MONK_UNARMED_STRIKE,
                                                WeaponGroupType.QUARTERSTAFF,
                                                WeaponGroupType.SHURIKEN,
                                                WeaponGroupType.SLING,
                                                WeaponGroupType.SPEAR
                                        }
                                )
                        },
                        new ImplementType[]{
                                ImplementType.KI_FOCUS,
                                ImplementType.WEAPON
                        },
                        new SkillType[]{},
                        4,
                        new SkillType[]{
                                SkillType.ACROBATICS,
                                SkillType.ATHLETICS,
                                SkillType.DIPLOMACY,
                                SkillType.ENDURANCE,
                                SkillType.HEAL,
                                SkillType.INSIGHT,
                                SkillType.PERCEPTION,
                                SkillType.RELIGION,
                                SkillType.STEALTH,
                                SkillType.THIEVERY
                        },
                        new Feat[]{
                                new Feat(
                                        "Monastic Tradition",
                                        "Monks train in a number of traditional techniques, with each monastery focusing on a specific style. " +
                                                "Choose either Centered Breath or Stone Fist as your tradition. " +
                                                "The choice you make grants you a Flurry of Blows power and a defensive benefit:" +
                                                "\n\tCentered Breath: The Centered Breath tradition emphasizes honing your mental awareness to better harness psionic magic. " +
                                                "This tradition teaches that only by controlling yourself can you control your environment. " +
                                                "The tradition's adherents are typically ascetics, whose monasteries stand in quiet corners of the world where the monks can train and study without distraction. " +
                                                "In some of these monasteries, speech is forbidden except for one hour each day." +
                                                "\nFlurry ofBlows: You gain the Centered Flurry of Blows power." +
                                                "\nMental Equilibrium: You gain a +1 bonus to Fortitude. " +
                                                "This bonus increases to +2 at 11th level and +3 at 21st level." +
                                                "\n\tStone Fist: The Stone Fist tradition is one of physical mastery, relentless exercise, and athletic perfection. " +
                                                "Its adherents seek to master their bodies, turning themselves into living weapons capable ofsupernatural feats of strength, agility, and speed. " +
                                                "Monks of the Stone Fist prefer to study among the trappings of civilization. " +
                                                "Some Stone Fist monasteries are small schools built in towns and villages, where students work as laborers and artisans when they aren't training. " +
                                                "Other Stone Fist monasteries stand in the most forbidding regions of the world-from the bitter cold of the tundra to the edge of a rumbling volcano—to test their students' endurance day after day." +
                                                "\nFlurry ofBlows: You gain the Stone Fist Flurry of Blows power." +
                                                "\nMental Bastion: You gain a +1 bonus to Will. This bonus increases to +2 at 11th level and +3 at 21st level."
                                ),
                                new Feat(
                                        "Unarmed Combatant",
                                        "You can make unarmed attacks with much greater effectiveness than most other combatants can. " +
                                                "When you make a weapon attack such as a melee basic attack. " +
                                                "you can use the monk unarmed strike, which is a weapon in the unarmed weapon group. " +
                                                "This weapon has the off-hand weapon property and a +3 proficiency bonus, and it deals Id8 damage. " +
                                                "You must have a hand free to use your monk unarmed strike, even if you're kicking, kneeing, elbowing, or headbutting a target. " +
                                                "Your monk unarmed strike can't be turned into a magic weapon, but it can benefit from a magic ki focus if you have one."
                                ),
                                new Feat(
                                        "Unarmed Defense",
                                        "While you are wearing cloth armor or no armor and aren't using a shield, you gain a +2 bonus to AC."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Paladin",
                        new PowerSource[]{
                                PowerSource.DIVINE
                        },
                        new Role[]{
                                Role.DEFENDER
                        },
                        new AbilityType[]{
                                AbilityType.STRENGTH,
                                AbilityType.CHARISMA,
                                AbilityType.WISDOM
                        },
                        new DefenseType[]{
                                DefenseType.FORTITUDE,
                                DefenseType.REFLEX,
                                DefenseType.WILL
                        },
                        15,
                        6,
                        10,
                        ArmorType.PLATE,
                        ShieldType.HEAVY,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.MILITARY_MELEE),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED)
                        },
                        new ImplementType[]{
                                ImplementType.HOLY_SYMBOL
                        },
                        new SkillType[]{
                                SkillType.RELIGION
                        },
                        3,
                        new SkillType[]{
                                SkillType.DIPLOMACY,
                                SkillType.ENDURANCE,
                                SkillType.HEAL,
                                SkillType.HISTORY,
                                SkillType.INSIGHT,
                                SkillType.INTIMIDATE,
                                SkillType.RELIGION
                        },
                        new Feat[]{
                                new Feat(
                                        "Channel Divinity",
                                        "Once per encounter you can invoke divine power, filling yourself with the might of your patron deity. " +
                                                "With the divine might you invoke you can wield special powers, such as divine mettle and divine strength. " +
                                                "Some paladins learn other uses for this feature, granting characters with access to the Channel Divinity class feature the ability to use additional special powers. " +
                                                "Regardless of how many different uses for Channel Divinity you know, you can use only one such ability per encounter. " +
                                                "The special ability or power you invoke works just like your other powers."
                                ),
                                new Feat(
                                        "Divine Challenge",
                                        "The challenge of a paladin is filled with divine menace. " +
                                                "You can use the divine challenge power to mark an enemy of your choice."
                                ),
                                new Feat(
                                        "Lay on Hands",
                                        "Using the Lay on Hands power, paladins can grant their comrades additional resilience with a touch of their hands and a short prayer, though they must give of their own strength to do so."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Psion",
                        new PowerSource[]{
                                PowerSource.PSIONIC
                        },
                        new Role[]{
                                Role.CONTROLLER
                        },
                        new AbilityType[]{
                                AbilityType.INTELLIGENCE,
                                AbilityType.CHARISMA,
                                AbilityType.WISDOM
                        },
                        new DefenseType[]{
                                DefenseType.WILL,
                                DefenseType.WILL
                        },
                        12,
                        4,
                        6,
                        ArmorType.CLOTH,
                        null,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED)
                        },
                        new ImplementType[]{
                                ImplementType.ORB,
                                ImplementType.STAFF
                        },
                        new SkillType[]{},
                        4,
                        new SkillType[]{
                                SkillType.ARCANA,
                                SkillType.BLUFF,
                                SkillType.DIPLOMACY,
                                SkillType.DUNGEONEERING,
                                SkillType.HISTORY,
                                SkillType.INSIGHT,
                                SkillType.INTIMIDATE,
                                SkillType.PERCEPTION
                        },
                        new Feat[]{
                                new Feat(
                                        "Discipline Focus",
                                        "Psions focus their studies and meditations toward perfecting or understanding a particular concept or manifestation of psionic power. " +
                                                "Choose one of these options. " +
                                                "Your choice represents the focus of your studies:" +
                                                "\n\tTelekinesis Focus: You gain the powers Far Hand and Forceful Push." +
                                                "\n\tTelepathy Focus: You gain the powers Distract and Send Thoughts."
                                ),
                                new Feat(
                                        "Psionic Augmentation",
                                        "Through discipline and careful study, you have mastered a form of psionic magic that offers greater versatility than other characters command. " +
                                                "You know a broad array of at-will powers, each of which is a conduit through which you can pour as much or as little psionic energy as you choose. " +
                                                "You channel psionic energy into a reservoir of personal power (represented in the game as power points) that you can use to augment your at-will attack powers, replacing the encounter attack powers that other characters use. " +
                                                "Because of this class feature, you acquire and use powers in a slightly different manner from how most other classes do." +
                                                "\n\tAt-Will Attack Powers: At 1st level, you choose two at-will attack powers and one daily attack power from your class, but you don't start with any encounter attack powers from your class. " +
                                                "You can instead augment your class at·will attack powers using power points. " +
                                                "These powers have the augmentable keyword." +
                                                "You gain new at-will attack powers from this class, instead of new encounter attack powers, as you increase in level. " +
                                                "At 3rd level, you choose a new at-will attack power from this class. " +
                                                "At 7th, 13th, 17th, 23rd, and 27th level, you can replace one ofy our at-will attack powers with another one of your level or lower. " +
                                                "Both powers must be augmentable and from this class." +
                                                "\n\tPower Points: At 1st, 3rd, 17th, 21st, 23rd, and 27th level, you gain +2 maximum power points. At 13th level you gain +1 maximum power points."
                                ),
                                new Feat(
                                        "Ritual Casting",
                                        "You gain the Ritual Caster feat as a bonus feat, allowing you to use magical rituals. " +
                                                "You own a ritual book. " +
                                                "Choose either Sending or Tenser's Floating Disk. " +
                                                "Your book contains that ritual, which you can use without expending components once per day. " +
                                                "It also contains another 1st-level ritual of your choice."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Ranger",
                        new PowerSource[]{
                                PowerSource.MARTIAL
                        },
                        new Role[]{
                                Role.STRIKER
                        },
                        new AbilityType[]{
                                AbilityType.STRENGTH,
                                AbilityType.DEXTERITY,
                                AbilityType.WISDOM
                        },
                        new DefenseType[]{
                                DefenseType.FORTITUDE,
                                DefenseType.REFLEX
                        },
                        12,
                        5,
                        6,
                        ArmorType.HIDE,
                        null,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.MILITARY_MELEE),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED),
                                new WeaponType(WeaponComplexity.MILITARY_RANGED)
                        },
                        new ImplementType[]{},
                        new SkillType[]{
                                SkillType.DUNGEONEERING,
                                SkillType.XOR,
                                SkillType.NATURE
                        },
                        4,
                        new SkillType[]{
                                SkillType.ACROBATICS,
                                SkillType.ATHLETICS,
                                SkillType.ENDURANCE,
                                SkillType.HEAL,
                                SkillType.PERCEPTION,
                                SkillType.STEALTH
                        },
                        new Feat[]{
                                new Feat(
                                        "Fighting Style",
                                        "Choose one of the following fighting styles and gain its benefit:" +
                                                "\n\tArcher Fighting Style: Because of your focus on ranged attacks, you gain Defensive Mobility as a bonus feat." +
                                                "\n\tTwo-Blade Fighting Style: Because of your focus on two-weapon melee attacks, you can wield a one-handed weapon in your off hand as if it were an off-hand weapon. " +
                                                "(Make sure to designate which weapon is main and which is off-hand.) In addition, you gain Toughness as a bonus feat."
                                ),
                                new Feat(
                                        "Hunter’s Quarry",
                                        "Once per turn as a minor action, you can designate the enemy nearest to you as your quarry. " +
                                                "Once per round, you deal extra damage to your quarry. " +
                                                "The extra damage is equal to +1d6 at 1st level, +2d6 at 11th level, and +3d6 at 21st level. " +
                                                "If you can make multiple attacks in a round, you decide which attack to apply the extra damage to after all the attacks are rolled. " +
                                                "The hunter’s quarry effect remains active until the end of the encounter, until the quarry is defeated, or until you designate a different target as your quarry. " +
                                                "You can designate one enemy as your quarry at a time."
                                ),
                                new Feat(
                                        "Prime Shot",
                                        "If none of your allies are nearer to your target than you are, you receive a +1 bonus to ranged attack rolls against that target."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Rogue",
                        new PowerSource[]{
                                PowerSource.MARTIAL
                        },
                        new Role[]{
                                Role.STRIKER
                        },
                        new AbilityType[]{
                                AbilityType.DEXTERITY,
                                AbilityType.STRENGTH,
                                AbilityType.CHARISMA
                        },
                        new DefenseType[]{
                                DefenseType.REFLEX,
                                DefenseType.REFLEX
                        },
                        12,
                        5,
                        6,
                        ArmorType.LEATHER,
                        null,
                        new WeaponType[]{
                                new WeaponType(
                                        new WeaponGroupType[]{
                                                WeaponGroupType.DAGGER,
                                                WeaponGroupType.HAND_CROSSBOW,
                                                WeaponGroupType.SHURIKEN,
                                                WeaponGroupType.SLING,
                                                WeaponGroupType.SHORTSWORD
                                        }
                                )
                        },
                        new ImplementType[]{},
                        new SkillType[]{
                                SkillType.STEALTH,
                                SkillType.AND,
                                SkillType.THIEVERY
                        },
                        4,
                        new SkillType[]{
                                SkillType.ACROBATICS,
                                SkillType.ATHLETICS,
                                SkillType.BLUFF,
                                SkillType.DUNGEONEERING,
                                SkillType.INSIGHT,
                                SkillType.INTIMIDATE,
                                SkillType.PERCEPTION,
                                SkillType.STREETWISE,
                        },
                        new Feat[]{
                                new Feat(
                                        "First Strike",
                                        "At the start of an encounter, you have combat advantage against any creatures that have not yet acted in that encounter."
                                ),
                                new Feat(
                                        "Rogue Tactics",
                                        "Rogues operate in a variety of ways. Some rogues use their natural charm and cunning trickery to deceive foes. " +
                                                "Others rely on brute strength to overcome their enemies." +
                                                "The choice you make also provides bonuses to certain rogue powers. " +
                                                "Individual powers detail the effects (if any) your Rogue Tactics selection has on them." +
                                                "Choose one of the following options:" +
                                                "\n\tArtful Dodger: You gain a bonus to AC equal to your Charisma modifier against opportunity attacks." +
                                                "\n\tBrutal Scoundrel: You gain a bonus to Sneak Attack damage equal to your Strength modifier."
                                ),
                                new Feat(
                                        "Rogue Weapon Talent",
                                        "When you wield a shuriken, your weapon damage die increases by one size. " +
                                                "When you wield a dagger, you gain a +1 bonus to attack rolls."
                                ),
                                new Feat(
                                        "Sneak Attack",
                                        "Once per round, when you have combat advantage against an enemy and are using a weapon from the light blade, the crossbow, or the sling weapon group, an attack you make against that enemy deals extra damage if the attack hits. " +
                                                "You decide whether to apply the extra damage after making the damage roll. " +
                                                "The extra damage is equal to +2d6 at 1st level, +3d6 at 11th level, and +5d6 at 21st level."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Runepriest",
                        new PowerSource[]{
                                PowerSource.DIVINE
                        },
                        new Role[]{
                                Role.LEADER
                        },
                        new AbilityType[]{
                                AbilityType.STRENGTH,
                                AbilityType.CONSTITUTION,
                                AbilityType.WISDOM
                        },
                        new DefenseType[]{
                                DefenseType.WILL,
                                DefenseType.WILL
                        },
                        12,
                        5,
                        7,
                        ArmorType.SCALE,
                        ShieldType.LIGHT,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED)
                        },
                        new ImplementType[]{},
                        new SkillType[]{
                                SkillType.RELIGION
                        },
                        3,
                        new SkillType[]{
                                SkillType.ATHLETICS,
                                SkillType.ENDURANCE,
                                SkillType.HEAL,
                                SkillType.HISTORY,
                                SkillType.INSIGHT,
                                SkillType.RELIGION,
                                SkillType.THIEVERY
                        },
                        new Feat[]{
                                new Feat(
                                        "Runic Master",
                                        "Some of your powers have the runic keyword. " +
                                                "When you are going to use a runic power, you first choose one of the runes noted in the power (either the rune of destruction or the rune of protection) and then use the power, applying the chosen rune's effects. " +
                                                "The moment you choose the rune, you enter its rune state. " +
                                                "You remain in that rune state until you enter another rune state or until the end of the encounter. " +
                                                "Whenever you enter the rune state of the rune of destruction or the rune of protection, you gain an additional benefit, specified below. " +
                                                "The benefit lasts while you're in the rune state." +
                                                "\n\tRune of Destruction: Allies gain a +1 bonus to attack rolls against enemies that are adjacent to you or to any other runepriests who are in this rune state." +
                                                "\n\tRune of Protection: While adjacent to you, allies gain resist 2 to all damage. " +
                                                "The resistance increases to 4 at 11th level and 6 at 21st level."
                                ),
                                new Feat(
                                        "Rune of Mending",
                                        "You gain the Rune of Mending power. " +
                                                "This rune restores your allies' health and grants them additional power based on your rune state."
                                ),
                                new Feat(
                                        "Runic Artistry",
                                        "Many runepriests follow one of the two major traditions of rune magic: the path of the Wrathful Hammer or the way of the Defiant Word. " +
                                                "The two traditions use the same runes but differ in their practices and teachings. " +
                                                "Choose one of the following options:" +
                                                "\n\tDefiant Word: Whenever an enemy misses you with an attack, you gain a bonus to damage rolls against that enemy until the end of your next turn. " +
                                                "The bonus equals your Wisdom modifier, regardless of the number of times the enemy misses you in a round." +
                                                "\n\tWrathful Hammer: You gain proficiency with military hammers and military maces. " +
                                                "In addition, whenever an enemy deals damage to you with an attack, you gain a bonus to damage rolls against that enemy until the end of your next turn. " +
                                                "The bonus equals your Constitution modifier, regardless of the number of times the enemy damages you in a round."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Seeker",
                        new PowerSource[]{
                                PowerSource.PRIMAL
                        },
                        new Role[]{
                                Role.CONTROLLER
                        },
                        new AbilityType[]{
                                AbilityType.WISDOM,
                                AbilityType.STRENGTH,
                                AbilityType.DEXTERITY
                        },
                        new DefenseType[]{
                                DefenseType.REFLEX,
                                DefenseType.WILL
                        },
                        12,
                        5,
                        7,
                        ArmorType.LEATHER,
                        null,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED),
                                new WeaponType(WeaponComplexity.MILITARY_RANGED)
                        },
                        new ImplementType[]{},
                        new SkillType[]{
                                SkillType.NATURE
                        },
                        3,
                        new SkillType[]{
                                SkillType.ACROBATICS,
                                SkillType.ATHLETICS,
                                SkillType.ENDURANCE,
                                SkillType.HEAL,
                                SkillType.INSIGHT,
                                SkillType.INTIMIDATE,
                                SkillType.PERCEPTION,
                                SkillType.STEALTH
                        },
                        new Feat[]{
                                new Feat(
                                        "Inevitable Shot",
                                        "You gain the inevitable shot power. " +
                                                "You can use this power to call on spirits to send your projectile hurling toward another enemy when you miss with a ranged attack."
                                ),
                                new Feat(
                                        "Seeker's Bond",
                                        "Seekers develop special bonds with primal spirits by offering solemn vows to further the spirits' purposes." +
                                                "In exchange for these vows, the spirits bestow a measure of their strength to aid their champions' cause. " +
                                                "Choose one of these options. " +
                                                "Your choice provides bonuses to certain seeker powers, as detailed in those powers:" +
                                                "\n\tB1oodbond: You gain the Encaging Spirits power. " +
                                                "In addition, while you are not wearing heavy armor, you can shift as a minor action." +
                                                "\n\tSpiritbond: You gain the spirits' rebuke power. " +
                                                "You also gain a +1 bonus to attack rolls with both light thrown and heavy thrown weapons, and when you make an attack by throwing a weapon with which you have proficiency, the weapon returns to your hand after the attack. " +
                                                "In addition, while you are not wearing heavy armor, you can use your Strength modifier in place of your Dexterity or Intelligence modifier to determine your AC."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Shaman",
                        new PowerSource[]{
                                PowerSource.PRIMAL
                        },
                        new Role[]{
                                Role.LEADER
                        },
                        new AbilityType[]{
                                AbilityType.WISDOM,
                                AbilityType.CONSTITUTION,
                                AbilityType.INTELLIGENCE
                        },
                        new DefenseType[]{
                                DefenseType.FORTITUDE,
                                DefenseType.WILL
                        },
                        12,
                        5,
                        7,
                        ArmorType.LEATHER,
                        null,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(
                                        new WeaponGroupType[]{
                                                WeaponGroupType.LONGSPEAR
                                        }
                                )
                        },
                        new ImplementType[]{
                                ImplementType.TOTEM
                        },
                        new SkillType[]{
                                SkillType.NATURE
                        },
                        3,
                        new SkillType[]{
                                SkillType.ARCANA,
                                SkillType.ATHLETICS,
                                SkillType.ENDURANCE,
                                SkillType.HEAL,
                                SkillType.HISTORY,
                                SkillType.INSIGHT,
                                SkillType.PERCEPTION,
                                SkillType.RELIGION
                        },
                        new Feat[]{
                                new Feat(
                                        "Companion Spirit",
                                        "As part of your initiation as a shaman, you acquired a spirit companion, an animal spirit that accompanies and assists you. " +
                                                "Many shaman powers have the spirit keyword. " +
                                                "Your spirit companion must be present when you use such a power. " +
                                                "You gain the call spirit companion power, which allows you to call your spirit companion to your side. " +
                                                "In addition, choose one of the following Companion Spirit options. " +
                                                "Your choice provides you with a Spirit Boon as well as a special attack made through your spirit companion, and your choice determines one of your at-will attack powers. " +
                                                "Your choice also provides bonuses to certain shaman powers, as detailed in those powers:" +
                                                "\n\tProtector Spirit: You draw on the strength of the bear or a similar protective spirit to defend and bolster your allies." +
                                                "\nSpirit Boon: Any ally adjacent to your spirit companion regains additional hit points equal to your Constitution modifier when he or she uses second wind or when you use a healing power on him or her." +
                                                "\nSpirit’s Shield: You gain the Spirit’s Shield power, an attack you make through your spirit companion as an opportunity action." +
                                                "\nAt-Will Attack Power: You gain the Protecting Strike power. " +
                                                "You choose a second at-will attack power as normal." +
                                                "\n\tStalker Spirit: You call on the stealth and cunning of the panther or a similar stalking spirit to empower and position your allies." +
                                                "\nSpirit Boon: Any ally adjacent to your spirit companion gains a bonus to damage rolls against bloodied enemies equal to your Intelligence modifier." +
                                                "\nSpirit’s Fangs: You gain the Spirit’s Fangs power, an attack you make through your spirit companion as an opportunity action." +
                                                "\nAt-Will Attack Power: You gain the Stalker’s Strike power. " +
                                                "You choose a second at-will attack power as normal."
                                ),
                                new Feat(
                                        "Healing Spirit",
                                        "You gain the Healing Spirit power. " +
                                                "Through this power, you grant your allies additional resilience with a short evocation of primal power."
                                ),
                                new Feat(
                                        "Speak with Spirits",
                                        "You gain the Speak with Spirits power. " +
                                                "You are aware of the constant presence of spirits that float at the edges of reality. " +
                                                "You can focus your inner energy and open your mind to these spirits, letting them guide your actions or fill you with insights."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Sorcerer",
                        new PowerSource[]{
                                PowerSource.ARCANE
                        },
                        new Role[]{
                                Role.STRIKER
                        },
                        new AbilityType[]{
                                AbilityType.CHARISMA,
                                AbilityType.DEXTERITY,
                                AbilityType.STRENGTH
                        },
                        new DefenseType[]{
                                DefenseType.WILL,
                                DefenseType.WILL
                        },
                        12,
                        5,
                        6,
                        ArmorType.CLOTH,
                        null,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED)
                        },
                        new ImplementType[]{
                                ImplementType.DAGGER,
                                ImplementType.STAFF
                        },
                        new SkillType[]{
                                SkillType.ARCANA
                        },
                        3,
                        new SkillType[]{
                                SkillType.ATHLETICS,
                                SkillType.BLUFF,
                                SkillType.DIPLOMACY,
                                SkillType.DUNGEONEERING,
                                SkillType.ENDURANCE,
                                SkillType.HISTORY,
                                SkillType.INSIGHT,
                                SkillType.INTIMIDATE,
                                SkillType.NATURE
                        },
                        new Feat[]{
                                new Feat(
                                        "Spell Source",
                                        "As a sorcerer, you gain power through an instinctive or inborn connection to an ancient arcane source. " +
                                                "Choose either Dragon Magic or Wild Magic. " +
                                                "The choice you make grants you specific features and also provides bonuses to certain sorcerer powers, as detailed in those powers:" +
                                                "\n\tDragon Magic: The elemental power of dragons flows through you, infusing your spells with draconic strength. " +
                                                "Through force of will, you tap into the arcane might of dragons." +
                                                "\nDraconic Power: You gain a bonus to the damage rolls of arcane powers equal to your Strength modifier. " +
                                                "The bonus increases to your Strength modifier + 2 at 11th level and your Strength modifier + 4 at 21st level. " +
                                                "\nDraconic Resilience: While you are not wearing heavy armor, you can use your Strength modifier in place of your Dexterity or Intelligence modifier to determine your AC. " +
                                                "\nDragon Soul: Choose a damage type: acid, cold, fire, lightning, poison, or thunder. " +
                                                "You gain resist 5 to that damage type. The resistance increases to 10 at 11th level and 15 at 21st level. " +
                                                "Your arcane powers ignore any target’s resistance to that damage type up to the value of your resistance. " +
                                                "\nScales of the Dragon: The first time you become bloodied during an encounter, you gain a +2 bonus to AC until the end of the encounter." +
                                                "\n\tWild Magic: You draw your spells from the entropic forces of the Elemental Chaos. " +
                                                "Whether tapping into the power of primordial beings or drawing strength directly from that plane, you unleash magic in wild surges. " +
                                                "\nChaos Burst: Your first attack roll during each of your turns determines a benefit you gain in that round. " +
                                                "If you roll an even number, you gain a +1 bonus to AC until the start of your next turn. " +
                                                "If you roll an odd number, you make a saving throw. " +
                                                "\nChaos Power: You gain a bonus to the damage rolls of arcane powers equal to your Dexterity modifier. " +
                                                "The bonus increases to your Dexterity modifier + 2 at 11th level and your Dexterity modifier + 4 at 21st level. " +
                                                "\nUnfettered Power: When you roll a natural 20 on an attack roll for an arcane power, you slide the target 1 square and knock it prone after applying the attack’s other effects. " +
                                                "When you roll a natural 1 on an attack roll for an arcane power, you must push each creature within 5 squares of you 1 square. " +
                                                "\nWild Soul: When you finish an extended rest, roll a d10 to determine a damage type. " +
                                                "You gain resist 5 to that damage type until the end of your next extended rest. " +
                                                "The resistance increases to 10 at 11th level and 15 at 21st level. " +
                                                "While you have resistance to that damage type, your arcane powers ignore any target’s resistance to that damage type up to the value of your resistance." +
                                                "\n\t1\nAcid" +
                                                "\n\t2\nCold" +
                                                "\n\t3\nFire" +
                                                "\n\t4\nForce" +
                                                "\n\t5\nLightning" +
                                                "\n\t6\nNecrotic" +
                                                "\n\t7\nPoison" +
                                                "\n\t8\nPsychic" +
                                                "\n\t9\nRadiant" +
                                                "\n\t10\nThunder"
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Warden",
                        new PowerSource[]{
                                PowerSource.PRIMAL
                        },
                        new Role[]{
                                Role.DEFENDER
                        },
                        new AbilityType[]{
                                AbilityType.STRENGTH,
                                AbilityType.CONSTITUTION,
                                AbilityType.WISDOM
                        },
                        new DefenseType[]{
                                DefenseType.FORTITUDE,
                                DefenseType.WILL
                        },
                        17,
                        7,
                        9,
                        ArmorType.HIDE,
                        ShieldType.HEAVY,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.MILITARY_MELEE),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED)
                        },
                        new ImplementType[]{},
                        new SkillType[]{
                                SkillType.NATURE
                        },
                        3,
                        new SkillType[]{
                                SkillType.ATHLETICS,
                                SkillType.DUNGEONEERING,
                                SkillType.ENDURANCE,
                                SkillType.HEAL,
                                SkillType.INTIMIDATE,
                                SkillType.PERCEPTION
                        },
                        new Feat[]{
                                new Feat(
                                        "Font of Life",
                                        "At the start of your turn, you can make a saving throw against one effect that a save can end. " +
                                                "On a save, the effect immediately ends, preventing it from affecting you on your current turn. " +
                                                "If you save against being stunned or dazed, you can act normally on your turn. " +
                                                "If you save against ongoing damage, you avoid taking the damage. " +
                                                "If you fail the saving throw, you still make a saving throw against the effect at the end of your turn."
                                ),
                                new Feat(
                                        "Guardian Might",
                                        "Wardens connect with the natural world in a variety of ways to augment their fighting abilities. " +
                                                "Choose one of the following options:" +
                                                "\n\tEarthstrength: While you are not wearing heavy armor, you can use your Constitution modifier in place of your Dexterity or Intelligence modifier to determine your AC. " +
                                                "In addition, when you use your second wind, you gain an additional bonus to AC equal to your Constitution modifier. " +
                                                "The bonus lasts until the end of your next turn. " +
                                                "\n\tWildblood: While you are not wearing heavy armor, you can use your Wisdom modifier in place of your Dexterity or Intelligence modifier to determine your AC. " +
                                                "In addition, when you use your second wind, each enemy marked by you takes an additional penalty to attack rolls for attacks that don’t include you as a target. " +
                                                "The penalty equals your Wisdom modifier and lasts until the end of your next turn."
                                ),
                                new Feat(
                                        "Nature’s Wrath",
                                        "Once during each of your turns, you can mark each adjacent enemy as a free action. " +
                                                "This mark lasts until the end of your next turn. " +
                                                "In addition, you gain the warden’s fury and warden’s grasp powers. " +
                                                "You can use these powers against enemies to prevent them from harming those you protect."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Warlock",
                        new PowerSource[]{
                                PowerSource.ARCANE
                        },
                        new Role[]{
                                Role.STRIKER
                        },
                        new AbilityType[]{
                                AbilityType.CHARISMA,
                                AbilityType.CONSTITUTION,
                                AbilityType.INTELLIGENCE
                        },
                        new DefenseType[]{
                                DefenseType.REFLEX,
                                DefenseType.WILL
                        },
                        12,
                        5,
                        6,
                        ArmorType.LEATHER,
                        null,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED)
                        },
                        new ImplementType[]{
                                ImplementType.ROD,
                                ImplementType.WAND
                        },
                        new SkillType[]{},
                        4,
                        new SkillType[]{
                                SkillType.ARCANA,
                                SkillType.BLUFF,
                                SkillType.HISTORY,
                                SkillType.INSIGHT,
                                SkillType.INTIMIDATE,
                                SkillType.RELIGION,
                                SkillType.STREETWISE,
                                SkillType.THIEVERY
                        },
                        new Feat[]{
                                new Feat(
                                        "Eldritch Blast",
                                        "All warlocks know the Eldritch Blast at-will power. " +
                                                "This power can be used as a basic attack. " +
                                                "You gain this power as well as another at-will power as determined by your Eldritch Pact."
                                ),
                                new Feat(
                                        "Eldritch Pact",
                                        "You have forged a pact with mysterious entities that grant you your arcane powers. " +
                                                "Choose one of the following pacts: Fey Pact, Infernal Pact, or Star Pact." +
                                                "The pact you choose determines the following warlock abilities:" +
                                                "\nAt-Will Spells: Your pact determines one of the at-will spells you know. " +
                                                "\nPact Boon: Each pact includes a pact boon. " +
                                                "The pact boon is a granted power you can use to further hex your enemies. " +
                                                "The pact you take also provides bonuses to certain warlock powers. " +
                                                "Individual powers detail the effects (if any) your Eldritch Pact selection has on them." +
                                                "\n\tFey Pact: You have forged a bargain with ancient, amoral powers of the Feywild. " +
                                                "Some are primitive earth spirits, grim and menacing; some are capricious wood, sky, or water spirits; and others are incarnations of seasons or natural forces who roam the faerie realm like wild gods. " +
                                                "They bestow magic that ranges from feral and savage to wondrous and enchanting. " +
                                                "\nEyebite: You know the eyebite at-will spell. " +
                                                "\nMisty Step: You have the Misty Step pact boon. " +
                                                "You instantly transform into silver mist that streams a short distance and reforms, allowing you to flee or maneuver to set up a deadly attack. " +
                                                "When an enemy under your Warlock’s Curse is reduced to 0 hit points or fewer, you can immediately teleport 3 squares as a free action. " +
                                                "\n\tInfernal Pact: Long ago a forgotten race of devils created a secret path to power and taught it to the tieflings of old to weaken their fealty to Asmodeus. " +
                                                "In his wrath, Asmodeus destroyed the scheming devils and struck their very names from the memory of all beings—but you dare to study their perilous secrets anyway. " +
                                                "\nHellish Rebuke: You know the hellish rebuke at-will spell. " +
                                                "\nDark One’s Blessing: You have the Dark One’s Blessing pact boon. " +
                                                "You instantly gain vitality from a cursed enemy when that enemy falls. " +
                                                "When an enemy under your Warlock’s Curse is reduced to 0 hit points or fewer, you immediately gain temporary hit points equal to your level. " +
                                                "\n\tStar Pact: You have mastered the astrologer’s art, learning the secret names of the stars and gazing into the Far Realm beyond, gaining great power thereby. " +
                                                "You can call upon powers that madden or terrify your enemies, manipulate chance and fate, or scour your foes with icy banes and curses drawn from beyond the night sky. " +
                                                "\nDire Radiance: You know the dire radiance at-will spell. " +
                                                "\nFate of the Void: You have the Fate of the Void pact boon. " +
                                                "Your curse intermingles with the lost vitality of a cursed enemy to reveal a glimpse of the future to you. " +
                                                "When an enemy under your Warlock’s Curse is reduced to 0 hit points or fewer, you gain a +1 bonus to any single d20 roll you make during your next turn (attack roll, saving throw, skill check, or ability check). " +
                                                "If you don’t use this bonus by the end of your turn, it is lost. " +
                                                "This bonus is cumulative; if three cursed enemies drop to 0 hit points or fewer before your next turn, you gain a +3 bonus to a d20 roll during your turn."
                                ),
                                new Feat(
                                        "Prime Shot",
                                        "If none of your allies are nearer to your target than you are, you receive a +1 bonus to ranged attack rolls against that target."
                                ),
                                new Feat(
                                        "Shadow Walk",
                                        "On your turn, if you move at least 3 squares away from where you started your turn, you gain concealment until the end of your next turn."
                                ),
                                new Feat(
                                        "Warlock’s Curse",
                                        "Once per turn as a minor action, you can place a Warlock’s Curse on the enemy nearest to you that you can see. " +
                                                "A cursed enemy is more vulnerable to your attacks. " +
                                                "If you damage a cursed enemy, you deal extra damage. " +
                                                "You decide whether to apply the extra damage after making the damage roll. " +
                                                "You can deal this extra damage once per round. " +
                                                "A Warlock’s Curse remains in effect until the end of the encounter or until the cursed enemy drops to 0 hit points or fewer. " +
                                                "You can place a Warlock’s Curse on multiple targets over the course of an encounter; each curse requires the use of a minor action. " +
                                                "You can’t place a Warlock’s Curse on a creature that is already affected by your or another character’s Warlock’s Curse. " +
                                                "The extra damage is equal to +1d6 at 1st level, +2d6 at 11th level, and +3d6 at 21st level."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Warlord",
                        new PowerSource[]{
                                PowerSource.MARTIAL
                        },
                        new Role[]{
                                Role.LEADER
                        },
                        new AbilityType[]{
                                AbilityType.STRENGTH,
                                AbilityType.INTELLIGENCE,
                                AbilityType.CHARISMA
                        },
                        new DefenseType[]{
                                DefenseType.FORTITUDE,
                                DefenseType.WILL
                        },
                        12,
                        5,
                        7,
                        ArmorType.CHAINMAIL,
                        ShieldType.LIGHT,
                        new WeaponType[]{
                                new WeaponType(WeaponComplexity.SIMPLE_MELEE),
                                new WeaponType(WeaponComplexity.MILITARY_MELEE),
                                new WeaponType(WeaponComplexity.SIMPLE_RANGED)
                        },
                        new ImplementType[]{},
                        new SkillType[]{},
                        4,
                        new SkillType[]{
                                SkillType.ATHLETICS,
                                SkillType.DIPLOMACY,
                                SkillType.ENDURANCE,
                                SkillType.HEAL,
                                SkillType.HISTORY,
                                SkillType.INTIMIDATE
                        },
                        new Feat[]{
                                new Feat(
                                        "Combat Leader",
                                        "You and each ally within 10 squares who can see and hear you gain a +2 power bonus to initiative."
                                ),
                                new Feat(
                                        "Commanding Presence",
                                        "Choose one of the following two benefits. " +
                                                "The choice you make also provides bonuses to certain warlord powers. " +
                                                "Individual powers detail the effects (if any) your Commanding Presence selection has on them:" +
                                                "\n\tInspiring Presence: When an ally who can see you spends an action point to take an extra action, that ally also regains lost hit points equal to one-half your level + your Charisma modifier. " +
                                                "\n\tTactical Presence: When an ally you can see spends an action point to make an extra attack, the ally gains a bonus to the attack roll equal to one-half your Intelligence modifier. "
                                ),
                                new Feat(
                                        "Inspiring Word",
                                        "Using the inspiring word power, warlords can grant their comrades additional resilience with nothing more than a shout of encouragement."
                                )
                        },
                        false
                )
        );
        BuilderActivity.sAvailableClasses.add(
                new D20Class(
                        "Wizard",
                        new PowerSource[]{
                                PowerSource.ARCANE
                        },
                        new Role[]{
                                Role.CONTROLLER
                        },
                        new AbilityType[]{
                                AbilityType.INTELLIGENCE,
                                AbilityType.WISDOM,
                                AbilityType.DEXTERITY
                        },
                        new DefenseType[]{
                                DefenseType.WILL,
                                DefenseType.WILL
                        },
                        10,
                        4,
                        6,
                        ArmorType.CLOTH,
                        null,
                        new WeaponType[]{
                                new WeaponType(
                                        new WeaponGroupType[]{
                                                WeaponGroupType.DAGGER,
                                                WeaponGroupType.QUARTERSTAFF
                                        }
                                )
                        },
                        new ImplementType[]{
                                ImplementType.ORB,
                                ImplementType.STAFF,
                                ImplementType.WAND
                        },
                        new SkillType[]{
                                SkillType.ARCANA
                        },
                        3,
                        new SkillType[]{
                                SkillType.DIPLOMACY,
                                SkillType.DUNGEONEERING,
                                SkillType.HISTORY,
                                SkillType.INSIGHT,
                                SkillType.NATURE,
                                SkillType.RELIGION
                        },
                        new Feat[]{
                                new Feat(
                                        "Arcane Implement Mastery",
                                        "You specialize in the use of one kind of implement to gain additional abilities when you wield it. " +
                                                "Choose one of the following forms of implement mastery:" +
                                                "\n\tOrb of Imposition: Once per encounter as a free action, you can use your orb to gain one of the following two effects. " +
                                                "You can designate one creature you have cast a wizard spell upon that has an effect that lasts until the subject succeeds on a saving throw. " +
                                                "That creature takes a penalty to its saving throws against that effect equal to your Wisdom modifier. " +
                                                "Alternatively, you can choose to extend the duration of an effect created by a wizard at-will spell (such as cloud of daggers or ray of frost) that would otherwise end at the end of your current turn. " +
                                                "The effect instead ends at the end of your next turn. " +
                                                "You must wield an orb to use this ability. " +
                                                "Control wizards select this form of mastery because it helps extend the duration of their control effects. " +
                                                "\n\tStaff of Defense: A staff of defense grants you a +1 bonus to AC. " +
                                                "In addition, once per encounter as an immediate interrupt, you gain a bonus to defense against one attack equal to your Constitution modifier. " +
                                                "You can declare the bonus after the Dungeon Master has already told you the damage total. " +
                                                "You must wield your staff to benefit from these features. " +
                                                "This form of mastery is useful for all wizards, particularly if you dabble in both control and damage-dealing spells. " +
                                                "\n\tWand of Accuracy: Once per encounter as a free action, you gain a bonus to a single attack roll equal to your Dexterity modifier. " +
                                                "You must wield your wand to benefit from this feature. " +
                                                "This form of mastery is good for war wizards because it helps increase their accuracy with damaging powers."
                                ),
                                new Feat(
                                        "Cantrips",
                                        "Cantrips are minor spells you gain at 1st level. " +
                                                "You can use the Ghost Sound, Light, Mage Hand, and Prestidigitation cantrips as at-will powers."
                                ),
                                new Feat(
                                        "Ritual Casting",
                                        "You gain the Ritual Caster feat as a bonus feat, allowing you to use magical rituals. " +
                                                "You own a ritual book, and it contains two rituals of your choice that you have mastered."
                                )
                        },
                        false
                )
        );
    }

    private void initRaces() {
        BuilderActivity.sAvailableRaces = new ArrayList<>();
        BuilderActivity.sAvailableRaces.add(
                new D20Race("Dragonborn",
                        getString(R.string.summary_race_dragonborn),
                        new Trait[]{
                                new Trait("Dragonborn Fury", "When you're bloodied, you gain a +1 racial bonus to attack rolls."),
                                new Trait("Draconic Heritage", "Your healing surge value is equal to one-quarter of your maximum hit points + your Constitution modifier."),
                                new Trait("Dragonborn Racial Power", "You can use either Dragon Breath or Dragon Fear as an encounter power.")
                        },
                        new String[]{
                                "+2 Charisma",
                                "+2 Strength"
                        },
                        new String[]{
                                "+2 History",
                                "+2 Intimidate"
                        },
                        SizeType.MEDIUM,
                        6,
                        VisionType.NORMAL,
                        new String[]{
                                "Common",
                                "Draconic"
                        },
                        74,
                        80,
                        220,
                        320,
                        new D20Class[]{
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.FIGHTER.ordinal()),
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.PALADIN.ordinal()),
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.WARLORD.ordinal())
                        }
                )
        );
        BuilderActivity.sAvailableRaces.add(
                new D20Race("Dwarf",
                        getString(R.string.summary_race_dwarf),
                        new Trait[]{
                                new Trait("Cast-Iron Stomach", "+5 racial bonus to saving throws against poison."),
                                new Trait("Dwarven Resilience", "You can use your second wind as a minor action instead of a standard action."),
                                new Trait("Dwarven Weapon Proficiency", "You gain proficiency with the throwing hammer and the warhammer."),
                                new Trait("Encumbered Speed", "You move at your normal speed even when it would normally be reduced by armor or a heavy load. " +
                                        "Other effects that limit speed (such as difficult terrain or magical effects) affect you normally."),
                                new Trait("Stand Your Ground", "When an effect forces you to move—through a pull, a push, or a slide—you can move 1 square less than the effect specifies. " +
                                        "This means an effect that normally pulls, pushes, or slides a target 1 square does not force you to move unless you want to. " +
                                        "In addition, when an attack would knock you prone, you can immediately make a saving throw to avoid falling prone.")
                        },
                        new String[]{
                                "+2 Constitution",
                                "+2 Wisdom"
                        },
                        new String[]{
                                "+2 Dungeoneering",
                                "+2 Endurance"
                        },
                        SizeType.MEDIUM,
                        5,
                        VisionType.LOW_LIGHT,
                        new String[]{
                                "Common",
                                "Dwarven"
                        },
                        51,
                        56,
                        160,
                        220,
                        new D20Class[]{
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.CLERIC.ordinal()),
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.FIGHTER.ordinal()),
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.PALADIN.ordinal())
                        }
                )
        );
        BuilderActivity.sAvailableRaces.add(
                new D20Race("Eladrin",
                        getString(R.string.summary_race_eladrin),
                        new Trait[]{
                                new Trait("Eladrin Education", "You gain training in any one additional skill."),
                                new Trait("Eladrin Weapon Proficiency", "You gain proficiency with the longsword."),
                                new Trait("Eladrin Will", "You gain a +1 racial bonus to your Will defense. " +
                                        "In addition, you gain a +5 racial bonus to saving throws against charm effects."),
                                new Trait("Fey Origin", "Your ancestors were native to the Feywild, so you are considered a fey creature for the purpose of effects that relate to creature origin."),
                                new Trait("Trance", "Rather than sleep, eladrin enter a meditative state known as trance. " +
                                        "You need to spend 4 hours in this state to gain the same benefits other races gain from taking a 6-hour extended rest. " +
                                        "While in a trance, you are fully aware of your surroundings and notice approaching enemies and other events as normal."),
                                new Trait("Fey Step", "You can use Fey Step as an encounter power.")
                        },
                        new String[]{
                                "+2 Dexterity",
                                "+2 Intelligence"
                        },
                        new String[]{
                                "+2 Arcana",
                                "+2 History"
                        },
                        SizeType.MEDIUM,
                        6,
                        VisionType.LOW_LIGHT,
                        new String[]{
                                "Common",
                                "Elven"
                        },
                        65,
                        73,
                        130,
                        180,
                        new D20Class[]{
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.ROGUE.ordinal()),
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.WARLORD.ordinal()),
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.WIZARD.ordinal())
                        }
                )
        );
        BuilderActivity.sAvailableRaces.add(
                new D20Race("Elf",
                        getString(R.string.summary_race_elf),
                        new Trait[]{
                                new Trait("Elven Weapon Proficiency", "You gain proficiency with the longbow and the shortbow."),
                                new Trait("Fey Origin", "Your ancestors were native to the Feywild, so you are considered a fey creature for the purpose of effects that relate to creature origin."),
                                new Trait("Group Awareness", "You grant non-elf allies within 5 squares of you a +1 racial bonus to Perception checks."),
                                new Trait("Wild Step", "You ignore difficult terrain when you shift (even if you have a power that allows you to shift multiple squares)."),
                                new Trait("Elven Accuracy", "You can use Elven Accuracy as an encounter power.")
                        },
                        new String[]{
                                "+2 Dexterity",
                                "+2 Wisdom"
                        },
                        new String[]{
                                "+2 Nature",
                                "+2 Perception"
                        },
                        SizeType.MEDIUM,
                        7,
                        VisionType.LOW_LIGHT,
                        new String[]{
                                "Common",
                                "Elven"
                        },
                        64,
                        72,
                        130,
                        170,
                        new D20Class[]{
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.CLERIC.ordinal()),
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.RANGER.ordinal()),
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.ROGUE.ordinal())
                        }
                )
        );
        BuilderActivity.sAvailableRaces.add(
                new D20Race("Half-Elf",
                        getString(R.string.summary_race_halfelf),
                        new Trait[]{
                                new Trait("Dilettante", "At 1st level, you choose an at-will power from a class different from yours. You can use that power as an encounter power."),
                                new Trait("Dual Heritage", "You can take feats that have either elf or human as a prerequisite (as well as those specifically for half-elves), as long as you meet any other requirements."),
                                new Trait("Group Diplomacy", "You grant allies within 10 squares of you a +1 racial bonus to Diplomacy checks.")
                        },
                        new String[]{
                                "+2 Constitution",
                                "+2 Charisma"
                        },
                        new String[]{
                                "+2 Diplomacy",
                                "+2 Insight"
                        },
                        SizeType.MEDIUM,
                        6,
                        VisionType.LOW_LIGHT,
                        new String[]{
                                "Common",
                                "Elven",
                                "choice of 1 other"
                        },
                        65,
                        74,
                        130,
                        190,
                        new D20Class[]{
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.PALADIN.ordinal()),
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.WARLOCK.ordinal()),
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.WARLORD.ordinal())
                        }
                )
        );
        BuilderActivity.sAvailableRaces.add(
                new D20Race("Halfling",
                        getString(R.string.summary_race_halfling),
                        new Trait[]{
                                new Trait("Bold", "You gain a +5 racial bonus to saving throws against fear."),
                                new Trait("Nimble Reaction", "You gain a +2 racial bonus to AC against opportunity attacks."),
                                new Trait("Second Chance", "You can use second chance as an encounter power.")
                        },
                        new String[]{
                                "+2 Dexterity",
                                "+2 Charisma"
                        },
                        new String[]{
                                "+2 Acrobatics",
                                "+2 Thievery"
                        },
                        SizeType.SMALL,
                        6,
                        VisionType.NORMAL,
                        new String[]{
                                "Common",
                                "choice of 1 other"
                        },
                        46,
                        50,
                        75,
                        85,
                        new D20Class[]{
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.RANGER.ordinal()),
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.ROGUE.ordinal()),
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.WARLOCK.ordinal())
                        }
                )
        );
        BuilderActivity.sAvailableRaces.add(
                new D20Race("Human",
                        getString(R.string.summary_race_human),
                        new Trait[]{
                                new Trait("Bonus At-Will Power", "You know one extra at-will power from your class."),
                                new Trait("Bonus Feat", "You gain a bonus feat at 1st level. You must meet the feat’s prerequisites."),
                                new Trait("Bonus Skill", "You gain training in one additional skill from your class skill list."),
                                new Trait("Human Defense Bonuses", "+1 to Fortitude, Reflex, and Will defenses.")
                        },
                        new String[]{
                                "+2 to one ability score of your choice"
                        },
                        new String[]{
                                "+2 Acrobatics",
                                "+2 Thievery"
                        },
                        SizeType.MEDIUM,
                        6,
                        VisionType.NORMAL,
                        new String[]{
                                "Common",
                                "choice of 1 other"
                        },
                        66,
                        74,
                        135,
                        220,
                        new D20Class[]{}
                )
        );
        BuilderActivity.sAvailableRaces.add(
                new D20Race("Tiefling",
                        getString(R.string.summary_race_tiefling),
                        new Trait[]{
                                new Trait("Bloodhunt", "You gain a +1 racial bonus to attack rolls against bloodied foes."),
                                new Trait("Fire Resistance", "You have resist fire 5 + one-half your level."),
                                new Trait("Infernal Wrath", "You can use infernal wrath as an encounter power.")
                        },
                        new String[]{
                                "+2 Intelligence",
                                "+2 Charisma"
                        },
                        new String[]{
                                "+2 Bluff",
                                "+2 Stealth"
                        },
                        SizeType.MEDIUM,
                        6,
                        VisionType.LOW_LIGHT,
                        new String[]{
                                "Common",
                                "choice of 1 other"
                        },
                        66,
                        74,
                        140,
                        230,
                        new D20Class[]{
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.ROGUE.ordinal()),
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.WARLOCK.ordinal()),
                                BuilderActivity.sAvailableClasses.get(D20ClassEnumerator.WARLORD.ordinal())
                        }
                )
        );
    }

    private class TraitListViewAdapter extends ArrayAdapter {
        Context context;
        ArrayList<Trait> traits;

        public TraitListViewAdapter(Context context, int resource, ArrayList<Trait> traits) {
            super(context, resource, traits);
            this.context = context;
            this.traits = traits;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.trait_row, null);

            String name = traits.get(position).getName();
            String desc = traits.get(position).getDesc();
            TextView nameView = row.findViewById(R.id.Trait_Row_Title);
            TextView descView = row.findViewById(R.id.Trait_Row_Desc);
            nameView.setText(name);
            descView.setText(desc);
            return row;
        }
    }
}
