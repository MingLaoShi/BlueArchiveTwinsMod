package baModDeveloper.power;

import baModDeveloper.action.BATwinsMakeTempCardInHandAction;
import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BATwinsIntegratingAndIntegratingPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("IntegratingAndIntegratingPower");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String IMG_84 = ModHelper.makeImgPath("power", "IntegratingAndIntegrating84");
    private static final String IMG_32 = ModHelper.makeImgPath("power", "IntegratingAndIntegrating32");
    private static TextureAtlas.AtlasRegion REGION128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_84), 0, 0, 84, 84);
    private static TextureAtlas.AtlasRegion REGION48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_32), 0, 0, 32, 32);

    public BATwinsIntegratingAndIntegratingPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = TYPE;
        this.owner = owner;
        this.region128 = REGION128;
        this.region48 = REGION48;
        this.amount = amount;

        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

//    @Override
//    public void onAfterCardPlayed(AbstractCard usedCard) {
//        if(usedCard instanceof BATwinsModCustomCard){
//            if(((BATwinsModCustomCard) usedCard).exchanged()){
//                this.flash();
//                for(int i=0;i<this.amount;i++){
//                    BATwinsModCustomCard card= (BATwinsModCustomCard) usedCard.makeSameInstanceOf();
//                    card.conversionColor();
//                    addToBot(new BATwinsMakeTempCardInHandAction(card,true,true,true,true,false));
//                }
//            }
//        }
//    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card instanceof BATwinsModCustomCard && card.costForTurn > 0) {
            if (((BATwinsModCustomCard) card).exchanged()) {
                this.flash();
                for (int i = 0; i < this.amount; i++) {
                    BATwinsModCustomCard c = (BATwinsModCustomCard) card.makeStatEquivalentCopy();
                    c.exhaust = card.exhaust;
                    c.isEthereal = card.isEthereal;
                    c.conversionColor();
                    //如果有技艺大师buff则升级
                    if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(BATwinsMasterCraftsmanshipPower.POWER_ID)) {
                        c.upgrade();
                    }
                    addToBot(new BATwinsMakeTempCardInHandAction(c, true, true, true, true, false));
                }
            }
        }
    }
}
