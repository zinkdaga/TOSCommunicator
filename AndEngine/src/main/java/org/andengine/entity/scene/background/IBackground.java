package org.andengine.entity.scene.background;

import org.andengine.engine.handler.IDrawHandler;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 13:47:41 - 19.07.2010
 */
public interface IBackground extends IDrawHandler, IUpdateHandler {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    void registerBackgroundModifier(final IModifier<IBackground> pBackgroundModifier);

    boolean unregisterBackgroundModifier(final IModifier<IBackground> pBackgroundModifier);

    void clearBackgroundModifiers();

    boolean isColorEnabled();

    void setColorEnabled(final boolean pColorEnabled);

    void setColor(final Color pColor);

    void setColor(final float pRed, final float pGreen, final float pBlue);

    void setColor(final float pRed, final float pGreen, final float pBlue, final float pAlpha);
}
