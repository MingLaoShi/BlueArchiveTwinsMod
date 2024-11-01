//package baModDeveloper.patch.alicePatchs;
//
//import BlueArchive_Aris.actions.JobChangeAction;
//import BlueArchive_Aris.characters.Aris;
//import BlueArchive_Aris.powers.JobPower;
//import baModDeveloper.helpers.ModHelper;
//import baModDeveloper.relic.BATwinsAlice;
//import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
//import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.powers.AbstractPower;
//
//public class JobChangeActionPatch {
//    @SpirePatch(clz = JobChangeAction.class,method = "update",requiredModId = "BlueArchive_Aris")
//    public static class updatePatch{
//        @SpireInsertPatch(rloc = 15,localvars = {"p"})
//        public static void insertPatch(JobChangeAction _instance, AbstractPower p){
//            if(ModHelper.ENABLE_DLC&&!(AbstractDungeon.player instanceof Aris)&&AbstractDungeon.player.hasRelic(BATwinsAlice.ID)){
//                if(p!=null){
//                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(((JobPower)p).equip,AbstractDungeon.player.discardPile));
//                }
//            }
//        }
//    }
//}
