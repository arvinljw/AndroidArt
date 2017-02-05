package net.arvin.afbaselibrary.nets.entities;

import com.google.gson.JsonElement;

/**
 * created by arvin on 16/10/24 17:25
 * email：1035407623@qq.com
 */
public class ResultEntity {
    /**
     * 状态码
     */
    private int status;
    /**
     * 处理消息
     */
    private String message;
    /**
     * 内容
     */
    private JsonElement content;

    private InputErrorEntity inputError;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        if (inputError != null) {
            String temp = inputError.getMessage();
            if (temp != null) {
                return temp;
            }
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getContent() {
        return content;
    }

    public void setContent(JsonElement content) {
        this.content = content;
    }

    public InputErrorEntity getInputError() {
        return inputError;
    }

    public void setInputError(InputErrorEntity inputError) {
        this.inputError = inputError;
    }
}
