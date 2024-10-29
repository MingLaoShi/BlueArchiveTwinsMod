package baModDeveloper.relic;

import BlueArchive_Aris.cards.*;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import basemod.abstracts.CustomRelic;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class BATwinsAlice extends CustomRelic {
    public static final String ID= ModHelper.makePath("Alice");
    private static final Texture texture= TextureLoader.getTexture(ModHelper.makeImgPath("relic","Alice"));
    private static final Texture outline= TextureLoader.getTexture(ModHelper.makeImgPath("relic","Alice"));
    private static final RelicTier type=RelicTier.SPECIAL;
    public static ArrayList<AbstractDynamicCard> cards=new ArrayList<>();
    static {
        if(Loader.isModLoaded("BlueArchive_Aris")){
            cards.add(new SuperNova());
            cards.add(new WoodenStick());
            cards.add(new Mask());
            cards.add(new Rogue());
            cards.add(new IdolRibbon());
            cards.add(new WizardHat());
            cards.add(new DevilYuuka());
        }
    }
    private CardGroup group;
    public BATwinsAlice() {
        super(ID, texture,outline,type, LandingSound.MAGICAL);
        this.group=new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for(AbstractCard card:cards){
            group.addToBottom(card);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        AbstractCard card=group.getRandomCard(AbstractDungeon.miscRng);
        card.freeToPlayOnce=true;
        CardModifierManager.addModifier(card,new ExhaustMod());
        CardModifierManager.addModifier(card,new EtherealMod());
        addToBot(new MakeTempCardInHandAction(card,1));

    }
}
