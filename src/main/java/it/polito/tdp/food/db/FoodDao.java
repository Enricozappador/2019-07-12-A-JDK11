package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Food> ordersByPortion(int portions){
		String sql="SELECT f.food_code, f.display_name COUNT(DISRINCT p.portion_id) AS cnt FROM food f, portion p, WHERE f.food_code = p.food_code GROUP BY f.food_code HAVING cnt = ? ORDER BY diplay_name ASC";
		List<Food> result = new ArrayList<>(); 
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, portions);
	
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(new Food(res.getInt("food_code"), res.getString("display_name"))); 
			}
			
			conn.close();
			return result; 

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	
	
	
	}
	
	
	public Double calorieCongiunte(Food f1, Food f2) {
		String sql ="SELECT fc1.condiment_code, fc2.condiment_code, AVG(c.condiment_calories) FROM food_condiment fc1, food_comdiment fc2  ,condiment c WHERE  fc1.condiment_code=fc2.condiment_code  AND c.condiment_code = fc1.condiment_code AND fc1.food_code!=fc2.food_code GROUP BY fc1.food_code, fc2.food_code ";
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, portions);
	
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(new Food(res.getInt("food_code"), res.getString("display_name"))); 
			}
			
			conn.close();
			return result; 

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	
		
		return null;
	} 
	
}
