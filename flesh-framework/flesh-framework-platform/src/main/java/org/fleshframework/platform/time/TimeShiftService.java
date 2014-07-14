/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fleshframework.platform.time;

/**
 *
 * @author jakubs
 */
public interface TimeShiftService extends TimeSource {
    public long getTimeShift();
}
