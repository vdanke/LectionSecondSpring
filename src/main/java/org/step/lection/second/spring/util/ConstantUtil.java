package org.step.lection.second.spring.util;

public class ConstantUtil {

    public static final String UNIVERSAL_QUERY_USERNAME = "select u from #{#entityName} u where u.username = ?1";
}
