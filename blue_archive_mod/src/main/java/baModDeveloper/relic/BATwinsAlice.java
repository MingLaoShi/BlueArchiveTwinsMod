package baModDeveloper.relic;

import BlueArchive_Aris.cards.AbstractDynamicCard;
import BlueArchive_Aris.characters.Aris;
import baModDeveloper.helpers.BATwinsCharacterRelic;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

public class BATwinsAlice extends CustomRelic implements BATwinsCharacterRelic {
    public static final String ID = ModHelper.makePath("Alice");
    private static final Texture texture = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "Alice"));
    private static final Texture outline = TextureLoader.getTexture(ModHelper.makeImgPath("relic", "Alice"));
    private static final RelicTier type = RelicTier.SPECIAL;
    public static ArrayList<AbstractDynamicCard> cards = new ArrayList<>();
    private AbstractPlayer player;
    private float offset = -300.0F * Settings.xScale;

    //    static {
//        if(Loader.isModLoaded("BlueArchive_Aris")){
//            cards.add(new SuperNova());
//            cards.add(new WoodenStick());
//            cards.add(new Mask());
//            cards.add(new Rogue());
//            cards.add(new IdolRibbon());
//            cards.add(new WizardHat());
//            cards.add(new DevilYuuka());
//        }
//    }
//    private CardGroup group;
    public BATwinsAlice() {
        super(ID, texture, outline, type, LandingSound.MAGICAL);
        if (ModHelper.isAliceLoaded()) {
            this.player = new Aris("", Aris.Enums.ARIS);
            this.player.hideHealthBar();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }


    @Override
    public void updateCharacter() {
        if (ModHelper.isAliceLoaded()) {
            if (AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.player != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                this.player.update();
                this.player.drawX = AbstractDungeon.player.drawX + this.offset;
                this.player.hb.moveX(this.player.drawX);
                this.player.flipHorizontal=AbstractDungeon.player.flipHorizontal;
            }
        }
    }

    @Override
    public void renderCharacter(SpriteBatch sb) {
        if (ModHelper.isAliceLoaded()) {
            if (AbstractDungeon.isPlayerInDungeon() && AbstractDungeon.player != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                this.player.renderPlayerImage(sb);
            }
        }
    }
}
