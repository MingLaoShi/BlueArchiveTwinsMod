package baModDeveloper.relic;

import YUZUMod.cards.YUZUCamouflage;
import YUZUMod.cards.YUZUDefend;
import YUZUMod.cards.YUZUDesignShooting;
import YUZUMod.cards.YUZUStrike;
import YUZUMod.character.YuzuCharacter;
import baModDeveloper.effect.BATwinsRemoveRelicEffect;
import baModDeveloper.helpers.BATwinsCharacterRelic;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BATwinsYUZU extends CustomRelic implements BATwinsCharacterRelic {
    public static final String ID= ModHelper.makePath("YUZU");
    private static final Texture texture=TextureLoader.getTexture(ModHelper.makeImgPath("relic","YUZU"));
    private static final Texture outline= TextureLoader.getTexture(ModHelper.makeImgPath("relic","YUZU"));
    private static final RelicTier type=RelicTier.SPECIAL;
//    private CardGroup group=new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
//    private CardGroup usedGroup=new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private AbstractPlayer player;
    private float offset;
    public BATwinsYUZU() {
        super(ID, texture,outline,type, LandingSound.MAGICAL);
        if(ModHelper.ENABLE_DLC&&ModHelper.isYUZULoaded()){
            this.player=new YuzuCharacter("");
            this.player.masterDeck.group.clear();
            this.player.masterDeck.addToBottom(new YUZUStrike());
            this.player.masterDeck.addToBottom(new YUZUDefend());
            this.player.masterDeck.addToBottom(new YUZUStrike());
            this.player.masterDeck.addToBottom(new YUZUDefend());
            this.player.masterDeck.addToBottom(new YUZUCamouflage());
            this.player.masterDeck.addToBottom(new YUZUDesignShooting());
            this.player.hideHealthBar();
            this.offset=300.0F* Settings.xScale;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        super.onEquip();
        CardCrawlGame.sound.play(ModHelper.makePath("yuzu"));
        if(!ModHelper.ENABLE_DLC){
            AbstractDungeon.effectsQueue.add(new BATwinsRemoveRelicEffect(this));
        }
    }

    @Override
    public void atTurnStartPostDraw() {
//        super.atTurnStartPostDraw();
//        if(this.usedGroup.isEmpty()){
//            this.copyGroup();
//        }
//        AbstractCard card=this.usedGroup.getRandomCard(true);
//        this.usedGroup.removeCard(card);
//        addToBot(new BATwinsPlayTempCardAction(card,1,null));
    }

//    public void copyGroup(){
//        for(AbstractCard card:this.group.group){
//            usedGroup.addToBottom(card);
//        }
//    }

    @Override
    public void updateCharacter() {
        if(ModHelper.isYUZULoaded()){
            if(AbstractDungeon.isPlayerInDungeon()&&AbstractDungeon.player!=null&&AbstractDungeon.getCurrRoom().phase== AbstractRoom.RoomPhase.COMBAT) {
                this.player.update();
                this.player.drawX=AbstractDungeon.player.drawX+offset;
                this.player.hb.moveX(this.player.drawX);
                this.player.flipHorizontal=AbstractDungeon.player.flipHorizontal;
            }
        }
    }

    @Override
    public void renderCharacter(SpriteBatch sb) {
        if(ModHelper.isYUZULoaded()){
            if(AbstractDungeon.isPlayerInDungeon()&&AbstractDungeon.player!=null&&AbstractDungeon.getCurrRoom().phase== AbstractRoom.RoomPhase.COMBAT) {
                this.player.renderPlayerImage(sb);
            }
        }
    }
}
