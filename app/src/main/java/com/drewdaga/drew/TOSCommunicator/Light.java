package com.drewdaga.drew.TOSCommunicator;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Light extends Sprite {

    public Light(float pX, float pY, ITextureRegion pTextureRegion,
                 VertexBufferObjectManager vertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, vertexBufferObjectManager);
        // TODO Auto-generated constructor stub
    }

//	public void setTextureRegion(ITextureRegion textureRegion){
//		this.mTextureRegion = textureRegion;
//	}

//	public Boolean flicker(Engine pEngine){
//		Engine engine = pEngine;
//		Boolean returnStatus = false;
//		this.setVisible(false);
//		engine.registerUpdateHandler(new TimerHandler(0.2f, new ITimerCallback()
//	    {                                    
//	        public void onTimePassed(final TimerHandler pTimerHandler)
//	        {
//	            pTimerHandler.reset();
//	            getParent().unregisterUpdateHandler(pTimerHandler);
//	            engine.setVisible(true);
//	        }
//	    }));
//		getParent().registerUpdateHandler(new TimerHandler(0.4f, new ITimerCallback()
//	    {                                    
//	        public void onTimePassed(final TimerHandler pTimerHandler)
//	        {
//	            pTimerHandler.reset();
//	            getParent().unregisterUpdateHandler(pTimerHandler);
//	            getParent().setVisible(false);
//	        }
//	    }));
//		getParent().registerUpdateHandler(new TimerHandler(0.6f, new ITimerCallback()
//	    {                                    
//	        public void onTimePassed(final TimerHandler pTimerHandler)
//	        {
//	            pTimerHandler.reset();
//	            getParent().unregisterUpdateHandler(pTimerHandler);
//	            getParent().setVisible(true);
//	        }
//	    }));
//		
//		
//		return returnStatus;
//	}

}
