package com.example.clockwise

import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

// Class to salt and hash the password.
class SaltHashPassword() {

    // Method to generate the salt.
    fun generateSalt(): ByteArray {
        val salt = ByteArray(16)
        val random = SecureRandom()
        random.nextBytes(salt)
        return salt
    }

    // Method to hash the password
    fun hashPassword(password: String, salt: ByteArray): String {
        val md = MessageDigest.getInstance("SHA-256")
        md.update(salt)
        val hashedPassword = md.digest(password.toByteArray())
        return Base64.getEncoder().encodeToString(hashedPassword)
    }

    // Checking if the password is correct.
    fun verifyPassword(enteredPassword: String, hashedPassword: String, salt: ByteArray): Boolean {
        val hashedEnteredPassword = hashPassword(enteredPassword, salt)
        return hashedEnteredPassword == hashedPassword
    }

}