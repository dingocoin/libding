/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.libdohj.params;

import org.bitcoinj.core.Block;
import org.bitcoinj.core.Sha256Hash;

import static com.google.common.base.Preconditions.checkState;

/**
 * Parameters for the main Dingocoin production network on which people trade
 * goods and services.
 */
public class DingocoinMainNetParams extends AbstractDingocoinParams {
    public static final int MAINNET_MAJORITY_WINDOW = 2000;
    public static final int MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED = 1900;
    public static final int MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE = 1500;
    protected static final int DIFFICULTY_CHANGE_TARGET = 145000;

    public DingocoinMainNetParams() {
        super(DIFFICULTY_CHANGE_TARGET);
        dumpedPrivateKeyHeader = 158; //This is always addressHeader + 128
        addressHeader = 30;
        p2shHeader = 22;
        port = 33117;
        packetMagic = 0xc1c1c1c1;
        segwitAddressHrp = "dingo";
        // Note that while BIP44 makes HD wallets chain-agnostic, for legacy
        // reasons we use a Dingo-specific header for main net. At some point
        // we'll add independent headers for BIP32 legacy and BIP44.
        bip32HeaderP2PKHpub = 0x02facafd; //The 4 byte header that serializes in base58 to "dgub".
        bip32HeaderP2PKHpriv =  0x02fac398; //The 4 byte header that serializes in base58 to "dgpv".
        genesisBlock.setDifficultyTarget(0x1e0ffff0L);
        genesisBlock.setTime(1386325540L);
        genesisBlock.setNonce(99943L);
        id = ID_DINGO_MAINNET;
        subsidyDecreaseBlockCount = 100000;
        spendableCoinbaseDepth = 100;

        // Note this is an SHA256 hash, not a Scrypt hash. Scrypt hashes are only
        // used in difficulty calculations.
        String genesisHash = genesisBlock.getHashAsString();
        checkState(genesisHash.equals("1a91e3dace36e2be3bf030a65679fe821aa1d6ef92e7c9902eb318182c355691"),
                genesisHash);

        majorityEnforceBlockUpgrade = MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE;
        majorityRejectBlockOutdated = MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED;
        majorityWindow = MAINNET_MAJORITY_WINDOW;

        // This contains (at a minimum) the blocks which are not BIP30 compliant. BIP30 changed how duplicate
        // transactions are handled. Duplicated transactions could occur in the case where a coinbase had the same
        // extraNonce and the same outputs but appeared at different heights, and greatly complicated re-org handling.
        // Having these here simplifies block connection logic considerably.
        checkpoints.put(     0, Sha256Hash.wrap("1a91e3dace36e2be3bf030a65679fe821aa1d6ef92e7c9902eb318182c355691"));
        checkpoints.put(581515, Sha256Hash.wrap("1a8e374e25b4c175669dae9beea1f5d2cc035ef5d4301defaf4a4ca7778fa83c"));

        dnsSeeds = new String[] {
                "seed.dingocoin.org",
                "seed2.dingocoin.org",
                "seed3.dingocoin.org",
                "seed.dingocoin.com",
                "seed2.dingocoin.com",
                "seed3.dingocoin.com"
        };
    }

    private static DingocoinMainNetParams instance;
    public static synchronized DingocoinMainNetParams get() {
        if (instance == null) {
            instance = new DingocoinMainNetParams();
        }
        return instance;
    }

    @Override
    public boolean allowMinDifficultyBlocks() {
        return false;
    }

    @Override
    public String getPaymentProtocolId() {
        // TODO: CHANGE THIS
        return ID_DINGO_MAINNET;
    }

    @Override
    public Block getGenesisBlock() {
        return genesisBlock;
    }

    @Override
    public boolean isTestNet() {
        return false;
    }
}
