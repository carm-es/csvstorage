/*
 * Copyright (C) 2012-13 MINHAP, Gobierno de España This program is licensed and may be used,
 * modified and redistributed under the terms of the European Public License (EUPL), either version
 * 1.1 or (at your option) any later version as soon as they are approved by the European
 * Commission. Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * more details. You should have received a copy of the EUPL1.1 license along with this program; if
 * not, you may find it at http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 */

package es.gob.aapp.csvstorage.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;


public class ContentRangeInputStream extends FilterInputStream {

  private static final int BUFFER_SIZE = 4096;

  private long offset;
  private long length;
  private long remaining;

  public ContentRangeInputStream(InputStream stream, BigInteger offset, BigInteger length)
      throws IOException {
    super(stream);

    this.offset = offset != null ? offset.longValue() : 0;
    this.length = length != null ? length.longValue() : Long.MAX_VALUE;

    this.remaining = this.length;

    if (this.offset > 0) {
      skipBytes();
    }
  }

  private void skipBytes() throws IOException {
    long remainingSkipBytes = offset;

    while (remainingSkipBytes > 0) {
      long skipped = super.skip(remainingSkipBytes);
      remainingSkipBytes -= skipped;

      if (skipped == 0) {
        // stream might not support skipping
        skipBytesByReading(remainingSkipBytes);
        break;
      }
    }

  }

  private void skipBytesByReading(long remainingSkipBytes) throws IOException {

    final byte[] buffer = new byte[BUFFER_SIZE];
    while (remainingSkipBytes > 0) {
      long skipped = super.read(buffer, 0, (int) Math.min(buffer.length, remainingSkipBytes));
      if (skipped == -1) {
        break;
      }

      remainingSkipBytes -= skipped;
    }

  }

  @Override
  public boolean markSupported() {
    return false;
  }

  @Override
  public long skip(long n) throws IOException {
    if (remaining <= 0) {
      return 0;
    }

    long skipped = super.skip(n);
    remaining -= skipped;

    return skipped;
  }

  @Override
  public int available() throws IOException {
    if (remaining <= 0) {
      return 0;
    }

    int avail = super.available();

    if (remaining < avail) {
      return (int) remaining;
    }

    return avail;
  }

  @Override
  public int read() throws IOException {
    if (remaining <= 0) {
      return -1;
    }

    remaining--;

    return super.read();
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    if (remaining <= 0) {
      return -1;
    }

    int readBytes = super.read(b, off, (int) Math.min(len, remaining));
    if (readBytes == -1) {
      return -1;
    }

    remaining -= readBytes;

    return readBytes;
  }

  @Override
  public int read(byte[] b) throws IOException {
    return read(b, 0, b.length);
  }
}
