package com.n0ize.wait;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.n0ize.wait.config.WaitConfiguration;
import com.n0ize.wait.events.OnConnectedListener;
import com.n0ize.wait.models.State;
import com.n0ize.wait.utils.AttrUtils;
import com.n0ize.wait.utils.NetworkUtils;

import static com.n0ize.wait.helpers.LogHelper.tag;

/**
 * n0ise on 3/2/2017.
 */
public class WaitLayout extends RelativeLayout {

    //region -properties
    private Context context;
    private ViewGroup container;
    private View emptyLayout;
    private View waitLayout;
    private View loadingLayout;
    private View retryLayout;
    private TextView retryMessage, emptyMessage;
    private View workingLayout;
    private ProgressBar loadingProgressBar, workingProgressBar;
    private Button retryButton;
    private ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
    );

    //endregion
    //region +events
    private OnConnectedListener onConnectedListener;

    public void setOnConnectedListener(OnConnectedListener onConnectedListener) {
        this.onConnectedListener = onConnectedListener;
    }

    //endregion
    //region -methods
    private void removeChildView(View view) {
        if (container != null) {
            container.removeView(view);
        } else Log.e(tag, "WaitLayout.removeChildView: on no, impossible, null container.");
    }

    private void replaceView(View currentView, int newViewRes) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View newView = inflater.inflate(newViewRes, null, false);
        replaceView(currentView, newView);
    }

    private void replaceView(View currentView, View newView) {
        if (container == null) {
            return;
        } else Log.e(tag, "WaitLayout.replaceView: on no, impossible, null container.");

        final int index = container.indexOfChild(currentView);
        removeChildView(currentView);
        matchViewToParent(newView);
        container.addView(newView, index);
    }

    //assign view resources in your App class
    //it will use default view resources if could'nt find any valid view
    private void setupViews() {
        LayoutInflater inflater = LayoutInflater.from(context);
        emptyLayout = inflater.inflate(Wait.getConfiguration().getEmptyLayout(), null, false);
        loadingLayout = inflater.inflate(Wait.getConfiguration().getLoadingViewRes(), null, false);
        retryLayout = inflater.inflate(Wait.getConfiguration().getRetryViewRes(), null, false);
        workingLayout = inflater.inflate(Wait.getConfiguration().getWorkingViewRes(), null, false);

        setupView(emptyLayout);
        setupView(loadingLayout);
        setupView(retryLayout);
        setupView(workingLayout);

        bindRetryButton();
    }

    private void bindRetryButton() {
        View retryLayout = this.retryLayout.findViewById(R.id.retry_button);
        if (retryLayout != null)
            retryLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    //callback to single listener
                    if (NetworkUtils.isNetworkAvailable(context))
                        if (onConnectedListener != null)
                            onConnectedListener.OnConnected();

                    //multiple listeners callback
                    if (Wait.getOnConnectedListeners() != null)
                        for (OnConnectedListener listener : Wait.getOnConnectedListeners())
                            if (listener != null)
                                listener.OnConnected();
                }
            });

    }

    private void setupView(View view) {
        //replace layouts backgrounds and make them clickable
        view.setClickable(true);
        if (view.getBackground() == null)
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.background_holo_light));

        int backgroundColor = AttrUtils.getColor(context, R.attr.wt_background_color);
        if (backgroundColor != -1) {
            view.setBackgroundColor(backgroundColor);
        }
        matchViewToParent(view);
        //add views to container
        container.addView(view);
    }

    private void bindViews() {
        emptyLayout = findViewById(R.id.empty_layout);
        emptyMessage = (TextView) emptyLayout.findViewById(R.id.empty_message);
        waitLayout = findViewById(R.id.wait_layout);
        loadingLayout = findViewById(R.id.loading_layout);
        retryLayout = findViewById(R.id.retry_layout);
        retryMessage = (TextView) retryLayout.findViewById(R.id.message);
        workingLayout = findViewById(R.id.working_layout);
        loadingProgressBar = (ProgressBar) loadingLayout.findViewById(R.id.progress_bar);
        workingProgressBar = (ProgressBar) workingLayout.findViewById(R.id.progress_bar);
        retryButton = (Button) retryLayout.findViewById(R.id.retry_button);
    }

    private void initializeViews() {
        WaitConfiguration conf = Wait.getConfiguration();
        Typeface typeface = conf.getTypeFace();

        //set empty message
        if (emptyMessage != null) {
            emptyMessage.setText(conf.getEmptyMessage());
            emptyMessage.setVisibility(conf.isEmptyMessageVisible() ? VISIBLE : GONE);
            if (typeface != null)
                emptyMessage.setTypeface(typeface);
        }

        //set retry message
        if (retryMessage != null) {
            retryMessage.setText(conf.getRetryMessage());
            retryMessage.setVisibility(conf.isRetryMessageVisible() ? VISIBLE : GONE);
            if (typeface != null)
                retryMessage.setTypeface(typeface);
        }

        //set retry button text inside retry layout
        if (retryButton != null) {
            retryButton.setText(conf.getRetryButtonText());
            if (typeface != null)
                retryButton.setTypeface(typeface);
        }

        //set loading layout progress color
        int loadingProgressBarColor = AttrUtils.getColor(context, R.attr.wt_loading_progress_color);
        if (loadingProgressBar != null && loadingProgressBarColor != -1)
            loadingProgressBar.getIndeterminateDrawable().setColorFilter(loadingProgressBarColor,
                    android.graphics.PorterDuff.Mode.SRC_IN);

        //set working layout progress color
        int workingProgressBarColor = AttrUtils.getColor(context, R.attr.wt_working_progress_color);
        if (workingProgressBar != null && loadingProgressBarColor != -1)
            workingProgressBar.getIndeterminateDrawable().setColorFilter(workingProgressBarColor,
                    android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private void matchViewToParent(View view) {
        view.setLayoutParams(layoutParams);
    }

    //endregion
    //region +methods
    public WaitLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        container = (ViewGroup) inflate(context, R.layout.wait_layout, this);
        setupViews();
        bindViews();
        initializeViews();
        setState(State.NONE);
    }

    //use this method at runtime
    public void setState(State state) {
        if (state == State.NONE) {
            waitLayout.setVisibility(GONE);
        } else {

            waitLayout.setVisibility(VISIBLE);
            emptyLayout.setVisibility(GONE);
            loadingLayout.setVisibility(GONE);
            retryLayout.setVisibility(GONE);
            workingLayout.setVisibility(GONE);

            if (state == State.EMPTY) {
                emptyLayout.setVisibility(VISIBLE);
            } else if (state == State.LOADING) {
                loadingLayout.setVisibility(VISIBLE);
            } else if (state == State.RETRY) {
                retryLayout.setVisibility(VISIBLE);
            } else if (state == State.WORKING) {
                workingLayout.setVisibility(VISIBLE);
            }
        }
    }

    public void setLoadingViewRes(int resource) {
        if (resource == 0) {
            Log.e(tag, "WaitLayout.setStateView: " + "i cant use this invalid resource :)");
            return;
        }
        replaceView(loadingLayout, resource);
    }

    public void setWorkingViewRes(int resource) {
        if (resource == 0) {
            Log.e(tag, "WaitLayout.setStateView: " + "i cant use this invalid resource :)");
            return;
        }
        replaceView(workingLayout, resource);
    }

    public void setRetryViewRes(int resource) {
        if (resource == 0) {
            Log.e(tag, "WaitLayout.setStateView: " + "i cant use this invalid resource :)");
            return;
        }
        replaceView(retryLayout, resource);
    }

    public void setLoadingView(View view) {
        if (view == null) {
            Log.e(tag, "WaitLayout.setStateView: " + "i cant use this null view :)");
            return;
        }
        setupView(view);
        replaceView(loadingLayout, view);
    }

    public void setWorkingView(View view) {
        if (view == null) {
            Log.e(tag, "WaitLayout.setStateView: " + "i cant use this null view :)");
            return;
        }
        setupView(view);
        replaceView(workingLayout, view);
    }

    public void setEmptyView(View view) {
        if (view == null) {
            Log.e(tag, "WaitLayout.setEmptyView: " + "i cant use this null view :)");
            return;
        }
        setupView(view);
        replaceView(emptyLayout, view);
    }

    public void setRetryView(View view) {
        if (view == null) {
            Log.e(tag, "WaitLayout.setRetryView: " + "i cant use this null view :)");
            return;
        }
        setupView(view);
        bindRetryButton();
        replaceView(retryLayout, view);
    }

    public void setRetryMessage(String message) {
        retryMessage.setText(message);
    }

    public void showRetryMessage() {
        if (retryMessage == null) {
            return;
        } else Log.e(tag, "WaitLayout.showRetryMessage: i cant find retry message view, are'nt you using custom view?");

        retryMessage.setVisibility(VISIBLE);
    }

    public void hideRetryMessage() {
        if (retryMessage == null) {
            return;
        } else Log.e(tag, "WaitLayout.hideRetryMessage: i cant find retry message view, are'nt you using custom view?");

        retryMessage.setVisibility(GONE);
    }


    //endregion

}
