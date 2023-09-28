#	Computer Systems Organization
#	Winter Semester 2021-2022
#	1st Assignment
#
# 	Pseudocode by MARIA TOGANTZH (mst@aueb.gr)
#
# 	MIPS Code by Alviona Mantso, p3200098@aueb.gr, 3200098 
# 	(Please note your name, e-mail and student id number)


	.text
	.globl __start		

# ------------------- Read and Validate Data ------------------------------

__start:	  		 	# counter = 4
						#
loop: 		 			# while counter != 0 do
	lw   $t0, count		#
	beqz $t0, exit_loop	#
						#
	li  $v0, 12 		#
		syscall			#	Read hex character in $v0
						# 	
	# input<'0'			#	
	lb  $t0, char0  	# 	if charakter is not ('0'..'9') and is not ('A'..'F') then
	blt $v0, $t0, exit_on_error  			
						#			goto exit_on_error
	# '0'<=input<='9'	# 		else 	
	lb  $t0, char9		# 			goto isHex
	ble $v0, $t0, isHex #
						#
	# '9'<input<'A' 	#
	lb  $t0, charA		#
	blt $v0, $t0, exit_on_error
						#
	# 'A'<=input<='F'	#
	lb  $t0, charF		#
	ble $v0, $t0, isHex	#
						#
	# input>'F' 		#
	j exit_on_error		#
						#
						#		
						#
isHex:					#
	sll $t1, $t1, 8 	# 	shift left $t1
	or  $t1, $t1, $v0 	# 	pack $v0 to $t1 
						#
						#
	lw  $t0, count		# 	counter = counter - 1
	sub $t0, $t0, 1 	#
	sw  $t0, count		#
						#
	j loop				# goto loop
						
		
# ------------------- Calculate Decimal Number -----------------------------	
exit_loop:		

	la $a0, endl		# print '\n'
	li $v0, 4			#
		syscall			#
						#
						# result = 0
						#
	li $t0, 4			# counter = 4
	sw $t0, count		# 
						# power = 1
						#
	li $s1, 255			# $s1 = 255 (mask = 11111111)		
						#
						#
loop2:					# while counter != 0 do
	lw  $t0, count   	#
	beqz $t0, exit_loop2#
						#
	and $t2, $t1, $s1	# 	$t2 =  least significant byte from $t1 (unpack)
	srl $t1, $t1, 8		# 	shift right $t1 
						#
						#
						#
						#
	lb  $t0, char9		# 	if $t2 is letter A..F then 
	bgt $t2, $t0, sub_55# 		$t2 = $t2 - 55
						# 	else 
	#else: 0..9			#		$t2 = $t2 - 48
		sub $t2,$t2,48	#
		j after_sub		#
						#
	#A..F				#
	sub_55:				#
		sub $t2,$t2,55	#
						#
						#
						#
	after_sub:			#		
	lw  $t0, power		#
						#
	#32 bits are enough	#
	#as the operation	#
	#will give 15*16^3	#
	#at most			#
	mul $t2, $t2, $t0	# 	$t2 = $t2 * power
						#
	#32 bits are enough	#
	#here as well		#
	mul $t0, $t0, 16	#
	sw  $t0, power		# 	power = power * 16
						#
	lw  $t0, count		#
	sub $t0, $t0, 1 	#	counter = counter - 1
	sw  $t0, count		#
						#
	lw  $t0, result		#
	add $t0, $t0, $t2 	#	result = result + $t2
	sw 	$t0, result		# 	
						#
	j loop2				# goto loop2

# ------------------- Print Results ------------------------------------		

exit_loop2:				# print result
	lw $a0, result		# print resul
	li $v0, 1			#
		syscall			#
	j exit				# goto exit
						#
						#
exit_on_error:			#
	la $a0, endl		# print '\n'
	li $v0, 4			#
		syscall			#
						#
	la $a0, error		# print error message
		syscall			#
						#
						#
exit:					# 
	la $a0, endl		# print '\n'
	li $v0, 4			#
		syscall			#	
						#
	li $v0, 10			# exit
		syscall			#
						#


	.data
	count: .word  4
	power: .word  1
	result:.word  0
	char0: .byte '0'
	char9: .byte '9'
	charA: .byte 'A'
	charF: .byte 'F'
	endl:  .asciiz "\n"
	error: .asciiz "Wrong Hex Number ..." 