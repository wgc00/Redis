package com.wgc.decorator;

import java.io.IOException;
import java.io.OutputStream;

public class LogOutputStram extends OutputStream {

    private OutputStream os;

    public LogOutputStram(OutputStream outputStream) {
        this.os = outputStream;
    }

    @Override
    public void write(int b) throws IOException {
        this.os.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        System.out.println("开始要做什么是");
        super.write(b);
        System.out.println("结束又要做什么事情");
    }
}
