package com.rookies3.myspringbootlab.config;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MyEnvironment {
    String mode;
}
