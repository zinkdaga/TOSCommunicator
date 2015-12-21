package com.drewdaga.drew.TOSCommunicator;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import java.io.IOException;
import java.io.InputStream;


public class TOSCommunicator extends SimpleBaseGameActivity {

    private static int CAMERA_BASE_WIDTH = 480;
    private static int CAMERA_BASE_HEIGHT = 800;
    private static int CAMERA_WIDTH = 480;
    private static int CAMERA_HEIGHT = 800;
    private static float CAMERA_ACTUAL_OVER_BASE = 1;
    private Sound mSoundSpockFascinating, mPhotonTorpedo, mSoundLiveLongAndProsper, mSoundStun, mSoundTribble, mSoundNeedYou, mSoundCommChirp;//, mSoundBeamMeAboard;
    private Music mSpockCaptainJim, mSoundBeamMeAboard;
    private ITextureRegion mCircleThingTR, mPanelTR, mSecurityButtonTR, mCommandButtonTR, mMedicalButtonTR, mSecurityLightOffTR, mSecurityLightOnTR, mMedicalLightOffTR, mMedicalLightOnTR, mCommandLightOffTR, mCommandLightOnTR, mBackgroundTR;
    private ITiledTextureRegion mCommandLightTTR;
    private Sprite medicalLightOnSprite, securityLightOnSprite, commandLightOnSprite;
//	private Light commandLightSprite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        Point point = new Point();
        final Display display = getWindowManager().getDefaultDisplay();
        display.getSize(point);
        int cameraWidth = 0;
        int cameraHeight = 0;
        if (point.x < point.y) {
            cameraWidth = point.x;
            //cameraHeight = point.y;
        } else {
            cameraWidth = point.y;
            //cameraHeight = point.x;
        }
        cameraHeight = cameraWidth * CAMERA_BASE_HEIGHT / CAMERA_BASE_WIDTH;
        CAMERA_ACTUAL_OVER_BASE = ((float) cameraHeight) / ((float) CAMERA_BASE_HEIGHT);
        CAMERA_HEIGHT = cameraHeight;
        CAMERA_WIDTH = cameraWidth;
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
        engineOptions.getAudioOptions().setNeedsSound(true);
        engineOptions.getAudioOptions().setNeedsMusic(true);

        return engineOptions;

    }

    @Override
    protected void onCreateResources() {
        try {


            // 1 - Set up bitmap textures
            ITexture backgroundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/Background.png");
                }
            });
            ITexture circleThingTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/CircleThing.png");
                }
            });
            ITexture panelTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/Panel.png");
                }
            });
            ITexture securityButtonTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/SecurityButton.png");
                }
            });
            ITexture commandButtonTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/CommandButton.png");
                }
            });
            ITexture medicalButtonTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/MedicalButton.png");
                }
            });
            ITexture securityLightOffTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/SecurityLightOff.png");
                }
            });
            ITexture medicalLightOffTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/MedicalLightOff.png");
                }
            });
            ITexture securityLightOnTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/SecurityLightOn.png");
                }
            });
            ITexture medicalLightOnTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/MedicalLightOn.png");
                }
            });
            ITexture commandLightOffTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/CommandLightOff.png");
                }
            });

            ITexture commandLightOnTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gfx/CommandLightOn.png");
                }
            });

            // 2 - Load bitmap textures into VRAM
            backgroundTexture.load();
            circleThingTexture.load();
            panelTexture.load();
            securityButtonTexture.load();
            commandButtonTexture.load();
            medicalButtonTexture.load();
            securityLightOffTexture.load();
            commandLightOffTexture.load();
            medicalLightOffTexture.load();
            securityLightOnTexture.load();
            medicalLightOnTexture.load();
            commandLightOnTexture.load();


            // 3 - Set up texture regions
            this.mBackgroundTR = TextureRegionFactory.extractFromTexture(backgroundTexture);
            this.mCircleThingTR = TextureRegionFactory.extractFromTexture(circleThingTexture);
            this.mPanelTR = TextureRegionFactory.extractFromTexture(panelTexture);
            this.mSecurityButtonTR = TextureRegionFactory.extractFromTexture(securityButtonTexture);
            this.mMedicalButtonTR = TextureRegionFactory.extractFromTexture(medicalButtonTexture);
            this.mCommandButtonTR = TextureRegionFactory.extractFromTexture(commandButtonTexture);
            this.mMedicalLightOffTR = TextureRegionFactory.extractFromTexture(medicalLightOffTexture);
            this.mMedicalLightOnTR = TextureRegionFactory.extractFromTexture(medicalLightOnTexture);
            this.mCommandLightOffTR = TextureRegionFactory.extractFromTexture(commandLightOffTexture);
            this.mCommandLightOnTR = TextureRegionFactory.extractFromTexture(commandLightOnTexture);
            this.mSecurityLightOffTR = TextureRegionFactory.extractFromTexture(securityLightOffTexture);
            this.mSecurityLightOnTR = TextureRegionFactory.extractFromTexture(securityLightOnTexture);
            //this.mCommandLightTTR = TextureRegionFactory.


        } catch (IOException e) {
            Debug.e(e);
        }

        try {
            mSoundSpockFascinating = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(),
                    "aud/Spock_Fascinating.wav");

            mPhotonTorpedo = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(),
                    "aud/tos_photon_torpedo.mp3");

            mSoundStun = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(),
                    "aud/tos_phaser_stun.mp3");

            mSoundLiveLongAndProsper = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(),
                    "aud/Spock_Livelong.wav");

            mSoundTribble = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(),
                    "aud/tos-tribble.wav");

            mSoundNeedYou = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(),
                    "aud/need_you.wav");

            mSpockCaptainJim = MusicFactory.createMusicFromAsset(this.getMusicManager(), this.getApplicationContext(),
                    "aud/Spock_CaptainJimShortLoud.mp3");
            mSpockCaptainJim.setLooping(false);

            mSoundCommChirp = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(),
                    "aud/tos_chirp_2.mp3");

            mSoundBeamMeAboard = MusicFactory.createMusicFromAsset(this.getMusicManager(), this.getApplicationContext(),
                    "aud/beam_me_aboard_cut.mp3");
            mSoundBeamMeAboard.setLooping(false);

        } catch (IOException e) {
            Debug.e(e);
        }

    }

    @Override
    protected Scene onCreateScene() {
        final Scene scene = new Scene();
        scene.getBackground().setColor(Color.BLACK);
        Sprite backgroundSprite = new Sprite(0, 0, this.mBackgroundTR, getVertexBufferObjectManager());
        Sprite panelSprite = new Sprite(CAMERA_WIDTH * 3 / 18, CAMERA_HEIGHT * 19 / 60, this.mPanelTR, getVertexBufferObjectManager());

        securityLightOnSprite = new Sprite(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 7 / 30, this.mSecurityLightOnTR, getVertexBufferObjectManager());
        medicalLightOnSprite = new Sprite(CAMERA_WIDTH * 8 / 18, CAMERA_HEIGHT * 7 / 30, this.mMedicalLightOnTR, getVertexBufferObjectManager());
        commandLightOnSprite = new Sprite(CAMERA_WIDTH * 12 / 18, CAMERA_HEIGHT * 7 / 30, this.mCommandLightOnTR, getVertexBufferObjectManager());
        Sprite commandLightOffSprite = new Sprite(CAMERA_WIDTH * 12 / 18, CAMERA_HEIGHT * 7 / 30, this.mCommandLightOffTR, getVertexBufferObjectManager());
        Sprite medicalLightOffSprite = new Sprite(CAMERA_WIDTH * 8 / 18, CAMERA_HEIGHT * 7 / 30, this.mMedicalLightOffTR, getVertexBufferObjectManager());
        Sprite securityLightOffSprite = new Sprite(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 7 / 30, this.mSecurityLightOffTR, getVertexBufferObjectManager());
        commandLightOnSprite.setVisible(false);
        medicalLightOnSprite.setVisible(false);
        securityLightOnSprite.setVisible(false);
        Sprite circleThingSprite = new Sprite(CAMERA_WIDTH * 3 / 9, CAMERA_HEIGHT * 1 / 30, this.mCircleThingTR, getVertexBufferObjectManager());

        Button securityButton1Sprite = new Button(CAMERA_WIDTH * 2 / 18, CAMERA_HEIGHT * 14 / 30, this.mSecurityButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                    mSoundStun.play();
                    flickerLights(securityLightOnSprite, 0);
                    flickerLights(medicalLightOnSprite, 1);
                    flickerLights(commandLightOnSprite, 2);

                }
                return true;
            }


        };
        Button medicalButton1Sprite = new Button(CAMERA_WIDTH * 7 / 18, CAMERA_HEIGHT * 14 / 30, this.mMedicalButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                    mSoundLiveLongAndProsper.play();
                    flickerLights(securityLightOnSprite, 0);
                    flickerLights(medicalLightOnSprite, 1);
                    flickerLights(commandLightOnSprite, 2);
                    flickerLights(securityLightOnSprite, 5);
                    flickerLights(medicalLightOnSprite, 6);
                    flickerLights(commandLightOnSprite, 7);

                }
                return true;
            }


        };
        Button commandButton1Sprite = new Button(CAMERA_WIDTH * 12 / 18, CAMERA_HEIGHT * 14 / 30, this.mCommandButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                    mSoundCommChirp.play();
                    flickerLights(securityLightOnSprite, 0);
                    flickerLights(medicalLightOnSprite, 1);
                    flickerLights(commandLightOnSprite, 2);

                }
                return true;
            }


        };

        Button securityButton2Sprite = new Button(CAMERA_WIDTH * 12 / 18, CAMERA_HEIGHT * 19 / 30, this.mSecurityButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                    mPhotonTorpedo.play();
                    flickerLights(securityLightOnSprite, 0);
                    flickerLights(medicalLightOnSprite, 1);
                    flickerLights(commandLightOnSprite, 2);

                }
                return true;
            }


        };

        Button medicalButton2Sprite = new Button(CAMERA_WIDTH * 2 / 18, CAMERA_HEIGHT * 19 / 30, this.mMedicalButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                    mSpockCaptainJim.play();
                    flickerLights(securityLightOnSprite, 0);
                    flickerLights(medicalLightOnSprite, 1);
                    flickerLights(commandLightOnSprite, 2);
                    flickerLights(securityLightOnSprite, 30);
                    flickerLights(medicalLightOnSprite, 31);
                    flickerLights(commandLightOnSprite, 32);


                }
                return true;
            }


        };
        Button commandButton2Sprite = new Button(CAMERA_WIDTH * 7 / 18, CAMERA_HEIGHT * 19 / 30, this.mCommandButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                    mSoundNeedYou.play();
                    flickerLights(securityLightOnSprite, 0);
                    flickerLights(medicalLightOnSprite, 1);
                    flickerLights(commandLightOnSprite, 2);
//					flickerLights(securityLightOnSprite, 10);
//					flickerLights(medicalLightOnSprite, 11);
//					flickerLights(commandLightOnSprite, 12);
                    flickerLights(securityLightOnSprite, 12);
                    flickerLights(medicalLightOnSprite, 13);
                    flickerLights(commandLightOnSprite, 14);
//					flickerLights(securityLightOnSprite, 20);
//					flickerLights(medicalLightOnSprite, 21);
//					flickerLights(commandLightOnSprite, 22);

                }
                return true;
            }


        };

        Button securityButton3Sprite = new Button(CAMERA_WIDTH * 7 / 18, CAMERA_HEIGHT * 24 / 30, this.mSecurityButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                    mSoundTribble.play();
                    flickerLights(securityLightOnSprite, 0);
                    flickerLights(medicalLightOnSprite, 1);
                    flickerLights(commandLightOnSprite, 2);
//					flickerLights(securityLightOnSprite, 10);
//					flickerLights(medicalLightOnSprite, 11);
//					flickerLights(commandLightOnSprite, 12);
                    flickerLights(securityLightOnSprite, 15);
                    flickerLights(medicalLightOnSprite, 16);
                    flickerLights(commandLightOnSprite, 17);
                    flickerLights(securityLightOnSprite, 30);
                    flickerLights(medicalLightOnSprite, 31);
                    flickerLights(commandLightOnSprite, 32);

                }
                return true;
            }


        };
        Button medicalButton3Sprite = new Button(CAMERA_WIDTH * 12 / 18, CAMERA_HEIGHT * 24 / 30, this.mMedicalButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                    mSoundSpockFascinating.play();
                    flickerLights(securityLightOnSprite, 0);
                    flickerLights(medicalLightOnSprite, 1);
                    flickerLights(commandLightOnSprite, 2);

                }
                return true;
            }


        };
        Button commandButton3Sprite = new Button(CAMERA_WIDTH * 2 / 18, CAMERA_HEIGHT * 24 / 30, this.mCommandButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                    mSoundBeamMeAboard.play();
                    flickerLights(securityLightOnSprite, 3);
                    flickerLights(medicalLightOnSprite, 4);
                    flickerLights(commandLightOnSprite, 5);

//					flickerLights(securityLightOnSprite, 13);
//					flickerLights(medicalLightOnSprite, 14);
//					flickerLights(commandLightOnSprite, 15);


                }
                return true;
            }


        };


        //		Sprite panelSprite = new Sprite(CAMERA_WIDTH*(1/9), CAMERA_HEIGHT*(1/3), this.mPanelTR, getVertexBufferObjectManager());
        //		backgroundSprite.setSize(CAMERA_WIDTH, CAMERA_HEIGHT);

        backgroundSprite.setSize(CAMERA_WIDTH * 18 / 18, CAMERA_HEIGHT * 30 / 30);

        securityLightOnSprite.setSize(CAMERA_WIDTH * 2 / 18, CAMERA_HEIGHT * 2 / 30);
        securityLightOffSprite.setSize(CAMERA_WIDTH * 2 / 18, CAMERA_HEIGHT * 2 / 30);
        commandLightOnSprite.setSize(CAMERA_WIDTH * 2 / 18, CAMERA_HEIGHT * 2 / 30);
        commandLightOffSprite.setSize(CAMERA_WIDTH * 2 / 18, CAMERA_HEIGHT * 2 / 30);
        medicalLightOnSprite.setSize(CAMERA_WIDTH * 2 / 18, CAMERA_HEIGHT * 2 / 30);
        medicalLightOffSprite.setSize(CAMERA_WIDTH * 2 / 18, CAMERA_HEIGHT * 2 / 30);

        panelSprite.setSize(CAMERA_WIDTH * 12 / 18, CAMERA_HEIGHT * 4 / 30);
        circleThingSprite.setSize(CAMERA_WIDTH * 6 / 18, CAMERA_HEIGHT * 6 / 30);

        securityButton1Sprite.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);
        medicalButton1Sprite.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);
        commandButton1Sprite.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);

        securityButton2Sprite.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);
        medicalButton2Sprite.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);
        commandButton2Sprite.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);

        securityButton3Sprite.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);
        medicalButton3Sprite.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);
        commandButton3Sprite.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);

        float test = panelSprite.getHeight();
        //panelSprite.setS
        scene.attachChild(backgroundSprite);
        scene.attachChild(panelSprite);
        scene.attachChild(circleThingSprite);
        scene.attachChild(securityLightOffSprite);
        scene.attachChild(medicalLightOffSprite);
        scene.attachChild(commandLightOffSprite);
        scene.attachChild(securityLightOnSprite);
        scene.attachChild(commandLightOnSprite);
        scene.attachChild(medicalLightOnSprite);
        scene.attachChild(securityButton1Sprite);
        scene.attachChild(medicalButton1Sprite);
        scene.attachChild(commandButton1Sprite);
        scene.attachChild(securityButton2Sprite);
        scene.attachChild(medicalButton2Sprite);
        scene.attachChild(commandButton2Sprite);
        scene.attachChild(securityButton3Sprite);
        scene.attachChild(medicalButton3Sprite);
        scene.attachChild(commandButton3Sprite);

        scene.registerTouchArea(commandButton1Sprite);
        scene.registerTouchArea(commandButton2Sprite);
        scene.registerTouchArea(commandButton3Sprite);
        scene.registerTouchArea(medicalButton1Sprite);
        scene.registerTouchArea(medicalButton2Sprite);
        scene.registerTouchArea(medicalButton3Sprite);
        scene.registerTouchArea(securityButton1Sprite);
        scene.registerTouchArea(securityButton2Sprite);
        scene.registerTouchArea(securityButton3Sprite);
        return scene;
    }

    protected Boolean flickerLights(final Sprite pLightSprite, final int pOffset) {
        Boolean returnStatus = false;


        mEngine.registerUpdateHandler(new TimerHandler(0.2f + pOffset * 0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                pLightSprite.setVisible(true);
            }
        }));
        mEngine.registerUpdateHandler(new TimerHandler(0.4f + pOffset * 0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                pLightSprite.setVisible(false);
            }
        }));

        mEngine.registerUpdateHandler(new TimerHandler(0.6f + pOffset * 0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                pLightSprite.setVisible(true);
            }
        }));
        mEngine.registerUpdateHandler(new TimerHandler(0.8f + pOffset * 0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                pLightSprite.setVisible(false);
            }
        }));


        return returnStatus;
    }
}
