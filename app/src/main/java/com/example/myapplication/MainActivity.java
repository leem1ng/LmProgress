package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.DatabaseUtils;
import android.drm.DrmStore;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private  LmProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        mProgressBar =(LmProgressBar)findViewById( R.id.lmProgressbar );
        mProgressBar.setClickable( true );
        new Thread( new Runnable() {
            @Override
            public void run() {
                for (int i =0 ; i<100 ;i++){
                    try {
                        Thread.sleep( 200 );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int finalI = i;
                    runOnUiThread( new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setRadio( finalI /100.0f );
                        }
                    } );
                }
            }
        }).start();
    }

}



