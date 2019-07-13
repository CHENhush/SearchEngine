package cn.zucc.searchfinal.vo;


import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ResponseResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private Integer code;

    private String msg;

    private Object data;

    private boolean successResponse;


    public ResponseResult() {

    }

    public ResponseResult(Object data) {
        this.code = 200;
        this.msg = "OK";
        this.data = data;
        this.successResponse = true;
    }

    public ResponseResult(Integer code, String msg, Object data, boolean successResponse) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.successResponse = successResponse;
    }

    public static ResponseResult ok() {
        return new ResponseResult(null);
    }

    public static ResponseResult ok(Object data) {
        return new ResponseResult(data);
    }

    public static ResponseResult build(Integer code, String msg, Object data, boolean successResponse) {
        return new ResponseResult(code, msg, data, successResponse);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccessResponse() {
        return successResponse;
    }

    public void setSuccessResponse(boolean successResponse) {
        this.successResponse = successResponse;
    }

    public static ResponseResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, ResponseResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(), obj, jsonNode.get("successResponse").asBoolean());
        } catch (Exception e) {
            return null;
        }
    }

    public static ResponseResult format(String json) {
        try {
            return MAPPER.readValue(json, ResponseResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(), obj, jsonNode.get("successResponse").asBoolean());
        } catch (Exception e) {
            return null;
        }
    }
}
