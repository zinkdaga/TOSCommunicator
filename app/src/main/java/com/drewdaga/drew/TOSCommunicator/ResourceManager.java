package com.drewdaga.drew.TOSCommunicator;

import android.graphics.Color;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;


public class ResourceManager {
    private static final ResourceManager INSTANCE = new ResourceManager();

    public Engine engine;
    public BaseGameActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;

    public Font mFont;
    public ITextureRegion logoTR, startTR, splashTR, bgTR, mRedSphereTR, mBlueSphereTR, mGreenSphereTR, mYellowSphereTR;
    private BitmapTextureAtlas splashTA, gameTA, menuTA;

    public static void prepareManager(Engine engine, BaseGameActivity activity, Camera camera, VertexBufferObjectManager vbom) {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }

    public static ResourceManager getInstance() {
        return INSTANCE;
    }

    public void loadMenuResources() {
        loadMenuGraphics();
        loadMenuAudio();
    }

    public void loadGameResources() {
        loadGameGraphics();
        loadGameFonts();
        loadGameAudio();
    }

    private void loadMenuGraphics() {
//    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
//		menuTA = new BitmapTextureAtlas(this.activity.getTextureManager(),
//				1280, 1280);
//		logoTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
//				menuTA, this.activity, "Logo.png", 0, 0);
//		startTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
//				menuTA, this.activity, "start.png", 640, 640+128);
//		menuTA.load();
    }

    private void loadMenuAudio() {

    }

    private void loadGameGraphics() {
        FontFactory.setAssetBasePath("font/");
        this.mFont = FontFactory.createFromAsset(activity.getFontManager(), activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR, activity.getAssets(), "courbd.ttf", 32, true, Color.BLACK);
        this.mFont.load();

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        gameTA = new BitmapTextureAtlas(this.activity.getTextureManager(), 1024, 1024);
        mRedSphereTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTA, this.activity, "RedBall.png", 0, 0);
        mBlueSphereTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTA, this.activity, "BlueBall.png", 0, 256);
        mGreenSphereTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTA, this.activity, "GreenBall.png", 0, 512);
        mYellowSphereTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                gameTA, this.activity, "YellowBall.png", 0, 512 + 256);
        gameTA.load();
    }

    private void loadGameFonts() {

    }

    private void loadGameAudio() {

    }

    public void loadSplashResources() {
//		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
//		splashTA = new BitmapTextureAtlas(this.activity.getTextureManager(),
//				1280, 1280);
//		splashTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
//				splashTA, this.activity, "Logo.png", 0, 0);
        splashTA.load();
    }

    public void unloadSplashScreen() {
//    	splashTA.clearTextureAtlasSources();
    }
}
