package com.jrsoft.order.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

import com.jrsoft.order.entity.OrderHeader;

/**
 * 
 */

/**
 * 外部文件读取接口
 * 
 * <p>
 * 外部文件读取类必须实现此接口中的readFile方法
 * </p>
 * 
 * @author chrismao
 * 
 * @version 1.0
 *
 */
public interface DataReader {
	public ArrayList<OrderHeader> readFile(String inputFileName) throws IOException, DataFormatException;
}
