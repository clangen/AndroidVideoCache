package com.danikula.videocache;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

/**
 * Simple memory based {@link Cache} implementation.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
public class ByteArrayCache implements Cache {

    private volatile byte[] data;
    private volatile boolean completed;
    private volatile int writePos = 0;

    ByteArrayCache() {
        this(new byte[0]);
    }

    ByteArrayCache(byte[] data) {
        this.data = Preconditions.checkNotNull(data);
        this.writePos = this.data.length;
    }

    @Override
    public void position(long offset) throws ProxyCacheException {
        this.writePos = (int) offset;
    }

    @Override
    public int read(byte[] buffer, long offset, int length) throws ProxyCacheException {
        if (offset >= data.length) {
            return -1;
        }
        if (offset > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Too long offset for memory cache " + offset);
        }
        return new ByteArrayInputStream(data).read(buffer, (int) offset, length);
    }

    @Override
    public long length() throws ProxyCacheException {
        return this.writePos;
    }

    @Override
    public void append(byte[] newData, int length) throws ProxyCacheException {
        Preconditions.checkNotNull(data);
        Preconditions.checkArgument(length >= 0 && length <= newData.length);

        final int available = data.length - writePos;
        final int required = length - available;

        if (required > 0) {
            data = Arrays.copyOf(data, data.length + required);
        }

        System.arraycopy(newData, 0, data, this.writePos, length);

        this.writePos += length;
    }

    @Override
    public void close() throws ProxyCacheException {
    }

    @Override
    public void complete() {
        if (data.length > this.writePos) {
            data = Arrays.copyOf(data, this.writePos);
        }
        completed = true;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }
}
