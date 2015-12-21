package org.andengine.util.modifier;

import org.andengine.util.exception.AndEngineRuntimeException;

import java.util.Comparator;


/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 11:17:50 - 19.03.2010
 */
public interface IModifier<T> {
    // ===========================================================
    // Constants
    // ===========================================================

    Comparator<IModifier<?>> MODIFIER_COMPARATOR_DURATION_DESCENDING = new Comparator<IModifier<?>>() {
        @Override
        public int compare(final IModifier<?> pModifierA, final IModifier<?> pModifierB) {
            final float durationA = pModifierA.getDuration();
            final float durationB = pModifierB.getDuration();

            if (durationA < durationB) {
                return 1;
            } else if (durationA > durationB) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    // ===========================================================
    // Methods
    // ===========================================================

    void reset();

    boolean isFinished();

    boolean isAutoUnregisterWhenFinished();

    void setAutoUnregisterWhenFinished(final boolean pRemoveWhenFinished);

    IModifier<T> deepCopy() throws DeepCopyNotSupportedException;

    float getSecondsElapsed();

    float getDuration();

    float onUpdate(final float pSecondsElapsed, final T pItem);

    void addModifierListener(final IModifierListener<T> pModifierListener);

    boolean removeModifierListener(final IModifierListener<T> pModifierListener);

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

    interface IModifierListener<T> {
        // ===========================================================
        // Constants
        // ===========================================================

        // ===========================================================
        // Methods
        // ===========================================================

        void onModifierStarted(final IModifier<T> pModifier, final T pItem);

        void onModifierFinished(final IModifier<T> pModifier, final T pItem);
    }

    class DeepCopyNotSupportedException extends AndEngineRuntimeException {
        // ===========================================================
        // Constants
        // ===========================================================

        private static final long serialVersionUID = -5838035434002587320L;

        // ===========================================================
        // Fields
        // ===========================================================

        // ===========================================================
        // Constructors
        // ===========================================================

        // ===========================================================
        // Getter & Setter
        // ===========================================================

        // ===========================================================
        // Methods for/from SuperClass/Interfaces
        // ===========================================================

        // ===========================================================
        // Methods
        // ===========================================================

        // ===========================================================
        // Inner and Anonymous Classes
        // ===========================================================
    }
}
