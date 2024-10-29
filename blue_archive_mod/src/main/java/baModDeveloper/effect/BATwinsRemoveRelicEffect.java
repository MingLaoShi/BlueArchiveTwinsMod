package baModDeveloper.effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BATwinsRemoveRelicEffect extends AbstractGameEffect {
    AbstractRelic relic;

    public BATwinsRemoveRelicEffect(AbstractRelic relic) {
        this.relic = relic;
    }

    @Override
    public void update() {
        AbstractDungeon.player.relics.remove(this.relic);
        this.isDone=true;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
