package njuse.via.paster;

/**
 * Created by Administrator on 2015/7/19 0019.
 */
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import njuse.via.R;

/**
 * ���ֶ�ͼƬ�������ţ���ת��ƽ�Ʋ���
 */
public class SingleTouchView extends View {
    /**
     * ͼƬ��������ű���
     */
    public static final float MAX_SCALE = 4.0f;

    /**
     * ͼƬ����С���ű���
     */
    public static final float MIN_SCALE = 0.3f;

    /**
     * �������ţ���תͼ�������ĸ����λ��
     */
    public static final int LEFT_TOP = 0;
    public static final int RIGHT_TOP = 1;
    public static final int RIGHT_BOTTOM = 2;
    public static final int LEFT_BOTTOM = 3;

    /**
     * һЩĬ�ϵĳ���
     */
    public static final int DEFAULT_FRAME_PADDING = 8;
    public static final int DEFAULT_FRAME_WIDTH = 2;
    public static final int DEFAULT_FRAME_COLOR = Color.WHITE;
    public static final float DEFAULT_SCALE = 1.0f;
    public static final float DEFAULT_DEGREE = 0;
    public static final int DEFAULT_CONTROL_LOCATION = RIGHT_TOP;
    public static final boolean DEFAULT_EDITABLE = true;
    public static final int DEFAULT_OTHER_DRAWABLE_WIDTH = 50;
    public static final int DEFAULT_OTHER_DRAWABLE_HEIGHT = 50;



    /**
     * ������ת���ŵ�Bitmap
     */
    private Bitmap mBitmap;

    public Bitmap mBitmap1;

    /**
     * SingleTouchView�����ĵ����꣬������丸�಼�ֶ��Ե�
     */
    private PointF mCenterPoint = new PointF();

    /**
     * View�Ŀ�Ⱥ͸߶ȣ�����ͼƬ����ת���仯(������������ת������ͼƬ�Ŀ��)
     */
    private int mViewWidth, mViewHeight;

    /**
     * ͼƬ����ת�Ƕ�
     */
    private float mDegree = DEFAULT_DEGREE;

    /**
     * ͼƬ�����ű���
     */
    private float mScale = DEFAULT_SCALE;

    /**
     * �������ţ���ת��ƽ�Ƶľ���
     */
    private Matrix matrix = new Matrix();

    /**
     * SingleTouchView���븸�಼�ֵ�����
     */
    private int mViewPaddingLeft;

    /**
     * SingleTouchView���븸�಼�ֵ��ϼ��
     */
    private int mViewPaddingTop;

    /**
     * ͼƬ�ĸ�������
     */
    private Point mLTPoint;
    private Point mRTPoint;
    private Point mRBPoint;
    private Point mLBPoint;

    /**
     * �������ţ���ת�Ŀ��Ƶ������
     */
    private Point mControlPoint = new Point();

    /**
     * �������ţ���ת��ͼ��
     */
    private Drawable controlDrawable;

    /**
     * ���ţ���תͼ��Ŀ�͸�
     */
    private int mDrawableWidth, mDrawableHeight;

    /**
     * ����Χ���Path
     */
    private Path mPath = new Path();

    /**
     * ����Χ��Ļ���
     */
    private Paint mPaint ;

    /**
     * ��ʼ״̬
     */
    public static final int STATUS_INIT = 0;

    /**
     * �϶�״̬
     */
    public static final int STATUS_DRAG = 1;

    /**
     * ��ת���߷Ŵ�״̬
     */
    public static final int STATUS_ROTATE_ZOOM = 2;

    /**
     * ��ǰ������״̬
     */
    private int mStatus = STATUS_INIT;

    /**
     * ��߿���ͼƬ֮��ļ��, ��λ��dip
     */
    private int framePadding = DEFAULT_FRAME_PADDING;

    /**
     * ��߿���ɫ
     */
    private int frameColor = DEFAULT_FRAME_COLOR;

    /**
     * ��߿�������ϸ, ��λ�� dip
     */
    private int frameWidth = DEFAULT_FRAME_WIDTH;

    /**
     * �Ƿ��ڿ������ţ�ƽ�ƣ���ת״̬
     */
    private boolean isEditable = DEFAULT_EDITABLE;

    private DisplayMetrics metrics;


    private PointF mPreMovePointF = new PointF();
    private PointF mCurMovePointF = new PointF();

    /**
     * ͼƬ����תʱx�����ƫ����
     */
    private int offsetX;
    /**
     * ͼƬ����תʱy�����ƫ����
     */
    private int offsetY;

    public int xNow;
    public int yNow;
    public int xNow1;
    public int yNow1;
    /**
     * ����ͼ�����ڵ�λ�ã��������ϣ����ϣ����£����£�
     */
    private int controlLocation = DEFAULT_CONTROL_LOCATION;


    public SingleTouchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SingleTouchView(Context context) {
        this(context, null);
    }

    public SingleTouchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        obtainStyledAttributes(attrs);
        init();
    }

    /**
     * ��ȡ�Զ�������
     * @param attrs
     */
    private void obtainStyledAttributes(AttributeSet attrs){
        metrics = getContext().getResources().getDisplayMetrics();
        framePadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_FRAME_PADDING, metrics);
        frameWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_FRAME_WIDTH, metrics);

        TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.SingleTouchView);

        Drawable srcDrawble = mTypedArray.getDrawable(R.styleable.SingleTouchView_src);
        mBitmap = drawable2Bitmap(srcDrawble);
        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.styleable.SingleTouchView_src);

        framePadding = mTypedArray.getDimensionPixelSize(R.styleable.SingleTouchView_framePadding, framePadding);
        frameWidth = mTypedArray.getDimensionPixelSize(R.styleable.SingleTouchView_frameWidth, frameWidth);
        frameColor = mTypedArray.getColor(R.styleable.SingleTouchView_frameColor, DEFAULT_FRAME_COLOR);
        mScale = mTypedArray.getFloat(R.styleable.SingleTouchView_scale, DEFAULT_SCALE);
        mDegree = mTypedArray.getFloat(R.styleable.SingleTouchView_degree, DEFAULT_DEGREE);
        controlDrawable = mTypedArray.getDrawable(R.styleable.SingleTouchView_controlDrawable);
        controlLocation = mTypedArray.getInt(R.styleable.SingleTouchView_controlLocation, DEFAULT_CONTROL_LOCATION);
        isEditable = mTypedArray.getBoolean(R.styleable.SingleTouchView_editable, DEFAULT_EDITABLE);

        mTypedArray.recycle();

    }


    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(frameColor);
        mPaint.setStrokeWidth(frameWidth);
        mPaint.setStyle(Style.STROKE);

        if(controlDrawable == null){
            controlDrawable = getContext().getResources().getDrawable(R.drawable.st_rotate_icon);
        }

        mDrawableWidth = controlDrawable.getIntrinsicWidth();
        mDrawableHeight = controlDrawable.getIntrinsicHeight();

        transformDraw();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //��ȡSingleTouchView���ڸ����ֵ����ĵ�
        ViewGroup mViewGroup = (ViewGroup) getParent();
        if(null != mViewGroup){
            int parentWidth = mViewGroup.getWidth();
            int parentHeight = mViewGroup.getHeight();
            mCenterPoint.set(parentWidth/2, parentHeight/2);
        }
    }


    /**
     * ����View�Ĵ�С��λ��
     */
    private void adjustLayout(){
        int actualWidth = mViewWidth + mDrawableWidth;
        int actualHeight = mViewHeight + mDrawableHeight;

        int newPaddingLeft = (int) (mCenterPoint.x - actualWidth /2);
        int newPaddingTop = (int) (mCenterPoint.y - actualHeight/2);

        if(mViewPaddingLeft != newPaddingLeft || mViewPaddingTop != newPaddingTop){
            mViewPaddingLeft = newPaddingLeft;
            mViewPaddingTop = newPaddingTop;

//			layout(newPaddingLeft, newPaddingTop, newPaddingLeft + actualWidth, newPaddingTop + actualHeight);
        }
        xNow = newPaddingLeft;
        yNow = newPaddingTop;
        xNow1 = newPaddingLeft + actualWidth;
        yNow1 = newPaddingTop + actualHeight;

        layout(newPaddingLeft, newPaddingTop, newPaddingLeft + actualWidth, newPaddingTop + actualHeight);

    }

    public PointF getLocation(){
        int actualWidth = mViewWidth + mDrawableWidth;
        int actualHeight = mViewHeight + mDrawableHeight;

        int newPaddingLeft = (int) (mCenterPoint.x - actualWidth /2);
        int newPaddingTop = (int) (mCenterPoint.y - actualHeight/2);
        xNow = newPaddingLeft;
        yNow = newPaddingTop;
        xNow1 = newPaddingLeft + actualWidth;
        yNow1 = newPaddingTop + actualHeight;
        return (new PointF(xNow,yNow));
    }



    /**
     * ������תͼ
     * @param bitmap
     */
    public void setImageBitamp(Bitmap bitmap){
        this.mBitmap = bitmap;
        transformDraw();
    }


    /**
     * ������תͼ
     * @param drawable
     */
    public void setImageDrawable(Drawable drawable){
        this.mBitmap = drawable2Bitmap(drawable);
        this.mBitmap1  = drawable2Bitmap(drawable);
//        this.mBitmap1 = BitmapFactory.decodeResource(getResources(), R.styleable.SingleTouchView_src);
        transformDraw();
    }

    /**
     * ��Drawable�л�ȡBitmap����
     * @param drawable
     * @return
     */
    private Bitmap drawable2Bitmap(Drawable drawable) {
        try {
            if (drawable == null) {
                return null;
            }

            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }

            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            Bitmap bitmap = Bitmap.createBitmap(
                    intrinsicWidth <= 0 ? DEFAULT_OTHER_DRAWABLE_WIDTH
                            : intrinsicWidth,
                    intrinsicHeight <= 0 ? DEFAULT_OTHER_DRAWABLE_HEIGHT
                            : intrinsicHeight, Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }

    }

    /**
     * ����id������תͼ
     * @param resId
     */
    public void setImageResource(int resId){
        Drawable drawable = getContext().getResources().getDrawable(resId);
        setImageDrawable(drawable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //ÿ��draw֮ǰ����View��λ�úʹ�С
        super.onDraw(canvas);
        if(mBitmap == null) return;
        canvas.drawBitmap(mBitmap, matrix, mPaint);


        //���ڿɱ༭״̬�Ż��߿�Ϳ���ͼ��
        if(isEditable){
            mPath.reset();
            mPath.moveTo(mLTPoint.x, mLTPoint.y);
            mPath.lineTo(mRTPoint.x, mRTPoint.y);
            mPath.lineTo(mRBPoint.x, mRBPoint.y);
            mPath.lineTo(mLBPoint.x, mLBPoint.y);
            mPath.lineTo(mLTPoint.x, mLTPoint.y);
            mPath.lineTo(mRTPoint.x, mRTPoint.y);
            canvas.drawPath(mPath, mPaint);
            //����ת, ����ͼ��

            controlDrawable.setBounds(mControlPoint.x - mDrawableWidth / 2,
                    mControlPoint.y - mDrawableHeight / 2, mControlPoint.x + mDrawableWidth
                            / 2, mControlPoint.y + mDrawableHeight / 2);
            controlDrawable.draw(canvas);
        }

        adjustLayout();


    }



    /**
     * ����Matrix, ǿ��ˢ��
     */
    private void transformDraw(){
        if(mBitmap == null) return;
        int bitmapWidth = (int)(mBitmap.getWidth() * mScale);
        int bitmapHeight = (int)(mBitmap.getHeight()* mScale);
        computeRect(-framePadding, -framePadding, bitmapWidth + framePadding, bitmapHeight + framePadding, mDegree);

        //�������ű���
        matrix.setScale(mScale, mScale);
        //����ͼƬ���Ľ�����ת
        matrix.postRotate(mDegree % 360, bitmapWidth / 2, bitmapHeight / 2);
        //���û���ͼƬ����ʼ��
        matrix.postTranslate(offsetX + mDrawableWidth/2, offsetY + mDrawableHeight/2);

        adjustLayout();
    }


    public boolean onTouchEvent(MotionEvent event) {
        if(!isEditable){
            return super.onTouchEvent(event);
        }
        switch (event.getAction() ) {
            case MotionEvent.ACTION_DOWN:
                mPreMovePointF.set(event.getX() + mViewPaddingLeft, event.getY() + mViewPaddingTop);

                mStatus = JudgeStatus(event.getX(), event.getY());

                break;
            case MotionEvent.ACTION_UP:
                mStatus = STATUS_INIT;
                break;
            case MotionEvent.ACTION_MOVE:
                mCurMovePointF.set(event.getX() + mViewPaddingLeft, event.getY() + mViewPaddingTop);
                if (mStatus == STATUS_ROTATE_ZOOM) {
                    float scale = 1f;

                    int halfBitmapWidth = mBitmap.getWidth() / 2;
                    int halfBitmapHeight = mBitmap.getHeight() /2 ;

                    //ͼƬĳ���㵽ͼƬ���ĵľ���
                    float bitmapToCenterDistance = FloatMath.sqrt(halfBitmapWidth * halfBitmapWidth + halfBitmapHeight * halfBitmapHeight);

                    //�ƶ��ĵ㵽ͼƬ���ĵľ���
                    float moveToCenterDistance = distance4PointF(mCenterPoint, mCurMovePointF);

                    //�������ű���
                    scale = moveToCenterDistance / bitmapToCenterDistance;


                    //���ű����Ľ����ж�
                    if (scale <= MIN_SCALE) {
                        scale = MIN_SCALE;
                    } else if (scale >= MAX_SCALE) {
                        scale = MAX_SCALE;
                    }


                    // �Ƕ�
                    double a = distance4PointF(mCenterPoint, mPreMovePointF);
                    double b = distance4PointF(mPreMovePointF, mCurMovePointF);
                    double c = distance4PointF(mCenterPoint, mCurMovePointF);

                    double cosb = (a * a + c * c - b * b) / (2 * a * c);

                    if (cosb >= 1) {
                        cosb = 1f;
                    }

                    double radian = Math.acos(cosb);
                    float newDegree = (float) radianToDegree(radian);

                    //center -> proMove�������� ����ʹ��PointF��ʵ��
                    PointF centerToProMove = new PointF((mPreMovePointF.x - mCenterPoint.x), (mPreMovePointF.y - mCenterPoint.y));

                    //center -> curMove ������
                    PointF centerToCurMove = new PointF((mCurMovePointF.x - mCenterPoint.x), (mCurMovePointF.y - mCenterPoint.y));

                    //������˽��, ������Ϊ������ ��ʾΪ��ʱ�룬 ���Ϊ������ʾ˳ʱ��
                    float result = centerToProMove.x * centerToCurMove.y - centerToProMove.y * centerToCurMove.x;

                    if (result < 0) {
                        newDegree = -newDegree;
                    }

                    mDegree = mDegree + newDegree;
                    mScale = scale;

                    transformDraw();
                }
                else if (mStatus == STATUS_DRAG) {
                    // �޸����ĵ�
                    mCenterPoint.x += mCurMovePointF.x - mPreMovePointF.x;
                    mCenterPoint.y += mCurMovePointF.y - mPreMovePointF.y;

                    System.out.println(this + "move = " + mCenterPoint);

                    adjustLayout();
                }

                mPreMovePointF.set(mCurMovePointF);
                break;
        }
        return true;
    }



    /**
     * ��ȡ�ĸ����View�Ĵ�С
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param degree
     */
    private void computeRect(int left, int top, int right, int bottom, float degree){
        Point lt = new Point(left, top);
        Point rt = new Point(right, top);
        Point rb = new Point(right, bottom);
        Point lb = new Point(left, bottom);
        Point cp = new Point((left + right) / 2, (top + bottom) / 2);
        mLTPoint = obtainRoationPoint(cp, lt, degree);
        mRTPoint = obtainRoationPoint(cp, rt, degree);
        mRBPoint = obtainRoationPoint(cp, rb, degree);
        mLBPoint = obtainRoationPoint(cp, lb, degree);

        //����X��������ֵ����С��ֵ
        int maxCoordinateX = getMaxValue(mLTPoint.x, mRTPoint.x, mRBPoint.x, mLBPoint.x);
        int minCoordinateX = getMinValue(mLTPoint.x, mRTPoint.x, mRBPoint.x, mLBPoint.x);;

        mViewWidth = maxCoordinateX - minCoordinateX ;


        //����Y��������ֵ����С��ֵ
        int maxCoordinateY = getMaxValue(mLTPoint.y, mRTPoint.y, mRBPoint.y, mLBPoint.y);
        int minCoordinateY = getMinValue(mLTPoint.y, mRTPoint.y, mRBPoint.y, mLBPoint.y);

        mViewHeight = maxCoordinateY - minCoordinateY ;


        //View���ĵ������
        Point viewCenterPoint = new Point((maxCoordinateX + minCoordinateX) / 2, (maxCoordinateY + minCoordinateY) / 2);

        offsetX = mViewWidth / 2 - viewCenterPoint.x;
        offsetY = mViewHeight / 2 - viewCenterPoint.y;



        int halfDrawableWidth = mDrawableWidth / 2;
        int halfDrawableHeight = mDrawableHeight /2;

        //��Bitmap���ĸ����X�������ƶ�offsetX + halfDrawableWidth
        mLTPoint.x += (offsetX + halfDrawableWidth);
        mRTPoint.x += (offsetX + halfDrawableWidth);
        mRBPoint.x += (offsetX + halfDrawableWidth);
        mLBPoint.x += (offsetX + halfDrawableWidth);

        //��Bitmap���ĸ����Y�����ƶ�offsetY + halfDrawableHeight
        mLTPoint.y += (offsetY + halfDrawableHeight);
        mRTPoint.y += (offsetY + halfDrawableHeight);
        mRBPoint.y += (offsetY + halfDrawableHeight);
        mLBPoint.y += (offsetY + halfDrawableHeight);

        mControlPoint = LocationToPoint(controlLocation);
    }


    /**
     * ����λ���жϿ���ͼ�괦���Ǹ���
     * @return
     */
    private Point LocationToPoint(int location){
        switch(location){
            case LEFT_TOP:
                return mLTPoint;
            case RIGHT_TOP:
                return mRTPoint;
            case RIGHT_BOTTOM:
                return mRBPoint;
            case LEFT_BOTTOM:
                return mLBPoint;
        }
        return mLTPoint;
    }


    /**
     * ��ȡ�䳤��������ֵ
     * @param array
     * @return
     */
    public int getMaxValue(Integer...array){
        List<Integer> list = Arrays.asList(array);
        Collections.sort(list);
        return list.get(list.size() -1);
    }


    /**
     * ��ȡ�䳤��������ֵ
     * @param array
     * @return
     */
    public int getMinValue(Integer...array){
        List<Integer> list = Arrays.asList(array);
        Collections.sort(list);
        return list.get(0);
    }



    /**
     * ��ȡ��תĳ���Ƕ�֮��ĵ�
     * @param source
     * @param degree
     * @return
     */
    public static Point obtainRoationPoint(Point center, Point source, float degree) {
        //����֮��ľ���
        Point disPoint = new Point();
        disPoint.x = source.x - center.x;
        disPoint.y = source.y - center.y;

        //û��ת֮ǰ�Ļ���
        double originRadian = 0;

        //û��ת֮ǰ�ĽǶ�
        double originDegree = 0;

        //��ת֮��ĽǶ�
        double resultDegree = 0;

        //��ת֮��Ļ���
        double resultRadian = 0;

        //������ת֮��������
        Point resultPoint = new Point();

        double distance = Math.sqrt(disPoint.x * disPoint.x + disPoint.y * disPoint.y);
        if (disPoint.x == 0 && disPoint.y == 0) {
            return center;
            // ��һ����
        } else if (disPoint.x >= 0 && disPoint.y >= 0) {
            // ������x������ļн�
            originRadian = Math.asin(disPoint.y / distance);

            // �ڶ�����
        } else if (disPoint.x < 0 && disPoint.y >= 0) {
            // ������x������ļн�
            originRadian = Math.asin(Math.abs(disPoint.x) / distance);
            originRadian = originRadian + Math.PI / 2;

            // ��������
        } else if (disPoint.x < 0 && disPoint.y < 0) {
            // ������x������ļн�
            originRadian = Math.asin(Math.abs(disPoint.y) / distance);
            originRadian = originRadian + Math.PI;
        } else if (disPoint.x >= 0 && disPoint.y < 0) {
            // ������x������ļн�
            originRadian = Math.asin(disPoint.x / distance);
            originRadian = originRadian + Math.PI * 3 / 2;
        }

        // ���Ȼ���ɽǶ�
        originDegree = radianToDegree(originRadian);
        resultDegree = originDegree + degree;

        // �Ƕ�ת����
        resultRadian = degreeToRadian(resultDegree);

        resultPoint.x = (int) Math.round(distance * Math.cos(resultRadian));
        resultPoint.y = (int) Math.round(distance * Math.sin(resultRadian));
        resultPoint.x += center.x;
        resultPoint.y += center.y;

        return resultPoint;
    }

    /**
     * ���Ȼ���ɽǶ�
     *
     * @return
     */
    public static double radianToDegree(double radian) {
        return radian * 180 / Math.PI;
    }


    /**
     * �ǶȻ���ɻ���
     * @param degree
     * @return
     */
    public static double degreeToRadian(double degree) {
        return degree * Math.PI / 180;
    }

    /**
     * ���ݵ����λ���ж��Ƿ���п�����ת�����ŵ�ͼƬ�� ���Եļ���
     * @param x
     * @param y
     * @return
     */
    private int JudgeStatus(float x, float y){
        PointF touchPoint = new PointF(x, y);
        PointF controlPointF = new PointF(mControlPoint);

        //����ĵ㵽������ת�����ŵ�ľ���
        float distanceToControl = distance4PointF(touchPoint, controlPointF);

        //�������֮��ľ���С�� ����ͼ��Ŀ�ȣ��߶ȵ���Сֵ������Ϊ�����˿���ͼ��
        if(distanceToControl < Math.min(mDrawableWidth/2, mDrawableHeight/2)){
            return STATUS_ROTATE_ZOOM;
        }

        return STATUS_DRAG;

    }


    public float getImageDegree() {
        return mDegree;
    }

    /**
     * ����ͼƬ��ת�Ƕ�
     * @param degree
     */
    public void setImageDegree(float degree) {
        if(this.mDegree != degree){
            this.mDegree = degree;
            transformDraw();
        }
    }

    public float getImageScale() {
        return mScale;
    }

    /**
     * ����ͼƬ���ű���
     * @param scale
     */
    public void setImageScale(float scale) {
        if(this.mScale != scale){
            this.mScale = scale;
            transformDraw();
        };
    }


    public Drawable getControlDrawable() {
        return controlDrawable;
    }

    /**
     * ���ÿ���ͼ��
     * @param drawable
     */
    public void setControlDrawable(Drawable drawable) {
        this.controlDrawable = drawable;
        mDrawableWidth = drawable.getIntrinsicWidth();
        mDrawableHeight = drawable.getIntrinsicHeight();
        transformDraw();
    }

    public int getFramePadding() {
        return framePadding;
    }

    public void setFramePadding(int framePadding) {
        if(this.framePadding == framePadding)
            return;
        this.framePadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, framePadding, metrics);
        transformDraw();
    }

    public int getFrameColor() {
        return frameColor;
    }

    public void setFrameColor(int frameColor) {
        if(this.frameColor == frameColor)
            return;
        this.frameColor = frameColor;
        mPaint.setColor(frameColor);
        invalidate();
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        if(this.frameWidth == frameWidth)
            return;
        this.frameWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, frameWidth, metrics);
        mPaint.setStrokeWidth(frameWidth);
        invalidate();
    }

    /**
     * ���ÿ���ͼ���λ��, ���õ�ֵֻ��ѡ��LEFT_TOP ��RIGHT_TOP�� RIGHT_BOTTOM��LEFT_BOTTOM

     */
    public void setControlLocation(int location) {
        if(this.controlLocation == location)
            return;
        this.controlLocation = location;
        transformDraw();
    }

    public int getControlLocation() {
        return controlLocation;
    }



    public PointF getCenterPoint() {
        return mCenterPoint;
    }

    /**
     * ����ͼƬ���ĵ�λ�ã�����ڸ����ֶ���
     * @param mCenterPoint
     */
    public void setCenterPoint(PointF mCenterPoint) {
        this.mCenterPoint = mCenterPoint;
        adjustLayout();
    }


    public boolean isEditable() {
        return isEditable;
    }

    /**
     * �����Ƿ��ڿ����ţ�ƽ�ƣ���ת״̬
     * @param isEditable
     */
    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
        invalidate();
    }

    /**
     * ������֮��ľ���

     */
    private float distance4PointF(PointF pf1, PointF pf2) {
        float disX = pf2.x - pf1.x;
        float disY = pf2.y - pf1.y;
        return FloatMath.sqrt(disX * disX + disY * disY);
    }

    /*
        public Bitmap createNewPhoto() {
            transformDraw();
            Bitmap bitmap = Bitmap.createBitmap(mViewWidth*4, mViewHeight*4,
                    Config.ARGB_8888); // ����ͼƬ
            Canvas canvas = new Canvas(bitmap); // �½�����
            int bitmapWidth = (int)(mBitmap.getWidth() * mScale);
            int bitmapHeight = (int)(mBitmap.getHeight()* mScale);
           // computeRect(-framePadding, -framePadding, bitmapWidth + framePadding, bitmapHeight + framePadding, mDegree);

           //�������ű���
            Matrix matrix1= new Matrix();
            matrix1.setScale(mScale*2, mScale*2);
            //����ͼƬ���Ľ�����ת
            matrix1.postRotate(mDegree % 360, bitmapWidth / 2, bitmapHeight / 2);
            //����ͼƬ��ʼ��
            matrix1.postTranslate((offsetX + mDrawableWidth/2)*2, (offsetY+ mDrawableHeight/2 )*2);

            canvas.drawBitmap(mBitmap, matrix1, null); // ��ͼƬ
         //   System.out.println(mBitmap1.getWidth()+"........."+mBitmap1.getHeight()+"::::::");
            canvas.save(Canvas.ALL_SAVE_FLAG); // ���滭��
            canvas.restore();
            return bitmap;
        }
    */
    public Bitmap createNewPhoto1(){
        setDrawingCacheEnabled(true);
        buildDrawingCache();
        Bitmap pic =  getDrawingCache();
        //  setDrawingCacheEnabled(false);
        System.out.println(pic.getHeight()+"________"+pic.getWidth());
        return pic;
    }


}
