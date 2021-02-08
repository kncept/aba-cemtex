package com.kncept.abacemtex.file.field.value.bankmneumonic;

import com.kncept.abacemtex.file.HeaderRecord;

import java.util.HashMap;
import java.util.Map;

// UNVERIFIED, PARTIAL list of Australian banks
public interface BankMnemonic {
    String getMneumonic();
    String getName();
    void onMneumonicSet(HeaderRecord headerRecord);

    Map<String, BankMnemonic> nonEnumCache = new HashMap<>();

    public static BankMnemonic lookup(String id) {
        if (id == null) return null;
        for (BankMnemonics knownBank : BankMnemonics.values()) {
            if (knownBank.getMneumonic().equals(id))
                return knownBank;
        }
        if (nonEnumCache.containsKey(id)) return nonEnumCache.get(id);
        BankMnemonic mnemonic = new UnknownBankMneumonic(id);
        nonEnumCache.put(id, mnemonic);
        return mnemonic;
    }

    class UnknownBankMneumonic implements BankMnemonic {
        private final String id;
        public UnknownBankMneumonic(String id) {
            this.id = id;
        }

        @Override
        public void onMneumonicSet(HeaderRecord headerRecord) {
        }

        @Override
        public String getMneumonic() {
            return id;
        }

        @Override
        public String getName() {
            return id;
        }
    }
}
