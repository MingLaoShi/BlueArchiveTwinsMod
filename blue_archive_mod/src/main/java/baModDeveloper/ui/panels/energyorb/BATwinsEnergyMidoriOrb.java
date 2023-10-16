package baModDeveloper.ui.panels.energyorb;

import baModDeveloper.ui.panels.BATwinsEnergyPanel;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

import basemod.abstracts.CustomEnergyOrb;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class BATwinsEnergyMidoriOrb extends CustomEnergyOrb{

    private static final int ORB_W = 128;
	private static final float ORB_IMG_SCALE = 1.15f * Settings.scale;
	private Texture orbMark;

    public BATwinsEnergyMidoriOrb(String[] orbTexturePaths, String orbVfxPath, float[] layerSpeeds) {
        super(orbTexturePaths, orbVfxPath, layerSpeeds);
    }
	public BATwinsEnergyMidoriOrb(String[] orbTexturePaths, String orbVfxPath, float[] layerSpeeds, String orbMarkPath) {
		super(orbTexturePaths,orbVfxPath,layerSpeeds);
		this.orbMark= ImageMaster.loadImage(orbMarkPath);
	}
    @Override
    public void updateOrb(int eneryCount){
        super.updateOrb(eneryCount);
    }
    
    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y){
        sb.setColor(Color.WHITE);
		if(BATwinsEnergyPanel.selectedEnergySlot== BATwinsEnergyPanel.EnergyType.MIDORI){
			sb.draw(this.orbMark,current_x+84.0F,current_y+19.0F,70.0F/2,115.0F/2);
		}
        if(enabled){
            for(int i=0;i<energyLayers.length;++i){
                sb.draw(energyLayers[i],
						current_x , current_y ,
						64.0f, 64.0f,
						128.0f, 128.0f,
						ORB_IMG_SCALE, ORB_IMG_SCALE,
						angles[i],
						0, 0,
						ORB_W, ORB_W,
						false, false);
			}
        }else{
            for (int i=0; i<noEnergyLayers.length; ++i) {
				sb.draw(noEnergyLayers[i],
						current_x , current_y ,
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
				current_x , current_y ,
				64.0f, 64.0f,
				128.0f, 128.0f,
				ORB_IMG_SCALE, ORB_IMG_SCALE,
				0.0f,
				0, 0,
				ORB_W, ORB_W,
				false, false);
    }
}
