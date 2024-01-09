package com.portaudio;

public class WasapiParams implements HostParams  {

    public int flags = 0;

    public int threadPriority = ThreadPriorityNone;

    public int streamCategory = StreamCategoryOther;

    public int streamOption = StreamOptionNone;

    /**
     * Put WASAPI into exclusive mode.
     */
    public static final int Exclusive = 1;
    /**
     * Allow to skip internal PA processing completely.
     */
    public static final int RedirectHostProcessor = 1 << 1;
    /**
     * Assign custom channel mask.
     */
    public static final int UseChannelMask = 1 << 2;
    /**
     * Select non-Event driven method of data read/write.
     * Note: WASAPI Event driven core is capable of 2ms latency!!!, but Polling
     * method can only provide 15-20ms latency.
     */
    public static final int Polling = 1 << 3;
    /**
     * Force custom thread priority setting, must be used if PaWasapiStreamInfo::threadPriority
     * is set to a custom value.
     */
    public static final int ThreadPriority = 1 << 4;
    /**
     * Force explicit sample format and do not allow PA to select suitable working format, API will
     * fail if provided sample format is not supported by audio hardware in Exclusive mode
     * or system mixer in Shared mode.
     */
    public static final int ExplicitSampleFormat = 1 << 5;
    /**
     * Allow API to insert system-level channel matrix mixer and sample rate converter to allow
     * playback formats that do not match the current configured system settings.
     * This is in particular required for streams not matching the system mixer sample rate.
     * Only applies in Shared mode.
     */
    public static final int AutoConvert = 1 << 6;

    /* Thread Priority */
    public static final int ThreadPriorityNone = 0;
    public static final int ThreadPriorityAudio = 1;
    public static final int ThreadPriorityCapture = 2;
    public static final int ThreadPriorityDistribution = 3;
    public static final int ThreadPriorityGames = 4;
    public static final int ThreadPriorityPlayback = 5;
    public static final int ThreadPriorityProAudio = 6;
    public static final int ThreadPriorityWindowManager = 7;

    /* Stream Option */
    public static final int StreamOptionNone = 0;
    public static final int StreamOptionRaw = 1;
    public static final int StreamOptionMatchFormat = 2;

    /* Stream Category */
    public static final int StreamCategoryOther = 0;
    public static final int StreamCategoryCommunications = 3;
    public static final int StreamCategoryAlerts = 4;
    public static final int StreamCategorySoundEffects = 5;
    public static final int StreamCategoryGameEffects = 6;
    public static final int StreamCategoryGameMedia = 7;
    public static final int StreamCategoryGameChat = 8;
    public static final int StreamCategorySpeech = 9;
    public static final int StreamCategoryMovie = 10;
    public static final int StreamCategoryMedia = 11;
}
