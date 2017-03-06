package ru.profi.vkphotov2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import ru.profi.vkphotov2.profilephotos.ProfilePhotosActivity;
import ru.profi.vkphotov2.social.APIManager;
import ru.profi.vkphotov2.social.AuthorizationListener;
import ru.profi.vkphotov2.social.SocialNetworkAPI;

public class AuthorizationActivity extends AppCompatActivity implements AuthorizationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        // Попытаться авторизоваться
        SocialNetworkAPI api = APIManager.getAPIImpl();
        api.addAuthorizationListener(this);
        WebView webView = (WebView) findViewById(R.id.web_view);
        api.authorize(webView);
    }

    @Override
    public void authorizationSuccess() {
        Log.d("AUTH", "success");
        Intent intent = new Intent(this, ProfilePhotosActivity.class);
        startActivity(intent);
    }

    @Override
    public void authorizationError() {
        Log.d("AUTH", "error");
    }
}
