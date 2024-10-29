package baModDeveloper.patch.yuzuPatchs;

import YUZUMod.action.YUZUApplyCriticalRateAction;
import baModDeveloper.helpers.DlcUIs;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.relic.BATwinsYUZU;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class YUZUApplyCriticalRateActionPatch {
    @SpirePatch(clz = YUZUApplyCriticalRateAction.class,method = "update",requiredModId = "BlueArchive_yuzu_Mod")
    public static class updatePatch{
        @SpirePostfixPatch
        public static void postfixPatch(YUZUApplyCriticalRateAction _instance){
            if(ModHelper.ENABLE_DLC&& AbstractDungeon.player.hasRelic(BATwinsYUZU.ID)){
                DlcUIs.criticalRatePanel.increase(_instance.amount);
            }
        }
    }
}
