package wingbank.com.kh.service;

public interface PasswordEncryption {
    public String encryptPassword(String password);
    boolean verifyPassword (String rawPassword, String hashedPassword);
}
