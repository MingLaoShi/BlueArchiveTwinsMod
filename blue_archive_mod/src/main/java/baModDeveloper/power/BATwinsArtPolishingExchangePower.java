package baModDeveloper.power;

import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class BATwinsArtPolishingExchangePower extends AbstractPower {
    public static final String POWER_ID= ModHelper.makePath("ArtPolishingExchangePower");
    private static final AbstractPower.PowerType TYPE=PowerType.BUFF;
    private static final PowerStrings powerStrings= CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME=powerStrings.NAME;
    private static final String[] DESCRIPTIONS=powerStrings.DESCRIPTIONS;
    private static final String IMG_84=ModHelper.makeImgPath("power","ArtPolishing84");
    private static final String IMG_32=ModHelper.makeImgPath("power","ArtPolishing32");
    private static final AbstractCreature sourcePower=new AbstractCreature() {

        @Override
        public void damage(DamageInfo damageInfo) {

        }

        @Override
        public void render(SpriteBatch spriteBatch) {

        }
    };

    public BATwinsArtPolishingExchangePower(AbstractCreature owner,int amount){
        this.name=NAME;
        this.ID=POWER_ID;
        this.type=TYPE;
        this.owner=owner;
        this.region128=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_84),0,0,84,84);
        this.region48=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_32),0,0,32,32);
        this.amount=amount;

        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description=DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(source== AbstractDungeon.player&&power.type==PowerType.DEBUFF){
            addToBot(new ApplyPowerAction(target,sourcePower,new BATwinsBurnPower(target,sourcePower,this.amount)));
        }
    }
}
