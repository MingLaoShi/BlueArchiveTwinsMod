package baModDeveloper.action;

import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.patch.BATwinsAbstractCardPatch;
import baModDeveloper.relic.BATwinsRubiksCube;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsPlayHandCardAction extends AbstractGameAction {
    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("LoopBreak"));
    public AbstractCard card;
    private AbstractPlayer p;
    private boolean ignoreDeath;
    private int numberOfConnections;
    private boolean blockTheOriginalEffect;

    public BATwinsPlayHandCardAction(AbstractCard card, AbstractCreature target, boolean randomTarget, boolean ignoreDeath, int numberOfConnections, boolean blockTheOriginalEffect) {
        this.card = card;
        this.p = AbstractDungeon.player;
        this.target = target;
        this.ignoreDeath = ignoreDeath;
        this.numberOfConnections = numberOfConnections;
        this.blockTheOriginalEffect = blockTheOriginalEffect;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    BATwinsPlayHandCardAction(AbstractCard card, AbstractCreature target, boolean randomTarget, boolean ignoreDeath) {
        this(card, target, randomTarget, ignoreDeath, 0, false);
    }

    public BATwinsPlayHandCardAction(AbstractCard card, AbstractCreature target) {
        this(card, target, false, true);
    }

    public BATwinsPlayHandCardAction(AbstractCard card, AbstractCreature target, boolean ignoreDeath) {
        this(card, target, false, ignoreDeath);
    }

    public BATwinsPlayHandCardAction(AbstractCard card, AbstractCreature target, int numberOfConnections) {
        this(card, target, false, true, numberOfConnections, false);
    }

    public BATwinsPlayHandCardAction(AbstractCard card, AbstractCreature target, int numberOfConnections, boolean blockTheOriginalEffect) {
        this(card, target, false, true, numberOfConnections, blockTheOriginalEffect);
    }

    @Override
    public void update() {
        if (this.numberOfConnections > 10) {
            for (int i = uiStrings.TEXT.length - 1; i >= 0; i--) {
                addToTop(new TalkAction(true, uiStrings.TEXT[i], 3.0F, 3.0F));
                if (!this.p.hand.contains(this.card)) {
                    if (this.p.hand.size() < BaseMod.MAX_HAND_SIZE) {
                        this.p.hand.addToTop(this.card);
                    } else {
                        this.p.discardPile.addToTop(this.card);
                    }
                }
            }
            this.isDone = true;
            return;
        }
        if (AbstractDungeon.player.hasRelic(BATwinsRubiksCube.ID)) {
            AbstractDungeon.player.getRelic(BATwinsRubiksCube.ID).onTrigger();
        }
        if (!card.isInAutoplay) {
            this.card.applyPowers();
            this.card.calculateCardDamage((AbstractMonster) this.target);
//            this.p.hand.removeCard(this.card);
            AbstractDungeon.getCurrRoom().souls.remove(this.card);
//            AbstractDungeon.player.limbo.group.add(card);
//            card.current_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
//            card.current_y = 0.0F * Settings.scale;
            if (AbstractDungeon.player.limbo.group.isEmpty()) {
                card.target_x = (float) Settings.WIDTH / 2.0F;
            } else {
                card.target_x = (float) Settings.WIDTH / 2.0F - 200.0F * Settings.xScale;
            }
            card.target_y = (float) Settings.HEIGHT / 2.0F;
            AbstractDungeon.player.limbo.group.add(this.card);

            BATwinsAbstractCardPatch.FieldPatch.blockTheOriginalEffect.set(card, this.blockTheOriginalEffect);
            card.isInAutoplay = true;
            if (card instanceof BATwinsModCustomCard) {
                ((BATwinsModCustomCard) card).numberOfConnections = this.numberOfConnections;
            } else {
                BATwinsAbstractCardPatch.FieldPatch.numberOfConnections.set(card, this.numberOfConnections);
            }
//            this.card.isInAutoplay=true;
//            addToTop(new ShowCardAction(this.card));
//            this.p.limbo.ad
//            if(card instanceof BATwinsModCustomCard){
//                ((BATwinsModCustomCard) card).playedByOtherCard=true;
//            }
            BATwinsAbstractCardPatch.FieldPatch.dontFadeOutInLimbo.set(this.card, true);
            if (this.target == null || this.target.isDeadOrEscaped()) {
                addToTop((AbstractGameAction) new NewQueueCardAction(card, true, false, true));
            } else {
                addToTop((AbstractGameAction) new NewQueueCardAction(card, this.target, false, true));
            }
//            addToTop(new UnlimboAction(this.card));

//            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(this.card, (AbstractMonster) this.target,card.energyOnUse,true,true),true);
        }
        this.isDone = true;
    }
}
