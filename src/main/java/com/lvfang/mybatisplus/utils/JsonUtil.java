package com.lvfang.mybatisplus.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: LvFang
 * @Date: Created in 2018/6/5.
 * @Description:
 */
public class JsonUtil {

    private static final ObjectMapper jsonMapper = new ObjectMapper();

    public JsonUtil() {
    }

    public static String stringOf(Object target) {
        try {
            return jsonMapper.writeValueAsString(target);
        } catch (Exception var2) {
            throw new RuntimeException("Error while converting " + target.getClass() + ": " + target + " to json string", var2);
        }
    }

    public static String stringOfObjects(List<Object> target) {
        try {
            if(!target.isEmpty()) {
                StringBuilder e = new StringBuilder();
                if(target.size() > 1) {
                    for(int i = 0; i < target.size(); ++i) {
                        if(target.get(i) != null) {
                            String value = jsonMapper.writeValueAsString(target.get(i));
                            if(i != target.size() - 1) {
                                if(value.endsWith("}")) {
                                    e.append(value.substring(0, value.length() - 1));
                                } else {
                                    e.append(value);
                                }

                                e.append(",");
                            }

                            if(i != 0) {
                                if(value.startsWith("{")) {
                                    e.append(value.substring(1, value.length()));
                                } else {
                                    e.append(value);
                                }
                            }
                        }
                    }

                    return e.toString();
                } else {
                    return jsonMapper.writeValueAsString(target.get(0));
                }
            } else {
                return "";
            }
        } catch (Exception var4) {
            throw new RuntimeException("Error while converting " + target.getClass() + ": " + target + " to json string", var4);
        }
    }

    public static <T> T readJson2Entity(String jsonString, Class<T> valueType) throws Exception {
        try {
            return jsonMapper.readValue(jsonString, valueType);
        } catch (Exception var3) {
            throw new Exception(var3);
        }
    }

    public static <T> T readJson2Entity(String jsonString, TypeReference<T> typeReference) throws Exception {
        try {
            return jsonMapper.readValue(jsonString, typeReference);
        } catch (Exception var3) {
            throw new Exception(var3);
        }
    }

    public static String toJson(Object obj) {
        if(obj != null && obj instanceof Serializable) {
            try {
                return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            } catch (Exception var2) {
                var2.printStackTrace();
                return "";
            }
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        System.out.println();
    }

    static {
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
