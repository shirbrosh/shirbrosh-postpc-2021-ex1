package android.exercise.mini.interactions;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditTitleActivity extends AppCompatActivity {


  private boolean isEditing = false;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_title);

    // find all views
    FloatingActionButton fabStartEdit = findViewById(R.id.fab_start_edit);
    FloatingActionButton fabEditDone = findViewById(R.id.fab_edit_done);
    TextView textViewTitle = findViewById(R.id.textViewPageTitle);
    EditText editTextTitle = findViewById(R.id.editTextPageTitle);

    // setup - start from static title with "edit" button
    fabStartEdit.setVisibility(View.VISIBLE);
    fabEditDone.setVisibility(View.GONE);
    textViewTitle.setText("Page title here");
    textViewTitle.setVisibility(View.VISIBLE);
    editTextTitle.setText("Page title here");
    editTextTitle.setVisibility(View.GONE);

    // handle clicks on "start edit"
    fabStartEdit.setOnClickListener(v -> {
      this.isEditing = true;

      // animate out the "start edit" FAB
      fabStartEdit.animate()
              .alpha(0f)
              .setDuration(500L)
              .withEndAction(() -> fabStartEdit.setVisibility(View.GONE))
              .start();

      // animate in the "done edit" FAB
      fabEditDone.setVisibility(View.VISIBLE);
      fabEditDone.setAlpha(0f);
      fabEditDone.animate()
              .alpha(1f)
              .setDuration(500L)
              .start();

      // disappear the text view and animate in the edit title
      textViewTitle.setVisibility(View.GONE);

      editTextTitle.setVisibility(View.VISIBLE);
      editTextTitle.setAlpha(0f);
      editTextTitle.animate()
              .alpha(1f)
              .setDuration(500L)
              .start();

      // make the keyboard to open with the edit-text focused
      editTextTitle.requestFocus();
      showSoftKeyboard(editTextTitle);
    });

    // handle clicks on "done edit"
    fabEditDone.setOnClickListener(v -> {
      this.isEditing = false;

      // animate out the "done edit" FAB
      fabEditDone.animate()
              .alpha(0f)
              .setDuration(500L)
              .withEndAction(() -> fabEditDone.setVisibility(View.GONE))
              .start();

      // animate in the "start edit" FAB
      fabStartEdit.setVisibility(View.VISIBLE);
      fabStartEdit.setAlpha(0f);
      fabStartEdit.animate()
              .alpha(1f)
              .setDuration(500L)
              .start();

      // save the new edited text
      String newText = editTextTitle.getText().toString();
      editTextTitle.setText(newText);
      textViewTitle.setText(newText);

      // disappear the edit title and animate in the text view
      editTextTitle.setVisibility(View.GONE);

      textViewTitle.setVisibility(View.VISIBLE);
      textViewTitle.setAlpha(0f);
      textViewTitle.animate()
              .alpha(1f)
              .setDuration(500L)
              .start();

      // close the keyboard
      hideSoftKeyboard(editTextTitle);
    });
  }

  @Override
  public void onBackPressed() {
    // BACK button was clicked

    // find all views
    FloatingActionButton fabStartEdit = findViewById(R.id.fab_start_edit);
    FloatingActionButton fabEditDone = findViewById(R.id.fab_edit_done);
    TextView textViewTitle = findViewById(R.id.textViewPageTitle);
    EditText editTextTitle = findViewById(R.id.editTextPageTitle);

    if(this.isEditing){

      // save the edit text title as the old title before the change
      String oldText = textViewTitle.getText().toString();
      editTextTitle.setText(oldText);

      // disappear the edit title and animate in the text view
      editTextTitle.setVisibility(View.GONE);
      textViewTitle.setVisibility(View.VISIBLE);
      textViewTitle.setAlpha(0f);
      textViewTitle.animate()
              .alpha(1f)
              .setDuration(500L)
              .start();

      // animate out the "done-edit" FAB
      fabEditDone.animate()
              .alpha(0f)
              .setDuration(500L)
              .withEndAction(() -> fabEditDone.setVisibility(View.GONE))
              .start();

      // animate in the "start-edit" FAB
      fabStartEdit.setVisibility(View.VISIBLE);
      fabStartEdit.setAlpha(0f);
      fabStartEdit.animate()
              .alpha(1f)
              .setDuration(500L)
              .start();

    }
    else{
      super.onBackPressed();
    }
  }

  // this function opens the keyboard
  private void showSoftKeyboard(View view) {
    if (view.requestFocus()) {
      InputMethodManager imm = (InputMethodManager)
              getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }
  }

  // this function closes the keyboard
  public void hideSoftKeyboard(View view){
    InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }

}