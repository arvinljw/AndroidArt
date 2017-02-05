package net.arvin.afbaselibrary.nets.converters;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import net.arvin.afbaselibrary.nets.entities.ResultEntity;
import net.arvin.afbaselibrary.nets.exceptions.ResultException;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * created by arvin on 16/10/24 17:24
 * email：1035407623@qq.com
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type adapter;

    public GsonResponseBodyConverter(Gson gson, Type adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string().replace("SUCCESS", 200 + "");
        try {
            Logger.i(response);
            ResultEntity resultModel = gson.fromJson(response, ResultEntity.class);
            if (resultModel.getStatus() == 200) {
                if (resultModel.getContent() != null) {
                    return gson.fromJson(resultModel.getContent(), adapter);
                }
                return null;
            } else {
                throw new ResultException(resultModel.getStatus(), resultModel.getMessage());
            }
        } finally {
            value.close();
        }
    }
}
