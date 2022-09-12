package com.o2s.conn;

import java.io.File;

public interface Connection extends AutoCloseable {
    
    void runCommand(String cmd);

    String executeCommand(String cmd);

    void copyFile(File file, String path);

}
