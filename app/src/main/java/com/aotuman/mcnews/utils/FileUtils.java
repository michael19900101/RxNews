package com.aotuman.mcnews.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * Created by aotuman on 2017/4/25.
 */

public class FileUtils {
    private void getFiles(){
        String basePath = Environment.getExternalStorageDirectory().getPath();
        File rootFile = new File(basePath);

        Observable.just(rootFile)
                // 遍历文件夹,通过flatMap转换为Observable<File>
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        return listFiles(file);
                    }
                })
                // 筛选以.jpg结尾的文件
                .filter(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File file) {
                        return file.getName().endsWith(".jpg");
                    }
                })
                // 得到复合条件的文件名
                .map(new Func1<File, String>() {
                    @Override
                    public String call(File file) {
                        return file.getName();
                    }
                })
                // 通过toList装到集合(如果不用toList,那就直接返回String,如果有1000条记录,那么onNext会执行1000次)
                .toList()
                // 通过订阅发送给观察者
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<String> s) {
                        Log.e("test","onNext "+ s.size());
                    }
                });
    }

    // 遍历文件夹
    private Observable<File> listFiles(File file){
        if(file.isDirectory()){
            return Observable.from(file.listFiles()).flatMap(new Func1<File, Observable<File>>() {
                @Override
                public Observable<File> call(File file) {
                    return listFiles(file);
                }
            });
        }else {
            return Observable.just(file);
        }
    }
}
