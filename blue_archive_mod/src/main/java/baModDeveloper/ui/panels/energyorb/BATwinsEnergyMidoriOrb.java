package baModDeveloper.ui.panels.energyorb;

import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class BATwinsEnergyMidoriOrb extends CustomEnergyOrb {

    private static final int ORB_W = 128;
    private static final float ORB_IMG_SCALE = 1.15f * Settings.scale;
    private static float JEWELRY_DEV_X = 20.0F;
    private static float JEWELRY_DEV_Y = -90.0F;
    private Texture orbMark;

    public BATwinsEnergyMidoriOrb(String[] orbTexturePaths, String orbVfxPath, float[] layerSpeeds) {
        super(orbTexturePaths, orbVfxPath, layerSpeeds);
    }

    public BATwinsEnergyMidoriOrb(String[] orbTexturePaths, String orbVfxPath, float[] layerSpeeds, String orbMarkPath) {
        super(orbTexturePaths, orbVfxPath, layerSpeeds);
        this.orbMark = ImageMaster.loadImage(orbMarkPath);
    }

    @Override
    public void updateOrb(int eneryCount) {
        super.updateOrb(eneryCount);
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        sb.setColor(Color.WHITE);
        if (BATwinsEnergyPanel.selectedEnergySlot == BATwinsEnergyPanel.EnergyType.MIDORI) {
            sb.draw(this.orbMark, current_x - 100.0F * Settings.scale + JEWELRY_DEV_X * Settings.scale, current_y + 100.0F * Settings.scale + JEWELRY_DEV_Y * Settings.scale, 70.0F * Settings.scale, 115.0F * Settings.scale);
        }
        if (enabled) {
            for (int i = 0; i < energyLayers.length; ++i) {
                sb.draw(energyLayers[i],
                        current_x - 64.0f - 100.0F * Settings.scale, current_y - 64.0f + 100.0F * Settings.scale,
                        64.0f, 64.0f,
                        128.0f, 128.0f,
                        ORB_IMG_SCALE, ORB_IMG_SCALE,
                        angles[i],
                        0, 0,
                        ORB_W, ORB_W,
                        false, false);
            }
        } else {
            for (int i = 0; i < noEnergyLayers.length; ++i) {
                sb.draw(noEnergyLayers[i],
                        current_x - 64.0f - 100.0F * Settings.scale, current_y - 64.0f + 100.0F * Settings.scale,
                        64.0f, 64.0f,
                        128.0f, 128.0f,
                        ORB_IMG_SCALE, ORB_IMG_SCALE,
                        angles[i],
                        0, 0,
                        ORB_W, ORB_W,
                        false, false);
            }
        }

        sb.draw(baseLayer,
                current_x - 64.0f - 100.0F * Settings.scale, current_y - 64.0f + 100.0F * Settings.scale,
                64.0f, 64.0f,
                128.0f, 128.0f,
                ORB_IMG_SCALE, ORB_IMG_SCALE,
                0.0f,
                0, 0,
                ORB_W, ORB_W,
                false, false);
    }
}
