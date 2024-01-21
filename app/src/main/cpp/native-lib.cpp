#include <jni.h>

#include <android/native_window_jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_gandis_kamera_MainActivity_stringFromJNI(JNIEnv* env,
                                                  jobject /* this */)
{
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT void JNICALL
Java_com_gandis_kamera_MainActivity_createVulkanSwapchain(JNIEnv* env,
                                                                     jobject thiz,
                                                                     jobject surface)
{
    // Surface 객체를 ANativeWindow로 변환
    ANativeWindow* nativeWindow = ANativeWindow_fromSurface(env, surface);

    // Vulkan 스왑체인 생성
    // ...

    // 필요한 Vulkan 초기화 및 리소스 생성
    // ...

    ANativeWindow_release(nativeWindow);
}