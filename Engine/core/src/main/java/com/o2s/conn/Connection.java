package com.o2s.conn;

import com.o2s.conn.ex.NonZeroExitStatusException;
import com.o2s.data.dto.DeviceDto;
import com.o2s.data.enm.DeviceType;

public interface Connection extends AutoCloseable {

    String getHost();
    
    void runCommand(String cmd);

    String executeCommand(String cmd) throws NonZeroExitStatusException;

    void copyFile(String sourcePath, String targetPath, DeviceDto device, boolean replaceProps);

    void runScript(String path, DeviceType type, String extention);

    String executeScript(String path, DeviceType type, String extention) throws NonZeroExitStatusException;

    boolean deleteFile(String path, DeviceType type);

    String mkDir(String targetDir, DeviceType type) throws NonZeroExitStatusException;

    boolean extractFile(String basePath, String fleName, String targetFolder, DeviceType type);

}
