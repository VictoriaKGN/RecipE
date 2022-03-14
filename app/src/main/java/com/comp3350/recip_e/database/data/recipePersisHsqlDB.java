package com.comp3350.recip_e.database.data;

import com.comp3350.recip_e.objects.Recipe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class recipePersisHsqlDB {
    private final String dbpath;

    public recipePersisHsqlDB(String path){
        dbpath=path;
    }

    private Connection connection() throws SQLException{
        return DriverManager.getConnection("jdbc:hsqldb:file:"+ dbpath + ";shutdown=true", "SA", "");
    }

    private Recipe fromResultSet(ResultSet rs) throws SQLException{
        final int id=rs.getInt("id");
        final String name=rs.getString("name");
        final String ingredients=rs.getString("ingredients");
        final String instructions=rs.getString("instructions");
        final String picture=rs.getString("picture");
        final int serving=rs.getInt("servings");
        final int prepTime=rs.getInt("prepTime");
        final int cookTime=rs.getInt("cookTime");

        Recipe objResult=new Recipe(name,ingredients,instructions,serving,prepTime,cookTime,picture);
        objResult.setID(id);

        return objResult;
    }

    private void sqlSetHelper(String prepSt, Recipe recipe){
        try (final Connection c=connection()){
            final PreparedStatement st=c.prepareStatement(prepSt);
            //do we still need id?
//            st.setInt(1,recipe.getID());
            st.setString(1,recipe.getName());
            st.setString(2,recipe.getIngredients());
            st.setString(3,recipe.getInstructions());
            st.setInt(4,recipe.getServings());
            st.setInt(5,recipe.getPrepTime());
            st.setInt(6,recipe.getCookTime());
            st.setString(7, recipe.getPicture());
            st.setInt(8,recipe.getID());

            st.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertRecipe(Recipe recipe){
        String insertQuery="INSERT INTO Recipes VALUE(?,?,?,?,?,?,?,?)";
        sqlSetHelper(insertQuery,recipe);

    }

    //new Recipe(name,ingredients,instructions,serving,prepTime,cookTime,'picture');
    public void updateRecipe(Recipe recipe){
        String updateQuery="UPDATE Recipes SET name = ?,ingredients =?, instructions =?, serving=?,prepTime=?,cookTime=?,picture=? where recipeID = ?";
        sqlSetHelper(updateQuery,recipe);
    }


}
