package com.example.myapplication;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {
    private static DataUtils mContext;


    public  static List<Integer> list =new ArrayList<>(  );
    public void getData2(){
        mContext = DataUtils.this;
        for(int i=0;i<10;i++){
            list.add( i );
            try {
                Thread.sleep( 500 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
