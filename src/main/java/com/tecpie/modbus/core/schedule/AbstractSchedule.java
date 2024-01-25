package com.tecpie.modbus.core.schedule;

import com.serotonin.modbus4j.BatchResults;
import com.serotonin.modbus4j.ModbusMaster;
import com.tecpie.modbus.entity.CachePoint;
import com.tecpie.modbus.entity.CacheTask;
import com.tecpie.modbus.exception.ModbusCommunicationException;
import com.tecpie.modbus.toolkit.DateTimeUtil;
import com.tecpie.modbus.toolkit.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSchedule {

    private final String filePath;

    private final String preFileName;

    private final String fileExtension;

    protected AbstractSchedule(String filePath, String preFileName, String fileExtension) {
        this.filePath = filePath;
        this.preFileName = preFileName;
        this.fileExtension = fileExtension;
    }

    protected static final Logger logger = LoggerFactory.getLogger(AbstractSchedule.class);

    protected abstract Runnable run(List<CacheTask> cacheTaskList, ModbusMaster modbusMaster);

    protected void initAndWrite(CacheTask cacheTask, List<CachePoint> offsetList, BatchResults<Integer> results,
                                String formatStyle) {
        List<String> lineList = new ArrayList<>();
        String format = DateTimeUtil.format(LocalDateTime.now(), formatStyle);
        String format2 = DateTimeUtil.format(LocalDateTime.now(), "yyyyMMdd HHmm");
        String fileName = format2 + fileExtension;
        File file = new File(filePath + preFileName + fileName);
        offsetList.forEach(point -> {
            String value = results.getValue(point.getOffset()).toString();
            logger.info("{} ---> function:{},point:{},value:{}", filePath + preFileName + fileName, cacheTask.getFunction(), point.getName(), value);
            lineList.add(String.format("%s,%s,%s,%s,%s", format, point.getOffset(), value, point.getPlant(), point.getName()));
        });
        try {
            FileUtil.writeLines(file, StandardCharsets.UTF_8.toString(), lineList, "\n", true);
        } catch (IOException e) {
            throw new ModbusCommunicationException(e);
        }
    }

}
