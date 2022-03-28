package com.comp3350.recip_e.database.data;

import com.comp3350.recip_e.objects.Recipe;
import com.comp3350.recip_e.objects.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class recipePersisHsqlDB {
    private final String dbpath;

    public recipePersisHsqlDB(String path){
        dbpath=path;
    }

    private Connection connection() throws SQLException{
        return DriverManager.getConnection("jdbc:hsqldb:file:"+ dbpath + ";shutdown=true", "SA", "");
    }

    private Recipe fromResultSet(ResultSet rs) throws SQLException{
        final int id=rs.getInt("RecipeId");
        final String name=rs.getString("name");
        //final String ingredients=rs.getString("ingredients");
        //final String instructions=rs.getString("instructions");
        final String picture=rs.getString("picture");
        final int serving=rs.getInt("servings");
        final int prepTime=rs.getInt("prepTime");
        final int cookTime=rs.getInt("cookTime");
        final String userID = rs.getString("userID");

        Recipe objResult=new Recipe(name,null,null,serving,prepTime,cookTime,picture,userID);
        objResult.setID(id);

        return objResult;
    }

    private void sqlSetHelper(String prepSt, Recipe recipe){
        try (final Connection c=connection()){
            final PreparedStatement st=c.prepareStatement(prepSt);
            //do we still need id?
//            st.setInt(1,recipe.getID());
            st.setString(1,recipe.getName());
            //st.setString(2,recipe.getIngredients());
            //st.setString(3,recipe.getInstructions());
            st.setInt(2,recipe.getServings());
            st.setInt(3,recipe.getPrepTime());
            st.setInt(4,recipe.getCookTime());
            st.setString(5, recipe.getPicture());
            st.setString(6, recipe.getUserID());
            st.setInt(7,recipe.getID());

            st.executeUpdate();
            st.close();
        }catch (SQLException e){
            throw new hsqlDBException(e);
        }
    }

    private void weakEntHelper(String ingrQuery, String instQuery, Recipe recipe)
    {
        try(final Connection c = connection())
        {
            recipe.resetNextIngredient();
            String nextIngredient = recipe.getNextIngredient();

            while(!nextIngredient.equals(""))
            {
                final PreparedStatement st = c.prepareStatement(ingrQuery);
                //st.setString(1, "Ingredients");
                st.setString(1, nextIngredient);
                st.setInt(2, recipe.getID());
                st.executeUpdate();
                nextIngredient = recipe.getNextIngredient();
            }

            recipe.resetNextInstruction();
            String nextInstruction = recipe.getNextInstruction();
            int counter = 1;

            while(!nextInstruction.equals(""))
            {
                final PreparedStatement st = c.prepareStatement(instQuery);
                //st.setString(1, "Instructions");
                st.setString(1, nextInstruction);
                st.setInt(2, recipe.getID());
                st.setInt(3, counter);
                st.executeUpdate();
                nextInstruction = recipe.getNextInstruction();
                counter++;
            }
        }
        catch (SQLException e)
        {
            throw new hsqlDBException(e);
        }
    }

    private ArrayList<Recipe> getHelper(ResultSet rs, ArrayList<Recipe> recipes)
    {
        try(final Connection c = connection())
        {
            final Statement st = c.createStatement();

            while (rs.next())
            {
                final Recipe recipe = fromResultSet(rs);
                final ResultSet rt = st.executeQuery("SELECT ingredient FROM Ingredients where recipeId = " + recipe.getID());
                final ResultSet ru = st.executeQuery("SELECT instruction FROM Instructions where recipeId = " + recipe.getID() + " order by instructionNum");
                final ArrayList<String> ingredients = new ArrayList<>();
                final ArrayList<String> instructions = new ArrayList<>();

                while (rt.next()) {
                    String ingredient = "" + fromResultSet(rt);
                    ingredients.add(ingredient);
                }
                recipe.updateIngredients(ingredients);

                while (ru.next()) {
                    String instruction = "" + fromResultSet(rt);
                    instructions.add(instruction);
                }
                recipe.updateInstructions(instructions);

                recipes.add(recipe);
            }
        }
        catch (SQLException e)
        {
            throw new hsqlDBException(e);
        }
        return recipes;
    }

    public void insertRecipe(Recipe recipe){
        String insertQuery="INSERT INTO Recipes VALUE(?,?,?,?,?,?,?)";
        sqlSetHelper(insertQuery,recipe);

        String ingredientQuery = "INSERT INTO Ingredients VALUE(?,?)";
        String instructionQuery = "INSERT INTO Instructions VALUE (?,?,?)";
        weakEntHelper(ingredientQuery, instructionQuery, recipe);
    }

    //new Recipe(name,ingredients,instructions,serving,prepTime,cookTime,'picture');
    public void updateRecipe(Recipe recipe){
        String updateQuery="UPDATE Recipes SET name = ?,serving=?,prepTime=?,cookTime=?,picture=?, userID = ? where recipeId = ?";
        sqlSetHelper(updateQuery,recipe);

        String ingredientQuery = "UPDATE Ingredients SET ingredient = ? where recipeId = ?";
        String instructionQuery = "UPDATE Instructions SET instruction = ? where recipeId = ? and instructionNum = ?";
        weakEntHelper(ingredientQuery, instructionQuery, recipe);
    }

    public void deleteRecipe(Recipe recipe)
    {
        try(final Connection c = connection())
        {
            String deleteQuery = "DELETE FROM Recipes where recipeId = ?";
            final PreparedStatement st = c.prepareStatement(deleteQuery);
            st.setInt(1, recipe.getID());

            st.executeUpdate();
            st.close();
        }
        catch(SQLException e)
        {
            throw new hsqlDBException(e);
        }
    }

    public ArrayList<Recipe> getUserRecipes(User user)
    {
        ArrayList<Recipe> recipes = new ArrayList<>();

        try(final Connection c = connection())
        {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM Recipes where userID = " + user.getUserEmail());

            recipes = getHelper(rs, recipes);

            rs.close();
            st.close();
        }
        catch (SQLException e)
        {
            throw new hsqlDBException(e);
        }
        return recipes;
    }

    public ArrayList<Recipe> searchName(String keyword)
    {
        ArrayList<Recipe> recipes = new ArrayList<>();

        try(final Connection c = connection())
        {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM Recipes where name LIKE '%" + keyword + "%'");

            recipes = getHelper(rs, recipes);

            rs.close();
            st.close();
        }
        catch(SQLException e)
        {
            throw new hsqlDBException(e);
        }
        return recipes;
    }

    public ArrayList<Recipe> searchIngredients(String keyword)
    {
        ArrayList<Recipe> recipes = new ArrayList<>();

        try(final Connection c = connection())
        {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT name, serving, prepTime, cookTime, picture, recipeId FROM Recipes natural join Ingredients where ingredient LIKE '%" + keyword + "%'");

            recipes = getHelper(rs, recipes);

            rs.close();
            st.close();
        }
        catch(SQLException e)
        {
            throw new hsqlDBException(e);
        }
        return recipes;
    }
}