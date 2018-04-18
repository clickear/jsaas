package com.redxun.saweb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;


public class SpringRestServlet extends DispatcherServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {

		addCrosHeader(request,response);
		
		super.doService(request, response);
	}
	
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		addCrosHeader(req,resp);
		super.doHead(req, resp);
	}
	
	@Override
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		addCrosHeader(request,response);
		super.doDispatch(request, response);
	}
	
	@Override
	protected void doTrace(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		addCrosHeader(request,response);
		super.doTrace(request, response);
	}
	
	private void addCrosHeader(HttpServletRequest request,HttpServletResponse response){
		
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE"); 
		response.setHeader("Access-Control-Max-Age", "3600"); 
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization"); 
		response.setHeader("Access-Control-Allow-Credentials","true");

	}
}
