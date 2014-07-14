/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fleshframework.platform.time.impl;

import java.util.concurrent.atomic.AtomicLong;
import org.fleshframework.platform.time.TimeShiftService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jakubs
 */
public class FleshTimeShiftService implements TimeShiftService {
    private static final Logger logger = LoggerFactory.getLogger(FleshTimeShiftService.class);
    private AtomicLong timeShift = new AtomicLong(0);
    
    public void receiveMessage(String message) {
        long newTimeShift = Long.valueOf(message);
        timeShift.set(newTimeShift);
        logger.warn("Time-shift changed to {} millis.", newTimeShift);
    }

    public long getTimeShift() {
        return timeShift.get();
    }

    public long getCurrentMillis() {
        return System.currentTimeMillis() + timeShift.get();
    }
}
