LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.all;

ENTITY Control_Circuit IS 
	PORT(  opCode: IN  STD_LOGIC_VECTOR(2 DOWNTO 0);
	       AInvert, BInvert, CarryIn: OUT STD_LOGIC;
			 Operation: OUT STD_LOGIC_VECTOR(1 DOWNTO 0));
	                     
END Control_Circuit;

ARCHITECTURE behaviorControl OF Control_Circuit IS 
	SIGNAL func: STD_LOGIC_VECTOR(4 DOWNTO 0);
	
BEGIN 
	WITH OpCode SELECT 
	func <= "00000"  WHEN  "000",
			  "01000"  WHEN  "001",
			  "10000"  WHEN  "010",
			  "10011"  WHEN  "011",
			  "00110"  WHEN  "100",
			  "01110"  WHEN  "101",
			  "11000"  WHEN  others; --Η περίπτωση OpCode = "111" αντιμετωπίζεται ως don't care 
			  
	--Λαμβάνουν τιμές ανάλογα με το func
		
	Operation <= func(4) & func(3); 
	AInvert   <= func(2);
	BInvert   <= func(1);
	CarryIn   <= func(0);
	
END behaviorControl;

--////////////////////////////////////////////////

LIBRARY work;
USE work.ALU_Slice.all;

LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.all;

ENTITY Sixteen_Bit_ALU IS 
	PORT (  opCode: IN  STD_LOGIC_VECTOR(2  DOWNTO 0);
			  A, B  : IN  STD_LOGIC_VECTOR(15 DOWNTO 0);
			  Result: OUT STD_LOGIC_VECTOR(15 DOWNTO 0);
			Overflow: OUT STD_LOGIC);
END Sixteen_Bit_ALU;

ARCHITECTURE behavior16ALU OF Sixteen_Bit_ALU IS
	COMPONENT Control_Circuit
		PORT(  opCode: IN  STD_LOGIC_VECTOR(2 DOWNTO 0);
				 AInvert, BInvert, CarryIn: OUT STD_LOGIC;
			    Operation: OUT STD_LOGIC_VECTOR(1 DOWNTO 0));
	                          
	END COMPONENT;
 
	SIGNAL AInvert, BInvert, CarryIn: STD_LOGIC;
   SIGNAL Operation: STD_LOGIC_VECTOR(1 DOWNTO 0);
	SIGNAL CarryOut : STD_LOGIC_VECTOR(15 DOWNTO 0);
	
BEGIN 
	--Καθορισμός εκτελεστέας πράξης
	Stage0:     Control_Circuit PORT MAP(opCode, AInvert, BInvert, CarryIn, Operation);
	
	--Εκτέλεση πράξης σε κάθε slice της 16-bit ALU
	Stage1:     One_bit_ALU PORT MAP(A(0), B(0), AInvert, BInvert, CarryIn, Operation, CarryOut(0), Result(0));
	Stage2to16: FOR i IN 1 TO 15 GENERATE
		           Calc: One_bit_ALU PORT MAP(A(i), B(i), AInvert, BInvert, CarryOut(i-1), Operation, CarryOut(i), Result(i));
					END GENERATE;
					
	--Προσδιορισμός του overflow: Αν η πράξη δεν ειναι ADD/SUB τότε το Overflow είναι 0
	Stage17:    Overflow <= (CarryOut(15) XOR CarryOut(14)) AND (NOT Operation(0) AND Operation(1));
	
END behavior16ALU;