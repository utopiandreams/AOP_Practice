package com.example.NplusOneDetector.aop;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NPlusOneLogger {
    boolean flag;
    String objectName;
    String request;
}
