package com.o2s.conn;

import com.o2s.data.enm.DeviceType;

public interface Connection extends AutoCloseable {
    
    void runCommand(String cmd);

    String executeCommand(String cmd) throws NonZeroExitStatusException;

    void copyFile(String sourcePath, String targetPath, DeviceType type);

    String executeScript(String path, DeviceType type) throws NonZeroExitStatusException;

    boolean deleteFile(String path, DeviceType type);

}
