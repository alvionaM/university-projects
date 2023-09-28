#	Computer Systems Organization
#	Winter Semester 2021-2022
#	3rd Assignment
#
#
# 	MIPS Code by |Petsa Georgia	  |	p3200155@aueb.gr  |	id: 3200155 
#				 |Mancho Alviona  |	p3200098@aueb.gr  |	id: 3200098 

	.text
	.globl main_
	
main_:
	move $s0, $zero  			#head of list (at first the list is empty)
	
		
	LoopMenu:
		la $a0, menu			#print menu 
		li $v0, 4
			syscall
			
		la $a0, mess				
		li $v0, 4
			syscall
			
		li $v0, 5				#read user input for operation
			syscall
			
		beq $v0, 1, InsertNode	
			
		beq $v0, 2, RemoveNode
			
		beq $v0, 3, PrintList
			
		beq $v0, 0, ExitMain
		
		j LoopMenu
	
	InsertNode:
		move $a0, $s0  			#head as argument
		jal insert
		move $s0, $v0  			#update head 
		j LoopMenu
			
	RemoveNode:
		move $a0, $s0  			#head as argument
		jal remove
		move $s0, $v0  			#update head 
		j LoopMenu
		
	PrintList:
		move $a0, $s0 			#head as argument 
		jal print
		j LoopMenu
		
	ExitMain:
		li $v0, 10
			syscall
		
#-------------------subroutine for insertion---------------------
#Argument: 	head of list ($s0)
#Return value: 	updated head of list
#The method reads an integer, then it traverses the list and insterts the integer in the right position (ascending order) 

insert:
	move $t0, $a0   			#head
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	li $a0, 8					#2x4 bytes (for 2 integers)
	li $v0, 9					#sbrk
		syscall
	move $t1, $v0  				#first address
	
	#calling read integer routine
	jal readInt
		
	sw $v0, ($t1)     			#new node t
	sw $zero, 4($t1)  			#t.next = null	(at least initially)
	
	#CASE 1: EMPTY LIST
	beqz $t0, empty
	
	
	#CASE 2: INSERT AT FRONT
	lw   $t3, ($t0)   			#$t3 = head.data
	ble  $v0, $t3, insertAtFront
	
	
	#CASE 3: GENERAL INSERTION
	move $t4, $t0   			#prev = head
	move $t5, $t0   			#curr = head 

	loop1: 	
		beqz $t5, insertBefore	#insert at back (same operations as general insertion) 
		lw   $t3, ($t5) 		#$t3=curr.data
		
		ble  $v0, $t3, insertBefore 
		move $t4, $t5 			#prev = curr 
		lw   $t5, 4($t5)		#curr = curr.next
		j loop1
	

	empty:
		move $t0, $t1   		#head = t
		j done1
		
	insertAtFront:
		sw $t0, 4($t1)  		#t.next = head
		move $t0, $t1   		#head = t
		j done1
		
	insertBefore: 
		sw $t5, 4($t1) 			#t.next = curr
		sw $t1, 4($t4) 			#prev.next = t
		j done1 
		
	done1:
		move $v0, $t0
		
		lw $ra, 0($sp)
		addi $sp, $sp, 4
		jr $ra 
		
#-------------------subroutine for removal---------------------
#Argument: 	head of list ($s0)
#Return value: 	updated head of list
#The method reads an integer, then it traverses the list and removes the first integer matching the input. Prints informing
#message if the list is empty or doesen't contain the input.

remove:
	move $t0, $a0
	
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	#CASE 1: EMPTY LIST
	beqz $t0, emptyList 		#if the list is empty there is no need to proceed below
	
	
	#if the list is not empty...
	
	#calling read integer routine
	jal readInt

	
	#CASE 2: REMOVE FROM FRONT 
	lw $t3, ($t0)				#$t3 = head.data
	beq $t3, $v0, removeFromFront
	
	#CASE 3: GENERAL
	move $t4, $t0   			#prev = head
	move $t5, $t0   			#curr = head 
	
	loop2:
		beqz $t5, notFound
		lw   $t3, ($t5)  		#$t3 = curr.data
		beq  $t3, $v0, found
		move $t4, $t5 			#prev = curr 
		lw   $t5, 4($t5)		#curr = curr.next

		j loop2
	
	emptyList:
		la $a0, mess3
		li $v0, 4
			syscall
		j done2
	
	removeFromFront:
		lw $t9, 4($t0)			#$t5 = t.next	
		beq $t9, $zero, oneElement
		
		move $t0, $t9			#head = t.next
		j done2
		
		oneElement:
			move $t0, $zero		#list becomes empty
			j done2
	
	found:
		lw $t9, 4($t5)			#$t9 = t.next
		sw $t9, 4($t4)			#prev.next = curr.next
		j done2
	
	notFound:
		la $a0, mess4
		li $v0, 4
			syscall
		j done2
	
	done2:
		move $v0, $t0
		
		lw $ra, 0($sp)
		addi $sp, $sp, 4
		
		jr $ra
		
#-------------------subroutine for printing---------------------	
#Argument: 	head of list ($s0)
#Return value: none (void method)
#The method traverses the list and prints all the elements.	
	
print: 
	move $t0, $a0 				#head 
	
	la $a0, list				#print "list: "
	li $v0, 4
		syscall
		
	beqz $t0, done3 			#if the list is empty print "null"
	 
	move $t5, $t0 				#$t5<->curr = head 
	
	loop3:
		beqz $t5, done3		 	#end of list
		
		lw $a0, ($t5)       	#print node
		li $v0, 1
			syscall
			
		li $v0, 4
		la $a0, pointer			#print "->"
			syscall
			
		lw $t5, 4($t5)			#curr = curr.next
		
		
		j loop3
		
	done3:
		la $a0, null
			syscall
		la $a0, endl			#new line
			syscall
		
		jr $ra 

#-------------------subroutine for reading an integer---------------------	
#Argument: 	none
#Return value: the integer input 
#The method prints a message and reads an integer from the console.
readInt:
	
	la $a0, mess2				#print message 
	li $v0, 4
		syscall
	li $v0, 5		  			#read int
		syscall
	jr $ra 
		
		
	.data
	menu: .asciiz "-----------------------------------\n1. Insert new node\n2. Remove node\n3. Print the list\n0. Exit\n"
	mess: .asciiz "\t> Enter operation code: "
	mess2:.asciiz "\t> Give number: "
	endl: .asciiz "\n"
	pointer:.asciiz " -> "
	list: .asciiz "\tlist: "
	null: .asciiz "null"
	mess3:.asciiz "\t The list is Empty! \n"
	mess4:.asciiz "\t Element not found! \n"