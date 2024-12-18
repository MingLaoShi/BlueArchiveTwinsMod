package baModDeveloper.helpers;

import baModDeveloper.BATwinsMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.utils.JsonReader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;

import java.io.IOException;
import java.util.function.Consumer;

public class ModelController {
    private static final float SCALE = 4.5F * Settings.scale*Character3DHelper.ExpandScale;
    private static final float MOVESCALE = Settings.xScale * 4;
    private G3dModelLoader loader;
    private Model model;
    private ModelInstance instance;
    private ModelBatch modelBatch;
    private AnimationController animationController;
    private float current_x = 0, current_y = 0, target_x = 0, target_y = 0;
    private String StandAnima;
    public boolean inited=false;

    public ModelController(String modelPath, float x, float y, float z, String StandAnima) {
        try {
            loader = new G3dModelLoader(new JsonReader());
            model = loader.loadModel(Gdx.files.internal(modelPath));        instance = new ModelInstance(model, 0, 0, 0);

            animationController = new AnimationController(instance);

            DefaultShaderProvider shaderProvider = new DefaultShaderProvider();
            if(BATwinsMod.EnableModelLighting){
                shaderProvider.config.fragmentShader = Gdx.files.internal("baModResources/shader/model/myfragshader.fs").readString();
            }
            modelBatch = new ModelBatch(shaderProvider);
            instance.transform.setTranslation(z, y, x);
            instance.transform.rotate(0, 3, 1, 180);
            instance.transform.rotate(1, 0, 0, 34);
            instance.transform.scale(SCALE, SCALE, SCALE);
            for (int i = 0; i < instance.materials.size; i++) {
                instance.materials.get(i).set(ColorAttribute.createDiffuse(Color.WHITE.cpy()));
            }

            this.StandAnima = StandAnima;
            this.inited=true;
        }catch (OutOfMemoryError error){
            BATwinsMod.Enable3D=false;
            this.inited=false;
            try {
                SpireConfig spireConfig = new SpireConfig("BATwinsMod", "Common");
                spireConfig.setBool(ModHelper.makePath("Enable3D"), BATwinsMod.Enable3D);
                spireConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void update() {
        if (this.animationController.current == null || this.animationController.current.time >= this.animationController.current.duration) {
            this.animationController.queue(this.StandAnima, -1, 1, null, 0.4F);
        }
        animationController.update(Gdx.graphics.getDeltaTime());
        if (Math.abs(this.current_x - this.target_x) > MOVESCALE) {
            this.current_x += Math.signum(this.target_x - this.current_x) * MOVESCALE;
        } else {
            this.current_x = this.target_x;
        }
        if (Math.abs(this.current_y - this.target_y) > MOVESCALE) {
            this.current_y += Math.signum(this.target_y - this.current_y) * MOVESCALE;
        }
        {
            this.current_y = this.target_y;
        }
        this.instance.transform.setTranslation(-500, current_y, current_x);
    }

    public void render(OrthographicCamera camera, Environment environment) {
        modelBatch.begin(camera);
        modelBatch.render(instance, environment);
        modelBatch.end();
    }

    public void resetPosition(float x, float y) {
        this.current_x = this.target_x = x;
        this.current_y = this.target_y = y;
    }

    public void setCurrentPosition(float x, float y) {
        this.current_x = x;
        this.current_y = y;
    }

    public void moveCurrentPosition(float x, float y) {
        this.current_x += x;
        this.current_y += y;
    }

    private void clearQueue(AnimationController controller) {
        while (controller.current != null) {
            controller.setAnimation(null);
        }
    }

    public void setAnimation(Consumer<AnimationController> animation) {
        this.clearQueue(animationController);
        resetPosition(this.target_x, this.target_y);
        animation.accept(this.animationController);
    }

    public void setStandAnima(String anima) {
        if (this.animationController.current != null && this.animationController.current.animation.id.equals(this.StandAnima)) {
            this.clearQueue(this.animationController);
        }
        this.StandAnima = anima;
    }

    public String getCurrentAnima() {
        if (this.animationController.current == null) {
            return "";
        }
        return this.animationController.current.animation.id;
    }

    public void resetDefaultAnima() {
        this.clearQueue(this.animationController);
    }

    public void setAttribute(String matId, Attribute attribute) {
        for (int i = 0; i < instance.materials.size; i++) {
            if (this.instance.materials.get(i).id.equals(matId)) {
                this.instance.materials.get(i).set(attribute);
            }
        }
    }

    public void rotate(float x, float y, float z, float degree) {
        this.instance.transform.rotate(x, y, z, degree);
    }

    public void switchLighting(){
        if(this.modelBatch==null){
            return;
        }
        this.modelBatch.dispose();
        if(BATwinsMod.EnableModelLighting){
            DefaultShaderProvider shaderProvider = new DefaultShaderProvider();
            shaderProvider.config.fragmentShader = Gdx.files.internal("baModResources/shader/model/myfragshader.fs").readString();
            modelBatch = new ModelBatch(shaderProvider);
        }else{
            modelBatch=new ModelBatch();
        }
    }

}
