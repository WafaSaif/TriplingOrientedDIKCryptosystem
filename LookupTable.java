LookupTable = new Point[128]; // set the size of the array to be 128 
		BigInteger Counter = new BigInteger ("0");	
		Point G=new Point (BigInteger.ZERO,BigInteger.ZERO,BigInteger.ONE);
		BigInteger i = new BigInteger ("0");
		int j = 0;
		while ((i.compareTo(P))==0 || (i.compareTo(P))==-1) 
		{			
			BigInteger R_Side = ((i.pow(3)).add(Three.multiply(a).multiply((i.add(ONE)).pow(2)))).mod(P);
			 // find the sloution of the right
																									// side of the TDIK Curve
																									// Eqaution
		    	 Solution sol = ts(R_Side, P);
			        if (sol.exists) {
			            G= new Point (i,sol.root1,BigInteger.ONE);
			            LookupTable[j]=new Point (G.getX(),G.getY(),G.getZ());
			            j++;
			            G= new Point (i,sol.root2,BigInteger.ONE);
			            LookupTable[j]=new Point (G.getX(),G.getY(),G.getZ());
			            j++;
			          Counter=Counter.add(TWO);
			        } 
		    
		    if (Counter.compareTo(BigInteger.valueOf(128))==0) break;
		    	 i=i.add(BigInteger.ONE); } 

 private static Solution ts(BigInteger n, BigInteger p) {
	      BiFunction<BigInteger, BigInteger, BigInteger> powModP = (BigInteger a, BigInteger e) -> a.modPow(e, p);
	      Function<BigInteger, BigInteger> ls = (BigInteger a) -> powModP.apply(a, p.subtract(ONE).divide(TWO));

	      if (!ls.apply(n).equals(ONE)) return new Solution(ZERO, ZERO, false);

	      BigInteger q = p.subtract(ONE);
	      BigInteger ss = ZERO;
	      while (q.and(ONE).equals(ZERO)) {
	          ss = ss.add(ONE);
	          q = q.shiftRight(1);
	      }

	      if (ss.equals(ONE)) {
	          BigInteger r1 = powModP.apply(n, p.add(ONE).divide(FOUR));
	          return new Solution(r1, p.subtract(r1), true);
	      }

	      BigInteger z = TWO;
	      while (!ls.apply(z).equals(p.subtract(ONE))) z = z.add(ONE);
	      BigInteger c = powModP.apply(z, q);
	      BigInteger r = powModP.apply(n, q.add(ONE).divide(TWO));
	      BigInteger t = powModP.apply(n, q);
	      BigInteger m = ss;

	      while (true) {
	          if (t.equals(ONE)) return new Solution(r, p.subtract(r), true);
	          BigInteger i = ZERO;
	          BigInteger zz = t;
	          while (!zz.equals(BigInteger.ONE) && i.compareTo(m.subtract(ONE)) < 0) {
	              zz = zz.multiply(zz).mod(p);
	              i = i.add(ONE);
	          }
	          BigInteger b = c;
	          BigInteger e = m.subtract(i).subtract(ONE);
	          while (e.compareTo(ZERO) > 0) {
	              b = b.multiply(b).mod(p);
	              e = e.subtract(ONE);
	          }
	          r = r.multiply(b).mod(p);
	          c = b.multiply(b).mod(p);
	          t = t.multiply(c).mod(p);
	          m = i;
	      }
	  }

