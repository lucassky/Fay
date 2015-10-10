package com.lucassky.fay.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.lucassky.fay.R;
import com.lucassky.fay.utils.AccessTokenKeeper;
import com.lucassky.fay.utils.Constants;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * 欢迎页面
 */
public class WelcomActivity extends Activity {
    /**
     * 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
     */
    private SsoHandler mSsoHandler;


    private AuthInfo mAuthInfo;

    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    private Oauth2AccessToken mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        Oauth2AccessToken oauth2AccessToken = AccessTokenKeeper.readAccessToken(this);
        if (oauth2AccessToken != null && !TextUtils.isEmpty(oauth2AccessToken.getUid())
                && !TextUtils.isEmpty(oauth2AccessToken.getToken())
                && !TextUtils.isEmpty(oauth2AccessToken.getRefreshToken())) {
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        } else {
            mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
            mSsoHandler = new SsoHandler(WelcomActivity.this, mAuthInfo);
            mSsoHandler.authorize(new AuthListener());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSsoHandler.authorizeCallBack(requestCode,resultCode,data);//mSsoHandler回调方法，最终回调AuthListener里面的方法
    }

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     * 该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {
        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token

            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息 String  phoneNum =  mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                    // 保存 Token 到 SharedPreferences
                    AccessTokenKeeper.writeAccessToken(WelcomActivity.this, mAccessToken);
                    Toast.makeText(WelcomActivity.this, R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(WelcomActivity.this, MainActivity.class));
                    WelcomActivity.this.finish();
                } else {
                    // 以下几种情况，您会收到 Code：
                    // 1. 当您未在平台上注册的应用程序的包名与签名时；
                    // 2. 当您注册的应用程序包名与签名不正确时；
                    // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                    String code = values.getString("code");
                    String message = getString(R.string.weibosdk_demo_toast_auth_failed);
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(WelcomActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(WelcomActivity.this,
                    R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(WelcomActivity.this,
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
