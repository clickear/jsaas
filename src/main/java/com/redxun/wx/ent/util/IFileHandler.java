package com.redxun.wx.ent.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;



public interface IFileHandler {
	
	void handInputStream(HttpResponse response, InputStream is) throws IOException;

}
