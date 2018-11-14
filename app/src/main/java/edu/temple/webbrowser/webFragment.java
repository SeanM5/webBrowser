package edu.temple.webbrowser;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class webFragment extends Fragment {

    WebView web;
    Button go;
    EditText url;
    ImageButton forward;
    ImageButton backward;
    LinearLayout ll;
    RelativeLayout rel;
    openPage parent;


    public webFragment() {
        // Required empty public constructor
    }

    static webFragment init() {
        webFragment web = new webFragment();
        return web;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_web, container, false);
        go = v.findViewById(R.id.go);
        web = v.findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setDomStorageEnabled(true);
        web.setWebViewClient(new myWebViewClient());



        url = v.findViewById(R.id.url);
        url.setHint(R.string.hint);
        go.setText(R.string.go);


        rel = v.findViewById(R.id.webRel);
        ll = rel.findViewById(R.id.webLinear);
        backward = rel.findViewById(R.id.backButton);
        forward = rel.findViewById(R.id.forwardButton);

                if(parent.open()!=null){
            url.setText(parent.open());
            load();
        }

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(web.canGoBack()){
                    web.goBack();
                }
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(web.canGoForward()){
                    web.goForward();
                }
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });


        return v;

    }

    public void load(){
        String link = url.getText().toString();
        if(!link.contains("http")){
            link = "https://" + link;
        }

        web.loadUrl(link);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = (openPage) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        url.setText(web.getUrl());
    }

    @Override
    public void onResume() {
        super.onResume();
        load();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }




    private class myWebViewClient extends WebViewClient {


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }



}
interface openPage {
    public String open();

}

