package com.cttnet.zhwg.ywkt.exception;

/**
 * 业务异常
 *
 * @author wangzefeng
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -2170357660530652164L;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String errorMessageTemplate, Object... args) {
        super(format(errorMessageTemplate, args));
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    /**
     * 替换每个占位符 %s 在 template 的位置。
     * 如果有比占位符更多的参数，多余的参数用中括号括起来，附在错误信息末尾
     *
     * @param template 包含 0 个或多个 %s 占位符的字符串模板
     * @param args     代入到消息模板的参数。参数被转换为使用字符串String.valueOf(Object)。参数可以为空。
     * @return 格式化后字符串
     */
    protected static String format(String template, Object... args) {
        // null -> "null"
        template = String.valueOf(template);

        // 开始替换占位符 %s
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template, templateStart, placeholderStart);
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template, templateStart, template.length());

        // 多余的参数用中括号括起来，附在错误信息末尾
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }
}
