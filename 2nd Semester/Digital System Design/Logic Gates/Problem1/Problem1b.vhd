LIBRARY IEEE;
USE IEEE.std_logic_1164.all;

ENTITY Problem1b IS 
PORT (x1,x2,x3,x4,x5: IN std_logic;
						f : OUT std_logic);
END Problem1b;

--Επειδή οι μεταβλητές είναι διαθέσιμες στην
--κανονική μορφή και την αντίστοιχη μορφή συμπληρώματος
--δεν υλοποιούμε πύλη NOT

LIBRARY IEEE;
USE IEEE.std_logic_1164.all;

ENTITY AND_2 IS
PORT (in1,in2: IN std_logic;
		outAND_2: OUT std_logic);
END AND_2;

ARCHITECTURE behaviorAND_2 OF AND_2 IS
BEGIN
	outAND_2 <= in1 AND in2;
END behaviorAND_2;

--////////////////////////////////////////////////////

LIBRARY IEEE;
USE IEEE.std_logic_1164.all;

ENTITY AND_3 IS
PORT (in1,in2,in3: IN std_logic;
		   outAND_3: OUT std_logic);
END AND_3;


ARCHITECTURE behaviorAND_3 OF AND_3 IS
BEGIN
	outAND_3 <= in1 AND in2 AND in3;
END behaviorAND_3;

--///////////////////////////////////////////////////

LIBRARY IEEE;
USE IEEE.std_logic_1164.all;

ENTITY OR_5 IS
PORT (in1,in2,in3,in4,in5: IN std_logic;
						outOR_5: OUT std_logic);
END OR_5;

ARCHITECTURE behaviorOR_5 OF OR_5 IS
BEGIN 
	outOR_5 <= in1 OR in2 OR in3 OR in4 OR in5;
END behaviorOR_5;

--///////////////////////////////////////////////////

ARCHITECTURE Structural OF Problem1b IS
COMPONENT AND_2
	PORT (in1,in2: IN std_logic;
			outAND_2: OUT std_logic);
END COMPONENT;

COMPONENT AND_3
	PORT (in1,in2,in3: IN std_logic;
				outAND_3: OUT std_logic);
END COMPONENT;

COMPONENT OR_5
	PORT (in1,in2,in3,in4,in5: IN std_logic;
							outOR_5: OUT std_logic);
END COMPONENT;

SIGNAL K1,K2,K3,K4,K5 : std_logic;

BEGIN
	L1: AND_2 PORT MAP(NOT x3, NOT x5, K1);
	L2: AND_2 PORT MAP(NOT x4, NOT x5, K2);
	L3: AND_3 PORT MAP(NOT x1, x2, NOT x5, K3);
	L4: AND_3 PORT MAP(NOT x1, NOT x2, NOT x4, K4);
	L5: AND_3 PORT MAP(x2, x4, x5, K5);
	L6: OR_5  PORT MAP(K1,K2,K3,K4,K5,f); 
END Structural;	