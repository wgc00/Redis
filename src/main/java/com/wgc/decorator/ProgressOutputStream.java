package com.wgc.decorator;

import java.io.IOException;
import java.io.OutputStream;

/*装饰模式：灵活*/
/*
* 装饰模式： 是在不改变源文件或是使用继承的情况下，动态的扩展一个对象的功能，
* 它通过创建一个包装对象，也就是装饰包裹真实的对象。
* */
public class ProgressOutputStream extends OutputStream {
    private OutputStream ous;

    public ProgressOutputStream(OutputStream outputStream) {
        this.ous = outputStream;
    }

    @Override
    public void write(int b) throws IOException {
        System.out.println("这个添加额外功能了, 功能是进度条");
        this.ous.write(b);
    }


}
