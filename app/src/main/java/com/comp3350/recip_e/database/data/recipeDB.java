package com.comp3350.recip_e.database.data;

import com.comp3350.recip_e.database.iRecipeManager;
import com.comp3350.recip_e.objects.Recipe;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class recipeDB implements iRecipeManager {
    private String path;
    //constructor, save the json file path.
    public recipeDB(String filePath){
       path=filePath;
    }

    //as required, return a object that contain certain information of a recipe
    @Override
    public Recipe getRecipe(int recipeId, boolean withpic) {

        String name = getRecipeName(recipeId);
        String ingre = getIngredients(recipeId);
        String direction = getDirection(recipeId);
        int serving = Integer.parseInt(getServing(recipeId));
        int prep = Integer.parseInt(getPrepTime(recipeId));
        int cook = Integer.parseInt(getCookTime(recipeId));

        Recipe wholeRecipe;
        if (withpic) {
            String picPath = getCoverPic(recipeId);
            wholeRecipe = new Recipe(name, ingre, direction, serving, prep, cook, picPath);
        } else {
            wholeRecipe = new Recipe(name, ingre, direction, serving, prep, cook);
        }
        wholeRecipe.setID(recipeId);

        wholeRecipe.setID(recipeId);

        return wholeRecipe;
    }

    public void resetRecipe(){
        JSONObject newJson=new JSONObject();
        JSONObject currentRecipe=jsonReader();
        String runTimePath="src/main/asset/runTimeRecipe.json";
        try{
            JSONObject tempObj=null;
            Object ob=new JSONParser().parse(new FileReader(runTimePath));
            tempObj=(JSONObject) ob;
            if(!tempObj.isEmpty()){
                tempObj.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        for (int i = 0; i < 5; i++) {
            if(currentRecipe.get(""+i)!=null){
                newJson.put(i,currentRecipe.get(""+i));
            }
        }

        try{
            FileWriter jFile=new FileWriter(runTimePath);
            jFile.write(newJson.toJSONString());
            jFile.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private JSONObject jsonReader(){
        JSONObject jsob=null;
        try {

            Object ob=new JSONParser().parse(new FileReader(path));
            jsob=(JSONObject) ob;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsob;
    }
    private JSONObject getValue(int recipeId){
        JSONObject obj= jsonReader();
        JSONObject result=null;
        try {
             result=(JSONObject) obj.get(""+recipeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public String getRecipeName(int recipeId){

        return (String)getValue(recipeId).get("name");
    }

    //get the ingredients of a recipe
    public String getIngredients(int recipeId){

        return (String)getValue(recipeId).get("ingredients");
    }

    //get directions of a recipe
    public String getDirection(int recipeId){

        return (String)getValue(recipeId).get("Directions");
    }

    @Override
    public String getServing(int recipeId){

        return "" + getValue(recipeId).get("serving");
    }

    @Override
    public String getPrepTime(int recipeId){
        return "" + getValue(recipeId).get("prep");
    }

    @Override
    public String getCookTime(int recipeId) {
        return "" + getValue(recipeId).get("cook");
    }

    private void jsonWriter(JSONObject object){

        try (FileWriter file = new FileWriter(path)) {
            file.write(object.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    private void addHelper(JSONObject value,JSONObject key,int recipeId, String name, String ingre,
                           String direction,int serving,int prep, int cook, String cover){

        value.put("name",name);
        value.put("ingredients",ingre);
        value.put("direction",direction);
        value.put("serving",serving);
        value.put("prep", prep);
        value.put("cook", cook);
        value.put("cover",cover);

        key.put(""+recipeId,value);

       jsonWriter(key);
    }

    //add a new recipe to the fake database, return a Recipe object
    public Recipe addRecipe(Recipe recipe){

        JSONObject value=new JSONObject();
        int recipeId=0;

        JSONObject key=jsonReader();
        Boolean nextId = false;
        while (recipeId <Integer.MAX_VALUE && !nextId) {
            if(key.get(""+recipeId)!=null) {
                recipeId++;
            }
            else
            {
                nextId = true;
            }
        }
//        System.out.println(recipeId+"!!\n");
        String coverPath="src/main/asset/recipeCover/"+recipe.getPicture();
        addHelper(value, key,recipeId, recipe.getName(), recipe.getIngredients(), recipe.getInstructions(),
                                    recipe.getServings(), recipe.getPrepTime(), recipe.getCookTime(), recipe.getPicture());

        //        System.out.println("exit try-catch.\n");
        recipe.setID(recipeId);

        return recipe;
    }

    //delete a current recipe from fake database
    public boolean delRecipe(int recipeId){
        JSONObject origin=jsonReader();

        if(origin.get(""+recipeId)!=null){
            origin.remove(""+recipeId);
            jsonWriter(origin);

            return true;
        }
        return false;
    }

    //Keep the fixed path prefix, find the corresponding image by id and return the full path or empty path
    public String getCoverPic(int recipeId){
        String tempPath="src/main/asset/recipeCover/";
        JSONObject recipes=jsonReader();

        if(recipes.get(""+recipeId)!=null){
          String cover=(String)getValue(recipeId).get("cover");
          tempPath+=cover;
          return tempPath;
        }

        return "No such pic.";
    }
}

