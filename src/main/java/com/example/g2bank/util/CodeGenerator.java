package com.example.g2bank.util;

import com.mifmif.common.regex.Generex;




public class CodeGenerator {
    public static final String ACCOUNT_NUMBER_PATTERN_STRING = "[0-9]{8}";
    Generex accountNumberGenerex = new Generex(ACCOUNT_NUMBER_PATTERN_STRING);

    public CodeGenerator() {

    }

    public String generateAccountNumber() {
        return accountNumberGenerex.random();
    }
}
