package Beverage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//get input from user as order 
		Scanner sc= new Scanner(System.in);
		System.out.print("Your order- ");

		String order = sc.nextLine();              //reads string   
		
		//use split(,) to get key i.e for actual cost and (-/+)  symbol to add or subtract amount write
		Map<String, Double> productPrice = new HashMap<>();

		// add value
		productPrice.put("milk", 1.0);
		productPrice.put("sugar", 0.5);
		productPrice.put("soda",0.5);
		productPrice.put("mint", 0.5);
		productPrice.put("water",0.5);
		productPrice.put("coffee",3.0);
		productPrice.put("tea",2.0);
		productPrice.put("banana",4.0);
		productPrice.put("strawberry",5.0);	
		productPrice.put("lemon",5.5);

		
		Map<String, MenuDetails> menus = new HashMap<String, MenuDetails>();
		
		//menus.put("coffee", "coffee,milk,sugar,water");
		menus.put("coffee", new MenuDetails("coffee,milk,sugar,water",5.0));
		menus.put("chai", new MenuDetails("tea,milk,sugar,water",4.0));
		menus.put("banana smoothie", new MenuDetails("banana,milk,sugar,water",6.0));
		menus.put("strawberry shake", new MenuDetails("strawberries,milk,sugar,water",7.0));
		menus.put("mojito", new MenuDetails("lemon,milk,sugar,water,soda,mint",7.0));
		
		List<String> orderList = new ArrayList<String>();
		if(order.contains("]")) {
			order = order.replace("[", "");
			order = order.replace("]", "");
			
			while(order.contains("\"")) {
				order = order.substring(order.indexOf("\"") + 1,order.length());
				orderList.add(order.substring(0,order.indexOf("\"")));
				order = order.substring(order.indexOf("\"") + 1,order.length());
			}
		}else {
			orderList.add(order);
		}
		
		
		new Test().placeOrder(orderList, menus, productPrice);
	}
	
	
	private String placeOrder(List<String> orderList,Map menus,Map productPrice) {
		for(int i = 0; i < orderList.size(); i++) {
			String order = orderList.get(i);
			String orderContent[] = order.split(",");
			MenuDetails menuDtlObj = (MenuDetails)menus.get(orderContent[0].trim().toLowerCase());
			String ingreduents = menuDtlObj.getIngreduents();
			
			if(orderContent.length > 1) {
				for(int j = 1; j < orderContent.length; j++) {
					ingreduents = ingreduents.replace(orderContent[j].trim().replace("-", ""), "-" + productPrice.get(orderContent[j].trim().toLowerCase().replace("-", "")));
				}
			}else {
				ingreduents = ingreduents.replace(orderContent[0], menuDtlObj.getPrice() + "");
			}
			
			String indArr[] = ingreduents.split(",");	
			for(int k = 0; k < indArr.length; k++) {
				String product = indArr[k];
				
				if(!product.contains("-")) {
					Double price  = (Double)productPrice.get(product.trim().toLowerCase());
					ingreduents = ingreduents.replace(product.trim(),"+" + price);	
				}
				
			}
			ingreduents = ingreduents.replace("null", "0");
			Double finalPrice = 0.0;
			String ingreduentsPriceArr[] = ingreduents.split(",");
			for(int l = 0; l < ingreduentsPriceArr.length; l++) {
				String elePrice = ingreduentsPriceArr[l];
				
				if(elePrice.contains("-")) {
					finalPrice = finalPrice - Double.parseDouble(elePrice.replace("-", "").trim());
				}else {
					finalPrice = finalPrice + Double.parseDouble(elePrice.replace("+", "").trim());
				}
				
			}
			System.out.println("Order " + i + " " + orderContent[0] + " Price := " + finalPrice);
		}
		return "";
	}

}



class MenuDetails{
	private String ingreduents = null;
	private Double price = null;
	
	MenuDetails(String ingreduents,Double price) {
		this.ingreduents = ingreduents;
		this.price = price;
	}
	
	public String getIngreduents() {
		return ingreduents;
	}
	public void setIngreduents(String ingreduents) {
		this.ingreduents = ingreduents;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
}
