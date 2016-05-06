package com.yc.BaiSiBuDeJie.net;

import android.app.FragmentManager;
import android.os.AsyncTask;

import com.yc.BaiSiBuDeJie.module.listview.entity.ShowApiEntity;
import com.yc.BaiSiBuDeJie.net.parser.JsonParser;

import java.lang.reflect.Type;

/**
 * 解析类的门面模式类
 */
public class ParserFacade {

    private JsonParser mParser;
    final IParserListener mParserListener;
    private String mRequestCode;
    private FragmentManager mFragmentManager;

    public ParserFacade(String requestCode, IParserListener parserListener) {
        this(requestCode, parserListener, null);
    }

    public ParserFacade(String requestCode, IParserListener parserListener, FragmentManager fragmentManager) {
        mParserListener = parserListener;
        mRequestCode = requestCode;
        mFragmentManager = fragmentManager;
        if(mParserListener == null) {
            throw new NullPointerException("ParserListener is null!");
        }
    }

    /**
     * 开启Task解析result
     * @param result     json str
     * @param classOfT   解析对象
     */
    public <T> void start(String result, Class<T> classOfT) {
        start(result, null, classOfT);
    }

    /**
     * 开启Task解析result
     * @param result     json str
     * @param detcet     要解析目标的label
     * @param classOfT   解析对象
     */
    public <T> void start(String result, String detcet, Class<T> classOfT) {
        mParser = new JsonParser(detcet, classOfT);
        new ParserTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, result);
    }

    /**
     * 开启Task解析result
     * @param result     json str
     * @param typeOfT    解析type
     */
    public void start(String result, Type typeOfT) {
        start(result, null, typeOfT);
    }

    /**
     * 开启Task解析result
     * @param result     json str
     * @param detcet     要解析目标的label
     * @param typeOfT    解析type
     */
    public void start(String result, String detcet, Type typeOfT) {
        mParser = new JsonParser(detcet, typeOfT);
        new ParserTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, result);
    }

    protected Object handler(String result) {
        return mParser.parser(result);
    }

    protected class ParserTask extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... params) {
            return handler(params[0]);
        }

        @Override
        protected void onPostExecute(Object result) {
            if (result instanceof ShowApiEntity) {
                mParserListener.onParserError(mRequestCode, (ShowApiEntity) result);
            } else {
                mParserListener.onParserSuccess(mRequestCode, result);
            }

        }

    }

}
