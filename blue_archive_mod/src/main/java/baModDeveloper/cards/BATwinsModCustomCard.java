package baModDeveloper.cards;

import baModDeveloper.BATwinsMod;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.power.BATwinsDoubleExperiencePower;
import baModDeveloper.power.BATwinsExperiencePower;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class BATwinsModCustomCard extends CustomCard {
    public BATwinsEnergyPanel.EnergyType modifyEnergyType;
    public CardColor OriginalColor;
    public boolean playBackOriginalColor=false;
    public ArrayList<Color> GradientColor=new ArrayList<>();
    private int startColor=0;
    private float gradientDuration=0.0F;

    public int numberOfConnections=0;
    public boolean blockTheOriginalEffect=false;
//    public boolean playedByOtherCard=false;
    public BATwinsModCustomCard(String ID, String NAME, String IMG_PATH, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET, BATwinsEnergyPanel.EnergyType ENERGYTYPE) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.OriginalColor= COLOR;
        this.modifyEnergyType=ENERGYTYPE;
        this.GradientColor.add(BATwinsMod.MOMOIColor);
        this.GradientColor.add(BATwinsMod.MIDORIColor);
    }

    @Override
    public boolean hasEnoughEnergy() {
        if (AbstractDungeon.actionManager.turnHasEnded) {
            this.cantUseMessage = TEXT[9];
        } else {
            Iterator var1 = AbstractDungeon.player.powers.iterator();

            AbstractPower p;
            do {
                if (!var1.hasNext()) {
                    if (AbstractDungeon.player.hasPower("Entangled") && this.type == AbstractCard.CardType.ATTACK) {
                        this.cantUseMessage = TEXT[10];
                        return false;
                    }

                    var1 = AbstractDungeon.player.relics.iterator();

                    AbstractRelic r;
                    do {
                        if (!var1.hasNext()) {
                            var1 = AbstractDungeon.player.blights.iterator();

                            AbstractBlight b;
                            do {
                                if (!var1.hasNext()) {
                                    var1 = AbstractDungeon.player.hand.group.iterator();

                                    AbstractCard c;
                                    do {
                                        if (!var1.hasNext()) {
                                            if(AbstractDungeon.overlayMenu.energyPanel instanceof BATwinsEnergyPanel){
                                                if(this.freeToPlay()||this.isInAutoplay){
                                                    return true;
                                                }
                                                boolean hasEnoughEnergy=true;
                                                if(this.modifyEnergyType== BATwinsEnergyPanel.EnergyType.MOMOI){
                                                    if(BATwinsEnergyPanel.MomoiCount+BATwinsEnergyPanel.MidoriCount/2<this.costForTurn){
                                                        this.cantUseMessage=TEXT[11];
                                                        return false;
                                                    }
                                                } else if (this.modifyEnergyType== BATwinsEnergyPanel.EnergyType.MIDORI) {
                                                    if(BATwinsEnergyPanel.MidoriCount+BATwinsEnergyPanel.MomoiCount/2<this.costForTurn){
                                                        this.cantUseMessage=TEXT[11];
                                                        return false;
                                                    }
                                                } else if (this.modifyEnergyType== BATwinsEnergyPanel.EnergyType.SHARE) {
                                                    if(BATwinsEnergyPanel.MomoiCount+BATwinsEnergyPanel.MidoriCount<this.costForTurn){
                                                        this.cantUseMessage=TEXT[11];
                                                        return false;
                                                    }

                                                }
                                            }else{
                                                if (EnergyPanel.totalCount < this.costForTurn && !this.freeToPlay() && !this.isInAutoplay) {
                                                    this.cantUseMessage = TEXT[11];
                                                    return false;
                                                }
                                            }
                                            return true;
                                        }

                                        c = (AbstractCard)var1.next();
                                    } while(c.canPlay(this));

                                    return false;
                                }

                                b = (AbstractBlight)var1.next();
                            } while(b.canPlay(this));

                            return false;
                        }

                        r = (AbstractRelic)var1.next();
                    } while(r.canPlay(this));

                    return false;
                }

                p = (AbstractPower)var1.next();
            } while(p.canPlayCard(this));

            this.cantUseMessage = TEXT[13];
        }
        return false;
    }

    public CardColor getCardColor(){
        return this.color;
    }
    public void conversionColor(){
        if(this.color==BATwinsCharacter.Enums.BATWINS_MOMOI_CARD){
            this.color=BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
            if(this.modifyEnergyType!= BATwinsEnergyPanel.EnergyType.SHARE)
                this.modifyEnergyType= BATwinsEnergyPanel.EnergyType.MIDORI;
        }else{
            this.color=BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
            if(this.modifyEnergyType!= BATwinsEnergyPanel.EnergyType.SHARE)
                this.modifyEnergyType= BATwinsEnergyPanel.EnergyType.MOMOI;
        }
        this.rawDescription=replaceDescription(this.rawDescription);
        super.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy(){
        BATwinsModCustomCard _instance= (BATwinsModCustomCard) super.makeCopy();
        _instance.modifyEnergyType=this.modifyEnergyType;
        _instance.OriginalColor=this.OriginalColor;
        return _instance;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        BATwinsModCustomCard temp= (BATwinsModCustomCard) super.makeStatEquivalentCopy();
        temp.color=this.color;
        temp.modifyEnergyType=this.modifyEnergyType;
        temp.OriginalColor=this.OriginalColor;
        temp.playBackOriginalColor=this.playBackOriginalColor;
        temp.GradientColor.addAll(this.GradientColor);
        return temp;
    }

    @Override
    public void initializeDescription() {
        if(this.OriginalColor!=null&&this.OriginalColor!=this.color){
            this.rawDescription=replaceDescription(this.rawDescription);
        }
        super.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(!this.blockTheOriginalEffect){
            if (this.color==BATwinsCharacter.Enums.BATWINS_MOMOI_CARD){
                useMOMOI(abstractPlayer,abstractMonster);
            } else if (this.color==BATwinsCharacter.Enums.BATWINS_MIDORI_CARD) {
                useMIDORI(abstractPlayer,abstractMonster);
            }
        }

        if(this.isInAutoplay){
            if(this.numberOfConnections<1){
                this.numberOfConnections=1;
            }
            triggerOnConnectePlayed(abstractPlayer,abstractMonster);
            if(this.numberOfConnections>1){
                triggerOnSuperConnectPlayed(abstractPlayer,abstractMonster);
            }
//            this.playedByOtherCard=false;
        }
        this.numberOfConnections=0;
        this.blockTheOriginalEffect=false;
        if(!this.freeToPlay()&&!this.freeToPlayOnce){
            if(this.costForTurn>0) {
                if(AbstractDungeon.player.hasPower(BATwinsDoubleExperiencePower.POWER_ID)){
                    addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new BATwinsExperiencePower(abstractPlayer, this.costForTurn*2)));
                }else{
                    addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new BATwinsExperiencePower(abstractPlayer, this.costForTurn)));
                }
            }
            else if(this.costForTurn==-1){
                if(AbstractDungeon.player.hasPower(BATwinsDoubleExperiencePower.POWER_ID)){
                    addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new BATwinsExperiencePower(abstractPlayer,this.energyOnUse*2)));
                }else{
                    addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new BATwinsExperiencePower(abstractPlayer,this.energyOnUse)));
                }
            }
        }
        if(this.playBackOriginalColor&&this.exchanged()){
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    BATwinsModCustomCard.this.conversionColor();
                    this.isDone=true;
                    tickDuration();
                }
            });
        }
    }


    abstract public void useMOMOI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster);
    abstract public void useMIDORI(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster);
    public String replaceDescription(String description){
        description=exchangeStr(description,"中毒","batwinsmod:灼伤");
        description=exchangeStr(description,"[TE]","[LE]");
        description=exchangeStr(description,"batwinsmod:桃牌","batwinsmod:绿牌");
        return description;
    }
    private String exchangeStr(String description,String str1,String str2){
        String temp="#######";
        description=description.replace(str1,temp);
        description=description.replace(str2,str1);
        description=description.replace(temp,str2);
        return description;
    }
    public static class BATwinsAttackEffect{
        @SpireEnum
        public static AbstractGameAction.AttackEffect BATwinsShooting;
    }

    public static class BATwinsCardTags{
        @SpireEnum
        public static CardTags Adventure;
    }


    @Override
    public void render(SpriteBatch sb) {
        if(this.modifyEnergyType== BATwinsEnergyPanel.EnergyType.SHARE){
            if(gradientDuration>Settings.MAX_FPS){
                startColor=(startColor+1)%GradientColor.size();
                this.gradientDuration=0.0F;
            }
            this.glowColor=this.GradientColor.get(startColor);
            this.gradientDuration+=Settings.ACTION_DUR_MED;
        }
        super.render(sb);
    }

    public void triggerOnEnergyUse(int amount, BATwinsEnergyPanel.EnergyType energyType){
        return;
    }
    public void triggerOnEnergyExhausted(BATwinsEnergyPanel.EnergyType energyType){
        return;
    }

    public void triggerOnConnectePlayed(AbstractPlayer abstractPlayer,AbstractMonster abstractMonster){
        return;
    }
    public void triggerOnSuperConnectPlayed(AbstractPlayer abstractPlayer,AbstractMonster abstractMonster){return;}
    public boolean exchanged(){
        return this.color!=this.OriginalColor;
    }
}
