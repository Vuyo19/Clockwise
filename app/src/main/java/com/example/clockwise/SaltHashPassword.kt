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
    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

    // Checking if the password is correct.
    fun verifyPassword(enteredPassword: String, hashedPassword: String): Boolean {

        val hashedEnteredPassword = hashPassword(enteredPassword)

        return hashedEnteredPassword == hashedPassword
    }

}