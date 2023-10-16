package baModDeveloper.character;

import java.util.ArrayList;

import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.relics.Vajra;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;

import baModDeveloper.BATwinsMod;
import baModDeveloper.cards.BATwinsStrick;
import baModDeveloper.core.BATwinsEnergyManager;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.energyorb.BATwinsEnergyMidoriOrb;
import baModDeveloper.ui.panels.energyorb.BATwinsEnergyMomoiOrb;
import basemod.abstracts.CustomPlayer;

public class BATwinsCharacter extends CustomPlayer {
    private static final String BATWINS_CHARACTER_SHOULDER_1 = ModHelper.makeImgPath("char", "shoulder1");
    private static final String BATWINS_CHARACTER_SHOULDER_2 = ModHelper.makeImgPath("char", "shoulder2");
    private static final String BATWINS_CHARACTER_CORPSE = ModHelper.makeImgPath("char", "corpse");
    private static final String[] MOMOI_ORB_TEXTURES = new String[] {
            ModHelper.makeImgPath("UI/orb", "layer1_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer2_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer3_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer4_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer5_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer6_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer1d_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer2d_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer3d_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer4d_momoi"),
            ModHelper.makeImgPath("UI/orb", "layer5d_momoi")
    };
    private static final String[] MIDORI_ORB_TEXTURES = new String[] {
            ModHelper.makeImgPath("UI/orb", "layer1_midori"),
            ModHelper.makeImgPath("UI/orb", "layer2_midori"),
            ModHelper.makeImgPath("UI/orb", "layer3_midori"),
            ModHelper.makeImgPath("UI/orb", "layer4_midori"),
            ModHelper.makeImgPath("UI/orb", "layer5_midori"),
            ModHelper.makeImgPath("UI/orb", "layer6_midori"),
            ModHelper.makeImgPath("UI/orb", "layer1d_midori"),
            ModHelper.makeImgPath("UI/orb", "layer2d_midori"),
            ModHelper.makeImgPath("UI/orb", "layer3d_midori"),
            ModHelper.makeImgPath("UI/orb", "layer4d_midori"),
            ModHelper.makeImgPath("UI/orb", "layer5d_midori")
    };
    private static final String MOMOI_ORB_VFX = ModHelper.makeImgPath("UI/orb", "vfx_momoi");
    private static final String MIDORI_ORB_VFX=ModHelper.makeImgPath("UI/orb","vfx_midori");
    private static final String MOMOI_ORB_MARK=ModHelper.makeImgPath("UI/orb","orbMark_momoi");
    private static final String MIDORI_ORB_MARK=ModHelper.makeImgPath("UI/orb","orbMark_midori");
    private static final float[] LAYER_SPEED = new float[] { -40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F,
            -5.0F, 0.0F };
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack
            .getCharacterString(ModHelper.makePath("Twins"));

    private EnergyOrbInterface energyOrbMomoi;
    private EnergyOrbInterface energyOrbMidori;

    public BATwinsCharacter(String name) {
        super(name, Enums.BATwins, MOMOI_ORB_TEXTURES, MOMOI_ORB_VFX, LAYER_SPEED, null, null);
        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 0.0F * Settings.scale);

        this.energyOrbMomoi = new BATwinsEnergyMomoiOrb(MOMOI_ORB_TEXTURES, MOMOI_ORB_VFX, LAYER_SPEED,MOMOI_ORB_MARK);
        this.energyOrbMidori = new BATwinsEnergyMidoriOrb(MIDORI_ORB_TEXTURES, MIDORI_ORB_VFX, LAYER_SPEED,MIDORI_ORB_MARK);

        this.initializeClass(ModHelper.makeImgPath("char", "character"), BATWINS_CHARACTER_SHOULDER_2,
                BATWINS_CHARACTER_SHOULDER_1, BATWINS_CHARACTER_CORPSE, getLoadout(), 0.0F, 0.0F, 200.0F, 200.0F,
                new BATwinsEnergyManager(2));
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public CardColor getCardColor() {
        return Enums.BATWINS_CARD;
    }

    @Override
    public Color getCardRenderColor() {
        return BATwinsMod.BATwinsColor;
    }

    @Override
    public Color getCardTrailColor() {
        return BATwinsMod.BATwinsColor;
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_HEAVY";
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(characterStrings.NAMES[0], characterStrings.TEXT[0], 75, 75, 0, 99, 5, this,
                this.getStartingRelics(), this.getStartingDeck(), false);
    }

    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    @Override
    public Color getSlashAttackColor() {
        return BATwinsMod.BATwinsColor;
    }

    @Override
    public AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] { AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL };
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new BATwinsStrick();
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        for (int x = 0; x < 5; x++) {
            retVal.add(BATwinsStrick.ID);
        }
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Vajra.ID);
        return retVal;
    }

    @Override
    public String getTitle(PlayerClass arg0) {
        return characterStrings.NAMES[0];
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new BATwinsCharacter(this.name);
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        this.energyOrbMomoi.renderOrb(sb, enabled, current_x, current_y);
        this.energyOrbMidori.renderOrb(sb, enabled, current_x, current_y);
    }

    public static class Enums {
        @SpireEnum
        public static PlayerClass BATwins;

        @SpireEnum(name = "EXAMPLE_GREEN")
        public static AbstractCard.CardColor BATWINS_CARD;

        @SpireEnum(name = "EXAMPLE_GREEN")
        public static CardLibrary.LibraryType BATWINS_LIBRARY;
    }

    @Override
    public Texture getEnergyImage() {
        return super.getEnergyImage();
    }

    public Texture[] getEnergyImages(){
        return new Texture[]{((CustomEnergyOrb)this.energyOrbMomoi).getEnergyImage(),((CustomEnergyOrb)energyOrbMidori).getEnergyImage()};
    }
    @Override
    public void updateOrb(int totalCount) {
        this.energyOrbMomoi.updateOrb(totalCount);
        this.energyOrbMidori.updateOrb(totalCount);
    }

    public void updateOrb(int MomoiCount, int MidoriCount) {
        this.energyOrbMomoi.updateOrb(MomoiCount);
        this.energyOrbMidori.updateOrb(MidoriCount);
    }

    @Override
    public void gainEnergy(int e){
        BATwinsEnergyPanel.addEnergy(e, BATwinsEnergyPanel.EnergyType.ALL);
        this.hand.glowCheck();
    }
}
