/* 
 * Copyright 2017 Robert Hewitt.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.runouw.rcodec;

import com.runouw.rcodec.DecodeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * An implementation of ByteReader that reads from a byte array.
 * @author rhewitt - Originator
 * @author zmichaels - Maintainer, Documentation 
 */
final class ByteReaderByteArray implements ByteReader{
    private final ByteBuffer bb;
    
    /**
     * Constructs a new ByteReaderByteArray by using the supplied ByteBuffer.
     * @param buffer the ByteBuffer to read.
     */
    public ByteReaderByteArray(final ByteBuffer buffer) {
        this.bb = buffer.asReadOnlyBuffer();
    }
    
    /**
     * Constructs a new ByteReaderByteArray by wrapping the supplied byte array.
     * @param bytes the array of bytes to wrap.
     */
    public ByteReaderByteArray(final byte[] bytes) {
        this(ByteBuffer.wrap(bytes));
    }
    
    /**
     * Constructs a new ByteReaderByteArray by wrapping a segment of the supplied byte array.
     * @param bytes  the array of bytes to wrap
     * @param offset the offset of the segment.
     * @param length the length of the segment.
     */
    public ByteReaderByteArray(
            final byte[] bytes, 
            final int offset, 
            final int length) {        
        
        this(ByteBuffer.wrap(bytes, offset, length));
    }

    @Override
    public byte readByte() {
        return bb.get();
    }

    @Override
    public short readShort() {
        return bb.getShort();
    }

    @Override
    public int readInt() {
        return bb.getInt();
    }

    @Override
    public long readLong() {
        return bb.getLong();
    }

    @Override
    public float readFloat() {
        return bb.getFloat();
    }

    @Override
    public double readDouble() {
        return bb.getDouble();
    }

    @Override
    public String readString() throws DecodeException {
        try (ByteArrayOutputStream buildString = new ByteArrayOutputStream()) {
            while(true){
                char c = (char) bb.get();
                if (c == '\0') {
                    break;
                } else {
                    buildString.write(c);
                }
            }
            return new String(buildString.toByteArray(), "UTF8");
        }catch(IOException ex){
            throw new DecodeException("Could not decode UTF8 String!", ex);
        }
    }
    
    @Override
    public byte[] readBytes(int length) {
        byte[] bytes = new byte[length];
        
        bb.get(bytes, 0, length);
        
        return bytes;
    }
    
    
}
