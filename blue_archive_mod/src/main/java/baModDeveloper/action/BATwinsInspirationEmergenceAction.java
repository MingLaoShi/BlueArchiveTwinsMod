package baModDeveloper.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BATwinsInspirationEmergenceAction extends AbstractGameAction {
    private int amount;
    public BATwinsInspirationEmergenceAction(int amount){
        this.amount=amount;
    }
    @Override
    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.5F));
        tickDuration();
        if(this.isDone){
            boolean same=true;
            if(DrawCardAction.drawnCards.size()>=2){
                for(int i=1;i< DrawCardAction.drawnCards.size();i++){
                    if(DrawCardAction.drawnCards.get(i).color!=DrawCardAction.drawnCards.get(i-1).color){
                        same=false;
                    }
                }
            }
            if(same){
                addToTop(new DrawCardAction(1));
                addToTop(new ScryAction(this.amount));
            }

        }
    }
}
