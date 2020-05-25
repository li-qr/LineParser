package com.elong.hotel.quan.exception;

import com.elong.hotel.quan.Quan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileCanNotOpenException extends Exception {
    private static final long serialVersionUID = 1229965732237774653L;
    private static final Logger logger = LoggerFactory.getLogger(Quan.LOGGER);

    public FileCanNotOpenException(){
        super();
    }

    public FileCanNotOpenException(String message){
        super(message);
        logger.error("文件流读取失败。{}",message);
    }
}
