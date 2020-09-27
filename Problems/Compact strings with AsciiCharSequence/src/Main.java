import java.util.*;

class AsciiCharSequence implements CharSequence {

    private final byte[] data;

    public AsciiCharSequence(byte[] data) {
        this.data = data;
    }

    @Override
    public int length() {
        return data.length;
    }

    @Override
    public char charAt(int index) {
        return (char) data[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        byte[] slice = new byte[end - start];
        for (int i = start, j = 0; i < end; i++) {
            slice[j++] = data[i];
        }
        return new AsciiCharSequence(slice);
    }

    @Override
    public String toString() {
        return new String(data);
    }
}