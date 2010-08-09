/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections;

import java.util.NoSuchElementException;

/**
 * The BufferUnderflowException is used when the buffer is already empty.
 * <p>
 * NOTE: From version 3.0, this exception extends NoSuchElementException.
 * 
 * @since Commons Collections 2.1
 * @version $Revision: 555824 $ $Date: 2007-07-13 01:27:15 +0100 (Fri, 13 Jul 2007) $
 *
 * @author Avalon
 * @author Berin Loritsch
 * @author Jeff Turner
 * @author Paul Jack
 * @author Stephen Colebourne
 */
public class BufferUnderflowException extends NoSuchElementException {
    
    /** The root cause throwable */
    private final Throwable throwable;

    /**
     * Constructs a new <code>BufferUnderflowException</code>.
     */
    public BufferUnderflowException() {
        super();
        throwable = null;
    }

    /** 
     * Construct a new <code>BufferUnderflowException</code>.
     * 
     * @param message  the detail message for this exception
     */
    public BufferUnderflowException(String message) {
        this(message, null);
    }

    /** 
     * Construct a new <code>BufferUnderflowException</code>.
     * 
     * @param message  the detail message for this exception
     * @param exception  the root cause of the exception
     */
    public BufferUnderflowException(String message, Throwable exception) {
        super(message);
        throwable = exception;
    }

    /**
     * Gets the root cause of the exception.
     *
     * @return the root cause
     */
    public final Throwable getCause() {
        return throwable;
    }
    
}
