import java.io.*;

public class CryptoFileHandler {
    private static class EncryptFilterWriter extends FilterWriter {
        private final int key;

        protected EncryptFilterWriter(Writer out, char keyChar) {
            super(out);
            this.key = keyChar;
        }

        @Override
        public void write(int c) throws IOException {
            super.write(c + key);
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            for (int i = off; i < off + len; i++) {
                write(cbuf[i]);
            }
        }

        @Override
        public void write(String str, int off, int len) throws IOException {
            for (int i = off; i < off + len; i++) {
                write(str.charAt(i));
            }
        }
    }

    private static class DecryptFilterReader extends FilterReader {
        private final int key;

        protected DecryptFilterReader(Reader in, char keyChar) {
            super(in);
            this.key = keyChar;
        }

        @Override
        public int read() throws IOException {
            int c = super.read();
            if (c == -1) return -1;
            return c - key;
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            int numChars = super.read(cbuf, off, len);
            if  (numChars == -1) return -1;
            for (int i = off; i < off + numChars; i++) {
                cbuf[i] = (char) (cbuf[i] - key);
            }
            return numChars;
        }
    }

    public static void encryptFile(String sourceFile, String destFile, char key) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        EncryptFilterWriter writer = new EncryptFilterWriter(new FileWriter(destFile), key)) {
            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c);
            }
            System.out.println("Encryption successful.");
        } catch (IOException e) {
            System.out.println("Encryption error: " + e.getMessage());
        }
    }

    public static void decryptFile(String sourceFile, String destFile, char key) {
        try (DecryptFilterReader reader = new DecryptFilterReader(new FileReader(sourceFile), key);
             BufferedWriter writer = new BufferedWriter(new FileWriter(destFile))) {
            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c);
            }
            System.out.println("Decryption successful.");
        } catch (IOException e) {
            System.out.println("Decryption error: " + e.getMessage());
        }
    }
}
