package com.wgc.decorator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] arge) throws IOException {

        FileOutputStream fus = new FileOutputStream(new File("d:/wang.txt"));
        ProgressOutputStream pus = new ProgressOutputStream(fus);
        LogOutputStram logOutputStram = new LogOutputStram(pus);
        logOutputStram.write("You A is are person".getBytes());
    }
}
