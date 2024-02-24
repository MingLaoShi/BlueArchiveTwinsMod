package baModDeveloper.cards;

import baModDeveloper.action.BATwinsDisCardByColorAction;
import baModDeveloper.action.BATwinsDisOtherCardByColorAction;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.power.BATwinsExperiencePower;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.function.Consumer;

public class BATwinsItsSoPainful extends BATwinsModCustomCard{
    public static final String ID= ModHelper.makePath("ItsSoPainful");
    private static final CardStrings CARD_STRINGS= CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME=CARD_STRINGS.NAME;
    private static final String IMG_PATH=ModHelper.makeImgPath("cards","defaultAttack");
    private static final int COST=2;
    private static final String DESCRIPTION=CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE=CardType.ATTACK;
    private static final CardColor COLOR= BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
    private static final CardTarget TARGET=CardTarget.ENEMY;
    private static final CardRarity RARITY=CardRarity.COMMON;
    private static final BATwinsEnergyPanel.EnergyType ENERGYTYPE= BATwinsEnergyPanel.EnergyType.MOMOI;

    public BATwinsItsSoPainful() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, ENERGYTYPE);
        this.baseDamage=10;
        this.damage=this.baseDamage;
        this.baseMagicNumber=2;
        this.magicNumber=this.baseMagicNumber;
    }

    @Override
    public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        Consumer<Integer> callback=integer -> {
            if(integer==0){
                if(AbstractDungeon.player.hasPower(BATwinsExperiencePower.POWER_ID)){
                    BATwinsExperiencePower power = (BATwinsExperiencePower) AbstractDungeon.player.getPower(BATwinsExperiencePower.POWER_ID);
                    BATwinsItsSoPainful.this.baseDamage+=power.LEVEL;
                }
            }
        };
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage), AbstractGameAction.AttackEffect.LIGHTNING));
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage), AbstractGameAction.AttackEffect.LIGHTNING));
        addToBot(new BATwinsDisOtherCardByColorAction(BATwinsCharacter.Enums.BATWINS_MOMOI_CARD,callback));
    }

    @Override
    public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        Consumer<Integer> callback=integer -> {
            if(integer==0){
                if(AbstractDungeon.player.hasPower(BATwinsExperiencePower.POWER_ID)){
                    BATwinsExperiencePower power = (BATwinsExperiencePower) AbstractDungeon.player.getPower(BATwinsExperiencePower.POWER_ID);
                    BATwinsItsSoPainful.this.baseDamage+=power.LEVEL;
                }
            }
        };
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage), AbstractGameAction.AttackEffect.LIGHTNING));
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,this.damage), AbstractGameAction.AttackEffect.LIGHTNING));
        addToBot(new BATwinsDisOtherCardByColorAction(BATwinsCharacter.Enums.BATWINS_MIDORI_CARD,callback));
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void triggerOnHovered() {
        if(AbstractDungeon.player!=null){
            for(AbstractCard c:AbstractDungeon.player.hand.group){
                if(c.color!=this.color){
                    c.flash(BATwinsCharacter.getColorWithCardColor(c.color));
                }
            }
        }
    }
}
