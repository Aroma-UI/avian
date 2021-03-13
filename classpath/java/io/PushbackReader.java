/* Copyright (c) 2008-2015, Avian Contributors

   Permission to use, copy, modify, and/or distribute this software
   for any purpose with or without fee is hereby granted, provided
   that the above copyright notice and this permission notice appear
   in all copies.

   There is NO WARRANTY for this software.  See license.txt for
   details. */

package java.io;

public class PushbackReader extends Reader {
  private final Reader in;
  private final char[] buffer;
  private int savedChars;

  public PushbackReader(Reader in, int bufferSize) {
    if (bufferSize <= 0) {
      throw new IllegalArgumentException("Buffer size could not be zero or negative: " + bufferSize);
    }
    buffer = new char[bufferSize];
    this.in = in;
    this.savedChars = 0;
  }

  public PushbackReader(Reader in) {
    this(in, 1);
  }

  public int read(char[] b, int offset, int length) throws IOException {
    int count = 0;
    if (savedChars > 0 && length > 0) {
      count = length > savedChars ? savedChars : length;
      System.arraycopy(buffer, savedChars - count, b, offset, count);
      savedChars -= count;
      offset += count;
      length -= count;
    }
    if (length > 0) {
      int c = in.read(b, offset, length);
      if (c == -1) {
        if (count == 0) {
          count = -1;
        }
      } else {
        count += c;
      }
    }

    return count;
  }

  public void unread(char[] b, int offset, int length) throws IOException {
    if (length > buffer.length - savedChars) {
      throw new IOException("Buffer is full");
    } else {
      System.arraycopy(b, offset, buffer, savedChars, length);
      savedChars += length;
    }
  }

  public void unread(char[] b) throws IOException {
    unread(b, 0, b.length);
  }

  public void unread(int c) throws IOException {
    unread(new char[] { (char) c });
  }

  public void close() throws IOException {
    in.close();
  }
}
