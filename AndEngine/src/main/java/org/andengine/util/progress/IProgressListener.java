package org.andengine.util.progress;


/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 18:07:35 - 09.07.2009
 */
public interface IProgressListener {
    // ===========================================================
    // Constants
    // ===========================================================

    int PROGRESS_MIN = 0;
    int PROGRESS_MAX = 100;

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * @param pProgress between 0 and 100.
     */
    void onProgressChanged(final int pProgress);
}