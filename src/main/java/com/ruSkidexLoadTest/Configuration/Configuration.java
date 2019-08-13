package com.ruSkidexLoadTest.Configuration;

import java.io.File;
import java.io.FileWriter;

public class Configuration {
    public final static String SUSTEM_REP = System.getProperty("user.dir");
    public final static String FILE_OUT_REP =SUSTEM_REP +"/";
    public final static File NEW_FILE = new File(SUSTEM_REP,"TestOut.txt");
    public static FileWriter WRITER_FILE_OUT;
}
