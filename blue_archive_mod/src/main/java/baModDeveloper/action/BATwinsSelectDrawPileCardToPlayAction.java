package baModDeveloper.action;

import baModDeveloper.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class BATwinsSelectDrawPileCardToPlayAction extends AbstractGameAction {
    protected ArrayList<AbstractCard> canNotSelect;
    private boolean isRandom;
    private AbstractPlayer p;
    private int numberOfConnections;
    private AbstractCard.CardColor color;
    private boolean isBlockOrigin = false;
    private boolean removePower = false;
    private UIStrings UISTRINGS = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("GridSelectTitle"));
    private CardGroup temp;

    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom) {
        this(isRandom, null);
    }

    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, AbstractMonster target, int numberOfConnections, AbstractCard.CardColor color) {
        this.isRandom = isRandom;
        this.target = target;
        this.p = AbstractDungeon.player;
        this.numberOfConnections = numberOfConnections;
        this.color = color;
        this.canNotSelect = new ArrayList<>();
        this.amount = 1;
        this.duration = Settings.ACTION_DUR_FAST;
        temp=new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        temp.group.addAll(this.p.drawPile.group);
    }

    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, AbstractMonster target, int numberOfConnections) {
        this(isRandom, target, numberOfConnections, null);
        this.amount = 1;
    }

    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, AbstractMonster target) {
        this(isRandom, target, 1);
    }

    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, int numberOfConnections) {
        this(isRandom, (AbstractMonster) null, numberOfConnections);
    }

    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, AbstractCard.CardColor color, int numberOfConnections) {
        this(isRandom, null, numberOfConnections, color);
    }

    public BATwinsSelectDrawPileCardToPlayAction(boolean isRandom, AbstractMonster target, int amount, int numberOfConnections, boolean isBlockOrigin, boolean removePower) {
        this(isRandom, target, numberOfConnections);
        this.amount = amount;
        this.isBlockOrigin = isBlockOrigin;
        this.removePower = removePower;
    }

    @Override
    public void update() {
        if (this.temp.isEmpty()) {
            if (!this.canNotSelect.isEmpty()) {
                this.temp.group.addAll(canNotSelect);
            }
            this.isDone = true;
            return;
        }
        if (this.isRandom) {
            removeCards();
            if (!this.temp.group.isEmpty()) {
                WhatTheCardDo(this.temp.getRandomCard(AbstractDungeon.cardRandomRng));
            }
            if (!this.canNotSelect.isEmpty()) {
                this.temp.group.addAll(this.canNotSelect);
            }
            this.isDone = true;
            tickDuration();
            return;
        } else if (this.duration == Settings.ACTION_DUR_FAST) {
            removeCards();
            if (this.temp.isEmpty()) {
                this.temp.group.addAll(this.canNotSelect);
                this.isDone = true;
                return;
            }
            this.amount = Math.min(this.amount, this.temp.size());
            AbstractDungeon.gridSelectScreen.open(this.temp, this.amount, String.format(UISTRINGS.TEXT[10] + UISTRINGS.TEXT[0], this.amount), false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                WhatTheCardDo(c);
            }
            if (!this.canNotSelect.isEmpty()) {
                this.temp.group.addAll(this.canNotSelect);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.isDone = true;
            return;
        }
    }

    private void removeCards() {
        for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
            if ((this.color != null && card.color != this.color) ||
                    (this.removePower && card.type == AbstractCard.CardType.POWER || card.cost == -2)) {
                this.canNotSelect.add(card);
            }
        }

        this.temp.group.removeAll(this.canNotSelect);
    }

    private void WhatTheCardDo(AbstractCard c) {
//        if (this.target == null) {
//            this.target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
//        }
        addToTop(new BATwinsPlayDrawPailCardAction(c, this.target, c.exhaust, this.numberOfConnections, this.isBlockOrigin));

    }

}
