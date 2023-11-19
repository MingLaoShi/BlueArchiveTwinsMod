package baModDeveloper.power;

import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BATwinsNoramlAttackAuxiliary extends AbstractPower {
    public static final String POWER_ID= ModHelper.makePath("NoramlAttackAuxiliaryPower");
    private static final AbstractPower.PowerType TYPE=PowerType.BUFF;
    private static final PowerStrings powerStrings= CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME=powerStrings.NAME;
    private static final String[] DESCRIPTIONS=powerStrings.DESCRIPTIONS;
    private static final String IMG_84=ModHelper.makeImgPath("power","NoramlAttackAuxiliary84");
    private static final String IMG_32=ModHelper.makeImgPath("power","NoramlAttackAuxiliary32");

    public BATwinsNoramlAttackAuxiliary(AbstractCreature owner){
        this.name=NAME;
        this.ID=POWER_ID;
        this.owner=owner;
        this.type=TYPE;
        this.amount=-1;
        this.region128=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_84),0,0,84,84);
        this.region48=new TextureAtlas.AtlasRegion(ImageMaster.loadImage(IMG_32),0,0,32,32);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description=DESCRIPTIONS[0];
    }
}
