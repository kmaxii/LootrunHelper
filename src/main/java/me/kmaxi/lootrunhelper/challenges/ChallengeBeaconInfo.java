package me.kmaxi.lootrunhelper.challenges;

import me.kmaxi.lootrunhelper.beacon.Beacon;
import me.kmaxi.lootrunhelper.beacon.BeaconType;

public class ChallengeBeaconInfo {

    public Beacon beacon;
    public Challenge challenge;

    public Challenge getChallenge(){
        return challenge;
    }

    public ChallengeBeaconInfo(Beacon beacon, Challenge challenge) {
        this.beacon = beacon;
        this.challenge = challenge;
    }
}
