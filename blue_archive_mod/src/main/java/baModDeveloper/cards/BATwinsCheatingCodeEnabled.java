package baModDeveloper.cards;

import baModDeveloper.action.BATwinsCheatingCodeEnabledAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BATwinsCheatingCodeEnabled extends BATwinsModCustomCard {
    public static final String ID = ModHelper.makePath("CheatingCodeEnabled");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = ModHelper.makeImgPath("cards", "CheatingCodeEnabled");
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE = BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsCheatingCodeEnabled() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.exhaust = true;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new BATwinsCheatingCodeEnabledAction(BATwinsCharacter.Enums.BATWINS_MOMOI_CARD, true));
//        if (upgraded) {
//            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new BATwinsShufflePower(abstractPlayer)));
//        }
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new BATwinsCheatingCodeEnabledAction(BATwinsCharacter.Enums.BATWINS_MIDORI_CARD, true));
//        if (upgraded) {
//            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new BATwinsShufflePower(abstractPlayer)));
//        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
//            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
//            this.originRawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
//
//            initializeDescription();
        }
    }

    @Override
    public void triggerOnHovered() {
        if (AbstractDungeon.player != null) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.color == BATwinsCharacter.getOtherColor(this.color)) {
                    c.flash(BATwinsCharacter.getColorWithCardColor(c.color));
                }
            }
        }

    }
}
