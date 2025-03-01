package com.example.tastify.model;

import com.example.tastify.model.dataClasses.PlannedRecipe;
import com.example.tastify.model.dataClasses.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.core.Observable;

public class RecipeFirebaseDataSource {

    private static RecipeFirebaseDataSource instance;
    private static String userId;

    private final String USER_KEY = "users";
    private final String RECIPE_KEY = "recipes";
    private final FirebaseFirestore db;

    private RecipeFirebaseDataSource() {
        db = FirebaseFirestore.getInstance();
    }

    public static RecipeFirebaseDataSource getInstance() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (instance == null || !Objects.equals(userId, currentUser.getUid())) {
            if (currentUser == null) {
                throw new IllegalStateException("Cannot access Firebase data source without a logged-in user.");
            }
            userId = currentUser.getUid();
            instance = new RecipeFirebaseDataSource();
        }
        return instance;
    }

    public  Observable<List<Recipe>> getRecipesFromFirestore() {
        return Observable.create(emitter -> {
            db.collection(USER_KEY).document(userId)
                    .collection(RECIPE_KEY)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<Recipe> recipes = new ArrayList<>();
                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            recipes.add(doc.toObject(Recipe.class));
                        }
                        emitter.onNext(recipes);
                        emitter.onComplete();
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    public void addRecipeToFirestore(Recipe recipe) {
        db.collection(USER_KEY).document(userId)
                .collection(RECIPE_KEY)
                .document(recipe.getIdMeal())
                .set(recipe);
    }

    public void removeRecipeFromFireStore(Recipe recipe) {
        db.collection(USER_KEY).document(userId)
                .collection(RECIPE_KEY)
                .document(recipe.getIdMeal())
                .delete();
    }

    public Observable<List<PlannedRecipe>> getPlannedRecipesFromFirestoreByDate(String date) {
        return Observable.create(emitter -> {
            db.collection(USER_KEY).document(userId)
                    .collection("planned_recipes")
                    .whereEqualTo("date", date)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<PlannedRecipe> recipes = new ArrayList<>();
                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            PlannedRecipe recipe = doc.toObject(PlannedRecipe.class);
                            if (recipe != null) {
                                recipes.add(recipe);
                            }
                        }
                        emitter.onNext(recipes);
                        emitter.onComplete();
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    public void addPlannedRecipeToFirestore(PlannedRecipe recipe) {
        String compositeKey = recipe.getDate() + "_" + recipe.getIdMeal();
        db.collection(USER_KEY).document(userId)
                .collection("planned_recipes")
                .document(compositeKey)
                .set(recipe);
    }

    public void removePlannedRecipeFromFireStore(PlannedRecipe plannedRecipe) {
        String compositeKey = plannedRecipe.getDate() + "_" + plannedRecipe.getIdMeal();
        db.collection(USER_KEY).document(userId)
                .collection("planned_recipes")
                .document(compositeKey)
                .delete();
    }


boolean isNullUser(){
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            return true;
        }
        else {
            return false;
        }
}

    public static void resetInstance() {
        instance = null;
        userId = null;
    }
}
