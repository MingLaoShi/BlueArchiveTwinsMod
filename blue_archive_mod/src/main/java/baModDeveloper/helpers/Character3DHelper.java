package baModDeveloper.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.megacrit.cardcrawl.core.Settings;

import java.awt.*;

public class Character3DHelper {
    Rectangle bucket;
    OrthographicCamera camera;
    G3dModelLoader loader;
    Model model;
    ModelInstance instance;
    ModelBatch modelBatch;
    CameraInputController cameraInputController;
    FitViewport viewport;
    AnimationController animationController;
    FrameBuffer frameBuffer;
    TextureRegion region;
    Environment environment;
    PolygonSpriteBatch psb;
    private static float SCALE = 400.0F;
    private static float X = 0.0F, Y = 0.0F, Z = 0.0F;


    public Character3DHelper() {

    }

    public void init() {
        //        if(CardCrawlGamePatch.camera!=null){
//            this.camera=CardCrawlGamePatch.camera;
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.position.set(0, 0, -120);
//            this.camera.lookAt(Settings.WIDTH/2.0F,Settings.HEIGHT/2.0F,20);
        this.camera.near = 1.0F;
        this.camera.far = 1000.0F;
        this.camera.rotate(new Vector3(0.0F, 1.0F, 0.0F), 90.0F);
//            this.camera.lookAt(50,50,0);
        camera.update();
//            viewport = new FitViewport(Settings.WIDTH, (Settings.M_H - Settings.HEIGHT / 2), (Camera)this.camera);
//            viewport.apply();
//        }
        loader = new G3dModelLoader(new JsonReader());
        model = loader.loadModel(Gdx.files.internal("baModResources/img/char/model/momoi.g3dj"));
        instance = new ModelInstance(model, 0, 0, 0);
        animationController = new AnimationController(instance);
//        animationController.setAnimation("Momoi_Original_Cafe_Hockey2",-1);

        modelBatch = new ModelBatch();
        cameraInputController = new CameraInputController(camera);
        instance.transform.setTranslation(-800, -50, -120.0F);
        instance.transform.rotate(0, 1, 1, 180);
        instance.transform.scale(SCALE, SCALE, SCALE);
//        Gdx.input.setInputProcessor(cameraInputController);

        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8F, 0.8F, 0.8F, 1.0F));
        psb = new PolygonSpriteBatch();
    }

    public void update() {
        if (this.animationController.current == null) {
            this.animationController.setAnimation(MomoiAnimationList.STAND_ATTACK_DELAY.getName(), -1);
        }
        animationController.update(Gdx.graphics.getDeltaTime());
        cameraInputController.update();
        instance.transform.translate(X, Y, Z);

    }

    public void render(SpriteBatch sb, float x, float y) {
        //3D相关
        sb.end();

        frameBuffer.begin();
//
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
//        sb.setProjectionMatrix(camera.combined);
//        sb.begin();
        modelBatch.begin(camera);
        modelBatch.render(instance);
        modelBatch.end();
        frameBuffer.end();
        region = new TextureRegion(frameBuffer.getColorBufferTexture());
        region.flip(false, true);
        frameBuffer.getColorBufferTexture().bind(0);
        psb.begin();
        psb.draw(region, 0, 20F,
                Gdx.graphics.getWidth() / 2.0F, Gdx.graphics.getHeight() / 2.0F,
                Gdx.graphics.getWidth() * Settings.scale, Gdx.graphics.getHeight() * Settings.scale,
                1.0F, 1.0F, 0.0F);
        psb.setShader(null);
        psb.setBlendFunction(770, 771);
        psb.end();
//        sb.setProjectionMatrix(BATwinsCardCrawlGamePatch.createPatch.camera.combined);
        sb.begin();
    }

    private enum MomoiAnimationList {
        STAND_ATTACK_DELAY("Momoi_Original_Stand_Attack_Delay"),
        MOVING("Momoi_Original_Move_Ing"),
        MOVING_END_NORMAL("Momoi_Original_Move_End_Normal"),
        ATTACK_START("Momoi_Original_Normal_Attack_Start"),
        ATTACK_ING("Momoi_Original_Normal_Attack_Ing"),
        ATTACK_END("Momoi_Original_Normal_Attack_End");

        private String name;

        private MomoiAnimationList(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum MomoiActionList {
        STAND_NORMAL(new MomoiAnimationList[]{MomoiAnimationList.STAND_ATTACK_DELAY}),
        ATTACK(new MomoiAnimationList[]{MomoiAnimationList.ATTACK_START, MomoiAnimationList.ATTACK_ING, MomoiAnimationList.ATTACK_END});
        private final MomoiAnimationList[] animList;

        private MomoiActionList(MomoiAnimationList[] animList) {
            this.animList = animList;
        }

        public MomoiAnimationList[] getAnimList() {
            return animList;
        }
    }

    public void setAnimation(MomoiActionList action) {
        this.clearQueue(this.animationController);
        for (MomoiAnimationList anim : action.getAnimList()) {
            this.animationController.queue(anim.getName(), 1, 1.0F, null, 0.3F);
        }
    }

    private void clearQueue(AnimationController controller) {
        while (controller.current != null) {
            controller.setAnimation(null);
        }
    }
}
