package com.redis.Result;

import com.redis.base.exception.ExceptionCodeDef;
import com.redis.base.vo.EmptyBody;
import com.redis.base.vo.ResponseStatusModel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by zhangguilin on 1/26/2018.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseModel<T> {

    private int resultCode = 200;

    private String message = "SUCCESS";

    private T data;

    public static ResponseModel<EmptyBody> empty() {
        return ok(EmptyBody.INSTANCE);
    }

    public static <T> ResponseModel<T> ok(@NonNull final T data) {
        ResponseModel<T> responseModel = new ResponseModel<>();
        responseModel.setData(data);
        responseModel.setResultCode(ExceptionCodeDef.SC_OK);
        responseModel.setMessage(StringUtils.EMPTY);
        return responseModel;
    }

    public static ResponseModel<EmptyBody> error(final ResponseStatusModel statusModel){
        ResponseModel<EmptyBody> responseModel = new ResponseModel<>();
        responseModel.setResultCode(statusModel.getStatusCode());
        responseModel.setMessage(statusModel.getErrorMessage());
        responseModel.setData(EmptyBody.INSTANCE);
        return responseModel;
    }

}
