/*
 * Portable Audio I/O Library
 * Java Binding for PortAudio
 *
 * Based on the Open Source API proposed by Ross Bencina
 * Copyright (c) 2008 Ross Bencina
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files
 * (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/*
 * The text above constitutes the entire PortAudio license; however, 
 * the PortAudio community also makes the following non-binding requests:
 *
 * Any person wishing to distribute modifications to the Software is
 * requested to send the modifications to the original developer so that
 * they can be incorporated into the canonical version. It is also 
 * requested that these non-binding requests be included along with the 
 * license above.
 */

#include "com_portaudio_PortAudio.h"
#include "portaudio.h"
#include "pa_win_wasapi.h"

#ifndef JPA_TOOLS_H
#define JPA_TOOLS_H

jint jpa_GetIntField( JNIEnv *env, jclass cls, jobject obj, const char *fieldName );
void jpa_SetIntField( JNIEnv *env, jclass cls, jobject obj, const char *fieldName, jint value );

jlong jpa_GetLongField( JNIEnv *env, jclass cls, jobject obj, const char *fieldName );
void jpa_SetLongField( JNIEnv *env, jclass cls, jobject obj, const char *fieldName, jlong value );

jdouble jpa_GetDoubleField( JNIEnv *env, jclass cls, jobject obj, const char *fieldName );
void jpa_SetDoubleField( JNIEnv *env, jclass cls, jobject obj, const char *fieldName, jdouble value );

void jpa_SetStringField( JNIEnv *env, jclass cls, jobject obj, const char *fieldName, const char *value );
PaStreamParameters *jpa_FillStreamParameters( JNIEnv *env, jobject jstreamParam, PaStreamParameters *myParams );

jint jpa_CheckError( JNIEnv *env, PaError err );
jint jpa_ThrowError( JNIEnv *env, const char *message );

PaStream *jpa_GetStreamPointer( JNIEnv *env, jobject blockingStream );

jstring jpa_GetObjectClassName( JNIEnv *env, jobject obj );
jobject jpa_GetHostParams( JNIEnv *env, jobject obj );
PaWasapiStreamInfo jpa_GetWasapiParams( JNIEnv *env, jobject obj );

#endif /* JPA_TOOLS_H */
