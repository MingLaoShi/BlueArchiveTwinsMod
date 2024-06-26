package baModDeveloper.cards;

import baModDeveloper.action.BATwinsUniversalBulletAction;
import baModDeveloper.cards.bullets.BATwinsCustomBulletCard;
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

public class BATwinsUniversalBullet extends BATwinsCustomBulletCard {
    public static final String ID = ModHelper.makePath("UniversalBullet");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "UniversalBullet");
    private static final int COST = 0;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsUniversalBullet() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseDamage = this.damage = 3;
        this.baseMagicNumber = this.magicNumber = 1;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeDamage(3);
    }

    @Override
    public boolean canUpgrade() {
        return !this.upgraded;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new BATwinsUniversalBulletAction(this.color, new BATwinsUniversalBullet()));
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        useMOMOI(abstractPlayer, abstractMonster);
    }

    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = this.timesUpgraded + 1 + "X" + NAME;
        this.initializeTitle();
    }
}
