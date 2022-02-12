

import java.math.BigInteger;

public class Solution {
	//private readonly BigInteger root1, root2;
    //private readonly bool exists;
	
	BigInteger root1;
	BigInteger root2;
	boolean exists;
	

    public Solution(BigInteger root1, BigInteger root2, boolean exists) {
        this.root1 = root1;
        this.root2 = root2;
        this.exists = exists;
    }

    public BigInteger Root1() {
        return root1;
    }

    public BigInteger Root2() {
        return root2;
    }

    public boolean Exists() {
        return exists;
    }
}
