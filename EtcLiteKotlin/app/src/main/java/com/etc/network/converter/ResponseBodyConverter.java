package com.etc.network.converter;


import static java.nio.charset.StandardCharsets.UTF_8;

import com.etc.network.ApiResponse;
import com.etc.network.AppException;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @Author holo
 * @Date 2022/1/27
 */
public class ResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    ResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string().toString();

        JsonReader jsonReader = null;
        MediaType mediaType = value.contentType();
        Charset charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        jsonReader = gson.newJsonReader(new InputStreamReader(inputStream, charset));

        try {
            T result = adapter.read(jsonReader);
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
            return result;
        } catch (Exception ex) {
            if ("1".equals(response)) {
                response = " {\"data\":null,\"code\":200,\"msg\":\"\"}";
                ApiResponse<String> r = new ApiResponse<String>(200, response, "success");
                return (T) r;
            } else {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    //单独为判断时间加的判断
                    if (jsonObject.getString("v") != null) {
                        JSONObject data = (JSONObject) jsonObject.get("data");
                        String t = data.getString("t");
                        ApiResponse<String> r = new ApiResponse<String>(200, t, "success");
                        return (T) r;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String code = "";
                code = String.valueOf(jsonObject != null ? jsonObject.optInt("code") : 200);
                if (code.equals("0")) {
                    code = jsonObject.optString("code");
                }
                String message = jsonObject != null ? jsonObject.optString("msg") : "";

                throw new AppException(message, code);
            }
        } finally {
            value.close();
        }
    }
}

