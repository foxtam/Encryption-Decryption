package encryptdecrypt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

enum CryptographerType {
    SHIFT,
    UNICODE
}

enum Mode {
    ENC,
    DEC
}

interface Cryptographer {
    String encryptText(String text, int key);

    String decryptText(String text, int key);
}

public class Main {
    public static void main(String[] args) {
        try {
            Dictionary arguments = Dictionary.fromStringArray(args);
            EncryptionArgsParser parser = new EncryptionArgsParser(arguments);

            Optional<Path> outFile = parser.getFileToOut();

            String processedText = getProcessedText(parser);

            if (outFile.isPresent()) {
                Files.writeString(outFile.get(), processedText);
            } else {
                System.out.println(processedText);
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    private static String getProcessedText(EncryptionArgsParser parser) throws IOException {
        String data = parser.getData();
        Mode mode = parser.getMode();
        int key = parser.getKey();
        Cryptographer cryptographer = parser.getCryptographer();

        String processedText;
        switch (mode) {
            case ENC:
                processedText = cryptographer.encryptText(data, key);
                break;
            case DEC:
                processedText = cryptographer.decryptText(data, key);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mode);
        }
        return processedText;
    }
}

class Dictionary {

    final private String[] args;

    private Dictionary(String[] args) {
        this.args = Arrays.copyOf(args, args.length);
    }

    public static Dictionary fromStringArray(String[] args) {
        return new Dictionary(args);
    }

    public Optional<String> get(String key) {
        for (int i = 0; i < args.length; i++) {
            if (key.equals(args[i])) return Optional.of(args[i + 1]);
        }
        return Optional.empty();
    }
}

class EncryptionArgsParser {

    private static final CryptographerType defaultCryptoType = CryptographerType.SHIFT;
    private final Dictionary arguments;

    public EncryptionArgsParser(Dictionary dictionary) {
        this.arguments = dictionary;
    }

    public String getData() throws IOException {
        Optional<String> dataFromArgs = arguments.get("-data");
        if (dataFromArgs.isPresent()) return dataFromArgs.get();
        Optional<String> fileName = arguments.get("-in");
        if (fileName.isEmpty()) return "";
        return Files.readString(Path.of(fileName.get()));
    }

    public int getKey() {
        return Integer.parseInt(arguments.get("-key").orElse("0"));
    }

    public Mode getMode() {
        return Mode.valueOf(arguments.get("-mode").orElse(Mode.ENC.name()).toUpperCase());
    }

    public Optional<Path> getFileToOut() {
        return arguments.get("-out").map(Path::of);
    }

    public Cryptographer getCryptographer() {
        CryptographerType type = getCryptographerType();
        switch (type) {
            case UNICODE:
                return new UnicodeCryptographer();
            case SHIFT:
                return new ShiftCryptographer();
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private CryptographerType getCryptographerType() {
        String name = arguments.get("-alg").orElse(defaultCryptoType.name().toLowerCase());
        return CryptographerType.valueOf(name.toUpperCase());
    }
}

abstract class AbstractShiftCryptographer implements Cryptographer {
    @Override
    public String encryptText(String text, int key) {
        StringBuilder encrypted = new StringBuilder();
        for (char symbol : text.toCharArray()) {
            encrypted.append(encryptChar(symbol, key));
        }
        return encrypted.toString();
    }

    @Override
    public String decryptText(String text, int key) {
        return encryptText(text, -key);
    }

    abstract protected char encryptChar(char symbol, int shift);
}

class ShiftCryptographer extends AbstractShiftCryptographer {

    private static final String lowerAscii = Alphabet.alphabet('a', 'z');
    private static final String upperAscii = Alphabet.alphabet('A', 'Z');

    protected char encryptChar(char symbol, int shift) {
        int index = lowerAscii.indexOf(symbol);
        String alphabet = lowerAscii;

        if (index == -1) {
            index = upperAscii.indexOf(symbol);
            alphabet = upperAscii;
        }
        if (index == -1) return symbol;

        index += shift;
        index = Math.floorMod(index, alphabet.length());

        return alphabet.charAt(index);
    }
}

class UnicodeCryptographer extends AbstractShiftCryptographer {

    protected char encryptChar(char symbol, int shift) {
        return (char) (symbol + shift);
    }
}

class Alphabet {
    public static String alphabet(char from, char to) {
        StringBuilder builder = new StringBuilder();
        for (char i = from; i <= to; i++) {
            builder.append(i);
        }
        return builder.toString();
    }
}