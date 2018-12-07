package com.weyee.sdk.api.interceptor;

import com.weyee.sdk.api.config.JsonUtil;
import com.weyee.sdk.log.Logger;

/**
 * <p>日志打印格式化处理
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
public class HttpLoggingInterceptor implements okhttp3.logging.HttpLoggingInterceptor.Logger {
    private StringBuffer mMessage = new StringBuffer();

    @Override
    public void log(String message) {
        // 请求或者响应开始
        if (message.startsWith("--> POST")) {
            mMessage.setLength(0);
            mMessage.append(" ");
            mMessage.append("\r\n");
        }
        if (message.startsWith("--> GET")) {
            mMessage.setLength(0);
            mMessage.append(" ");
            mMessage.append("\r\n");
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if ((message.startsWith("{") && message.endsWith("}")) || (message.startsWith("[") && message.endsWith("]"))) {
            message = JsonUtil.formatJson(message);
        }
        mMessage.append(message.concat("\n"));
        // 请求或者响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            Logger.e("OkHttp", mMessage.toString());
        }
    }
}