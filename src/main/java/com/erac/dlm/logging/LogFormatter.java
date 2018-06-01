package com.erac.dlm.logging;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by E676P7 on 5/24/2018.
 */
public class LogFormatter {

    @Value("${rsi.application.name}")
    private static String appName;

    //todo: add changes to follow ehi logging policy


}
