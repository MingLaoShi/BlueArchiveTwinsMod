package baModDeveloper.helpers;

import baModDeveloper.BATwinsMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static com.megacrit.cardcrawl.helpers.ImageMaster.vfxAtlas;

public class ImageHelper {
    public static TextureRegion ATK_Shooting=vfxAtlas.findRegion(ModHelper.makeImgPath("effect","ATK_shooting"));
    public static final TextureAtlas.AtlasRegion MOMOISMALLORB=vfxAtlas.findRegion(ModHelper.makeImgPath("512","small_orb"));
    public static final TextureAtlas.AtlasRegion MIDORISMALLORB=vfxAtlas.findRegion(ModHelper.makeImgPath("512","small_orb_2"));


}
