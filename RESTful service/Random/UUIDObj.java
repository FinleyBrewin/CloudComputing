/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Azure.Random;

import java.util.Arrays;
import java.util.UUID;

/**
 *
 * @author xzims
 */
public class UUIDObj {
    private UUID[] UUID;
    
    public UUID[] getUUID()
    {
        return UUID;
    }
 
    public void setUUID(UUID[] UUID)
    {
        this.UUID = UUID;
    }
    @Override
    public String toString()
    {
        return Arrays.toString(UUID);
    }
}

