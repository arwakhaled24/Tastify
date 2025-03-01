package com.example.tastify.model;
import android.util.Log;
import com.example.tastify.model.dataClasses.PlannedRecipe;
import com.example.tastify.model.dataClasses.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.core.Observable;

public class RecipeFirebaseDataSource {

    private final String USER_KEY = "users";
    private final String RECIPE_KEY = "recipes";
    private final FirebaseFirestore db;
    private String userId;

    public RecipeFirebaseDataSource() {
        db = FirebaseFirestore.getInstance();
    }

    public Observable<List<Recipe>> getRecipesFromFirestore() {
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

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
                    });
        });
    }


    public void addRecipeToFirestore(Recipe recipe) {
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection(USER_KEY).document(userId)
                .collection(RECIPE_KEY)
                .document(recipe.getIdMeal())
                .set(recipe);
    }

    public void removeRecipeFromFireStore(Recipe recipe) {
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection(USER_KEY)
                .document(userId).
                collection(RECIPE_KEY)
                .document(recipe.getIdMeal())
                .delete();
    }

   public Observable<List<PlannedRecipe>> getPlannedRecipesFromFirestoreByDate(String date) {
       return Observable.create(emitter -> {
           userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

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
                   .addOnFailureListener(e -> {
                       emitter.onError(e);
                   });
       });
   }

    public void addPlannedRecipeToFirestore(PlannedRecipe recipe) {
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String compositeKey = recipe.getDate() + "_" + recipe.getIdMeal();
        db.collection(USER_KEY).document(userId)
                .collection("planned_recipes")
                .document(compositeKey)
                .set(recipe);
    }

    public void removePlannedRecipeFromFireStore(PlannedRecipe plannedRecipe) {
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String compositeKey = plannedRecipe.getDate() + "_" + plannedRecipe.getIdMeal();
        db.collection(USER_KEY)
                .document(userId).
                collection("planned_recipes")
                .document(compositeKey)
                .delete();
    }


}
