package baModDeveloper.cards;

import baModDeveloper.action.BATwinsPlayHandCardAction;
import baModDeveloper.action.BATwinsRemoveHandCardAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class BATwinsTwoStageAttack extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("TwoStageAttack");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "TwoStageAttack");
    private static final int COST = -22;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsTwoStageAttack() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseDamage = 8;
        this.isMultiDamage = true;
//        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
//        addToBot(new DamageAllEnemiesAction(abstractPlayer,this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.LIGHTNING));
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
//        useMOMOI(abstractPlayer,abstractMonster);
    }

    @Override
    public void triggerOnConnectPlayed(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot((AbstractGameAction) new SFXAction("ATTACK_HEAVY"));
        addToBot((AbstractGameAction) new VFXAction((AbstractCreature) abstractPlayer, (AbstractGameEffect) new CleaveEffect(), 0.1F));
        addToBot(new DamageAllEnemiesAction(abstractPlayer, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));
//        addToBot(new DamageAllEnemiesAction(abstractPlayer, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.LIGHTNING));
    }

    @Override
    public void triggerOnSuperConnectPlayed(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        this.triggerOnConnectPlayed(abstractPlayer, abstractMonster);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        return this.isInAutoplay;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (AbstractDungeon.player.hand.contains(this) && c.hasTag(CardTags.STRIKE)) {
            addToTop(new BATwinsRemoveHandCardAction(this));
            addToBot(new BATwinsPlayHandCardAction(this, null, 1));
//            AbstractDungeon.player.hand.removeCard(this);
        }
    }
}
