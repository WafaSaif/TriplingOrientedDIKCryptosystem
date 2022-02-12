static Point[] endocding (char[] charArray) {
		Point [] messagetoPoints= new Point[charArray.length];
		// this method used to map a character into a point on the elliptic curve stored in the lookup table
		
		for (int n = 0; n< charArray.length; n++) {
			int ascii =(char)charArray[n];  // get the ascii value of a character 
			messagetoPoints[n]=LookupTable[ascii];
			
		}
		return messagetoPoints;
	}