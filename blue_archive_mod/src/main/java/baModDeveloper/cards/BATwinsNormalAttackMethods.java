package baModDeveloper.cards;

import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.power.BATwinsNoramlAttackAuxiliary;
import baModDeveloper.power.BATwinsNormalAttackMethodsPower;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsNormalAttackMethods extends BATwinsModCustomCard{
    public static final String ID= ModHelper.makePath("NormalAttackMethods");
    private static final CardStrings CARD_STRINGS= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME=CARD_STRINGS.NAME;
    private static final String IMG_PATH=ModHelper.makeImgPath("cards","defaultPower");
    private static final int COST=1;
    private static final String DESCRIPTION=CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE=CardType.POWER;
    private static final CardColor COLOR= BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET=CardTarget.SELF;
    private static final CardRarity RARITY=CardRarity.UNCOMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE= BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsNormalAttackMethods() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseMagicNumber=1;
        this.magicNumber=this.baseMagicNumber;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new BATwinsNormalAttackMethodsPower(abstractPlayer,this.magicNumber)));
        if(upgraded){
            addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new BATwinsNoramlAttackAuxiliary(abstractPlayer)));
        }
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMOMOI(abstractPlayer,abstractMonster);
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            this.upgradeName();
            this.rawDescription=CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.originRawDescription=CARD_STRINGS.UPGRADE_DESCRIPTION;

            this.initializeDescription();
        }
    }
}
