package com.example.srmc.utils.validator

object AuthValidator {

    fun isValidName(name : String) : Boolean = name.trim().length in (1..255)

    fun isValidAddress(address: String) : Boolean = address.trim().length >= 8

    /** [email] */
    val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    fun isValidEmail(email : String) : Boolean { return EMAIL_REGEX.toRegex().matches(email) }

    /**  [contact_number] */
    fun isValidNumber(contact_number : String) : Boolean = contact_number.trim().length in (11..12)

    /** [password] */
    fun isValidPassword(password: String): Boolean = password.trim().length in (8..50)

    fun isPasswordAndConfirmPasswordSame(
            password: String,
            confirmedPassword: String): Boolean = password.trim() == confirmedPassword.trim()

}