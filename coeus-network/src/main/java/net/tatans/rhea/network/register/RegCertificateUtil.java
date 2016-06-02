package net.tatans.rhea.network.register;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import net.tatans.coeus.network.callback.HttpRequestCallBack;
import net.tatans.coeus.network.callback.HttpRequestParams;
import net.tatans.coeus.network.tools.TatansApp;
import net.tatans.coeus.network.tools.TatansCache;
import net.tatans.coeus.network.tools.TatansHttp;
import net.tatans.coeus.network.tools.TatansToast;
import net.tatans.coeus.network.utils.TatansDirPath;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        if ((ctx.getIntent().getAction()).endsWith(".MAIN"))
            initCertificate();
    }

    private void initCertificate(){
        tatansCache = TatansCache.get(new File(TatansDirPath.getMyCacheDir("reg","ctr")).getPath());
        /*
        Log.d("imeiStr","imei��"+imei);
        */
        boolean ctrFlag = tatansCache.isCacheExist(key);/**�ж�key�Ƿ����*/
        if (ctrFlag){
             String ctrStr = tatansCache.getAsStringNotDelete(key);
            /**ƾ֤��α*/
            if(checkRegCode(ctrStr)){
                /**��֤ƾ֤�Ƿ����*/
                boolean isCacheDue = tatansCache.isCacheDue(key);
                if (isCacheDue){
                    getTestService(false);
                }
            }else{
                tatansCache.remove(key);/**�Ƴ������Ϣ*/
                getTestService(true);
                Log.e("certificate","err�����ƾ֤!");
            }
        }else{
            Log.e("certificate","err��ƾ֤������!");
            /**����������֤*/
            getTestService(true);
        }
    }

    /**
     * ������֤����
     */
    private void getTestService(boolean isOk){
        if (NetworkUtil.isNetwork()){
            fh = new TatansHttp();
            /**����������֤*/
            final String imei = getImei();//**String ��ȡIMEI*//*
            sign = getSignature();
            HttpRequestParams paramss = new HttpRequestParams();
            paramss.put("sign", sign);
            paramss.put("imei", imei);
            fh.get(url,paramss, new HttpRequestCallBack<String>() {

                @Override
                public void onFailure(Throwable t, String strMsg) {
                    super.onFailure(t, strMsg);
                    showHint();
                    TatansToast.showAndCancel("���ݼ���ʧ�ܣ����Ժ�����");
                    Log.e("certificate","err������ʧ��!t��"+t+"��strMsg��"+strMsg);
                }

                @Override
                public void onSuccess(String s) {
                    super.onSuccess(s);
                    try {
                        Log.e("certificate","����ֵ��"+s);
                        /**������֤ʧ��*/
                        if (!s.equals("true")){
                            showHint();
                            TatansToast.showAndCancel("������֤ʧ��");
                            Log.e("certificate","������֤ʧ��");
                        }else{
                            tatansCache.put(key,getCertificate(imei));
                            Log.e("certificate","������֤ͨ��");
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
     * ���ز�����֤��α
     * @return
     */
    private boolean checkRegCode(String ctrStr){
        boolean T_F = (getCertificate(getImei()).equals(ctrStr))?true:false;
        Log.d("certificate","��֤��α��"+T_F);
        return T_F;
    }

    /**
     * ƾ֤���ڡ����ƾ֤��ƾ֤�����ھ�����ע����ʾ
     */
    private void showHint(){
        mTirisDemo = new TirisDemo(mCtx);
        mTirisDemo.setOnTirisListener(new onTirisListener() {
            @Override
            public void onRegistration() {
                getTestService(true);
                TatansToast.showAndCancel("����ע��");
            }

            @Override
            public void onContinue() {
                TatansToast.showAndCancel("����ʹ��");
            }
        });
    }

    /**
     * ��ȡӦ��ǩ��
     * @return
     */
    private PackageManager manager;
    private PackageInfo packageInfo;
    private Signature[] signatures;
    private StringBuilder builder;
    private String signature;
    public String getSignature() {
        manager = mCtx.getPackageManager();
        builder = new StringBuilder();
        String pkgname = getAppInfo();
        boolean isEmpty = TextUtils.isEmpty(pkgname);
        if (isEmpty) {
            TatansToast.showAndCancel("Ӧ�ó���İ�������Ϊ�գ�");
        } else {
            try {
                /** ͨ�������������ָ����������ǩ���İ���Ϣ **/
                packageInfo = manager.getPackageInfo(pkgname, PackageManager.GET_SIGNATURES);
                /******* ͨ�����صİ���Ϣ���ǩ������ *******/
                signatures = packageInfo.signatures;
                /******* ѭ������ǩ������ƴ��Ӧ��ǩ�� *******/
                for (Signature signature : signatures) {
                    builder.append(signature.toCharsString());
                }
                /************** �õ�Ӧ��ǩ�� **************/
                signature = builder.toString();
                Log.d("signature","signature��"+signature);
                return signature;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * ��ȡӦ�ð���
     * @return
     */
    private String getAppInfo() {
        try {
            String pkName = mCtx.getPackageName();
            String versionName = mCtx.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = mCtx.getPackageManager().getPackageInfo(pkName, 0).versionCode;
            Log.d("signature","getAppInfo��"+pkName + "   " + versionName + "  " + versionCode);
            return pkName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
