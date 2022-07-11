package com.johann.io.processingStream;

import java.io.File;

/**
 * @see java.io.RandomAccessFile#RandomAccessFile(File, String)
 *      * <table summary="Access mode permitted values and meanings">
 *      * <tr><th align="left">Value</th><th align="left">Meaning</th></tr>
 *      * <tr><td valign="top"><tt>"r"</tt></td>
 *      *     <td> Open for reading only.  Invoking any of the <tt>write</tt>
 *      *     methods of the resulting object will cause an {@link
 *      *     java.io.IOException} to be thrown. </td></tr>
 *      * <tr><td valign="top"><tt>"rw"</tt></td>
 *      *     <td> Open for reading and writing.  If the file does not already
 *      *     exist then an attempt will be made to create it. </td></tr>
 *      * <tr><td valign="top"><tt>"rws"</tt></td>
 *      *     <td> Open for reading and writing, as with <tt>"rw"</tt>, and also
 *      *     require that every update to the file's content or metadata be
 *      *     written synchronously to the underlying storage device.  </td></tr>
 *      * <tr><td valign="top"><tt>"rwd"&nbsp;&nbsp;</tt></td>
 *      *     <td> Open for reading and writing, as with <tt>"rw"</tt>, and also
 *      *     require that every update to the file's content be written
 *      *     synchronously to the underlying storage device. </td></tr>
 *      * </table>
 */
@SuppressWarnings("all")
public enum RandomAccessFileMode {
    r,
    rw,
    rws,
    rwd

//    R("r",1),
//    RW("rw",2),
//    RWS("rws",4),
//    RWD("rwd",8);
//
//
//    private final String key;
//    private final Integer value;
//
//    public String getKey() {
//        return key;
//    }
//
//    public Integer getValue() {
//        return value;
//    }
//
//    RandomAccessFileMode(String key, Integer value){
//        this.key = key;
//        this.value = value;
//    }
}
