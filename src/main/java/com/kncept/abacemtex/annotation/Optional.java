package com.kncept.abacemtex.annotation;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Does not have to be used/called to generate a valid cemtex file
 */
@Retention(RUNTIME)
public @interface Optional {
}
