package com.redxun.saweb.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.redxun.bpm.activiti.entity.BpmMessage;
import com.redxun.bpm.activiti.service.ActRepService;
import com.redxun.bpm.core.entity.BpmDef;
import com.redxun.bpm.core.entity.BpmInst;
import com.redxun.bpm.core.entity.BpmTask;
import com.redxun.bpm.core.manager.BpmDefManager;
import com.redxun.bpm.core.manager.BpmInstManager;
import com.redxun.bpm.core.manager.BpmTaskManager;
import com.redxun.core.engine.FreemarkEngine;
import com.redxun.core.util.Dom4jUtil;
import com.redxun.saweb.util.WebAppUtil;

/**
 * bpm图像热区area脚本,，对应xxxGet.jsp 任意传actDefId actInstId
 * taskId属性之一进标签里面后台可自动生成area标签在jsp
 * 
 * @author 陈茂昌
 * @Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn） 本源代码受软件著作法保护，请在授权允许范围内使用
 * 
 */
public class ImgAreaScriptTag extends TagSupport {

	public final String XML_NS="http://www.omg.org/spec/BPMN/20100524/MODEL";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * actDefId
	 */
	private String actDefId;
	/**
	 * actInstId
	 */
	private String actInstId;
	/**
	 * taskId
	 */
	private String taskId;
	/**
	 * instId
	 */
	private String instId;

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getActDefId() {
		return actDefId;
	}

	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	public String getActInstId() {
		return actInstId;
	}

	public void setActInstId(String actInstId) {
		this.actInstId = actInstId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		try {
			FreemarkEngine freemarkEngine = WebAppUtil.getBean(FreemarkEngine.class);
			Map<String, Object> model = new HashMap<String, Object>();
			List<BpmMessage> list = new ArrayList<BpmMessage>();
			ActRepService actRepService = WebAppUtil.getBean(ActRepService.class);
			if ((StringUtils.isNotEmpty(actDefId)) || (StringUtils.isNotEmpty(instId)) || (StringUtils.isNotEmpty(actInstId)) || (StringUtils.isNotEmpty(taskId))) {
				String xml = new String();
				if (StringUtils.isNotEmpty(actDefId)) {
					model.put("actDefId", actDefId);
					BpmDefManager bpmDefManager = WebAppUtil.getBean(BpmDefManager.class);
					BpmDef bpmDef = bpmDefManager.getByActDefId(actDefId);
					xml = actRepService.getBpmnXmlByDeployId(bpmDef.getActDepId());// 读取xml

				}

				if (StringUtils.isNotEmpty(instId)) {
					model.put("instId", instId);
					BpmInstManager bpmInstManager = WebAppUtil.getBean(BpmInstManager.class);
					BpmInst bpmInst = bpmInstManager.get(instId);
					BpmDefManager bpmDefManager = WebAppUtil.getBean(BpmDefManager.class);
					BpmDef bpmDef = bpmDefManager.getByActDefId(bpmInst.getActDefId());
					xml = actRepService.getBpmnXmlByDeployId(bpmDef.getActDepId());

				}

				if (StringUtils.isNotEmpty(actInstId)) {
					model.put("actInstId", actInstId);
					BpmInstManager bpmInstManager = WebAppUtil.getBean(BpmInstManager.class);
					BpmInst bpmInst = bpmInstManager.getByActInstId(actInstId);
					BpmDefManager bpmDefManager = WebAppUtil.getBean(BpmDefManager.class);
					BpmDef bpmDef = bpmDefManager.getByActDefId(bpmInst.getActDefId());
					xml = actRepService.getBpmnXmlByDeployId(bpmDef.getActDepId());

				}

				if (StringUtils.isNotEmpty(taskId)) {
					model.put("taskId", taskId);
					BpmTaskManager bpmTaskManager = WebAppUtil.getBean(BpmTaskManager.class);
					BpmTask bpmTask = bpmTaskManager.get(taskId);
					String procDefId = bpmTask.getProcDefId();
					BpmDefManager bpmDefManager = WebAppUtil.getBean(BpmDefManager.class);
					BpmDef bpmDef = bpmDefManager.getByActDefId(procDefId);
					xml = actRepService.getBpmnXmlByDeployId(bpmDef.getActDepId());

				}
				xml=xml.replace(XML_NS, "");
				Document document = Dom4jUtil.stringToDocument(xml);
				Element root = document.getRootElement();
				
				List<Node> startEvents = root.selectNodes("process//startEvent");
				List<Element> endEvents = root.selectNodes("process//endEvent");
				List<Element> exclusiveGateway = root.selectNodes("process//exclusiveGateway");
				List<Element> parallelGateway = root.selectNodes("process//parallelGateway");
				List<Element> inclusiveGateway = root.selectNodes("process//inclusiveGateway");
				List<Element> userTaskElements = root.selectNodes("process//userTask");
				List<Element> eventBasedGateway = root.selectNodes("process//eventBasedGateway");

				for (Element element : exclusiveGateway) { // 遍历所有exclusiveGateway分支节点
					String endId = element.attributeValue("id");
					List<Element> nodeShapes = root.element("BPMNDiagram").element("BPMNPlane").elements("BPMNShape");// 把xml中属于BPMNShape的元素取出来
					for (Element nodeShape : nodeShapes) {// 对BPMNShape的Id进行查找bounds元素
						if (nodeShape.attributeValue("bpmnElement").equals(endId)) {
							Element bounds = nodeShape.element("Bounds");
							double upperX = Double.parseDouble(bounds.attributeValue("x")); // 取出左上角顶点x
							double upperY = Double.parseDouble(bounds.attributeValue("y")); // 取出左上角顶点y
							double lowerX = Double.parseDouble(bounds.attributeValue("x")) + Double.parseDouble(bounds.attributeValue("width")); // 取出右下角顶点x
							double lowerY = Double.parseDouble(bounds.attributeValue("y")) + Double.parseDouble(bounds.attributeValue("height")); // 取出右下角顶点y
							String cood = "" + upperX + "," + upperY + "," + lowerX + "," + lowerY;
							String bpmId = nodeShape.attributeValue("bpmnElement");
							BpmMessage bpmMessage = new BpmMessage();
							bpmMessage.setBpmId(bpmId);
							bpmMessage.setCoord(cood);
							bpmMessage.setXPosition(String.valueOf(upperX));
							bpmMessage.setYPosition(String.valueOf(upperY));
							bpmMessage.setWidth(bounds.attributeValue("width"));
							bpmMessage.setHeight(bounds.attributeValue("height"));
							bpmMessage.setShapeType("exclusiveGateway");
							bpmMessage.setTaskName("exclusiveGateway:" + bpmId);
							list.add(bpmMessage);
						}
					}
				}

				for (Element element : parallelGateway) { // 遍历所有分支节点
					String endId = element.attributeValue("id");
					List<Element> nodeShapes = root.element("BPMNDiagram").element("BPMNPlane").elements("BPMNShape");
					for (Element nodeShape : nodeShapes) {
						if (nodeShape.attributeValue("bpmnElement").equals(endId)) {
							Element bounds = nodeShape.element("Bounds");
							double upperX = Double.parseDouble(bounds.attributeValue("x"));
							double upperY = Double.parseDouble(bounds.attributeValue("y"));
							double lowerX = Double.parseDouble(bounds.attributeValue("x")) + Double.parseDouble(bounds.attributeValue("width"));
							double lowerY = Double.parseDouble(bounds.attributeValue("y")) + Double.parseDouble(bounds.attributeValue("height"));
							String cood = "" + upperX + "," + upperY + "," + lowerX + "," + lowerY;
							String bpmId = nodeShape.attributeValue("bpmnElement");
							BpmMessage bpmMessage = new BpmMessage();
							bpmMessage.setBpmId(bpmId);
							bpmMessage.setCoord(cood);
							bpmMessage.setXPosition(String.valueOf(upperX));
							bpmMessage.setYPosition(String.valueOf(upperY));
							bpmMessage.setWidth(bounds.attributeValue("width"));
							bpmMessage.setHeight(bounds.attributeValue("height"));
							bpmMessage.setShapeType("parallelGateway");
							bpmMessage.setTaskName("parallelGateway:" + bpmId);
							list.add(bpmMessage);
						}
					}
				}

				for (Element endEvent : endEvents) { // 遍历所有结束点
					String endId = endEvent.attributeValue("id");
					List<Element> nodeShapes = root.element("BPMNDiagram").element("BPMNPlane").elements("BPMNShape");
					for (Element nodeShape : nodeShapes) {
						if (nodeShape.attributeValue("bpmnElement").equals(endId)) {
							Element bounds = nodeShape.element("Bounds");
							double upperX = Double.parseDouble(bounds.attributeValue("x"));
							double upperY = Double.parseDouble(bounds.attributeValue("y"));
							double lowerX = Double.parseDouble(bounds.attributeValue("x")) + Double.parseDouble(bounds.attributeValue("width"));
							double lowerY = Double.parseDouble(bounds.attributeValue("y")) + Double.parseDouble(bounds.attributeValue("height"));
							String cood = "" + upperX + "," + upperY + "," + lowerX + "," + lowerY;
							String bpmId = nodeShape.attributeValue("bpmnElement");
							BpmMessage bpmMessage = new BpmMessage();
							bpmMessage.setBpmId(bpmId);
							bpmMessage.setCoord(cood);
							bpmMessage.setXPosition(String.valueOf(upperX));
							bpmMessage.setYPosition(String.valueOf(upperY));
							bpmMessage.setWidth(bounds.attributeValue("width"));
							bpmMessage.setHeight(bounds.attributeValue("height"));
							bpmMessage.setShapeType("endNode");
							bpmMessage.setTaskName("endNode:" + bpmId);
							list.add(bpmMessage);
						}
					}
				}

				for (Node node : startEvents) {// 遍历所有开始点
					Element startEvent=(Element)node;
					String startId = startEvent.attributeValue("id");
					List<Element> nodeShapes = root.element("BPMNDiagram").element("BPMNPlane").elements("BPMNShape");
					for (Element nodeShape : nodeShapes) {
						if (nodeShape.attributeValue("bpmnElement").equals(startId)) {
							Element bounds = nodeShape.element("Bounds");
							double upperX = Double.parseDouble(bounds.attributeValue("x"));
							double upperY = Double.parseDouble(bounds.attributeValue("y"));
							double lowerX = Double.parseDouble(bounds.attributeValue("x")) + Double.parseDouble(bounds.attributeValue("width"));
							double lowerY = Double.parseDouble(bounds.attributeValue("y")) + Double.parseDouble(bounds.attributeValue("height"));
							String cood = "" + upperX + "," + upperY + "," + lowerX + "," + lowerY;
							String bpmId = nodeShape.attributeValue("bpmnElement");
							BpmMessage bpmMessage = new BpmMessage();
							bpmMessage.setBpmId(bpmId);
							bpmMessage.setCoord(cood);
							bpmMessage.setXPosition(String.valueOf(upperX));
							bpmMessage.setYPosition(String.valueOf(upperY));
							bpmMessage.setWidth(bounds.attributeValue("width"));
							bpmMessage.setHeight(bounds.attributeValue("height"));
							bpmMessage.setShapeType("startNode");
							bpmMessage.setTaskName("startNode:" + bpmId);
							list.add(bpmMessage);
						}
					}
				}

				for (Element element : userTaskElements) {// 遍历所有userTask节点
					String taskId = element.attributeValue("id");
					List<Element> taskShapes = root.element("BPMNDiagram").element("BPMNPlane").elements("BPMNShape");
					for (Element taskShape : taskShapes) {
						if (taskShape.attributeValue("bpmnElement").equals(taskId)) {// 如果任务Id与图形Id匹配的话,获取左上角右下角坐标值
							Element bounds = taskShape.element("Bounds");
							double upperX = Double.parseDouble(bounds.attributeValue("x"));
							double upperY = Double.parseDouble(bounds.attributeValue("y"));
							double lowerX = Double.parseDouble(bounds.attributeValue("x")) + Double.parseDouble(bounds.attributeValue("width"));
							double lowerY = Double.parseDouble(bounds.attributeValue("y")) + Double.parseDouble(bounds.attributeValue("height"));
							String cood = "" + upperX + "," + upperY + "," + lowerX + "," + lowerY;
							String bpmId = taskShape.attributeValue("bpmnElement");
							String bpmName = element.attributeValue("name");
							BpmMessage bpmMessage = new BpmMessage();
							if(StringUtils.isBlank(bpmName)){//假如未给自定义名字的时候给它赋名
								bpmName="userTask:"+bpmId;
							}
							bpmMessage.setTaskName(bpmName);
							bpmMessage.setBpmId(bpmId);
							bpmMessage.setCoord(cood);
							bpmMessage.setXPosition(String.valueOf(upperX));
							bpmMessage.setYPosition(String.valueOf(upperY));
							bpmMessage.setWidth(bounds.attributeValue("width"));
							bpmMessage.setHeight(bounds.attributeValue("height"));
							bpmMessage.setShapeType("userTask");
							list.add(bpmMessage);
						}
					}

				}

				for (Element element : inclusiveGateway) {// 遍历包含节点
					String taskId = element.attributeValue("id");
					List<Element> taskShapes = root.element("BPMNDiagram").element("BPMNPlane").elements("BPMNShape");
					for (Element taskShape : taskShapes) {
						if (taskShape.attributeValue("bpmnElement").equals(taskId)) {// 如果任务Id与图形Id匹配的话,获取左上角右下角坐标值
							Element bounds = taskShape.element("Bounds");
							double upperX = Double.parseDouble(bounds.attributeValue("x"));
							double upperY = Double.parseDouble(bounds.attributeValue("y"));
							double lowerX = Double.parseDouble(bounds.attributeValue("x")) + Double.parseDouble(bounds.attributeValue("width"));
							double lowerY = Double.parseDouble(bounds.attributeValue("y")) + Double.parseDouble(bounds.attributeValue("height"));
							String cood = "" + upperX + "," + upperY + "," + lowerX + "," + lowerY;
							String bpmId = taskShape.attributeValue("bpmnElement");
							BpmMessage bpmMessage = new BpmMessage();
							bpmMessage.setTaskName("inclusiveGateway:" + bpmId);
							bpmMessage.setBpmId(bpmId);
							bpmMessage.setCoord(cood);
							bpmMessage.setXPosition(String.valueOf(upperX));
							bpmMessage.setYPosition(String.valueOf(upperY));
							bpmMessage.setWidth(bounds.attributeValue("width"));
							bpmMessage.setHeight(bounds.attributeValue("height"));
							bpmMessage.setShapeType("inclusiveGateway");
							list.add(bpmMessage);
						}
					}

				}

				for (Element element : eventBasedGateway) {// 遍历事件节点
					String taskId = element.attributeValue("id");
					List<Element> taskShapes = root.element("BPMNDiagram").element("BPMNPlane").elements("BPMNShape");
					for (Element taskShape : taskShapes) {
						if (taskShape.attributeValue("bpmnElement").equals(taskId)) {// 如果任务Id与图形Id匹配的话,获取左上角右下角坐标值
							Element bounds = taskShape.element("Bounds");
							double upperX = Double.parseDouble(bounds.attributeValue("x"));
							double upperY = Double.parseDouble(bounds.attributeValue("y"));
							double lowerX = Double.parseDouble(bounds.attributeValue("x")) + Double.parseDouble(bounds.attributeValue("width"));
							double lowerY = Double.parseDouble(bounds.attributeValue("y")) + Double.parseDouble(bounds.attributeValue("height"));
							String cood = "" + upperX + "," + upperY + "," + lowerX + "," + lowerY;
							String bpmId = taskShape.attributeValue("bpmnElement");
							BpmMessage bpmMessage = new BpmMessage();
							bpmMessage.setTaskName("eventBasedGateway:" + bpmId);
							bpmMessage.setBpmId(bpmId);
							bpmMessage.setCoord(cood);
							bpmMessage.setXPosition(String.valueOf(upperX));
							bpmMessage.setYPosition(String.valueOf(upperY));
							bpmMessage.setWidth(bounds.attributeValue("width"));
							bpmMessage.setHeight(bounds.attributeValue("height"));
							bpmMessage.setShapeType("eventBasedGateway");
							list.add(bpmMessage);
						}
					}

				}
			}

			model.put("list", list);
			String html = freemarkEngine.mergeTemplateIntoString("imgAreaScript.ftl", model);
			out.println(html);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return SKIP_BODY;
	}

}
