package com.comp3350.recip_e.database.data;

import com.comp3350.recip_e.objects.Recipe;
import com.comp3350.recip_e.objects.User;
import com.comp3350.recip_e.database.iRecipeManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class recipePersisHsqlDB implements iRecipeManager {
    private final String dbpath;

    public recipePersisHsqlDB(String path){
        dbpath=path;
    }

    private Connection connection() throws SQLException{
        return DriverManager.getConnection("jdbc:hsqldb:file:"+ dbpath + ";shutdown=true", "SA", "");
    }

    private Recipe fromResultSet(ResultSet rs) throws SQLException{
        final int id=rs.getInt("recipeID");
        final String name=rs.getString("name");
        final String picture=rs.getString("picture");
        final int serving=rs.getInt("servings");
        final int prepTime=rs.getInt("prepTime");
        final int cookTime=rs.getInt("cookTime");
        final String userID = rs.getString("userID");

        Recipe objResult=new Recipe(name,null,null,serving,prepTime,cookTime,picture,userID);
        objResult.setID(id);

        return objResult;
    }

    private String resultIngredient(ResultSet rs) throws SQLException
    {
        return rs.getString("ingredient");
    }

    private String resultInstruction(ResultSet rs) throws SQLException
    {
        return rs.getString("instruction");
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
            int counter = 1;

            while(!nextIngredient.equals(""))
            {
                final PreparedStatement st = c.prepareStatement(ingrQuery);
                st.setString(1, nextIngredient);
                st.setInt(2, recipe.getID());
                st.setInt(3, counter);
                st.executeUpdate();
                nextIngredient = recipe.getNextIngredient();
                counter++;
            }

            recipe.resetNextInstruction();
            String nextInstruction = recipe.getNextInstruction();
            counter = 1;

            while(!nextInstruction.equals(""))
            {
                final PreparedStatement st = c.prepareStatement(instQuery);
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
            while (rs.next())
            {
                final Recipe recipe = fromResultSet(rs);

                final PreparedStatement st = c.prepareStatement("SELECT ingredient FROM Ingredients where recipeID = ?");
                st.setInt(1, recipe.getID());
                final ResultSet rt = st.executeQuery();

                final PreparedStatement su = c.prepareStatement("SELECT instruction FROM Instructions where recipeID = ? order by instructionNum");
                su.setInt(1,recipe.getID());
                final ResultSet ru = su.executeQuery();
                //final ResultSet rt = st.executeQuery("SELECT ingredient FROM Ingredients where recipeID = " + recipe.getID());
                //final ResultSet ru = st.executeQuery("SELECT instruction FROM Instructions where recipeID = " + recipe.getID() + " order by instructionNum");
                final ArrayList<String> ingredients = new ArrayList<>();
                final ArrayList<String> instructions = new ArrayList<>();

                while (rt.next()) {
                    String ingredient = resultIngredient(rt);
                    ingredients.add(ingredient);
                }
                recipe.updateIngredients(ingredients);

                while (ru.next()) {
                    String instruction = resultInstruction(ru);
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

    private int getNextID()
    {
        try(final Connection c = connection())
        {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM Recipes order by recipeID");
            int counter = 0;
            boolean found = false;

            while(rs.next() && !found)
            {
                final Recipe recipe = fromResultSet(rs);

                if(!(recipe.getID() == counter))
                {
                    found = true;
                }
                else
                {
                    counter++;
                }
            }
            return counter;
        }
        catch(SQLException e)
        {
            throw new hsqlDBException(e);
        }
    }


    @Override
    public Recipe addRecipe(Recipe recipe){
        String insertQuery="INSERT INTO Recipes (name, servings, prepTime, cookTime, picture, userID, recipeID) VALUES(?,?,?,?,?,?,?)";
        recipe.setID(getNextID());
        sqlSetHelper(insertQuery,recipe);

        String ingredientQuery = "INSERT INTO Ingredients VALUES(?,?,?)";
        String instructionQuery = "INSERT INTO Instructions VALUES (?,?,?)";
        weakEntHelper(ingredientQuery, instructionQuery, recipe);

        return recipe;
    }

    @Override
    public void updateRecipe(Recipe recipe){
        String updateQuery="UPDATE Recipes SET name = ?,servings=?,prepTime=?,cookTime=?,picture=?, userID = ? where recipeID = ?";
        sqlSetHelper(updateQuery,recipe);

        String ingredientQuery = "UPDATE Ingredients SET ingredient = ? where recipeID = ? and ingredientNum = ?";
        String instructionQuery = "UPDATE Instructions SET instruction = ? where recipeID = ? and instructionNum = ?";
        weakEntHelper(ingredientQuery, instructionQuery, recipe);
    }

    @Override
    public boolean delRecipe(int recipeID, String userID)
    {
        boolean deleted = false;

        try(final Connection c = connection())
        {
            if(this.getRecipe(recipeID).getUserID().equals(userID) || this.getRecipe(recipeID).getUserID() == null) {
                String deleteQuery = "DELETE FROM Recipes where recipeID = ?";
                final PreparedStatement st = c.prepareStatement(deleteQuery);
                st.setInt(1, recipeID);

                st.executeUpdate();
                st.close();
            }
        }
        catch(SQLException e)
        {
            throw new hsqlDBException(e);
        }

        if (this.getRecipe(recipeID) == null)
            deleted = true;

        return deleted;
    }

    public Recipe getRecipe(int recipeID)
    {
        Recipe recipe;
        ArrayList<Recipe> tempStorage = new ArrayList<>();

        try(final Connection c = connection())
        {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM Recipes where recipeID = ?");
            st.setInt(1, recipeID);
            final ResultSet rs = st.executeQuery();

            tempStorage = getHelper(rs, tempStorage);
            if (tempStorage.size() > 0)
                recipe = tempStorage.get(0);
            else
                recipe = null;

            rs.close();
            st.close();
        }
        catch(SQLException e)
        {
            throw new hsqlDBException(e);
        }
        return recipe;
    }

    @Override
    public ArrayList<Recipe> getUserRecipes(String userID)
    {
        ArrayList<Recipe> recipes = new ArrayList<>();

        try(final Connection c = connection())
        {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM Recipes where userID = ? or userID IS NULL");
            st.setString(1, userID);
            final ResultSet rs = st.executeQuery();

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

    @Override
    public ArrayList<Recipe> searchRecipeByName(String userID, String keyword)
    {
        ArrayList<Recipe> recipes = new ArrayList<>();

        try(final Connection c = connection())
        {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM Recipes where LCASE(name) LIKE ? and (userID = ? or userID IS NULL)");
            st.setString(1, "%" + keyword.toLowerCase() + "%");
            st.setString(2, userID);
            final ResultSet rs = st.executeQuery();

            recipes = getHelper(rs, recipes);
            //System.out.println("" + recipes.size());

            rs.close();
            st.close();
        }
        catch(SQLException e)
        {
            throw new hsqlDBException(e);
        }
        return recipes;
    }

    @Override
    public ArrayList<Recipe> searchRecipeByIngredient(String userID, String keyword)
    {
        ArrayList<Recipe> recipes = new ArrayList<>();

        try(final Connection c = connection())
        {
            final PreparedStatement st = c.prepareStatement("SELECT DISTINCT userID, name, servings, prepTime, cookTime, picture, recipeID from (SELECT name, servings, prepTime, cookTime, picture, recipeID, userID FROM Recipes natural join Ingredients where LCASE(ingredient) LIKE ? and (userID = ? or userID IS NULL))");
            st.setString(1, "%" + keyword.toLowerCase() + "%");
            st.setString(2, userID);
            final ResultSet rs = st.executeQuery();

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