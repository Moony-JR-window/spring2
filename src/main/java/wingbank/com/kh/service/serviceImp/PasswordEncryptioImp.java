package wingbank.com.kh.service.serviceImp;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import wingbank.com.kh.service.PasswordEncryption;

@Service
public class PasswordEncryptioImp implements PasswordEncryption {
    @Override
    public String encryptPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    @Override
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }

}
