package baModDeveloper.relic;

import YUZUMod.cards.YUZUCustomCard;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import baModDeveloper.power.BATwinsDoubleExperiencePower;
import baModDeveloper.power.BATwinsExperiencePower;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsTwins extends CustomRelic {
    public static final String ID= ModHelper.makePath("Twins");
    private static final Texture texture= TextureLoader.getTexture(ModHelper.makeImgPath("relic","Twins"));
    private static final Texture outline= TextureLoader.getTexture(ModHelper.makeImgPath("relic","Twins"));
    private static final RelicTier type=RelicTier.SPECIAL;
    public BATwinsTwins() {
        super(ID, texture,outline,type, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        //todo:加上爱丽丝的牌
        if(c instanceof YUZUCustomCard){
            AbstractPlayer abstractPlayer=AbstractDungeon.player;
            if(!c.freeToPlay() && !c.freeToPlayOnce){
                if (c.costForTurn > 0) {
                    if (AbstractDungeon.player.hasPower(BATwinsDoubleExperiencePower.POWER_ID)) {
                        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new BATwinsExperiencePower(abstractPlayer, c.costForTurn * 2)));
                    } else {
                        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new BATwinsExperiencePower(abstractPlayer, c.costForTurn)));
                    }
                } else if (c.costForTurn == -1) {
                    if (AbstractDungeon.player.hasPower(BATwinsDoubleExperiencePower.POWER_ID)) {
                        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new BATwinsExperiencePower(abstractPlayer, c.energyOnUse * 2)));
                    } else {
                        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new BATwinsExperiencePower(abstractPlayer, c.energyOnUse)));
                    }
                }
            }
        }
    }
}
