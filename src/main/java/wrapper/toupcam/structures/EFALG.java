package wrapper.toupcam.structures;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public interface EFALG {

    class eFALG extends Structure {

        public static int FLAG_CMOS = 0x00000001;       //cmos sensor
        public static int FLAG_CCD_PROGRESSIVE = 0x00000002;   // progressive ccd sensor
        public static int FLAG_CCD_INTERLACED = 0x00000004;    //interlaced ccd sensor
        public static int FLAG_ROI_HARDWARE = 0x00000008;    // support hardware ROI
        public static int FLAG_MONO = 0x00000010;     // monochromatic
        public static int FLAG_BINSKIP_SUPPORTED = 0x00000020;    // support bin/skip mode
        public static int FLAG_USB30 = 0x00000040;    // USB 3.0
        public static int FLAG_COOLED = 0x00000080;    // Cooled
        public static int FLAG_USB30_OVER_USB20 = 0x00000100;   // usb3.0 camera connected to usb2.0 port
        public static int FLAG_ST4 = 0x00000200;    // ST4
        public static int FLAG_GETTEMPERATURE = 0x00000400;    // support to get the temperature of sensor
        public static int FLAG_PUTTEMPERATURE = 0x00000800;    // support to put the temperature of sensor
        public static int FLAG_BITDEPTH10 = 0x00001000;    // Maximum Bit Depth = 10
        public static int FLAG_BITDEPTH12 = 0x00002000;    // Maximum Bit Depth = 12
        public static int FLAG_BITDEPTH14 = 0x00004000;    // Maximum Bit Depth = 14
        public static int FLAG_BITDEPTH16 = 0x00008000;    // Maximum Bit Depth = 16
        public static int FLAG_FAN = 0x00010000;    // cooling fan
        public static int FLAG_COOLERONOFF = 0x00020000;    // cooler can be turn on or off
        public static int FLAG_ISP = 0x00040000;    // image signal processing supported
        public static int FLAG_TRIGGER = 0x00080000;   //  support the trigger mode

        protected List getFieldOrder() {
            return Arrays.asList(new String[] { "FLAG_CMOS", "FLAG_CCD_PROGRESSIVE"});
        }
    }

}