package com.spisoft.spcontrolbox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nshmura.snappysmoothscroller.LinearLayoutScrollVectorDetector;
import com.nshmura.snappysmoothscroller.SnappySmoothScroller;
import com.spisoft.spcircleview.CircleView;

public class SpCtrlBox extends RelativeLayout {

    private Context mContext;
    private RecyclerView IncRecyclerView;
    private LinearLayoutManager HorizontalLayout;
    private TextView vText, vText_n;
    private CircleView IvsHead;
    private CircleView vEdit;
    private CircleView vAdd;
    private Drawable mIconAdd, mIconEdit, mIconSave, mIconCancel;
    private RelativeLayout vMain;
    private OnAddClickTaskListener mOnAddClickTaskListener;
    private OnEditClickTaskListener mOnEditClickTaskListener;
    private boolean isEditMode = false;
    private boolean isViewBottom = false;
    private View LyCnt;
    private CircleView vBefore, vBefore_n, vNext, vNext_n;

    public SpCtrlBox(Context context) {
        super(context);
        init(context, null , -1);
    }

    public SpCtrlBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, null , -1);
    }

    public SpCtrlBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, null , -1);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SpCtrlBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, null , -1);
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void init(Context context, AttributeSet attrs, int defStyle) {
        View rootView = inflate(context, R.layout.sps_ctrl_box, SpCtrlBox.this);

        mContext = context;

        IvsHead = rootView.findViewById(R.id.cv_head);
        IncRecyclerView = rootView.findViewById(R.id.incRecyclerView);

        LyCnt = rootView.findViewById(R.id.lyCnt);
        vBefore = rootView.findViewById(R.id.cv_before);
        vBefore_n = rootView.findViewById(R.id.cv_before_n);
        vNext = rootView.findViewById(R.id.cv_next);
        vNext_n = rootView.findViewById(R.id.cv_next_n);

        vEdit = rootView.findViewById(R.id.cv_edit);
        vAdd = rootView.findViewById(R.id.cv_add);

        vMain = rootView.findViewById(R.id.lyMain);

        vText = rootView.findViewById(R.id.txtDesc);
        vText_n = rootView.findViewById(R.id.txtDesc_n);

        Configuration config = getResources().getConfiguration();
        if(config.getLayoutDirection() != View.LAYOUT_DIRECTION_RTL) {
            vBefore.setIcon(getResources().getDrawable(R.drawable.ic_baseline_navigate_before_24));
            vBefore_n.setIcon(getResources().getDrawable(R.drawable.ic_baseline_navigate_before_24));
            vNext.setIcon(getResources().getDrawable(R.drawable.ic_baseline_navigate_next_24));
            vNext_n.setIcon(getResources().getDrawable(R.drawable.ic_baseline_navigate_next_24));
        }else {
            vBefore.setIcon(getResources().getDrawable(R.drawable.ic_baseline_navigate_next_24));
            vBefore_n.setIcon(getResources().getDrawable(R.drawable.ic_baseline_navigate_next_24));
            vNext.setIcon(getResources().getDrawable(R.drawable.ic_baseline_navigate_before_24));
            vNext_n.setIcon(getResources().getDrawable(R.drawable.ic_baseline_navigate_before_24));
        }

        vNext.setOnClickListener(v -> {
            int __CurrentPosition = HorizontalLayout.findFirstVisibleItemPosition();
            if(__CurrentPosition < HorizontalLayout.getItemCount())
                HorizontalLayout.smoothScrollToPosition(IncRecyclerView, null ,HorizontalLayout.findFirstVisibleItemPosition()+1);
//                    HorizontalLayout.scrollToPositionWithOffset(HorizontalLayout.findFirstVisibleItemPosition()+1, 0);
        });

        vNext_n.setOnClickListener(v -> {
            int __CurrentPosition = HorizontalLayout.findFirstVisibleItemPosition();
            if(__CurrentPosition < HorizontalLayout.getItemCount())
                HorizontalLayout.smoothScrollToPosition(IncRecyclerView, null ,HorizontalLayout.findFirstVisibleItemPosition()+1);
//                    HorizontalLayout.scrollToPositionWithOffset(HorizontalLayout.findFirstVisibleItemPosition()+1, 0);
        });

        vBefore.setOnClickListener(v -> {
            int __CurrentPosition = HorizontalLayout.findFirstVisibleItemPosition();
            if(__CurrentPosition > 0)
                HorizontalLayout.smoothScrollToPosition(IncRecyclerView, null ,HorizontalLayout.findFirstVisibleItemPosition()-1);
//                    HorizontalLayout.scrollToPositionWithOffset(HorizontalLayout.findFirstVisibleItemPosition()-1, 0);
        });

        vBefore_n.setOnClickListener(v -> {
            int __CurrentPosition = HorizontalLayout.findFirstVisibleItemPosition();
            if(__CurrentPosition > 0)
                HorizontalLayout.smoothScrollToPosition(IncRecyclerView, null ,HorizontalLayout.findFirstVisibleItemPosition()-1);
//                    HorizontalLayout.scrollToPositionWithOffset(HorizontalLayout.findFirstVisibleItemPosition()-1, 0);
        });

        vEdit.setOnClickListener(v -> {
            mOnEditClickTaskListener.onEvent(isEditMode, HorizontalLayout.findFirstVisibleItemPosition());
            if (!isEditMode)
                setMode(true, -1);
        });

        vAdd.setOnClickListener(v -> {
            mOnAddClickTaskListener.onEvent(isEditMode);
            if (!isEditMode)
                setMode(true, -1);
        });

        //-------------------------------------------
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SpCtrlBox, defStyle, 0);

        isViewBottom = a.getBoolean(R.styleable.SpCtrlBox_view_bottom, false);

        int drawableResId_Add = a.getResourceId(R.styleable.SpCtrlBox_icon_add, -1);
        if(drawableResId_Add >= 0)
            mIconAdd = getResources().getDrawable(drawableResId_Add);
        else
            mIconAdd = getResources().getDrawable(R.drawable.ic_baseline_add_24);
        vAdd.setIcon(mIconAdd);

        int drawableResId_Edit = a.getResourceId(R.styleable.SpCtrlBox_icon_edit, -1);
        if(drawableResId_Edit >= 0)
            mIconEdit = getResources().getDrawable(drawableResId_Edit);
        else
            mIconEdit = getResources().getDrawable(R.drawable.ic_baseline_edit_24);
        vEdit.setIcon(mIconEdit);

//        int drawableResId_Add = a.getResourceId(R.styleable.SpLateBox_icon_add, -1);
//        if(drawableResId_Add >= 0) vAdd.setIcon(getResources().getDrawable(drawableResId_Add));

//        int drawableResId_Edit = a.getResourceId(R.styleable.SpLateBox_icon_edit, -1);
//        if(drawableResId_Edit >= 0) vEdit.setIcon(getResources().getDrawable(drawableResId_Edit));

        int drawableResId_Save = a.getResourceId(R.styleable.SpCtrlBox_icon_save, -1);
        if(drawableResId_Save >= 0)
            mIconSave = getResources().getDrawable(drawableResId_Save);
        else
            mIconSave = getResources().getDrawable(R.drawable.ic_baseline_check_24);

        int drawableResId_Cancel = a.getResourceId(R.styleable.SpCtrlBox_icon_cancel, -1);
        if(drawableResId_Cancel >= 0)
            mIconCancel = getResources().getDrawable(drawableResId_Cancel);
        else
            mIconCancel = getResources().getDrawable(R.drawable.ic_baseline_close_24);

        a.recycle();
    }

    public void setMode(boolean editMode, int currentPosition){
        if (editMode)
        {
            vEdit.setVisibility(VISIBLE);
            vEdit.setIcon(mIconSave);
            vAdd.setIcon(mIconCancel);
            SetViewVisible(false);
            IncRecyclerView.setVisibility(GONE);
            vMain.setVisibility(VISIBLE);
        }
        else
        {
            RefreshCntText();
            if(IncRecyclerView.getAdapter() == null || IncRecyclerView.getAdapter().getItemCount() == 0){
                vEdit.setVisibility(GONE);
                SetViewVisible(false);
            }
            else
                SetViewVisible(true);

            vEdit.setIcon(mIconEdit);
            vAdd.setIcon(mIconAdd);
            IncRecyclerView.setVisibility(VISIBLE);
            vMain.setVisibility(GONE);

            if(currentPosition >= 0)
                HorizontalLayout.smoothScrollToPosition(IncRecyclerView, null , currentPosition);
        }

        isEditMode = editMode;
        invalidate();
    }

    private void SetViewVisible(boolean visible) {
        if(visible){
            if(isViewBottom)
                LyCnt.setVisibility(VISIBLE);
            else {
                vText_n.setVisibility(VISIBLE);
                vBefore_n.setVisibility(VISIBLE);
                vNext_n.setVisibility(VISIBLE);
            }
        } else {
            if(isViewBottom)
                LyCnt.setVisibility(GONE);
            else {
                vText_n.setVisibility(GONE);
                vBefore_n.setVisibility(GONE);
                vNext_n.setVisibility(GONE);
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void SetHeadSrc(int headSrc, int fillColor, int strokeColor){
        IvsHead.setIcon(getResources().getDrawable(headSrc));
        if(strokeColor != 0) IvsHead.setStrokeColor(strokeColor);
        if(fillColor != 0) IvsHead.setFillColor(fillColor);
        invalidate();
    }

    public void SetList(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter){
        IncRecyclerView.setAdapter(adapter);
        HorizontalLayout = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                SnappySmoothScroller scroller = new SnappySmoothScroller.Builder()
                        .setPosition(position)
                        .setScrollVectorDetector(new LinearLayoutScrollVectorDetector(this))
                        .build(recyclerView.getContext());

                startSmoothScroll(scroller);
            }

            @Override
            public boolean canScrollHorizontally() {
                return super.canScrollHorizontally();
//                return false;
            }
        };

        IncRecyclerView.setLayoutManager(HorizontalLayout);
        HorizontalLayout.setSmoothScrollbarEnabled(false);

        IncRecyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return true;
            }
        });

        IncRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RefreshCntText();
            }
        });

        if(adapter.getItemCount() > 0){
            SetViewVisible(true);
            vEdit.setVisibility(VISIBLE);
        }

        IncRecyclerView.invalidate();
    }

    @SuppressLint("SetTextI18n")
    private void RefreshCntText() {
        int mItem = HorizontalLayout.findFirstVisibleItemPosition()+1;
        String CurrentItem = String.valueOf(mItem);
        vText.setText("  " + CurrentItem + "/" + HorizontalLayout.getItemCount() + "  ");
        vText_n.setText("  " + CurrentItem + "/" + HorizontalLayout.getItemCount() + "  ");

        if(mItem == 1){
            vBefore.setAlpha(60);
            vBefore_n.setAlpha(60);
        } else {
            vBefore.setAlpha(255);
            vBefore_n.setAlpha(255);
        }

        if(mItem == HorizontalLayout.getItemCount()){
            vNext.setAlpha(50);
            vNext_n.setAlpha(50);
        } else {
            vNext.setAlpha(255);
            vNext_n.setAlpha(255);
        }
    }

    public void AddView(View view){
        vMain.addView(view);
        invalidate();
    }

    public interface OnAddClickTaskListener {
        void onEvent(boolean editMode);
    }

    public void setOnAddClickTaskListener(OnAddClickTaskListener eventListener) {
        mOnAddClickTaskListener = eventListener;
    }

    public interface OnEditClickTaskListener {
        void onEvent(boolean editMode, int currentPosition);
    }

    public void setOnEditClickTaskListener(OnEditClickTaskListener eventListener) {
        mOnEditClickTaskListener = eventListener;
    }
}
