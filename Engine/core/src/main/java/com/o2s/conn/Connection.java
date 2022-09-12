package com.o2s.conn;


public interface Connection extends AutoCloseable {
    
    void runCommand(String cmd);

    String executeCommand(String cmd);

    void copyFile(String sourcePath, String targetPath, boolean updatePermissions);

}
