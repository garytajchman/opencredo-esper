/*
 * OpenCredo-Esper - simplifies adopting Esper in Java applications. 
 * Copyright (C) 2010  OpenCredo Ltd.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.opencredo.esper.integration.config.xml;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opencredo.esper.integration.throughput.EsperChannelThroughputMonitor;
import org.opencredo.esper.sample.SampleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.core.PollableChannel;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ChannelThroughputMonitorTest {

    @Autowired
    @Qualifier("throughputMonitor")
    EsperChannelThroughputMonitor throughputMonitor;

    @Autowired
    @Qualifier("throughputMonitorTwo")
    EsperChannelThroughputMonitor throughputMonitorTwo;

    @Autowired
    @Qualifier("messageChannel")
    PollableChannel pollableChannel;

    @Autowired
    @Qualifier("messageChannelTwo")
    MessageChannel channelTwo;

    @Test
    @DirtiesContext
    public void testMessageThroughputPerSampleWindow() throws Exception {
        pollableChannel.receive(10);

        int count = 10;
        for (int i = 0; i < count; i++) {
            pollableChannel.send(new GenericMessage<SampleEvent>(new SampleEvent()));
        }

        for (int i = 0; i < count - 1; i++) {
            pollableChannel.receive(10);
        }

        // Need to sleep longer than default time_batch of throughput monitor
        // (which is 1 second).
        Thread.sleep(1100);

        long throughput = throughputMonitor.getThroughput();

        System.out.println("Throughput is: " + throughputMonitor.getThroughput());

        assertEquals("Throughput calculated incorrectly ", count - 1, throughput);
    }

    @Test
    @DirtiesContext
    public void testMessageThroughputOnMutlipleChannels() throws Exception {
        for (int i = 0; i < 10; i++) {
            pollableChannel.send(new GenericMessage<SampleEvent>(new SampleEvent()));
            pollableChannel.receive(10);
        }

        // Need to sleep longer than default time_batch of throughput monitor
        // (which is 1 second).
        Thread.sleep(1100);

        long throughput = throughputMonitor.getThroughput();

        System.out.println("Throughput is: " + throughputMonitor.getThroughput());

        assertEquals("Throughput one calculated incorrectly ", 10, throughput);

        for (int i = 0; i < 20; i++) {
            channelTwo.send(new GenericMessage<SampleEvent>(new SampleEvent()));
        }

        // Need to sleep longer than default time_batch of throughput monitor
        // (which is 1 second).
        Thread.sleep(1100);

        long throughputTwo = throughputMonitorTwo.getThroughput();

        System.out.println("Throughput two is: " + throughputMonitorTwo.getThroughput());

        assertEquals("Throughput two calculated incorrectly ", 20, throughputTwo);
    }

}
