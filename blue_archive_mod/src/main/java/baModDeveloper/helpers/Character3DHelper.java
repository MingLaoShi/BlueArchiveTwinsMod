package baModDeveloper.helpers;

import baModDeveloper.BATwinsMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static baModDeveloper.character.BATwinsCharacter.Enums.BATWINS_MIDORI_CARD;
import static baModDeveloper.character.BATwinsCharacter.Enums.BATWINS_MOMOI_CARD;
import static baModDeveloper.helpers.Character3DHelper.AnimationName.*;

public class Character3DHelper {
    private static final Map<AnimationName, String[]> AnimationNames = new HashMap<>();

    static {
        AnimationNames.put(NORMAL_IDLE, new String[]{"Armature|Momoi_Original_Normal_Idle", "Armature|Midori_Original_Normal_Idle"});
        AnimationNames.put(STAND_ATTACK_DELAY, new String[]{"Armature|Momoi_Original_Stand_Attack_Delay", "Armature|Midori_Original_Stand_Attack_Delay"});
        AnimationNames.put(MOVING, new String[]{"Armature|Momoi_Original_Move_Ing", "Armature|Midori_Original_Move_Ing"});
        AnimationNames.put(MOVING_END, new String[]{"Armature|Momoi_Original_Move_End_Normal", "Armature|Midori_Original_Move_End_Normal"});
        AnimationNames.put(ATTACK_START, new String[]{"Armature|Momoi_Original_Normal_Attack_Start", "Armature|Midori_Original_Normal_Attack_Start"});
        AnimationNames.put(ATTACKING, new String[]{"Armature|Momoi_Original_Normal_Attack_Ing", "Armature|Midori_Original_Normal_Attack_Ing"});
        AnimationNames.put(ATTACK_END, new String[]{"Armature|Momoi_Original_Normal_Attack_End", "Armature|Midori_Original_Normal_Attack_End"});
        AnimationNames.put(MOVE_JUMP, new String[]{"Armature|Momoi_Original_Move_Jump", "Armature|Midori_Original_Move_Jump"});
        AnimationNames.put(DEATH, new String[]{"Armature|Momoi_Original_Vital_Death", "Armature|Midori_Original_Vital_Death"});
        AnimationNames.put(DYING, new String[]{"Armature|Momoi_Original_Vital_Dying_ing", "Armature|Midori_Original_Vital_Dying_ing"});
        AnimationNames.put(RELOAD, new String[]{"Armature|Momoi_Original_Normal_Reload", "Armature|Midori_Original_Normal_Reload"});
        AnimationNames.put(PANIC, new String[]{"Armature|Momoi_Original_Vital_Panic", "Armature|Midori_Original_Vital_Panic"});
    }

    public float current_x = 0, current_y = 0;
    OrthographicCamera camera;
    FrameBuffer frameBuffer;
    TextureRegion region;
    Environment environment;
    PolygonSpriteBatch psb;
    ModelController momoiController;
    ModelController midoriController;
    private boolean inited = false;

    public Character3DHelper() {
        if (BATwinsMod.Enable3D)
            init();
    }

    public static String getAnimationString(AnimationName name, AbstractCard.CardColor color) {
        if (color == BATWINS_MOMOI_CARD)
            return AnimationNames.get(name)[0];
        else
            return AnimationNames.get(name)[1];
    }

    public void init() {
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.position.set(0, 0, -120);
        this.camera.near = 1.0F;
        this.camera.far = 1000.0F;
        this.camera.rotate(new Vector3(0.0F, 1.0F, 0.0F), 90.0F);
        camera.update();


        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1.0F, 1.0F, 1.0F, 1.0F));

        psb = new PolygonSpriteBatch();

        this.momoiController = new ModelController("baModResources/img/char/model/momoi.g3dj", 0, 0, -500, getAnimationString(NORMAL_IDLE,BATWINS_MOMOI_CARD));
        this.midoriController = new ModelController("baModResources/img/char/model/midori.g3dj", 0, 0, -500, getAnimationString(NORMAL_IDLE,BATWINS_MIDORI_CARD));

        this.resetCharacterPosition();

        this.inited = true;
    }

    public void update() {

        momoiController.update();
        midoriController.update();
    }

    public void render(SpriteBatch sb) {
        render(sb, false);
    }

    public void render(SpriteBatch sb, boolean flipHorizontal) {
        //3D相关
        sb.end();

        frameBuffer.begin();
//
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//        sb.setProjectionMatrix(camera.combined);
//        sb.begin();
        momoiController.render(camera, environment);
        midoriController.render(camera, environment);

        frameBuffer.end();
        region = new TextureRegion(frameBuffer.getColorBufferTexture());
        region.flip(flipHorizontal, true);
        frameBuffer.getColorBufferTexture().bind(0);
        psb.begin();

        if (flipHorizontal) {
            psb.draw(region, this.current_x - 125.0F * Settings.scale, this.current_y,
                    Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                    Gdx.graphics.getWidth() / 2.0F, Gdx.graphics.getHeight() / 2.0F,
                    1.0F, 1.0F, 0.0F);
        } else {
            psb.draw(region, this.current_x, this.current_y,
                    Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                    Gdx.graphics.getWidth() / 2.0F, Gdx.graphics.getHeight() / 2.0F,
                    1.0F, 1.0F, 0.0F);
        }

        psb.setShader(null);
        psb.setBlendFunction(770, 771);
        psb.end();
//        sb.setProjectionMatrix(BATwinsCardCrawlGamePatch.createPatch.camera.combined);
        sb.begin();
    }

    public void setMomoiAnimation(MomoiActionList action) {
        this.momoiController.setAnimation(action.getOperation());
        switch (action) {
            case MOVING:
                this.momoiController.moveCurrentPosition(300 * Settings.scale, 0);
                break;
        }
    }

    public void setMidoriAnimation(MidoriActionList action) {
        this.midoriController.setAnimation(action.getOperation());
        switch (action) {
            case MOVING:
                this.midoriController.moveCurrentPosition(300 * Settings.scale, 0);
                break;
        }
    }

    public void resetModelPosition(float x, float y, AbstractCard.CardColor color) {
        if (color == BATWINS_MOMOI_CARD) {
            this.momoiController.resetPosition(x, y);
        } else {
            this.midoriController.resetPosition(x, y);
        }

    }

    public void setPosition(float x, float y) {
        this.current_x = x;
        this.current_y = y;
    }

    public boolean inited() {
        return this.inited;
    }

    public void setStandAnima(AnimationName anima, AbstractCard.CardColor color) {
        if (color == BATWINS_MOMOI_CARD) {
            this.momoiController.setStandAnima(getAnimationString(anima,BATWINS_MOMOI_CARD));
        } else {
            this.midoriController.setStandAnima(getAnimationString(anima,BATWINS_MIDORI_CARD));
        }
    }

    public String getCurrentAnima(AbstractCard.CardColor color) {
        if (color == BATWINS_MOMOI_CARD) {
            return this.momoiController.getCurrentAnima();
        } else {
            return this.midoriController.getCurrentAnima();
        }
    }

    public void resetDefaultAnima(AbstractCard.CardColor color) {
        if (color == BATWINS_MOMOI_CARD) {
            this.momoiController.resetDefaultAnima();
        } else {
            this.midoriController.resetDefaultAnima();
        }
    }

//    public void initWithTimeout() throws RuntimeException {
//        ModelLoaderThread thread = new ModelLoaderThread();
//        thread.start();
//        try {
//            thread.join(5000);
//            if (!thread.finishLoading) {
//                throw new RuntimeException("Model loading exceeded timeout");
//            }
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void clearMomoiAnima() {
        this.momoiController.resetDefaultAnima();
    }

    public void clearMidoriAnima() {
        this.midoriController.resetDefaultAnima();
    }

    public void resetCharacterPosition() {
        this.momoiController.resetPosition(150 * Settings.scale, 0);
        this.midoriController.resetPosition(-150 * Settings.scale, 0);
    }

    public enum AnimationName {
        NORMAL_IDLE,
        STAND_ATTACK_DELAY,
        MOVING,
        MOVING_END,
        ATTACK_START,
        ATTACKING,
        ATTACK_END,
        MOVE_JUMP,
        DEATH,
        DYING,
        RELOAD,
        PANIC
    }

    public enum MomoiActionList {
        STAND_NORMAL(animationController -> {
            System.out.println("STAND_NORMAL");
            animationController.queue(getAnimationString(STAND_ATTACK_DELAY,BATWINS_MOMOI_CARD), -1, 1, null, 0.5F);
        }),
        ATTACK(animationController -> {
            System.out.println("ATTACK");
            animationController.queue(getAnimationString(ATTACK_START,BATWINS_MOMOI_CARD), 1, 1, null, 0.2F);
            animationController.queue(getAnimationString(ATTACKING,BATWINS_MOMOI_CARD), 1, 1, new AnimationController.AnimationListener() {
                @Override
                public void onEnd(AnimationController.AnimationDesc animationDesc) {
                    animationController.queue(getAnimationString(ATTACK_END,BATWINS_MOMOI_CARD), 1, 1, null, 0.2F);
                }

                @Override
                public void onLoop(AnimationController.AnimationDesc animationDesc) {

                }
            }, 0.2F);
        }),
        MOVING(animationController -> {
            System.out.println("MOVING");
            animationController.queue(getAnimationString(AnimationName.MOVING,BATWINS_MOMOI_CARD), 4, 1.8F, null, 0.5F);
            animationController.queue(getAnimationString(MOVING_END,BATWINS_MOMOI_CARD), 1, 1.0F, null, 0.2F);
        }),
        JUMP(animationController -> {
            System.out.println("JUMP");
            animationController.queue(getAnimationString(MOVE_JUMP,BATWINS_MOMOI_CARD), 1, 1, null, 0.5F);
        }),
        DEATH(animationController -> {
            System.out.println("DEATH");
            animationController.queue(getAnimationString(AnimationName.DEATH,BATWINS_MOMOI_CARD), 1, 1, null, 0.5F);
        }),
        DYING(animationController -> {
            System.out.println("DYING");
            animationController.queue(getAnimationString(AnimationName.DYING,BATWINS_MOMOI_CARD), -1, 1, null, 0.5F);
        }),
        RELOAD(animationController -> {
            System.out.println("RELOAD");
            animationController.queue(getAnimationString(AnimationName.RELOAD,BATWINS_MOMOI_CARD), 1, 1, null, 0.5F);
        }),
        PANIC(animationController -> {
            System.out.println("PANIC");
            animationController.queue(getAnimationString(AnimationName.PANIC,BATWINS_MOMOI_CARD), -1, 1, null, 0.5F);
        });
        private final Consumer<AnimationController> operation;

        private MomoiActionList(Consumer<AnimationController> operation) {
            this.operation = operation;
        }

        public Consumer<AnimationController> getOperation() {
            return operation;
        }
    }

    public enum MidoriActionList {
        STAND_NORMAL(animationController -> {
            System.out.println("STAND_NORMAL");
            animationController.queue(getAnimationString(STAND_ATTACK_DELAY,BATWINS_MIDORI_CARD), -1, 1, null, 0.5F);
        }),
        ATTACK(animationController -> {
            System.out.println("ATTACK");
            animationController.queue(getAnimationString(ATTACK_START,BATWINS_MIDORI_CARD), 1, 1, null, 0.2F);
            animationController.queue(getAnimationString(ATTACKING,BATWINS_MIDORI_CARD), 1, 1, new AnimationController.AnimationListener() {
                @Override
                public void onEnd(AnimationController.AnimationDesc animationDesc) {
                    animationController.queue(getAnimationString(ATTACK_END,BATWINS_MIDORI_CARD), 1, 1, null, 0.2F);
                }

                @Override
                public void onLoop(AnimationController.AnimationDesc animationDesc) {

                }
            }, 0.2F);
        }),
        MOVING(animationController -> {
            System.out.println("MOVING");
            animationController.queue(getAnimationString(AnimationName.MOVING,BATWINS_MIDORI_CARD), 4, 1.8F, null, 0.5F);
            animationController.queue(getAnimationString(MOVING_END,BATWINS_MIDORI_CARD), 1, 1.0F, null, 0.2F);
        }),
        JUMP(animationController -> {
            System.out.println("JUMP");
            animationController.queue(getAnimationString(MOVE_JUMP,BATWINS_MIDORI_CARD), 1, 1, null, 0.5F);
        }),
        DEATH(animationController -> {
            System.out.println("DEATH");
            animationController.queue(getAnimationString(AnimationName.DEATH,BATWINS_MIDORI_CARD), 1, 1, null, 0.5F);
        }),
        DYING(animationController -> {
            System.out.println("DYING");
            animationController.queue(getAnimationString(AnimationName.DYING,BATWINS_MIDORI_CARD), -1, 1, null, 0.5F);
        }),
        RELOAD(animationController -> {
            System.out.println("RELOAD");
            animationController.queue(getAnimationString(AnimationName.RELOAD,BATWINS_MIDORI_CARD), 1, 1, null, 0.5F);
        }),
        PANIC(animationController -> {
            System.out.println("PANIC");
            animationController.queue(getAnimationString(AnimationName.PANIC,BATWINS_MIDORI_CARD), -1, 1, null, 0.5F);
        });

        private final Consumer<AnimationController> operation;

        private MidoriActionList(Consumer<AnimationController> operation) {
            this.operation = operation;
        }

        public Consumer<AnimationController> getOperation() {
            return operation;
        }
    }

    public static class ModelLoaderThread extends Thread {
        public boolean finishLoading = false;
        private Character3DHelper character3DHelper;
        public ModelLoaderThread(Character3DHelper character3DHelper) {
            this.character3DHelper=character3DHelper;
        }

        @Override
        public void run() {
            character3DHelper.init();
            finishLoading = true;
        }
    }
}
