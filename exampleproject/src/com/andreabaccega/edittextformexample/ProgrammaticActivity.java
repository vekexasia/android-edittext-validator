package com.andreabaccega.edittextformexample;

import com.andreabaccega.formedittextvalidator.Validator;
import com.andreabaccega.widget.FormEditText;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProgrammaticActivity extends Activity {
  private FrameLayout flContainer;
  private TextView tvExplanation;
  private TextView tvTitle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_examplegeneric);

    flContainer     = (FrameLayout) findViewById(R.id.fl);
    tvExplanation   = (TextView)    findViewById(R.id.tv_explanation);
    tvTitle         = (TextView)    findViewById(R.id.tv_title);
    
    flContainer.addView(LayoutInflater.from(this).inflate(R.layout.example_nocheck, flContainer, false));
    tvExplanation.setText(R.string.explanation_programatic);
    tvTitle.setText(R.string.programmatic_title);
    FormEditText fdt = (FormEditText) findViewById(R.id.et);
    fdt.addValidator(new CiaoValidator());
  }
  
  public static class CiaoValidator extends Validator {

    public CiaoValidator() {
        super("You should enter 'ciao' here");
    }

    @Override
    public boolean isValid(EditText et) {
        return TextUtils.equals(et.getText(), "ciao");
    }

}
  
  public void onClickValidate(View v) {
    FormEditText fdt = (FormEditText) findViewById(R.id.et);
    if (fdt.testValidity()) {
      Toast.makeText(this, ":)", Toast.LENGTH_LONG).show();
    } 
  }
}
