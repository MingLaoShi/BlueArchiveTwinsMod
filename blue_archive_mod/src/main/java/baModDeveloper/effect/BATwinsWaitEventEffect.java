package baModDeveloper.effect;

import baModDeveloper.event.BATwinsWaitForTwins;
import baModDeveloper.event.BATwinsWaitForYUZU;
import baModDeveloper.helpers.BATwinsWaitEvent;
import baModDeveloper.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BATwinsWaitEventEffect extends AbstractGameEffect {
    public enum WaitCharacter {
        YUZU,
        MOMOIMIDORI,
        ALICE
    }

    AbstractImageEvent event;
    EventRoom room = new EventRoom();

    public BATwinsWaitEventEffect(WaitCharacter character) {
        this.initEvent(character);

    }

    private void initEvent(WaitCharacter character) {
        switch (character) {
            case YUZU:
                event = new BATwinsWaitForYUZU();
                break;
            case MOMOIMIDORI:
                event = new BATwinsWaitForTwins();
                break;
            default:
                ModHelper.logger.error("Unknown characterEvent!");
        }

        room.phase = AbstractRoom.RoomPhase.EVENT;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        room.event = event;
    }

    @Override
    public void update() {
//        this.event.update();
//        if (this.event.waitTimer == 0.0F &&!this.event.started) {
//            this.event.reopen();
//        }

        this.room.update();
        if (this.event instanceof BATwinsWaitEvent) {
            this.isDone = ((BATwinsWaitEvent) this.event).isDone();
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
//        this.event.renderRoomEventPanel(spriteBatch);
//        this.event.renderText(spriteBatch);
        this.room.renderEventTexts(spriteBatch);
//        this.event.renderRoomEventPanel(spriteBatch);
//        this.event.renderText(spriteBatch);
//        this.event.roomEventText.render(spriteBatch);
//        this.event.imageEventText.render(spriteBatch);
    }

    @Override
    public void dispose() {

    }
}
