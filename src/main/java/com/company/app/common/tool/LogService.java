package com.company.app.common.tool;

import com.company.app.core.temp.tool.api.DataExtractorTool;
import com.google.common.io.ByteSource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogService {

    public static final String LOG_ZIP_FILE_NAME = "logs.zip";
    public static final String PACKAGE_NAME = "logs";

    private final DataExtractorTool dataExtractorService;

    @SneakyThrows
    public InputStream getLogsAsInputStream() {
        return ByteSource.wrap(getLogsAsZip()).openStream();
    }

    @SneakyThrows
    public byte[] getLogsAsZip() {
        if (log.isDebugEnabled()) {
            log.debug("Пробую положить все файлы из папки [{}] в архив [{}].", PACKAGE_NAME, LOG_ZIP_FILE_NAME);
        }
        List<File> files = dataExtractorService.getFiles(PACKAGE_NAME);
        byte[] bytes = getBytes(files);
        FileUtils.forceDelete(new File(LOG_ZIP_FILE_NAME));
        return bytes;
    }

    private byte[] getBytes(List<File> files) throws IOException {
        try (ZipFile zipFile = new ZipFile(LOG_ZIP_FILE_NAME)) {
            zipFile.addFiles(files);
            return FileUtils.readFileToByteArray(zipFile.getFile());
        }
    }

}