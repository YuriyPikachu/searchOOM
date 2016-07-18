package net.tatans.rhea.network.register;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import net.tatans.coeus.network.callback.HttpRequestCallBack;
import net.tatans.coeus.network.callback.HttpRequestParams;
import net.tatans.coeus.network.tools.TatansApp;
import net.tatans.coeus.network.tools.TatansCache;
import net.tatans.coeus.network.tools.TatansHttp;
import net.tatans.coeus.network.tools.TatansToast;
import net.tatans.coeus.network.utils.TatansDirPath;

import java.io.File;

/**
 * Created by SiLiPing on 2016/5/25.
 */
public class RegCertificateUtil {

    private Context mCtx;
    private TatansCache tatansCache;
    private TirisDemo mTirisDemo;
    private TatansHttp fh;
    private String url = "http://115.29.11.17:8093/android/rest/v1.0/android/validaApp.do";
    private String sign;
    private String key = "certificate";

    public RegCertificateUtil(Activity ctx) {
        this.mCtx = ctx;
       // if (("android.intent.action.MAIN").equals(ctx.getIntent().getAction()))
        initCertificate();
    }

    private void initCertificate(){
        if(getImei()==null){
            return;
        }
        tatansCache = TatansCache.get(new File(TatansDirPath.getMyCacheDir(".systemtt")));
        boolean ctrFlag = tatansCache.isCacheExist(key);/**判断key是否存在*/
        Log.e("certificate","isExist:"+ctrFlag);
        if (ctrFlag){
             String ctrStr = tatansCache.getAsStringNotDelete(key);
            /**凭证真伪*/
            if(checkRegCode(ctrStr)){
                /**验证凭证是否过期*/
                boolean isCacheDue = tatansCache.isCacheDue(key);
                if (isCacheDue){
                    getTestService(false);
                }
            }else{
                tatansCache.remove(key);/**移除虚假信息*/
                getTestService(true);
                Log.e("certificate","err：虚假凭证!");
            }
        }else{
            Log.e("certificate","err：凭证不存在!");
            /**网络请求验证*/
            getTestService(true);
        }
    }

    /**
     * 在线验证服务
     */
    private void getTestService(boolean isOk){
        if (NetworkUtil.isNetwork()){
            fh = new TatansHttp();
            /**网络请求验证*/
            final String imei = getImei().trim();//**String 获取IMEI*//*
            sign = (TatansApp.getSignature()).trim();
            HttpRequestParams paramss = new HttpRequestParams();
            /*Log.e("certificate","sign："+sign);
            Log.e("certificate","imei："+imei);*/
            paramss.put("sign", sign);
            paramss.put("imei", imei);
            fh.post(url,paramss, new HttpRequestCallBack<String>() {

                @Override
                public void onFailure(Throwable t, String strMsg) {
                    super.onFailure(t, strMsg);
                    showHint();
                    TatansToast.showAndCancel("数据加载失败，请稍后再试");
                    Log.e("certificate","err：加载失败!t："+t+"，strMsg："+strMsg);
                }

                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
                    try {
                        Log.e("certificate","返回值："+s);
                        /**在线验证失败*/
                        if (!s.equals("true")){
                            showHint();
                            TatansToast.showAndCancel("在线验证失败");
                            Log.e("certificate","在线验证失败");
                        }else{
                            tatansCache.put(key,getCertificate(imei),TatansCache.TIME_MONTH);
                            Log.e("certificate","在线验证通过");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }else{
            if(isOk)showHint();
        }
    }
    private String getCertificate(String Ime){
        String code=Ime+"10010";
        return TatansApp.md5(code);
    }
    private String getImei(){
        TelephonyManager telephonyManager = (TelephonyManager) mCtx.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
    /**
     * 本地测试验证真伪
     * @return
     */
    private boolean checkRegCode(String ctrStr){
        boolean T_F = (getCertificate(getImei()).equals(ctrStr))?true:false;
        Log.d("certificate","验证真伪："+T_F);
        return T_F;
    }

    /**
     * 凭证过期、虚假凭证、凭证不存在均启动注册提示
     */
    private void showHint(){
        mTirisDemo = new TirisDemo(mCtx);
        mTirisDemo.setOnTirisListener(new onTirisListener() {
            @Override
            public void onRegistration() {
                getTestService(true);
                TatansToast.showAndCancel("在线注册");
            }
            @Override
            public void onContinue() {
                TatansToast.showAndCancel("继续使用");
            }
        });
    }
}
