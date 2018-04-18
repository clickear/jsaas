package com.redxun.pub.anony.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.redxun.sys.core.controller.FileController;

/**
 *  匿名文件上传,继承FileController，重写访问地址
 * @author csx
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 *
 */
@RequestMapping("/pub/anony/")
@Controller
public class AnonyFileUploadController extends FileController {
	
	@RequestMapping("upload")
	@Override
	public void upload(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		super.upload(request, response);
	}
	
	@RequestMapping("previewImage")
	@Override
	public void previewImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.previewImage(request, response);
	}
	
	@RequestMapping("imageView")
	@Override
	public void imageView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.imageView(request, response);
	}
}
