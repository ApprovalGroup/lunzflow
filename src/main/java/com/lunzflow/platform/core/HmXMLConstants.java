package com.lunzflow.platform.core;

public interface  HmXMLConstants {

	/**
	 * 名称空间前缀
	 */
	public static final String NAMESPACE_PREFIX = "custom";
	/**
	 * 名称空间
	 */
	public static final String NAMESPACE = "http://www.custom.com/";
	/**
	 * 审批操作类型：会签审批、或签审批、依次审批
	 */
	public static final String APPROVALOPERATE_TYPE = "approvaloperatetype";
	/**
	 * 抄送人：指定人员、所属部门、角色、部门选择
	 */
	public static final String COPYTO = "copyto";
	/**
	 * 审批环节：一审、二审、三审、终审、打印、盖章
	 */
	public static final String APPROVAL = "approval";
	/**
	 * 操作开关：加签、允许退回、不同意、安全确认、强制跳转
	 */
	public static final String OPERATIONSWITCH = "operationswitch";
	/**
	 * 超时提醒:短信催办提醒、微信催办提醒、邮件催办提醒
	 */
	public static final String TIMEOUTREMINDER = "timeoutreminder";
	/**
	 * 超时时间(小时)
	 */
	public static final String TIMEOUT = "timeout";
	
	/**
	 * 报告撰写人所属部门
	 */
	public static final String REPORTWRITERDEPT = "reportWriterDept";
	
	/**
	 * 发起人所属部门
	 */
	public static final String SPONSOR = "sponsorDept";
	
	/**
	 * 当前用户所属部门
	 */
	public static final String CURRENT_USER_DEPT = "currentUserDept";

}
