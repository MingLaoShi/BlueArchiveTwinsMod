package baModDeveloper.cards;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsEndCombo extends BATwinsModCustomCard{
    public static final String ID= ModHelper.makePath("EndCombo");
    private static final CardStrings CARD_STRINGS= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME=CARD_STRINGS.NAME;
    private static final String IMG_PATH=ModHelper.makeImgPath("cards","defaultAttack");
    private static final int COST=1;
    private static final String DESCRIPTION=CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE=CardType.ATTACK;
    private static final CardColor COLOR= BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET=CardTarget.ENEMY;
    private static final CardRarity RARITY=CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE= BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsEndCombo() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseDamage=8;
        this.baseBlock=10;
        this.baseMagicNumber=12;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage), AbstractGameAction.AttackEffect.LIGHTNING));
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMOMOI(abstractPlayer,abstractMonster);
    }

    @Override
    public void triggerOnConnectePlayed(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.block), AbstractGameAction.AttackEffect.LIGHTNING));
    }

    @Override
    public void triggerOnSuperConnectPlayed(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.magicNumber), AbstractGameAction.AttackEffect.LIGHTNING));
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeBlock(4);
            this.upgradeMagicNumber(5);
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int realBaseDamage=this.baseDamage;
        int realDamage=this.damage;
        this.baseDamage=this.baseBlock;
        super.applyPowers();
        this.block=this.damage;
        this.baseDamage=this.baseMagicNumber;
        super.applyPowers();
        this.magicNumber=this.damage;
        this.baseDamage=realBaseDamage;
        this.damage=realDamage;
        this.isDamageModified= this.baseDamage != this.damage;
        this.isBlockModified=this.baseBlock!=this.block;
        this.isMagicNumberModified=this.baseMagicNumber!=this.magicNumber;
    }

    @Override
    protected void applyPowersToBlock() {
        return;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        int realBaseDamage=this.baseDamage;
        int realDamage=this.damage;
        this.baseDamage=this.baseBlock;
        super.calculateCardDamage(mo);
        this.block=this.damage;
        this.baseDamage=this.baseMagicNumber;
        super.calculateCardDamage(mo);
        this.magicNumber=this.damage;
        this.baseDamage=realBaseDamage;
        this.damage=realDamage;
        this.isDamageModified= this.baseDamage != this.damage;
        this.isBlockModified=this.baseBlock!=this.block;
        this.isMagicNumberModified=this.baseMagicNumber!=this.magicNumber;
    }
}
