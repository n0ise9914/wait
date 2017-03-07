package com.n0ize.wait.config;

import android.graphics.Typeface;
import android.util.Log;
import com.n0ize.wait.R;

import static com.n0ize.wait.helpers.LogHelper.tag;

/**
 * n0ise on 3/4/2017.
 */
public class WaitConfiguration {
    private final Typeface typeFace;
    private final int emptyLayout;
    private final boolean retryMessageVisible;
    private final String retryButtonText;
    private final int workingViewRes;
    private final int loadingViewRes;
    private final int retryViewRes;
    private final String retryMessage;
    private final String emptyMessage;
    private boolean emptyMessageVisible;

    public int getEmptyLayout() {
        return emptyLayout;
    }

    public String getEmptyMessage() {
        return emptyMessage;
    }

    public String getRetryButtonText() {
        return retryButtonText;
    }

    public Typeface getTypeFace() {
        return typeFace;
    }

    public int getWorkingViewRes() {
        return workingViewRes;
    }

    public int getLoadingViewRes() {
        return loadingViewRes;
    }

    public int getRetryViewRes() {
        return retryViewRes;
    }

    public String getRetryMessage() {
        return retryMessage;
    }

    public boolean isRetryMessageVisible() {
        return retryMessageVisible;
    }

    WaitConfiguration(int emptyLayout, String emptyMessage, boolean emptyMessageVisible,
                      int loadingLayout, int retryLayout, int workingLayout, String retryMessage,
                      boolean retryMessageVisible, String retryButtonText, Typeface typeFace) {
        this.emptyLayout = emptyLayout;
        this.emptyMessage = emptyMessage;
        this.loadingViewRes = loadingLayout;
        this.retryViewRes = retryLayout;
        this.workingViewRes = workingLayout;
        this.retryMessage = retryMessage;
        this.retryMessageVisible = retryMessageVisible;
        this.emptyMessageVisible = emptyMessageVisible;
        this.retryButtonText = retryButtonText;
        this.typeFace = typeFace;
    }

    public boolean isEmptyMessageVisible() {
        return emptyMessageVisible;
    }

    public static class Builder {
        private int emptyViewRes;
        private int workingViewRes;
        private int loadingViewRes;
        private int retryViewRes;
        private String retryMessage;
        private boolean retryMessageVisible;
        private String retryButtonText;
        private Typeface typeFace;
        private String emptyMessage;
        private boolean emptyMessageVisible;

        public Builder() {
            retryMessageVisible = true;
            emptyMessageVisible = true;
        }

        public WaitConfiguration build() {
            if (emptyViewRes == 0) emptyViewRes = R.layout.empty_layout;
            if (loadingViewRes == 0) loadingViewRes = R.layout.loading_layout;
            if (workingViewRes == 0) workingViewRes = R.layout.working_layout;
            if (retryViewRes == 0) retryViewRes = R.layout.retry_layout;
            if (retryMessage == null) retryMessage = "Check your connection and try again.";
            if (retryMessage.equals("")) retryMessage = "Check your connection and try again.";

            if (emptyMessage == null) emptyMessage = "Nothing to show.";
            if (emptyMessage.equals("")) emptyMessage = "Nothing to show.";

            if (retryButtonText == null) retryButtonText = "retry";
            if (retryButtonText.equals("")) retryButtonText = "retry";


            Log.i(tag, "Builder.build: " + retryMessage);
            return new WaitConfiguration(
                    emptyViewRes,
                    emptyMessage,
                    emptyMessageVisible,
                    loadingViewRes,
                    retryViewRes,
                    workingViewRes,
                    retryMessage,
                    retryMessageVisible,
                    retryButtonText,
                    typeFace);
        }

        public Builder setEmptyViewRes(int emptyLayout) {
            this.emptyViewRes = emptyLayout;
            return this;
        }

        public Builder setLoadingViewRes(int loadingLayout) {
            this.loadingViewRes = loadingLayout;
            return this;
        }

        public Builder setWorkingViewRes(int workingView) {
            this.workingViewRes = workingView;
            return this;
        }

        public Builder setRetryViewRes(int retryLayout) {
            this.retryViewRes = retryLayout;
            return this;
        }

        public Builder setRetryMessage(String retryMessage) {
            this.retryMessage = retryMessage;
            return this;
        }

        public Builder setEmptyMessage(String emptyMessage) {
            this.emptyMessage = emptyMessage;
            return this;
        }

        public Builder showRetryMessage() {
            this.retryMessageVisible = true;
            return this;
        }

        public Builder hideRetryMessage() {
            this.retryMessageVisible = false;
            return this;
        }

        public Builder showEmptyMessage() {
            this.emptyMessageVisible = true;
            return this;
        }

        public Builder hideEmptyMessage() {
            this.emptyMessageVisible = false;
            return this;
        }

        public Builder setRetryButtonText(String retryButtonText) {
            this.retryButtonText = retryButtonText;
            return this;
        }

        public Builder setTypeFace(Typeface typeFace) {
            this.typeFace = typeFace;
            return this;
        }
    }

}
