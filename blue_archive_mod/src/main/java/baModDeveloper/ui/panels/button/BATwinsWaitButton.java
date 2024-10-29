package baModDeveloper.ui.panels.button;

import YUZUMod.character.YuzuCharacter;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.effect.BATwinsWaitEventEffect;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import baModDeveloper.relic.BATwinsTwins;
import baModDeveloper.relic.BATwinsYUZU;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class BATwinsWaitButton extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("WaitButton"));
    public static final String[] TEXT = uiStrings.TEXT;
    private static final String buttonImg = ModHelper.makeImgPath("UI", "waitButton");
    private static BATwinsWaitEventEffect.WaitCharacter[] characters=new BATwinsWaitEventEffect.WaitCharacter[3];
    static {
        characters[0]= BATwinsWaitEventEffect.WaitCharacter.YUZU;
        characters[1]= BATwinsWaitEventEffect.WaitCharacter.MOMOIMIDORI;
//        characters[2]= BATwinsWaitEventEffect.WaitCharacter.ALICE;
    }
    public BATwinsWaitButton(){
        this.label = TEXT[0];
        this.usable = true;
        this.img = TextureLoader.getTexture(buttonImg);
        this.description = TEXT[0];
    }
    @Override
    public void useOption() {
        ArrayList<BATwinsWaitEventEffect.WaitCharacter> list=new ArrayList<>(Arrays.asList(characters));
        //todo:加上爱丽丝
        if(AbstractDungeon.player instanceof YuzuCharacter){
            list.remove(BATwinsWaitEventEffect.WaitCharacter.YUZU);
        }else if(AbstractDungeon.player instanceof BATwinsCharacter){
            list.remove(BATwinsWaitEventEffect.WaitCharacter.MOMOIMIDORI);
        }
        if(AbstractDungeon.player.hasRelic(BATwinsYUZU.ID)){
            list.remove(BATwinsWaitEventEffect.WaitCharacter.YUZU);
        }else if(AbstractDungeon.player.hasRelic(BATwinsTwins.ID)){
            list.remove(BATwinsWaitEventEffect.WaitCharacter.MOMOIMIDORI);
        }
        if(!list.isEmpty()){
            Collections.shuffle(list,new Random(AbstractDungeon.miscRng.randomLong()));
            AbstractDungeon.topLevelEffectsQueue.add(new BATwinsWaitEventEffect(list.get(0)));
        }
    }
}
