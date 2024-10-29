package baModDeveloper.ui.panels.button;

import baModDeveloper.effect.BATwinsWaitEventEffect;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.helpers.TextureLoader;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

public class BATwinsWaitButton extends AbstractCampfireOption {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModHelper.makePath("WaitButton"));
    public static final String[] TEXT = uiStrings.TEXT;
    private static final String buttonImg = ModHelper.makeImgPath("UI", "waitButton");

    public BATwinsWaitButton(){
        this.label = TEXT[0];
        this.usable = true;
        this.img = TextureLoader.getTexture(buttonImg);
        this.description = TEXT[0];
    }
    @Override
    public void useOption() {
        AbstractDungeon.topLevelEffectsQueue.add(new BATwinsWaitEventEffect(BATwinsWaitEventEffect.WaitCharacter.YUZU));
    }
}
