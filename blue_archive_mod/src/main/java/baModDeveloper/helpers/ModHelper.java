package baModDeveloper.helpers;

import YUZUMod.character.YuzuCharacter;
import baModDeveloper.BATwinsMod;
import baModDeveloper.character.BATwinsCharacter;
import baModDeveloper.localization.SoraItemStrings;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ModHelper {
    public static final Logger logger = LogManager.getLogger(BATwinsMod.class.getName());
    public static String MOMOI_FLODER = "momoi";
    public static String MIDORI_FLODER = "midori";
    public static Gson gson=new Gson();
    public static String makePath(String id) {
        return "BATwinsMod:" + id;
    }

    public static String makeImgPath(String floder, String imgName) {
        return "baModResources/img/" + floder + "/" + imgName + ".png";
    }

    public static String makeGifPath(String floder, String character, String imgName) {
        return String.format("baModResources/img/%s/%s/%s.gif", floder, character, imgName);
    }

    public static <T> T checkBATwinPlayer(T player) {
        if (player instanceof BATwinsCharacter) {
            return (T) player;
        } else {
            return player;
        }
    }

    public static String getModID() {
        return "BATwinsMod";
    }

    public static AbstractCard returnTrulyRandomCardInCombatByColor(AbstractCard.CardColor color) {
        ArrayList<AbstractCard> list = new ArrayList<>();
        Iterator var1 = AbstractDungeon.srcCommonCardPool.group.iterator();

        AbstractCard c;
        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (!c.hasTag(AbstractCard.CardTags.HEALING) && c.color == color) {
                list.add(c);
                UnlockTracker.markCardAsSeen(c.cardID);
            }
        }
        var1 = AbstractDungeon.srcUncommonCardPool.group.iterator();
        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (!c.hasTag(AbstractCard.CardTags.HEALING) && c.color == color) {
                list.add(c);
                UnlockTracker.markCardAsSeen(c.cardID);
            }
        }
        var1 = AbstractDungeon.srcRareCardPool.group.iterator();
        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (!c.hasTag(AbstractCard.CardTags.HEALING) && c.color == color) {
                list.add(c);
                UnlockTracker.markCardAsSeen(c.cardID);
            }
        }
        if(list.isEmpty()){
           return null;
        }
        return list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
    }

    public static String makeAudioPath(String filename, String fileType) {
        return "baModResources/sound/" + filename + "." + fileType;
    }

    public static String makeAudioPath(String filename) {
        return makeAudioPath(filename, "ogg");
    }

    public static Logger getLogger() {
        return logger;
    }

    public static Color getBATwinsOtherColor(Color color){
        if(color.equals(BATwinsMod.MOMOIColor)){
            return BATwinsMod.MIDORIColor;
        }else{
            return BATwinsMod.MOMOIColor;
        }
    }

    public static String makeFilePath(String floder,String fileName,String fileType){
        return "baModResources/img/" + floder + "/" + fileName + "."+fileType;
    }

    public static Map<String, SoraItemStrings> soraItemStringsMap=new HashMap<>();

    public static boolean ENABLE_DLC=false;

    public static boolean isEnableDlc(){
        ENABLE_DLC=Loader.isModLoaded("BlueArchive_yuzu_Mod")&&Loader.isModLoaded("BlueArchive_Aris");
        //todo:记得删掉
        ENABLE_DLC=true;
        return ENABLE_DLC;
    }

    public static boolean isDlcCharacter(AbstractPlayer player){
        return player instanceof YuzuCharacter||player instanceof BATwinsCharacter;
    }
}
