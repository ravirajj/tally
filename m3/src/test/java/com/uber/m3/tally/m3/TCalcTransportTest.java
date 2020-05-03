// Copyright (c) 2017 Uber Technologies, Inc.
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

package com.uber.m3.tally.m3;

import com.uber.m3.tally.m3.thrift.TCalcTransport;
import org.apache.thrift.transport.TTransportException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TCalcTransportTest {
    private TCalcTransport transport;

    @Before
    public void setUp() {
        transport = new TCalcTransport();
    }

    @Test
    public void isOpen() {
        TCalcTransport transport = new TCalcTransport();

        assertTrue(transport.isOpen());
    }

    @Test
    public void open() {
        try {
            // Method is no-op
            transport.open();
        } catch (TTransportException e) {
            // Should not reach here
            fail();
        }
    }

    @Test
    public void close() {
        // Method is no-op
        transport.close();
    }

    @Test
    public void read() {
        try {
            assertEquals(0, transport.read(null, 0, 0));
        } catch (TTransportException e) {
            // Should not reach here
            fail();
        }
    }

    @Test
    public void writeGetSizeReset() {
        try {
            assertEquals(0, transport.getSize());

            transport.write(null, 0, 5);

            assertEquals(5, transport.getSize());

            transport.write(null, 0, 1);
            transport.write(null, 0, 2);
            transport.write(null, 0, 3);

            assertEquals(11, transport.getSize());

            transport.resetSize();

            assertEquals(0, transport.getSize());

            transport.getSizeAndReset();

            assertEquals(0, transport.getSize());

            transport.write(null, 0, 42);
            assertEquals(42, transport.getSizeAndReset());

            assertEquals(0, transport.getSize());
        } catch (TTransportException e) {
            // Should not reach here
            fail();
        }
    }
}
