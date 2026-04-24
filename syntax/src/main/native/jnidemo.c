#include <jni.h>
#include "com_huauauaa_syntax_jni_JniMath.h"

JNIEXPORT jint JNICALL Java_com_huauauaa_syntax_jni_JniMath_increment0(
        JNIEnv *env, jclass clazz, jint value) {
    (void) env;
    (void) clazz;
    return value + 1;
}
