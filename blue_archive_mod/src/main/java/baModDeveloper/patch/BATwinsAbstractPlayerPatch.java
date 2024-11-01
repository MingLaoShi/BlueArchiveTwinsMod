package baModDeveloper.patch;

import YUZUMod.character.YuzuCharacter;
import baModDeveloper.cards.BATwinsModCustomCard;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.core.BATwinsEnergyManager;
import baModDeveloper.helpers.BATwinsCharacterRelic;
import baModDeveloper.helpers.DlcUIs;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.power.BATwinsBorrowMePower;
import baModDeveloper.relic.BATwinsYUZU;
import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class BATwinsAbstractPlayerPatch {
    public static void useBATwinsEnergy(int cost, AbstractPlayer player, AbstractCard c) {
        if (c instanceof BATwinsModCustomCard) {
            if (player.hasPower(BATwinsBorrowMePower.POWER_ID)) {
                ((BATwinsEnergyManager) player.energy).use(cost, BATwinsEnergyPanel.EnergyType.SHARE);
            } else {
                ((BATwinsEnergyManager) player.energy).use(cost, ((BATwinsModCustomCard) c).modifyEnergyType);
            }
        } else {
            ((BATwinsEnergyManager) player.energy).use(cost, BATwinsEnergyPanel.EnergyType.SHARE);
        }

    }

    @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
    public static class useCardPatch {
        @SpireInstrumentPatch
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (isMethodCalled(m)) {
                        m.replace("{if(" + AbstractDungeon.class.getName() + ".player instanceof " + BATwinsCharacter.class.getName() + "){" +
                                BATwinsAbstractPlayerPatch.class.getName() + ".useBATwinsEnergy($1,this,c);" +
                                "}else{$_=$proceed($$);}}");
                    }
                }

                private boolean isMethodCalled(MethodCall m) {
                    return m.getClassName().equals(EnergyManager.class.getName()) && m.getMethodName().equals("use");
                }
            };
        }


    }

    @SpirePatch(clz = AbstractPlayer.class,method = "render",requiredModId = "BlueArchive_yuzu_Mod")
    @SuppressWarnings("unused")
    public static class renderPatch{
        @SpirePrefixPatch
        public static void prefixPatch(AbstractPlayer _instance, SpriteBatch sb){
            if(ModHelper.ENABLE_DLC&&AbstractDungeon.player.hasRelic(BATwinsYUZU.ID)&&
                    !(AbstractDungeon.player instanceof YuzuCharacter)){
                DlcUIs.criticalRatePanel.render(sb);
            }
            for(AbstractRelic r:_instance.relics){
                if(r instanceof BATwinsCharacterRelic){
                    ((BATwinsCharacterRelic) r).renderCharacter(sb);
                }
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class,method = "update")
    @SuppressWarnings("unused")
    public static class updatePatch{
        @SpirePostfixPatch
        public static void postfixPatch(AbstractPlayer _instance){
            if(ModHelper.ENABLE_DLC&&AbstractDungeon.player.hasRelic(BATwinsYUZU.ID)&&
                    !(AbstractDungeon.player instanceof YuzuCharacter)) {
                DlcUIs.criticalRatePanel.update();
            }
            for(AbstractRelic r:_instance.relics){
                if(r instanceof BATwinsCharacterRelic){
                    ((BATwinsCharacterRelic) r).updateCharacter();
                }
            }
        }
    }
}
