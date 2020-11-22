package com.kncept.abacemtex.annotation;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Must be used/called to generate a valid cemtex file
 */
@Retention(RUNTIME)
public @interface Mandatory {
}
