package co.newco.newco_android.network;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * Created by jayd on 11/11/15.
 * Source: https://github.com/square/retrofit/blob/07d1f3de5e0eb11fb537464bd14fdbacfc9e55a7/retrofit/src/test/java/retrofit/ToStringConverterFactory.java
 */
class ToStringConverterFactory extends Converter.Factory {
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain");

    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        if (String.class.equals(type)) {
            return new Converter<ResponseBody, String>() {
                @Override public String convert(ResponseBody value) throws IOException {
                    return value.string();
                }
            };
        }
        return null;
    }

    @Override public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        if (String.class.equals(type)) {
            return new Converter<String, RequestBody>() {
                @Override public RequestBody convert(String value) throws IOException {
                    return RequestBody.create(MEDIA_TYPE, value);
                }
            };
        }
        return null;
    }
}