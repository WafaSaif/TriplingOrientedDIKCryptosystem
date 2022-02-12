static char[] decoding (Point []  messagetoPoints) {
		//this method map back the char from a point on EC stored in the lookup table
		char []  mappedChar = new char[messagetoPoints.length];
		 int ascii =0;
		 for (int n = 0; n < messagetoPoints.length; n++) {
		for (int i = 0; i < LookupTable.length; i++) {
			 if (LookupTable[i].getX().equals(messagetoPoints[n].getX())&&LookupTable[i].getY().equals(messagetoPoints[n].getY())) {
				  ascii =i; 
			 }
			 mappedChar[n] = (char)ascii; // get the character corresponding to the ascii value
			}}
		return mappedChar;
	}