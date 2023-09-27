LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.all;

PACKAGE ALU_Slice IS 
	COMPONENT One_bit_ALU
		PORT (a, b, AInvert, BInvert, CarryIn: IN  std_logic;
										   Operation : IN  std_logic_vector(1 downto 0);
								   CarryOut, Result: OUT std_logic);
	END COMPONENT;
END PACKAGE ALU_Slice;	

--////////////////////////////////////////

LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.all;

ENTITY AND_2 IS
PORT (in1, in2: IN std_logic;
     outAND_2: OUT std_logic);
END AND_2;

ARCHITECTURE behaviorAND_2 OF AND_2 IS
BEGIN
    outAND_2 <= in1 AND in2;
END behaviorAND_2;

--////////////////////////////////////////

LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.all;

ENTITY OR_2 IS
PORT (in1,in2: IN std_logic;
     outOR_2: OUT std_logic);
END OR_2;

ARCHITECTURE behaviorOR_2 OF OR_2 IS
BEGIN
    outOR_2 <= in1 OR in2; 
END behaviorOR_2;

--///////////////////////////////////////

LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.all;

ENTITY XOR_2 IS
PORT (in1,in2: IN std_logic;
     outXOR_2: OUT std_logic);
END XOR_2;

ARCHITECTURE behaviorXOR_2 OF XOR_2 IS
BEGIN
    outXOR_2 <= in1 XOR in2;
END behaviorXOR_2; 

--///////////////////////////////////////
LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.all;

ENTITY FullAdder IS 
PORT (in1, in2, Carry_in: IN std_logic;
			 sum, Carry_out: OUT std_logic);
END FullAdder;

ARCHITECTURE behaviorFA OF FullAdder IS
BEGIN 
	sum <= (in1 AND NOT in2 AND NOT Carry_in) OR (NOT in1 AND in2 AND NOT Carry_in) OR (in1 AND in2 AND Carry_in) OR (NOT in1 AND NOT in2 AND Carry_in);
	Carry_out <= (in2 AND Carry_in) OR (in1 AND Carry_in) OR (in1 AND in2);
END behaviorFA;

--//////////////////////////////////////

LIBRARY ieee ;
USE ieee.std_logic_1164.all ;

ENTITY Mux2to1 IS
	PORT (in1, in2, s : IN std_logic ;
					    f : OUT std_logic ) ;
END Mux2to1;

ARCHITECTURE behaviorMux2 OF Mux2to1 IS
BEGIN
	WITH s SELECT
	f <= in1 WHEN '0', in2 WHEN OTHERS ;
END behaviorMux2 ;

--/////////////////////////////////////

LIBRARY ieee ;
USE ieee.std_logic_1164.all ;

ENTITY Mux4to1 IS
	PORT (in1, in2, in3, in4, s1, s2: IN std_logic;
									       f: OUT std_logic);
END Mux4to1;

ARCHITECTURE behaviorMux4 OF Mux4to1 IS
	COMPONENT Mux2to1
		PORT (in1, in2, s : IN std_logic ;
					       f : OUT std_logic );
	END COMPONENT;

	SIGNAL M1, M2: std_logic;
BEGIN
	A1: Mux2to1 PORT MAP(in1, in2, s1, M1);
	A2: Mux2to1 PORT MAP(in3, in4, s1, M2);
	A3: Mux2to1 PORT MAP(M1 ,  M2, s2, f );
END behaviorMux4;

--////////////////////////////////////
LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.ALL;

ENTITY One_bit_ALU IS 
	PORT (a, b, AInvert, BInvert, CarryIn: IN std_logic;
										Operation : IN std_logic_vector(1 downto 0);
								CarryOut, Result: OUT std_logic);

END One_bit_ALU;

ARCHITECTURE behaviorALU OF One_bit_ALU IS
	COMPONENT AND_2 
		PORT (in1, in2: IN std_logic;
            outAND_2: OUT std_logic);
	END COMPONENT;
	
	COMPONENT OR_2
		PORT (in1,in2: IN std_logic;
				outOR_2: OUT std_logic);
	END COMPONENT;
	
	COMPONENT XOR_2
		PORT (in1,in2: IN std_logic;
			  outXOR_2: OUT std_logic);
	END COMPONENT;
	
	COMPONENT FullAdder
		PORT (in1, in2, Carry_in: IN std_logic;
					 sum, Carry_out: OUT std_logic);
	END COMPONENT;
	
	COMPONENT Mux2to1
		PORT (in1, in2, s : IN std_logic ;
							 f : OUT std_logic ) ;
	END COMPONENT;
	
	COMPONENT Mux4to1
		PORT (in1, in2, in3, in4, s1, s2 : IN std_logic;
												  f: OUT std_logic);
	END COMPONENT;
	
	SIGNAL aNa, bNb, X1, X2, X3, X4, CarryOutInternal: std_logic;
	
BEGIN
	A1: Mux2to1 PORT MAP(a, NOT a, AInvert, aNa);
	A2: Mux2to1 PORT MAP(b, NOT b, BInvert, bNb);
	A3: AND_2 PORT MAP(aNa, bNb, X1);
	A4: OR_2  PORT MAP(aNa, bNb, X2);
	A5: FullAdder PORT MAP(aNa, bNb, CarryIn, X3, CarryOutInternal);
	A6: XOR_2 PORT MAP(aNa, bNb, X4);
	A7: Mux4to1 PORT MAP(X1, X2, X3, X4, Operation(0), Operation(1), Result);
	
	--Το CarryΟut παίρνει την τιμή του carryOutInternal αν γίνει πρόσθεση/αφαίρεση 
	--ενώ σε διαφορετική περίπτωση γίνεται 0
	CarryOut <= (CarryOutInternal AND (Operation(1) AND NOT Operation(0)));
END behaviorALU;		  