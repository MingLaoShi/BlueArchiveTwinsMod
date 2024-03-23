package baModDeveloper;

import baModDeveloper.cards.*;
import baModDeveloper.cards.bullets.*;
import baModDeveloper.cards.colorless.BATwinsAccelerate;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.character.BATwinsCharacter.Enums;
import baModDeveloper.event.BATwinsDirtShowdown;
import baModDeveloper.event.BATwinsHurdleGame;
import baModDeveloper.event.BATwinsTrainingCamp;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.potion.BATwinsAcceleratePotion;
import baModDeveloper.potion.BATwinsBurnPotion;
import baModDeveloper.potion.BATwinsConnectPotion;
import baModDeveloper.relic.*;
import baModDeveloper.ui.panels.icons.BATwinsMidoriEnergyOrbSmall;
import baModDeveloper.ui.panels.icons.BATwinsMomoiEnergyOrbSmall;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.eventUtil.AddEventParams;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.Settings.GameLanguage;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;

import static com.megacrit.cardcrawl.core.Settings.language;

@SpireInitializer
public class BATwinsMod implements EditCardsSubscriber, EditStringsSubscriber, EditCharactersSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber, AddAudioSubscriber, PostInitializeSubscriber {

    public static final Color BATwinsColor = new Color(254.0F / 255.0F, 168.0F / 255.0F, 198.0F / 255.0F, 1.0F);
    public static final Color MOMOIColor = new Color(254.0F / 255.0F, 168.0F / 255.0F, 198.0F / 255.0F, 1.0F);
    public static final Color MIDORIColor = new Color(85.0F / 255.0F, 171.0F / 255.0F, 72.0F / 255.0F, 1.0F);
    private static final String BATWINS_CHARACTER_BUTTON = ModHelper.makeImgPath("char", "Character_Button");
    private static final String BATWINS_CHARACTER_PORTRAIT = ModHelper.makeImgPath("char", "Character_Portrait");
    private static final String BATWINS_MOMOI_ATTACK_512 = ModHelper.makeImgPath("512", "bg_attack_512");
    private static final String BATWINS_MOMOI_POWER_512 = ModHelper.makeImgPath("512", "bg_power_512");
    private static final String BATWINS_MOMOI_SKILL_512 = ModHelper.makeImgPath("512", "bg_skill_512");
    private static final String BATWINS_MIDORI_ATTACK_512 = ModHelper.makeImgPath("512", "bg_attack_512_2");
    private static final String BATWINS_MIDORI_POWER_512 = ModHelper.makeImgPath("512", "bg_power_512_2");
    private static final String BATWINS_MIDORI_SKILL_512 = ModHelper.makeImgPath("512", "bg_skill_512_2");
    private static final String MOMOI_SMALL_ORB = ModHelper.makeImgPath("512", "small_orb_double");
    private static final String MIDORI_SMALL_ORB = ModHelper.makeImgPath("512", "small_orb_2");
    private static final String BATWINS_MOMOI_ATTACK_1024 = ModHelper.makeImgPath("1024", "bg_attack_1024");
    private static final String BATWINS_MOMOI_POWER_1024 = ModHelper.makeImgPath("1024", "bg_power_1024");
    private static final String BATWINS_MOMOI_SKILL_1024 = ModHelper.makeImgPath("1024", "bg_skill_1024");
    private static final String BATWINS_MIDORI_ATTACK_1024 = ModHelper.makeImgPath("1024", "bg_attack_1024_2");
    private static final String BATWINS_MIDORI_POWER_1024 = ModHelper.makeImgPath("1024", "bg_power_1024_2");
    private static final String BATWINS_MIDORI_SKILL_1024 = ModHelper.makeImgPath("1024", "bg_skill_1024_2");
    private static final String MOMOI_BIG_ORB = ModHelper.makeImgPath("512", "card_orb");
    private static final String MIDORI_BIG_ORB = ModHelper.makeImgPath("512", "card_orb_2");
    private static final String MOMOI_ENERGY_ORB = ModHelper.makeImgPath("1024", "cost_orb");
    private static final String MIDORI_ENERGY_ORB = ModHelper.makeImgPath("1024", "cost_orb_2");


    //模组选项
    public static boolean AutoSort = true;
    public static boolean ShowExpBar = true;
    public static boolean Enable3D = false;
    public static boolean Tutorial = true;

    public BATwinsMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(Enums.BATWINS_MOMOI_CARD, MOMOIColor, MOMOIColor, MOMOIColor, MOMOIColor, MOMOIColor, MOMOIColor, MOMOIColor, BATWINS_MOMOI_ATTACK_512, BATWINS_MOMOI_SKILL_512, BATWINS_MOMOI_POWER_512, MOMOI_BIG_ORB, BATWINS_MOMOI_ATTACK_1024, BATWINS_MOMOI_SKILL_1024, BATWINS_MOMOI_POWER_1024, MOMOI_ENERGY_ORB, MOMOI_SMALL_ORB);
        BaseMod.addColor(Enums.BATWINS_MIDORI_CARD, MIDORIColor, MIDORIColor, MIDORIColor, MIDORIColor, MIDORIColor, MIDORIColor, MIDORIColor, BATWINS_MIDORI_ATTACK_512, BATWINS_MIDORI_SKILL_512, BATWINS_MIDORI_POWER_512, MIDORI_BIG_ORB, BATWINS_MIDORI_ATTACK_1024, BATWINS_MIDORI_SKILL_1024, BATWINS_MIDORI_POWER_1024, MIDORI_ENERGY_ORB, MIDORI_SMALL_ORB);
    }

    public static void initialize() {
        new BATwinsMod();
        try {
            Properties defaults = new Properties();
            defaults.setProperty(ModHelper.makePath("AutoSort"), "true");
            defaults.setProperty(ModHelper.makePath("ShowExpBar"), "true");
            SpireConfig config = new SpireConfig(ModHelper.getModID(), "Common", defaults);
            AutoSort = config.getBool(ModHelper.makePath("AutoSort"));
            ShowExpBar = config.getBool(ModHelper.makePath("ShowExpBar"));
            Enable3D = config.getBool(ModHelper.makePath("Enable3D"));
            Tutorial = config.getBool(ModHelper.makePath("Tutorial"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receiveEditCards() {
        CustomIconHelper.addCustomIcon(BATwinsMomoiEnergyOrbSmall.get());
        CustomIconHelper.addCustomIcon(BATwinsMidoriEnergyOrbSmall.get());

        BaseMod.addCard(new BATwinsMomoiStrick());
        BaseMod.addCard(new BATwinsMidoriStrick());
        BaseMod.addCard(new BATwinsMomoiDefend());
        BaseMod.addCard(new BATwinsMidoriDefend());
        BaseMod.addCard(new BATwinsAlreadyAngry());
        BaseMod.addCard(new BATwinsPaintingConception());
        BaseMod.addCard(new BATwinsParoxysmalPain());
        BaseMod.addCard(new BATwinsPaintingArt());
        BaseMod.addCard(new BATwinsDeveloperCollaboration());
        BaseMod.addCard(new BATwinsExchange());
        BaseMod.addCard(new BATwinsKeepItToTheEnd());
        BaseMod.addCard(new BATwinsArtPolishing());
        BaseMod.addCard(new BATwinsBattleCommand());
//        BaseMod.addCard(new BATwinsIgniteTheUpperBody());
        BaseMod.addCard(new BATwinsNotReconciled());
        BaseMod.addCard(new BATwinsAdditionalAttacks());
        BaseMod.addCard(new BATwinsAccurateBlocking());
        BaseMod.addCard(new BATwinsGameLaunch());
        BaseMod.addCard(new BATwinsInspirationEmergence());
        BaseMod.addCard(new BATwinsEnchantedBullet());
//        BaseMod.addCard(new BATwinsPassageInForce());
//        BaseMod.addCard(new BATwinsSiteAdaptation());
        BaseMod.addCard(new BATwinsOnceMore());
//        BaseMod.addCard(new BATwinsBadDesigner());
//        BaseMod.addCard(new BATwinsExcellentDesigner());
//        BaseMod.addCard(new BATwinsBugCard());
//        BaseMod.addCard(new BATwinsPlotRepair());
//        BaseMod.addCard(new BATwinsRushToDraft());
//        BaseMod.addCard(new BATwinsPlotRepair());
//        BaseMod.addCard(new BATwinsBubFix());
//        BaseMod.addCard(new BATwinsAdventureOpening());
//        BaseMod.addCard(new BATwinsAdventureBattle());
        BaseMod.addCard(new BATwinsLightSpeedStrike());
        BaseMod.addCard(new BATwinsSinglePlayerGame());
        BaseMod.addCard(new BATwinsCoolingTime());
        BaseMod.addCard(new BATwinsDoubleExperience());
        BaseMod.addCard(new BATwinsBDStudy());
//        BaseMod.addCard(new BATwinsEmergencyRecovery());
        BaseMod.addCard(new BATwinsAbstractSchool());
//        BaseMod.addCard(new BATwinsDefensiveCounterattack());
//        BaseMod.addCard(new BATwinsAdventureRewards());
//        BaseMod.addCard(new BATwinsAccumulatedStrike());
//        BaseMod.addCard(new BATwinsNormalAttackMethods());
        BaseMod.addCard(new BATwinsAlternatingAttack());
        BaseMod.addCard(new BATwinsTemporaryAssistance());
//        BaseMod.addCard(new BATwinsAdventureExperience());
        BaseMod.addCard(new BATwinsSwitchStrike());
        BaseMod.addCard(new BATwinsCoverCharge());
        BaseMod.addCard(new BATwinsMutualUnderstanding());
        BaseMod.addCard(new BATwinsMysteriousChest());
        BaseMod.addCard(new BATwinsSeeYouHaveASharen());
        BaseMod.addCard(new BATwinsCheatingCodeEnabled());
        BaseMod.addCard(new BATwinsFundOverdraft());
        BaseMod.addCard(new BATwinsTakeActionsSeparately());
        BaseMod.addCard(new BATwinsRepeatOperation());
        BaseMod.addCard(new BATwinsConvenientConnectivity());
        BaseMod.addCard(new BATwinsEndCombo());
        BaseMod.addCard(new BATwinsReadingDocuments());
        BaseMod.addCard(new BATwinsMandatoryInstruction());
        BaseMod.addCard(new BATwinsTwoStageAttack());
        BaseMod.addCard(new BATwinsTakeABreak());
        BaseMod.addCard(new BATwinsAttackWithAllMight());
        BaseMod.addCard(new BATwinsDualProtection());
        BaseMod.addCard(new BATwinsOperateFreely());
        BaseMod.addCard(new BATwinsExperienceGiftPackage());
        BaseMod.addCard(new BATwinsSelfConnectivity());
        BaseMod.addCard(new BATwinsSkillCombination());
        BaseMod.addCard(new BATwinsIntegratingAndIntegrating());
//        BaseMod.addCard(new BATwinsPlotPrediction());
        BaseMod.addCard(new BATwinsMasterCraftsmanship());
        BaseMod.addCard(new BATwinsPoisonGasBomb());
        BaseMod.addCard(new BATwinsAdventureBegins());
        BaseMod.addCard(new BATwinsPropCollection());
        BaseMod.addCard(new BATwinsEquipmentUpgrade());
        BaseMod.addCard(new BATwinsAutomaticDefense());
        BaseMod.addCard(new BATwinsBenefitReducingMagic());
        BaseMod.addCard(new BATwinsBorrowMe());
        BaseMod.addCard(new BATwinsMaidForm());
        BaseMod.addCard(new BATwinsShiftingAndGhosting());
        BaseMod.addCard(new BATwinsLeaveItToMe());
        BaseMod.addCard(new BATwinsAssault());
        BaseMod.addCard(new BATwinsDontSayIt());
        BaseMod.addCard(new BATwinsItsSoPainful());
        BaseMod.addCard(new BATwinsCollaboration());
        BaseMod.addCard(new BATwinsPenetrationDamage());
        BaseMod.addCard(new BATwinsCheckTheStrategy());
//        BaseMod.addCard(new BATwinsLoginRewards());
        BaseMod.addCard(new BATwinsLearned());
        BaseMod.addCard(new BATwinsForceDetonation());
        BaseMod.addCard(new BATwinsScriptRewriting());
//        BaseMod.addCard(new BATwinsBullet());
        BaseMod.addCard(new BATwinsContinuousShooting());
        BaseMod.addCard(new BATwinsIncendiaryBullet());
        BaseMod.addCard(new BATwinsPoisonBullet());
        BaseMod.addCard(new BATwinsSniperBullet());
        BaseMod.addCard(new BATwinsArmorPiercingBullet());
        BaseMod.addCard(new BATwinsBreechLoading());
        BaseMod.addCard(new BATwinsStableShooting());
        BaseMod.addCard(new BATwinsRandomShooting());
        BaseMod.addCard(new BATwinsFocusShooting());
        BaseMod.addCard(new BATwinsHeavyBullets());
        BaseMod.addCard(new BATwinsBulletWarehouse());
//        BaseMod.addCard(new BATwinsExpansionMagazine());
        BaseMod.addCard(new BATwinsAccelerate());
    }

    @Override
    public void receiveEditStrings() {
        String lang;
        if (language == GameLanguage.ZHS) {
            lang = "ZHS";
        } else {
            lang = "ENG";
        }
        try{
            BaseMod.loadCustomStringsFile(CardStrings.class, "baModResources/localization/" + lang + "/cards.json");
            BaseMod.loadCustomStringsFile(CharacterStrings.class, "baModResources/localization/" + lang + "/character.json");
            BaseMod.loadCustomStringsFile(PowerStrings.class, "baModResources/localization/" + lang + "/power.json");
            BaseMod.loadCustomStringsFile(UIStrings.class, "baModResources/localization/" + lang + "/uistring.json");
            BaseMod.loadCustomStringsFile(RelicStrings.class, "baModResources/localization/" + lang + "/relic.json");
            BaseMod.loadCustomStringsFile(EventStrings.class, "baModResources/localization/" + lang + "/event.json");
            BaseMod.loadCustomStringsFile(PotionStrings.class,"baModResources/localization/" + lang + "/potion.json");

        }catch (GdxRuntimeException e){
            System.out.println("BATwinsMod:该语言选项没有文本。");
            BaseMod.loadCustomStringsFile(CardStrings.class, "baModResources/localization/ZHS/cards.json");
            BaseMod.loadCustomStringsFile(CharacterStrings.class, "baModResources/localization/ZHS/character.json");
            BaseMod.loadCustomStringsFile(PowerStrings.class, "baModResources/localization/ZHS/power.json");
            BaseMod.loadCustomStringsFile(UIStrings.class, "baModResources/localization/ZHS/uistring.json");
            BaseMod.loadCustomStringsFile(RelicStrings.class, "baModResources/localization/ZHS/relic.json");
            BaseMod.loadCustomStringsFile(EventStrings.class, "baModResources/localization/ZHS/event.json");
            BaseMod.loadCustomStringsFile(PotionStrings.class,"baModResources/localization/ZHS/potion.json");


        }
  }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new BATwinsCharacter(CardCrawlGame.playerName), BATWINS_CHARACTER_BUTTON, BATWINS_CHARACTER_PORTRAIT, Enums.BATwins);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "ENG";
        if (language == GameLanguage.ZHS) {
            lang = "ZHS";
        }
        try{
            String json = Gdx.files.internal("baModResources/localization/" + lang + "/keyword.json").readString(String.valueOf(StandardCharsets.UTF_8));
            Keyword[] keywords = gson.fromJson(json, Keyword[].class);
            if (keywords != null) {
                for (Keyword keyword : keywords) {
                    BaseMod.addKeyword("batwinsmod", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
                }
            }
        }catch (GdxRuntimeException e){
            System.out.println("BATwinsMod:该语言选项没有文本。");
            String json = Gdx.files.internal("baModResources/localization/ZHS/keyword.json").readString(String.valueOf(StandardCharsets.UTF_8));
            Keyword[] keywords = gson.fromJson(json, Keyword[].class);
            if (keywords != null) {
                for (Keyword keyword : keywords) {
                    BaseMod.addKeyword("batwinsmod", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
                }
            }
        }

    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new BATwinsMomoisGameConsole(), Enums.BATWINS_MOMOI_CARD);
        BaseMod.addRelicToCustomPool(new BATwinsMidorisGameConsole(), Enums.BATWINS_MOMOI_CARD);
        BaseMod.addRelic(new BATwinsAncientGameCartridges(), RelicType.SHARED);
        BaseMod.addRelic(new BATwinsByProving(), RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new BATwinsGameMagazine(), Enums.BATWINS_MOMOI_CARD);
        BaseMod.addRelicToCustomPool(new BATwinsGameGuide(), Enums.BATWINS_MOMOI_CARD);

    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio(ModHelper.makePath("campfire_momoi"), ModHelper.makeAudioPath("campfire_momoi"));
        BaseMod.addAudio(ModHelper.makePath("campfire_midori"), ModHelper.makeAudioPath("campfire_midori"));
        BaseMod.addAudio(ModHelper.makePath("eateregg1"), ModHelper.makeAudioPath("eateregg1"));
        BaseMod.addAudio(ModHelper.makePath("eateregg2"), ModHelper.makeAudioPath("eateregg2"));
        BaseMod.addAudio(ModHelper.makePath("charSelect_momoi"), ModHelper.makeAudioPath("charSelect_momoi"));
        BaseMod.addAudio(ModHelper.makePath("charSelect_midori"), ModHelper.makeAudioPath("charSelect_midori"));
        BaseMod.addAudio(ModHelper.makePath("pixelTime"),ModHelper.makeAudioPath("PixelTime"));
    }

    @Override
    public void receivePostInitialize() {
        try {
            CreateConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BaseMod.addEvent(BATwinsTrainingCamp.ID, BATwinsTrainingCamp.class);
        BaseMod.addEvent(new AddEventParams.Builder(BATwinsDirtShowdown.ID,BATwinsDirtShowdown.class).playerClass(Enums.BATwins).create());
        BaseMod.addEvent(BATwinsHurdleGame.ID, BATwinsHurdleGame.class);

        BaseMod.addPotion(BATwinsAcceleratePotion.class,BATwinsAcceleratePotion.liquidColor,BATwinsAcceleratePotion.hybridColor,BATwinsAcceleratePotion.spotsColor,BATwinsAcceleratePotion.ID);
        BaseMod.addPotion(BATwinsConnectPotion.class,BATwinsConnectPotion.liquidColor,BATwinsConnectPotion.hybridColor,BATwinsConnectPotion.spotsColor,BATwinsConnectPotion.ID);
        BaseMod.addPotion(BATwinsBurnPotion.class,BATwinsBurnPotion.liquidColor,BATwinsBurnPotion.hybridColor,BATwinsBurnPotion.spotsColor,BATwinsBurnPotion.ID);
    }

    private void CreateConfig() throws IOException {
        SpireConfig spireConfig = new SpireConfig("BATwinsMod", "Common");
        ModPanel settingPanel = new ModPanel();
        ModLabeledToggleButton autoSort = new ModLabeledToggleButton("AutoSort", 500.0F, 600.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, AutoSort, settingPanel, modLabel -> {

        }, modToggleButton -> {
            spireConfig.setBool(ModHelper.makePath("AutoSort"), AutoSort = modToggleButton.enabled);
            CardCrawlGame.mainMenuScreen.optionPanel.effects.clear();
            try {
                spireConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        settingPanel.addUIElement(autoSort);

        ModLabeledToggleButton showExpBar = new ModLabeledToggleButton("ShowExpBar", 500.0F, 400.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, ShowExpBar, settingPanel, modLabel -> {

        }, modToggleButton -> {
            spireConfig.setBool(ModHelper.makePath("ShowExpBar"), ShowExpBar = modToggleButton.enabled);
            CardCrawlGame.mainMenuScreen.optionPanel.effects.clear();
            try {
                spireConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        settingPanel.addUIElement(showExpBar);

        ModLabeledToggleButton enable3D = new ModLabeledToggleButton("Enable3D（Need to restart game!）", 500.0F, 200.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, Enable3D, settingPanel, modLabel -> {

        }, modToggleButton -> {
            spireConfig.setBool(ModHelper.makePath("Enable3D"), Enable3D = modToggleButton.enabled);
            CardCrawlGame.mainMenuScreen.optionPanel.effects.clear();
            try {
                spireConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        settingPanel.addUIElement(enable3D);

        Texture badgeTexture = ImageMaster.loadImage(ModHelper.makeImgPath("UI", "configButton"));
        BaseMod.registerModBadge(badgeTexture, "BATwinsMod", "0v0", "config", settingPanel);
    }
}
