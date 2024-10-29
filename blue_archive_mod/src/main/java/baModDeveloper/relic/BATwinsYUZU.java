package baModDeveloper.relic;

import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class BATwinsYUZU extends CustomRelic {
    public static final String ID= ModHelper.makePath("YUZU");
    private static final Texture texture=TextureLoader.getTexture(ModHelper.makeImgPath("relic","YUZU"));
    private static final Texture outline= TextureLoader.getTexture(ModHelper.makeImgPath("relic","YUZU"));
    private static final RelicTier type=RelicTier.SPECIAL;
    private CardGroup group=new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private CardGroup usedGroup=new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public BATwinsYUZU() {
        super(ID, texture,outline,type, LandingSound.MAGICAL);

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        super.onEquip();
        CardCrawlGame.sound.play(ModHelper.makePath("yuzu"));
//        if(ModHelper.ENABLE_DLC&&this.group.isEmpty()){
//            group.addToBottom(new YUZUStrike());
//            group.addToBottom(new YUZUDefend());
//            group.addToBottom(new YUZUCamouflage());
//            group.addToBottom(new YUZUDesignShooting());
//
//        }else{
//            AbstractDungeon.effectsQueue.add(new BATwinsRemoveRelicEffect(this));
//        }

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

    public void copyGroup(){
        for(AbstractCard card:this.group.group){
            usedGroup.addToBottom(card);
        }
    }
}
