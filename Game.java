import java.util.InputMismatchException;
import java.util.Scanner;

	public class Game {
	
		
		private final int longStep = 3;              	
		private int x = 3; 
		                                                      // довжина ходів 
		private int y = 3; 	
		
		private int userX = 0;  						
																//ходи гравців x,y
		private int userY = 0;  
		
		private char[][] field;
		
		private int activeUser = 0; 

		public Game(int x, int y){ 
			
			if(x > 3){
				this.x = x;
			}		
			if(y > 3){
				this.y = y;
			} 
			
			field 		= new char[this.y][this.x];		
			activeUser 	= 1;
		}
		
		public void setUserCoors(int x, int y){
			userX = x;
			userY = y;
		}
		
		public void show(){
			System.out.print("  ");
			for(int i=0; i<field[0].length; i++){
				System.out.print(""+i);
			}
			System.out.println("");
			//-------------------------------
			for(int i=0; i<field.length; i++){
				System.out.print(i+"|"); 
				
				for(int j=0; j<field[i].length; j++){		 
					
					if(field[i][j] != 0){
						System.out.print(""+field[i][j]);
					} else {
						System.out.print("_");
					} 
					
				}	
				System.out.println("");
			}
		}
		
		
		private char getUserSymbol(){
			if(activeUser == 1){
				return '0';                                      //символ активного юзера
			} else if(activeUser == 2){			
				return 'X'; 
			}
			
			return ' ';
		}
		
		
		private boolean countSteps(int x, int y, String way, int count){ 
			
			
			switch(way){
				case "up": 
					if(checkEmpty(x,y-1) >= 0){  
						                                               
						                 //якщо такий ж самий символ
						
						if(field[y-1][x] == getUserSymbol()){  						
							if(count+1 == longStep-1){ // максимальна кількість 
								return true;
							} else {
								return countSteps(x, y-1, way, count+1);
							} 
						}
					}   
					break;
				case "up-left": 
					if(checkEmpty(x-1,y-1) >= 0){  
						//якщо такий ж самий символ 
						if(field[y-1][x-1] == getUserSymbol()){  						
							if(count+1 == longStep-1){ // досягнута максимальна кількість 
								return true;
							} else {
								return countSteps(x-1, y-1, way, count+1);
							} 
						}
					}   
					break;
				
				case "up-right": 
					if(checkEmpty(x+1,y-1) >= 0){  
						if(field[y-1][x+1] == getUserSymbol()){  						
							if(count+1 == longStep-1){ 
								return true;
							} else {
								return countSteps(x+1, y-1, way, count+1);
							} 
						}
					}   
					break;
					
				case "down": 
					if(checkEmpty(x,y+1) >= 0){  
							if(field[y+1][x] == getUserSymbol()){  						
								if(count+1 == longStep-1){ 
								return true;
							} else {
								return countSteps(x, y+1, way, count+1);
							} 
						}
					}   
					break;
					
				case "down-left":   
					if(checkEmpty(x-1,y+1) >= 0){  
			if(field[y+1][x-1] == getUserSymbol()){  						
							if(count+1 == longStep-1){ 
								return true;
							} else {
								return countSteps(x-1, y+1, way, count+1);
							} 
						}
					}   
					break;
					 
				case "down-right":  
					if(checkEmpty(x+1,y+1) >= 0){  
					if(field[y+1][x+1] == getUserSymbol()){  						
							if(count+1 == longStep-1){ 
								return true;
							} else {
								return countSteps(x+1, y+1, way, count+1);
							} 
						}
					}   
					break;
					
				case "left": 
					if(checkEmpty(x-1,y) >= 0){  
						if(field[y][x-1] == getUserSymbol()){  						
							if(count+1 == longStep-1){ 
								return true;
							} else {
								return countSteps(x-1, y, way, count+1);
							} 
						}
					}   
					break;
					
				case "right": 
					if(checkEmpty(x+1,y) >= 0){  
						if(field[y][x+1] == getUserSymbol()){  						
							if(count+1 == longStep-1){ 
								return true;
							} else {
								return countSteps(x+1, y, way, count+1);
							} 
						}
					}   
					break;
			}
			return false;
		}
		
		
		/** 
		Перевірка чи не виходить за поля
		 */
		private boolean checkStop(){
			
			String [] arr = new String[]{"up", "down", "left", "right", "up-left", "up-right", "down-left", "down-right"};
			
			for(int i = 0; i < arr.length; i++){
				if(countSteps(userX, userY, arr[i], 0)){
					return true;
				}
			} 
			
			return false;
		}
		
		/**	 
		 * @return int 0 - занято, -1 - вихід за діапазон, 1 - пусто 
		 */
		public int checkEmpty(int x, int y){
			
			int status = 0;  
			
			if((x >= 0 && y >= 0) && (x < this.x && y < this.y)){
				
				if(field[y][x] == 0){
					status = 1;
				} 
				
			} else {	 
				status = -1; // вихід за діапазон
			}
			
			return status;
		}
		
		/**
		 * Робиться хід 
		 */
		private void setStep(){ 
		
			if(activeUser == 1){
				field[userY][userX] = getUserSymbol();
				activeUser = 2;
			} else if(activeUser == 2){			
				field[userY][userX] = getUserSymbol();
				activeUser = 1;
			}
		}
		
		/**
		 * вивід чий хід
		 */
		public void getUserMessage(){
			System.out.println("");
			System.out.print("Гравець №"+activeUser+" - введіть хід (1x0): ");
		}
		

		public static void main(String [] args){
			
			Scanner sc = null; 
			
			int x = 0;
			int y = 0;
			Game obj = null;
			boolean init = false; 
			
			
			while(true){ 
				
				if(!init){
					
					try{	
						
						sc = new Scanner(System.in);
					
						if(x == 0){
							System.out.print("Розмір 3:  ");
							x = sc.nextInt();
						}
						
						if(y == 0){
							System.out.print("Розмір 3: ");
							y = sc.nextInt();
						}
						
						obj = new Game(x,y); 
						obj.show(); 
						init = true;
						//obj.getUserMessage(); 
					} catch(InputMismatchException e){
						System.out.println("Введені невірні символи. Повторіть спробу"); 
					}   
				} else {
					String str = sc.nextLine(); 
					if(!str.equals("")){				
						String [] coors  = str.split("x");
						
						if(coors.length == 2){
							
							try	{
								int userX = Integer.parseInt(coors[0]);
								int userY = Integer.parseInt(coors[1]); 
								
								obj.setUserCoors(userX, userY);
								
								int status = obj.checkEmpty(userX, userY);
								
								//switch
								if(status > 0){				
									
									boolean end = obj.checkStop();
									String message = "@@Кінець, виграв гравец №"+obj.activeUser;
									
									
									obj.setStep();  
									obj.show();
									
									
									if(end){  
										System.out.println(message); 
										break;
									} 
									
								} else if(status == -1){
									System.out.println("Вихід за діапазон"); 
								} else {
									System.out.println("Повторіть спробу"); 
								}		 
								
							} catch(NumberFormatException e){
								System.out.println("Координати мають бути цілими числами"); 
							} 
						} else {
							System.out.println("Координати мають виглядати так (1x0)!"); 
						}
					}   
					obj.getUserMessage(); 
				} 
				
				
				
			}   
			 
		}
	}
