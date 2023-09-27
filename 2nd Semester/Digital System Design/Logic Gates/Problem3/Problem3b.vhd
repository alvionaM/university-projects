LIBRARY IEEE;
USE IEEE.std_logic_1164.all;

ENTITY Problem3b IS
PORT (x1,x2,x3,x4: IN std_logic;
					f :OUT std_logic);
END Problem3b;

ARCHITECTURE Behavioral OF Problem3b IS 
BEGIN 
	f<= (NOT x1 AND x2) OR (x3 AND x4);
END Behavioral;