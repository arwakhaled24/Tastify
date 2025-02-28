package com.example.tastify.model;

import com.example.tastify.model.dataClasses.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.core.Observable;


public class RecipeFirebaseDataSource {

    private final String USER_KEY="users";
    private final String RECIPE_KEY="recipes";
    private final FirebaseFirestore db;
    private String userId;

    public RecipeFirebaseDataSource() {
        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getUid();
    }

    public Observable<List<Recipe>> getRecipesFromFirestore() {
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
        db.collection(USER_KEY).document(userId)
                .collection(RECIPE_KEY)
                .document(recipe.getIdMeal())
                .set(recipe);
    }

    public void removeRecipeFromFireStore(Recipe recipe){
        db.collection(USER_KEY)
        .document(userId).
        collection(RECIPE_KEY)
        .document(recipe.getIdMeal())
        .delete();
    }

}
