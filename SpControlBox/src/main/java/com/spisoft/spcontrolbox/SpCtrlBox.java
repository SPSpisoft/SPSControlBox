package com.spisoft.spcontrolbox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.nshmura.snappysmoothscroller.LinearLayoutScrollVectorDetector;
import com.nshmura.snappysmoothscroller.SnappySmoothScroller;
import com.spisoft.spcircleview.CircleView;

public class SpCtrlBox extends RelativeLayout {

    private Context mContext;
    private RecyclerView IncRecyclerView;
    private LinearLayoutManager HorizontalLayout;
    private TextView vText, vText_n;
    private CircleView IvsHead;
    private CircleView vDelete;
    private CircleView vEdit;
    private CircleView vAdd;
//    private View vLyEdit;
//    private ImageView ivEdit;
    private Drawable mIconAdd, mIconEdit, mIconSave, mIconCancel;
    private RelativeLayout vMain;
    private LinearLayout vLyControl;
    private OnDeleteClickTaskListener mOnDeleteClickTaskListener;
    private OnAddClickTaskListener mOnAddClickTaskListener;
    private OnEditClickTaskListener mOnEditClickTaskListener;
    private boolean isEditMode = false;
    private boolean isViewBottom = false;
    private View LyCnt;
    private CircleView vBefore, vNext;
    private ImageView vNext_n, vBefore_n;

    public SpCtrlBox(Context context) {
        super(context);
        init(context, null);
    }

    public SpCtrlBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, null);
    }

    public SpCtrlBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SpCtrlBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, null);
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void init(Context context, AttributeSet attrs) {
        View rootView = inflate(context, R.layout.sps_ctrl_box, SpCtrlBox.this);

        mContext = context;

        IvsHead = rootView.findViewById(R.id.cv_head);
        IncRecyclerView = rootView.findViewById(R.id.incRecyclerView);

        LyCnt = rootView.findViewById(R.id.lyCnt);
        vBefore = rootView.findViewById(R.id.cv_before);
        vBefore_n = rootView.findViewById(R.id.cv_before_n);
        vNext = rootView.findViewById(R.id.cv_next);
        vNext_n = rootView.findViewById(R.id.cv_next_n);

//        vLyEdit = rootView.findViewById(R.id.ily_edit);
//        ivEdit = rootView.findViewById(R.id.iv_edit);
        vDelete = rootView.findViewById(R.id.cv_delete);
        vEdit = rootView.findViewById(R.id.cv_edit);
        vAdd = rootView.findViewById(R.id.cv_add);

        vMain = rootView.findViewById(R.id.lyMain);
        vLyControl = rootView.findViewById(R.id.lyControl);

        vText = rootView.findViewById(R.id.txtDesc);
        vText_n = rootView.findViewById(R.id.txtDesc_n);

        Configuration config = getResources().getConfiguration();
        if(config.getLayoutDirection() != View.LAYOUT_DIRECTION_LTR) {
            vBefore.setIcon(getResources().getDrawable(R.drawable.ic_baseline_navigate_before_24), 0);
            vBefore_n.setImageResource((R.drawable.ic_baseline_navigate_before_24));
            vNext.setIcon(getResources().getDrawable(R.drawable.ic_baseline_navigate_next_24), 0);
            vNext_n.setImageResource((R.drawable.ic_baseline_navigate_next_24));
        }else {
            vBefore.setIcon(getResources().getDrawable(R.drawable.ic_baseline_navigate_next_24), 0);
            vBefore_n.setImageResource((R.drawable.ic_baseline_navigate_next_24));
            vNext.setIcon(getResources().getDrawable(R.drawable.ic_baseline_navigate_before_24), 0);
            vNext_n.setImageResource((R.drawable.ic_baseline_navigate_before_24));
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

        mIconSave = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_check_24, null);
        mIconCancel = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_close_24, null);
        mIconEdit = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_edit_24, null);
        mIconAdd = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_add_24, null);

        vEdit.setOnClickListener(v -> {
            mOnEditClickTaskListener.onEvent(isEditMode, HorizontalLayout != null ? HorizontalLayout.findFirstVisibleItemPosition() : -1);
            if (!isEditMode)
                setMode(true, -1);
        });

        vAdd.setOnClickListener(v -> {
            mOnAddClickTaskListener.onEvent(isEditMode);
            if (!isEditMode)
                setMode(true, -1);
        });

        vDelete.setOnClickListener(v -> {
            if(mOnDeleteClickTaskListener != null) mOnDeleteClickTaskListener.onEvent();
        });

//        mIconSave = getResources().getDrawable(R.drawable.ic_baseline_check_24);


        //-------------------------------------------
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SpCtrlBox, -1, 0);

        isViewBottom = a.getBoolean(R.styleable.SpCtrlBox_view_bottom, false);

        a.recycle();
        invalidate();
    }

    public void setMode(boolean editMode, int currentPosition){
        if (editMode)
        {
            vDelete.setVisibility(GONE);
            vEdit.setVisibility(VISIBLE);
            vEdit.setIcon(mIconSave, 0);
//            ivEdit.setImageDrawable(mIconSave);
//            vEdit.setAlpha(180);
//            vAdd.setAlpha(180);
            vAdd.setIcon(mIconCancel, 0);
            SetViewVisible(false);
            IncRecyclerView.setVisibility(GONE);
            vMain.setVisibility(VISIBLE);

//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(mContext, "R_1", Toast.LENGTH_SHORT).show();
//                    vAdd.setIcon(mIconCancel);
//                    vEdit.setIcon(mIconSave);
//                    invalidate();
//                }
//            }, 500);
        }
        else
        {
            RefreshCntText();
            if(IncRecyclerView.getAdapter() == null || IncRecyclerView.getAdapter().getItemCount() == 0){
                vDelete.setVisibility(GONE);
                vEdit.setVisibility(GONE);
                SetViewVisible(false);
                invalidate();
            }
            else
                SetViewVisible(true);

            if(HorizontalLayout.getItemCount() > 0)
            {
                vEdit.setVisibility(VISIBLE);
                vDelete.setVisibility(VISIBLE);
            }
            else {
                vEdit.setVisibility(GONE);
                vDelete.setVisibility(GONE);
            }

            vEdit.setIcon(mIconEdit, 0);
//            ivEdit.setImageDrawable(mIconEdit);
            vAdd.setIcon(mIconAdd, 0);
//            vEdit.setAlpha(180);
//            vAdd.setAlpha(180);
            IncRecyclerView.setVisibility(VISIBLE);
            vMain.setVisibility(GONE);

            if(currentPosition >= 0)
                HorizontalLayout.smoothScrollToPosition(IncRecyclerView, null , currentPosition);

//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(mContext, "R_2", Toast.LENGTH_SHORT).show();
//                    vEdit.setIcon(mIconEdit);
//                    vAdd.setIcon(mIconAdd);
//                    invalidate();
//                }
//            }, 500);
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
                LyCnt.setVisibility(VISIBLE);  // TODO ??? Gone
            else {
                vText_n.setVisibility(VISIBLE);
                vBefore_n.setVisibility(GONE);
                vNext_n.setVisibility(GONE);
            }
        }
    }

    public void SetEditable(boolean editMode){
        if(editMode) {
            vLyControl.setVisibility(VISIBLE);
        }
        else{
            vLyControl.setVisibility(GONE);
        }
        if(isEditMode)
            setMode(false, -1);

        invalidate();
    }

//    public void Invalidate(){
////        if(editMode) {
////            vLyControl.setVisibility(VISIBLE);
////        }
////        else{
////            vLyControl.setVisibility(GONE);
////        }
////        if(isEditMode)
////            setMode(false, -1);
//
//        RefreshCntText();
//        invalidate();
//    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void SetHeadSrc(int headSrc, int fillColor, int strokeColor){
        IvsHead.setIcon(getResources().getDrawable(headSrc), 0);
        if(strokeColor != 0) IvsHead.setStrokeColor(strokeColor);
        if(fillColor != 0) IvsHead.setFillColor(fillColor);
        invalidate();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void SetSrc(int addIcn, int cancelIcn, int editIcn, int saveIcn){
        mIconAdd = getResources().getDrawable(addIcn);
        mIconCancel = getResources().getDrawable(cancelIcn);
        mIconEdit = getResources().getDrawable(editIcn);
        mIconSave = getResources().getDrawable(saveIcn);
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
            vDelete.setVisibility(VISIBLE);
            vEdit.setVisibility(VISIBLE);
            vEdit.setIcon(mIconEdit, 0);
        }else {
            vDelete.setVisibility(GONE);
            vEdit.setVisibility(GONE);
        }

        IncRecyclerView.invalidate();
        invalidate();
    }

    @SuppressLint("SetTextI18n")
    public void RefreshCntText() {
        int mItem = HorizontalLayout != null ? HorizontalLayout.findFirstVisibleItemPosition()+1 : 0;
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
            vNext.setAlpha(60);
            vNext_n.setAlpha(60);
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

    public interface OnDeleteClickTaskListener {
        void onEvent();
    }

    public void setOnDeleteClickTaskListener(OnDeleteClickTaskListener eventListener) {
        mOnDeleteClickTaskListener = eventListener;
    }
}
