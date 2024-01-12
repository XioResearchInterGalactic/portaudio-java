cmake_minimum_required(VERSION 3.2)

project(jportaudio_project)

include_directories($ENV{JAVA_HOME}/include)
include_directories($ENV{JAVA_HOME}/include/win32)
include_directories($ENV{JAVA_HOME}/include/darwin)
include_directories($ENV{JAVA_HOME}/include/linux)

# for installed portaudio.h
include_directories(portaudio/include)

#link_directories(/usr/local/lib)
link_directories(portaudio/pagit)
link_directories(portaudio/pagit/lib)
link_directories(portaudio/x64/RelWithDebInfo)
link_directories(portaudio/win32/RelWithDebInfo)
link_directories(portaudio/build)

set (jportaudio_sources
    src/main/jni/com_portaudio_BlockingStream.c
    src/main/jni/com_portaudio_PortAudio.c
    src/main/jni/jpa_tools.c
    )

set (jportaudio_libname jportaudio_0_1_0)

add_library(${jportaudio_libname} SHARED ${jportaudio_sources})
target_link_libraries(${jportaudio_libname} portaudio)

install(TARGETS ${jportaudio_libname} DESTINATION lib)
