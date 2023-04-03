package com.johann.designPattern.dahao.chainOfResponsibility;

import lombok.Data;

/** 请求
 * @ClassName: Request
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
@Data
public class Request {

    private String RequestId;

    private String RequestName;

    private String RequestType;

    private Integer RequestLevel;

    private String RequestContent;
}
