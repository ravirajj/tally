// Copyright (c) 2020 Uber Technologies, Inc.
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

package com.uber.m3.tally;

import com.uber.m3.util.Duration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Noop implementation of Scope.
 */
public class NoopScope extends ScopeImpl {

    private NoopScope(ScheduledExecutorService scheduler, Registry registry, ScopeBuilder builder) {
        super(scheduler, registry, builder);
    }

    /**
     * Creates a new NoopScope.
     */
    public static NoopScope create() {
        return (NoopScope) new NoopScopeBuilder()
            .reporter(new NullStatsReporter())
            .reportEvery(Duration.ZERO);
    }

    private static class NoopScopeBuilder extends ScopeBuilder {

        public NoopScopeBuilder() {
            super(Executors.newSingleThreadScheduledExecutor(), new ScopeImpl.Registry());
        }

        @Override
        ScopeImpl build() {
            return new NoopScope(scheduler, registry, this);
        }
    }
}
