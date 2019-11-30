package com.lvfang.mybatisplus.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;

/**
 *@author lvfang
 *@date 2018年08月06日
 */
public class CallResult<T> implements Serializable{

	// 定义jackson对象
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	private int code;
	
	private String message;
	
	private T result;

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public CallResult(int code, String message, T result){
		this.code = code;
		this.message = message;
		this.result = result;
	}
	
	public static <T>CallResult<T> success() {
		return new CallResult<T>(BusinessCodeEnum.DEFAULT_SUCCESS.getCode(), BusinessCodeEnum.DEFAULT_SUCCESS.getMsg(), null);
	}

	public static <T>CallResult<T> success(T result) {
		return new CallResult<T>(BusinessCodeEnum.DEFAULT_SUCCESS.getCode(), BusinessCodeEnum.DEFAULT_SUCCESS.getMsg(), result);
	}
	
	public static <T>CallResult<T> fail() {
		return new CallResult<T>(BusinessCodeEnum.DEFAULT_SYS_ERROR.getCode(), BusinessCodeEnum.DEFAULT_SYS_ERROR.getMsg(), null);
	}
	
	public static <T>CallResult<T> fail(int code, String message) {
		return new CallResult<T>(code, message, null);
	}
	
	public  boolean isSuccess(){
		return this.code == BusinessCodeEnum.DEFAULT_SUCCESS.getCode();
	}

	public static CallResult build(Integer status, String msg, Object data) {
		return new CallResult(status, msg, data);
	}

	/**
	 * 将json结果集转化为CallResult对象
	 *
	 * @param jsonData json数据
	 * @param clazz TaotaoResult中的object类型
	 * @return
	 */
	public static CallResult formatToPojo(String jsonData, Class<?> clazz) {
		try {
			if (clazz == null) {
				return MAPPER.readValue(jsonData, CallResult.class);
			}
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("result");
			Object obj = null;
			if (clazz != null) {
				if (data.isObject()) {
					obj = MAPPER.readValue(data.traverse(), clazz);
				} else if (data.isTextual()) {
					obj = MAPPER.readValue(data.asText(), clazz);
				}
			}
			return build(jsonNode.get("code").intValue(), jsonNode.get("message").asText(), obj);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 没有object对象的转化
	 *
	 * @param json
	 * @return
	 */
	public static CallResult format(String json) {
		try {
			return MAPPER.readValue(json, CallResult.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Object是集合转化
	 *
	 * @param jsonData json数据
	 * @param clazz 集合中的类型
	 * @return
	 */
	public static CallResult formatToList(String jsonData, Class<?> clazz) {
		try {
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("result");
			Object obj = null;
			if (data.isArray() && data.size() > 0) {
				obj = MAPPER.readValue(data.traverse(),
						MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
			}
			return build(jsonNode.get("code").intValue(), jsonNode.get("message").asText(), obj);
		} catch (Exception e) {
			return null;
		}
	}
}
