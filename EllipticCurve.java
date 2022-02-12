import java.math.BigInteger;

public class EllipticCurve {
	static BigInteger Two = new BigInteger ("2");
	static BigInteger Three = new BigInteger ("3");
	static BigInteger Four = new BigInteger ("4");
	static BigInteger Six = new BigInteger ("6");
	static BigInteger Eight = new BigInteger ("8");
	static BigInteger Nine = new BigInteger ("9");
	static BigInteger Twelve = new BigInteger ("12");
	static BigInteger Sixteen = new BigInteger ("16");
	final static BigInteger ZERO = new BigInteger("0");
	final static BigInteger ONE = new BigInteger("1");
	
	public static   int no_Of_PointDBL_operation=0;
	public static   int no_Of_PoinADD_operation=0;
	public int get_no_Of_PointDBL_operation() {return no_Of_PointDBL_operation;}
	public int get_no_Of_PoinADD_operation() {return no_Of_PoinADD_operation;}

	private BigInteger a; // where a is the 3DIK curve coeffient
	private BigInteger P;// P is the prime number
	private BigInteger n;//n is the number of elements in the EC group or subgroup
	private BigInteger orderE; // orderE is the order of the whole elliptic curve group
	private BigInteger h;// h is the cofactor , the number of subgroup in the whole EC group
	
	
	public EllipticCurve (BigInteger a , BigInteger p,BigInteger n , BigInteger h ) { // construcure to initilize the domain paramters
		this.a=a; // where a is the 3DIK curve coeffient
		this.P=p;// P is the prime number
		this.n=n;// n is the number of elements in the EC group or subgroup
		this.h=h;// h is the cofactor , the number of subgroup in the whole EC group
		this.orderE=h.multiply(n); // orderE is the order of the whole elliptic curve group
	}

	public BigInteger getA() {return a;}
	public BigInteger getP() {return P;}
	public BigInteger getN() {return n;}
	public BigInteger getH() {return h;}
	public BigInteger getOrderE() {return orderE;}
	
	private boolean isInverse(Point G, Point T) { // this java method return true if one point is the ivers of the the other
		return (P.compareTo(T.getY().add(G.getY()))==0 && G.getX().compareTo(T.getX())==0);
// return 1 , if the x coordiates are equal AND the result of adding the y coordiates are equal to the prime number P
	}
	
	public boolean isOnCurve(Point T ) { // this method return true if the point T is on 3DIK Curve
		boolean isOnCurve =false;
		BigInteger L_Side = ((T.getY().pow(2))).mod(this.P); //substitute the y-coordinates in the left side of 3DIK EC equation 
		BigInteger R_Side = ((T.getX().pow(3)).add(Three.multiply(a).multiply((T.getX().add(ONE)).pow(2)))).mod(P);//substitute the y-coordinates in the right side of 3DIK EC equation 
	if (L_Side.compareTo(R_Side)==0) // check if both side of the EC equation are euals
		isOnCurve=true; //if true , update the boolean variable to true
			return isOnCurve; 
		}
	
	
	 Point PointDoubling(Point G) throws InterruptedException {// theis method perform the Affine point doubling operation by using the EC point doubling Equation
		 Point returnValue = new Point (BigInteger.ZERO , BigInteger.ZERO,BigInteger.ZERO);
			BigInteger X3 ,Y3,Z3;
			
			if (G.equals(Point.POINT_AT_INFINITY)) returnValue=G;
			else {
			
				//Addition Level 1
				Thread thread0 = new Thread (new Runnable () {
					public void run() {A1=G.getX().add(G.getZ());}});thread0.start();thread0.join();
				//Multiplication Level 1
				Thread thread1 = new Thread (new Runnable () {public void run() {M1=G.getX().multiply(G.getX());}});thread1.start();
				Thread thread2 = new Thread (new Runnable () {public void run() {M2=G.getY().multiply(G.getY());}});thread2.start();
				Thread thread3 = new Thread (new Runnable () {public void run() {M3=G.getZ().multiply(G.getZ());}});thread3.start();
				Thread thread4 = new Thread (new Runnable () {public void run() {M4=G.getX().multiply(G.getZ());}});thread4.start();
				thread1.join();thread2.join();thread3.join();thread4.join();
				//Multiplication Level 2
				Thread thread5 = new Thread (new Runnable () {public void run() {M5=G.getY().multiply(G.getZ());}});thread5.start();
				Thread thread6 = new Thread (new Runnable () {public void run() {M6=Six.multiply(a).multiply(G.getZ()).multiply(A1);}});thread6.start();
				Thread thread7 = new Thread (new Runnable () {public void run() {M7=M2.multiply(M4);}});thread7.start();
				Thread thread8 = new Thread (new Runnable () {public void run() {M8=M2.multiply(M2);}});thread8.start();
				thread5.join();thread6.join();thread7.join();thread8.join();
	            //Addition Level 2
				Thread thread9 = new Thread (new Runnable () {public void run() {A2=Three.multiply(M1).add(M6);}});
				thread9.start();thread9.join();
				//Multiplication Level 3
				Thread thread10 = new Thread (new Runnable () {public void run() {M9=A2.multiply(A2);}});thread10.start();
				Thread thread11 = new Thread (new Runnable () {public void run() {M10=M3.multiply(M8);}});thread11.start();
				Thread thread12 = new Thread (new Runnable () {public void run() {M11=M5.multiply(M5); }});thread12.start();
				thread10.join();thread11.join();thread12.join();
				//Addition Level 3
				Thread thread13 = new Thread (new Runnable () {public void run() {A3=M9.add(P.subtract(Eight.multiply(M7)));}});thread13.start();
				Thread thread14 = new Thread (new Runnable () {public void run() {A4=Twelve.multiply(M7).add(P.subtract(M9));}});thread14.start();
				thread13.join();thread14.join();
				//Multiplication Level 4
				Thread thread15 = new Thread (new Runnable () {public void run() {M12=Two.multiply(M5).multiply(A3);}});thread15.start();
				Thread thread16 = new Thread (new Runnable () {public void run() {M13=A2.multiply(A4);}});thread16.start();
				Thread thread17 = new Thread (new Runnable () {public void run() {M14=Eight.multiply(M11).multiply(M5);}});thread17.start();
				thread15.join();thread16.join();thread17.join();
				//Addition Level 4
				Thread thread18 = new Thread (new Runnable () {public void run() {A5=M13.add(P.subtract(Eight.multiply(M10)));}});
				thread18.start();thread18.join();
				X3=M12.mod(P);
				Y3=A5.mod(P); 
				Z3=M14.mod(P);
				/*
				 * BigInteger modInvOFZ=Z3.modInverse(P); X3=X3.multiply(modInvOFZ).mod(P);
				 * Y3=Y3.multiply(modInvOFZ).mod(P); Z3=Z3.multiply(modInvOFZ).mod(P);
				 */
				returnValue.setX(X3);
				returnValue.setY(Y3);
				returnValue.setZ(Z3);
			}
			
			return returnValue;
			
	}
	
	
	 private static BigInteger M1 = new BigInteger("0");
	 private static BigInteger M2 = new BigInteger("0");
		private static BigInteger M3 = new BigInteger("0");
		private static BigInteger M4 = new BigInteger("0");
		private static BigInteger M5 = new BigInteger("0");
		private static BigInteger M6 = new BigInteger("0");
		private static BigInteger M7 = new BigInteger("0");
		private static BigInteger M8 = new BigInteger("0");
		private static BigInteger M9 = new BigInteger("0");
		private static BigInteger M10 = new BigInteger("0");
		private static BigInteger M11 = new BigInteger("0");
		private static BigInteger M12 = new BigInteger("0");
		private static BigInteger M13 = new BigInteger("0");
		private static BigInteger M14 = new BigInteger("0");
		private static BigInteger M15 = new BigInteger("0");
		private static BigInteger M16 = new BigInteger("0");
		private static BigInteger A1 = new BigInteger("0");
		private static BigInteger A2 = new BigInteger("0");
		private static BigInteger A3 = new BigInteger("0");
		private static BigInteger A4 = new BigInteger("0");
		private static BigInteger A5 = new BigInteger("0");
		private static BigInteger A6 = new BigInteger("0");

	 Point PointAddition(Point G , Point Q) throws InterruptedException { // theis method perform the Affine point addition operation by using the EC point addition Equation
	Point returnValue = new Point (BigInteger.ZERO , BigInteger.ZERO,BigInteger.ZERO);
		BigInteger X3 ,Y3,Z3;
		if (G.equals(Q)) returnValue = PointDoubling(G);
		else if (G.equals(Point.POINT_AT_INFINITY)) returnValue=Q;
		else if  (Q.equals(Point.POINT_AT_INFINITY)) returnValue=G;
		else if (isInverse(G,Q)== true) returnValue=Point.POINT_AT_INFINITY;
		else {

		//Multiplication Level 1
				Thread thread1 = new Thread (new Runnable () {public void run() {M1=Q.getY().multiply(G.getZ());}});thread1.start();
				Thread thread2 = new Thread (new Runnable () {public void run() {M2=G.getY().multiply(Q.getZ());}});thread2.start();
				Thread thread3 = new Thread (new Runnable () {public void run() {M3=Q.getX().multiply(G.getZ());}});thread3.start();
				Thread thread4 = new Thread (new Runnable () {public void run() {M4=G.getX().multiply(Q.getZ());}});thread4.start();
				Thread thread5 = new Thread (new Runnable () {public void run() {M5=G.getZ().multiply(Q.getZ());}});thread5.start();
				thread1.join();thread2.join();thread3.join();thread4.join();thread5.join();
				//Addition Level 1
				Thread thread6 = new Thread (new Runnable () {public void run() {A1=M1.add(P.subtract(M2));}});thread6.start();
				Thread thread7 = new Thread (new Runnable () {public void run() {A2=M3.add(P.subtract(M4));}});thread7.start();
				Thread thread8 = new Thread (new Runnable () {public void run() {A3=M3.add(M4);}});thread8.start();
				thread6.join();thread7.join();thread8.join();
				//Multiplication Level 2
				Thread thread9 = new Thread (new Runnable () {public void run() {M6=A2.multiply(A2);}});thread9.start();
				Thread thread10 = new Thread (new Runnable () {public void run() {M7=A1.multiply(A1);}});thread10.start();
				Thread thread11 = new Thread (new Runnable () {public void run() {M8=M2.multiply(A2);}});thread11.start();
				Thread thread12 = new Thread (new Runnable () {public void run() {M9=A3.multiply(M6);}});thread12.start();
				thread9.join();thread10.join();thread11.join();thread12.join();
				//Multiplication Level 3
				Thread thread13 = new Thread (new Runnable () {public void run() {M10=M4.multiply(M6);}});thread13.start();
				Thread thread14 = new Thread (new Runnable () {public void run() {M11=M5.multiply(M7);}});thread14.start();
				Thread thread15 = new Thread (new Runnable () {public void run() {M12=M6.multiply(M5);}});thread15.start();
				//Thread thread16 = new Thread (new Runnable () {public void run() {M13=A2.multiply(M12);}});thread16.start();///////
				Thread thread17 = new Thread (new Runnable () {public void run() {M14=M8.multiply(M6);}});thread17.start();
				thread13.join();thread14.join();thread15.join();thread17.join();
				//Addition Level 2
				Thread thread18 = new Thread (new Runnable () {public void run() {A4=M11.add(P.subtract(M9));}});thread18.start();	
				Thread thread19 = new Thread (new Runnable () {public void run() {A5=M10.add(P.subtract(M11)).add(M9);}});thread19.start();
				thread18.join();
				thread19.join();
				//Multiplication Level 3
				Thread thread16 = new Thread (new Runnable () {public void run() {M13=A2.multiply(M12);}});thread16.start();///////
				Thread thread20 = new Thread (new Runnable () {public void run() {M15=A4.multiply(A2);}});thread20.start();
				Thread thread21 = new Thread (new Runnable () {public void run() {M16=A5.multiply(A1);}});thread21.start();
				thread16.join();thread20.join();thread21.join();
				//Addition Level 4
				Thread thread22 = new Thread (new Runnable () {public void run() {A6=M16.add(P.subtract(M14));}});thread22.start();	
				thread22.join();
		X3=M15.mod(P);
		Y3=A6.mod(P);
		Z3=M13.mod(P);
		/*
		 * BigInteger modInvOFZ=Z3.modInverse(P); X3=X3.multiply(modInvOFZ).mod(P);
		 * Y3=Y3.multiply(modInvOFZ).mod(P); Z3=Z3.multiply(modInvOFZ).mod(P);
		 */
				returnValue.setX(X3);
				returnValue.setY(Y3);
				returnValue.setZ(Z3);
			}
			
			
			return returnValue;
			
	}
	
	
		

	 
		public  Point scalarMultplication(BigInteger k , Point G) throws InterruptedException {
			
			// addition-subtraction method NAF represntation
			Point Q = new Point (new BigInteger ("0"), new BigInteger("0"),  new BigInteger("1"));
			BigInteger [] s =computeNAF(k);
			Q=G;
			for (int i=s.length-2;i>=0;i--) {
				Q=PointDoubling(Q);
				if (s[i].compareTo(BigInteger.ONE)==0)
					Q=PointAddition(Q,G);
				else if (s[i].compareTo( new BigInteger("-1"))==0)
					{Point inverseG= new Point (G.getX(),P.subtract(G.getY()),G.getZ());
					Q=PointAddition(Q,inverseG);
					}
			}
			return Q;
			
			
		}
		
		 public static BigInteger[] computeNAF (BigInteger k) {
				
				String ss=k.toString();
				int length = k.bitLength()+1;
				int l = 0;
				BigInteger [] s = new BigInteger[length];

			BigInteger c=k;
				while (c.compareTo(BigInteger.ZERO)==1) {
					if ((c.mod(Two)).compareTo(BigInteger.ZERO)!=0) //c%2!=0
					{
						s[l]=Two.subtract(c.mod(Four));
						c=c.subtract(s[l]);
						
					}
					else {s[l]=BigInteger.ZERO;
						
						
					}
					
					//c=c/2;
					c=c.divide(Two);
					l=l+1;
				}
				return s;
				
			}
	 
	 Point[] Encryption (BigInteger Private_Key ,  Point [] M , Point otherPartyPublicKey) throws InterruptedException { 
			Point Ciphers[] = new Point[M.length];
			for (int n = 0; n< M.length; n++) {
				Ciphers[n]=PointAddition(M[n], scalarMultplication(Private_Key,otherPartyPublicKey));
				no_Of_PoinADD_operation = no_Of_PoinADD_operation+1;

			}
			
			return Ciphers;
	 }
	 
	 
	 Point[] Decryption (Point []cipher,Point other_Party_Public_Key, BigInteger PrivateKey) throws Exception { 
			Point [] decryptedPoints= new Point [cipher.length];
			Point Shared_Secert=scalarMultplication(PrivateKey,other_Party_Public_Key);
			Point inversePoint= new Point(Shared_Secert.getX(),this.P.subtract(Shared_Secert.getY()),Shared_Secert.getZ());
			for (int n = 0; n< decryptedPoints.length; n++) {
			decryptedPoints[n]=(PointAddition(cipher[n],inversePoint));
			no_Of_PoinADD_operation = no_Of_PoinADD_operation+1;}
			for (int n = 0; n< decryptedPoints.length; n++) {
				BigInteger modInv = decryptedPoints[n].getZ().modInverse(P);
				decryptedPoints[n].setX(decryptedPoints[n].getX().multiply(modInv).mod(P));
				decryptedPoints[n].setY(decryptedPoints[n].getY().multiply(modInv).mod(P));
				decryptedPoints[n].setZ(decryptedPoints[n].getZ().multiply(modInv).mod(P));}
				
			return decryptedPoints;
	 }
	
	
}
