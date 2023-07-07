/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Azure.Random;

public class Result {
    public Random random;
    public long bitsUsed;
    public long bitsLeft;
    public long requestsLeft;
    public long advisoryDelay;

    public Random getRandom() { return random; }
    public void setRandom(Random value) { this.random = value; }

    public long getBitsUsed() { return bitsUsed; }
    public void setBitsUsed(long value) { this.bitsUsed = value; }

    public long getBitsLeft() { return bitsLeft; }
    public void setBitsLeft(long value) { this.bitsLeft = value; }

    public long getRequestsLeft() { return requestsLeft; }
    public void setRequestsLeft(long value) { this.requestsLeft = value; }

    public long getAdvisoryDelay() { return advisoryDelay; }
    public void setAdvisoryDelay(long value) { this.advisoryDelay = value; }
}