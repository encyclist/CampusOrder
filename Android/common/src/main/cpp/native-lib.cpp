#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jint JNICALL
Java_com_erning_common_sharedperference_CPPUtils_getD4(JNIEnv *env, jclass type) {
    int a=160,b,c,d;

    a = a >> 5;
    b = 53 & 76;
    c = 118 ^ 116;
    d = ~ -1;

    return a*1000 + b*100 + c*10 +d;
    // 从反汇编的内容来看，这些计算根本没有意义😂
    // 它直接返回了最终结果
}