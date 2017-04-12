//
// Created by arvin on 17/3/2.
//

#include "net_arvin_androidart_jni_NdkTest.h"

JNIEXPORT jstring JNICALL Java_net_arvin_androidart_jni_NdkTest_getSomethingFromNDK
        (JNIEnv *env, jclass type) {
    return env->NewStringUTF("Hello,I am from JNI!");
}

/*
 * Class:     net_arvin_androidart_jni_NdkTest
 * Method:    reduce
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_net_arvin_androidart_jni_NdkTest_reduce
        (JNIEnv *env, jclass type, jint params1, jint params2) {
    return params1 - params2;
}