package Utils
import java.security.Key
import java.security.SecureRandom
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec

object AesUtils {
    private final val transformation = "AES/CBC/PKCS5Padding"
    fun generateAESKey(): Key {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256, SecureRandom())
        return keyGenerator.generateKey()
    }

    fun encryptAES(bytes: ByteArray, key: Key): ByteArray {
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, key)

        val iv = cipher.parameters.getParameterSpec(IvParameterSpec::class.java).iv

        return cipher.doFinal(bytes) + iv
    }

    @Throws(BadPaddingException::class)
    fun decryptAES(encryptedText: ByteArray, key: Key): ByteArray {
        val cipher = Cipher.getInstance(transformation)

        // Extract IV from the encrypted data
        val ivSize = cipher.blockSize
        val iv = encryptedText.copyOfRange(encryptedText.size - ivSize, encryptedText.size)

        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))

        val decryptedBytes = cipher.doFinal(encryptedText.copyOfRange(0, encryptedText.size - ivSize))
        return decryptedBytes
    }
}