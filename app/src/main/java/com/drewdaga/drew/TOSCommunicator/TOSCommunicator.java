package com.drewdaga.drew.TOSCommunicator;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class TOSCommunicator extends SimpleBaseGameActivity {

    private static final String LOG_TAG = "TOSCommunicator";
    private static int CAMERA_BASE_WIDTH = 480;
    private static int CAMERA_BASE_HEIGHT = 800;
    private static int CAMERA_WIDTH = 480;
    private static int CAMERA_HEIGHT = 800;
    private static float CAMERA_ACTUAL_OVER_BASE = 1;
    private Sound mSoundComputerBeep, mSoundClick, mSoundSpockFascinating, mPhotonTorpedo, mSoundLiveLongAndProsper, mSoundStun, mSoundTribble, mSoundNeedYou, mSoundCommChirp;//, mSoundBeamMeAboard;
    private Music mSpockCaptainJim, mSoundBeamMeAboard;
    private ITextureRegion mCircleThingTR, mPanelTR, mSecurityButtonTR, mCommandButtonTR, mMedicalButtonTR, mSecurityLightOffTR, mSecurityLightOnTR, mMedicalLightOffTR, mMedicalLightOnTR, mCommandLightOffTR, mCommandLightOnTR, mBackgroundTR;
    private ITiledTextureRegion mCommandLightTTR;
    private static String mFilenameSecurityOne, mFilenameMedicalOne, mFilenameCommandOne, mFilenameMedicalTwo, mFilenameCommandTwo,
            mFilenameSecurityTwo, mFilenameCommandThree, mFilenameSecurityThree, mFilenameMedicalThree = null;
    private Sprite medicalLightOnSprite, securityLightOnSprite, commandLightOnSprite;
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;

    private enum ModeType {
        SOUNDBOARD_MODE, RECORDER_PLAYBACK_MODE, RECORDER_RECORD_MODE
    }

    private Boolean mIsRecording = false;
    private final int TOTAL_LEDS = 3;

    //Set the current mode
    private ModeType itsCurrentMode = ModeType.SOUNDBOARD_MODE;

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
        } else {
            cameraWidth = point.y;
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


        } catch (IOException e) {
            Debug.e(e);
        }

        try {
            mSoundComputerBeep = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(),
                    "aud/click.wav");

            mSoundClick = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(),
                    "aud/click.wav");

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

        mFilenameSecurityOne = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilenameSecurityOne += "/TOSSecurityOne.3gp";

        mFilenameMedicalOne = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilenameMedicalOne += "/TOSMedicalOne.3gp";

        mFilenameCommandOne = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilenameCommandOne += "/TOSCommandOne.3gp";

        mFilenameSecurityTwo = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilenameSecurityTwo += "/TOSSecurityTwo.3gp";

        mFilenameMedicalTwo = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilenameMedicalTwo += "/TOSMedicalTwo.3gp";

        mFilenameCommandTwo = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilenameCommandTwo += "/TOSCommandTwo.3gp";

        mFilenameSecurityThree = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilenameSecurityThree += "/TOSSecurityThree.3gp";

        mFilenameMedicalThree = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilenameMedicalThree += "/TOSMedicalThree.3gp";

        mFilenameCommandThree = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilenameCommandThree += "/TOSCommandThree.3gp";

    }

    @Override
    protected Scene onCreateScene() {
        final Scene scene = new Scene();
        //scene.getBackground().setColor(Color.BLACK);

        //Set up the background
        Sprite backgroundSprite = new Sprite(0, 0, this.mBackgroundTR, getVertexBufferObjectManager());

        //Set up the panel of mode switch buttons
        Sprite panelSprite = new Sprite(CAMERA_WIDTH * 3 / 18, CAMERA_HEIGHT * 19 / 60, this.mPanelTR, getVertexBufferObjectManager());

        //Set up the sprite for the speaker thing
        Sprite circleThingSprite = new Sprite(CAMERA_WIDTH * 3 / 9, CAMERA_HEIGHT * 1 / 30, this.mCircleThingTR, getVertexBufferObjectManager());

        //Setup the LEDs
        securityLightOnSprite = new Sprite(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 7 / 30, this.mSecurityLightOnTR, getVertexBufferObjectManager());
        medicalLightOnSprite = new Sprite(CAMERA_WIDTH * 8 / 18, CAMERA_HEIGHT * 7 / 30, this.mMedicalLightOnTR, getVertexBufferObjectManager());
        commandLightOnSprite = new Sprite(CAMERA_WIDTH * 12 / 18, CAMERA_HEIGHT * 7 / 30, this.mCommandLightOnTR, getVertexBufferObjectManager());

        Sprite commandLightOffSprite = new Sprite(CAMERA_WIDTH * 12 / 18, CAMERA_HEIGHT * 7 / 30, this.mCommandLightOffTR, getVertexBufferObjectManager());
        Sprite medicalLightOffSprite = new Sprite(CAMERA_WIDTH * 8 / 18, CAMERA_HEIGHT * 7 / 30, this.mMedicalLightOffTR, getVertexBufferObjectManager());
        Sprite securityLightOffSprite = new Sprite(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 7 / 30, this.mSecurityLightOffTR, getVertexBufferObjectManager());

        commandLightOnSprite.setVisible(false);
        medicalLightOnSprite.setVisible(false);
        securityLightOnSprite.setVisible(false);

        //Set up the mode switch buttons
        Button recordButton = new Button(CAMERA_WIDTH * 2 / 18, CAMERA_HEIGHT * 9 / 30, this.mSecurityButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                Boolean retVal = false;
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

                    itsCurrentMode = ModeType.RECORDER_RECORD_MODE;
                    resetLights(commandLightOnSprite);
                    resetLights(medicalLightOnSprite);
                    resetLights(securityLightOnSprite);
                    mSoundClick.play();
                    retVal = true;
                }
                return retVal;
            }
        };

        //Soundboard button setup
        Button soundboardButton = new Button(CAMERA_WIDTH * 7 / 18, CAMERA_HEIGHT * 9 / 30, this.mMedicalButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                boolean retValue = false;

                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

                    itsCurrentMode = ModeType.SOUNDBOARD_MODE;
                    resetLights(commandLightOnSprite);
                    resetLights(medicalLightOnSprite);
                    resetLights(securityLightOnSprite);
                    mSoundClick.play();
                    retValue = true;
                }
                return retValue;

            }

        };

        //Record playback button
        Button playbackButton = new Button(CAMERA_WIDTH * 12 / 18, CAMERA_HEIGHT * 9 / 30, this.mCommandButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                boolean retValue = false;

                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                    if (mIsRecording) {
                        //Stop recording - the filename doesn't matter
                        recorderRecord(mFilenameSecurityThree);
                    }
                    itsCurrentMode = ModeType.RECORDER_PLAYBACK_MODE;
                    resetLights(commandLightOnSprite);
                    resetLights(medicalLightOnSprite);
                    resetLights(securityLightOnSprite);
                    mSoundClick.play();
                    retValue = true;
                }
                return retValue;

            }

        };

        //Set up the 9 buttons

        //First red button
        Button securityButton1Sprite = new Button(CAMERA_WIDTH * 2 / 18, CAMERA_HEIGHT * 14 / 30, this.mSecurityButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                boolean retValue = false;
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

                    switch (itsCurrentMode) {
                        case SOUNDBOARD_MODE: {
                            mSoundStun.play();
                            flickerLights(securityLightOnSprite, 0);
                            flickerLights(medicalLightOnSprite, 1);
                            flickerLights(commandLightOnSprite, 2);
                            retValue = true;
                            break;

                        }
                        case RECORDER_PLAYBACK_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderPlayback(mFilenameSecurityOne);
                            break;
                        }
                        case RECORDER_RECORD_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderRecord(mFilenameSecurityOne);
                            break;
                        }
                        default: {
                            retValue = false;
                            break;
                        }
                    }
                } else {
                    retValue = true;
                }

                return retValue;
            }

        };

        //First blue button
        Button medicalButton1Sprite = new Button(CAMERA_WIDTH * 7 / 18, CAMERA_HEIGHT * 14 / 30, this.mMedicalButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                boolean retValue = false;
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

                    switch (itsCurrentMode) {
                        case SOUNDBOARD_MODE: {
                            mSoundLiveLongAndProsper.play();
                            flickerLights(securityLightOnSprite, 0);
                            flickerLights(medicalLightOnSprite, 1);
                            flickerLights(commandLightOnSprite, 2);
                            flickerLights(securityLightOnSprite, 5);
                            flickerLights(medicalLightOnSprite, 6);
                            flickerLights(commandLightOnSprite, 7);
                            retValue = true;
                            break;

                        }
                        case RECORDER_PLAYBACK_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderPlayback(mFilenameMedicalOne);
                            break;
                        }
                        case RECORDER_RECORD_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderRecord(mFilenameMedicalOne);
                            break;
                        }
                        default: {
                            retValue = false;
                            break;
                        }
                    }
                } else {
                    retValue = true;
                }

                return retValue;
            }

        };

        //First yellow button
        Button commandButton1Sprite = new Button(CAMERA_WIDTH * 12 / 18, CAMERA_HEIGHT * 14 / 30, this.mCommandButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                boolean retValue = false;
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

                    switch (itsCurrentMode) {
                        case SOUNDBOARD_MODE: {
                            mSoundCommChirp.play();
                            flickerLights(securityLightOnSprite, 0);
                            flickerLights(medicalLightOnSprite, 1);
                            flickerLights(commandLightOnSprite, 2);
                            retValue = true;
                            break;

                        }
                        case RECORDER_PLAYBACK_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderPlayback(mFilenameCommandOne);
                            break;
                        }
                        case RECORDER_RECORD_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderRecord(mFilenameCommandOne);
                            break;
                        }
                        default: {
                            retValue = false;
                            break;
                        }
                    }
                } else {
                    retValue = true;
                }

                return retValue;
            }

        };

        //Second red button
        Button securityButton2Sprite = new Button(CAMERA_WIDTH * 12 / 18, CAMERA_HEIGHT * 19 / 30, this.mSecurityButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                boolean retValue = false;
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

                    switch (itsCurrentMode) {
                        case SOUNDBOARD_MODE: {
                            mPhotonTorpedo.play();
                            flickerLights(securityLightOnSprite, 0);
                            flickerLights(medicalLightOnSprite, 1);
                            flickerLights(commandLightOnSprite, 2);
                            retValue = true;
                            break;

                        }
                        case RECORDER_PLAYBACK_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderPlayback(mFilenameSecurityTwo);
                            break;
                        }
                        case RECORDER_RECORD_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderRecord(mFilenameSecurityTwo);
                            break;
                        }
                        default: {
                            retValue = false;
                            break;
                        }
                    }
                } else {
                    retValue = true;
                }

                return retValue;
            }

        };

        //second blue button
        Button medicalButton2Sprite = new Button(CAMERA_WIDTH * 2 / 18, CAMERA_HEIGHT * 19 / 30, this.mMedicalButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                boolean retValue = false;
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

                    switch (itsCurrentMode) {
                        case SOUNDBOARD_MODE: {
                            mSpockCaptainJim.play();
                            flickerLights(securityLightOnSprite, 0);
                            flickerLights(medicalLightOnSprite, 1);
                            flickerLights(commandLightOnSprite, 2);
                            flickerLights(securityLightOnSprite, 30);
                            flickerLights(medicalLightOnSprite, 31);
                            flickerLights(commandLightOnSprite, 32);
                            retValue = true;
                            break;

                        }
                        case RECORDER_PLAYBACK_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderPlayback(mFilenameMedicalTwo);
                            break;
                        }
                        case RECORDER_RECORD_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderRecord(mFilenameMedicalTwo);
                            break;
                        }
                        default: {
                            retValue = false;
                            break;
                        }
                    }
                } else {
                    retValue = true;
                }

                return retValue;
            }

        };

        //Second yellow button
        Button commandButton2Sprite = new Button(CAMERA_WIDTH * 7 / 18, CAMERA_HEIGHT * 19 / 30, this.mCommandButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                boolean retValue = false;
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

                    switch (itsCurrentMode) {
                        case SOUNDBOARD_MODE: {
                            mSoundNeedYou.play();
                            flickerLights(securityLightOnSprite, 0);
                            flickerLights(medicalLightOnSprite, 1);
                            flickerLights(commandLightOnSprite, 2);
                            flickerLights(securityLightOnSprite, 12);
                            flickerLights(medicalLightOnSprite, 13);
                            flickerLights(commandLightOnSprite, 14);
                            retValue = true;
                            break;

                        }
                        case RECORDER_PLAYBACK_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderPlayback(mFilenameCommandTwo);
                            break;
                        }
                        case RECORDER_RECORD_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderRecord(mFilenameCommandTwo);
                            break;
                        }
                        default: {
                            retValue = false;
                            break;
                        }
                    }
                } else {
                    retValue = true;
                }

                return retValue;
            }

        };

        //Third red button
        Button securityButton3Sprite = new Button(CAMERA_WIDTH * 7 / 18, CAMERA_HEIGHT * 24 / 30, this.mSecurityButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                boolean retValue = false;
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

                    switch (itsCurrentMode) {
                        case SOUNDBOARD_MODE: {
                            mSoundTribble.play();
                            flickerLights(securityLightOnSprite, 0);
                            flickerLights(medicalLightOnSprite, 1);
                            flickerLights(commandLightOnSprite, 2);
                            flickerLights(securityLightOnSprite, 15);
                            flickerLights(medicalLightOnSprite, 16);
                            flickerLights(commandLightOnSprite, 17);
                            flickerLights(securityLightOnSprite, 30);
                            flickerLights(medicalLightOnSprite, 31);
                            flickerLights(commandLightOnSprite, 32);
                            retValue = true;
                            break;

                        }
                        case RECORDER_PLAYBACK_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderPlayback(mFilenameSecurityThree);
                            break;
                        }
                        case RECORDER_RECORD_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderRecord(mFilenameSecurityThree);
                            break;
                        }
                        default: {
                            retValue = false;
                            break;
                        }
                    }
                } else {
                    retValue = true;
                }

                return retValue;
            }

        };

        //Third blue button
        Button medicalButton3Sprite = new Button(CAMERA_WIDTH * 12 / 18, CAMERA_HEIGHT * 24 / 30, this.mMedicalButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                boolean retValue = false;
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

                    switch (itsCurrentMode) {
                        case SOUNDBOARD_MODE: {
                            mSoundSpockFascinating.play();
                            flickerLights(securityLightOnSprite, 0);
                            flickerLights(medicalLightOnSprite, 1);
                            flickerLights(commandLightOnSprite, 2);
                            retValue = true;
                            break;

                        }
                        case RECORDER_PLAYBACK_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderPlayback(mFilenameMedicalThree);
                            break;
                        }
                        case RECORDER_RECORD_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderRecord(mFilenameMedicalThree);
                            break;
                        }
                        default: {
                            retValue = false;
                            break;
                        }
                    }
                } else {
                    retValue = true;
                }

                return retValue;
            }

        };

        //Third yellow button
        Button commandButton3Sprite = new Button(CAMERA_WIDTH * 2 / 18, CAMERA_HEIGHT * 24 / 30, this.mCommandButtonTR, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                boolean retValue = false;
                if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

                    switch (itsCurrentMode) {
                        case SOUNDBOARD_MODE: {
                            mSoundBeamMeAboard.play();
                            flickerLights(securityLightOnSprite, 3);
                            flickerLights(medicalLightOnSprite, 4);
                            flickerLights(commandLightOnSprite, 5);
                            retValue = true;
                            break;

                        }
                        case RECORDER_PLAYBACK_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderPlayback(mFilenameCommandThree);
                            break;
                        }
                        case RECORDER_RECORD_MODE: {
                            mSoundComputerBeep.play();
                            retValue = recorderRecord(mFilenameCommandThree);
                            break;
                        }
                        default: {
                            retValue = false;
                            break;
                        }
                    }
                } else {
                    retValue = true;
                }

                return retValue;
            }

        };

        //Setup the sizes
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
        recordButton.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);
        soundboardButton.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);
        playbackButton.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);
        medicalButton1Sprite.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);
        commandButton1Sprite.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);

        securityButton2Sprite.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);
        medicalButton2Sprite.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);
        commandButton2Sprite.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);

        securityButton3Sprite.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);
        medicalButton3Sprite.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);
        commandButton3Sprite.setSize(CAMERA_WIDTH * 4 / 18, CAMERA_HEIGHT * 4 / 30);

        float test = panelSprite.getHeight();

        //Attach all the sprites to the scene
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
        scene.attachChild(recordButton);
        scene.attachChild(soundboardButton);
        scene.attachChild(playbackButton);

        //Register touchable areas for mode buttons and sound buttons
        scene.registerTouchArea(commandButton1Sprite);
        scene.registerTouchArea(commandButton2Sprite);
        scene.registerTouchArea(commandButton3Sprite);
        scene.registerTouchArea(medicalButton1Sprite);
        scene.registerTouchArea(medicalButton2Sprite);
        scene.registerTouchArea(medicalButton3Sprite);
        scene.registerTouchArea(securityButton1Sprite);
        scene.registerTouchArea(securityButton2Sprite);
        scene.registerTouchArea(securityButton3Sprite);
        scene.registerTouchArea(recordButton);
        scene.registerTouchArea(soundboardButton);
        scene.registerTouchArea(playbackButton);

        //Hide the buttons to switch modes
        recordButton.setVisible(false);
        soundboardButton.setVisible(false);
        playbackButton.setVisible(false);

        return scene;
    }

    protected Boolean flickerLights(final Sprite pLightSprite, final int pOffset) {
        Boolean returnStatus = false;

        float i = 0.2f;
        //Flicker the lights 2 times //- on off - on off
        for (i = 0.2f; i < 1; i += .2f) {
            mEngine.registerUpdateHandler(new TimerHandler(i + pOffset * 0.1f, new ITimerCallback() {
                public void onTimePassed(final TimerHandler pTimerHandler) {
                    mEngine.unregisterUpdateHandler(pTimerHandler);
                    boolean currentLEDState = pLightSprite.isVisible();
                    currentLEDState = currentLEDState ^ true;
                    pLightSprite.setVisible(currentLEDState);
                }
            }));
        }
        //Reset the lights after flickering has finished
        mEngine.registerUpdateHandler(new TimerHandler(i + pOffset * 0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                resetLights(pLightSprite);
            }
        }));

        return returnStatus;
    }

    //Put the leds back to the correct configuration for the current mode
    protected Boolean resetLights(final Sprite pLightSprite) {
        Boolean retVal = false;

        switch (itsCurrentMode) {
            case SOUNDBOARD_MODE: {
                medicalLightOnSprite.setVisible(true);
                securityLightOnSprite.setVisible(false);
                commandLightOnSprite.setVisible(false);
                retVal = true;
                break;

            }
            case RECORDER_PLAYBACK_MODE: {
                medicalLightOnSprite.setVisible(false);
                securityLightOnSprite.setVisible(false);
                commandLightOnSprite.setVisible(true);
                retVal = true;
                break;
            }
            case RECORDER_RECORD_MODE: {
                medicalLightOnSprite.setVisible(false);
                securityLightOnSprite.setVisible(true);
                commandLightOnSprite.setVisible(false);
                retVal = true;
                break;
            }
            default: {
                break;
            }
        }


        return retVal;

    }

    //Blink the playback LED while the recorded sound is playing
    protected Boolean playbackBlinkLED() {
        Boolean retVal = false;
        //Flicker the lights once in half a second
        mEngine.registerUpdateHandler(new TimerHandler(0.5f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                boolean currentLEDState = commandLightOnSprite.isVisible();
                currentLEDState = currentLEDState ^ true;
                commandLightOnSprite.setVisible(currentLEDState);
                //If still playing
                if (mPlayer.isPlaying()) {
                    //Blink it again in a bit
                    playbackBlinkLED();
                } else {
                    //Put the LEDs back the way they should be
                    resetLights(commandLightOnSprite);

                    //Release the resources
                    mPlayer.release();
                    mPlayer = null;

                }
            }
        }));

        return retVal;
    }

    //Blink the record LED while a recording is in progress
    protected Boolean recordBlinkLED() {
        Boolean retVal = false;
        //Flicker the lights once in half a second
        mEngine.registerUpdateHandler(new TimerHandler(0.5f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                boolean currentLEDState = securityLightOnSprite.isVisible();
                currentLEDState = currentLEDState ^ true;
                securityLightOnSprite.setVisible(currentLEDState);
                //If still recording
                if (mIsRecording) {
                    //Blink it again in a bit
                    recordBlinkLED();
                } else {
                    //Put the LEDs back the way they should be
                    resetLights(securityLightOnSprite);
                }
            }
        }));

        return retVal;
    }

    //playback the previously recorded clip
    protected Boolean recorderPlayback(String pFilename) {
        Boolean retValue = false;

        //If it isn't currently playing
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(pFilename);
                mPlayer.prepare();
                mPlayer.start();

                //start blinking the playback led
                playbackBlinkLED();

            //if the playback fails
            } catch (IOException e) {
                mPlayer = null;
                ///print a log message
                Log.e(LOG_TAG, "prepare() failed");
            }
            retValue = true;
        }
        return retValue;
    }

    //record a clip with the corresponding filename
    protected Boolean recorderRecord(String pFilename) {
        Boolean retValue = false;

        //If it is already recording
        if (mIsRecording) {
            //stop recording the current clip
            mIsRecording = false;
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }

        //otherwise start recording
        else {
            mIsRecording = true;
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(pFilename);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            //if preparing the recorder fails
            try {
                mRecorder.prepare();
            } catch (IOException e) {
                //log the error
                Log.e(LOG_TAG, "prepare() failed");
            }

            mRecorder.start();

            //Begin blinking the record LED
            recordBlinkLED();

        }
        retValue = true;
        return retValue;
    }
}