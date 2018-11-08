package com.jcohy.utils;

import com.jcohy.utils.enums.DocumentType;

import java.io.File;

/**
 * Created by jiac on 2018/11/8.
 * ClassName  : com.jcohy.utils
 * Description  :
 */
public interface DocumentConverter {
    File documentConverter(File file,String path);
}
