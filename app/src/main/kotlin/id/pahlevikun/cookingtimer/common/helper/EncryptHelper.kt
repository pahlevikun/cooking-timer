package id.pahlevikun.cookingtimer.common.helper

import android.util.Base64
import id.pahlevikun.cookingtimer.BuildConfig
import java.security.NoSuchAlgorithmException
import java.security.Security
import java.security.spec.InvalidKeySpecException
import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import kotlin.text.Charsets.UTF_8

object EncryptHelper {
    private const val ALGORITHM = "AES/ECB/PKCS5Padding"

    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    private fun generateKey(): SecretKey {
        return SecretKeySpec(BuildConfig.ENCRYPT_KEY.toByteArray(), "AES")
    }

    fun encrypt(data: String, isEncrypt: Boolean = true): String {
        return when {
            data.isNotBlank() && isEncrypt -> {
                try {
                    Security.addProvider(org.bouncycastle.jce.provider.BouncyCastleProvider())
                    val secretKey = generateKey()

                    val cipher = Cipher.getInstance(ALGORITHM)
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey)
                    val cipherText = cipher.doFinal(data.toByteArray(UTF_8))
                    Logger.debug(
                        "RAW ENCRYPT RESULT ${
                            String(
                                Base64.encode(
                                    cipherText,
                                    Base64.DEFAULT
                                )
                            )
                        }"
                    )
                    String(Base64.encode(cipherText, Base64.DEFAULT))
                } catch (e: InvalidKeySpecException) {
                    Logger.record("ENCRYPTING >> InvalidKeySpecException", e)
                    data
                } catch (e: NoSuchAlgorithmException) {
                    Logger.record("ENCRYPTING >> NoSuchAlgorithmException", e)
                    data
                } catch (e: NoSuchPaddingException) {
                    Logger.record("ENCRYPTING >> NoSuchPaddingException", e)
                    data
                } catch (e: Exception) {
                    Logger.record("ENCRYPTING >> EXCEPTION", e)
                    data
                }
            }
            else -> data
        }
    }

    fun decrypt(data: String, isEncrypt: Boolean): String {
        return when {
            data.isNotBlank() && isEncrypt -> {
                try {
                    Security.addProvider(org.bouncycastle.jce.provider.BouncyCastleProvider())
                    val secretKey = generateKey()

                    val cipher = Cipher.getInstance(ALGORITHM)
                    cipher.init(Cipher.DECRYPT_MODE, secretKey)
                    val cipherText = Base64.decode(data.toByteArray(UTF_8), Base64.DEFAULT)
                    Logger.debug("RAW DECRYPT RESULT ${String(cipher.doFinal(cipherText), UTF_8)}")
                    String(cipher.doFinal(cipherText), UTF_8)
                } catch (e: InvalidKeySpecException) {
                    Logger.record("ENCRYPTING >> InvalidKeySpecException", e)
                    data
                } catch (e: NoSuchAlgorithmException) {
                    Logger.record("ENCRYPTING >> NoSuchAlgorithmException", e)
                    data
                } catch (e: NoSuchPaddingException) {
                    Logger.record("ENCRYPTING >> NoSuchPaddingException", e)
                    data
                } catch (e: Exception) {
                    Logger.record("ENCRYPTING >> EXCEPTION", e)
                    data
                }
            }
            else -> data
        }
    }

}