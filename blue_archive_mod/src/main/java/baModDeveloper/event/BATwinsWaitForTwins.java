package baModDeveloper.event;

import baModDeveloper.helpers.BATwinsWaitEvent;
import baModDeveloper.helpers.ModHelper;
import baModDeveloper.relic.BATwinsTwins;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;

public class BATwinsWaitForTwins extends AbstractImageEvent implements BATwinsWaitEvent {
    public static final String ID = ModHelper.makePath("WaitForTwins");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private static final String imgUrl = ModHelper.makeImgPath("event", "WaitForTwins");
    private boolean completed=false;
    private enum CurrentPhase{
        START,END
    }
    private CurrentPhase phase;
    public BATwinsWaitForTwins() {
        super(title, DESCRIPTIONS[0], imgUrl);
        completed=false;
        phase= CurrentPhase.START;

        this.imageEventText.clearAllDialogs();
        this.imageEventText.updateBodyText(DESCRIPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[0]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (this.phase){
            case START:
                this.imageEventText.clearAllDialogs();
                this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                this.imageEventText.setDialogOption(OPTIONS[1]);
                this.phase= CurrentPhase.END;
                break;
            case END:
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(((float) Settings.WIDTH / 2), ((float) Settings.HEIGHT / 2), new BATwinsTwins());
                this.completed=true;
                if (CampfireUI.hidden) {
                    AbstractRoom.waitTimer = 0.0F;
                    AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                    ((RestRoom) AbstractDungeon.getCurrRoom()).cutFireSound();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isDone() {
        return completed;
    }
}
