package com.example.kage.httpserver;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import junit.framework.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private AsyncHttpServer server = new AsyncHttpServer();
    private AsyncServer myAsyncServer = new AsyncServer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startServer();
    }

    public String readFileAsString(String path) {
        AssetManager assetManager = getAssets();

        String index = "Nothing found";
        InputStream input;
        try {
            input = assetManager.open(path.substring(1, path.length()));
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            index = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return index;
    }

    private void startServer() {
        AssetManager am = getAssets();
        server.get("/api", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                response.send("dummy");
            }
        });
        server.directory(this, "/.*?", "site/");
//        server.get("/.*", new HttpServerRequestCallback() {
//                    @Override
//                    public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse
//                            response) {
//                        String path = request.getPath();
//                        Log.d("myTag", "onRequest: path = "+path);
//
//                        if (path.equals("/")) {
//                            path = "/index.html";
//                        }
//
//                        response.send()
//                        response.send(readFileAsString(path));
//                        Log.d("myTag", "onRequest:" + request.toString());
//                    }
//                }
//        );
//        server.get("favicon.ico", new HttpServerRequestCallback() {
//            @Override
//            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
//                response.send(readFileAsString("favicon.ico"));
//            }
//        });

        server.listen(myAsyncServer, 8080);
    }
}
