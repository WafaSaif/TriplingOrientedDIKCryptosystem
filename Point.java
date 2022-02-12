import java.math.BigInteger;

public class Point {
private BigInteger X;
private BigInteger Y;
private BigInteger Z;

public Point( BigInteger X,  BigInteger Y,BigInteger Z ) { // Point construcure to initilize the x , y and z-coordiates 
	      this.X=X;
	      this.Y=Y;
	      this.Z=Z;
	    }

public Point( BigInteger X,  BigInteger Y ) { //// Point construcure to initilize the x and sy-coordiates 
    this.X=X;
    this.Y=Y;
   
  }



public BigInteger getX() {
	       return X;
	    }

public BigInteger getY() {
    return Y;
 }

public BigInteger getZ() {
    return Z;
 }

public boolean isInfinity() {
      return X == null && Y == null;
   }


public void setX(BigInteger X) {
    this.X= X;
 }

public void setY(BigInteger Y) {
    this.Y= Y;
 }
public void setZ(BigInteger Z) {
    this.Z= Z;
 }
static Point POINT_AT_INFINITY = new Point(BigInteger.ZERO, new BigInteger("1") ,BigInteger.ZERO);

public void PrintPoint() {
	System.out.println("("+this.getX()+","+this.getY()+","+this.getZ()+")");
	
}


}
